package com.zt.nightrun.model.req;

import com.quncao.core.http.annotation.HttpReqParam;
import com.zt.nightrun.model.resp.RespModify;
import com.zt.nightrun.model.resp.RespModifyColor;

import java.io.Serializable;

/**
 * 作者：by chris
 * 日期：on 2017/11/15 - 下午2:12.
 * 邮箱：android_cjx@163.com
 */

@HttpReqParam(responseType = RespModifyColor.class, protocal = "group/modifyColor.do")
public class ReqModifyColor implements Serializable {

    private int colorB;
    private int colorR;
    private int colorG;
    private int isFlash;
    private int groupId;


    public int getColorB() {
        return colorB;
    }

    public void setColorB(int colorB) {
        this.colorB = colorB;
    }

    public int getColorR() {
        return colorR;
    }

    public void setColorR(int colorR) {
        this.colorR = colorR;
    }

    public int getColorG() {
        return colorG;
    }

    public void setColorG(int colorG) {
        this.colorG = colorG;
    }

    public int getIsFlash() {
        return isFlash;
    }

    public void setIsFlash(int isFlash) {
        this.isFlash = isFlash;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "ReqModifyColor{" +
                "colorB=" + colorB +
                ", colorR=" + colorR +
                ", colorG=" + colorG +
                ", isFlash=" + isFlash +
                ", groupId=" + groupId +
                '}';
    }
}
