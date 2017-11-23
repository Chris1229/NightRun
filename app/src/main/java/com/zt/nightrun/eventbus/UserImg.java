package com.zt.nightrun.eventbus;

/**
 * 作者：by chris
 * 日期：on 2017/8/28 - 下午4:44.
 * 邮箱：android_cjx@163.com
 */

public class UserImg {
    private String image;

    public UserImg(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "UserImg{" +
                "image='" + image + '\'' +
                '}';
    }
}
