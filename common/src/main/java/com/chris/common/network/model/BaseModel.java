package com.chris.common.network.model;

import com.google.gson.Gson;

/**
 * 作者：by chris
 * 日期：on 2017/6/28 - 下午3:41.
 * 邮箱：android_cjx@163.com
 */

public class BaseModel extends ModelBase {
    private String ver;
    private boolean ret;
    private String errmsg;
    private int errcode;
    private String sign;
    private String redirct;
    private long server_time;

    public BaseModel() {

    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public boolean isRet() {
        return ret;
    }

    public void setRet(boolean ret) {
        this.ret = ret;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getRedirct() {
        return redirct;
    }

    public void setRedirct(String redirct) {
        this.redirct = redirct;
    }

    public long getServer_time() {
        return server_time;
    }

    public void setServer_time(long server_time) {
        this.server_time = server_time;
    }

    @Override
    public ModelBase parseData(String strData) {
        Gson gson = new Gson();
        return (ModelBase)gson.fromJson(strData, this.getClass());
    }
}
