package com.org.nightrun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chris.common.view.BaseActivity;
import com.org.nightrun.R;

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

        etPhoneNum.setOnClickListener(this);
        etPassword.setOnClickListener(this);
        etIdentifyingCode.setOnClickListener(this);
        tvEnsure.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
        tvWeixin.setOnClickListener(this);

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

                break;

            case R.id.tvLoginId:
                startActivity(new Intent(this,LoginActivity.class));
                finish();
                break;

            case R.id.weixinLoginId:

                break;
        }
    }
}

