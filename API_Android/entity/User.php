<?php 
include_once(dirname(__FILE__).'/../authen/CheckURL.php');
class User {
    public int $id;
    public ?string $fullName;
    public ?string $alias;
    public ?DateTime $dob;
    public ?bool $gender;
    public ?string $lifeStory;
    public ?bool $locked;
    public string $username;
    public string $password;
    public string $email;
    public DateTime $timeCreate;
    public ?int $photoId;
    public ?string $authenCode;
    public ?DateTime $authenTimeCreate;
}