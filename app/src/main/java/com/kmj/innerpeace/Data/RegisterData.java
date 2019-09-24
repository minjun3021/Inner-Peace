package com.kmj.innerpeace.Data;

public class RegisterData {
    String result;
    String data;
    String message;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RegisterData(String result, String data, String message) {
        this.result = result;
        this.data = data;
        this.message = message;
    }
}
