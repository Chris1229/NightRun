package com.zt.nightrun.model.resp;

import java.io.Serializable;

/**
 * 作者：by chris
 * 日期：on 2017/8/8 - 下午7:47.
 * 邮箱：android_cjx@163.com
 */

public class DeviceItem implements Serializable{

    private Device device;
    private User user;
    private boolean isOwner;

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public void setOwner(boolean owner) {
        isOwner = owner;
    }

    @Override
    public String toString() {
        return "DeviceItem{" +
                "device=" + device +
                ", user=" + user +
                ", isOwner=" + isOwner +
                '}';
    }
}
