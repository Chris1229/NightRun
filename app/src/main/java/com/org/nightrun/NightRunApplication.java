package com.org.nightrun;

import com.chris.common.KeelApplication;
import com.quncao.core.http.HttpRequestManager;

/**
 * 作者：by chris
 * 日期：on 2017/6/28 - 下午5:09.
 * 邮箱：android_cjx@163.com
 */

public class NightRunApplication extends KeelApplication {

    private static NightRunApplication singleton;

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        HttpRequestManager.getInstance().init(this);
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
}
