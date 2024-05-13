<?php 
include_once(dirname(__FILE__).'/../../authen/CheckURL.php');
include_once(dirname(__FILE__).'/../../include/ConnectSQL.php');
include_once(dirname(__FILE__).'/../../data/UserRepository.php');
include_once(dirname(__FILE__).'/../../authen/AuthenLogin.php');
include_once(dirname(__FILE__).'/../../dto/UserDto.php');
include_once(dirname(__FILE__).'/../../helper/Convert.php');
include_once(dirname(__FILE__).'/../../data/DateInvitationRepository.php');
include_once(dirname(__FILE__).'/../../entity/RequestAPI.php');
$context = new UserRepository();

echo json_encode(new RequestAPI(RequestAPI::$SUCCESS, "Thành công", $context->getAllDtoForDateInvitation($currentUser->id)), JSON_UNESCAPED_UNICODE);
