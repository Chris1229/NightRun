package com.zt.nightrun.model.req;

import com.quncao.core.http.annotation.HttpReqParam;
import com.zt.nightrun.model.resp.RespAddGroup;
import com.zt.nightrun.model.resp.RespCreateGroup;

import java.io.Serializable;

/**
 * 作者：by chris
 * 日期：on 2017/8/11 - 下午2:24.
 * 邮箱：android_cjx@163.com
 */
@HttpReqParam(responseType = RespAddGroup.class, protocal = "group/joinGroup.do")
public class ReqAddGroup implements Serializable {

    private String groupCode;
    private int groupId;
    private String qrcode;

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    @Override
    public String toString() {
        return "ReqAddGroup{" +
                "groupCode='" + groupCode + '\'' +
                ", groupId=" + groupId +
                ", qrcode='" + qrcode + '\'' +
                '}';
    }
}
