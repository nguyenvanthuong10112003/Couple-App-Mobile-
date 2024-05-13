<?php 
include_once(dirname(__FILE__).'/../../authen/CheckURL.php');
include_once(dirname(__FILE__).'/../../include/ConnectSQL.php');
include_once(dirname(__FILE__).'/../../data/UserRepository.php');
include_once(dirname(__FILE__).'/../../authen/AuthenLogin.php');
include_once(dirname(__FILE__).'/../../entity/RequestAPI.php');
include_once(dirname(__FILE__).'/../../helper/Convert.php');
include_once(dirname(__FILE__).'/../../attr/DateInvitationAttribute.php');
include_once(dirname(__FILE__).'/../../entity/DateInvitationAdd.php');
include_once(dirname(__FILE__).'/../../entity/DateInvitation.php');
include_once(dirname(__FILE__).'/../../data/CoupleRepository.php');
include_once(dirname(__FILE__).'/../../service/PhotoService.php');
include_once(dirname(__FILE__).'/../../helper/StringHelper.php');
include_once(dirname(__FILE__).'/../../attr/CoupleAttribute.php');
include_once(dirname(__FILE__).'/../../attr/PhotoAttribute.php');
include_once(dirname(__FILE__).'/../../data/PhotoRepository.php');

if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
    header('HTTP/1.0 404 Not Found');
    exit(0);
}

if (!isset($_FILES['image_file'])) {
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, 'Dữ liệu gửi lên không họp lệ', null), JSON_UNESCAPED_UNICODE);
    exit(0);
}
$context = new CoupleRepository();
$couple = $context->getForUser($currentUser->id);
if (!$couple) {
    echo json_encode(new RequestAPI(RequestAPI::$ERROR, "Bạn chưa có cặp đôi, vui lòng ghép cặp đôi", null), JSON_UNESCAPED_UNICODE);
    exit(0);
}
try {
    $newPhoto = PhotoService::addImageFile($_FILES['image_file']);
    if (!($newPhoto != null && isset($newPhoto[PhotoService::$PUBLIC_ID]) && isset($newPhoto[PhotoService::$SECURE_URL]))) 
    {
        echo json_encode(new RequestAPI(RequestAPI::$ERROR, 'Ảnh không hợp lệ', null), JSON_UNESCAPED_UNICODE);
        exit(0);
    }
    $contextPhoto = new PhotoRepository();
    if ($couple->photoId) {
        $photo = $contextPhoto->getById($couple->photoId);
        if ($photo) {
            PhotoService::removeImage($photo->photoPublicId);
            $photo->photoPublicId = $newPhoto[PhotoService::$PUBLIC_ID];
            $photo->photoUrl = $newPhoto[PhotoService::$SECURE_URL];
            if ($contextPhoto->edit($photo->photoId, $newPhoto[PhotoService::$SECURE_URL], $newPhoto[PhotoService::$PUBLIC_ID])) {
                echo json_encode(new RequestAPI(RequestAPI::$SUCCESS, 'Thành công', $photo->photoUrl), JSON_UNESCAPED_UNICODE);
                exit(0);
            } else {
                echo json_encode(new RequestAPI(RequestAPI::$ERROR, 'Thất bại', null), JSON_UNESCAPED_UNICODE);
                exit(0);
            }
        }
    }
    if (!$contextPhoto->create($newPhoto[PhotoService::$SECURE_URL], $newPhoto[PhotoService::$PUBLIC_ID]))
    {
        echo json_encode(new RequestAPI(RequestAPI::$ERROR, 'Thất bại', null), JSON_UNESCAPED_UNICODE);
        exit(0);
    }
    $newPhoto = $contextPhoto->getByPublicId($newPhoto[PhotoService::$PUBLIC_ID]);
    $couple->photoId = $newPhoto->photoId;
    if ($context->changeBackground($couple->id, $couple->photoId))
    {
        echo json_encode(new RequestAPI(RequestAPI::$SUCCESS, 'Thành công', $newPhoto->photoUrl), JSON_UNESCAPED_UNICODE);
        exit(0);
    }
} catch(Exception $e) {}
echo json_encode(new RequestAPI(RequestAPI::$ERROR, 'Thất bại', null), JSON_UNESCAPED_UNICODE);
