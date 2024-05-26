<?php 
include_once(dirname(__FILE__).'/../../authen/CheckURL.php');
include_once(dirname(__FILE__).'/../../include/ConnectSQL.php');
include_once(dirname(__FILE__).'/../../authen/AuthenLogin.php');
include_once(dirname(__FILE__).'/../../entity/RequestAPI.php');
include_once(dirname(__FILE__).'/../../helper/Convert.php');
include_once(dirname(__FILE__).'/../../data/ScheduleRepository.php');
include_once(dirname(__FILE__).'/../../entity/Couple.php');
include_once(dirname(__FILE__).'/../../entity/Schedule.php');
include_once(dirname(__FILE__).'/../../attr/ScheduleAttribute.php');
include_once(dirname(__FILE__).'/../../data/CoupleRepository.php');


if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
    header('HTTP/1.0 404 Not Found');
    exit(0);
}
$contextCouple = new CoupleRepository();
$couple = $contextCouple->getForUser($currentUser->id);
if (!$couple) {
    echo json_encode(new RequestAPI(RequestAPI::$NO_HAVE_COUPLE, "Bạn chưa có cặp đôi, vui lòng ghép cặp đôi", null), JSON_UNESCAPED_UNICODE);
    exit(0);
}
if (!(isset($_POST[ScheduleAttr::title[KeyTable::name]]) && isset($_POST[ScheduleAttr::time[KeyTable::name]])) || 
    strlen($_POST[ScheduleAttr::title[KeyTable::name]]) == 0) {
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, "Dữ liệu gửi lên không hợp lệ", null), JSON_UNESCAPED_UNICODE);
    exit(0);
}
$scheduleAdd = new Schedule();
$scheduleAdd->title = $_POST[ScheduleAttr::title[KeyTable::name]];
try {
    $scheduleAdd->time = new DateTime($_POST[ScheduleAttr::time[KeyTable::name]]);
} catch(Exception $e) {
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, "Dữ liệu gửi lên không hợp lệ", null), JSON_UNESCAPED_UNICODE);
    exit(0);
} finally {
    if (new DateTime() > $scheduleAdd->time) {
        echo json_encode(new RequestAPI(RequestAPI::$ERROR, "Không thể thêm lịch trình vào quá khứ", null), JSON_UNESCAPED_UNICODE);
        exit(0);
    }
}
$context = new ScheduleRepository();
if ($context->findByTime($couple->id, $scheduleAdd->time)) {
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, "Thời gian này đã có lịch trình trước đó rồi", null), JSON_UNESCAPED_UNICODE);
    exit(0);
}
if (isset($_POST[ScheduleAttr::content[KeyTable::name]]))
    $scheduleAdd->content = $_POST[ScheduleAttr::content[KeyTable::name]];
$scheduleAdd->coupleId = $couple->id;
$scheduleAdd->senderId = $currentUser->id;
$scheduleAdd->timeSend = new DateTime();
$result = $context->create($scheduleAdd);
if ($result) 
    echo json_encode(new RequestAPI(RequestAPI::$SUCCESS, "Thành công", $result), JSON_UNESCAPED_UNICODE);
else 
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, "Thất bại", null), JSON_UNESCAPED_UNICODE);