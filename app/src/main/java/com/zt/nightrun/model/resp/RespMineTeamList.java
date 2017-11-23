package com.zt.nightrun.model.resp;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：by chris
 * 日期：on 2017/8/14 - 下午8:17.
 * 邮箱：android_cjx@163.com
 */

public class RespMineTeamList extends BaseModel implements Serializable {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RespMineTeamList{" +
                "data=" + data +
                '}';
    }

    public class Data implements Serializable{

        private List<GroupVos> groupVos;

        public List<GroupVos> getGroupVos() {
            return groupVos;
        }

        public void setGroupVos(List<GroupVos> groupVos) {
            this.groupVos = groupVos;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "groupVos=" + groupVos +
                    '}';
        }
    }

}
