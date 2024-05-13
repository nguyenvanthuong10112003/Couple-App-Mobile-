<?php 
include_once(dirname(__FILE__).'/../authen/CheckURL.php');

class PHPMailer {
    private const CharSet = 'UTF-8';
    private const Host = 'smtp.gmail.com';
    private const SMTPSecure = 'tls';
    private const Port = 587;
    private const SMTPDebug = 1;
    private const SMTPAuth = true;
    private const Username = 'thuong0206066@huce.edu.vn';
    private const Password = 'Thuong2003@';
    private const Subject = 'subject';
    public static function sendEmail() {
        
    }
}