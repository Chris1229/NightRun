package com.zt.nightrun.model.req;

import com.quncao.core.http.annotation.HttpReqParam;
import com.zt.nightrun.model.resp.RespActiveDevice;
import com.zt.nightrun.model.resp.RespFindPwd;

import java.io.Serializable;

/**
 * 作者：by chris
 * 日期：on 2017/8/7 - 下午9:53.
 * 邮箱：android_cjx@163.com
 */
@HttpReqParam(responseType = RespActiveDevice.class, protocal = "device/activeDevice.do")
public class ReqActiveDevice implements Serializable {

    private int isActive;  //1-激活，0－反激活
    private String name;  //用户给设备命名
    private String qrcode;  //md5 = md5(uid+key2017uidqrcode),用来校验二维码是否正确，测试阶段不起用校验，前端校验
    private String uid;  //uid和qrcode都传到后台， 后台会二次校验qrcode

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "ReqActiveDevice{" +
                "isActive=" + isActive +
                ", name='" + name + '\'' +
                ", qrcode='" + qrcode + '\'' +
                ", uid='" + uid + '\'' +
                '}';
    }
}
