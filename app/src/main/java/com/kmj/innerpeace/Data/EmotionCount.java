package com.kmj.innerpeace.Data;

import java.util.ArrayList;

public class EmotionCount {
    boolean result;
    ArrayList<Integer> data;
    String message;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public ArrayList<Integer> getData() {
        return data;
    }

    public void setData(ArrayList<Integer> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public EmotionCount(boolean result, ArrayList<Integer> data, String message) {
        this.result = result;
        this.data = data;
        this.message = message;
    }
}
