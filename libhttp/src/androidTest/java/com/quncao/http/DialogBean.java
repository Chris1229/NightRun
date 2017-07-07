package com.quncao.http;

import java.util.List;

/**
 * Created by jamespeng on 2016/10/18.
 * 首页弹窗
 */

public class DialogBean {


    /**
     * title : 达人首页
     * imgurl : http://192.168.30.6/hybrid/h5zip/raw/develop/app/dialog_guide.png
     * pageurl : http://www.baidu.com
     * starttime : 1476585000
     * endtime : 1477362600
     */

    private List<HomepageBean> homepage;
    /**
     * title : 场馆首页
     * imgurl : http://192.168.30.6/hybrid/h5zip/raw/develop/app/dialog_guide.png
     * pageurl : http://www.baidu.com
     * starttime : 1476585000
     * endtime : 1477362600
     */

    private List<StadiumBean> stadium;

    public List<HomepageBean> getHomepage() {
        return homepage;
    }

    public void setHomepage(List<HomepageBean> homepage) {
        this.homepage = homepage;
    }

    public List<StadiumBean> getStadium() {
        return stadium;
    }

    public void setStadium(List<StadiumBean> stadium) {
        this.stadium = stadium;
    }

    public static class HomepageBean {
        private String title;
        private String imgurl;
        private String pageurl;
        private String starttime;
        private String endtime;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public String getPageurl() {
            return pageurl;
        }

        public void setPageurl(String pageurl) {
            this.pageurl = pageurl;
        }

        public String getStarttime() {
            return starttime;
        }

        public void setStarttime(String starttime) {
            this.starttime = starttime;
        }

        public String getEndtime() {
            return endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }
    }

    public static class StadiumBean {
        private String title;
        private String imgurl;
        private String pageurl;
        private String starttime;
        private String endtime;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public String getPageurl() {
            return pageurl;
        }

        public void setPageurl(String pageurl) {
            this.pageurl = pageurl;
        }

        public String getStarttime() {
            return starttime;
        }

        public void setStarttime(String starttime) {
            this.starttime = starttime;
        }

        public String getEndtime() {
            return endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }
    }
}