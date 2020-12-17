package com.wx.inspect.transaction.single.base;

public class Result<T> {

    private String result;
    private String message;
    private T obj;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public Result(String result, String message) {
        this.result = result;
        this.message = message;
    }


    public Result() {
    }

    public static String getResult(String result, String message){
        return "{\"result\":\""+result+"\",\"message\":\""+message+"\"}";
    }
}
