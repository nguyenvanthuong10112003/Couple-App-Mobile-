<?php 
include_once(dirname(__FILE__).'/../authen/CheckURL.php');
include_once(dirname(__FILE__).'/../service/PhotoService.php');
include_once(dirname(__FILE__).'/../entity/User.php');
include_once(dirname(__FILE__).'/../dto/UserDto.php');

class Convert {
    static function UserToUserDto(User $user): UserDto {
        $dto = new UserDto();
        $dto->id = $user->id;
        $dto->fullName = $user->fullName;
        $dto->alias = $user->alias;
        $dto->dob = $user->dob;
        $dto->gender = $user->gender;
        $dto->lifeStory = $user->lifeStory;
        $dto->username = $user->username;
        $dto->email = $user->email;
        $dto->timeCreate = $user->timeCreate;
        $dto->urlAvatar = !$user->photoId ? null : PhotoService::getImage(strval($user->photoId))['secure_url'];
        return $dto;
    }
}