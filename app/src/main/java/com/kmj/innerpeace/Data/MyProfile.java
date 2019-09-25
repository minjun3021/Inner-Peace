package com.kmj.innerpeace.Data;

public class MyProfile {
    String salt;
    String _id;
    String email;
    String password;
    String name;
    String phone;
    String imgPath;
    String lastLogin;
    String createAt;
    String __v;

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
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

    public MyProfile(String salt, String _id, String email, String password, String name, String phone, String imgPath, String lastLogin, String createAt, String __v) {
        this.salt = salt;
        this._id = _id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.imgPath = imgPath;
        this.lastLogin = lastLogin;
        this.createAt = createAt;
        this.__v = __v;
    }
}
