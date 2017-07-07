package com.org.nightrun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

import com.chris.common.view.BaseActivity;
import com.org.nightrun.R;

/**
 * 作者：by chris
 * 日期：on 2017/6/30 - 下午4:14.
 * 邮箱：android_cjx@163.com
 */

public class DeviceDetailsActivity extends BaseActivity implements View.OnClickListener,CheckBox.OnCheckedChangeListener{

    private RelativeLayout mShouQuanLayout,mLocationLayout,mGuijiLayout,mWramTimeLayout;
    private CheckBox mRadioWarming,mRadioUnband,mRadioZhendong,mRadioDengguang,mRadioFengming;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_device_detail_layout,true);

        initUI();
    }

    private void initUI(){
        setTitle("汤米的设备");
        mShouQuanLayout =(RelativeLayout)findViewById(R.id.shouquanId);
        mLocationLayout =(RelativeLayout)findViewById(R.id.locationId);
        mGuijiLayout =(RelativeLayout)findViewById(R.id.historyGuiJiId);
        mWramTimeLayout =(RelativeLayout)findViewById(R.id.wramingTimeId);
        mRadioWarming =(CheckBox)findViewById(R.id.check_wramimg);
        mRadioUnband =(CheckBox)findViewById(R.id.check_unband);
        mRadioZhendong =(CheckBox)findViewById(R.id.check_zhendong);
        mRadioDengguang =(CheckBox)findViewById(R.id.check_dengguang);
        mRadioFengming =(CheckBox)findViewById(R.id.check_fengming);

        mShouQuanLayout.setOnClickListener(this);
        mLocationLayout.setOnClickListener(this);
        mGuijiLayout.setOnClickListener(this);
        mWramTimeLayout.setOnClickListener(this);
        mRadioWarming.setOnCheckedChangeListener(this);
        mRadioUnband.setOnCheckedChangeListener(this);
        mRadioZhendong.setOnCheckedChangeListener(this);
        mRadioDengguang.setOnCheckedChangeListener(this);
        mRadioFengming.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.shouquanId:
                startActivity(new Intent(this,AuthorityMemberListActivity.class));
                break;

            case R.id.locationId:

                break;

            case R.id.historyGuiJiId:

                break;

            case R.id.wramingTimeId:

                break;

        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.check_wramimg:

                break;

            case R.id.check_unband:

                break;

            case R.id.check_zhendong:

                break;

            case R.id.check_dengguang:

                break;

            case R.id.check_fengming:

                break;

        }
    }
}
