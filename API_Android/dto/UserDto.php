<?php 
include_once(dirname(__FILE__).'/../authen/CheckURL.php');
include_once(dirname(__FILE__).'/../entity/DateInvitation.php');
class UserDto {
    public int $id;
    public ?string $fullName;
    public ?string $alias;
    public ?datetime $dob;
    public ?bool $gender;
    public ?string $lifeStory;
    public string $username;
    public string $email;
    public datetime $timeCreate;
    public ?string $urlAvatar;
    public DateInvitation $invite; 
}