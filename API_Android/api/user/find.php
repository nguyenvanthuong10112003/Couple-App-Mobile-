<?php 
include_once(dirname(__FILE__).'/../../authen/CheckURL.php');
include_once(dirname(__FILE__).'/../../include/ConnectSQL.php');
include_once(dirname(__FILE__).'/../../data/UserRepository.php');
include_once(dirname(__FILE__).'/../../authen/AuthenLogin.php');
include_once(dirname(__FILE__).'/../../dto/UserDto.php');
include_once(dirname(__FILE__).'/../../helper/Convert.php');

if (!$_SERVER['REQUEST_METHOD'] === 'GET') {
    header('HTTP/1.0 404');
    exit(0);
}

$params = [];
if (isset($_GET['user_username'])) 
    $params['username'] = $_GET['user_username'];
if (isset($_GET['user_email'])) 
    $params['email'] = $_GET['user_email'];
if (isset($_GET['user_fullname']))
    $params['name'] = $_GET['user_fullname'];

$context = new UserRepository();
$userDtos = $context->findDto($params);

echo json_encode($userDto, JSON_UNESCAPED_UNICODE);
