package com.kmj.innerpeace.Data;

public class DiaryData {
    boolean result;
    PostData data;
    String message;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public PostData getData() {
        return data;
    }

    public void setData(PostData data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DiaryData(boolean result, PostData data, String message) {
        this.result = result;
        this.data = data;
        this.message = message;
    }
}
