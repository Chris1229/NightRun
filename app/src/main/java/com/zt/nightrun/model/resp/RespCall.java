package com.zt.nightrun.model.resp;

import java.io.Serializable;

/**
 * 作者：by chris
 * 日期：on 2017/8/30 - 下午8:59.
 * 邮箱：android_cjx@163.com
 */

public class RespCall extends BaseModel implements Serializable{
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RespCall{" +
                "data=" + data +
                '}';
    }

    public class Data implements Serializable{
        private String result;

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "result='" + result + '\'' +
                    '}';
        }
    }
}
