package com.zt.nightrun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.error.VolleyError;
import com.chris.common.KeelApplication;
import com.chris.common.network.HttpRequestProxy;
import com.chris.common.utils.Md5Utils;
import com.chris.common.utils.SharedPreferencesUtil;
import com.chris.common.utils.ToastUtils;
import com.chris.common.view.BaseActivity;
import com.quncao.core.http.AbsHttpRequestProxy;
import com.zt.nightrun.NightRunApplication;
import com.zt.nightrun.R;
import com.zt.nightrun.model.req.ReqLogin;
import com.zt.nightrun.model.req.ReqRegister;
import com.zt.nightrun.model.req.ReqVcode;
import com.zt.nightrun.model.resp.RespLogin;
import com.zt.nightrun.model.resp.RespRegister;
import com.zt.nightrun.model.resp.RespVcode;
import com.zt.nightrun.model.resp.User;

import java.lang.ref.WeakReference;

/**
 * 作者：by chris
 * 日期：on 2017/6/29 - 下午4:11.
 * 邮箱：android_cjx@163.com
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener{

    private EditText etPhoneNum,etPassword,etIdentifyingCode;
    private TextView tvEnsure;
    private TextView tvLogin;
    private TextView tvWeixin;
    private TextView tvGetCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);
        initUI();

    }



    private void initUI(){
        etPhoneNum =(EditText)findViewById(R.id.phoneNumId);
        etIdentifyingCode =(EditText)findViewById(R.id.identifyingCodeId);
        etPassword =(EditText)findViewById(R.id.passWordId);
        tvEnsure =(TextView)findViewById(R.id.ensureId);
        tvLogin =(TextView)findViewById(R.id.tvLoginId);
        tvWeixin =(TextView)findViewById(R.id.weixinLoginId);
        tvGetCode =(TextView)findViewById(R.id.getCode);
        etPhoneNum.setOnClickListener(this);
        etPassword.setOnClickListener(this);
        etIdentifyingCode.setOnClickListener(this);
        tvEnsure.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
        tvWeixin.setOnClickListener(this);
        tvGetCode.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.phoneNumId:

                break;

            case R.id.identifyingCodeId:

                break;

            case R.id.passWordId:

                break;

            case R.id.ensureId:
                if(!TextUtils.isEmpty(etPhoneNum.getText().toString())){
                    if(!TextUtils.isEmpty(etPassword.getText().toString())){
                        if(!TextUtils.isEmpty(etIdentifyingCode.getText().toString())){
                            reqRegister();
                        }else{
                            ToastUtils.show(this,"请输入验证码");
                        }
                    }else{
                        ToastUtils.show(this,"请输入密码");
                    }
                }else{
                    ToastUtils.show(this,"请输入手机号");
                }

                break;

            case R.id.tvLoginId:
                startActivity(new Intent(this,LoginActivity.class));
                finish();
                break;

            case R.id.weixinLoginId:

                break;

            case R.id.getCode:
                /** 倒计时60秒，一次1秒 */
                CountDownTimer timer = new CountDownTimer(60*1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        // TODO Auto-generated method stub
                        tvGetCode.setText(millisUntilFinished/1000+"s");
                        tvGetCode.setClickable(false);
                    }

                    @Override
                    public void onFinish() {
                        tvGetCode.setText("重新获取");
                        tvGetCode.setClickable(true);
                    }
                }.start();
                reqVcode();
                break;
        }
    }

    private void reqRegister(){
        ReqRegister reqRegister = new ReqRegister();
        reqRegister.setMobile(etPhoneNum.getText().toString());
        reqRegister.setPassword(Md5Utils.Md5(etPassword.getText().toString()));
        reqRegister.setVcode(6666);
        HttpRequestProxy.get().create(reqRegister, new AbsHttpRequestProxy.RequestListener() {
            @Override
            public void onSuccess(Object response) {
                Log.i("info",response.toString());
                RespRegister respRegister =(RespRegister) response;
                if(respRegister.getData()!=null && respRegister.getData().getUser()!=null){
                    ToastUtils.show(RegisterActivity.this,"注册成功");
                    User user =respRegister.getData().getUser();

                    SharedPreferencesUtil.saveInteger(KeelApplication.getApplicationConext(),"userId",user.getId());
                    SharedPreferencesUtil.saveString(KeelApplication.getApplicationConext(),"mobile",user.getMobile());
                    SharedPreferencesUtil.saveString(KeelApplication.getApplicationConext(),"password",user.getPassword());
                    SharedPreferencesUtil.saveString(KeelApplication.getApplicationConext(),"nick",user.getNick());
                    SharedPreferencesUtil.saveString(KeelApplication.getApplicationConext(),"image",user.getImage());
                    SharedPreferencesUtil.saveInteger(KeelApplication.getApplicationConext(),"gender",user.getGender());
                    NightRunApplication.getInstance().userId =user.getId();
                    NightRunApplication.getInstance().mobile =user.getMobile();
                    NightRunApplication.getInstance().nick =user.getNick();
                    NightRunApplication.getInstance().image =user.getImage();
                    NightRunApplication.getInstance().gender =user.getGender();

                    startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                    finish();
                }else{
                    ToastUtils.show(RegisterActivity.this,respRegister.getMessage());
                }
            }

            @Override
            public void onFailed(VolleyError error) {
                Log.i("info",error.toString());
                ToastUtils.show(RegisterActivity.this,error.toString());
            }
        }).tag(this.toString()).build().excute();
    }

    private void reqVcode(){
        final ReqVcode reqVcode = new ReqVcode();
        reqVcode.setMobile(etPhoneNum.getText().toString());
        HttpRequestProxy.get().create(reqVcode, new AbsHttpRequestProxy.RequestListener() {
            @Override
            public void onSuccess(Object response) {
                RespVcode respVcode =(RespVcode)response;
                if(respVcode.getCode()==0){
                    etIdentifyingCode.setText("");
                }
            }

            @Override
            public void onFailed(VolleyError error) {

            }
        }).tag(this.toString()).build().excute();

    }
}

