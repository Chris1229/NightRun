package com.zt.nightrun.model.resp;

import java.io.Serializable;

/**
 * 作者：by chris
 * 日期：on 2017/7/30 - 下午5:58.
 * 邮箱：android_cjx@163.com
 */

public class BaseModel implements Serializable {

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "BaseModel{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
