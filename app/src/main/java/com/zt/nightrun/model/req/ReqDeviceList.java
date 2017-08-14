package com.zt.nightrun.model.req;

import com.quncao.core.http.annotation.HttpReqParam;
import com.zt.nightrun.model.resp.RespBinddevice;
import com.zt.nightrun.model.resp.RespDeviceList;

import java.io.Serializable;

/**
 * 作者：by chris
 * 日期：on 2017/8/7 - 下午9:28.
 * 邮箱：android_cjx@163.com
 */
@HttpReqParam(responseType = RespDeviceList.class, protocal = "device/getListDevice.do")
public class ReqDeviceList implements Serializable {

    private boolean isMine; //true->获取我的， false->获取我和好友的

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    @Override
    public String toString() {
        return "ReqDeviceList{" +
                "isMine=" + isMine +
                '}';
    }
}
