package com.zt.nightrun.model.resp;

import java.io.Serializable;

/**
 * 作者：by chris
 * 日期：on 2017/8/14 - 下午2:34.
 * 邮箱：android_cjx@163.com
 */

public class RespActiveGroup extends BaseModel implements Serializable {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RespActiveGroup{" +
                "data=" + data +
                '}';
    }

    public class  Data implements Serializable{
        private Group group;

        public Group getGroup() {
            return group;
        }

        public void setGroup(Group group) {
            this.group = group;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "group=" + group +
                    '}';
        }
    }

}
