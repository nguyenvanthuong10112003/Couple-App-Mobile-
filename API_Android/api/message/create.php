<?php 
include_once(dirname(__FILE__).'/../../authen/CheckURL.php');
include_once(dirname(__FILE__).'/../../include/ConnectSQL.php');
include_once(dirname(__FILE__).'/../../authen/AuthenLogin.php');
include_once(dirname(__FILE__).'/../../entity/RequestAPI.php');
include_once(dirname(__FILE__).'/../../helper/Convert.php');
include_once(dirname(__FILE__).'/../../data/MessageRepository.php');
include_once(dirname(__FILE__).'/../../data/CoupleRepository.php');
include_once(dirname(__FILE__).'/../../entity/Couple.php');
include_once(dirname(__FILE__).'/../../entity/Message.php');
include_once(dirname(__FILE__).'/../../attr/MessageAttribute.php');
include_once(dirname(__FILE__).'/../../dto/MessageDto.php');


if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
    header('HTTP/1.0 404 Not Found');
    exit(0);
}
if (!isset($_POST[MessageAttr::content[KeyTable::name]]) || strlen($_POST[MessageAttr::content[KeyTable::name]]) == 0) {
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, "Dữ liệu gửi lên không hợp lệ", null), JSON_UNESCAPED_UNICODE);
    exit(0);
}
$contextCouple = new CoupleRepository();
$coupleDto = $contextCouple->getDtoForUser($currentUser->id);
if (!$coupleDto) {
    echo json_encode(new RequestAPI(RequestAPI::$NO_HAVE_COUPLE, "Bạn chưa có cặp đôi, vui lòng ghép cặp đôi", null), JSON_UNESCAPED_UNICODE);
    exit(0);
}
$content = $_POST[MessageAttr::content[KeyTable::name]];
$message = new Message();
$message->senderId = $currentUser->id;
$message->coupleId = $coupleDto->id;
$message->timeSend = new DateTime();
$message->content = $content;
$context = new MessageRepository();
$result = $context->create($message);
if ($result) {
    $messageDto = new MessageDto();
    $messageDto->id = $result->id;
    $messageDto->timeSend = $result->timeSend;
    $messageDto->senderId = $currentUser->id;
    $messageDto->content = $result->content;
    echo json_encode(new RequestAPI(RequestAPI::$SUCCESS, "Thành công", $messageDto), JSON_UNESCAPED_UNICODE);
}
else
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, "Thất bại", null), JSON_UNESCAPED_UNICODE);


