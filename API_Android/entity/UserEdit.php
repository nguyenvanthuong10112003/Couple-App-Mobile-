<?php

use Cloudinary\Asset\File;

include_once(dirname(__FILE__).'/../authen/CheckURL.php');
class UserEdit {
    public string $username;
    public string $fullName;
    public ?string $alias;
    public DateTime $dob;
    public bool $gender;
    public ?string $lifeStory;
    public string $email;
    public string $photoId;
    public function __construct() {

    }
}