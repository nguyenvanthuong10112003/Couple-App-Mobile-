<?php 
include_once(dirname(__FILE__).'/../authen/CheckURL.php');
class UserToken {
    public int $id;
    public string $username;
    public string $token;
    public ?string $alias;
    public ?string $urlAvatar;
    public ?string $fullName;
    public string $email;
}