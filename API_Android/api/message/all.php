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
include_once(dirname(__FILE__).'/../../dto/ListMessageDto.php');

$contextCouple = new CoupleRepository();
$couple = $contextCouple->getDtoForUser($currentUser->id);
if (!$couple) {
    echo json_encode(new RequestAPI(RequestAPI::$NO_HAVE_COUPLE, "Bạn chưa có cặp đôi, vui lòng ghép cặp đôi", null), JSON_UNESCAPED_UNICODE);
    exit(0);
}
$context = new MessageRepository();
$context->read($couple->id, $currentUser->id);
$listMessageDto = new ListMessageDto();
$listMessageDto->couple = $couple;
$listMessageDto->messages = $context->getAllMessages($couple);
echo json_encode(new RequestAPI(RequestAPI::$SUCCESS, "Thành công", $listMessageDto), JSON_UNESCAPED_UNICODE);