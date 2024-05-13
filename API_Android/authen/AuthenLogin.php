<?php
include_once(dirname(__FILE__).'/../authen/CheckURL.php');
include_once(dirname(__DIR__).'/service/TokenService.php');
include_once(dirname(__DIR__).'/data/UserRepository.php');
include_once(dirname(__DIR__).'/entity/RequestAPI.php');
if (!(isset(getallheaders()['authorization']) || isset(getallheaders()['Authorization']))) {
    echo json_encode(new RequestAPI(RequestAPI::$NEED_LOGIN, 'Cần đăng nhập', null), JSON_UNESCAPED_UNICODE);
    exit(0);
}

$AUTHORIZATION = isset(getallheaders()['Authorization']) ? getallheaders()['Authorization'] : getallheaders()['authorization'];

if (!preg_match('/Bearer\s(\S+)/', $AUTHORIZATION, $matches)) {
    echo json_encode(new RequestAPI(RequestAPI::$NEED_LOGIN, 'Thiếu token', null), JSON_UNESCAPED_UNICODE);
    exit(0);
}

$jwt = $matches[1];
if (!$jwt) {
    echo json_encode(new RequestAPI(RequestAPI::$NEED_LOGIN, 'Thiếu token', null), JSON_UNESCAPED_UNICODE);
    exit(0);
}

$token = TokenService::decodeToken($jwt);

if (!$token) {
    echo json_encode(new RequestAPI(RequestAPI::$NEED_LOGIN, 'Token không hợp lệ', null), JSON_UNESCAPED_UNICODE);
    exit(0);
}

$now = new DateTimeImmutable();

if ($token->nbf > $now->getTimestamp() ||
    $token->exp < $now->getTimestamp())
{
    echo json_encode(new RequestAPI(RequestAPI::$NEED_LOGIN, 'Token hết hạn', null), JSON_UNESCAPED_UNICODE);
    exit(0);
}

$id = $token->id;

$context = new UserRepository();

$currentUser = $context->getByPK($id);

if (!$currentUser)
{
    echo json_encode(new RequestAPI(RequestAPI::$NEED_LOGIN, 'Người dùng không tồn tại', null), JSON_UNESCAPED_UNICODE);
    exit(0);
}

if ($currentUser->locked === true)
{
    echo json_encode(new RequestAPI(RequestAPI::$NEED_LOGIN, 'Tài khoản không tồn tại', null), JSON_UNESCAPED_UNICODE);
    exit(0);
}



