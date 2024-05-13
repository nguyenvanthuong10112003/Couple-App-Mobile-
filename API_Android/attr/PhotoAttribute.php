<?php  
include_once(dirname(__FILE__).'/../authen/CheckURL.php');
include_once(dirname(__FILE__).'/../define/KeyTable.php');
class PhotoAttr { 
    public const id = [KeyTable::name => 'photo_id', KeyTable::isPrimary => true];
    public const publicId = [KeyTable::name => 'photo_publicid', KeyTable::isUnique => true, KeyTable::canIsNull => false];
    public const url = [KeyTable::name => 'photo_url', KeyTable::canIsNull => false];
}
