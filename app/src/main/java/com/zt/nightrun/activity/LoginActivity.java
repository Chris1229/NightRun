package com.zt.nightrun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chris.common.view.BaseActivity;
import com.zt.nightrun.R;

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

                break;
        }
    }
}
