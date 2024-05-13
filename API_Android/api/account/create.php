<?php 
include_once(dirname(__FILE__).'/../../authen/CheckURL.php');
include_once(dirname(__FILE__).'/../../data/UserRepository.php');
include_once(dirname(__FILE__).'/../../entity/RequestAPI.php');
include_once(dirname(__FILE__).'/../../entity/UserAdd.php');
include_once(dirname(__FILE__).'/../../dto/UserDto.php');
include_once(dirname(__FILE__).'/../../helper/StringHelper.php');

if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
    header('HTTP/1.0 404 Not Found');
    exit(0);
}

if (!(isset($_POST[UserAttr::username[KeyTable::name]]) && isset($_POST[UserAttr::password[KeyTable::name]]) && 
    isset($_POST[UserAttr::email[KeyTable::name]]))) {
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, "Dữ liệu gửi lên không hợp lệ", null), JSON_UNESCAPED_UNICODE);
    exit(0);
}

$username = trim($_POST[UserAttr::username[KeyTable::name]]);
$password = $_POST[UserAttr::password[KeyTable::name]];
$email = $_POST[UserAttr::email[KeyTable::name]];

if (!StringHelper::isValUsername($username)) {
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, 'Tên đăng nhập chỉ chứa các ký tự a-z, A-Z, 0-9', null), JSON_UNESCAPED_UNICODE);
    exit(0);
}

if (!StringHelper::isValPassword($password)) {
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, 'Mật khẩu phải có tối thiểu 6 ký tự', null), JSON_UNESCAPED_UNICODE);
    exit(0);
}

if (!StringHelper::isValEmail($email)) {
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, 'Email không đúng định dạng', null), JSON_UNESCAPED_UNICODE);
    exit(0);
}

$context = new UserRepository();
$users = $context->getByUnique(['username' => $username, 'email' => $email]);

if ($users && count($users) > 0) {
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, "Tên đăng nhập hoặc email đã tồn tại", null), JSON_UNESCAPED_UNICODE);
    exit(0);
}

$userAdd = new UserAdd();
$userAdd->username = $username;
$userAdd->password = $password;
$userAdd->email = $email;

if ($context->create($userAdd)) 
    echo json_encode(new RequestAPI(RequestAPI::$SUCCESS, "Tạo thành công", null), JSON_UNESCAPED_UNICODE);
else 
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, "Thất bại", null), JSON_UNESCAPED_UNICODE);