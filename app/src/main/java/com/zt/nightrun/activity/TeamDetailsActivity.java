package com.zt.nightrun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.error.VolleyError;
import com.chris.common.network.HttpRequestProxy;
import com.chris.common.share.IShareCallback;
import com.chris.common.share.ShareDialog;
import com.chris.common.share.ShareModel;
import com.chris.common.share.ShareUtils;
import com.chris.common.utils.ToastUtils;
import com.chris.common.view.ActionItem;
import com.chris.common.view.BaseActivity;
import com.chris.common.view.CustomDialog;
import com.quncao.core.http.AbsHttpRequestProxy;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.zt.nightrun.R;
import com.zt.nightrun.model.req.ReqActiveGroup;
import com.zt.nightrun.model.req.ReqDeleteGroup;
import com.zt.nightrun.model.req.ReqTeamShareCode;
import com.zt.nightrun.model.resp.RespActiveGroup;
import com.zt.nightrun.model.resp.RespDeleteGroup;
import com.zt.nightrun.model.resp.RespShareCode;

/**
 * 作者：by chris
 * 日期：on 2017/7/9 - 上午11:30.
 * 邮箱：android_cjx@163.com
 */

public class TeamDetailsActivity extends BaseActivity implements View.OnClickListener{

    private RelativeLayout teamListLayout,colorLayout,locationLayout,codeLayout;
    private TextView tvActiveTeam,tvOverTeam;
    private int groupId;
    private ShareUtils shareUtils;
    private boolean flag =true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_team_detail,true);
        Intent intent =getIntent();
        groupId =intent.getIntExtra("groupId",0);
        shareUtils = new ShareUtils(this);
        initUI();
    }

    private void initUI(){
        setRightImage(R.mipmap.share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reqShareCode(groupId);
            }
        });

        teamListLayout =(RelativeLayout)findViewById(R.id.teamListId);
        colorLayout =(RelativeLayout)findViewById(R.id.colorId);
        locationLayout =(RelativeLayout)findViewById(R.id.memberLocationId);
        codeLayout =(RelativeLayout)findViewById(R.id.teamCodeId);
        tvOverTeam =(TextView)findViewById(R.id.overTeam);
        tvActiveTeam =(TextView)findViewById(R.id.openTeam);
        teamListLayout.setOnClickListener(this);
        colorLayout.setOnClickListener(this);
        locationLayout.setOnClickListener(this);
        codeLayout.setOnClickListener(this);
        tvActiveTeam.setOnClickListener(this);
        tvOverTeam.setOnClickListener(this);
        if(flag){
            tvActiveTeam.setText("开启活动");
        }else{
            tvActiveTeam.setText("结束活动");
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.teamListId:
                startActivity(new Intent(this,TeamMemberListActivity.class));
                break;

            case R.id.colorId:

                break;

            case R.id.memberLocationId:
                startActivity(new Intent(this,LocationActivity.class));
                break;


            case R.id.openTeam:
                reqActiveGroup();
                break;

            case R.id.overTeam:
                CustomDialog dialog = new CustomDialog(this, new CustomDialog.OnClickListener() {
                    @Override
                    public void onLeftClick() {

                    }

                    @Override
                    public void onRightClick() {
                        showLoadingDialog();
                        reqDeleteGroup();
                    }
                });

                dialog.setTitle("提示");
                dialog.setContent("确认解散组队");
                dialog.show();
                break;

        }

    }

    private void reqDeleteGroup(){

        ReqDeleteGroup reqDeleteGroup = new ReqDeleteGroup();

        reqDeleteGroup.setGroupId(groupId);
        HttpRequestProxy.get().create(reqDeleteGroup, new AbsHttpRequestProxy.RequestListener() {
            @Override
            public void onSuccess(Object response) {
                dismissLoadingDialog();
                Log.i("info",response.toString());
                RespDeleteGroup respDeleteGroup =(RespDeleteGroup) response;
                if(respDeleteGroup.getCode()==0){
                    ToastUtils.show(TeamDetailsActivity.this,"解散组队成功");
                    finish();

                }else{

                    ToastUtils.show(TeamDetailsActivity.this,"解散组队失败");
                }
            }

            @Override
            public void onFailed(VolleyError error) {
                Log.i("info",error.toString());
                dismissLoadingDialog();
                ToastUtils.show(TeamDetailsActivity.this,error.toString());
            }
        }).tag(this.toString()).build().excute();

    }

    private void reqShareCode(int groupId){
        final ReqTeamShareCode reqShareCode = new ReqTeamShareCode();
        reqShareCode.setGroupId(groupId);
        HttpRequestProxy.get().create(reqShareCode, new AbsHttpRequestProxy.RequestListener() {
            @Override
            public void onSuccess(Object response) {

                Log.i("info",response.toString());
                final RespShareCode respShareCode =(RespShareCode) response;
                if(respShareCode.getData()!=null){
                    if(!TextUtils.isEmpty(respShareCode.getData().getShareUrl())){

                        ShareDialog shareDialog = new ShareDialog(TeamDetailsActivity.this, "分享至", 1);
                        shareDialog.addAction(new ActionItem(TeamDetailsActivity.this, "微信", R.mipmap.icon_weixin));
                        shareDialog.addAction(new ActionItem(TeamDetailsActivity.this, "朋友圈", R.mipmap.icon_friendship));
                        shareDialog.addAction(new ActionItem(TeamDetailsActivity.this, "QQ", R.mipmap.icon_qq1));
                        shareDialog.addAction(new ActionItem(TeamDetailsActivity.this, "新浪微博", R.mipmap.icon_weibo_1));
                        shareDialog.setOnItemClickListener(new ShareDialog.OnItemOnClickListener() {
                            @Override
                            public void onItemClick(ActionItem item, int position) {
                                ShareModel model = new ShareModel();
                                model.setTitle("夜行神器");
                                model.setContent("邀请你加入xx组队");
                                model.setShareUrl(respShareCode.getData().getShareUrl());
                                model.setImageMedia(new UMImage(TeamDetailsActivity.this, ""));

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
                Log.i("info",error.toString());
                ToastUtils.show(TeamDetailsActivity.this,error.toString());
            }
        }).tag(this.toString()).build().excute();

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


    private void reqActiveGroup(){

        ReqActiveGroup reqActiveGroup = new ReqActiveGroup();

        reqActiveGroup.setGroupId(groupId);
        if(flag){
            reqActiveGroup.setActive(true);
        }else{
            reqActiveGroup.setActive(false);
        }
        HttpRequestProxy.get().create(reqActiveGroup, new AbsHttpRequestProxy.RequestListener() {
            @Override
            public void onSuccess(Object response) {
                dismissLoadingDialog();
                Log.i("info",response.toString());
                RespActiveGroup respActiveGroup =(RespActiveGroup) response;
                if(respActiveGroup.getCode()==0){
                    if(flag){
                        ToastUtils.show(TeamDetailsActivity.this,"激活活动成功");
                        flag =false;
                    }else{
                        ToastUtils.show(TeamDetailsActivity.this,"结束活动成功");
                        flag =true;
                    }

                }else{

                    if(flag){
                        ToastUtils.show(TeamDetailsActivity.this,"激活活动失败");
                        flag =false;
                    }else{
                        ToastUtils.show(TeamDetailsActivity.this,"结束活动失败");
                        flag =true;
                    }
                }
            }

            @Override
            public void onFailed(VolleyError error) {
                Log.i("info",error.toString());
                dismissLoadingDialog();
                ToastUtils.show(TeamDetailsActivity.this,error.toString());
            }
        }).tag(this.toString()).build().excute();

    }
}
