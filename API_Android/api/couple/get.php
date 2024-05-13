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

$context = new CoupleRepository();
echo json_encode(new RequestAPI(RequestAPI::$SUCCESS, "Thành công", $context->getDtoForUser($currentUser->id)), JSON_UNESCAPED_UNICODE);