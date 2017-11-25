package com.zt.nightrun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
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
import com.zt.nightrun.model.req.ReqFindPwd;
import com.zt.nightrun.model.req.ReqRegister;
import com.zt.nightrun.model.req.ReqVcode;
import com.zt.nightrun.model.resp.RespFindPwd;
import com.zt.nightrun.model.resp.RespVcode;
import com.zt.nightrun.model.resp.User;

/**
 * 作者：by chris
 * 日期：on 2017/6/29 - 下午4:11.
 * 邮箱：android_cjx@163.com
 */

public class FindPassWordActivity extends BaseActivity implements View.OnClickListener {
    private EditText etPhoneNum, etNewPassword, etIdentifyingCode;
    private TextView tvEnsure;
    private TextView tvBack;
    private TextView tvGetCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_findpassword);

        initUI();

        if(!TextUtils.isEmpty(NightRunApplication.getInstance().mobile)){
            etPhoneNum.setText(NightRunApplication.getInstance().mobile);
            etPhoneNum.setSelection(etPhoneNum.getText().length());
        }
    }

    private void initUI() {
        etPhoneNum = (EditText) findViewById(R.id.phoneNumId);
        etIdentifyingCode = (EditText) findViewById(R.id.identifyingCodeId);
        etNewPassword = (EditText) findViewById(R.id.newPassWordId);
        tvEnsure = (TextView) findViewById(R.id.ensureId);
        tvBack = (TextView) findViewById(R.id.back);
        tvGetCode =(TextView)findViewById(R.id.getCode);
        etPhoneNum.setOnClickListener(this);
        etNewPassword.setOnClickListener(this);
        etIdentifyingCode.setOnClickListener(this);
        tvEnsure.setOnClickListener(this);
        tvBack.setOnClickListener(this);
        tvGetCode.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.phoneNumId:

                break;

            case R.id.identifyingCodeId:

                break;

            case R.id.newPassWordId:

                break;

            case R.id.ensureId:
                if(!TextUtils.isEmpty(etPhoneNum.getText().toString())){
                    if(!TextUtils.isEmpty(etNewPassword.getText().toString())){
                        if(!TextUtils.isEmpty(etIdentifyingCode.getText().toString())){
                            reqRegister();
                        }else{
                            ToastUtils.show(this,"请输入验证码");
                        }
                    }else{
                        ToastUtils.show(this,"请输入新密码");
                    }
                }else{
                    ToastUtils.show(this,"请输入手机号");
                }

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

            case R.id.back:
                finish();
                break;
        }
    }

    private void reqRegister(){
        final ReqFindPwd reqFindPwd = new ReqFindPwd();
        reqFindPwd.setMobile(etPhoneNum.getText().toString());
        reqFindPwd.setPassword(Md5Utils.Md5(etNewPassword.getText().toString()));
        reqFindPwd.setVcode(6666);
        HttpRequestProxy.get().create(reqFindPwd, new AbsHttpRequestProxy.RequestListener() {
            @Override
            public void onSuccess(Object response) {
                Log.i("info",response.toString());
                RespFindPwd respFindPwd =(RespFindPwd)response;
                if(respFindPwd.getData()!=null && respFindPwd.getData().getUser()!=null){
                    ToastUtils.show(FindPassWordActivity.this,"设置新密码成功");
                    User user =respFindPwd.getData().getUser();
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

                    startActivity(new Intent(FindPassWordActivity.this,MainActivity.class));
                    finish();

                }else{
                    ToastUtils.show(FindPassWordActivity.this,respFindPwd.getMessage());
                }
            }

            @Override
            public void onFailed(VolleyError error) {
                Log.i("info",error.toString());
                ToastUtils.show(FindPassWordActivity.this,error.toString());
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
