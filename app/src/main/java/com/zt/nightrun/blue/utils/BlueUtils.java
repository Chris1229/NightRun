package com.zt.nightrun.blue.utils;

import android.text.TextUtils;

import com.chris.common.utils.SharedPreferencesUtil;
import com.zt.nightrun.NightRunApplication;
import com.zt.nightrun.blue.model.BlueFunction;

/**
 * 作者：by chris
 * 日期：on 2017/9/5 - 上午10:08.
 * 邮箱：android_cjx@163.com
 */

public class BlueUtils {

    public static final String BLUE_WARMING_OPEN ="00151000000";
    public static final String BLUE_WARMING_CLOSE ="00151000001";

    public static final String BLUE_LIGHT_COLOR_1 ="00181002000";
    public static final String BLUE_LIGHT_COLOR_2 ="00181002001";
    public static final String BLUE_LIGHT_COLOR_3 ="00181002002";
    public static final String BLUE_LIGHT_COLOR_4 ="00181002003";
    public static final String BLUE_LIGHT_COLOR_5 ="00181002004";
    public static final String BLUE_LIGHT_COLOR_6 ="00181002005";
    public static final String BLUE_LIGHT_COLOR_7 ="00181002006";
    public static final String BLUE_LIGHT_COLOR_8 ="00181002007";
    public static final String BLUE_LIGHT_COLOR_9 ="00181002008";
    public static final String BLUE_LIGHT_COLOR_0 ="00181002009";

    public static final String BLUE_LIGHT_BRIGHT_ALL ="00151001000";
    public static final String BLUE_LIGHT_BRIGHT_CENTER ="00151001001";
    public static final String BLUE_LIGHT_BRIGHT_SMALL ="00151001002";

    public static final String BLUE_LIGHT_DODGE_FAST ="000";
    public static final String BLUE_LIGHT_DODGE_SLOW ="001";
    public static final String BLUE_LIGHT_DODGE_NO ="002";

    public static final String BLUE_LIGHT_MOVE_ALL ="00151003000";
    public static final String BLUE_LIGHT_MOVE_INTERMISSION ="00151003001";
    public static final String BLUE_LIGHT_MOVE_NO ="00151003002";


    public static BlueFunction getFunction(){
        BlueFunction function = new BlueFunction();
        if(!TextUtils.isEmpty(SharedPreferencesUtil.getString(NightRunApplication.getApplicationConext(),"switchCraft"))){
            function.setSwitchCraft(SharedPreferencesUtil.getString(NightRunApplication.getApplicationConext(),"switchCraft"));
        }

        if(!TextUtils.isEmpty(SharedPreferencesUtil.getString(NightRunApplication.getApplicationConext(),"lightColor"))){
            function.setLightColor(SharedPreferencesUtil.getString(NightRunApplication.getApplicationConext(),"lightColor"));
        }

        if(!TextUtils.isEmpty(SharedPreferencesUtil.getString(NightRunApplication.getApplicationConext(),"lightBright"))){
            function.setLightBright(SharedPreferencesUtil.getString(NightRunApplication.getApplicationConext(),"lightBright"));
        }

        if(!TextUtils.isEmpty(SharedPreferencesUtil.getString(NightRunApplication.getApplicationConext(),"lightDodge"))){
            function.setLightDodge(SharedPreferencesUtil.getString(NightRunApplication.getApplicationConext(),"lightDodge"));
        }

        if(!TextUtils.isEmpty(SharedPreferencesUtil.getString(NightRunApplication.getApplicationConext(),"lightMove"))){
            function.setLightMove(SharedPreferencesUtil.getString(NightRunApplication.getApplicationConext(),"lightMove"));
        }
        return function;
    }
}
