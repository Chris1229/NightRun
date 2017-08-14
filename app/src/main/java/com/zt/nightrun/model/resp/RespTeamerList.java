package com.zt.nightrun.model.resp;

import java.io.Serializable;

/**
 * 作者：by chris
 * 日期：on 2017/8/14 - 下午2:39.
 * 邮箱：android_cjx@163.com
 */

public class RespTeamerList extends BaseModel implements Serializable {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RespTeamerList{" +
                "data=" + data +
                '}';
    }

    public class Data implements Serializable{

    }
}
