package com.zt.nightrun.model.req;

import com.quncao.core.http.annotation.HttpReqParam;
import com.zt.nightrun.model.resp.RespDeleteGroup;

import java.io.Serializable;

/**
 * 作者：by chris
 * 日期：on 2017/8/11 - 下午2:33.
 * 邮箱：android_cjx@163.com
 */
@HttpReqParam(responseType = RespDeleteGroup.class, protocal = "group/exitGroup.do")
public class ReqDeleteGroup implements Serializable {

    private int groupId;

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "ReqDeleteGroup{" +
                "groupId=" + groupId +
                '}';
    }
}
