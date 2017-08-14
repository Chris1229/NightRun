package com.zt.nightrun.model.req;

import com.quncao.core.http.annotation.HttpReqParam;
import com.zt.nightrun.model.resp.RespFindPwd;
import com.zt.nightrun.model.resp.RespLogin;

import java.io.Serializable;

/**
 * 作者：by chris
 * 日期：on 2017/7/30 - 上午9:18.
 * 邮箱：android_cjx@163.com
 */
@HttpReqParam(responseType = RespFindPwd.class, protocal = "user/findpwd.do")
public class ReqFindPwd implements Serializable {

    private String mobile;
    private String password;
    private long vcode;

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
        return "ReqFindPwd{" +
                "mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                ", vcode=" + vcode +
                '}';
    }
}
