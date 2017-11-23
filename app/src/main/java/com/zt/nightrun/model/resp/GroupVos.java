package com.zt.nightrun.model.resp;

import java.io.Serializable;

/**
 * 作者：by chris
 * 日期：on 2017/8/14 - 下午8:13.
 * 邮箱：android_cjx@163.com
 */

public class GroupVos implements Serializable{

    private Group group;
    private User user;
    private boolean blnOwner;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
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

    @Override
    public String toString() {
        return "GroupVos{" +
                "group=" + group +
                ", user=" + user +
                ", blnOwner=" + blnOwner +
                '}';
    }
}
