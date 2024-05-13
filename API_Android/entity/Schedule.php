<?php 
include_once(dirname(__FILE__).'/../authen/CheckURL.php');

class Schedule {
    public int $id;
    public int $coupleId;
    public int $senderId;
    public DateTime $time;
    public DateTime $timeSend;
    public ?DateTime $timeFeedBack;
    public ?bool $isAccept;
    public string $title;
    public ?string $content;
    public ?bool $isDeleted;
}