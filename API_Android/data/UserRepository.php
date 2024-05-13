<?php 
include_once(dirname(__FILE__).'/../authen/CheckURL.php');
include_once(dirname(__FILE__).'/../include/ConnectSQL.php');
include_once(dirname(__FILE__).'/IRepository.php');
include_once(dirname(__FILE__).'/../entity/User.php');
include_once(dirname(__FILE__).'/../attr/UserAttribute.php');
include_once(dirname(__FILE__).'/../attr/PhotoAttribute.php');
include_once(dirname(__FILE__).'/../dto/UserDto.php');
include_once(dirname(__FILE__).'/../entity/UserAdd.php');
include_once(dirname(__FILE__).'/../attr/DateInvitationAttribute.php');
include_once(dirname(__FILE__).'/../entity/DateInvitation.php');
include_once(dirname(__FILE__).'/../attr/CoupleAttribute.php');

class UserRepository implements IRepository {
    private string $sql;
    private readonly PDO $pdo;
    private readonly string $tableName;
    private readonly string $tablePhotoName;
    private readonly string $tableDateInvitationName;
    private readonly string $tableCoupleName;
    public function __construct() {
        $this->pdo = ConnectSQL::getConnection();
        $this->tableName = 'user';
        $this->tablePhotoName = 'photo';
        $this->tableDateInvitationName = 'date_invitation';
        $this->tableCoupleName = 'couple';
    }
    public function __destruct() {

    }
    public function getAllDto() {
        if (!$this->pdo) 
            return [];
        $this->sql = "select * from $this->tableName left join $this->tablePhotoName ON $this->tableName." . 
            UserAttr::photoId[KeyTable::name] . " = $this->tablePhotoName." . PhotoAttr::id[KeyTable::name] . " left join ";
        $stmt = $this->pdo->prepare($this->sql);
        $stmt->execute();
        $result = $stmt->fetchAll(PDO::FETCH_ASSOC);
        if (count($result) == 0)
            return array();
        $users = array();
        foreach ($result as $row) {
            $user = new UserDto();
            $user->id = $row[UserAttr::id[KeyTable::name]];
            $user->fullName = $row[UserAttr::fullName[KeyTable::name]];
            $user->alias = $row[UserAttr::alias[KeyTable::name]];
            $user->dob = new DateTime($row[UserAttr::dob[KeyTable::name]]);
            $user->gender = $row[UserAttr::gender[KeyTable::name]];
            $user->lifeStory = $row[UserAttr::lifeStory[KeyTable::name]];
            $user->username = $row[UserAttr::username[KeyTable::name]];
            $user->email = $row[UserAttr::email[KeyTable::name]];
            $user->timeCreate = new DateTime($row[UserAttr::timeCreate[KeyTable::name]]);
            $user->urlAvatar = $row[PhotoAttr::url[KeyTable::name]];
            $users[] = $user;
        }
        return $users;
    }
    public function getAllDtoForDateInvitation(int $userId) {
        if (!$this->pdo) 
            return [];
        $sql1 = "SELECT DISTINCT $this->tableName. " . UserAttr::id[KeyTable::name] . " FROM $this->tableName INNER JOIN $this->tableDateInvitationName ON 
            $this->tableDateInvitationName." . DateInvitationAttr::senderId[KeyTable::name] . " = $this->tableName." . UserAttr::id[KeyTable::name] . " OR 
            $this->tableDateInvitationName." . DateInvitationAttr::receiverId[KeyTable::name] . " = $this->tableName." . UserAttr::id[KeyTable::name] . 
            " INNER JOIN $this->tableCoupleName ON $this->tableCoupleName." . CoupleAttr::dateInvitationId[KeyTable::name] . " = $this->tableDateInvitationName." .  
            DateInvitationAttr::id[KeyTable::name] . " WHERE $this->tableDateInvitationName." . 
            DateInvitationAttr::isAccepted[KeyTable::name] . " = 1";
        $this->sql = "SELECT * FROM $this->tableName LEFT JOIN $this->tablePhotoName ON $this->tableName." . 
            UserAttr::photoId[KeyTable::name] . " = $this->tablePhotoName." . PhotoAttr::id[KeyTable::name] . " left join 
            (SELECT * FROM $this->tableDateInvitationName WHERE ($this->tableDateInvitationName." . DateInvitationAttr::senderId[KeyTable::name] . " = $userId OR " . 
            "$this->tableDateInvitationName." . DateInvitationAttr::receiverId[KeyTable::name] . " = $userId) AND $this->tableDateInvitationName." . 
            DateInvitationAttr::timeFeedBack[KeyTable::name] . " IS NULL ORDER BY " . DateInvitationAttr::timeSend[KeyTable::name] . 
            " DESC) AS $this->tableDateInvitationName ON ($this->tableDateInvitationName." . DateInvitationAttr::senderId[KeyTable::name] . " = $this->tableName." . 
            UserAttr::id[KeyTable::name] . " OR $this->tableDateInvitationName." . DateInvitationAttr::receiverId[KeyTable::name] . " = $this->tableName." . 
            UserAttr::id[KeyTable::name] . ") WHERE $this->tableName." . UserAttr::id[KeyTable::name] . " != $userId AND $this->tableName." . 
            UserAttr::id[KeyTable::name] . " NOT IN ($sql1) ORDER BY $this->tableName. " . UserAttr::id[KeyTable::name];
        $stmt = $this->pdo->prepare($this->sql);
        $stmt->execute();
        $result = $stmt->fetchAll(PDO::FETCH_ASSOC);
        if (count($result) == 0)
            return array();
        $users = [];
        for ($i = 0; $i < count($result); $i++) {
            $user = new UserDto();
            $user->id = $result[$i][UserAttr::id[KeyTable::name]];
            $user->fullName = $result[$i][UserAttr::fullName[KeyTable::name]];
            $user->alias = $result[$i][UserAttr::alias[KeyTable::name]];
            $user->dob = new DateTime($result[$i][UserAttr::dob[KeyTable::name]]);
            $user->gender = $result[$i][UserAttr::gender[KeyTable::name]];
            $user->lifeStory = $result[$i][UserAttr::lifeStory[KeyTable::name]];
            $user->username = $result[$i][UserAttr::username[KeyTable::name]];
            $user->email = $result[$i][UserAttr::email[KeyTable::name]];
            $user->timeCreate = new DateTime($result[$i][UserAttr::timeCreate[KeyTable::name]]);
            $user->urlAvatar = $result[$i][PhotoAttr::url[KeyTable::name]];
            if ($result[$i][DateInvitationAttr::id[KeyTable::name]]) {
                $dateInvitation = new DateInvitation();
                $dateInvitation->id = $result[$i][DateInvitationAttr::id[KeyTable::name]];
                $dateInvitation->isAccepted = $result[$i][DateInvitationAttr::isAccepted[KeyTable::name]];
                $dateInvitation->receiverId = $result[$i][DateInvitationAttr::receiverId[KeyTable::name]];
                $dateInvitation->senderId = $result[$i][DateInvitationAttr::senderId[KeyTable::name]];
                $dateInvitation->timeFeedBack = $result[$i][DateInvitationAttr::timeFeedBack[KeyTable::name]] 
                    ? new DateTime($result[$i][DateInvitationAttr::timeFeedBack[KeyTable::name]]) : null;
                $dateInvitation->timeSend = $result[$i][DateInvitationAttr::timeSend[KeyTable::name]] 
                    ? new DateTime($result[$i][DateInvitationAttr::timeSend[KeyTable::name]]) : null;
                $user->invite = $dateInvitation;
                while(($i + 1) < count($result) && $result[$i + 1][UserAttr::id[KeyTable::name]] == $user->id)
                    $i++;
            }
            $users[] = $user;
        }
        return $users;
    }
    public function getAll() {
        return null;
    }
    public function getDtoByUsername($username) {
        if (!$this->pdo) 
            return null;
        $this->sql = "select * from $this->tableName left join $this->tablePhotoName ON $this->tableName." . 
            UserAttr::photoId[KeyTable::name] . " = $this->tablePhotoName." . PhotoAttr::id[KeyTable::name] . 
            " where " . UserAttr::username[KeyTable::name] . " =:username";
        $stmt = $this->pdo->prepare($this->sql);
        $stmt->execute(["username" => $username]);
        $result = $stmt->fetchAll(PDO::FETCH_ASSOC);
        if (count($result) == 0)
            return null;
        $row = $result[0];
        $user = new UserDto();
        $user->id = $row[UserAttr::id[KeyTable::name]];
        $user->fullName = $row[UserAttr::fullName[KeyTable::name]];
        $user->alias = $row[UserAttr::alias[KeyTable::name]];
        $user->dob = new DateTime($row[UserAttr::dob[KeyTable::name]]);
        $user->gender = $row[UserAttr::gender[KeyTable::name]];
        $user->lifeStory = $row[UserAttr::lifeStory[KeyTable::name]];
        $user->username = $row[UserAttr::username[KeyTable::name]];
        $user->email = $row[UserAttr::email[KeyTable::name]];
        $user->timeCreate = new DateTime($row[UserAttr::timeCreate[KeyTable::name]]);
        $user->urlAvatar = $row[PhotoAttr::url[KeyTable::name]];
        return $user;
    }
    public function getDtoByPK($pk) {
        if (!$this->pdo) 
            return null;
        $this->sql = "select * from $this->tableName left join $this->tablePhotoName ON $this->tableName." . 
            UserAttr::photoId[KeyTable::name] . " = $this->tablePhotoName." . PhotoAttr::id[KeyTable::name] . 
            " where " . UserAttr::id[KeyTable::name] . " =:id";
        $stmt = $this->pdo->prepare($this->sql);
        $stmt->execute(["id" => $pk]);
        $result = $stmt->fetchAll(PDO::FETCH_ASSOC);
        if (count($result) == 0)
            return null;
        $row = $result[0];
        $user = new UserDto();
        $user->id = $row[UserAttr::id[KeyTable::name]];
        $user->fullName = $row[UserAttr::fullName[KeyTable::name]];
        $user->alias = $row[UserAttr::alias[KeyTable::name]];
        $user->dob = new DateTime($row[UserAttr::dob[KeyTable::name]]);
        $user->gender = $row[UserAttr::gender[KeyTable::name]];
        $user->lifeStory = $row[UserAttr::lifeStory[KeyTable::name]];
        $user->username = $row[UserAttr::username[KeyTable::name]];
        $user->email = $row[UserAttr::email[KeyTable::name]];
        $user->timeCreate = new DateTime($row[UserAttr::timeCreate[KeyTable::name]]);
        $user->urlAvatar = $row[PhotoAttr::url[KeyTable::name]];
        return $user;
    }
    public function getByUsername($username) {
        if (!$this->pdo) 
            return null;
        $this->sql = "select * from $this->tableName where " . UserAttr::username[KeyTable::name] . " = :username";
        $stmt = $this->pdo->prepare($this->sql);
        $stmt->execute(['username' => $username]);
        $result = $stmt->fetchAll(PDO::FETCH_ASSOC);
        if (count($result) == 0)
            return null;
        $row = $result[0];
        $user = new User();
        $user->id = $row[UserAttr::id[KeyTable::name]];
        $user->fullName = $row[UserAttr::fullName[KeyTable::name]];
        $user->alias = $row[UserAttr::alias[KeyTable::name]];
        $user->dob = $row[UserAttr::dob[KeyTable::name]] ? new DateTime($row[UserAttr::dob[KeyTable::name]]) : null;
        $user->gender = $row[UserAttr::gender[KeyTable::name]];
        $user->lifeStory = $row[UserAttr::lifeStory[KeyTable::name]];
        $user->locked = $row[UserAttr::locked[KeyTable::name]];
        $user->username = $row[UserAttr::username[KeyTable::name]];
        $user->password = $row[UserAttr::password[KeyTable::name]];
        $user->email = $row[UserAttr::email[KeyTable::name]];
        $user->timeCreate = $row[UserAttr::timeCreate[KeyTable::name]] ? new DateTime($row[UserAttr::timeCreate[KeyTable::name]]) : null;
        $user->photoId = $row[UserAttr::photoId[KeyTable::name]];
        $user->authenCode = $row[UserAttr::authenCode[KeyTable::name]];
        $user->authenTimeCreate = $row[UserAttr::authenTimeCreate[KeyTable::name]] ? new DateTime($row[UserAttr::authenTimeCreate[KeyTable::name]]) : null;
        return $user;
    }
    public function getByPK($pk) {
        if (!$this->pdo) 
            return null;
        $this->sql = "select * from $this->tableName where " . UserAttr::id[KeyTable::name] . " = :id";
        $stmt = $this->pdo->prepare($this->sql);
        $stmt->execute(['id' => (int)$pk]);
        $result = $stmt->fetchAll(PDO::FETCH_ASSOC);
        if (count($result) == 0)
            return null;
        $row = $result[0];
        $user = new User();
        $user->id = $row[UserAttr::id[KeyTable::name]];
        $user->fullName = $row[UserAttr::fullName[KeyTable::name]];
        $user->alias = $row[UserAttr::alias[KeyTable::name]];
        $user->dob = $row[UserAttr::dob[KeyTable::name]] ? new DateTime($row[UserAttr::dob[KeyTable::name]]) : null;
        $user->gender = $row[UserAttr::gender[KeyTable::name]];
        $user->lifeStory = $row[UserAttr::lifeStory[KeyTable::name]];
        $user->locked = $row[UserAttr::locked[KeyTable::name]];
        $user->username = $row[UserAttr::username[KeyTable::name]];
        $user->password = $row[UserAttr::password[KeyTable::name]];
        $user->email = $row[UserAttr::email[KeyTable::name]];
        $user->timeCreate = $row[UserAttr::timeCreate[KeyTable::name]] ? new DateTime($row[UserAttr::timeCreate[KeyTable::name]]) : null;
        $user->photoId = $row[UserAttr::photoId[KeyTable::name]];
        $user->authenCode = $row[UserAttr::authenCode[KeyTable::name]];
        $user->authenTimeCreate = $row[UserAttr::authenTimeCreate[KeyTable::name]] ? new DateTime($row[UserAttr::authenTimeCreate[KeyTable::name]]) : null;
        return $user;
    }
    public function getByEmail($email) {
        if (!$this->pdo) 
            return null;
        $this->sql = "select * from $this->tableName where " . UserAttr::email[KeyTable::name] . " = :email";
        $stmt = $this->pdo->prepare($this->sql);
        $stmt->execute(['email' => $email]);
        $result = $stmt->fetchAll(PDO::FETCH_ASSOC);
        if (count($result) == 0)
            return null;
        $row = $result[0];
        $user = new User();
        $user->id = $row[UserAttr::id[KeyTable::name]];
        $user->fullName = $row[UserAttr::fullName[KeyTable::name]];
        $user->alias = $row[UserAttr::alias[KeyTable::name]];
        $user->dob = $row[UserAttr::dob[KeyTable::name]] ? new DateTime($row[UserAttr::dob[KeyTable::name]]) : null;
        $user->gender = $row[UserAttr::gender[KeyTable::name]];
        $user->lifeStory = $row[UserAttr::lifeStory[KeyTable::name]];
        $user->locked = $row[UserAttr::locked[KeyTable::name]];
        $user->username = $row[UserAttr::username[KeyTable::name]];
        $user->password = $row[UserAttr::password[KeyTable::name]];
        $user->email = $row[UserAttr::email[KeyTable::name]];
        $user->timeCreate = $row[UserAttr::timeCreate[KeyTable::name]] ? new DateTime($row[UserAttr::timeCreate[KeyTable::name]]) : null;
        $user->photoId = $row[UserAttr::photoId[KeyTable::name]];
        $user->authenCode = $row[UserAttr::authenCode[KeyTable::name]];
        $user->authenTimeCreate = $row[UserAttr::authenTimeCreate[KeyTable::name]] ? new DateTime($row[UserAttr::authenTimeCreate[KeyTable::name]]) : null;
        return $user;
    }
    public function getByUnique($dic) {
        if (!$this->pdo) 
            throw new Exception("error connect sql");
        $this->sql = "SELECT * FROM $this->tableName WHERE " . UserAttr::username[KeyTable::name] . 
            " = :username OR " . UserAttr::email[KeyTable::name] . " = :email";
        $stmt = $this->pdo->prepare($this->sql);
        $stmt->execute(['username' => $dic['username'], 'email' => $dic['email']]);
        $result = $stmt->fetchAll(PDO::FETCH_ASSOC);
        $values = [];
        if (count($result) == 0) 
            return $values;
        foreach ($result as $row) {
            $user = new User();
            $user = new User();
            $user->id = $row[UserAttr::id[KeyTable::name]];
            $user->fullName = $row[UserAttr::fullName[KeyTable::name]];
            $user->alias = $row[UserAttr::alias[KeyTable::name]];
            $user->dob = $row[UserAttr::dob[KeyTable::name]] ? new DateTime($row[UserAttr::dob[KeyTable::name]]) : null;
            $user->gender = $row[UserAttr::gender[KeyTable::name]];
            $user->lifeStory = $row[UserAttr::lifeStory[KeyTable::name]];
            $user->locked = $row[UserAttr::locked[KeyTable::name]];
            $user->username = $row[UserAttr::username[KeyTable::name]];
            $user->password = $row[UserAttr::password[KeyTable::name]];
            $user->email = $row[UserAttr::email[KeyTable::name]];
            $user->timeCreate = $row[UserAttr::timeCreate[KeyTable::name]] ? new DateTime($row[UserAttr::timeCreate[KeyTable::name]]) : null;
            $user->photoId = $row[UserAttr::photoId[KeyTable::name]];
            $user->authenCode = $row[UserAttr::authenCode[KeyTable::name]];
            $user->authenTimeCreate = $row[UserAttr::authenTimeCreate[KeyTable::name]] ? new DateTime($row[UserAttr::authenTimeCreate[KeyTable::name]]) : null;
            $values = [...$values, $user];
        }
        return $values;
    }
    public function create($userAdd): bool {
        if (!$this->pdo)
            return false;
        $sql = "INSERT INTO $this->tableName(" . 
        join(",", [
            UserAttr::username[KeyTable::name], UserAttr::password[KeyTable::name], 
            UserAttr::email[KeyTable::name], UserAttr::timeCreate[KeyTable::name]
        ]) .
        ") VALUES (:username, :password, :email, :timeCreate)";
        $params = [
            'username' => $userAdd->username,
            'password' => $userAdd->password,
            'email' => $userAdd->email,
            'timeCreate' => $userAdd->timeCreate->format('Y-m-d H:i:s') 
        ];
        try {
            $stmt = $this->pdo->prepare($sql);
            $stmt->execute($params);
            return $stmt->rowCount() > 0;
        } catch(Exception $e) {} 
        return false;
    }
    public function update($user): bool {
        if (!$this->pdo)
            return false;
        $sql = "UPDATE $this->tableName SET " . 
            UserAttr::fullName[KeyTable::name] .  "=:fullName," .
            UserAttr::alias[KeyTable::name] . "=:alias," . 
            UserAttr::dob[KeyTable::name] . "=:dob," . 
            UserAttr::gender[KeyTable::name] . "=" . ($user->gender ? 1 : 0) . "," . 
            UserAttr::lifeStory[KeyTable::name] . "=:lifeStory," . 
            UserAttr::email[KeyTable::name] . "=:email," . 
            UserAttr::photoId[KeyTable::name] . "=:photoId," .
            UserAttr::password[KeyTable::name] . "=:password," . 
            UserAttr::authenCode[KeyTable::name] . "=:authenCode," . 
            UserAttr::authenTimeCreate[KeyTable::name] . "=:authenTimeCreate WHERE " . 
            UserAttr::username[KeyTable::name] . "=:username";
        $params = [
            'fullName' => $user->fullName, 
            'alias' => $user->alias,
            'dob' => $user->dob->format('Y-m-d'), 
            'lifeStory' => $user->lifeStory, 
            'email' => $user->email,
            'photoId' => $user->photoId,
            'username' => $user->username,
            'password' => $user->password,
            'authenCode' => $user->authenCode,
            'authenTimeCreate' => $user->authenTimeCreate ? $user->authenTimeCreate->format('Y-m-d H:i:s') : null
        ];
        try {
            $stmt = $this->pdo->prepare($sql);
            $stmt->execute($params);
            return true;
        } catch(Exception $e) {}
        return false;
    }
    public function findDto($data = ['key' => 'value']) {
        if (gettype(['key' => 'value']) !== gettype($data))
            return null;
        $this->sql = "SELECT * FROM $this->tableName";
        $where = '';
        $params = [];
        if (isset($data['name'])) { 
            $where .= UserAttr::fullName[KeyTable::name] . ' LIKE :name ';
            $params['name'] = $data['name'] . '%';
        }
        if (isset($data['email'])) { 
            $where .= ($where ? ' AND ' : '') . UserAttr::email[KeyTable::name] . ' LIKE :email ';
            $params['email'] = $data['email'] . '%';    
        }
        if (isset($data['username'])) {  
            $where .= ($where ? ' AND ' : '') . UserAttr::username[KeyTable::name] . ' LIKE :username ';
            $params['username'] = $data['username'] . '%';
        }     
        if ($where) {
            $this->sql .= ' WHERE ' . $where;
        }
        $stmt = $this->pdo->prepare($this->sql);
        $stmt->execute($params);
        $result = $stmt->fetchAll(PDO::FETCH_ASSOC);
        $values = [];
        foreach ($result as $row) {
            $userDto = new UserDto();
            $userDto->id = $row[UserAttr::id[KeyTable::name]];
            $userDto->fullName = $row[UserAttr::fullName[KeyTable::name]];
            $userDto->alias = $row[UserAttr::alias[KeyTable::name]];
            $userDto->dob = $row[UserAttr::dob[KeyTable::name]] ? new DateTime($row[UserAttr::dob[KeyTable::name]]) : null;
            $userDto->gender = $row[UserAttr::gender[KeyTable::name]];
            $userDto->lifeStory = $row[UserAttr::lifeStory[KeyTable::name]];
            $userDto->username = $row[UserAttr::username[KeyTable::name]];
            $userDto->email = $row[UserAttr::email[KeyTable::name]];
            $userDto->timeCreate = $row[UserAttr::timeCreate[KeyTable::name]] ? new DateTime($row[UserAttr::timeCreate[KeyTable::name]]) : null;
            $userDto->urlAvatar = $row[PhotoAttr::url[KeyTable::name]];
            $values = [...$values, $userDto];
        }
        return $values;
    }   
}