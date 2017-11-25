package com.zt.nightrun.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.error.VolleyError;
import com.baidu.mapapi.map.Text;
import com.chris.common.KeelApplication;
import com.chris.common.image.ImageUtils;
import com.chris.common.network.HttpRequestProxy;
import com.chris.common.utils.AppUtil;
import com.chris.common.utils.FileUtil;
import com.chris.common.utils.SharedPreferencesUtil;
import com.chris.common.utils.ToastUtils;
import com.chris.common.view.BaseActivity;
import com.quncao.core.http.AbsHttpRequestProxy;
import com.zt.nightrun.NightRunApplication;
import com.zt.nightrun.R;
import com.zt.nightrun.eventbus.UserImg;
import com.zt.nightrun.model.req.ReqModify;
import com.zt.nightrun.model.resp.RespModify;
import com.zt.nightrun.model.resp.User;
import com.zt.nightrun.upyun.IUploadFileCallback;
import com.zt.nightrun.upyun.MUploadFile;
import com.zt.nightrun.upyun.UploadPic;
import com.zt.nightrun.view.CActionSheetDialog;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.chris.common.utils.FileUtil.saveBitmapFile;

/**
 * 作者：by chris
 * 日期：on 2017/8/5 - 下午7:25.
 * 邮箱：android_cjx@163.com
 */

public class PersonInfoActivity extends BaseActivity implements View.OnClickListener {

    private String imgUrl = "";
    private static final File PHOTO_DIR = new File(Environment.getExternalStorageDirectory() + "/DCIM/Camera"); // 拍照的照片存储位置
    private File mCurrentPhotoFile; // 照相机拍照得到的图片
    public static Map<String, Object> mObjectMap = new HashMap<String, Object>();
    public static final int CAMERA_WITH_DATA_TO_THUMB = 3025;
    public static final int CAMERA_WITH_DATA = 3023; // 用来标识请求照相功能的 activity
    public static final int PHOTO_PICKED_WITH_DATA = 3021; // 用来标识请求 gallery 的 activity
    public static final int CHOOSE_LOCAL_PICTURE = 2;
    private Uri mPhotoUri = null;
    private String imgFilePath;
    private static String FILE_DIR_NAME = "photo";
    public static final int MAX_WIDTH = 50;
    public static final int MAX_HEIGHT = 50;

    private LinearLayout headLinear, nickNameLinear;
    private RelativeLayout sexLayout;
    private TextView tvNick;
    private TextView tvSex;
    private ImageView img_avatar;
    private CActionSheetDialog shareDialog;
    private TextView tvAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_person_info, true);
        Intent intent = getIntent();
        initView();
    }

    private void initView() {

        setTitle("个人信息");
        headLinear = (LinearLayout) findViewById(R.id.layout_user_icon);
        nickNameLinear = (LinearLayout) findViewById(R.id.layout_user_nick_name);
        sexLayout = (RelativeLayout) findViewById(R.id.layout_sex);
        tvNick = (TextView) findViewById(R.id.tv_nick_name);
        tvSex = (TextView) findViewById(R.id.tv_sex);
        img_avatar =(ImageView)findViewById(R.id.img_avatar);
        tvAccount =(TextView)findViewById(R.id.userId);
        headLinear.setOnClickListener(this);
        nickNameLinear.setOnClickListener(this);
        sexLayout.setOnClickListener(this);

        tvNick.setText(NightRunApplication.getInstance().nick);
        if (NightRunApplication.getInstance().gender == 1) {
            tvSex.setText("男");
        } else {
            tvSex.setText("女");
        }
        if(!TextUtils.isEmpty(NightRunApplication.getInstance().image)){
            Log.i("info=====-",NightRunApplication.getInstance().image);
            ImageUtils.loadCircleImage(this,NightRunApplication.getInstance().image+"!thumb",R.mipmap.default_avtar,img_avatar);
        }

        if(!TextUtils.isEmpty(NightRunApplication.getInstance().mobile)){
            tvAccount.setText(NightRunApplication.getInstance().mobile);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        tvNick.setText(NightRunApplication.getInstance().nick);
        if (NightRunApplication.getInstance().gender == 1) {
            tvSex.setText("男");
        } else {
            tvSex.setText("女");
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_user_icon:

                showPhotoPopWindow();
                break;

            case R.id.layout_user_nick_name:
                startActivity(new Intent(PersonInfoActivity.this, ModifyNickNameActivity.class));
                break;

            case R.id.layout_sex:
                startActivity(new Intent(PersonInfoActivity.this, ModifySexActivity.class));
                break;

        }
    }

    private void showPhotoPopWindow() {
        shareDialog = new CActionSheetDialog(PersonInfoActivity.this);
        shareDialog.addSheetItem("拍照", shareDialog.getTextColor(), null, new CActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick() {
                if (AppUtil.sdcardAvailable() && AppUtil.getAvailaleSize() != 0) {
                    doTakePhoto();
                } else {
                    showNoSdCardDialog();
                }
            }
        });
        shareDialog.addSheetItem("手机中选择上传", shareDialog.getTextColor(), null, new CActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick() {
                getloaclPic();
            }
        });
        shareDialog.show();
    }

    /**
     * 点击没有SD卡 弹框
     */
    private void showNoSdCardDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PersonInfoActivity.this);
        builder.setTitle("SD卡暂不可用或没有剩余存储空间，请检查SD卡状态!");
        builder.setNegativeButton("确定", null);
        builder.show();
    }

    /**
     * 拍照获取图片
     */
    protected void doTakePhoto() {
        try {
            imgUrl = "";
            // Launch camera to take photo for selected contact
            PHOTO_DIR.mkdirs();
            // 创建照片的存储目录
            mCurrentPhotoFile = new File(PHOTO_DIR, getPhotoFileName());
            // 给新照的照片文件命名
            mObjectMap.put("key_photo_file", mCurrentPhotoFile.getAbsolutePath());

            final Intent intent = getTakePickIntent(mCurrentPhotoFile);
            startActivityForResult(intent, CAMERA_WITH_DATA);
        } catch (ActivityNotFoundException e) {
            ToastUtils.show(PersonInfoActivity.this, "没有图库");
        }
    }


    /**
     * 获取本地图片
     */
    private void getloaclPic() {
        /*Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
		intent.setType("image/*");
		startActivityForResult(intent, CHOOSE_LOCAL_PICTURE);*/
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, CHOOSE_LOCAL_PICTURE);
    }

    /**
     * 用当前时间给取得的图片命名,需要注意，如果文件名有空格，这货还取不到返回值
     */
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }

    public static Intent getTakePickIntent(File f) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        return intent;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {// result is not correct
            return;
        } else {
            if (requestCode == CAMERA_WITH_DATA_TO_THUMB) {
                processGalleryData(data.getData());
            } else if (requestCode == PHOTO_PICKED_WITH_DATA) {
                processGalleryData(data.getData());
            } else if (requestCode == CAMERA_WITH_DATA) {
                // 照相机程序返回的,再次调用图 片剪辑程序去修剪图片
                // doCropPhoto();
                if (null == mCurrentPhotoFile) {
                    String path = (String) mObjectMap.get("key_photo_file");
                    if (null != path) {
                        mCurrentPhotoFile = new File(path);
                    }
                }

                if (null == mCurrentPhotoFile) {
                    ToastUtils.show(PersonInfoActivity.this, "内存不足");
                    return;
                }

                String path = mCurrentPhotoFile.getAbsolutePath();
                if (!TextUtils.isEmpty(path)) {
                    imgUrl = path;
                    try {
                        mPhotoUri = Uri.fromFile(new File(path));
                        final Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                        intent.setData(mPhotoUri);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    showPhoto(imgUrl, true);
                }
            } else if (requestCode == CHOOSE_LOCAL_PICTURE) {
                processGalleryData(data.getData());
            }
        }
    }

    /**
     * @param imageFileUri
     */
    private void processGalleryData(Uri imageFileUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cur = null;
        try {
            System.out.println("imageFileUri:" + imageFileUri);
            cur = getContentResolver().query(imageFileUri, proj, null, null, null);
            int imageIdx = cur.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cur.moveToFirst();
            imgUrl = cur.getString(imageIdx);
            System.out.println("imgUrl:" + imgUrl);
            if (TextUtils.isEmpty(imgUrl)) {
                // ToastUtils.showToast(getActivity(), "图片获取失败");
                InputStream is = getContentResolver().openInputStream(imageFileUri);
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                is.close();
                File fileDir = NightRunApplication.getApplicationConext().getDir(FILE_DIR_NAME, Context.MODE_PRIVATE);
                String fileName = fileDir.getAbsolutePath() + File.separator + "temp.jpg";
                saveBigmapFile(fileName, bitmap, true);
                return;
            }
            File file = new File(imgUrl);
            if (file.exists()) {
                mPhotoUri = imageFileUri;
                showPhoto(imgUrl, true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != cur) {
                cur.close();
            }
        }
    }

    /**
     * @param bitmap
     * @param store
     */
    private void saveBigmapFile(String fileName, Bitmap bitmap, boolean store) {
        String absolutePath = fileName;
        boolean isSuccess = false;
        if (store) {
            isSuccess = saveBitmapFile(bitmap, absolutePath);
        } else {
            isSuccess = true;
        }
        if (isSuccess) {
            imgFilePath = fileName;
			img_avatar.setImageBitmap(bitmap);
            if (!TextUtils.isEmpty(imgFilePath)) {
                ArrayList<String> uploadList = new ArrayList<String>();
                uploadList.add(imgFilePath);
                doUpload(uploadList);
            }
            System.out.println("saveBigmapFile success");
        } else {
            System.out.println("saveBigmapFile error");
        }
    }

    /**
     * TODO 上传
     */
    private void doUpload(ArrayList<String> pathList) {
        if (!AppUtil.isNetworkConnected(NightRunApplication.getApplicationConext())) { // 无网络
            ToastUtils.show(NightRunApplication.getApplicationConext(), "网络连接不可用,请检查网络设置.");
            return;
        }

        Log.i("info=====", pathList.get(0).toString());
        showLoadingDialog();
        UploadPic.uploadFile(pathList.get(0), 0, new IUploadFileCallback() {
            @Override
            public void onProgress(int percent) {

            }

            @Override
            public void onData(Object output, Object input) {
                dismissLoadingDialog();
                MUploadFile uploadFile = (MUploadFile) output;
                if (!TextUtils.isEmpty(uploadFile.getUrl())) {
                    reqModifyHeadImage("http://image.carnote.cn/" + uploadFile.getUrl());
                }
                Log.i("info----===", uploadFile.toString());
            }
        });

//        UploadPic.uploadFile(this, pathList.get(0), new IUploadFileCallback() {
//            @Override
//            public void onProgress(int percent) {
//                showLoadingDialog();
//            }
//
//            @Override
//            public void onData(Object output, Object input) {
//                dismissLoadingDialog();
//                if (output != null) {
//                    MUploadFile uploadFile = (MUploadFile) output;
//                    Log.i("info----===",uploadFile.toString());
//                    if (uploadFile.isResult()) {
//
//                    } else {
//
//                    }
//                }
//            }
//        });

//        ArrayList<String> uploadList = new ArrayList<String>();
//
//        for (String path : pathList) {
//
//            Bitmap bitmap = getimage(path);
//
//            boolean isSuccess = saveBitmapFile(bitmap, path);
//
//            if (isSuccess) {
//                uploadList.add(path);
//            } else {
//                ToastUtils.show(PersonInfoActivity.this, "内存不足");
//            }
//
//        }
//
//        if (uploadList != null && uploadList.size() > 0) {
//            createUploadImageFile(pathList);
//        }
    }


    /**
     *
     */
    private void showPhoto(String filename, boolean store) {
        final Bitmap bitmap = decodeSampledBitmapFromFile(filename, MAX_WIDTH, MAX_HEIGHT, Bitmap.Config.RGB_565);
        if (null != bitmap) {
            setViewResource(filename, bitmap, store);
        }
    }

    private void setViewResource(String fileName, Bitmap bitmap, boolean store) {
        if (bitmap == null) {
            return;
        }
        saveBigmapFile(fileName, bitmap, store);
    }


    public static Bitmap decodeSampledBitmapFromFile(final String filename, int maxWidth, int maxheight, Bitmap.Config config) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filename, options);
        System.out.println("decodeSampledBitmapFromFile:" + options.outWidth + " height:" + options.outHeight);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, maxWidth, maxheight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = config;
        Bitmap bitmap = BitmapFactory.decodeFile(filename, options);
        System.out.println("decodeSampledBitmapFromFile2:" + bitmap.getWidth() + " height:" + bitmap.getHeight());
        return bitmap;
    }

    public static final int calculateInSampleSize(final BitmapFactory.Options options, final int reqWidth, final int reqHeight) {
		/* Raw height and width of image */
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }

            // This offers some additional logic in case the image has a strange
            // aspect ratio. For example, a panorama may have a much larger
            // width than height. In these cases the total pixels might still
            // end up being too large to fit comfortably in memory, so we should
            // be more aggressive with sample down the image (=larger
            // inSampleSize).

            final float totalPixels = width * height;

			/* More than 2x the requested pixels we'll sample down further */
            final float totalReqPixelsCap = reqWidth * reqHeight * 2;

            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++;
            }
        }
        System.out.println("inSampleSize:" + inSampleSize + " width:" + width + " height:" + height);
        return inSampleSize;
    }

    private void reqModifyHeadImage(String imageUrl) {
        final ReqModify reqModify = new ReqModify();
        reqModify.setImage(imageUrl);
        reqModify.setNick(NightRunApplication.getInstance().nick);
        reqModify.setGender(NightRunApplication.getInstance().gender);
        HttpRequestProxy.get().create(reqModify, new AbsHttpRequestProxy.RequestListener() {
            @Override
            public void onSuccess(Object response) {

                Log.i("info", response.toString());
                RespModify respModify = (RespModify) response;
                if (respModify.getData() != null && respModify.getData().getUser() != null) {
                    ToastUtils.show(PersonInfoActivity.this, "头像修改成功");
                    User user = respModify.getData().getUser();
                    SharedPreferencesUtil.saveString(KeelApplication.getApplicationConext(), "image", user.getImage());
                    NightRunApplication.getInstance().gender = user.getGender();
                    EventBus.getDefault().post(new UserImg(user.getImage()));
                } else {
                    ToastUtils.show(PersonInfoActivity.this, respModify.getMessage());
                }
            }

            @Override
            public void onFailed(VolleyError error) {
                Log.i("info", error.toString());
                ToastUtils.show(PersonInfoActivity.this, error.toString());
            }
        }).tag(this.toString()).build().excute();
    }
}
