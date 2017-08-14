package com.chris.common.share;

/**
 * Created by zhangxiaolong on 2016/4/14.
 */
public interface IShareCallback {
    void onSuccess();
    void onFailed();
    void onCancel();
}
