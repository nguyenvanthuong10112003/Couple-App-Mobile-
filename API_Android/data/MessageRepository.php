<?php 
include_once(dirname(__FILE__).'/../authen/CheckURL.php');
include_once(dirname(__FILE__).'/../include/ConnectSQL.php');
include_once(dirname(__FILE__).'/../attr/CoupleAttribute.php');
include_once(dirname(__FILE__).'/../entity/Message.php');
include_once(dirname(__FILE__).'/../attr/MessageAttribute.php');
include_once(dirname(__FILE__).'/../dto/CoupleDto.php');
include_once(dirname(__FILE__).'/../dto/MessageDto.php');
class MessageRepository {
    private string $sql;
    private readonly PDO $pdo;
    private readonly string $tableName;
    private readonly string $tableCoupleName;
    public function __construct() {
        $this->tableName = 'message';
        $this->tableCoupleName = 'couple';
        $this->pdo = ConnectSQL::getConnection();
    }
    public function getAllMessages(CoupleDto $couple) {
        $this->sql = "SELECT * FROM $this->tableName LEFT JOIN $this->tableCoupleName ON $this->tableCoupleName." . 
            CoupleAttr::id[KeyTable::name] . " = $this->tableName." . MessageAttr::coupleId[KeyTable::name] . " WHERE 
            $this->tableName." . MessageAttr::coupleId[KeyTable::name] . " = $couple->id " . " ORDER BY " . 
            MessageAttr::timeSend[KeyTable::name];
        try {
            $stmt = $this->pdo->prepare($this->sql);
            $stmt->execute();
            $result = $stmt->fetchAll(PDO::FETCH_ASSOC);
            if (!$result || count($result) == 0)
                return [];
            $list = [];
            foreach ($result as $row) {
                $msg = new MessageDto();
                $msg->id = $row[MessageAttr::id[KeyTable::name]];
                $msg->content = $row[MessageAttr::content[KeyTable::name]];
                $msg->timeSend = new DateTime($row[MessageAttr::timeSend[KeyTable::name]]);
                $msg->timeRead = $row[MessageAttr::timeRead[KeyTable::name]] ? 
                    new DateTime($row[MessageAttr::timeRead[KeyTable::name]]) : null;
                $msg->senderId = $row[MessageAttr::senderId[KeyTable::name]];
                $list[] = $msg;
            }
            return $list;
        } catch(Exception $e) {}
        return null;
    }
    public function create(Message $message) {
        $this->sql = "INSERT INTO $this->tableName (" . MessageAttr::coupleId[KeyTable::name] . "," .
            MessageAttr::senderId[KeyTable::name] . "," . MessageAttr::timeSend[KeyTable::name] . "," . 
            MessageAttr::content[KeyTable::name] . ") VALUES ($message->coupleId, $message->senderId, '" . 
            $message->timeSend->format('Y-m-d H:i:s') . "', :content)";
        try {
            $stmt = $this->pdo->prepare($this->sql);
            $stmt->execute(['content' => $message->content]);
            $message->id = $this->pdo->lastInsertId();
            return $message;
        } catch(Exception $e) {}
        return null;
    }
    public function read(int $coupleId, int $reader) {
        $now = new DateTime();
        $this->sql = "UPDATE $this->tableName SET " . MessageAttr::timeRead[KeyTable::name] . 
            " = '" . $now->format('Y-m-d H:i:s') . "' WHERE " . MessageAttr::coupleId[KeyTable::name] .
            " = $coupleId AND " . MessageAttr::senderId[KeyTable::name] . " != $reader AND " . 
            MessageAttr::timeRead[KeyTable::name] . " IS NULL";
        try {
            $stmt = $this->pdo->prepare($this->sql);
            $stmt->execute();
        } catch(Exception $e) {}
    }
}