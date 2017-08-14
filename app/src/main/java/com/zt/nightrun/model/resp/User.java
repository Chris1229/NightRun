package com.zt.nightrun.model.resp;

import java.io.Serializable;

/**
 * 作者：by chris
 * 日期：on 2017/8/5 - 上午8:43.
 * 邮箱：android_cjx@163.com
 */

public class User implements Serializable {
//            "id":9,
//            "password":"25f9e794323b453885f5181f1b624d0b",
//            "mobile":"18500996239",
//            "status":0,
//            "createTime":"Aug 5, 2017 8:42:31 AM",
//            "lastTime":"Aug 5, 2017 8:42:31 AM",
//            "msgCount":100
    private int id;
    private String password;
    private String mobile;
    private int status;
    private String createTime;
    private String lastTime;
    private int msgCount;
    private String nick;
    private String image;
    private int gender; //1男 0女

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public int getMsgCount() {
        return msgCount;
    }

    public void setMsgCount(int msgCount) {
        this.msgCount = msgCount;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", mobile='" + mobile + '\'' +
                ", status=" + status +
                ", createTime='" + createTime + '\'' +
                ", lastTime='" + lastTime + '\'' +
                ", msgCount=" + msgCount +
                ", nick='" + nick + '\'' +
                ", image='" + image + '\'' +
                ", gender=" + gender +
                '}';
    }
}
