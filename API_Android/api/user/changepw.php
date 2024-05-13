<?php 
include_once(dirname(__FILE__).'/../../authen/CheckURL.php');
include_once(dirname(__FILE__).'/../../data/UserRepository.php');
include_once(dirname(__FILE__).'/../../authen/AuthenLogin.php');
include_once(dirname(__FILE__).'/../../entity/RequestAPI.php');
include_once(dirname(__FILE__).'/../../helper/StringHelper.php');

if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
    header('HTTP/1.0 404 Not Found');
    exit(0);
}

if (!(isset($_POST[UserAttr::password[KeyTable::name]]) && isset($_POST[UserAttr::newPassword[KeyTable::name]]))) {
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, "Dữ liệu gửi lên không hợp lệ", null), JSON_UNESCAPED_UNICODE);
    exit(0);
}

$oldPassword = $_POST[UserAttr::password[KeyTable::name]];
$newPassword = $_POST[UserAttr::newPassword[KeyTable::name]];

if (!StringHelper::isValPassword($newPassword) || !StringHelper::isValPassword($oldPassword)) {
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, 'Mật khẩu phải có tối thiểu 6 ký tự', null), JSON_UNESCAPED_UNICODE);
    exit(0);
}

if ($oldPassword == $newPassword) {
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, 'Mật khẩu mới không được trùng mật khẩu cũ', null), JSON_UNESCAPED_UNICODE);
    exit(0);
}

$context = new UserRepository();
$user = $context->getByUsername($currentUser->username);
if ($user->password != $oldPassword) {
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, 'Mật khẩu cũ không chính xác', null), JSON_UNESCAPED_UNICODE);
    exit(0);
}

$user->password = $newPassword;

if ($context->update($user))
    echo json_encode(new RequestAPI(RequestAPI::$SUCCESS, "Thành công", null), JSON_UNESCAPED_UNICODE);
else 
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, "Thất bại", null), JSON_UNESCAPED_UNICODE);


