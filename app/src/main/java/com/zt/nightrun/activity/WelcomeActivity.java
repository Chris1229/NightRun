package com.zt.nightrun.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.error.VolleyError;
import com.chris.common.network.HttpRequestProxy;
import com.chris.common.utils.SharedPreferencesUtil;
import com.google.gson.Gson;
import com.quncao.core.http.AbsHttpRequestProxy;
import com.zt.nightrun.NightRunApplication;
import com.zt.nightrun.model.req.ReqInit;
import com.zt.nightrun.model.resp.RespInit;

/**
 * 作者：by chris
 * 日期：on 2017/7/30 - 上午9:45.
 * 邮箱：android_cjx@163.com
 */

public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        SharedPreferencesUtil.saveString(this,"sessionid","0492E2E0FA66AA04F3618A615AD57A4E");
//        NightRunApplication.getInstance().sessionid =SharedPreferencesUtil.getString(this,"sessionid");
        init();

        int userId = SharedPreferencesUtil.getInteger(NightRunApplication.getInstance().getApplicationContext(),"userId");

        if(userId>0){
            startActivity(new Intent(this,MainActivity.class));
            NightRunApplication.getInstance().userId =SharedPreferencesUtil.getInteger(NightRunApplication.getInstance().getApplicationContext(),"userId");
            NightRunApplication.getInstance().name =SharedPreferencesUtil.getString(NightRunApplication.getInstance().getApplicationContext(),"name");
            NightRunApplication.getInstance().mobile =SharedPreferencesUtil.getString(NightRunApplication.getInstance().getApplicationContext(),"mobile");
            NightRunApplication.getInstance().nick =SharedPreferencesUtil.getString(NightRunApplication.getInstance().getApplicationContext(),"nick");
            NightRunApplication.getInstance().image =SharedPreferencesUtil.getString(NightRunApplication.getInstance().getApplicationContext(),"image");
            NightRunApplication.getInstance().gender =SharedPreferencesUtil.getInteger(NightRunApplication.getInstance().getApplicationContext(),"gender");

            startActivity(new Intent(this,MainActivity.class));

        }else{
            startActivity(new Intent(this,LoginActivity.class));
        }

        finish();
    }

    private void init(){
        final ReqInit reqInit = new ReqInit();
        String mobile =SharedPreferencesUtil.getString(NightRunApplication.getInstance().getApplicationContext(),"mobile");
        String password =SharedPreferencesUtil.getString(NightRunApplication.getInstance().getApplicationContext(),"password");
        if(!TextUtils.isEmpty(mobile)){
            reqInit.setMobile(mobile);
        }

        if(!TextUtils.isEmpty(password)){
            reqInit.setPassword(password);
        }

        HttpRequestProxy.get().create(reqInit, new AbsHttpRequestProxy.RequestListener() {
            @Override
            public void onSuccess(Object response) {
                Log.i("info",response.toString());
                RespInit respInit =(RespInit)response;
                if(respInit.getData()!=null){
                    if(!TextUtils.isEmpty(respInit.getData().getSessionid())){
                        SharedPreferencesUtil.saveString(WelcomeActivity.this,"sessionid",respInit.getData().getSessionid());
                    }
                }
                Log.i("info===",reqInit.toString());
            }

            @Override
            public void onFailed(VolleyError error) {

            }
        }).tag(this.toString()).build().excute();
    }
}
