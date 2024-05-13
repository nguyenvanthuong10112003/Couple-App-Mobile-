<?php

include_once(dirname(__FILE__).'/../../authen/CheckURL.php');
include_once(dirname(__FILE__).'/../../data/UserRepository.php');
include_once(dirname(__FILE__).'/../../entity/RequestAPI.php');
include_once(dirname(__FILE__).'/../../entity/UserAdd.php');
include_once(dirname(__FILE__).'/../../dto/UserDto.php');
include_once(dirname(__FILE__).'/../../helper/StringHelper.php');
include_once(dirname(__FILE__).'/../../service/EmailService.php');

function checkAuthenCodeOk(User $user, $authenCode):bool {
    if ($user->authenCode != $authenCode) 
        return false;
    $diff = (new DateTime())->getTimestamp() - $user->authenTimeCreate->getTimestamp();
    if ($diff < 0 || $diff / 60 > 5)
        return false;
    return true;
}

if ($_SERVER['REQUEST_METHOD'] == 'GET') {
    if (!isset($_GET[UserAttr::email[KeyTable::name]])) {
        echo json_encode(new RequestAPI(RequestAPI::$ERROR, "Dữ liệu gửi lên không hợp lệ", null), JSON_UNESCAPED_UNICODE);
        exit(0);
    }
    $email = $_GET[UserAttr::email[KeyTable::name]];
    if (!StringHelper::isValEmail($email)) {
        echo json_encode(new RequestAPI(RequestAPI::$ERROR, "Email không đúng định dạng", null), JSON_UNESCAPED_UNICODE);
        exit(0);
    }
    $context = new UserRepository();
    $user = $context->getByEmail($email);
    if (!$user) {
        echo json_encode(new RequestAPI(RequestAPI::$ERROR, "Email không tồn tại trong hệ thống", null), JSON_UNESCAPED_UNICODE);
        exit(0);
    } 
    $maXacThuc = '';
    for ($i = 0; $i < 6; $i++)
        $maXacThuc = $maXacThuc . $random_number = mt_rand(0, 9);
    $user->authenCode = $maXacThuc;
    $user->authenTimeCreate = new DateTime();
    if (!$context->update($user)) {
        echo json_encode(new RequestAPI(RequestAPI::$ERROR, "Thất bại", null), JSON_UNESCAPED_UNICODE);
        exit(0);
    }
    if (EmailService::sendEmailAuthenCode($user->email, $user->fullName, $user->authenCode) === true) 
        echo json_encode(new RequestAPI(RequestAPI::$SUCCESS, 'Thành công', null), JSON_UNESCAPED_UNICODE);
    else
        echo json_encode(new RequestAPI(RequestAPI::$ERROR, 'Lỗi gửi mail', null), JSON_UNESCAPED_UNICODE);
} else 
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    if (!(isset($_POST[UserAttr::email[KeyTable::name]]) && isset($_POST[UserAttr::authenCode[KeyTable::name]]))) {
        echo json_encode(new RequestAPI(RequestAPI::$ERROR, "Dữ liệu gửi lên không hợp lệ", null), JSON_UNESCAPED_UNICODE);
        exit(0);
    }
    $email = $_POST[UserAttr::email[KeyTable::name]];
    $authenCode = $_POST[UserAttr::authenCode[KeyTable::name]];
    if (!StringHelper::isValEmail($email)) {
        echo json_encode(new RequestAPI(RequestAPI::$ERROR, "Email không đúng định dạng", null), JSON_UNESCAPED_UNICODE);
        exit(0);
    }
    if (!StringHelper::isValAuthenCode($authenCode)) {
        echo json_encode(new RequestAPI(RequestAPI::$ERROR, "Mã xác thực không hợp lệ", null), JSON_UNESCAPED_UNICODE);
        exit(0);
    }
    $context = new UserRepository();
    $user = $context->getByEmail($email);
    if (!$user) {
        echo json_encode(new RequestAPI(RequestAPI::$ERROR, "Email không tồn tại trong hệ thống", null), JSON_UNESCAPED_UNICODE);
        exit(0);
    }
    if (!checkAuthenCodeOk($user, $authenCode)) {
        echo json_encode(new RequestAPI(RequestAPI::$ERROR,"Mã xác thực không chính xác hoặc đã hết hạn.", null), JSON_UNESCAPED_UNICODE);
        exit(0);
    }
    if (!isset($_PUT[UserAttr::password[KeyTable::name]])) {
        echo json_encode(new RequestAPI(RequestAPI::$SUCCESS, "Thành công", null), JSON_UNESCAPED_UNICODE);
        exit(0);
    }
    $newPassword = $_PUT[UserAttr::password[KeyTable::name]];
    if (!StringHelper::isValPassword($newPassword)) {
        echo json_encode(new RequestAPI(RequestAPI::$ERROR, "Mật khẩu phải có tối thiểu 6 ký tự", null), JSON_UNESCAPED_UNICODE);
        exit(0);
    }
    $user->password = $newPassword;
    if ($context->update($newPassword))
        echo json_encode(new RequestAPI(RequestAPI::$ERROR, "Đổi mật khẩu mới thành công.", null), JSON_UNESCAPED_UNICODE);
    else 
        echo json_encode(new RequestAPI(RequestAPI::$ERROR, "Thất bại.", null), JSON_UNESCAPED_UNICODE);
}