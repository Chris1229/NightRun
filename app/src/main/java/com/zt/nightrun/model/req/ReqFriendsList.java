package com.zt.nightrun.model.req;

import com.quncao.core.http.annotation.HttpReqParam;
import com.zt.nightrun.model.resp.RespBinddevice;
import com.zt.nightrun.model.resp.RespFriendsList;

import java.io.Serializable;

/**
 * 作者：by chris
 * 日期：on 2017/8/7 - 下午10:02.
 * 邮箱：android_cjx@163.com
 */
@HttpReqParam(responseType = RespFriendsList.class, protocal = "device/getFriendList.do")
public class ReqFriendsList implements Serializable {

    private int deviceId;

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String toString() {
        return "ReqFriendsList{" +
                "deviceId=" + deviceId +
                '}';
    }
}
