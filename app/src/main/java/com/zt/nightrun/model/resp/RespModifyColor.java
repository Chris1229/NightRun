package com.zt.nightrun.model.resp;

import java.io.Serializable;

/**
 * 作者：by chris
 * 日期：on 2017/11/15 - 下午2:13.
 * 邮箱：android_cjx@163.com
 */

public class RespModifyColor extends BaseModel {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RespModifyColor{" +
                "data=" + data +
                '}';
    }

    public class Data implements Serializable{

    }
}
