package com.zt.nightrun.model.resp;

import java.io.Serializable;

/**
 * 作者：by chris
 * 日期：on 2017/8/9 - 上午10:50.
 * 邮箱：android_cjx@163.com
 */

public class RespShareCode extends BaseModel implements Serializable {


    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RespShareCode{" +
                "data=" + data +
                '}';
    }

    public class Data implements Serializable{
        private String shareCode;
        private String shareUrl;

        public String getShareCode() {
            return shareCode;
        }

        public void setShareCode(String shareCode) {
            this.shareCode = shareCode;
        }

        public String getShareUrl() {
            return shareUrl;
        }

        public void setShareUrl(String shareUrl) {
            this.shareUrl = shareUrl;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "shareCode='" + shareCode + '\'' +
                    ", shareUrl='" + shareUrl + '\'' +
                    '}';
        }
    }
}
