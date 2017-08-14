package com.zt.nightrun.model.req;

import com.quncao.core.http.annotation.HttpReqParam;
import com.zt.nightrun.model.resp.RespBinddevice;
import com.zt.nightrun.model.resp.RespCreateGroup;

import java.io.Serializable;

/**
 * 作者：by chris
 * 日期：on 2017/8/11 - 下午2:24.
 * 邮箱：android_cjx@163.com
 */
@HttpReqParam(responseType = RespCreateGroup.class, protocal = "group/createGroup.do")
public class ReqCreateGroup implements Serializable {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ReqCreateGroup{" +
                "name='" + name + '\'' +
                '}';
    }
}
