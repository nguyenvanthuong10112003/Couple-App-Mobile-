<?php 
include_once(dirname(__FILE__).'/../authen/CheckURL.php');
class DateInvitationAdd
{
    public DateTime $timeSend;
    public int $receiverId;
    public int $senderId;
    public function __construct() {
        $this->timeSend = new DateTime();
    }
}