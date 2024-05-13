<?php  
include_once(dirname(__FILE__).'/../authen/CheckURL.php');
include_once(dirname(__FILE__).'/../define/KeyTable.php');
class UserAttr { 
    public const id = [KeyTable::name => 'user_id', KeyTable::isPrimary => true];
    public const fullName = [KeyTable::name => 'user_fullname'];
    public const alias = [KeyTable::name => 'user_alias'];
    public const dob = [KeyTable::name => 'user_dob'];
    public const gender = [KeyTable::name => 'user_gender'];
    public const lifeStory = [KeyTable::name => 'user_lifestory'];
    public const locked = [KeyTable::name => 'user_locked'];
    public const username = [KeyTable::name => 'user_username', 
            KeyTable::isUnique => true, 
            KeyTable::canIsNull => false];
    public const password = [KeyTable::name => 'user_password', 
            KeyTable::canIsNull => false];
    public const email = [KeyTable::name => 'user_email', 
            KeyTable::isUnique => true, 
            KeyTable::canIsNull => false];
    public const timeCreate = [KeyTable::name => 'user_timecreate'];
    public const photoId = [KeyTable::name => 'photo_id'];
    public const newPassword = [KeyTable::name => 'user_newpassword'];
    public const authenCode = [KeyTable::name => 'user_authen_code'];
    public const authenTimeCreate = [KeyTable::name => 'user_authen_timecreate'];
}
