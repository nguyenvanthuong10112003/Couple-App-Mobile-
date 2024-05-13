<?php 
include_once(dirname(__FILE__).'/../authen/CheckURL.php');
class StringHelper {
    public static function isValUsername(string $str) {
        foreach (str_split($str, 1) as $c) {
            if (!(($c >= 'A' && $c <= 'Z') || 
                ($c >= 'a' && $c <= 'z') || 
                ($c >= '0' && $c <= '9')))
                return false;
        }
        return strlen($str) > 0;
    }
    public static function isValEmail(string $email) {
        return filter_var($email, FILTER_VALIDATE_EMAIL);
    }
    public static function isValPassword(string $password) {
        return strlen($password) >= 6;
    }
    public static function isValFullName(string $fullname) {
        return strlen($fullname) > 0;
    }
    public static function isValAuthenCode(string $code): bool {
        if (strlen($code) < 6)
            return false;
        foreach (str_split($code) as $c)
            if ($c > '9' || $c < '0')
                return false;
        return true;
    }
}