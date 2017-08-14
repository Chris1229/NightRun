package com.zt.nightrun.model.req;

import com.quncao.core.http.annotation.HttpReqParam;
import com.zt.nightrun.model.resp.RespShareCode;

import java.io.Serializable;

/**
 * 作者：by chris
 * 日期：on 2017/8/9 - 上午10:48.
 * 邮箱：android_cjx@163.com
 */
@HttpReqParam(responseType = RespShareCode.class, protocal = "group/getShareCode.do")
public class ReqTeamShareCode implements Serializable {
    private int groupId;

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "ReqTeamShareCode{" +
                "groupId=" + groupId +
                '}';
    }
}
