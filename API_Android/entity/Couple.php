<?php 
include_once(dirname(__FILE__).'/../authen/CheckURL.php');
class Couple
{
    public int $id;
    public DateTime $timeStart;
    public ?int $photoId;
    public int $dateInvitaionId;
}