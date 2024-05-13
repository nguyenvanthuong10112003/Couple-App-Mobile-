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

if (!(isset($_POST[DateInvitationAttr::id[KeyTable::name]]) && isset($_POST[DateInvitationAttr::isAccepted[KeyTable::name]]))) {
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, "Dữ liệu gửi lên không hợp lệ", null), JSON_UNESCAPED_UNICODE);
    exit(0);
}

try {
    $inviteId = intval($_POST[DateInvitationAttr::id[KeyTable::name]]);
} catch(Exception $e) {
} finally {
    if (!(isset($inviteId) && $inviteId > 0)) {
        echo json_encode(new RequestAPI(RequestAPI::$ERROR, "Dữ liệu gửi lên không hợp lệ", null), JSON_UNESCAPED_UNICODE);
        exit(0);
    }
}

$isAccept = $_POST[DateInvitationAttr::isAccepted[KeyTable::name]] === 'true';

$context = new DateInvitationRepository();
$invite = $context->getInviteById($inviteId);

if (!$invite) {
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, "Lời mời kết bạn này không tồn tại", null), JSON_UNESCAPED_UNICODE);
    exit(0);
}

if ($invite->receiverId != $currentUser->id) {
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, "Lời mời này không dành cho bạn", null), JSON_UNESCAPED_UNICODE);
    exit(0);
}

if ($invite->timeFeedBack != null) {
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, "Đã phản hồi trước đó", null), JSON_UNESCAPED_UNICODE);
    exit(0);
}

$invite->timeFeedBack = new DateTime();
$invite->isAccepted = $isAccept;

if (!$context->update($invite)) {
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, "Thất bại", null), JSON_UNESCAPED_UNICODE);
    exit(0);
}

if (!$invite->isAccepted) {
    echo json_encode(new RequestAPI(RequestAPI::$SUCCESS, "Thành công", null), JSON_UNESCAPED_UNICODE);
    exit(0);
}

$context = new CoupleRepository();
echo json_encode(new RequestAPI(RequestAPI::$SUCCESS, "Thành công", $context->getDtoForUser($currentUser->id)), JSON_UNESCAPED_UNICODE);





