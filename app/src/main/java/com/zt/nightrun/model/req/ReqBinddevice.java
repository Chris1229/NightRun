package com.zt.nightrun.model.req;

import com.quncao.core.http.annotation.HttpReqParam;
import com.zt.nightrun.model.resp.RespBinddevice;
import com.zt.nightrun.model.resp.RespInit;

import java.io.Serializable;

/**
 * 作者：by chris
 * 日期：on 2017/7/30 - 上午9:28.
 * 邮箱：android_cjx@163.com
 */
@HttpReqParam(responseType = RespBinddevice.class, protocal = "user/binddevice.do")
public class ReqBinddevice implements Serializable {

    private String devicetype; //Android or iOS
    private String pushChannelType; //ios,getui,xiaomi,huawei
    private String token; //第三方推送标识
    private String userid;  //选填

    public String getDevicetype() {
        return devicetype;
    }

    public void setDevicetype(String devicetype) {
        this.devicetype = devicetype;
    }

    public String getPushChannelType() {
        return pushChannelType;
    }

    public void setPushChannelType(String pushChannelType) {
        this.pushChannelType = pushChannelType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Override
    public String toString() {
        return "ReqBinddevice{" +
                "devicetype='" + devicetype + '\'' +
                ", pushChannelType='" + pushChannelType + '\'' +
                ", token='" + token + '\'' +
                ", userid='" + userid + '\'' +
                '}';
    }
}
