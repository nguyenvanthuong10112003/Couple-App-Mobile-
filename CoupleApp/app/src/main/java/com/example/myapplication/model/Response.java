package com.example.myapplication.model;

public class Response <T> {
    public static final int NEED_LOGIN = -1;
    public static final int ERROR = 0;
    public static final int SUCCESS = 1;
    public static final int EXPIRED = -2;
    private int status;
    private String message;
    private T data;
    public Response(int status, String message, T t) {
        this.status = status;
        this.message = message;
        this.data = t;
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
