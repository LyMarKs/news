package com.news.entity;

/**
 * @Author 谭志扬
 * @Date 2022/5/21 13 22
 */
public class User {
    private int uid;
    private String uname;
    private String upwd;
    private int uidentity;
    private String identityName;

    public int getUid() {
        return uid;
    }
    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }
    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUpwd() {
        return upwd;
    }
    public void setUpwd(String upwd) {
        this.upwd = upwd;
    }

    public int getUidentity() {
        return uidentity;
    }
    public void setUidentity(int uidentity) {
        this.uidentity = uidentity;
    }

    public String getIdentityName() {
        return identityName;
    }

    public void setIdentityName(String identityName) {
        this.identityName = identityName;
    }

    public User() {
    }
    public User(int uid, String uname, String upwd, int uidentity) {
        this.uid = uid;
        this.uname = uname;
        this.upwd = upwd;
        this.uidentity = uidentity;
    }
    public User(String uname, String upwd, int uidentity) {
        this.uname = uname;
        this.upwd = upwd;
        this.uidentity = uidentity;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", uname='" + uname + '\'' +
                ", upwd='" + upwd + '\'' +
                ", uidentity=" + uidentity +
                '}';
    }
}
