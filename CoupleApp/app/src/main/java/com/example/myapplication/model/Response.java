package com.example.myapplication.model;

public class Response <T> {
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
