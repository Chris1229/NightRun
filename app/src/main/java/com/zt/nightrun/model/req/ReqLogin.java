package com.zt.nightrun.model.req;

import com.quncao.core.http.annotation.HttpReqParam;
import com.zt.nightrun.model.resp.RespInit;
import com.zt.nightrun.model.resp.RespLogin;

import java.io.Serializable;

/**
 * 作者：by chris
 * 日期：on 2017/7/30 - 上午9:18.
 * 邮箱：android_cjx@163.com
 */
@HttpReqParam(responseType = RespLogin.class, protocal = "user/login.do")
public class ReqLogin implements Serializable {

    private String mobile;
    private String password;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "ReqLogin{" +
                "mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
