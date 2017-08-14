package com.zt.nightrun;

import com.baidu.mapapi.SDKInitializer;
import com.chris.common.KeelApplication;
import com.quncao.core.http.HttpRequestManager;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.zt.nightrun.wxapi.WxConstants;

/**
 * 作者：by chris
 * 日期：on 2017/6/28 - 下午5:09.
 * 邮箱：android_cjx@163.com
 */

public class NightRunApplication extends KeelApplication {

    private static NightRunApplication singleton;
    public static IWXAPI mWxApi;
    public int userId;
    public String name;
    public String mobile;
    public String nick;
    public String image;
    public int gender;

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        HttpRequestManager.getInstance().init(this);

        //初始化百度地图
        SDKInitializer.initialize(getApplicationContext());

        registToWX();

        //初始化友盟分享
        UMShareAPI.get(this);

        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad");
    }

    @Override
    public void AppExit() {
        super.AppExit();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }


    // Returns the application instance
    public static NightRunApplication getInstance() {
        return singleton;
    }

    public static IWXAPI getWxApi() {
        return mWxApi;
    }

    private void registToWX() {
        //AppConst.WEIXIN.APP_ID是指你应用在微信开放平台上的AppID，记得替换。
        mWxApi = WXAPIFactory.createWXAPI(this, WxConstants.APP_ID, false);
        // 将该app注册到微信
        mWxApi.registerApp(WxConstants.APP_ID);
    }
}
