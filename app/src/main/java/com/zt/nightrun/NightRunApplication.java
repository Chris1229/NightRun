package com.zt.nightrun;

import com.baidu.mapapi.SDKInitializer;
import com.chris.common.KeelApplication;
import com.quncao.core.http.HttpRequestManager;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.zt.db.MessageDao;
import com.zt.nightrun.db.DbManager;
import com.zt.nightrun.db.Message;
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
        Config.DEBUG =true;
        //初始化友盟分享
        UMShareAPI.get(this);
        PlatformConfig.setWeixin("wx2e98fdf5aa444270", "04fe3fc03104c7fc3c34efab9bdf761b");
        PlatformConfig.setQQZone("1106444430", "zJH59yibx9TK6F9h");
        PlatformConfig.setSinaWeibo("410499960", "5b81ac72c224429c6eeef9eea4ea7520","http://sns.whalecloud.com");

        //初始化数据库
        MessageDao messageDao = DbManager.getDaoSession(getApplicationContext()).getMessageDao();
        for(int i=11;i<=20;i++){
            messageDao.insertOrReplace(new Message(i,"Test"+i,"呵呵呵"+i));
        }
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
