package com.zt.nightrun.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
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
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.zt.nightrun.NightRunApplication;
import com.zt.nightrun.R;
import com.zt.nightrun.model.req.ReqLogin;
import com.zt.nightrun.model.resp.RespLogin;
import com.zt.nightrun.model.resp.User;

import java.util.List;

/**
 * 作者：by chris
 * 日期：on 2017/6/29 - 下午1:42.
 * 邮箱：android_cjx@163.com
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private EditText etPhoneNum,etPassword;
    private TextView tvEnsureLogin;
    private TextView tvRegister,tvFindPassword;
    private TextView tvWeixin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        initUI();
    }

    private void initUI(){
        etPhoneNum =(EditText)findViewById(R.id.phoneNumId);
        etPassword =(EditText)findViewById(R.id.passWordId);
        tvEnsureLogin =(TextView)findViewById(R.id.ensureLoginId);
        tvFindPassword =(TextView)findViewById(R.id.tvFindPasswordId);
        tvRegister =(TextView)findViewById(R.id.tvRegisterId);
        tvWeixin =(TextView)findViewById(R.id.weixinLoginId);

        etPhoneNum.setOnClickListener(this);
        etPassword.setOnClickListener(this);
        tvEnsureLogin.setOnClickListener(this);
        tvFindPassword.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
        tvWeixin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.phoneNumId:

                break;

            case R.id.passWordId:

                break;

            case R.id.ensureLoginId:

                if(!TextUtils.isEmpty(etPhoneNum.getText().toString())){
                    if(!TextUtils.isEmpty(etPassword.getText().toString())){
                        reqLogin();
                    }else{
                        ToastUtils.show(this,"请输入密码");
                    }
                }else{
                    ToastUtils.show(this,"请输入手机号");
                }

                break;

            case R.id.tvRegisterId:
                startActivity(new Intent(this,RegisterActivity.class));
                break;

            case R.id.tvFindPasswordId:
                startActivity(new Intent(this,FindPassWordActivity.class));
                break;

            case R.id.weixinLoginId:
                if (!getIsWeixinInstall()) {
                    ToastUtils.show(this,"您还未安装微信客户端");
                    return;
                }
                final SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
                req.state = "diandi_wx_login";
                NightRunApplication.getWxApi().sendReq(req);
                break;
        }
    }

    private boolean getIsWeixinInstall() {
        final PackageManager packageManager = NightRunApplication.getInstance().getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    private void reqLogin(){
        final ReqLogin reqLogin = new ReqLogin();
        reqLogin.setMobile(etPhoneNum.getText().toString());
        reqLogin.setPassword(Md5Utils.Md5(etPassword.getText().toString()));
        HttpRequestProxy.get().create(reqLogin, new AbsHttpRequestProxy.RequestListener() {
            @Override
            public void onSuccess(Object response) {

                Log.i("info",response.toString());
                RespLogin respLogin =(RespLogin)response;
                if(respLogin.getData()!=null && respLogin.getData().getUser()!=null){
                    ToastUtils.show(LoginActivity.this,"登录成功");
                    User user =respLogin.getData().getUser();
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

                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    finish();
                }else{
                    ToastUtils.show(LoginActivity.this,respLogin.getMessage());
                }
            }

            @Override
            public void onFailed(VolleyError error) {
                Log.i("info",error.toString());
                ToastUtils.show(LoginActivity.this,error.toString());
            }
        }).tag(this.toString()).build().excute();
    }
}
