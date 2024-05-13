<?php 
include_once(dirname(__FILE__).'/../authen/CheckURL.php');
include_once(dirname(__FILE__).'/../define/KeyTable.php');
class DateInvitationAttr
{
    public const id = [KeyTable::name => 'di_id', KeyTable::isPrimary => true];
    public const timeSend = [KeyTable::name => 'di_timesend'];
    public const timeFeedBack = [KeyTable::name => 'di_timefeedback'];
    public const isAccepted = [KeyTable::name => 'di_is_accepted'];
    public const senderId = [KeyTable::name => 'sender_id', KeyTable::canIsNull => false];
    public const receiverId = [KeyTable::name => 'receiver_id', KeyTable::canIsNull => false];
}