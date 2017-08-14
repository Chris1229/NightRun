package com.zt.nightrun.model.resp;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：by chris
 * 日期：on 2017/8/7 - 下午10:03.
 * 邮箱：android_cjx@163.com
 */

public class RespFriendsList extends BaseModel implements Serializable {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RespFriendsList{" +
                "data=" + data +
                '}';
    }

    public class Data implements Serializable{

        private List<Friend> listDeviceFriend;

        public List<Friend> getListDeviceFriend() {
            return listDeviceFriend;
        }

        public void setListDeviceFriend(List<Friend> listDeviceFriend) {
            this.listDeviceFriend = listDeviceFriend;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "listDeviceFriend=" + listDeviceFriend +
                    '}';
        }
    }
}
