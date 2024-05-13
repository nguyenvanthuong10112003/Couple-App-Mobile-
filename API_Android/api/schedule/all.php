<?php 
include_once(dirname(__FILE__).'/../../authen/CheckURL.php');
include_once(dirname(__FILE__).'/../../include/ConnectSQL.php');
include_once(dirname(__FILE__).'/../../authen/AuthenLogin.php');
include_once(dirname(__FILE__).'/../../entity/RequestAPI.php');
include_once(dirname(__FILE__).'/../../helper/Convert.php');
include_once(dirname(__FILE__).'/../../data/ScheduleRepository.php');
include_once(dirname(__FILE__).'/../../entity/Couple.php');
include_once(dirname(__FILE__).'/../../entity/Schedule.php');
include_once(dirname(__FILE__).'/../../data/CoupleRepository.php');

$contextCouple = new CoupleRepository();
$couple = $contextCouple->getForUser($currentUser->id);
if (!$couple) {
    echo json_encode(new RequestAPI(RequestAPI::$NO_HAVE_COUPLE, "Bạn chưa có cặp đôi, vui lòng ghép cặp đôi", null), JSON_UNESCAPED_UNICODE);
    exit(0);
}
$context = new ScheduleRepository();
echo json_encode(new RequestAPI(RequestAPI::$SUCCESS, "Thành công", $context->getAll($couple->id, $currentUser->id)), JSON_UNESCAPED_UNICODE);