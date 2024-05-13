<?php 
include_once(dirname(__FILE__).'/../authen/CheckURL.php');
class Message {
    public int $id;
    public ?int $coupleId;
    public int $senderId;
    public DateTime $timeSend;
    public ?DateTime $timeRead;
    public string $content;
}