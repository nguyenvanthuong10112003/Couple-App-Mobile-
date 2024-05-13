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

if ($_SERVER['REQUEST_METHOD'] !== 'GET') {
    header('HTTP/1.0 404 Not Found');
    exit(0);
}

if (!isset($_GET[DateInvitationAttr::id[KeyTable::name]])) {
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, "Dữ liệu gửi lên không hợp lệ", null), JSON_UNESCAPED_UNICODE);
    exit(0);
}

try {
    $inviteId = intval($_GET[DateInvitationAttr::id[KeyTable::name]]);
} catch(Exception $e) {
} finally {
    if (!(isset($inviteId) && $inviteId > 0)) {
        echo json_encode(new RequestAPI(RequestAPI::$ERROR, "Dữ liệu gửi lên không hợp lệ", null), JSON_UNESCAPED_UNICODE);
        exit(0);
    }
}

$context = new DateInvitationRepository();
$invite = $context->getInviteById($inviteId);
if (!$invite) {
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, "Lời mời này không tồn tại", null), JSON_UNESCAPED_UNICODE);
    exit(0);
}

if ($invite->senderId != $currentUser->id) {
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, "Lời mời này bạn không phải người gửi", null), JSON_UNESCAPED_UNICODE);
    exit(0);
}

if ($invite->timeFeedBack != null) {
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, "Lời mời này đã được phản hồi, không thể hủy", null), JSON_UNESCAPED_UNICODE);
    exit(0);
}

if ($context->delete($invite->id))
    echo json_encode(new RequestAPI(RequestAPI::$SUCCESS, "Hủy thành công", null), JSON_UNESCAPED_UNICODE);
else 
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, "Thất bại", null), JSON_UNESCAPED_UNICODE);