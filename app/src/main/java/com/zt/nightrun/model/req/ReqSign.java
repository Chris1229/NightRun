package com.zt.nightrun.model.req;

import com.quncao.core.http.annotation.HttpReqParam;
import com.zt.nightrun.model.resp.RespSign;

import java.io.Serializable;

/**
 * 作者：by chris
 * 日期：on 2017/8/23 - 下午3:19.
 * 邮箱：android_cjx@163.com
 */
@HttpReqParam(responseType = RespSign.class, protocal = "group/checkIn.do")
public class ReqSign implements Serializable {
    private int groupId;
    private int userId;

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ReqSign{" +
                "groupId=" + groupId +
                ", userId=" + userId +
                '}';
    }
}
