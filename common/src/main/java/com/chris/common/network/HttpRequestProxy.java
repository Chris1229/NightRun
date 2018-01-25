//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.chris.common.network;

import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.android.volley.error.NetworkError;
import com.android.volley.error.NoConnectionError;
import com.android.volley.error.ParseError;
import com.android.volley.error.ServerError;
import com.android.volley.error.TimeoutError;
import com.android.volley.error.VolleyError;
import com.chris.common.KeelApplication;
import com.chris.common.network.model.BaseModel;
import com.chris.common.utils.DeviceUuidFactory;
import com.chris.common.utils.NetworkUtil;
import com.chris.common.utils.SDeviceUtil;
import com.chris.common.utils.SharedPreferencesUtil;
import com.quncao.core.http.AbsHttpRequestProxy;
import com.quncao.core.http.annotation.HttpReqParam;

import java.util.HashMap;
import java.util.TreeMap;

public class HttpRequestProxy extends AbsHttpRequestProxy {
    private static final int HTTP_ERROR_TYPE_NETWORK = 1;
    private static final int HTTP_ERROR_TYPE_SERVER = 2;
    private static final int HTTP_ERROR_TYPE_TIMEOUT = 3;
    private static final int HTTP_ERROR_TYPE_PARSE = 4;
    private static final int HTTP_ERROR_TYPE_NOCONNECTION = 5;
    private static final int HTTP_ERROR_TYPE_OTHER = 6;

    public HttpRequestProxy(HttpRequestProxy.Builder builder) {
        this.listener = builder.listener;
        this.requestParamBody = builder.requestParamBody;
        this.tag = builder.tag;
        this.cache = builder.cache;
        HttpReqParam annotation = (HttpReqParam)this.requestParamBody.getClass().getAnnotation(HttpReqParam.class);
        this.protocal = annotation.protocal();
        this.clazz = annotation.responseType();
        this.method = annotation.method();
        this.format = annotation.format();
    }

    public HttpRequestProxy() {

    }

    public static HttpRequestProxy.Builder get() {
        return new HttpRequestProxy.Builder();
    }

    @Override
    protected String getDomain() {
//        return EAPIConstants.getUserUrl();
        return "http://www.carnote.cn/yxsq/";
    }

    @Override
    protected TreeMap<String, String> getCommonParamMap() {
//        String version = LarkUtils.getVersion(KeelApplication.getApplicationConext());
        TreeMap commonParam = new TreeMap();
        commonParam.put("clintInfo", getClientInfo());
        if(!TextUtils.isEmpty(SharedPreferencesUtil.getString(KeelApplication.getApplicationConext(),"sessionid"))){
            commonParam.put("sessionid", SharedPreferencesUtil.getString(KeelApplication.getApplicationConext(),"sessionid"));
        }
//        commonParam.put("platformType", "2");
//        commonParam.put("ver", version);
        return commonParam;
    }

    protected TreeMap<String, String> getHeader() {
        TreeMap heads = new TreeMap();
        heads.put("Connection", "Close");
        heads.put("Content-Type", "application/json;Charset=UTF-8");
        heads.put("Accept-Language", "zh,zh-cn");
        heads.put("Accept-Encoding", "gzip,deflate");
//        heads.put("APPVersion", "2.0");
//        heads.put("appChannel", LarkChannelUtil.getChannel(KeelApplication.getApplicationConext()));
//        if(!TextUtils.isEmpty(DBManager.getInstance().getCookie())) {
//            heads.put("userCookiesName", DBManager.getInstance().getCookie());
//        }
//
//        heads.put("sign", StringUtils.md5((this.getRequestJsonString() + "quanyan2016").getBytes()));
        return heads;
    }

    public static <T extends BaseModel> boolean isRet(T model) {
        if(model != null && model.getErrcode() == 609) {
//            ReqCookies reqCookies = new ReqCookies();
//            reqCookies.setDsn(DBManager.getInstance().getDsn());
//            reqCookies.setAppid("");
//            reqCookies.setDev(2);
//            reqCookies.setChannel("");
//            reqCookies.setOs(Build.MODEL + VERSION.RELEASE);
//            reqCookies.setScreen_resolution(DBManager.getInstance().getScreen());
//            get().create(reqCookies, new RequestListener() {
//                public void onSuccess(Object response) {
//                    RespUserCookiesName setCookies = (RespUserCookiesName)response;
//                    DBManager.getInstance().setCookie(setCookies.getData().getUserCookiesName());
//                }
//
//                public void onFailed(VolleyError volleyError) {
//                }
//            }).tag("HttpRequestProxy").build().excute();
        }

        return model != null && model.getErrcode() == 200 && model.isRet();
    }

    protected void reportNetworkError(String url, String paramJson, VolleyError error) {
        try {
            HashMap params = new HashMap();
            params.put("url", url);
            params.put("parameters", paramJson);
            params.put("code", error.networkResponse.statusCode + "");
            params.put("message", error.getMessage());
            params.put("type", this.getNetworkErrorType(error) + "");
//            StatisticsUtils.onEvent("http_request_error", params);
        } catch (Exception var5) {
            ;
        }

    }

    private int getNetworkErrorType(VolleyError error) {
        return error instanceof NoConnectionError ?5:(error instanceof NetworkError ?1:(error instanceof ServerError ?2:(error instanceof TimeoutError ?3:(error instanceof ParseError ?4:6))));
    }

    public static class Builder<T> {
        private RequestListener<T> listener;
        private Object requestParamBody;
        private String tag;
        private boolean cache;

        public Builder() {
        }

        public HttpRequestProxy.Builder create(Object requestParamBody, RequestListener<T> listener) {
            this.listener = listener;
            this.requestParamBody = requestParamBody;
            return this;
        }

        public HttpRequestProxy.Builder tag(String tag) {
            this.tag = tag;
            return this;
        }

        public HttpRequestProxy.Builder cache(boolean isCache) {
            this.cache = isCache;
            return this;
        }

        public HttpRequestProxy build() {
            return new HttpRequestProxy(this);
        }
    }

    public static String getClientInfo() {
        String uniqid = getUniqId();
        DisplayMetrics displayMetrics = KeelApplication.getApplicationConext().getResources().getDisplayMetrics();
        String screen = String.format("%d*%d", displayMetrics.widthPixels, displayMetrics.heightPixels);
        String imei = SDeviceUtil.getImei(KeelApplication.getApp().getApplicationContext());

        StringBuilder result = new StringBuilder();
        result.append("{\"model\":").append("\"").append(SDeviceUtil.getDeviceModel()).append("\",");
        if(!TextUtils.isEmpty(imei)){
            result.append("\"idfa\":").append("\"").append(imei).append("\",");
        }
        result.append("\"os\":").append("\"").append(Build.VERSION.RELEASE).append("\",");
        result.append("\"screen\":").append("\"").append(screen).append("\",");
        result.append("\"uniqId\":").append("\"").append(uniqid).append("\",");
        if(NetworkUtil.isUsingMobileNetwork()){
            result.append("\"networkState\":").append("\"").append("4").append("\",");
        }else if(NetworkUtil.isWIFIAvailable(KeelApplication.getApplicationConext())){
            result.append("\"networkState\":").append("\"").append("6").append("\",");
        }
        return result.toString();
    }


    private static String getUniqId() {
        DeviceUuidFactory uuidFactory = new DeviceUuidFactory(KeelApplication.getApplicationConext());
        return uuidFactory.getDeviceUuid().toString();
    }

}
