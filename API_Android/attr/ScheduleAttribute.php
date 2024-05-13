<?php 
include_once(dirname(__FILE__).'/../authen/CheckURL.php');
include_once(dirname(__FILE__).'/../define/KeyTable.php');
class ScheduleAttr {
    public const id = [KeyTable::name => 'schedule_id', KeyTable::isPrimary => true];
    public const coupleId = [KeyTable::name => 'couple_id', KeyTable::canIsNull => false];
    public const senderId = [KeyTable::name => 'sender_id', KeyTable::canIsNull => false];
    public const time = [KeyTable::name => 'schedule_time'];
    public const timeSend = [KeyTable::name => 'schedule_timesend'];
    public const timeFeedBack = [KeyTable::name => 'schedule_timefeedback'];
    public const isAccept = [KeyTable::name => 'schedule_isaccept'];
    public const title = [KeyTable::name => 'schedule_title', KeyTable::canIsNull => false];
    public const content = [KeyTable::name => 'schedule_content'];
    public const deleted = [KeyTable::name => 'schedule_deleted'];
}