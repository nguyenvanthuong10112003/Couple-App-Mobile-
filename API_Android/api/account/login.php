<?php
include_once(dirname(__FILE__).'/../../authen/CheckURL.php');
include_once(dirname(__FILE__).'/../../data/UserRepository.php');
include_once(dirname(__FILE__).'/../../service/TokenService.php');
include_once(dirname(__FILE__).'/../../entity/UserToken.php');
include_once(dirname(__FILE__).'/../../attr/UserAttribute.php');
include_once(dirname(__FILE__)."/../../entity/RequestAPI.php");
include_once(dirname(__FILE__).'/../../helper/StringHelper.php');
include_once(dirname(__FILE__).'/../../service/PhotoService.php');
include_once(dirname(__FILE__).'/../../dto/UserDto.php');

if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
    header('HTTP/1.0 404 Not Found');
    exit(0);
}

if (!(isset($_POST[UserAttr::username[KeyTable::name]]) && isset($_POST[UserAttr::password[KeyTable::name]]))) {
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, 'Username và password là bắt buộc', null), JSON_UNESCAPED_UNICODE);
    exit(0);
}

$username = $_POST[UserAttr::username[KeyTable::name]];
$password = $_POST[UserAttr::password[KeyTable::name]];

if (!StringHelper::isValUsername($username)) {
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, 'Tên đăng nhập chỉ chứa các ký tự a-z, A-Z, 0-9', null), JSON_UNESCAPED_UNICODE);
    exit(0);
}

if (!StringHelper::isValPassword($password)) {
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, 'Mật khẩu phải có tối thiểu 6 ký tự', null), JSON_UNESCAPED_UNICODE);
    exit(0);
}

$context = new UserRepository();

$user = $context->getByUsername($username);

if (!$user) {
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, 'Người dùng không tồn tại', null), JSON_UNESCAPED_UNICODE);
    exit(0);
}

if ($user->password !== $password) {
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, 'Mật khẩu không chính xác', null), JSON_UNESCAPED_UNICODE);
    exit(0);
}

if ($user->locked === true) {
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, 'Tài khoản của bạn đang bị khóa', null), JSON_UNESCAPED_UNICODE);
    exit(0);
}

$user = $context->getDtoByUsername($user->username);

$userToken = new UserToken();
$userToken->id = $user->id;
$userToken->username = $user->username;
$userToken->alias = $user->alias;
$userToken->urlAvatar = $user->urlAvatar;
$userToken->token = TokenService::createToken($user);
$userToken->fullName = $user->fullName;
$userToken->email = $user->email;
echo json_encode(new RequestAPI(RequestAPI::$SUCCESS, "Đăng nhập thành công", $userToken), JSON_UNESCAPED_UNICODE);