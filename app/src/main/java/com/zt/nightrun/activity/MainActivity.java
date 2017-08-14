package com.zt.nightrun.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.error.VolleyError;
import com.chris.common.image.ImageUtils;
import com.chris.common.network.HttpRequestProxy;
import com.chris.common.utils.ToastUtils;
import com.chris.common.view.BaseActivity;
import com.quncao.core.http.AbsHttpRequestProxy;
import com.zt.nightrun.NightRunApplication;
import com.zt.nightrun.R;
import com.zt.nightrun.fragment.DeviceFragment;
import com.zt.nightrun.fragment.FindFragment;
import com.zt.nightrun.fragment.MallFragment;
import com.zt.nightrun.fragment.MineFragment;
import com.zt.nightrun.fragment.TeamFragment;
import com.zt.nightrun.model.req.ReqActiveDevice;
import com.zt.nightrun.model.req.ReqDeviceList;
import com.zt.nightrun.model.resp.RespActiveDevice;
import com.zt.nightrun.model.resp.RespDeviceList;
import com.zt.nightrun.zxing.activity.CaptureActivity;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener,View.OnClickListener{
    private long exitTime = 0;
    private RelativeLayout mTopLinar;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private RadioGroup mRadioGroup;
    private DeviceFragment deviceFragment;
    private TeamFragment teamFragment;
    private FindFragment findFragment;
    private MallFragment mallFragment;
    private MineFragment mineFragment;
    private ImageView headImg;
    private TextView tvNick;
    private ImageView messageImg;
    private ImageView addImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();

    }

    private void initUI(){
        fragmentManager = getSupportFragmentManager();
        mRadioGroup =(RadioGroup)findViewById(R.id.rg_radio);
        mTopLinar =(RelativeLayout)findViewById(R.id.topLinear);
        headImg =(ImageView)findViewById(R.id.headImageId);
        tvNick =(TextView)findViewById(R.id.tvNick);
        messageImg =(ImageView)findViewById(R.id.messageId);
        addImg =(ImageView)findViewById(R.id.addPeopleId);
        headImg.setOnClickListener(this);
        messageImg.setOnClickListener(this);
        addImg.setOnClickListener(this);
        mRadioGroup.setOnCheckedChangeListener(this);
        mRadioGroup.check(R.id.radioBtn_device);
        if(!TextUtils.isEmpty(NightRunApplication.getInstance().nick)){
            tvNick.setText(NightRunApplication.getInstance().nick);
        }

        ImageUtils.loadCircleImage(this,"http://img.qq1234.org/uploads/allimg/141010/3_141010111902_4.png",R.mipmap.default_avtar,headImg);
    }
    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        fragmentTransaction = fragmentManager.beginTransaction();
        hideFragments(fragmentTransaction);
        switch (checkedId){
            case R.id.radioBtn_device:
                if(!mTopLinar.isShown()){
                    mTopLinar.setVisibility(View.VISIBLE);
                }
                if (deviceFragment == null) {
                    deviceFragment = new DeviceFragment();
                    fragmentTransaction.add(R.id.fragment_id, deviceFragment);
                } else {
                    fragmentTransaction.show(deviceFragment);
                }
                break;

            case R.id.radioBtn_team:
                if(!mTopLinar.isShown()){
                    mTopLinar.setVisibility(View.VISIBLE);
                }
                if (teamFragment == null) {
                    teamFragment = new TeamFragment();
                    fragmentTransaction.add(R.id.fragment_id, teamFragment);
                } else {
                    fragmentTransaction.show(teamFragment);
                }
                break;

            case R.id.radioBtn_find:
                if(mTopLinar.isShown()){
                    mTopLinar.setVisibility(View.GONE);
                }
                if (findFragment == null) {
                    findFragment = new FindFragment();
                    fragmentTransaction.add(R.id.fragment_id, findFragment);
                } else {
                    fragmentTransaction.show(findFragment);
                }
                break;

            case R.id.radioBtn_mall:
                if(mTopLinar.isShown()){
                    mTopLinar.setVisibility(View.GONE);
                }
                if (mallFragment == null) {
                    mallFragment = new MallFragment();
                    fragmentTransaction.add(R.id.fragment_id, mallFragment);
                } else {
                    fragmentTransaction.show(mallFragment);
                }
                break;

            case R.id.radioBtn_mine:
                if(mTopLinar.isShown()){
                    mTopLinar.setVisibility(View.GONE);
                }
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                    fragmentTransaction.add(R.id.fragment_id, mineFragment);
                } else {
                    fragmentTransaction.show(mineFragment);
                }
                break;
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    //隐藏所有的Fragment,避免fragment混乱
    private void hideFragments(FragmentTransaction transaction) {
        if (deviceFragment != null) {
            transaction.hide(deviceFragment);
        }
        if (teamFragment != null) {
            transaction.hide(teamFragment);
        }
        if (findFragment != null) {
            transaction.hide(findFragment);
        }
        if (mallFragment != null) {
            transaction.hide(mallFragment);
        }
        if (mineFragment != null) {
            transaction.hide(mineFragment);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //头像
            case R.id.headImageId:

                break;
            //消息
            case R.id.messageId:
                startActivity(new Intent(this,MessageActivity.class));
                break;
            //添加
            case R.id.addPeopleId:
                startActivity(new Intent(this,CaptureActivity.class));
                break;

        }
    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastUtils.show(this, "再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            NightRunApplication.getInstance().finishAllActivity();
            NightRunApplication.getInstance().AppExit();
        }
    }

}
