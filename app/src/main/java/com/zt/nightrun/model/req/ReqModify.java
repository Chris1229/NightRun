package com.zt.nightrun.model.req;

import com.quncao.core.http.annotation.HttpReqParam;
import com.zt.nightrun.model.resp.RespInit;
import com.zt.nightrun.model.resp.RespModify;

import java.io.Serializable;

/**
 * 作者：by chris
 * 日期：on 2017/7/30 - 上午9:18.
 * 邮箱：android_cjx@163.com
 */
@HttpReqParam(responseType = RespModify.class, protocal = "user/modify.do")
public class ReqModify implements Serializable {

    private String nick;
    private String image;
    private int gender; //1男 0女

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "ReqModify{" +
                "nick='" + nick + '\'' +
                ", image='" + image + '\'' +
                ", gender=" + gender +
                '}';
    }
}
