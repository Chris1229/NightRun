package com.zt.nightrun.model.req;

import com.quncao.core.http.annotation.HttpReqParam;
import com.zt.nightrun.model.resp.RespCall;

import java.io.Serializable;

/**
 * 作者：by chris
 * 日期：on 2017/8/30 - 下午8:58.
 * 邮箱：android_cjx@163.com
 */
@HttpReqParam(responseType = RespCall.class, protocal = "group/call.do")
public class ReqCall implements Serializable{

    private int groupId;

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "ReqCall{" +
                "groupId=" + groupId +
                '}';
    }
}
