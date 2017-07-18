package com.zt.nightrun.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chris.common.utils.ToastUtils;
import com.chris.common.view.BaseActivity;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.zt.nightrun.NightRunApplication;
import com.zt.nightrun.R;

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
}
