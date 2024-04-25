package com.example.myapplication.model;

public class ResponseAPI<T> {
    public static final int NEED_LOGIN = -1;
    public static final int ERROR = 0;
    public static final int SUCCESS = 1;
    public static final int EXPIRED = -2;
    public static final int SERVER_ERROR = -3;
    public static final int NO_HAVE_COUPLE = -4;
    private int status;
    private String message;
    private T data;
    public ResponseAPI(int status) {
        this.status = status;
    }
    public ResponseAPI(int status, String message) {
        this.status = status;
        this.message = message;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public int getStatus() {
        return this.status;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return this.message;
    }
    public void setData(T t) {
        this.data = t;
    }
    public T getData() {
        return this.data;
    }
}
