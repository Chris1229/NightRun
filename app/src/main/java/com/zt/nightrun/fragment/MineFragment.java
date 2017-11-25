package com.zt.nightrun.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chris.common.share.ILoginCallback;
import com.chris.common.share.IShareCallback;
import com.chris.common.share.ShareDialog;
import com.chris.common.share.ShareModel;
import com.chris.common.share.ShareUtils;
import com.chris.common.utils.SharedPreferencesUtil;
import com.chris.common.utils.ToastUtils;
import com.chris.common.view.ActionItem;
import com.chris.common.view.BaseFragment;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.zt.nightrun.NightRunApplication;
import com.zt.nightrun.R;
import com.zt.nightrun.activity.EnterUIDActivity;
import com.zt.nightrun.activity.FindPassWordActivity;
import com.zt.nightrun.activity.LoginActivity;
import com.zt.nightrun.activity.PersonInfoActivity;
import com.zt.nightrun.view.CustomEditDialog;

import java.util.Map;

/**
 * 作者：by chris
 * 日期：on 2017/6/28 - 下午4:59.
 * 邮箱：android_cjx@163.com
 */

public class MineFragment extends BaseFragment implements View.OnClickListener{

    private RelativeLayout mPersonInfoLayout, mTuiJianLayout,mWeixinLayout,mRevisePassWordLayout,mRefundLayout,mHelpLayout,mAboutLayout;
    private ShareUtils shareUtils;
    private TextView tvLoginOut;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shareUtils = new ShareUtils(getActivity());
//        showLoadingDialog();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_mine,null);
        mPersonInfoLayout =(RelativeLayout)view.findViewById(R.id.personInfoId);
        mTuiJianLayout =(RelativeLayout)view.findViewById(R.id.tuijianId);
        mWeixinLayout =(RelativeLayout)view.findViewById(R.id.bangdingweixinId);
        mRevisePassWordLayout =(RelativeLayout)view.findViewById(R.id.revisepasswordId);
        mRefundLayout =(RelativeLayout)view.findViewById(R.id.refundId);
        mHelpLayout =(RelativeLayout)view.findViewById(R.id.helpId);
        mAboutLayout =(RelativeLayout)view.findViewById(R.id.aboutId);
        tvLoginOut =(TextView)view.findViewById(R.id.loginOutId);
        mPersonInfoLayout.setOnClickListener(this);
        mTuiJianLayout.setOnClickListener(this);
        mWeixinLayout.setOnClickListener(this);
        mRevisePassWordLayout.setOnClickListener(this);
        mRefundLayout.setOnClickListener(this);
        mHelpLayout.setOnClickListener(this);
        mAboutLayout.setOnClickListener(this);
        tvLoginOut.setOnClickListener(this);
        return view;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.personInfoId:
                startActivity(new Intent(getActivity(), PersonInfoActivity.class));
                break;

            case R.id.tuijianId:
                ShareDialog shareDialog = new ShareDialog(getActivity(), "分享至", 1);
                shareDialog.addAction(new ActionItem(getActivity(), "微信", R.mipmap.icon_weixin));
                shareDialog.addAction(new ActionItem(getActivity(), "朋友圈", R.mipmap.icon_friendship));
                shareDialog.addAction(new ActionItem(getActivity(), "QQ", R.mipmap.icon_qq1));
                shareDialog.addAction(new ActionItem(getActivity(), "新浪微博", R.mipmap.icon_weibo_1));
                shareDialog.setOnItemClickListener(new ShareDialog.OnItemOnClickListener() {
                    @Override
                    public void onItemClick(ActionItem item, int position) {
                        ShareModel model = new ShareModel();
                        model.setTitle("夜行神器");
                        model.setContent("推荐给好友");
                        model.setShareUrl("http://www.baidu.com");
                        model.setImageMedia(new UMImage(getActivity(), ""));

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
                                    ToastUtils.show(getActivity(), "您还未安装此应用");
                                }
                                break;
                            default:
                                break;
                        }
                    }
                });

                shareDialog.showDialog();
                break;


            case R.id.bangdingweixinId:
                shareUtils.login(SHARE_MEDIA.WEIXIN, new ILoginCallback() {
                    @Override
                    public void onSuccess(Map<String, String> data) {
                        String id = data.get("openid");
                        String url = data.get("profile_image_url");
                        String uniond = data.get("unionid");
                        String thirdNickname = data.get("screen_name");
                        Log.i("info======---","success");
//                        reqDataBind(id, url, uniond, thirdNickname);
                    }

                    @Override
                    public void onFailed(String msg) {
                        Log.i("info======---","onFailed"+msg);
                    }

                    @Override
                    public void onCancel() {
                        Log.i("info======---","onCancel");
                    }
                });
                break;


            case R.id.revisepasswordId:
                startActivity(new Intent(getActivity(), FindPassWordActivity.class));
                break;


            case R.id.refundId:

                break;


            case R.id.helpId:

                break;


            case R.id.aboutId:
//                CustomEditDialog dialog = new CustomEditDialog(getActivity(), "设备蓝牙", "请输入一个蓝牙名称", InputType.TYPE_CLASS_TEXT, new CustomEditDialog.OnOkListener() {
//                    @Override
//                    public void onOkClick(CustomEditDialog dialog, String string) {
//
//                        if(!TextUtils.isEmpty(string)){
//                            SharedPreferencesUtil.saveString(getActivity(),"blueMac",string);
//                            dialog.dismiss();
//                        }else{
//                            ToastUtils.show(getActivity(),"请输入一个蓝牙名称");
//                        }
//                    }
//                });
//
//                dialog.show();
                break;

            case R.id.loginOutId:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                NightRunApplication.getInstance().finishAllActivity();
                SharedPreferencesUtil.saveInteger(getActivity(),"userId",-1);
                break;

        }
    }

    IShareCallback iShareCallback = new IShareCallback() {
        @Override
        public void onSuccess() {
            ToastUtils.show(getActivity(), "分享成功");
        }

        @Override
        public void onFailed() {
            ToastUtils.show(getActivity(), "分享失败");
        }

        @Override
        public void onCancel() {
            ToastUtils.show(getActivity(), "取消分享");
        }
    };
}
