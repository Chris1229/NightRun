package com.zt.nightrun.model.req;

import com.quncao.core.http.annotation.HttpReqParam;
import com.zt.nightrun.model.resp.RespModify;
import com.zt.nightrun.model.resp.RespModifyTeamInfo;

import java.io.Serializable;

/**
 * 作者：by chris
 * 日期：on 2017/7/30 - 上午9:18.
 * 邮箱：android_cjx@163.com
 */
@HttpReqParam(responseType = RespModifyTeamInfo.class, protocal = "group/modifyGroup.do")
public class ReqModifyTeamInfo implements Serializable {

    private int groupId;
    private String name;
    private String image;

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    @Override
    public String toString() {
        return "ReqModifyTeamInfo{" +
                "groupId=" + groupId +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
