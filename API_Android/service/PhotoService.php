<?php 
include_once(dirname(__FILE__).'/../authen/CheckURL.php');
include_once(dirname(__FILE__).'/../define/KeyPhoto.php');
require_once __DIR__ . '/../vendor/autoload.php';

use Cloudinary\Cloudinary;

class PhotoService {
    public static string $PUBLIC_ID = 'public_id';
    public static string $SECURE_URL = 'secure_url';
    static function addImageFile($file) {
        $cloudinary = new Cloudinary(
            [
                'cloud' => KeyPhotoCloudinary::objKey
            ]
        );
        $upload = $cloudinary->uploadApi();
        return $upload->upload(
            $file["tmp_name"]
        );
    }
    static function addImage(string $urlImage) {
        $cloudinary = new Cloudinary(
            [
                'cloud' => KeyPhotoCloudinary::objKey
            ]
        );
        $upload = $cloudinary->uploadApi();
        return $upload->upload(
                $urlImage, 
                [
                    'use_filename' => TRUE,
                    'overwrite' => TRUE
                ]
            );
    }
    static function getImage(string $idImage) {
        $cloudinary = new Cloudinary(
            [
                'cloud' => KeyPhotoCloudinary::objKey
            ]
        );
        $adminAPI = $cloudinary->adminApi();
        return $adminAPI->asset($idImage, [
            'colors' => TRUE]);
    }
    static function removeImage(string $publicId): bool {
        $cloudinary = new Cloudinary(
            [
                'cloud' => KeyPhotoCloudinary::objKey
            ]
        );
        $upload = $cloudinary->uploadApi();
        return $upload->destroy($publicId)['result'] == 'ok';
    }
}