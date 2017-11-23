package com.zt.nightrun.eventbus;

/**
 * 作者：by chris
 * 日期：on 2017/8/23 - 下午4:35.
 * 邮箱：android_cjx@163.com
 */

public class TeamName {
    private String name;

    public TeamName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TeamName{" +
                "name='" + name + '\'' +
                '}';
    }
}
