package com.zt.nightrun.model.req;

import com.quncao.core.http.annotation.HttpReqParam;
import com.zt.nightrun.model.resp.RespGroupMember;

import java.io.Serializable;

/**
 * 作者：by chris
 * 日期：on 2017/8/14 - 下午11:07.
 * 邮箱：android_cjx@163.com
 */
@HttpReqParam(responseType = RespGroupMember.class, protocal = "group/getMembersByGroupId.do")
public class ReqGroupMember implements Serializable {

    private int groupId;

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "ReqGroupMember{" +
                "groupId=" + groupId +
                '}';
    }
}
