package com.zt.nightrun.model.req;

import com.quncao.core.http.annotation.HttpReqParam;
import com.zt.nightrun.model.resp.RespActiveGroup;

import java.io.Serializable;

/**
 * 作者：by chris
 * 日期：on 2017/8/14 - 下午2:33.
 * 邮箱：android_cjx@163.com
 */
@HttpReqParam(responseType = RespActiveGroup.class, protocal = "group/activeGroup.do")
public class ReqActiveGroup implements Serializable {

    private int groupId;
    private boolean isActive;

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "ReqActiveGroup{" +
                "groupId=" + groupId +
                ", isActive=" + isActive +
                '}';
    }
}
