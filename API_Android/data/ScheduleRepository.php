<?php 
include_once(dirname(__FILE__).'/../authen/CheckURL.php');
include_once(dirname(__FILE__).'/../include/ConnectSQL.php');
include_once(dirname(__FILE__).'/IRepository.php');
include_once(dirname(__FILE__).'/../entity/Schedule.php');
include_once(dirname(__FILE__).'/../attr/ScheduleAttribute.php');
include_once(dirname(__FILE__).'/../attr/CoupleAttribute.php');

class ScheduleRepository 
{
    private string $sql;
    private readonly PDO $pdo;
    private readonly string $tableName;
    private readonly string $tableCoupleName;
    public function __construct() {
        $this->pdo = ConnectSQL::getConnection();
        $this->tableName = 'schedule';
        $this->tableCoupleName = 'couple';
    }
    public function getByPK($pk) {
        $this->sql = "SELECT * FROM $this->tableName WHERE " . ScheduleAttr::id[KeyTable::name] . " = $pk";
        try {
            $stmt = $this->pdo->prepare($this->sql);
            $stmt->execute();
            $result = $stmt->fetchAll(PDO::FETCH_ASSOC);
            if (count($result) == 0)
                return null;
            $row = $result[0];
            $schedule = new Schedule();
            $schedule->id = $row[ScheduleAttr::id[KeyTable::name]];
            $schedule->senderId = $row[ScheduleAttr::senderId[KeyTable::name]];
            $schedule->coupleId = $row[ScheduleAttr::coupleId[KeyTable::name]];
            $schedule->time = new DateTime($row[ScheduleAttr::time[KeyTable::name]]);
            $schedule->timeSend = new DateTime($row[ScheduleAttr::timeSend[KeyTable::name]]);
            $schedule->timeFeedBack = $row[ScheduleAttr::timeFeedBack[KeyTable::name]] 
                ? new DateTime($row[ScheduleAttr::timeFeedBack[KeyTable::name]]) : null;
            $schedule->isAccept = $row[ScheduleAttr::isAccept[KeyTable::name]];
            $schedule->title = $row[ScheduleAttr::title[KeyTable::name]];
            $schedule->content = $row[ScheduleAttr::content[KeyTable::name]];
            $schedule->isDeleted = $row[ScheduleAttr::deleted[KeyTable::name]];
            return $schedule;
        } catch(Exception $e) {}
        return null;
    }
    public function getAll(int $coupleId, int $currentUserId) {
        $this->sql = "SELECT * FROM $this->tableName LEFT JOIN $this->tableCoupleName ON $this->tableName." . 
            ScheduleAttr::coupleId[KeyTable::name] . " = $this->tableCoupleName." . CoupleAttr::id[KeyTable::name] . 
            " WHERE $this->tableCoupleName." . CoupleAttr::id[KeyTable::name] . " = $coupleId AND ( " . 
            ScheduleAttr::deleted[KeyTable::name] . " != 1 OR (" . ScheduleAttr::senderId[KeyTable::name] . " != $currentUserId AND " .
            ScheduleAttr::timeFeedBack[KeyTable::name] . " IS NOT NULL)) ORDER BY " . ScheduleAttr::time[KeyTable::name];
        try {
            $stmt = $this->pdo->prepare($this->sql);
            $stmt->execute();
            $result = $stmt->fetchAll(PDO::FETCH_ASSOC);
            if (count($result) == 0)
                return [];
            $kq = [];
            foreach ($result as $row) {
                $schedule = new Schedule();
                $schedule->id = $row[ScheduleAttr::id[KeyTable::name]];
                $schedule->senderId = $row[ScheduleAttr::senderId[KeyTable::name]];
                $schedule->coupleId = $row[ScheduleAttr::coupleId[KeyTable::name]];
                $schedule->time = new DateTime($row[ScheduleAttr::time[KeyTable::name]]);
                $schedule->timeSend = new DateTime($row[ScheduleAttr::timeSend[KeyTable::name]]);
                $schedule->timeFeedBack = $row[ScheduleAttr::timeFeedBack[KeyTable::name]] 
                    ? new DateTime($row[ScheduleAttr::timeFeedBack[KeyTable::name]]) : null;
                $schedule->isAccept = $row[ScheduleAttr::isAccept[KeyTable::name]];
                $schedule->title = $row[ScheduleAttr::title[KeyTable::name]];
                $schedule->content = $row[ScheduleAttr::content[KeyTable::name]];
                $schedule->isDeleted = $row[ScheduleAttr::deleted[KeyTable::name]];
                $kq[] = $schedule;
            }
            return $kq;
        } catch(Exception $e) {}
        return null;
    }
    public function create(Schedule $schedule) {
        $this->sql = "INSERT INTO $this->tableName (" . ScheduleAttr::coupleId[KeyTable::name] . "," . 
            ScheduleAttr::senderId[KeyTable::name] . "," . ScheduleAttr::time[KeyTable::name] . "," . 
            ScheduleAttr::timeSend[KeyTable::name] . "," . ScheduleAttr::title[KeyTable::name] . "," . 
            ScheduleAttr::content[KeyTable::name] . ") VALUES ($schedule->coupleId,
            $schedule->senderId,'" . $schedule->time->format('Y-m-d H:i:s') . "','" . 
            $schedule->timeSend->format('Y-m-d H:i:s') . "', :title, :content)";
        try {
            $stmt = $this->pdo->prepare($this->sql);
            $stmt->execute(['title' => $schedule->title, 'content' => $schedule->content]);
            if ($stmt->rowCount() == 0)
                return null;
            $schedule->id = $this->pdo->lastInsertId();
            return $schedule;
        } catch(Exception $e) {}
        return null;
    }
    public function delete(Schedule $schedule) {
        if ($schedule->timeFeedBack)
            $this->sql = "UPDATE $this->tableName SET " . ScheduleAttr::deleted[KeyTable::name] . " = 1 WHERE " . ScheduleAttr::id[KeyTable::name] . " = $schedule->id";
        else 
            $this->sql = "DELETE FROM $this->tableName WHERE " . ScheduleAttr::id[KeyTable::name] . " = $schedule->id";
        try {
            $stmt = $this->pdo->prepare($this->sql);
            $stmt->execute();
            return $stmt->rowCount() > 0;
        } catch(Exception $e) {}
        return false;
    }
    public function feedBack(Schedule $schedule) {
        $this->sql = "UPDATE $this->tableName SET " . ScheduleAttr::isAccept[KeyTable::name] . " = " . 
            ($schedule->isAccept ? 1 : 0) . ", " . ScheduleAttr::timeFeedBack[KeyTable::name] . " = '" . 
            $schedule->timeFeedBack->format('Y-m-d H:i:s') . "' WHERE " .  
            ScheduleAttr::id[KeyTable::name] . " = $schedule->id";
        try {
            $stmt = $this->pdo->prepare($this->sql);
            $stmt->execute();
            if ($stmt->rowCount() == 0)
                return null;
            return $schedule;
        } catch(Exception $e) {}
        return null;
    }
    public function findByTime(int $coupleId, DateTime $time) {
        $this->sql = "SELECT * FROM $this->tableName WHERE " . CoupleAttr::id[KeyTable::name] . " = $coupleId AND " . 
            ScheduleAttr::time[KeyTable::name] . " = '" . $time->format("Y-m-d H:i:s") . "'";
        try {
            $stmt = $this->pdo->prepare($this->sql);
            $stmt->execute();
            $result = $stmt->fetchAll(PDO::FETCH_ASSOC);
            if (count($result) == 0)
                return null;
            $row = $result[0];
            $schedule = new Schedule();
            $schedule->id = $row[ScheduleAttr::id[KeyTable::name]];
            $schedule->senderId = $row[ScheduleAttr::senderId[KeyTable::name]];
            $schedule->coupleId = $row[ScheduleAttr::coupleId[KeyTable::name]];
            $schedule->time = new DateTime($row[ScheduleAttr::time[KeyTable::name]]);
            $schedule->timeSend = new DateTime($row[ScheduleAttr::timeSend[KeyTable::name]]);
            $schedule->timeFeedBack = $row[ScheduleAttr::timeFeedBack[KeyTable::name]] 
                ? new DateTime($row[ScheduleAttr::timeFeedBack[KeyTable::name]]) : null;
            $schedule->isAccept = $row[ScheduleAttr::isAccept[KeyTable::name]];
            $schedule->title = $row[ScheduleAttr::title[KeyTable::name]];
            $schedule->content = $row[ScheduleAttr::content[KeyTable::name]];
            $schedule->isDeleted = $row[ScheduleAttr::deleted[KeyTable::name]];
            return $schedule;
        } catch(Exception $e) {}
        return null;
    }
}