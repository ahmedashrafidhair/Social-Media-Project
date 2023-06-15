package com.ahmedidhair.socialmedia.model;

import java.io.Serializable;

public class User implements Serializable {
    public String avatar;
    public String fullName;
    public String mobile;
    public String email;
    public String password;

    public User() {
    }
    public User(String fullName, String mobile, String email, String password,String avatar) {
        this.fullName = fullName;
        this.mobile = mobile;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
    }
}
