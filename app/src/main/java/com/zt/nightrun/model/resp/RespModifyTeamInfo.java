package com.zt.nightrun.model.resp;

import java.io.Serializable;

/**
 * 作者：by chris
 * 日期：on 2017/7/30 - 上午9:23.
 * 邮箱：android_cjx@163.com
 */

public class RespModifyTeamInfo extends BaseModel implements Serializable {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RespLogin{" +
                "data=" + data +
                '}';
    }

    public class Data implements Serializable{
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
