package com.zt.nightrun.model.resp;

import java.io.Serializable;

/**
 * 作者：by chris
 * 日期：on 2017/8/9 - 下午3:08.
 * 邮箱：android_cjx@163.com
 */

public class Location implements Serializable {

    private int id;
    private String accessTime;
    private double lat;
    private double lng;
    private int status;
    private int deviceId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(String accessTime) {
        this.accessTime = accessTime;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", accessTime='" + accessTime + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", status=" + status +
                ", deviceId=" + deviceId +
                '}';
    }
}
