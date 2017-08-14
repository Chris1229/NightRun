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
import android.widget.TextView;

import com.android.volley.error.VolleyError;
import com.chris.common.network.HttpRequestProxy;
import com.chris.common.utils.ToastUtils;
import com.chris.common.view.BaseFragment;
import com.quncao.core.http.AbsHttpRequestProxy;
import com.zt.nightrun.activity.DeviceDetailsActivity;
import com.zt.nightrun.activity.TeamDetailsActivity;
import com.zt.nightrun.model.req.ReqActiveDevice;
import com.zt.nightrun.model.req.ReqCreateGroup;
import com.zt.nightrun.model.resp.Group;
import com.zt.nightrun.model.resp.RespActiveDevice;
import com.zt.nightrun.model.resp.RespCreateGroup;
import com.zt.nightrun.view.CustomEditDialog;
import com.zt.nightrun.R;
import com.zt.nightrun.activity.TeamMemberListActivity;
import com.zt.nightrun.adapter.TeamListViewAdapter;
import com.zt.nightrun.view.CustomSuccessDialog;

import java.util.Date;

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
    private TextView mTextViewCreateTeam;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){

                case 0:
                    mXListView.stopRefresh(new Date());
                    mXListView.stopLoadMore();
                    break;


                case 1:

                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        showLoadingDialog();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_team,null);

        mXListView =(XListView) view.findViewById(R.id.teamListView);
        mTextViewCreateTeam =(TextView)view.findViewById(R.id.createTeam);
        mTextViewCreateTeam.setOnClickListener(new View.OnClickListener() {
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

        mAdapter = new TeamListViewAdapter(getActivity());
        mXListView.setAdapter(mAdapter);
        return view;

    }

    @Override
    public void onLoadMore() {
        handler.sendMessageDelayed(handler.obtainMessage(0),3000);
    }

    @Override
    public void onRefresh() {
        handler.sendMessageDelayed(handler.obtainMessage(0),3000);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent(getActivity(), TeamDetailsActivity.class));
    }

    private void reqCreateGroup(String name){

        final ReqCreateGroup reqCreateGroup = new ReqCreateGroup();
        reqCreateGroup.setName(name);
        HttpRequestProxy.get().create(reqCreateGroup, new AbsHttpRequestProxy.RequestListener() {
            @Override
            public void onSuccess(Object response) {

                Log.i("info",response.toString());
                RespCreateGroup respCreateGroup =(RespCreateGroup) response;
                if(respCreateGroup.getCode()==0){
                    dismissLoadingDialog();
                    ToastUtils.show(getActivity(),"创建组队成功");
                    Group group =respCreateGroup.getData().getGroup();
                    String content ="你已成功创建组队"+group.getName()+"现在通过微信邀请你的好友来参加吧";
                    CustomSuccessDialog successDialog = new CustomSuccessDialog(getActivity(), content, new CustomSuccessDialog.OnClickListener() {
                        @Override
                        public void onLeftClick(CustomSuccessDialog dialog) {
                            dialog.dismiss();

                        }

                        @Override
                        public void onRightClick(CustomSuccessDialog dialog) {
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
}
