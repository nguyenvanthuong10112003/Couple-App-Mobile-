<?php 
include_once(dirname(__FILE__).'/../authen/CheckURL.php');
class DateInvitation 
{
    public int $id;
    public DateTime $timeSend;
    public ?DateTime $timeFeedBack;
    public ?bool $isAccepted;
    public int $receiverId;
    public int $senderId;
}