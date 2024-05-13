<?php 
include_once(dirname(__FILE__).'/../../authen/CheckURL.php');
include_once(dirname(__FILE__).'/../../include/ConnectSQL.php');
include_once(dirname(__FILE__).'/../../authen/AuthenLogin.php');
include_once(dirname(__FILE__).'/../../entity/RequestAPI.php');
include_once(dirname(__FILE__).'/../../helper/Convert.php');
include_once(dirname(__FILE__).'/../../data/CoupleRepository.php');
include_once(dirname(__FILE__).'/../../data/FareWellRepository.php');

$contextCouple = new CoupleRepository();
$couple = $contextCouple->getForUser($currentUser->id);
if (!$couple) {
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, "Bạn chưa có cặp đôi, vui lòng ghép cặp đôi", null), JSON_UNESCAPED_UNICODE);
    exit(0);
}

$context = new FarewellRequestRepository();
echo json_encode(new RequestAPI(RequestAPI::$SUCCESS, "Thành công", $context->get($couple->id)), JSON_UNESCAPED_UNICODE);