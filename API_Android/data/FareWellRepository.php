<?php 
include_once(dirname(__FILE__).'/../authen/CheckURL.php');
include_once(dirname(__FILE__).'/../include/ConnectSQL.php');
include_once(dirname(__FILE__).'/../attr/CoupleAttribute.php');
include_once(dirname(__FILE__).'/../entity/FarewellRequest.php');
include_once(dirname(__FILE__).'/../attr/FarewellRequestAttribute.php');
class FarewellRequestRepository 
{
    private readonly string $tableName;
    private readonly string $tableCoupleName;
    private string $sql;
    private readonly PDO $pdo;
    public function __construct() {
        $this->tableName = 'farewell_request';
        $this->tableCoupleName = 'couple';
        $this->pdo = ConnectSQL::getConnection();
    }
    public function get($coupleId) {
        $this->sql = "SELECT * FROM $this->tableName LEFT JOIN $this->tableCoupleName ON $this->tableName." . 
            FarewellRequestAttr::coupleId[KeyTable::name] . " = $this->tableCoupleName." . CoupleAttr::id[KeyTable::name] . 
            " WHERE " . FarewellRequestAttr::timeFeedBack[KeyTable::name] . " IS NULL AND $this->tableCoupleName." . 
            CoupleAttr::id[KeyTable::name] . " = $coupleId";
        try {
            $stmt = $this->pdo->prepare($this->sql);
            $stmt->execute();
            $result = $stmt->fetchAll(PDO::FETCH_ASSOC);
            if (count($result) == 0)
                return null;
            $row = $result[0];
            $farewell = new FarewellRequest();
            $farewell->id = $row[FarewellRequestAttr::id[KeyTable::name]];
            $farewell->coupleId = $row[FarewellRequestAttr::coupleId[KeyTable::name]];
            $farewell->senderId = $row[FarewellRequestAttr::senderId[KeyTable::name]];
            $farewell->isAccept = $row[FarewellRequestAttr::isAccept[KeyTable::name]];
            $farewell->timeSend = new DateTime($row[FarewellRequestAttr::timeSend[KeyTable::name]]);
            $farewell->timeFeedBack = $row[FarewellRequestAttr::timeFeedBack[KeyTable::name]] ? 
                new DateTime($row[FarewellRequestAttr::timeFeedBack[KeyTable::name]]) : null;
            return $farewell;
        } catch(Exception $e) {echo $e->getMessage();}
        return null;
    }
    public function create(FarewellRequest $data) {
        $this->sql = "INSERT INTO $this->tableName (" . FarewellRequestAttr::coupleId[KeyTable::name] . "," . 
            FarewellRequestAttr::senderId[KeyTable::name] . "," . FarewellRequestAttr::timeSend[KeyTable::name] . 
            ") VALUES ($data->coupleId, $data->senderId,'" . $data->timeSend->format('Y-m-d H:i:s') . "')"; 
        try {
            $stmt = $this->pdo->prepare($this->sql);
            $stmt->execute();
            return $stmt->rowCount() > 0;
        } catch(Exception $e) {}
        return false;
    }
    public function update(FarewellRequest $data) {
        $now = new DateTime();
        $this->pdo->beginTransaction();
        try {
            if ($data->isAccept) {
                $this->sql = "DELETE FROM $this->tableName WHERE " . FarewellRequestAttr::coupleId[KeyTable::name] . " = $data->coupleId";
                $stmt = $this->pdo->prepare($this->sql);
                $stmt->execute();
                $this->sql = "DELETE FROM $this->tableCoupleName WHERE " . CoupleAttr::id[KeyTable::name] . " = $data->coupleId";
            } else {
                $this->sql = "UPDATE $this->tableName SET " . FarewellRequestAttr::isAccept[KeyTable::name] . " = 0, " . 
                    FarewellRequestAttr::timeFeedBack[KeyTable::name] . " = '" . $now->format('Y-m-d H:i:s') . "' WHERE " . 
                    FarewellRequestAttr::id[KeyTable::name] . " = " . $data->id;
            }
            $stmt = $this->pdo->prepare($this->sql);
            $stmt->execute();
            $this->pdo->commit();
            return $stmt->rowCount() > 0;
        } catch(Exception $e) {$this->pdo->rollBack();}
        return false;
    }
}