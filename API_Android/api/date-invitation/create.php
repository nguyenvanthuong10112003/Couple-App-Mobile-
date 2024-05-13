<?php 
include_once(dirname(__FILE__).'/../../authen/CheckURL.php');
include_once(dirname(__FILE__).'/../../include/ConnectSQL.php');
include_once(dirname(__FILE__).'/../../data/UserRepository.php');
include_once(dirname(__FILE__).'/../../authen/AuthenLogin.php');
include_once(dirname(__FILE__).'/../../entity/RequestAPI.php');
include_once(dirname(__FILE__).'/../../helper/Convert.php');
include_once(dirname(__FILE__).'/../../attr/DateInvitationAttribute.php');
include_once(dirname(__FILE__).'/../../entity/DateInvitationAdd.php');
include_once(dirname(__FILE__).'/../../entity/DateInvitation.php');
include_once(dirname(__FILE__).'/../../data/DateInvitationRepository.php');
include_once(dirname(__FILE__).'/../../data/CoupleRepository.php');

if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
    header('HTTP/1.0 404 Not Found');
    exit(0);
}

if (!isset($_POST[DateInvitationAttr::receiverId[KeyTable::name]])) {
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, "Dữ liệu gửi lên không hợp lệ", null), JSON_UNESCAPED_UNICODE);
    exit(0);
}

try {
    $receiverId = intval($_POST[DateInvitationAttr::receiverId[KeyTable::name]]);
} catch(Exception $e) {
} finally {
    if (!isset($receiverId) || $receiverId <= 0)
    {
        echo json_encode(new RequestAPI(RequestAPI::$ERROR, "Dữ liệu gửi lên không hợp lệ", null), JSON_UNESCAPED_UNICODE);
        exit(0);
    }
}

if ($currentUser->id == $receiverId) {
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, "Người nhận và người gửi không thể là cùng 1 người", null), JSON_UNESCAPED_UNICODE);
    exit(0);
}

$contextUser = new UserRepository();
$userReceiver = $contextUser->getByPK($receiverId);

if (!$userReceiver) {
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, "Người nhận không tồn tại trong hệ thống", null), JSON_UNESCAPED_UNICODE);
    exit(0);
}

$contextCouple = new CoupleRepository();
if ($contextCouple->getDtoForUser($currentUser->id) || $contextCouple->getDtoForUser($receiverId)) {
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, "Người gửi hoặc người nhận đã có cặp đôi", null), JSON_UNESCAPED_UNICODE);
    exit(0);
}

$context = new DateInvitationRepository();
$result = $context->getInvite($currentUser->id, $receiverId);
if ($result && count($result) > 0) {
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, "Người này đã gửi lời mời rồi, vui lòng chờ phản hồi", null), JSON_UNESCAPED_UNICODE);
    exit(0);
} 

$dateInvitationAdd = new DateInvitationAdd();
$dateInvitationAdd->senderId = $currentUser->id;
$dateInvitationAdd->receiverId = $receiverId;

$result = $context->create($dateInvitationAdd);
if ($result && $result > 0) {
    $kq = new DateInvitation();
    $kq->id = $result;
    $kq->receiverId = $dateInvitationAdd->receiverId;
    $kq->senderId = $currentUser->id;
    $kq->timeSend = $dateInvitationAdd->timeSend;
    echo json_encode(new RequestAPI(RequestAPI::$SUCCESS, "Thành công", $kq), JSON_UNESCAPED_UNICODE);
} else {
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, "Thất bại", null), JSON_UNESCAPED_UNICODE);    
}