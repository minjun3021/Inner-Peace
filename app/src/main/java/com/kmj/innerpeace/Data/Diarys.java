package com.kmj.innerpeace.Data;

import java.util.ArrayList;

public class Diarys {
    Boolean result;
    public ArrayList<PostData> data;
    String message;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public ArrayList<PostData> getData() {
        return data;
    }

    public void setData(ArrayList<PostData> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Diarys(Boolean result, ArrayList<PostData> data, String message) {
        this.result = result;
        this.data = data;
        this.message = message;
    }
}
