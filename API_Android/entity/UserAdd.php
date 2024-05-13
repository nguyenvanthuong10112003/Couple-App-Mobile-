<?php 
include_once(dirname(__FILE__).'/../authen/CheckURL.php');
class UserAdd
{
    public $username;
    public $password;
    public $timeCreate;
    public $email;
    public function __construct() {
        $this->timeCreate = new DateTime();
    }
}