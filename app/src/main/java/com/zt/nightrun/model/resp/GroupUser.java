package com.zt.nightrun.model.resp;

import java.io.Serializable;

/**
 * 作者：by chris
 * 日期：on 2017/8/14 - 下午11:06.
 * 邮箱：android_cjx@163.com
 */

public class GroupUser implements Serializable {
    private Device device;
    private User user;
    private boolean blnOwner;
    private boolean blnCheckIn;
    private boolean blnActive;

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

    public boolean isBlnOwner() {
        return blnOwner;
    }

    public void setBlnOwner(boolean blnOwner) {
        this.blnOwner = blnOwner;
    }

    public boolean isBlnCheckIn() {
        return blnCheckIn;
    }

    public void setBlnCheckIn(boolean blnCheckIn) {
        this.blnCheckIn = blnCheckIn;
    }

    public boolean isBlnActive() {
        return blnActive;
    }

    public void setBlnActive(boolean blnActive) {
        this.blnActive = blnActive;
    }

    @Override
    public String toString() {
        return "GroupUser{" +
                "device=" + device +
                ", user=" + user +
                ", blnOwner=" + blnOwner +
                ", blnCheckIn=" + blnCheckIn +
                ", blnActive=" + blnActive +
                '}';
    }
}
