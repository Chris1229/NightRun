package com.zt.nightrun.model.req;

import com.quncao.core.http.annotation.HttpReqParam;
import com.zt.nightrun.model.resp.RespLocationHistory;

import java.io.Serializable;

/**
 * 作者：by chris
 * 日期：on 2017/8/9 - 下午3:06.
 * 邮箱：android_cjx@163.com
 */
@HttpReqParam(responseType = RespLocationHistory.class, protocal = "device/getDeviceHistoryPosition.do")
public class ReqLocationHistory implements Serializable {

    private int deviceId;

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String toString() {
        return "ReqLocationHistory{" +
                "deviceId=" + deviceId +
                '}';
    }
}
