<?php 
include_once(dirname(__FILE__).'/../authen/CheckURL.php');
include_once(dirname(__FILE__).'/../define/KeyTable.php');
class CoupleAttr
{
    public const id = [KeyTable::name => 'couple_id', KeyTable::isPrimary => true];
    public const timeStart = [KeyTable::name => 'couple_timestart'];
    public const dateInvitationId = [KeyTable::name => 'di_id', KeyTable::canIsNull => false];
    public const photoId = [KeyTable::name => 'photo_id']; 
}