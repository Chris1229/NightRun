package com.zt.nightrun.model.resp;

import com.quncao.core.http.annotation.HttpReqParam;

import java.io.Serializable;

/**
 * 作者：by chris
 * 日期：on 2017/7/30 - 上午9:18.
 * 邮箱：android_cjx@163.com
 */
public class RespRegister extends BaseModel implements Serializable {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RespRegister{" +
                "data=" + data +
                '}';
    }

    public class Data implements Serializable{
        private User user;

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "user=" + user +
                    '}';
        }
    }
}
