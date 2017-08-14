package com.zt.nightrun.upyun;

import android.content.Context;
import android.util.Log;

import com.upyun.library.common.Params;
import com.upyun.library.common.UploadManager;
import com.upyun.library.listener.UpCompleteListener;
import com.upyun.library.listener.UpProgressListener;
import com.zt.nightrun.luban.Luban;
import com.zt.nightrun.luban.OnCompressListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class UploadPic {

    private final static String BUCKET_NAME = "yxsq-zt";
    private final static String OPERATOR_NAME = "yxsq";
    private final static String OPERATOR_PWD = "yxsq8888";

    public static String KEY = "ElhvfOKflDPdV5INETuzqBtTp74=";
    public static String savePath = "{random32}{.suffix}";

    private static final int COMPRESS_THRESHOLD = 1024 * 1024 * 1; //MB

    /**
     * 新版UpYun上传图片
     *
     * @param filePath 图片路径
     * @param callback 上传回调
     */
    public static void uploadFile(String filePath, final IUploadFileCallback callback) {
        uploadFile(filePath, 0, callback);
    }

    /**
     * 对大于{@link #COMPRESS_THRESHOLD}的图片本地压缩后上传
     * @param context
     * @param filePath
     * @param index
     * @param callback
     */
    public static void uploadFileWithCompress(Context context, String filePath, int index, IUploadFileCallback callback){
        File imageFile = new File(filePath);
        compressAndUpload(context, imageFile, index, callback);
    }

    private static void compressAndUpload(Context context, File imageFile, final int index, final IUploadFileCallback callback){
        Luban.newInstance(context).load(imageFile).putGear(Luban.THIRD_GEAR).setCompressListener(new OnCompressListener() {
            public void onStart() {}
            public void onSuccess(File file) {
                if (file != null) {
                    uploadFile(file.getAbsolutePath(), index, callback);
                } else {
                    MUploadFile mUploadFile = new MUploadFile();
                    mUploadFile.setResult(false);
                    callback.onData(mUploadFile, "UploadFile");
                }
            }
            public void onError(Throwable e) {
                MUploadFile mUploadFile = new MUploadFile();
                mUploadFile.setResult(false);
                callback.onData(mUploadFile, "UploadFile");
                e.printStackTrace();
            }
        }).launch();
    }

    public static void uploadFile(String filePath, int index, final IUploadFileCallback callback) {
        final MUploadFile mUploadFile = new MUploadFile();
        mUploadFile.setIndex(index);
        mUploadFile.setFile(filePath);
        try {
            final Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put(Params.BUCKET, BUCKET_NAME);
            paramsMap.put(Params.SAVE_KEY, savePath);
            paramsMap.put(Params.X_GMKERL_ROTATE, "auto");//设置旋转
            paramsMap.put(Params.RETURN_URL, "httpbin.org/post");

            UpProgressListener progressListener = new UpProgressListener() {
                @Override
                public void onRequestProgress(final long bytesWrite, final long contentLength) {
                    callback.onProgress((int) ((100 * bytesWrite) / contentLength));
                }
            };

            UpCompleteListener completeListener = new UpCompleteListener() {
                @Override
                public void onComplete(boolean isSuccess, String result) {
                    Log.i("=====result",result.toString());
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        mUploadFile.setUrl(jsonObject.getString("url"));
                        mUploadFile.setResult(isSuccess);
                        mUploadFile.setWidth(jsonObject.getInt("image-width"));
                        mUploadFile.setHeight(jsonObject.getInt("image-height"));
                        callback.onData(mUploadFile, "UploadFile");
                    } catch (JSONException e) {
                        mUploadFile.setResult(isSuccess);
                        callback.onData(mUploadFile, "UploadFile");
                        e.printStackTrace();
                    }
                }
            };
            UploadManager.getInstance().formUpload(new File(filePath), paramsMap, KEY, completeListener, progressListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param filePath   文件的完整路径文件名， 如 /storge/sdcard/dicm/camera/1.jpg
     * @param suffixName 文件后缀名， 如 ".jpg", ".png"
     * @param callback
     * @return
     */
    public static MUploadFile uploadFile(String filePath, String suffixName, IUploadFileCallback callback) {
        // 初始化空间
        UpYun upyun = new UpYun(BUCKET_NAME, OPERATOR_NAME, OPERATOR_PWD);
        MUploadFile result;
        result = new MUploadFile(suffixName);
        result.setFile(filePath);
        try {
            // 设置是否开启debug模式，默认不开启
            upyun.setDebug(true);
            /*
             * 上传方法3：采用数据流模式上传文件（节省内存），可自动创建父级目录（最多10级）
			 */
            String upName = filePath + suffixName;

            File file = new File(filePath);
            Map<String, String> map = new HashMap<String, String>();
            map.put(UpYun.PARAMS.KEY_X_GMKERL_QUALITY.getValue(), "90");//90%图片质量
            map.put(UpYun.PARAMS.KEY_X_GMKERL_ROTATE.getValue(), "auto");//设置旋转
            boolean result3 = upyun.writeFile(upName, file, true, map, callback);
            System.out.println("upName " + filePath + result3);
            result.setFile(filePath);
            if (result3) {
                result.setUrl(upName);
                result.setResult(true);
            } else {
                result.setResult(false);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
