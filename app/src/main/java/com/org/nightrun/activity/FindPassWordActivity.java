package com.org.nightrun.activity;

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

public class FindPassWordActivity extends BaseActivity implements View.OnClickListener {
    private EditText etPhoneNum, etNewPassword, etIdentifyingCode;
    private TextView tvEnsure;
    private TextView tvBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_findpassword);

        initUI();
    }

    private void initUI() {
        etPhoneNum = (EditText) findViewById(R.id.phoneNumId);
        etIdentifyingCode = (EditText) findViewById(R.id.identifyingCodeId);
        etNewPassword = (EditText) findViewById(R.id.newPassWordId);
        tvEnsure = (TextView) findViewById(R.id.ensureId);
        tvBack = (TextView) findViewById(R.id.back);

        etPhoneNum.setOnClickListener(this);
        etNewPassword.setOnClickListener(this);
        etIdentifyingCode.setOnClickListener(this);
        tvEnsure.setOnClickListener(this);
        tvBack.setOnClickListener(this);

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

                break;

            case R.id.back:
                finish();
                break;
        }
    }
}
