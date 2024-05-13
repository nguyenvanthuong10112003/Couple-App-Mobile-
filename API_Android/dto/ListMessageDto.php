<?php 
include_once(dirname(__FILE__).'/../authen/CheckURL.php');
include_once(dirname(__FILE__).'/../dto/UserDto.php');
include_once(dirname(__FILE__).'/../dto/CoupleDto.php');
class ListMessageDto
{
    public CoupleDto $couple;
    public array $messages;
}