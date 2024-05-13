<?php 
include_once(dirname(__FILE__).'/../../authen/CheckURL.php');
include_once(dirname(__FILE__).'/../../data/UserRepository.php');
include_once(dirname(__FILE__).'/../../data/PhotoRepository.php');
include_once(dirname(__FILE__).'/../../authen/AuthenLogin.php');
include_once(dirname(__FILE__).'/../../entity/RequestAPI.php');
include_once(dirname(__FILE__).'/../../dto/UserDto.php');
include_once(dirname(__FILE__).'/../../helper/Convert.php');
include_once(dirname(__FILE__).'/../../entity/UserEdit.php');
include_once(dirname(__FILE__).'/../../service/PhotoService.php');
include_once(dirname(__FILE__).'/../../helper/StringHelper.php');

if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
    header('HTTP/1.0 404 Not Found');
    exit(0);
}

if (!(isset($_POST[UserAttr::fullName[KeyTable::name]]) && isset($_POST[UserAttr::email[KeyTable::name]]) && 
    isset($_POST[UserAttr::gender[KeyTable::name]]) && isset($_POST[UserAttr::dob[KeyTable::name]]))) {
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, 'Dữ liệu gửi lên không hợp lệ', null), JSON_UNESCAPED_UNICODE);
    exit(0);
}

if (!StringHelper::isValEmail($_POST[UserAttr::email[KeyTable::name]])) {
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, 'Email không đúng định dạng', null), JSON_UNESCAPED_UNICODE);
    exit(0);
}

if (!StringHelper::isValFullName($_POST[UserAttr::fullName[KeyTable::name]])) {
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, 'Tên không được để trống', null), JSON_UNESCAPED_UNICODE);
    exit(0);
}

$context = new UserRepository();
$user = $context->getByUsername($currentUser->username);

if ($user == null)
{
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, 'Người dùng không tồn tại', null), JSON_UNESCAPED_UNICODE);
    exit(0);
}

$user->fullName = $_POST[UserAttr::fullName[KeyTable::name]];
$user->email = $_POST[UserAttr::email[KeyTable::name]];
$user->dob = new DateTime(($_POST[UserAttr::dob[KeyTable::name]]));
$user->gender = $_POST[UserAttr::gender[KeyTable::name]] === "true";
if (isset($_POST[UserAttr::alias[KeyTable::name]]))
    $user->alias = $_POST[UserAttr::alias[KeyTable::name]];
if (isset($_POST[UserAttr::lifeStory[KeyTable::name]]))
    $user->lifeStory = $_POST[UserAttr::lifeStory[KeyTable::name]];

$userByEmail = $context->getByEmail($user->email);
if ($userByEmail != null && $userByEmail->username != $user->username)
{
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, 'Email đã có người sử dụng', null), JSON_UNESCAPED_UNICODE);
    exit(0);
}

function changeImage() {
    try {
        if (!isset($_FILES['image_file'])) 
            return;
        $newPhoto = PhotoService::addImageFile($_FILES['image_file']);
        if (!($newPhoto != null && isset($newPhoto[PhotoService::$PUBLIC_ID]) && isset($newPhoto[PhotoService::$SECURE_URL]))) 
            return;
        $contextPhoto = new PhotoRepository();
        global $user;
        if ($user->photoId) {
            $photo = $contextPhoto->getById($user->photoId);
            if ($photo) {
                PhotoService::removeImage($photo->photoPublicId);
                $photo->photoPublicId = $newPhoto[PhotoService::$PUBLIC_ID];
                $photo->photoUrl = $newPhoto[PhotoService::$SECURE_URL];
                $contextPhoto->edit($photo->photoId, $newPhoto[PhotoService::$SECURE_URL], $newPhoto[PhotoService::$PUBLIC_ID]);
                return;
            }
        }
        if (!$contextPhoto->create($newPhoto[PhotoService::$SECURE_URL], $newPhoto[PhotoService::$PUBLIC_ID]))
            return;
        $newPhoto = $contextPhoto->getByPublicId($newPhoto[PhotoService::$PUBLIC_ID]);
        $user->photoId = $newPhoto->photoId;
    } catch(Exception $e) {}
}

changeImage();

$result = $context->update($user);
if ($result)
    echo json_encode(new RequestAPI(RequestAPI::$SUCCESS, "Sửa thành công", 
    $context->getDtoByUsername($currentUser->username)), JSON_UNESCAPED_UNICODE);
else 
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, "Thất bại", null), JSON_UNESCAPED_UNICODE);