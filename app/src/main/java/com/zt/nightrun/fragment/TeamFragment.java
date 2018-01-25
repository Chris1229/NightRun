package com.zt.nightrun.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.error.VolleyError;
import com.chris.common.network.HttpRequestProxy;
import com.chris.common.share.IShareCallback;
import com.chris.common.share.ShareDialog;
import com.chris.common.share.ShareModel;
import com.chris.common.share.ShareUtils;
import com.chris.common.utils.ToastUtils;
import com.chris.common.view.ActionItem;
import com.chris.common.view.BaseFragment;
import com.quncao.core.http.AbsHttpRequestProxy;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.zt.nightrun.activity.DeviceDetailsActivity;
import com.zt.nightrun.activity.TeamDetailsActivity;
import com.zt.nightrun.model.req.ReqActiveDevice;
import com.zt.nightrun.model.req.ReqCreateGroup;
import com.zt.nightrun.model.req.ReqMineTeamList;
import com.zt.nightrun.model.req.ReqTeamShareCode;
import com.zt.nightrun.model.resp.Group;
import com.zt.nightrun.model.resp.GroupVos;
import com.zt.nightrun.model.resp.RespActiveDevice;
import com.zt.nightrun.model.resp.RespAddGroup;
import com.zt.nightrun.model.resp.RespCreateGroup;
import com.zt.nightrun.model.resp.RespMineTeamList;
import com.zt.nightrun.model.resp.RespShareCode;
import com.zt.nightrun.view.CustomEditDialog;
import com.zt.nightrun.R;
import com.zt.nightrun.activity.TeamMemberListActivity;
import com.zt.nightrun.adapter.TeamListViewAdapter;
import com.zt.nightrun.view.CustomSuccessDialog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.IXListViewRefreshListener;
import me.maxwin.view.XListView;

/**
 * 作者：by chris
 * 日期：on 2017/6/28 - 下午4:59.
 * 邮箱：android_cjx@163.com
 */

public class TeamFragment extends BaseFragment implements IXListViewRefreshListener,IXListViewLoadMore,AdapterView.OnItemClickListener{
    private XListView mXListView;
    private TeamListViewAdapter mAdapter;
    private List<GroupVos> groupVosList = new ArrayList<>();
    private View headView;
    private LinearLayout headLayout;
    private ShareUtils shareUtils;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mXListView.stopRefresh(new Date());
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        headView =LayoutInflater.from(getActivity()).inflate(R.layout.team_list_head,null);
//        showLoadingDialog();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_team,null);

        mXListView =(XListView) view.findViewById(R.id.teamListView);
        mXListView.addHeaderView(headView);
        headLayout =(LinearLayout) view.findViewById(R.id.addTeamLinear);
        headLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CustomEditDialog dialog = new CustomEditDialog(getActivity(),"添加组队","输入组队名称", InputType.TYPE_CLASS_TEXT, new CustomEditDialog.OnOkListener() {
                    @Override
                    public void onOkClick(CustomEditDialog dialog, String string) {
                        if(!TextUtils.isEmpty(string)){
                            dialog.dismiss();
                            showLoadingDialog();
                            reqCreateGroup(string);
                        }else{
                            ToastUtils.show(getActivity(),"请输入组队名称");
                        }
                    }
                });

                dialog.show();

            }
        });
        mXListView.setOnItemClickListener(this);
        mXListView.setPullRefreshEnable(this);
        mXListView.setPullLoadEnable(this);
        mXListView.disablePullLoad();

        mAdapter = new TeamListViewAdapter(getActivity(),groupVosList);
        mXListView.setAdapter(mAdapter);
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();

        mXListView.startRefresh();
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onRefresh() {
        reqMineTeamList();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        position =position-2;

        if(position>=0){
            Intent intent = new Intent(getActivity(),TeamDetailsActivity.class);
            intent.putExtra("groupId",groupVosList.get(position).getGroup().getId());
            intent.putExtra("vCode",groupVosList.get(position).getGroup().getQrcode());
            intent.putExtra("ownerId",groupVosList.get(position).getGroup().getOwnerId());
            intent.putExtra("status",groupVosList.get(position).getGroup().getStatus());
            intent.putExtra("teamNick",groupVosList.get(position).getGroup().getName());
            if(!TextUtils.isEmpty(groupVosList.get(position).getGroup().getImage())){
                intent.putExtra("teamImg",groupVosList.get(position).getGroup().getImage());
            }
            startActivity(intent);
        }
    }

    private void reqCreateGroup(String name){

        final ReqCreateGroup reqCreateGroup = new ReqCreateGroup();
        reqCreateGroup.setName(name);
        HttpRequestProxy.get().create(reqCreateGroup, new AbsHttpRequestProxy.RequestListener() {
            @Override
            public void onSuccess(Object response) {

                Log.i("info",response.toString());
                final RespCreateGroup respCreateGroup =(RespCreateGroup) response;
                Log.i("info=====",respCreateGroup.toString());
                if(respCreateGroup.getCode()==0){
                    dismissLoadingDialog();
                    mXListView.startRefresh();
                    ToastUtils.show(getActivity(),"创建组队成功");
                    Group group =respCreateGroup.getData().getGroup();
                    String content ="你已成功创建组队"+group.getName()+"邀请你的好友来参加吧";
                    CustomSuccessDialog successDialog = new CustomSuccessDialog(getActivity(), content,respCreateGroup.getData().getGroup().getQrcode(),new CustomSuccessDialog.OnClickListener() {
                        @Override
                        public void onLeftClick(CustomSuccessDialog dialog) {
                            dialog.dismiss();

                        }

                        @Override
                        public void onRightClick(CustomSuccessDialog dialog) {
//                            dialog.dismiss();
                            showLoadingDialog();
                            reqShareCode(respCreateGroup.getData().getGroup().getId());
                            dialog.dismiss();
                        }
                    });
                    successDialog.show();
                }else{
                    ToastUtils.show(getActivity(),"创建组队失败");
                }
            }

            @Override
            public void onFailed(VolleyError error) {
                Log.i("info",error.toString());
                dismissLoadingDialog();
                ToastUtils.show(getActivity(),error.toString());
            }
        }).tag(this.toString()).build().excute();

    }

    private void reqMineTeamList(){

        final ReqMineTeamList reqMineTeamList = new ReqMineTeamList();
        HttpRequestProxy.get().create(reqMineTeamList, new AbsHttpRequestProxy.RequestListener() {
            @Override
            public void onSuccess(Object response) {
                handler.sendEmptyMessageDelayed(0,1000);
                Log.i("info",response.toString());
                RespMineTeamList respMineTeamList =(RespMineTeamList) response;
                if(respMineTeamList.getCode()==0){
                    if (respMineTeamList.getData() != null && respMineTeamList.getData().getGroupVos() != null && respMineTeamList.getData().getGroupVos().size() > 0) {
                        groupVosList.clear();
                        groupVosList.addAll(respMineTeamList.getData().getGroupVos());
                        mAdapter.notifyDataSetChanged();

                    } else {
                        ToastUtils.show(getActivity(),"暂无组队，请创建组队");
                    }

                }else{
                    ToastUtils.show(getActivity(),"获取组队列表失败");
                }
            }

            @Override
            public void onFailed(VolleyError error) {
                Log.i("info",error.toString());
                handler.sendEmptyMessageDelayed(0,1000);
                ToastUtils.show(getActivity(),error.toString());
            }
        }).tag(this.toString()).build().excute();

    }

    private void reqShareCode(int groupId){
        final ReqTeamShareCode reqShareCode = new ReqTeamShareCode();
        reqShareCode.setGroupId(groupId);
        HttpRequestProxy.get().create(reqShareCode, new AbsHttpRequestProxy.RequestListener() {
            @Override
            public void onSuccess(Object response) {
                dismissLoadingDialog();
                Log.i("info",response.toString());
                final RespShareCode respShareCode =(RespShareCode) response;
                if(respShareCode.getData()!=null){
                    if(!TextUtils.isEmpty(respShareCode.getData().getShareUrl())){
                        shareUtils = new ShareUtils(getActivity());
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
                                model.setContent("邀请你加入xx组队");
                                model.setShareUrl(respShareCode.getData().getShareUrl());
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
                                            ToastUtils.show(getActivity().getApplicationContext(), "您还未安装此应用");
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
                dismissLoadingDialog();
                ToastUtils.show(getActivity(),error.toString());
            }
        }).tag(this.toString()).build().excute();

    }

    IShareCallback iShareCallback = new IShareCallback() {
        @Override
        public void onSuccess() {
            ToastUtils.show(getActivity().getApplicationContext(), "分享成功");
        }

        @Override
        public void onFailed() {
            ToastUtils.show(getActivity().getApplicationContext(), "分享失败");
        }

        @Override
        public void onCancel() {
            ToastUtils.show(getActivity().getApplicationContext(), "取消分享");
        }
    };

}
