package com.zt.nightrun.model.resp;

import java.io.Serializable;

/**
 * 作者：by chris
 * 日期：on 2017/8/7 - 下午9:54.
 * 邮箱：android_cjx@163.com
 */

public class RespActiveDevice extends BaseModel implements Serializable {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "RespActiveDevice{" +
                "data=" + data +
                '}';
    }

    public class Data implements Serializable{

        private Device device;
        private int resultCode;

        public Device getDevice() {
            return device;
        }

        public void setDevice(Device device) {
            this.device = device;
        }

        public int getResultCode() {
            return resultCode;
        }

        public void setResultCode(int resultCode) {
            this.resultCode = resultCode;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "device=" + device +
                    ", resultCode=" + resultCode +
                    '}';
        }
    }
}
