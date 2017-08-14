package com.zt.nightrun.model.resp;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：by chris
 * 日期：on 2017/8/9 - 下午3:07.
 * 邮箱：android_cjx@163.com
 */

public class RespLocationHistory extends BaseModel implements Serializable {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RespLocationHistory{" +
                "data=" + data +
                '}';
    }

    public class Data implements Serializable{

        private List<Location> history;

        public List<Location> getHistory() {
            return history;
        }

        public void setHistory(List<Location> history) {
            this.history = history;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "history=" + history +
                    '}';
        }
    }
}
