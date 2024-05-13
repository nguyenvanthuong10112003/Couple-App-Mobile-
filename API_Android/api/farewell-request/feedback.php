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
include_once(dirname(__FILE__).'/../../data/CoupleRepository.php');
include_once(dirname(__FILE__).'/../../data/FareWellRepository.php');
include_once(dirname(__FILE__).'/../../attr/FarewellRequestAttribute.php');

if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
    header('HTTP/1.0 404 Not Found');
    exit(0);
}

if (!isset($_POST[FarewellRequestAttr::isAccept[KeyTable::name]])) {
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, "Dữ liệu gửi lên không hợp lệ", null), JSON_UNESCAPED_UNICODE);
    exit(0);
}

$contextCouple = new CoupleRepository();
$couple = $contextCouple->getForUser($currentUser->id);
if (!$couple) {
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, "Bạn chưa có cặp đôi, vui lòng ghép cặp đôi", null), JSON_UNESCAPED_UNICODE);
    exit(0);
}

$context = new FarewellRequestRepository();
$farewell = $context->get($couple->id);
if (!$farewell) {
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, "Đối phương chưa gửi yêu cầu", null), JSON_UNESCAPED_UNICODE);
    exit(0);   
}

if ($farewell->senderId == $currentUser->id) {
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, "Bạn không thể tự phản hồi yêu cầu của mình", null), JSON_UNESCAPED_UNICODE);
    exit(0);   
}

$farewell->timeFeedBack = new DateTime();
$farewell->isAccept = ($_POST[FarewellRequestAttr::isAccept[KeyTable::name]] === 'true');

if ($context->update($farewell)) 
    echo json_encode(new RequestAPI(RequestAPI::$SUCCESS, "Thành công", null), JSON_UNESCAPED_UNICODE);
else 
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, "Thất bại", null), JSON_UNESCAPED_UNICODE);