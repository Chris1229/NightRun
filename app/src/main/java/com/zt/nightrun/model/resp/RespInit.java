package com.zt.nightrun.model.resp;

import java.io.Serializable;

/**
 * 作者：by chris
 * 日期：on 2017/7/30 - 上午9:19.
 * 邮箱：android_cjx@163.com
 */

public class RespInit extends BaseModel implements Serializable{

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RespInit{" +
                "data=" + data +
                '}';
    }

    public class Data implements Serializable{

        private String sessionid;
        private User user;

        public String getSessionid() {
            return sessionid;
        }

        public void setSessionid(String sessionid) {
            this.sessionid = sessionid;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "sessionid='" + sessionid + '\'' +
                    ", user=" + user +
                    '}';
        }
    }
}
