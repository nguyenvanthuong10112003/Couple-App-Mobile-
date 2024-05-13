<?php 
include_once(dirname(__FILE__).'/../authen/CheckURL.php');
include_once(dirname(__FILE__).'/../dto/UserDto.php');
class MessageDto {
    public int $id;
    public int $senderId;
    public DateTime $timeSend;
    public ?DateTime $timeRead;
    public string $content;
}