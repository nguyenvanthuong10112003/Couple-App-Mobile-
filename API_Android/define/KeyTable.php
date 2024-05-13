<?php 
include_once(dirname(__FILE__).'/../authen/CheckURL.php');
class KeyTable {
    public const name = 'name';
    public const isUnique = 'IsUniQue';
    public const canIsNull = 'CanIsNull';
    public const isPrimary = 'IsPrimary';
    public const isIdentity = 'IsIdentity';
}