package com.zt.nightrun.activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.error.VolleyError;
import com.chris.common.network.HttpRequestProxy;
import com.chris.common.share.IShareCallback;
import com.chris.common.share.ShareDialog;
import com.chris.common.share.ShareModel;
import com.chris.common.share.ShareUtils;
import com.chris.common.utils.SharedPreferencesUtil;
import com.chris.common.utils.ToastUtils;
import com.chris.common.view.ActionItem;
import com.chris.common.view.BaseActivity;
import com.chris.common.view.CustomDialog;
import com.quncao.core.http.AbsHttpRequestProxy;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.zt.nightrun.NightRunApplication;
import com.zt.nightrun.R;
import com.zt.nightrun.blue.model.EntityDevice;
import com.zt.nightrun.blue.service.BLEService;
import com.zt.nightrun.blue.utils.BlueUtils;
import com.zt.nightrun.blue.utils.BluetoothController;
import com.zt.nightrun.blue.utils.ConstantUtils;
import com.zt.nightrun.model.req.ReqActiveDevice;
import com.zt.nightrun.model.req.ReqShareCode;
import com.zt.nightrun.model.resp.RespActiveDevice;
import com.zt.nightrun.model.resp.RespShareCode;

/**
 * 作者：by chris
 * 日期：on 2017/6/30 - 下午4:14.
 * 邮箱：android_cjx@163.com
 */

public class DeviceDetailsActivity extends BaseActivity implements View.OnClickListener, CheckBox.OnCheckedChangeListener {

    private RelativeLayout mShouQuanLayout, mLocationLayout, mGuijiLayout, mWramTimeLayout,mSettingLayout;
    private CheckBox mRadioWarming, mRadioUnband, mRadioZhendong, mRadioDengguang, mRadioFengming;
    private TextView tvUnbind;
    private ShareUtils shareUtils;
    private String uid;
    private int deviceId;
    private String deviceName;
    private Intent intentService;
    private MsgReceiver receiver;
    private BluetoothController controller = BluetoothController.getInstance();
    private String blueName = "bt-DCF1EB3C94AB";
//    private String blueName = "bt-C12D1E2E5D27";
    private TextView tvConnect, tvConnectStatus;
    private static final int REQUEST_ENABLE = 1001;
    private boolean isConnect =false;
    private EntityDevice device = new EntityDevice();
    private String switchCraft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_device_detail_layout, true);
        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
        blueName ="bt-"+uid;
        deviceId = intent.getIntExtra("deviceId", 0);
        deviceName = intent.getStringExtra("deviceName");
        shareUtils = new ShareUtils(this);
        if(controller.isConnect()){
            isConnect =true;
        }else{
            isConnect =false;
        }
        initUI();

        initService();
        registerReceiver();

    }

    private void initUI() {
        if(!TextUtils.isEmpty(deviceName)){
            setTitle(deviceName);
        }
        setRightImage(R.mipmap.share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reqShareCode(deviceId);

            }
        });
        mShouQuanLayout = (RelativeLayout) findViewById(R.id.shouquanId);
        mLocationLayout = (RelativeLayout) findViewById(R.id.locationId);
        mGuijiLayout = (RelativeLayout) findViewById(R.id.historyGuiJiId);
        mSettingLayout =(RelativeLayout)findViewById(R.id.settingDevice);
//        mWramTimeLayout =(RelativeLayout)findViewById(R.id.wramingTimeId);
        tvConnect = (TextView) findViewById(R.id.connect);
        tvConnectStatus = (TextView) findViewById(R.id.tvConnectStatus);
        mRadioWarming = (CheckBox) findViewById(R.id.check_wramimg);
        mRadioUnband = (CheckBox) findViewById(R.id.check_unband);
        mRadioZhendong = (CheckBox) findViewById(R.id.check_zhendong);
        mRadioDengguang = (CheckBox) findViewById(R.id.check_dengguang);
        mRadioFengming = (CheckBox) findViewById(R.id.check_fengming);
        tvUnbind =(TextView)findViewById(R.id.unBindDevice);
        tvUnbind.setOnClickListener(this);
        mShouQuanLayout.setOnClickListener(this);
        mLocationLayout.setOnClickListener(this);
        mGuijiLayout.setOnClickListener(this);
        mSettingLayout.setOnClickListener(this);
//        mWramTimeLayout.setOnClickListener(this);
        mRadioWarming.setOnCheckedChangeListener(this);
        mRadioUnband.setOnCheckedChangeListener(this);
        mRadioZhendong.setOnCheckedChangeListener(this);
        mRadioDengguang.setOnCheckedChangeListener(this);
        mRadioFengming.setOnCheckedChangeListener(this);
        tvConnect.setOnClickListener(this);

        if(isConnect){
            tvConnect.setText("断开连接");
            tvConnectStatus.setText("已连接");
        }else{
            tvConnect.setText("连接设备");
            tvConnectStatus.setText("未连接");
        }

        if(!TextUtils.isEmpty(SharedPreferencesUtil.getString(this,"switchCraft"))){
            switchCraft =SharedPreferencesUtil.getString(this,"switchCraft");
            if(switchCraft.equals(BlueUtils.BLUE_WARMING_OPEN)){
                mRadioWarming.setChecked(true);
            }else{
                mRadioWarming.setChecked(false);
            }
        }
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
                startActivity(new Intent(this, AuthorityMemberListActivity.class).putExtra("deviceId", deviceId));
                break;

            case R.id.locationId:
                startActivity(new Intent(this, LocationActivity.class).putExtra("deviceId",deviceId));
                break;

            case R.id.historyGuiJiId:
                startActivity(new Intent(this, GuijiActivity.class).putExtra("deviceId", deviceId));
                break;

            case R.id.connect:
                if(!isConnect){
                    if (!BluetoothController.getInstance().initBLE()) {//手机不支持蓝牙
                        Toast.makeText(DeviceDetailsActivity.this, "您的手机不支持蓝牙", Toast.LENGTH_SHORT).show();
                        return;//手机不支持蓝牙就啥也不用干了，关电脑睡觉去吧
                    }
                    if (!BluetoothController.getInstance().isBleOpen()) {// 如果蓝牙还没有打开
//                    Toast.makeText(DeviceDetailsActivity.this, "请打开蓝牙", Toast.LENGTH_SHORT).show();
                        //弹出对话框提示用户是后打开
                        Intent enabler = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(enabler, REQUEST_ENABLE);
                        //不做提示，直接打开，不建议用下面的方法，有的手机会有问题。
                        // mBluetoothAdapter.enable();
                        return;
                    }
                    new GetDataTask().execute();// 搜索任务
                }else{
                    controller.connect(device);
                }
                showLoadingDialog();
                break;

            case R.id.settingDevice:
                startActivity(new Intent(this,DeviceSettingActivity.class));
                break;

            case R.id.unBindDevice:
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
                if(isChecked){
                    switchCraft =BlueUtils.BLUE_WARMING_OPEN;
                }else{
                    switchCraft =BlueUtils.BLUE_WARMING_CLOSE;
                }
                controller.write("$AWF"+switchCraft);
                SharedPreferencesUtil.saveString(NightRunApplication.getApplicationConext(),"switchCraft",switchCraft);
                break;

        }
    }

    private void reqShareCode(int deviceId) {
        final ReqShareCode reqShareCode = new ReqShareCode();
        reqShareCode.setDeviceId(deviceId);
        HttpRequestProxy.get().create(reqShareCode, new AbsHttpRequestProxy.RequestListener() {
            @Override
            public void onSuccess(Object response) {

                Log.i("info", response.toString());
                final RespShareCode respShareCode = (RespShareCode) response;
                if (respShareCode.getData() != null) {
                    if (!TextUtils.isEmpty(respShareCode.getData().getShareUrl())) {

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
//                                model.setImageMedia(new UMImage(DeviceDetailsActivity.this, R.mipmap.logo));
                                model.setImageMedia(new UMImage(DeviceDetailsActivity.this, "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3576283734,1694787135&fm=200&gp=0.jpg"));

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
                Log.i("info", error.toString());
                ToastUtils.show(DeviceDetailsActivity.this, error.toString());
            }
        }).tag(this.toString()).build().excute();

    }

    private void reqActiveDevice(String uid) {
        final ReqActiveDevice reqActiveDevice = new ReqActiveDevice();

        reqActiveDevice.setUid(uid);
        reqActiveDevice.setIsActive(0);
        HttpRequestProxy.get().create(reqActiveDevice, new AbsHttpRequestProxy.RequestListener() {
            @Override
            public void onSuccess(Object response) {

                Log.i("info", response.toString());
                RespActiveDevice respActiveDevice = (RespActiveDevice) response;
                if (respActiveDevice.getData().getResultCode() == 0) {
                    dismissLoadingDialog();
                    ToastUtils.show(DeviceDetailsActivity.this, "设备解绑成功");
                    finish();

                } else {
                    ToastUtils.show(DeviceDetailsActivity.this, "设备解绑失败");
                }
            }

            @Override
            public void onFailed(VolleyError error) {
                Log.i("info", error.toString());
                dismissLoadingDialog();
                ToastUtils.show(DeviceDetailsActivity.this, error.toString());
            }
        }).tag(this.toString()).build().excute();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE) {
            switch (resultCode) {
                // 点击确认按钮
                case Activity.RESULT_OK:
                    //用户选择开启 Bluetooth，Bluetooth 会被开启
                    new GetDataTask().execute();// 搜索任务
                    break;

                // 点击取消按钮或点击返回键
                case Activity.RESULT_CANCELED: {
                    // TODO 用户拒绝打开 Bluetooth, Bluetooth 不会被开启
                }
                break;
                default:

                    break;
            }
        }
    }

    /**
     * 开始服务, 初始化蓝牙
     */
    private void initService() {
        //开始服务
        intentService = new Intent(DeviceDetailsActivity.this, BLEService.class);
        startService(intentService);
        // 初始化蓝牙
        BluetoothController.getInstance().initBLE();
    }

    private void registerReceiver() {
        receiver = new MsgReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConstantUtils.ACTION_UPDATE_DEVICE_LIST);
        intentFilter.addAction(ConstantUtils.ACTION_CONNECTED_ONE_DEVICE);
        intentFilter.addAction(ConstantUtils.ACTION_RECEIVE_MESSAGE_FROM_DEVICE);
        intentFilter.addAction(ConstantUtils.ACTION_STOP_CONNECT);
        registerReceiver(receiver, intentFilter);
    }

    private class GetDataTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {
            if (BluetoothController.getInstance().isBleOpen()) {
                BluetoothController.getInstance().startScanBLE();
            }
            // 开始扫描
            return null;
        }

        @Override
        protected void onPostExecute(String[] result) {
            super.onPostExecute(result);
        }
    }

    /**
     * 广播接收器
     */
    public class MsgReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(ConstantUtils.ACTION_UPDATE_DEVICE_LIST)) {
                String name = intent.getStringExtra("name");
                String address = intent.getStringExtra("address");
                Log.i("info====---", "device_name:" + name);
                if(name.equals(blueName)){
                    ToastUtils.show(DeviceDetailsActivity.this,"查找到对应设备~");
                    device.setName(name);
                    device.setAddress(address);
                    BluetoothController.getInstance().connect(device);
                }
//                boolean found=false;//记录该条记录是否已在list中，
//                for(EntityDevice device:list){
//                    if(device.getAddress().equals(address)){
//                        found=true;
//                        break;
//                    }
//                }// for
//                if(!found){
//                    EntityDevice temp = new EntityDevice();
//                    temp.setName(name);
//                    temp.setAddress(address);
//                    list.add(temp);
//                    adapter.notifyDataSetChanged();
//                }
            } else if (intent.getAction().equalsIgnoreCase(ConstantUtils.ACTION_CONNECTED_ONE_DEVICE)) {
//                connectedDevice.setText("连接的蓝牙是："+intent.getStringExtra("address"));
                tvConnectStatus.setText("已连接");
                tvConnect.setText("断开连接");
                isConnect =true;
                dismissLoadingDialog();
            } else if (intent.getAction().equalsIgnoreCase(ConstantUtils.ACTION_STOP_CONNECT)) {
                tvConnectStatus.setText("未连接");
                tvConnect.setText("连接设备");
                isConnect =false;
                dismissLoadingDialog();
            }
//
//            else if (intent.getAction().equalsIgnoreCase(ConstantUtils.ACTION_RECEIVE_MESSAGE_FROM_DEVICE)){
//                receivedMessage.append("\n\r"+intent.getStringExtra("message"));
//            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        stopService(intentService);
        unregisterReceiver(receiver);
    }
}
