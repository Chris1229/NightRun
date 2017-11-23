package com.zt.nightrun.activity;

import android.os.Bundle;

import com.chris.common.view.BaseActivity;
import com.zt.nightrun.R;

/**
 * 作者：by chris
 * 日期：on 2017/8/14 - 下午5:19.
 * 邮箱：android_cjx@163.com
 */

public class MemberSignActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_sign,true);

        initView();
    }

    private void initView(){
        setTitle("签到");


    }
}
