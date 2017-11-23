package com.chris.common.share;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;
import android.util.Log;

import com.chris.common.R;
import com.chris.common.utils.ToastUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangxiaolong on 2016/4/14.
 */
public class ShareUtils {
    private UMShareAPI mShareAPI = null;
    private Activity c;
    private ILoginCallback loginCallback;
    private IShareCallback shareCallback;

    public ShareUtils(Activity context) {
        this.c = context;
        if (mShareAPI == null) {
            mShareAPI = UMShareAPI.get(context);
        }
    }

    public void login(SHARE_MEDIA platform, ILoginCallback callback) {
        this.loginCallback = callback;
        Log.d("zxl", "444444444");
        mShareAPI.doOauthVerify(c, platform, umAuthListener);
    }

    public void share(ShareModel model, IShareCallback shareCallback) {
        this.shareCallback = shareCallback;
        UMWeb web = new UMWeb(model.getShareUrl());
        web.setTitle(model.getTitle());
        web.setThumb(new UMImage(c,R.mipmap.logo));
        web.setDescription(model.getContent());
        new ShareAction(c).setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE).withMedia(web).setCallback(umShareListener).open();
//        new ShareAction(c).setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
//                .withTitle(model.getTitle())
//                .withText(model.getContent())
//                .withTargetUrl(model.getShareUrl())
//                .withMedia(model.getImageMedia())
//                .setCallback(umShareListener)
//                .open();
    }

    private void initShareIntent(String type, String content) {
        boolean found = false;
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        // gets the list of intents that can be loaded.
        List<ResolveInfo> resInfo = c.getPackageManager().queryIntentActivities(share, 0);
        if (!resInfo.isEmpty()) {
            for (ResolveInfo info : resInfo) {
                if (info.activityInfo.packageName.toLowerCase().contains(type) ||
                        info.activityInfo.name.toLowerCase().contains(type)) {
                    share.putExtra(Intent.EXTRA_TEXT, content);

                    /**copy from internet ，caused some amazing problem，*/
//                    share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    // drunk fix later
                    share.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);


                    share.setComponent(new ComponentName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity"));
                    found = true;
                    break;
                }
            }
            if (!found) {
                ToastUtils.show(c, "您尚未安装QQ");
                return;
            }

            c.startActivity(share);
        }
    }

    public void share(final SHARE_MEDIA platform, final ShareModel model, IShareCallback shareCallback) {
        this.shareCallback = shareCallback;
//        if (model.isInvite()) {
//            //如果是邀请好友分享则直接请求一个短链接来分享，不用传过来的URL
//            if (!model.isLogin()) {
//                CustomDialog customDialog = new CustomDialog(c, new CustomDialog.OnClickListener() {
//                    @Override
//                    public void onLeftClick() {
//
//                    }
//
//                    @Override
//                    public void onRightClick() {
//                        Intent intent = new Intent();
//                        ComponentName componentName;
//                        if (model.isSecondLogin()) {
//                            componentName = new ComponentName(c, "com.quncao.lark.activity.user.SecondLoginActivity");
//                        } else {
//                            componentName = new ComponentName(c, "com.quncao.lark.activity.user.LoginActivity");
//                        }
//                        intent.setComponent(componentName);
//                        c.startActivity(intent);
//                        c.overridePendingTransition(R.anim.activity_open, R.anim.fake_anim);
//                    }
//                });
//                customDialog.setTitle("请登录后使用该功能");
//                customDialog.setRight("快速登录");
//                customDialog.setLeft("取消");
//                customDialog.setRightColor("#ed4d4d");
//                customDialog.show();
//                return;
//            }
//        }
        if (!TextUtils.isEmpty(model.getShareUrl())) {
            shareWithUrl(platform, model);
        } else {
            shareWithOutUrl(platform, model);
        }

    }

    private boolean isPureText(ShareModel model){
        UMImage imageMedia = model.getImageMedia();
        return TextUtils.isEmpty(model.getShareUrl()) && (null == imageMedia || TextUtils.isEmpty(imageMedia.asUrlImage()));
    }

    private void pureTextShare(SHARE_MEDIA platform, String shareText){
        if(SHARE_MEDIA.QQ == platform){
            initShareIntent("com.tencent.mobileqq", shareText);
        }else{
            new ShareAction(c)
                    .setPlatform(platform)
                    .withText(shareText)
                    .setCallback(umShareListener)
                    .share();
        }
    }
    public void pureTextShare(SHARE_MEDIA platform, String shareText, IShareCallback listener){
        this.shareCallback = listener;
        pureTextShare(platform, shareText);
    }

    private void shareWithUrl(SHARE_MEDIA platform, ShareModel model) {
        if(isPureText(model)){
            pureTextShare(platform, model.getContent(), shareCallback);
        }else {
            UMWeb web = new UMWeb(model.getShareUrl());
            web.setTitle(model.getTitle());
            web.setThumb(new UMImage(c,R.mipmap.logo));
            web.setDescription(model.getContent());
            new ShareAction(c).setPlatform(platform).withMedia(web).setCallback(umShareListener).share();
        }
    }

    private void shareWithOutUrl(final SHARE_MEDIA platform, final ShareModel model) {
        if(isPureText(model)){
            pureTextShare(platform, model.getContent());
            return;
        }

        if (platform != SHARE_MEDIA.SINA) {
            new ShareAction(c).setPlatform(platform)
                    .withMedia(model.getImageMedia())
                    .setCallback(umShareListener)
                    .share();
        } else {
            UMWeb web = new UMWeb(model.getShareUrl());
            web.setTitle(model.getTitle());
            web.setThumb(new UMImage(c,R.mipmap.logo));
            web.setDescription(model.getContent());
            new ShareAction(c).setPlatform(platform).withMedia(web).setCallback(umShareListener).share();
        }
    }

    //检测QQ是否安装
    public boolean isQQInstalled() {
        return isAvailable(c, "com.tencent.mobileqq");
    }

    //检测微信是否安装
    public boolean isWeixinInstalled() {
        return isAvailable(c, "com.tencent.mm");
    }

    //检测新浪微博是否安装
    public boolean isSinaInstalled() {
        return isAvailable(c, "com.sina.weibo");
    }

    /**
     * 判断手机已安装某程序的方法
     *
     * @param context
     * @param packageName
     * @return
     */
    private static boolean isAvailable(Context context, String packageName) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            //e.printStackTrace();
        }
        if (packageInfo == null) {
            return false;
        } else {
            return true;
        }
    }

    /**********
     * 内部方法
     ***********/
    private UMAuthListener umAuthListener = new UMAuthListener() {

        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Log.d("zxl", "登陆授权获取成功" + data);
            mShareAPI.getPlatformInfo(c, platform, getInfoListener);
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Log.d("zxl", "000000000");
            loginCallback.onFailed("授权失败");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Log.d("zxl", "111111111");
            loginCallback.onCancel();
        }
    };

    private UMAuthListener getInfoListener = new UMAuthListener() {

        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Log.d("zxl", "获取用户信息成功" + data);
            if (data != null) {
                loginCallback.onSuccess(data);
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Log.d("zxl", "22222222");
            loginCallback.onFailed("获取用户信息失败");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Log.d("zxl", "33333333");
            loginCallback.onCancel();
        }
    };

    private UMShareListener umShareListener = new UMShareListener() {

        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            shareCallback.onSuccess();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            shareCallback.onFailed();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            shareCallback.onCancel();
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mShareAPI.onActivityResult(requestCode, resultCode, data);
    }
}
