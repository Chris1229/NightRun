package com.chris.common.share;

import java.util.Map;

/**
 * Created by zhangxiaolong on 2016/4/14.
 */
public interface ILoginCallback {
    void onSuccess(Map<String, String> data);
    void onFailed(String msg);
    void onCancel();
}
