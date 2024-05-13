<?php 
include_once(dirname(__FILE__).'/../authen/CheckURL.php');
include_once(dirname(__FILE__).'/../config/ConfigDb.php');
class ConnectSQL {
    public static function getConnection() {
        $pdo = new PDO('mysql:host=' . ConfigDB::serverName . ';dbname=' . 
            ConfigDB::databaseName,ConfigDB::username,ConfigDB::password);
        $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        return $pdo;
    }
}