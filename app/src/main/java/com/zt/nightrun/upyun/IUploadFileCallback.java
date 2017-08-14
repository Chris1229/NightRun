package com.zt.nightrun.upyun;

/**
 * Created by xuxinjian on 15/9/22.
 */
public interface IUploadFileCallback extends IApiCallback {
    void onProgress(int percent);//0-100之间
}
