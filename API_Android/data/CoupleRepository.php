<?php 
include_once(dirname(__FILE__).'/../authen/CheckURL.php');
include_once(dirname(__FILE__).'/../include/ConnectSQL.php');
include_once(dirname(__FILE__).'/IRepository.php');
include_once(dirname(__FILE__).'/../entity/User.php');
include_once(dirname(__FILE__).'/../attr/UserAttribute.php');
include_once(dirname(__FILE__).'/../attr/PhotoAttribute.php');
include_once(dirname(__FILE__).'/../attr/DateInvitationAttribute.php');
include_once(dirname(__FILE__).'/../attr/CoupleAttribute.php');
include_once(dirname(__FILE__).'/../entity/Couple.php');
include_once(dirname(__FILE__).'/../data/UserRepository.php');
include_once(dirname(__FILE__).'/../dto/UserDto.php');
include_once(dirname(__FILE__).'/../dto/CoupleDto.php');
class CoupleRepository 
{
    private readonly string $tableName;
    private readonly string $tableDateInvitationName;
    private readonly string $tablePhotoName;
    private string $sql;
    private readonly PDO $pdo;
    public function __construct() {
        $this->pdo = ConnectSQL::getConnection();
        $this->tableName = "couple";
        $this->tableDateInvitationName = "date_invitation";
        $this->tablePhotoName = 'photo';
    }

    public function getByPk($pk) {
        $this->sql = "select * from $this->tableName where " . CoupleAttr::id[KeyTable::name] . " = :id";
        $stmt = $this->pdo->prepare($this->sql);
        $stmt->execute(['id' => (int)$pk]);
        $result = $stmt->fetchAll(PDO::FETCH_ASSOC);
        if (count($result) == 0)
            return null;
        $row = $result[0];
        $couple = new Couple();
        $couple->id = $row[CoupleAttr::id[KeyTable::name]];
        $couple->dateInvitaionId = $row[CoupleAttr::dateInvitationId[KeyTable::name]];
        $couple->photoId = $row[CoupleAttr::photoId[KeyTable::name]];
        $couple->timeStart = new DateTime($row[CoupleAttr::timeStart]);
        return $couple;
    }

    public function getDtoForUser(int $userId) {
        $this->sql = "SELECT * FROM $this->tableName LEFT JOIN $this->tableDateInvitationName ON $this->tableDateInvitationName." . 
            DateInvitationAttr::id[KeyTable::name] . " = $this->tableName." . CoupleAttr::dateInvitationId[KeyTable::name] . 
            " LEFT JOIN $this->tablePhotoName ON $this->tablePhotoName." . PhotoAttr::id[KeyTable::name] . 
            " = $this->tableName." . CoupleAttr::photoId[KeyTable::name] .
            " WHERE $this->tableDateInvitationName." . DateInvitationAttr::senderId[KeyTable::name] . " = $userId OR " . 
            " $this->tableDateInvitationName." . DateInvitationAttr::receiverId[KeyTable::name] . 
            " = $userId ORDER BY $this->tableDateInvitationName." . DateInvitationAttr::timeFeedBack[KeyTable::name] . " DESC";
        try {
            $stmt = $this->pdo->prepare($this->sql);
            $stmt->execute();
            $result = $stmt->fetchAll();
            if (count($result) == 0)
                return null;
            $couple = new CoupleDto();
            $couple->id = $result[0][CoupleAttr::id[KeyTable::name]];
            $couple->photoUrl = $result[0][PhotoAttr::url[KeyTable::name]];
            $couple->dateInvitationId = $result[0][CoupleAttr::dateInvitationId[KeyTable::name]];
            $couple->timeStart = $result[0][CoupleAttr::timeStart[KeyTable::name]] ? new DateTime($result[0][CoupleAttr::timeStart[KeyTable::name]]) : null;
            $enemyId = ($result[0][DateInvitationAttr::senderId[KeyTable::name]] == $userId ? $result[0][DateInvitationAttr::receiverId[KeyTable::name]] : 
                $result[0][DateInvitationAttr::senderId[KeyTable::name]]);
            $context = new UserRepository();
            $couple->mind = $context->getDtoByPK($userId);
            $couple->enemy = $context->getDtoByPK($enemyId);
            return $couple;
        } catch(Exception $e) {}
        return null;
    }

    public function getForUser(int $userId) {
        $this->sql = "SELECT * FROM $this->tableName LEFT JOIN $this->tableDateInvitationName ON $this->tableDateInvitationName." . 
            DateInvitationAttr::id[KeyTable::name] . " = $this->tableName." . CoupleAttr::dateInvitationId[KeyTable::name] . 
            " LEFT JOIN $this->tablePhotoName ON $this->tablePhotoName." . PhotoAttr::id[KeyTable::name] . 
            " = $this->tableName." . CoupleAttr::photoId[KeyTable::name] .
            " WHERE $this->tableDateInvitationName." . DateInvitationAttr::senderId[KeyTable::name] . " = $userId OR " . 
            " $this->tableDateInvitationName." . DateInvitationAttr::receiverId[KeyTable::name] . 
            " = $userId ORDER BY $this->tableDateInvitationName." . DateInvitationAttr::timeFeedBack[KeyTable::name] . " DESC";
        try {
            $stmt = $this->pdo->prepare($this->sql);
            $stmt->execute();
            $result = $stmt->fetchAll();
            if (count($result) == 0)
                return null;
            $row = $result[0];
            $couple = new Couple();
            $couple->id = $row[CoupleAttr::id[KeyTable::name]];
            $couple->dateInvitaionId = $row[CoupleAttr::dateInvitationId[KeyTable::name]];
            $couple->photoId = $row[CoupleAttr::photoId[KeyTable::name]];
            $couple->timeStart = new DateTime($row[CoupleAttr::timeStart[KeyTable::name]]);
            return $couple;
        } catch(Exception $e) {}
        return null;
    }

    public function changeBackground(int $id, int $photoId) {
        $this->sql = "UPDATE $this->tableName SET " . CoupleAttr::photoId[KeyTable::name] . " = $photoId WHERE " . CoupleAttr::id[KeyTable::name] . " = $id"; 
        try {
            $stmt = $this->pdo->prepare($this->sql);
            $stmt->execute();
            if ($stmt->rowCount() == 0)
                return false;
            return true;
        } catch(Exception $e) {}
        return false;
    }
}
