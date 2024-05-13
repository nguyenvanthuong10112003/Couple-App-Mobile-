<?php 
include_once(dirname(__FILE__).'/../authen/CheckURL.php');
include_once(dirname(__FILE__).'/../define/KeyTable.php');

class FarewellRequestAttr {
    public const id = [KeyTable::name => 'fr_id', KeyTable::isPrimary => true];
    public const timeSend = [KeyTable::name => 'fr_timesend'];
    public const timeFeedBack = [KeyTable::name => 'fr_timefeedback'];
    public const isAccept = [KeyTable::name => 'fr_isaccept'];
    public const senderId = [KeyTable::name => 'sender_id', KeyTable::canIsNull => false];
    public const coupleId = [KeyTable::name => 'couple_id', KeyTable::canIsNull => false];
}
