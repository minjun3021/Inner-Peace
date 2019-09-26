package com.kmj.innerpeace.Data;

public class EmotionAvg {
    Boolean result;
    double data;
    String message;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public double getData() {
        return data;
    }

    public void setData(double data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public EmotionAvg(Boolean result, double data, String message) {
        this.result = result;
        this.data = data;
        this.message = message;
    }
}
