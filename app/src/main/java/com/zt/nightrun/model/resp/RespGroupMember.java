package com.zt.nightrun.model.resp;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：by chris
 * 日期：on 2017/8/14 - 下午11:08.
 * 邮箱：android_cjx@163.com
 */

public class RespGroupMember extends BaseModel implements Serializable {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RespGroupMember{" +
                "data=" + data +
                '}';
    }

    public class Data implements Serializable{

        private List<GroupUser> listGroupUser;

        public List<GroupUser> getListGroupUser() {
            return listGroupUser;
        }

        public void setListGroupUser(List<GroupUser> listGroupUser) {
            this.listGroupUser = listGroupUser;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "listGroupUser=" + listGroupUser +
                    '}';
        }
    }
}
