<?php 
include_once(dirname(__FILE__).'/../authen/CheckURL.php');
include_once(dirname(__FILE__).'/../entity/User.php');
include_once(dirname(__FILE__).'/../dto/UserDto.php');
include_once(dirname(__FILE__).'/../config/ConfigToken.php');
chdir(dirname(__DIR__));
require "vendor/autoload.php";
use \Firebase\JWT\JWT;
use \Firebase\JWT\Key;
class TokenService {
    public static function createToken(UserDto $user) {
        $issuedAt   = new DateTimeImmutable();
        $expire = $issuedAt->modify('+30 days')->getTimestamp(); 
        $data = [
            'iat'  => $issuedAt->getTimestamp(),              
            'nbf'  => $issuedAt->getTimestamp(),         
            'exp'  => $expire,                           
            'userName' => $user->username,
            'id' => $user->id                   
        ];
        return JWT::encode(
            $data,
            ConfigToken::secretKey,
            ConfigToken::option
        );
    }
    public static function decodeToken($jwt) {
        try {
            JWT::$leeway = 60; // $leeway in seconds
            return JWT::decode($jwt, ConfigToken::secretKey, array(ConfigToken::option));
        } catch (\Exception $e) {
            echo $e;
            return FALSE;
        }
    }
}