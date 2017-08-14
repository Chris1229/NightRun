package com.zt.nightrun.model.req;

import com.quncao.core.http.annotation.HttpReqParam;
import com.zt.nightrun.model.resp.RespShareCode;

import java.io.Serializable;

/**
 * 作者：by chris
 * 日期：on 2017/8/9 - 上午10:48.
 * 邮箱：android_cjx@163.com
 */
@HttpReqParam(responseType = RespShareCode.class, protocal = "device/getDeviceShareCode.do")
public class ReqShareCode implements Serializable {
    private int deviceId;

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String toString() {
        return "ReqShareCode{" +
                "deviceId=" + deviceId +
                '}';
    }
}
