package com.zt.nightrun.model.resp;

import java.io.Serializable;

/**
 * 作者：by chris
 * 日期：on 2017/8/7 - 下午9:35.
 * 邮箱：android_cjx@163.com
 */

public class Device implements Serializable {

    private int id;
    private String uid;
    private int isActive;
    private int activeUserId;
    private String name;
    private String lastTime;
    private String createTime;
    private String activeTime;
    private String mobile;
    private int status;
    private double power;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public int getActiveUserId() {
        return activeUserId;
    }

    public void setActiveUserId(int activeUserId) {
        this.activeUserId = activeUserId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(String activeTime) {
        this.activeTime = activeTime;
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

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", uid='" + uid + '\'' +
                ", isActive=" + isActive +
                ", activeUserId=" + activeUserId +
                ", name='" + name + '\'' +
                ", lastTime='" + lastTime + '\'' +
                ", createTime='" + createTime + '\'' +
                ", activeTime='" + activeTime + '\'' +
                ", mobile='" + mobile + '\'' +
                ", status=" + status +
                ", power=" + power +
                '}';
    }
}
