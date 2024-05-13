<?php 
include_once(dirname(__FILE__).'/../authen/CheckURL.php');
include_once(dirname(__FILE__).'/../include/ConnectSQL.php');
include_once(dirname(__FILE__).'/IRepository.php');
include_once(dirname(__FILE__).'/../entity/Photo.php');
include_once(dirname(__FILE__).'/../attr/PhotoAttribute.php');

class PhotoRepository {
    private string $sql;
    private readonly PDO $pdo;
    private readonly string $tableName;
    public function __construct() {
        $this->pdo = ConnectSQL::getConnection();
        $this->tableName = 'photo';
    }
    public function getByPublicId($publicId) {
        if (!$this->pdo) 
            return null;
        $this->sql = "select * from $this->tableName where " . PhotoAttr::publicId[KeyTable::name] . " =:publicId";
        $stmt = $this->pdo->prepare($this->sql);
        $stmt->execute(["publicId" => $publicId]);
        $result = $stmt->fetchAll(PDO::FETCH_ASSOC);
        if (count($result) == 0)
            return null;
        $row = $result[0];
        $photo = new Photo();
        $photo->photoId = $row[PhotoAttr::id[KeyTable::name]];
        $photo->photoPublicId = $row[PhotoAttr::publicId[KeyTable::name]];
        $photo->photoUrl = $row[PhotoAttr::url[KeyTable::name]];
        return $photo;
    }
    public function getById($id) {
        if (!$this->pdo) 
            return null;
        $this->sql = "select * from $this->tableName where " . PhotoAttr::id[KeyTable::name] . " =:id";
        $stmt = $this->pdo->prepare($this->sql);
        $stmt->execute(["id" => $id]);
        $result = $stmt->fetchAll(PDO::FETCH_ASSOC);
        if (count($result) == 0)
            return null;
        $row = $result[0];
        $photo = new Photo();
        $photo->photoId = $row[PhotoAttr::id[KeyTable::name]];
        $photo->photoPublicId = $row[PhotoAttr::publicId[KeyTable::name]];
        $photo->photoUrl = $row[PhotoAttr::url[KeyTable::name]];
        return $photo;
    }
    public function edit($photoId, $newUrl, $newPublicId): bool {
        $photo = $this->getById($photoId);
        if ($photo == null)
            return false;
        $this->sql = "update $this->tableName set " . PhotoAttr::publicId[KeyTable::name] . 
            " =:publicId, " . PhotoAttr::url[KeyTable::name] . 
            " =:url where " . PhotoAttr::id[KeyTable::name] . "=:id";
        $stmt = $this->pdo->prepare($this->sql);
        $stmt->execute(["publicId" => $newPublicId, "url" => $newUrl, "id" => $photoId]);
        return $stmt->rowCount();
    }
    public function create($newUrl, $newPublicId): bool {
        $photo = $this->getByPublicId($newPublicId);
        if ($photo != null)
            return false;
        $this->sql = "insert into $this->tableName (" . PhotoAttr::publicId[KeyTable::name] . 
            ", " . PhotoAttr::url[KeyTable::name] . ") values (:publicId, :url)";
        $stmt = $this->pdo->prepare($this->sql);
        $stmt->execute(["publicId" => $newPublicId, "url" => $newUrl]);
        return $stmt->rowCount();
    }
    public function delete($photoId): bool {
        $photo = $this->getById($photoId);
        if ($photo == null)
            return true;
        $this->sql = "delete from $this->tableName where " . PhotoAttr::id[KeyTable::name] . "=:id";
        $stmt = $this->pdo->prepare($this->sql);
        $stmt->execute(['id' => $photoId]);
        return $stmt->rowCount();
    }
}