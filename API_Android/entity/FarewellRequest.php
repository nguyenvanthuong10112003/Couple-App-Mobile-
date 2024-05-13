<?php 
include_once(dirname(__FILE__).'/../authen/CheckURL.php');
class FarewellRequest
{
    public int $id;
    public int $coupleId;
    public int $senderId;
    public DateTime $timeSend;
    public ?DateTime $timeFeedBack;
    public ?bool $isAccept;
}