package com.zt.nightrun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

import com.android.volley.error.VolleyError;
import com.chris.common.network.HttpRequestProxy;
import com.chris.common.share.IShareCallback;
import com.chris.common.share.ShareDialog;
import com.chris.common.share.ShareModel;
import com.chris.common.share.ShareUtils;
import com.chris.common.utils.ToastUtils;
import com.chris.common.view.ActionItem;
import com.chris.common.view.BaseActivity;
import com.chris.common.view.CustomDialog;
import com.quncao.core.http.AbsHttpRequestProxy;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.zt.nightrun.R;
import com.zt.nightrun.model.req.ReqActiveDevice;
import com.zt.nightrun.model.req.ReqShareCode;
import com.zt.nightrun.model.resp.RespActiveDevice;
import com.zt.nightrun.model.resp.RespShareCode;
import com.zt.nightrun.view.CustomEditDialog;
import com.zt.nightrun.zxing.activity.CaptureActivity;


/**
 * 作者：by chris
 * 日期：on 2017/6/30 - 下午4:14.
 * 邮箱：android_cjx@163.com
 */

public class DeviceDetailsActivity extends BaseActivity implements View.OnClickListener, CheckBox.OnCheckedChangeListener {

    private RelativeLayout mShouQuanLayout, mLocationLayout, mGuijiLayout, mWramTimeLayout;
    private CheckBox mRadioWarming, mRadioUnband, mRadioZhendong, mRadioDengguang, mRadioFengming;
    private ShareUtils shareUtils;
    private String uid;
    private int deviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_device_detail_layout, true);
        Intent intent =getIntent();
        uid =intent.getStringExtra("uid");
        deviceId =intent.getIntExtra("deviceId",0);
        shareUtils = new ShareUtils(this);
        initUI();
    }

    private void initUI() {
        setTitle("汤米的设备");
        setRightImage(R.mipmap.share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reqShareCode(deviceId);

            }
        });
        mShouQuanLayout = (RelativeLayout) findViewById(R.id.shouquanId);
        mLocationLayout = (RelativeLayout) findViewById(R.id.locationId);
        mGuijiLayout = (RelativeLayout) findViewById(R.id.historyGuiJiId);
//        mWramTimeLayout =(RelativeLayout)findViewById(R.id.wramingTimeId);
        mRadioWarming = (CheckBox) findViewById(R.id.check_wramimg);
        mRadioUnband = (CheckBox) findViewById(R.id.check_unband);
        mRadioZhendong = (CheckBox) findViewById(R.id.check_zhendong);
        mRadioDengguang = (CheckBox) findViewById(R.id.check_dengguang);
        mRadioFengming = (CheckBox) findViewById(R.id.check_fengming);

        mShouQuanLayout.setOnClickListener(this);
        mLocationLayout.setOnClickListener(this);
        mGuijiLayout.setOnClickListener(this);
//        mWramTimeLayout.setOnClickListener(this);
        mRadioWarming.setOnCheckedChangeListener(this);
        mRadioUnband.setOnCheckedChangeListener(this);
        mRadioZhendong.setOnCheckedChangeListener(this);
        mRadioDengguang.setOnCheckedChangeListener(this);
        mRadioFengming.setOnCheckedChangeListener(this);
    }

    IShareCallback iShareCallback = new IShareCallback() {
        @Override
        public void onSuccess() {
            ToastUtils.show(getApplicationContext(), "分享成功");
        }

        @Override
        public void onFailed() {
            ToastUtils.show(getApplicationContext(), "分享失败");
        }

        @Override
        public void onCancel() {
            ToastUtils.show(getApplicationContext(), "取消分享");
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shouquanId:
                startActivity(new Intent(this, AuthorityMemberListActivity.class).putExtra("deviceId",deviceId));
                break;

            case R.id.locationId:
                startActivity(new Intent(this, LocationActivity.class));
                break;

            case R.id.historyGuiJiId:

                break;

//            case R.id.wramingTimeId:
//                CustomEditDialog dialog = new CustomEditDialog(this,"预警定位时间","分钟", InputType.TYPE_CLASS_NUMBER, new CustomEditDialog.OnOkListener() {
//                    @Override
//                    public void onOkClick(CustomEditDialog dialog, String string) {
//                        if(!TextUtils.isEmpty(string)){
//                            dialog.dismiss();
//                        }else{
//                            ToastUtils.show(DeviceDetailsActivity.this,"请输入有效时间");
//                        }
//                    }
//                });
//
//                dialog.show();
//
//                break;

        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.check_wramimg:

                break;

            case R.id.check_unband:
                if(!isChecked){
                    final CustomDialog dialog = new CustomDialog(this, new CustomDialog.OnClickListener() {
                        @Override
                        public void onLeftClick() {
                            mRadioUnband.setChecked(true);
                        }

                        @Override
                        public void onRightClick() {
                            showLoadingDialog();
                            reqActiveDevice(uid);
                        }
                    });
                    dialog.setContent("确定解绑本设备");
                    dialog.show();
                }

                break;

            case R.id.check_zhendong:

                break;

            case R.id.check_dengguang:

                break;

            case R.id.check_fengming:

                break;

        }
    }

    private void reqShareCode(int deviceId){
        final ReqShareCode reqShareCode = new ReqShareCode();
        reqShareCode.setDeviceId(deviceId);
        HttpRequestProxy.get().create(reqShareCode, new AbsHttpRequestProxy.RequestListener() {
            @Override
            public void onSuccess(Object response) {

                Log.i("info",response.toString());
                final RespShareCode respShareCode =(RespShareCode) response;
                if(respShareCode.getData()!=null){
                    if(!TextUtils.isEmpty(respShareCode.getData().getShareUrl())){

                        ShareDialog shareDialog = new ShareDialog(DeviceDetailsActivity.this, "分享至", 1);
                        shareDialog.addAction(new ActionItem(DeviceDetailsActivity.this, "微信", R.mipmap.icon_weixin));
                        shareDialog.addAction(new ActionItem(DeviceDetailsActivity.this, "朋友圈", R.mipmap.icon_friendship));
                        shareDialog.addAction(new ActionItem(DeviceDetailsActivity.this, "QQ", R.mipmap.icon_qq1));
                        shareDialog.addAction(new ActionItem(DeviceDetailsActivity.this, "新浪微博", R.mipmap.icon_weibo_1));
                        shareDialog.setOnItemClickListener(new ShareDialog.OnItemOnClickListener() {
                            @Override
                            public void onItemClick(ActionItem item, int position) {
                                ShareModel model = new ShareModel();
                                model.setTitle("夜行神器");
                                model.setContent("邀请好友");
                                model.setShareUrl(respShareCode.getData().getShareUrl());
                                model.setImageMedia(new UMImage(DeviceDetailsActivity.this, ""));

                                switch (position) {
                                    case 0:
                                        shareUtils.share(SHARE_MEDIA.WEIXIN, model, iShareCallback);
                                        break;
                                    case 1:
                                        shareUtils.share(SHARE_MEDIA.WEIXIN_CIRCLE, model, iShareCallback);
                                        break;
                                    case 2:
                                        shareUtils.share(SHARE_MEDIA.QQ, model, iShareCallback);
                                        break;
                                    case 3:
                                        if (shareUtils.isSinaInstalled()) {
                                            shareUtils.share(SHARE_MEDIA.SINA, model, iShareCallback);
                                        } else {
                                            ToastUtils.show(getApplicationContext(), "您还未安装此应用");
                                        }
                                        break;
                                    default:
                                        break;
                                }
                            }
                        });

                        shareDialog.showDialog();
                    }
                }
            }

            @Override
            public void onFailed(VolleyError error) {
                Log.i("info",error.toString());
                ToastUtils.show(DeviceDetailsActivity.this,error.toString());
            }
        }).tag(this.toString()).build().excute();

    }

    private void reqActiveDevice(String uid){
        final ReqActiveDevice reqActiveDevice = new ReqActiveDevice();

        reqActiveDevice.setUid(uid);
        reqActiveDevice.setIsActive(0);
        HttpRequestProxy.get().create(reqActiveDevice, new AbsHttpRequestProxy.RequestListener() {
            @Override
            public void onSuccess(Object response) {

                Log.i("info",response.toString());
                RespActiveDevice respActiveDevice =(RespActiveDevice) response;
                if(respActiveDevice.getData().getResultCode()==0){
                    dismissLoadingDialog();
                    ToastUtils.show(DeviceDetailsActivity.this,"设备解绑成功");
                    finish();

                }else{
                    ToastUtils.show(DeviceDetailsActivity.this,"设备解绑失败");
                }
            }

            @Override
            public void onFailed(VolleyError error) {
                Log.i("info",error.toString());
                dismissLoadingDialog();
                ToastUtils.show(DeviceDetailsActivity.this,error.toString());
            }
        }).tag(this.toString()).build().excute();

    }
}
