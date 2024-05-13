<?php 
include_once(dirname(__FILE__).'/../authen/CheckURL.php');

class RequestAPI
{
    public static int $NEED_LOGIN = -1;
    public static int $ERROR = 0;
    public static int $SUCCESS = 1;
    public static int $EXPIRE = -2;
    public static int $NO_HAVE_COUPLE = -4;
    public int $status;
    public string $message;
    public $data;
    public function __construct(int $id, string $message, $data) {
        $this->status = $id;
        $this->message = $message;
        $this->data = $data;
    }
}
