package com.zt.nightrun.model.req;

import com.quncao.core.http.annotation.HttpReqParam;
import com.zt.nightrun.model.resp.RespVcode;

import java.io.Serializable;

/**
 * 作者：by chris
 * 日期：on 2017/7/30 - 上午9:36.
 * 邮箱：android_cjx@163.com
 */
@HttpReqParam(responseType = RespVcode.class, protocal = "user/reqvcode.do")
public class ReqVcode implements Serializable {
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "ReqVcode{" +
                "mobile='" + mobile + '\'' +
                '}';
    }
}
