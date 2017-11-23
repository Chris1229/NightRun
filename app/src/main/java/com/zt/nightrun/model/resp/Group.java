package com.zt.nightrun.model.resp;

import java.io.Serializable;

/**
 * 作者：by chris
 * 日期：on 2017/8/11 - 下午2:26.
 * 邮箱：android_cjx@163.com
 */

public class Group implements Serializable {

//            "id": 5,
//            "name": "哈哈哈哈",
//            "ownerId": 9,
//            "code": "CA3F0C3877CE2F6F96B25D3D9A081FB7",
//            "status": 0,
//            "qrcode": "http://qrcode.ueye.cc?type=group&groupCode=CA3F0C3877CE2F6F96B25D3D9A081FB7&key=8D3704C40497799EBE05603C718395B9",
//            "activeCount": 0,
//            "createTime": "Aug 11, 2017 2:01:54 PM"
    private int id;
    private String name;
    private String image;
    private int ownerId;
    private String code;
    private int status;
    private String qrcode;
    private int activeCount;
    private String createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public int getActiveCount() {
        return activeCount;
    }

    public void setActiveCount(int activeCount) {
        this.activeCount = activeCount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", ownerId=" + ownerId +
                ", code='" + code + '\'' +
                ", status=" + status +
                ", qrcode='" + qrcode + '\'' +
                ", activeCount=" + activeCount +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
