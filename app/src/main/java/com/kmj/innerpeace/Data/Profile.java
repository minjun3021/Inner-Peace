package com.kmj.innerpeace.Data;

public class Profile {
    String result;
    MyProfile data;
    String message;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public MyProfile getData() {
        return data;
    }

    public void setData(MyProfile data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Profile(String result, MyProfile data, String message) {
        this.result = result;
        this.data = data;
        this.message = message;
    }
}
