package com.zt.nightrun.model.resp;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：by chris
 * 日期：on 2017/8/7 - 下午9:30.
 * 邮箱：android_cjx@163.com
 */

public class RespDeviceList extends BaseModel implements Serializable {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RespDeviceList{" +
                "data=" + data +
                '}';
    }

    public class Data implements Serializable{

        List<DeviceItem> listDeviceVo;

        public List<DeviceItem> getListDeviceVo() {
            return listDeviceVo;
        }

        public void setListDeviceVo(List<DeviceItem> listDeviceVo) {
            this.listDeviceVo = listDeviceVo;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "listDeviceVo=" + listDeviceVo +
                    '}';
        }
    }
}
