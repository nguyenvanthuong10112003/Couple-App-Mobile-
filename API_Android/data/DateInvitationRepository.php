<?php 
include_once(dirname(__FILE__).'/../authen/CheckURL.php');
include_once(dirname(__FILE__).'/../include/ConnectSQL.php');
include_once(dirname(__FILE__).'/../entity/DateInvitationAdd.php');
include_once(dirname(__FILE__).'/../entity/DateInvitation.php');
include_once(dirname(__FILE__).'/../attr/DateInvitationAttribute.php');
include_once(dirname(__FILE__).'/../define/KeyPhoto.php');
include_once(dirname(__FILE__).'/../attr/CoupleAttribute.php');
class DateInvitationRepository 
{
    private string $sql;
    private readonly PDO $pdo;
    private readonly string $tableName;
    private readonly string $tableNameUser;
    private readonly string $tableCoupleName;
    public function __construct() {
        $this->tableName = 'date_invitation';
        $this->tableNameUser = 'user';
        $this->tableCoupleName = 'couple';
        $this->pdo = ConnectSQL::getConnection();
    }

    public function create(DateInvitationAdd $dateInvitationAdd) {
        if ($this->pdo == null)
            return null;
        $this->sql = "INSERT INTO $this->tableName (" . DateInvitationAttr::senderId[KeyTable::name] . ',' . 
            DateInvitationAttr::receiverId[KeyTable::name] . ',' . DateInvitationAttr::timeSend[KeyTable::name] . 
            ') VALUES (:senderId, :receiverId, :timeSend)';
        try {
            $stmt = $this->pdo->prepare($this->sql);
            $stmt->execute(['senderId' => $dateInvitationAdd->senderId, 
                'receiverId' => $dateInvitationAdd->receiverId, 
                'timeSend' => $dateInvitationAdd->timeSend->format('Y-m-d H:i:s')
            ]);
            $id = $this->pdo->lastInsertId();
            if ($stmt->rowCount() > 0)
                return $id;
        } catch(Exception $e) {}
        return null;
    }

    public function getInvite(int $senderId, int $receiverId) {
        $this->sql = "SELECT * FROM $this->tableName WHERE ((" . 
            DateInvitationAttr::senderId[KeyTable::name] . ' =:senderId1 AND ' . DateInvitationAttr::receiverId[KeyTable::name] . ' =:receiverId1) OR (' . 
            DateInvitationAttr::senderId[KeyTable::name] . ' =:senderId2 AND ' . DateInvitationAttr::receiverId[KeyTable::name] . ' =:receiverId2)) AND ' .
            DateInvitationAttr::timeFeedBack[KeyTable::name] . ' IS NULL';
        try {
            $stmt = $this->pdo->prepare($this->sql);
            $stmt->execute(['senderId1' => $senderId, 'receiverId1' => $receiverId, 
                'senderId2' => $receiverId, 'receiverId2' => $senderId]);
            $result = $stmt->fetchAll();
            if (count($result) > 0)
                return $result;
        } catch(Exception $e) {}
        return null;
    }

    public function getInviteById(int $inviteId) {
        $this->sql = "SELECT * FROM $this->tableName WHERE " . DateInvitationAttr::id[KeyTable::name] . "=:id";
        try {
            $stmt = $this->pdo->prepare($this->sql);
            $stmt->execute(['id' => $inviteId]);
            $result = $stmt->fetchAll();
            if (count($result) == 0)
                return null;
            $row = $result[0];
            $dateInvitaion = new DateInvitation();
            $dateInvitaion->id = $row[DateInvitationAttr::id[KeyTable::name]];
            $dateInvitaion->isAccepted = $row[DateInvitationAttr::isAccepted[KeyTable::name]];
            $dateInvitaion->receiverId = $row[DateInvitationAttr::receiverId[KeyTable::name]];
            $dateInvitaion->senderId = $row[DateInvitationAttr::senderId[KeyTable::name]];
            $dateInvitaion->timeFeedBack = $row[DateInvitationAttr::timeFeedBack[KeyTable::name]] ? new DateTime($row[DateInvitationAttr::timeFeedBack[KeyTable::name]]) : null;
            $dateInvitaion->timeSend = new DateTime($row[DateInvitationAttr::timeSend[KeyTable::name]]);
            return $dateInvitaion;
        } catch(Exception $e) {}
        return null;
    }

    public function update(DateInvitation $dateInvitation) {
        if ($this->pdo == null)
            return false;
        $this->pdo->beginTransaction();
        try {
            $now = new DateTime();
            if ($dateInvitation->isAccepted) {
                $this->sql = "UPDATE $this->tableName SET " . DateInvitationAttr::isAccepted[KeyTable::name] . 
                    " = 0, " . DateInvitationAttr::timeFeedBack[KeyTable::name] . 
                    " = :timeFeedBack WHERE " . DateInvitationAttr::id[KeyTable::name] . " != " . $dateInvitation->id . 
                    " AND (" . DateInvitationAttr::receiverId[KeyTable::name] . " = " . $dateInvitation->senderId . 
                    " OR " . DateInvitationAttr::receiverId[KeyTable::name] . " = " . $dateInvitation->receiverId . ")";
                $stmt = $this->pdo->prepare($this->sql);
                $stmt->execute(['timeFeedBack' => $now->format('Y-m-d H:i:s')]);
                $this->sql = "DELETE FROM $this->tableName WHERE " . DateInvitationAttr::id[KeyTable::name] . " != " . $dateInvitation->id . 
                    " AND (" . DateInvitationAttr::senderId[KeyTable::name] . " = " . $dateInvitation->senderId . 
                    " OR " . DateInvitationAttr::senderId[KeyTable::name] . " = " . $dateInvitation->receiverId . ")";
                $stmt = $this->pdo->prepare($this->sql);
                $stmt->execute();
                $this->sql = "INSERT INTO $this->tableCoupleName (" . CoupleAttr::dateInvitationId[KeyTable::name] . "," . 
                    CoupleAttr::timeStart[KeyTable::name] . ") VALUES (:dateInvitationId, :timeStart)";
                $stmt = $this->pdo->prepare($this->sql);
                $stmt->execute(['dateInvitationId' => $dateInvitation->id, 'timeStart' => $now->format('Y-m-d H:i:s')]);
            }
            $this->sql = "UPDATE $this->tableName SET " . DateInvitationAttr::isAccepted[KeyTable::name] . 
                " = " . ($dateInvitation->isAccepted ? 1 : 0) . ", " . DateInvitationAttr::timeFeedBack[KeyTable::name] . 
                "=:timeFeedBack WHERE " . DateInvitationAttr::id[KeyTable::name] . "=:id";
            $stmt = $this->pdo->prepare($this->sql);
            $stmt->execute(['timeFeedBack' => $now->format('Y-m-d H:i:s'),
                            'id' => $dateInvitation->id]);
            $this->pdo->commit();
            return true;
        } catch(Exception $e) {
            $this->pdo->rollBack();
        }   
        return false;
    }

    public function delete(int $id) {
        if ($this->pdo == null)
            return false;
        $this->sql = "DELETE FROM $this->tableName WHERE " . DateInvitationAttr::id[KeyTable::name] . "=:id";
        try {
            $stmt = $this->pdo->prepare($this->sql);
            $stmt->execute(['id' => $id]);
            return $stmt->rowCount() > 0;
        } catch(Exception $e) {}
        return false;
    }
}
