<?php  
include_once(dirname(__FILE__).'/../authen/CheckURL.php');
include_once(dirname(__FILE__).'/../define/KeyTable.php');
class MessageAttr
{
    public const id = [KeyTable::name => 'message_id', KeyTable::isPrimary => true];
    public const timeSend = [KeyTable::name => 'message_timesend'];
    public const timeRead = [KeyTable::name => 'message_timeread'];
    public const coupleId = [KeyTable::name => 'couple_id', KeyTable::canIsNull => false];
    public const senderId = [KeyTable::name => 'sender_id', KeyTable::canIsNull => false];
    public const content = [KeyTable::name => 'message_content'];
}