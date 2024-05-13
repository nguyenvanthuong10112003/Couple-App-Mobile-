<?php 
include_once(dirname(__FILE__).'/../authen/CheckURL.php');
use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\SMTP;
use PHPMailer\PHPMailer\Exception;
require_once __DIR__ . '/../vendor/autoload.php';
class EmailService
{
    private const host = 'smtp.gmail.com';
    private const username = 'thuong0206066@huce.edu.vn';
    private const password = 'Thuong2003@';
    private const smtpAuth = true;
    private const port = 465;
    private const smtpSecure = PHPMailer::ENCRYPTION_SMTPS;
    private const smtpDebug = SMTP::DEBUG_OFF;
    private const fromEmail = 'nguyenvanthuong10112003@gmail.com';
    private const fromName = 'CoupleApp Hỗ trợ khách hàng';
    private const charset = 'UTF-8';
    private const language = 'vi';
    private const isHtml = true;
    public static function sendEmailAuthenCode($toEmail, $nameUser, $code) {
        $mail = new PHPMailer(true);
        try {
            $mail->setLanguage('vi');
            $mail->SMTPDebug = EmailService::smtpDebug;                      
            $mail->isSMTP();                                            
            $mail->Host       = EmailService::host;                     
            $mail->SMTPAuth   = EmailService::smtpAuth;                                   
            $mail->Username   = EmailService::username;                  
            $mail->Password   = EmailService::password;                            
            $mail->SMTPSecure = EmailService::smtpSecure;           
            $mail->Port       = EmailService::port;                                   
            $mail->setFrom(EmailService::fromEmail, EmailService::fromName);
            $mail->addAddress($toEmail, $nameUser);   
            $mail->CharSet = EmailService::charset;
            $mail->setLanguage(EmailService::language);
            $mail->isHTML(EmailService::isHtml);
            $mail->Subject = 'Xác thực tài khoản';
            $mail->Body    = 'Xin chào ' . $nameUser .  '. Mã xác thực của bạn là: ' . $code;
            $mail->send();
            return true;
        } catch (Exception $e) { echo $e->getMessage(); }
        return false;
    }
}