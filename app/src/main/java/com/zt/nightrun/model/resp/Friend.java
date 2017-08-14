package com.zt.nightrun.model.resp;

import java.io.Serializable;

/**
 * 作者：by chris
 * 日期：on 2017/8/9 - 下午2:40.
 * 邮箱：android_cjx@163.com
 */

public class Friend implements Serializable {

    private String mobile;
    private int deviceFriendId;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getDeviceFriendId() {
        return deviceFriendId;
    }

    public void setDeviceFriendId(int deviceFriendId) {
        this.deviceFriendId = deviceFriendId;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "mobile='" + mobile + '\'' +
                ", deviceFriendId=" + deviceFriendId +
                '}';
    }
}
