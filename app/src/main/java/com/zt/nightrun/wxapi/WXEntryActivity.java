package com.zt.nightrun.wxapi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.chris.common.utils.ToastUtils;
import com.chris.common.view.BaseActivity;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.zt.nightrun.NightRunApplication;

/**
 * 作者：by chris
 * 日期：on 2017/7/10 - 下午6:49.
 * 邮箱：android_cjx@163.com
 */

public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {
    private static final int RETURN_MSG_TYPE_LOGIN = 1;
    private static final int RETURN_MSG_TYPE_SHARE = 2;
    private IWXAPI api;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        api = WXAPIFactory.createWXAPI(this, WxConstants.APP_ID, true);
        //如果没回调onResp，八成是这句没有写
        NightRunApplication.getWxApi().handleIntent(getIntent(), this);
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    //app发送消息给微信，处理返回消息的回调
    @Override
    public void onResp(BaseResp resp) {
        Log.i("weixin",resp.errStr);
        Log.i("weixin","错误码 : " + resp.errCode + "");
        switch (resp.errCode) {

            case BaseResp.ErrCode.ERR_AUTH_DENIED:
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                if(RETURN_MSG_TYPE_SHARE == resp.getType()){
                    ToastUtils.show(this,"分享失败");
                }else{
                    ToastUtils.show(this,"登录失败");
                }
                break;
            case BaseResp.ErrCode.ERR_OK:
                switch (resp.getType()) {
                    case RETURN_MSG_TYPE_LOGIN:
                        //拿到了微信返回的code,立马再去请求access_token
                        String code = ((SendAuth.Resp) resp).code;
                        Log.i("weixin","code = " + code);

                        //就在这个地方，用网络库什么的或者自己封的网络api，发请求去咯，注意是get请求

                        break;

                    case RETURN_MSG_TYPE_SHARE:
                        ToastUtils.show(this,"微信分享成功");
                        finish();
                        break;
                }
                break;
        }
    }
}