/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zt.nightrun.zxing.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.android.volley.error.VolleyError;
import com.chris.common.KeelApplication;
import com.chris.common.network.HttpRequestProxy;
import com.chris.common.utils.Md5Utils;
import com.chris.common.utils.SharedPreferencesUtil;
import com.chris.common.view.BaseActivity;
import com.google.zxing.Result;
import com.google.zxing.client.result.ResultParser;
import com.chris.common.image.ImageUtils;
import com.chris.common.utils.ToastUtils;
import com.quncao.core.http.AbsHttpRequestProxy;
import com.zt.nightrun.NightRunApplication;
import com.zt.nightrun.R;
import com.chris.common.view.CustomDialog;
import com.zt.nightrun.activity.LoginActivity;
import com.zt.nightrun.activity.MainActivity;
import com.zt.nightrun.model.req.ReqActiveDevice;
import com.zt.nightrun.model.req.ReqLogin;
import com.zt.nightrun.model.resp.RespActiveDevice;
import com.zt.nightrun.model.resp.RespLogin;
import com.zt.nightrun.model.resp.User;
import com.zt.nightrun.view.CustomEditDialog;
import com.zt.nightrun.zxing.camera.CameraManager;
import com.zt.nightrun.zxing.decode.BitmapDecoder;
import com.zt.nightrun.zxing.decode.DecodeThread;
import com.zt.nightrun.zxing.utils.BeepManager;
import com.zt.nightrun.zxing.utils.CaptureActivityHandler;
import com.zt.nightrun.zxing.utils.InactivityTimer;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * This activity opens the camera and does the actual scanning on a background
 * thread. It draws a viewfinder to help the user place the barcode correctly,
 * shows feedback as the image processing is happening, and then overlays the
 * results when a scan is successful.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 * @author Sean Owen
 */
public class CaptureActivity extends BaseActivity implements SurfaceHolder.Callback, OnClickListener {

    private static final String TAG = CaptureActivity.class.getSimpleName();

    private static final int REQUEST_CODE = 100;

    private static final int PARSE_BARCODE_FAIL = 300;

    private static final int PARSE_BARCODE_SUC = 200;

    private CameraManager cameraManager;
    private CaptureActivityHandler handler;
    private InactivityTimer inactivityTimer;
    private BeepManager beepManager;

    private SurfaceView scanPreview = null;
    private RelativeLayout scanContainer;
    private RelativeLayout scanCropView;
    private ImageView scanLine;

    private Rect mCropRect = null;

    private boolean isFlashlightOpen;

    /**
     * 图片的路径
     */
    private String photoPath;

    private Handler mHandler = new MyHandler(this);

    static class MyHandler extends Handler {

        private WeakReference<Activity> activityReference;

        public MyHandler(Activity activity) {
            activityReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case PARSE_BARCODE_SUC: // 解析图片成功
                    String resultString = (String) msg.obj;
                    if (resultString.equals("")) {
                        ToastUtils.show(activityReference.get(), "扫码失败");
                    } else if (resultString.contains("http://") || resultString.startsWith("https://")) {
//                        if (resultString.contains("source=bailingniaoqrcode")) {
//                            Map<String, String> hashMap = new HashMap<>();
//                            String[] params = resultString.split("&");
//                            for (String str : params) {
//                                String[] sp = str.split("=");
//                                hashMap.put(sp[0], sp[1]);
//                            }
//                            if (hashMap.get("m").equals("c")) {
//                                ClubModuleApi.getInstance().startClubIndex(activityReference.get(), Integer.parseInt(hashMap.get("d")), 0);
//                                activityReference.get().finish();
//                            } else if (hashMap.get("m").equals("u")) {
//                                AppEntry.enterUserhomeActivity(activityReference.get(), Integer.parseInt(hashMap.get("d")));
//                                activityReference.get().finish();
//                            } else {
//                                Uri uri = Uri.parse(resultString);
//                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                                activityReference.get().startActivity(intent);
//                            }
//                        } else {
//                            Uri uri = Uri.parse(resultString);
//                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                            activityReference.get().startActivity(intent);
//                        }
                    }
                    break;
                case PARSE_BARCODE_FAIL:// 解析图片失败
                    Toast.makeText(activityReference.get(), "解析图片失败",
                            Toast.LENGTH_SHORT).show();
                    break;

                default:
                    break;
            }
            super.handleMessage(msg);
        }
    }

    public Handler getHandler() {
        return handler;
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    private boolean isHasSurface = false;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_capture,true);
        setTitle("扫一扫");
        scanPreview = (SurfaceView) findViewById(R.id.capture_preview);
        scanContainer = (RelativeLayout) findViewById(R.id.capture_container);
        scanCropView = (RelativeLayout) findViewById(R.id.capture_crop_view);
        scanLine = (ImageView) findViewById(R.id.capture_scan_line);

        inactivityTimer = new InactivityTimer(this);
        beepManager = new BeepManager(this);

        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT,
                0.9f);
        animation.setDuration(4500);
        animation.setRepeatCount(-1);
        animation.setRepeatMode(Animation.RESTART);
        scanLine.startAnimation(animation);

        findViewById(R.id.capture_flashlight).setOnClickListener(this);
        findViewById(R.id.capture_scan_photo).setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // CameraManager must be initialized here, not in onCreate(). This is
        // necessary because we don't
        // want to open the camera driver and measure the screen size if we're
        // going to show the help on
        // first launch. That led to bugs where the scanning rectangle was the
        // wrong size and partially
        // off screen.
        cameraManager = new CameraManager(getApplication());

        handler = null;

        if (isHasSurface) {
            // The activity was paused but not stopped, so the surface still
            // exists. Therefore
            // surfaceCreated() won't be called, so init the camera here.
            initCamera(scanPreview.getHolder());
        } else {
            // Install the callback and wait for surfaceCreated() to init the
            // camera.
            scanPreview.getHolder().addCallback(this);
        }

        inactivityTimer.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        inactivityTimer.onPause();
        beepManager.close();
        cameraManager.closeDriver();
        if (!isHasSurface) {
            scanPreview.getHolder().removeCallback(this);
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (holder == null) {
            Log.e(TAG, "*** WARNING *** surfaceCreated() gave us a null surface!");
        }
        if (!isHasSurface) {
            isHasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isHasSurface = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    /**
     * A valid barcode has been found, so give an indication of success and show
     * the results.
     *
     * @param rawResult The contents of the barcode.
     * @param bundle    The extras
     */
    public void handleDecode(Result rawResult, Bundle bundle) {
        inactivityTimer.onActivity();
        beepManager.playBeepSoundAndVibrate();

        String resultString = rawResult.getText();

        Log.i("info=====----",resultString.toString());

        if(!TextUtils.isEmpty(resultString)){

            CustomEditDialog dialog = new CustomEditDialog(this, "设备uid", "输入一个设备uid", InputType.TYPE_CLASS_NUMBER, new CustomEditDialog.OnOkListener() {
                @Override
                public void onOkClick(CustomEditDialog dialog, String string) {

                    if(!TextUtils.isEmpty(string)){
                        dialog.dismiss();
                        showLoadingDialog();
                        reqActiveDevice(string);
                    }else{
                        ToastUtils.show(CaptureActivity.this,"请输入设备uid");
                    }
                }
            });

            dialog.show();

        }else{
            ToastUtils.show(this, "扫码失败");
        }

//        if (resultString.equals("")) {
//            ToastUtils.show(this, "扫码失败");
//        } else if (resultString.contains("http://") || resultString.startsWith("https://")) {
//            if (resultString.contains("source=bailingniaoqrcode")) {
//                Map<String, String> hashMap = new HashMap<>();
//                String[] params = resultString.split("&");
//                for (String str : params) {
//                    String[] sp = str.split("=");
//                    hashMap.put(sp[0], sp[1]);
//                }
//                if (hashMap.get("m").equals("c")) {
////                    ClubModuleApi.getInstance().startClubIndex(this, Integer.parseInt(hashMap.get("d")), 0);
//                    finish();
//                } else if (hashMap.get("m").equals("u")) {
////                    AppEntry.enterUserhomeActivity(this, Integer.parseInt(hashMap.get("d")));
//                    finish();
//                } else {
//                    Uri uri = Uri.parse(resultString);
//                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                    startActivity(intent);
//                }
//            } else {
//                Uri uri = Uri.parse(resultString);
//                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                startActivity(intent);
//            }
//        }

    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (cameraManager.isOpen()) {
            Log.w(TAG, "initCamera() while already open -- late SurfaceView callback?");
            return;
        }
        try {
            cameraManager.openDriver(surfaceHolder);
            // Creating the handler starts the preview, which can also throw a
            // RuntimeException.
            if (handler == null) {
                handler = new CaptureActivityHandler(this, cameraManager, DecodeThread.ALL_MODE);
            }

            initCrop();
        } catch (IOException ioe) {
            Log.w(TAG, ioe);
            displayFrameworkBugMessageAndExit();
        } catch (RuntimeException e) {
            // Barcode Scanner has seen crashes in the wild of this variety:
            // java.?lang.?RuntimeException: Fail to connect to camera service
            Log.w(TAG, "Unexpected error initializing camera", e);
            displayFrameworkBugMessageAndExit();
        }
    }

    private void displayFrameworkBugMessageAndExit() {
        // camera error
        new CustomDialog(this, new CustomDialog.OnClickListener() {
            @Override
            public void onLeftClick() {
            }

            @Override
            public void onRightClick() {
                finish();
            }
        }).setOneBtn(true).setTitle("权限错误").setContent("请在应用权限设置里打开相机权限！").show();
    }

    public void restartPreviewAfterDelay(long delayMS) {
        if (handler != null) {
            handler.sendEmptyMessageDelayed(R.id.restart_preview, delayMS);
        }
    }

    public Rect getCropRect() {
        return mCropRect;
    }

    /**
     * 初始化截取的矩形区域
     */
    private void initCrop() {
        int cameraWidth = cameraManager.getCameraResolution().y;
        int cameraHeight = cameraManager.getCameraResolution().x;

        /** 获取布局中扫描框的位置信息 */
        int[] location = new int[2];
        scanCropView.getLocationInWindow(location);

        int cropLeft = location[0];
        int cropTop = location[1] - getStatusBarHeight();

        int cropWidth = scanCropView.getWidth();
        int cropHeight = scanCropView.getHeight();

        /** 获取布局容器的宽高 */
        int containerWidth = scanContainer.getWidth();
        int containerHeight = scanContainer.getHeight();

        /** 计算最终截取的矩形的左上角顶点x坐标 */
        int x = cropLeft * cameraWidth / containerWidth;
        /** 计算最终截取的矩形的左上角顶点y坐标 */
        int y = cropTop * cameraHeight / containerHeight;

        /** 计算最终截取的矩形的宽度 */
        int width = cropWidth * cameraWidth / containerWidth;
        /** 计算最终截取的矩形的高度 */
        int height = cropHeight * cameraHeight / containerHeight;

        /** 生成最终的截取的矩形 */
        mCropRect = new Rect(x, y, width + x, height + y);
    }

    private int getStatusBarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if (resultCode == RESULT_OK) {
            final ProgressDialog progressDialog;
            switch (requestCode) {
                case REQUEST_CODE:

                    photoPath = intent.getStringArrayListExtra("imageList").get(0);
                    progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("正在扫描...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    new Thread(new Runnable() {

                        @Override
                        public void run() {

                            Bitmap img = ImageUtils
                                    .getCompressedBitmap(photoPath);

                            BitmapDecoder decoder = new BitmapDecoder(
                                    CaptureActivity.this);
                            Result result = decoder.getRawResult(img);

                            if (result != null) {
                                Message m = mHandler.obtainMessage();
                                m.what = PARSE_BARCODE_SUC;
                                m.obj = ResultParser.parseResult(result)
                                        .toString();
                                mHandler.sendMessage(m);
                            } else {
                                Message m = mHandler.obtainMessage();
                                m.what = PARSE_BARCODE_FAIL;
                                mHandler.sendMessage(m);
                            }
                            progressDialog.dismiss();
                        }
                    }).start();
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.capture_scan_photo) {// 打开手机中的相册
//            startActivityForResult(new Intent(this, GalleryProxy.getGalleryClass()).putExtra("maxNum", 1), REQUEST_CODE);
        } else if (id == R.id.capture_flashlight) {
            if (isFlashlightOpen) {
                cameraManager.setTorch(false); // 关闭闪光灯
                isFlashlightOpen = false;
            } else {
                cameraManager.setTorch(true); // 打开闪光灯
                isFlashlightOpen = true;
            }

        }
    }

    private void reqActiveDevice(String uid){
        final ReqActiveDevice reqActiveDevice = new ReqActiveDevice();

        reqActiveDevice.setUid(uid);
        reqActiveDevice.setIsActive(1);
        HttpRequestProxy.get().create(reqActiveDevice, new AbsHttpRequestProxy.RequestListener() {
            @Override
            public void onSuccess(Object response) {

                Log.i("info",response.toString());
                RespActiveDevice respActiveDevice =(RespActiveDevice) response;
                dismissLoadingDialog();
                if(respActiveDevice.getData().getResultCode()==0){

                    ToastUtils.show(CaptureActivity.this,"设备绑定成功");

                }else{
                    ToastUtils.show(CaptureActivity.this,"设备绑定失败");
                }
                finish();
            }

            @Override
            public void onFailed(VolleyError error) {
                dismissLoadingDialog();
                Log.i("info",error.toString());
                ToastUtils.show(CaptureActivity.this,error.toString());
            }
        }).tag(this.toString()).build().excute();

    }
}