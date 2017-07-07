package com.chris.common;

import android.app.Application;

/**
 * 作者：by chris
 * 日期：on 2017/6/28 - 下午9:08.
 * 邮箱：android_cjx@163.com
 */


import android.app.Activity;
import android.content.Context;

import java.util.Stack;


public class KeelApplication extends Application {


    protected static KeelApplication mDemoApp;

    private static Stack<Activity> activityStack;

    public static Context getApplicationConext() {
        return getApp().getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mDemoApp = this;
    }

    public static KeelApplication getApp() {
        return mDemoApp;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    /**
     * 添加Activity到栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（栈中最后一个压入的）
     */
    public Activity currentActivity() {
        return activityStack.lastElement();
    }

    /**
     * 结束当前Activity（栈中最后一个压入的）
     */
    @SuppressWarnings("unused")
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 根据指定类名查找Activity
     */
    @SuppressWarnings("unused")
    public Activity findActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                return activity;
            }
        }
        return null;
    }

    public void destroyActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity = null;
        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            if (!activity.isFinishing()) {
                activity.finish();
            }
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (Activity activity : activityStack) {
            if (activity != null && !activity.isFinishing()) {
                activity.finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void AppExit() {
        try {
            finishAllActivity();
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception ignored) {
        }
    }
}

