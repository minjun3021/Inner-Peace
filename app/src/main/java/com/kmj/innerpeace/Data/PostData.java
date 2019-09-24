package com.kmj.innerpeace.Data;

public class PostData {
    String _id;
    String title;
    String content;
    String emotion;
    String imgPath;
    String owner;
    String createAt;
    String __v;
    String timeString;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String get__v() {
        return __v;
    }

    public void set__v(String __v) {
        this.__v = __v;
    }

    public String getTimeString() {
        return timeString;
    }

    public void setTimeString(String timeString) {
        this.timeString = timeString;
    }

    public PostData(String _id, String title, String content, String emotion, String imgPath, String owner, String createAt, String __v, String timeString) {
        this._id = _id;
        this.title = title;
        this.content = content;
        this.emotion = emotion;
        this.imgPath = imgPath;
        this.owner = owner;
        this.createAt = createAt;
        this.__v = __v;
        this.timeString = timeString;
    }
}
