<?php 
include_once(dirname(__FILE__).'/../authen/CheckURL.php');
include_once(dirname(__FILE__).'/../entity/User.php');
class CoupleDto
{
    public int $id;
    public DateTime $timeStart;
    public int $dateInvitationId;
    public ?string $photoUrl;
    public UserDto $mind;
    public UserDto $enemy;
}