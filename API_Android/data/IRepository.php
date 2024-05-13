<?php
include_once(dirname(__FILE__).'/../authen/CheckURL.php');
interface IRepository {
    function getAllDto();
    function getAll();
    function getByPK($pk);
    function getByUnique($listData);
    function create($data);
    function update($data): bool;
    function findDto($data);
}