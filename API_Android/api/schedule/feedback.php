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
if (!(isset($_POST[ScheduleAttr::id[KeyTable::name]]) && isset($_POST[ScheduleAttr::isAccept[KeyTable::name]]))) {
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, "Dữ liệu gửi lên không hợp lệ", null), JSON_UNESCAPED_UNICODE);
    exit(0);
}
$context = new ScheduleRepository();
$schedule = $context->getByPK(intval($_POST[ScheduleAttr::id[KeyTable::name]]));
if (!$schedule || $schedule->coupleId != $couple->id) {
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, "Không tồn tại lời mời này", null), JSON_UNESCAPED_UNICODE);
    exit(0);
}
if ($schedule->senderId == $currentUser->id) {
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, "Bạn không thể tự phản hồi lời mời của bản thân", null), JSON_UNESCAPED_UNICODE);
    exit(0);
}
if (new DateTime() > $schedule->time) {
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, "Không thể phản hồi lịch trình đã qua", null), JSON_UNESCAPED_UNICODE);
    exit(0);
}
if ($schedule->isDeleted) {
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, "Lời mời này đã xóa trước đó", null), JSON_UNESCAPED_UNICODE);
    exit(0);
}
$schedule->isAccept = $_POST[ScheduleAttr::isAccept[KeyTable::name]] === "true";
$schedule->timeFeedBack = new DateTime();
if ($context->feedBack($schedule))
    echo json_encode(new RequestAPI(RequestAPI::$SUCCESS, "Thành công", null), JSON_UNESCAPED_UNICODE);
else 
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, "Thất bại", null), JSON_UNESCAPED_UNICODE);