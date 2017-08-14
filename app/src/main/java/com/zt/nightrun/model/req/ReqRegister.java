package com.zt.nightrun.model.req;

import com.quncao.core.http.annotation.HttpReqParam;
import com.zt.nightrun.model.resp.RespLogin;
import com.zt.nightrun.model.resp.RespRegister;

import java.io.Serializable;

/**
 * 作者：by chris
 * 日期：on 2017/7/30 - 上午9:18.
 * 邮箱：android_cjx@163.com
 */
@HttpReqParam(responseType = RespRegister.class, protocal = "user/register.do")
public class ReqRegister implements Serializable {

    private String mobile;
    private String password;  //MD5加密
    private long vcode; //验证码

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

    public long getVcode() {
        return vcode;
    }

    public void setVcode(long vcode) {
        this.vcode = vcode;
    }

    @Override
    public String toString() {
        return "ReqRegister{" +
                "mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                ", vcode=" + vcode +
                '}';
    }
}
