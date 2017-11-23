package com.zt.nightrun.blue.model;

import java.io.Serializable;

/**
 * 作者：by chris
 * 日期：on 2017/9/5 - 上午10:01.
 * 邮箱：android_cjx@163.com
 */

public class BlueFunction implements Serializable{

    private String switchCraft;
    private String lightColor;
    private String lightBright;
    private String lightDodge;
    private String lightMove;

    public String getSwitchCraft() {
        return switchCraft;
    }

    public void setSwitchCraft(String switchCraft) {
        this.switchCraft = switchCraft;
    }

    public String getLightColor() {
        return lightColor;
    }

    public void setLightColor(String lightColor) {
        this.lightColor = lightColor;
    }

    public String getLightBright() {
        return lightBright;
    }

    public void setLightBright(String lightBright) {
        this.lightBright = lightBright;
    }

    public String getLightDodge() {
        return lightDodge;
    }

    public void setLightDodge(String lightDodge) {
        this.lightDodge = lightDodge;
    }

    public String getLightMove() {
        return lightMove;
    }

    public void setLightMove(String lightMove) {
        this.lightMove = lightMove;
    }

    @Override
    public String toString() {
        return "BlueFunction{" +
                "switchCraft='" + switchCraft + '\'' +
                ", lightColor='" + lightColor + '\'' +
                ", lightBright='" + lightBright + '\'' +
                ", lightDodge='" + lightDodge + '\'' +
                ", lightMove='" + lightMove + '\'' +
                '}';
    }
}
