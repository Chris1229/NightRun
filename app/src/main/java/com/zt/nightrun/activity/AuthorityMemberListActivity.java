package com.zt.nightrun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.android.volley.error.VolleyError;
import com.chris.common.network.HttpRequestProxy;
import com.chris.common.utils.ToastUtils;
import com.chris.common.view.BaseActivity;
import com.chris.common.view.CustomDialog;
import com.quncao.core.http.AbsHttpRequestProxy;
import com.zt.nightrun.R;
import com.zt.nightrun.adapter.AuthorityMemberListAdapter;
import com.zt.nightrun.model.req.ReqActiveDevice;
import com.zt.nightrun.model.req.ReqDeleteFriend;
import com.zt.nightrun.model.req.ReqFriendsList;
import com.zt.nightrun.model.resp.Friend;
import com.zt.nightrun.model.resp.RespActiveDevice;
import com.zt.nightrun.model.resp.RespDeleteFriend;
import com.zt.nightrun.model.resp.RespFriendsList;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.IXListViewRefreshListener;
import me.maxwin.view.XListView;

/**
 * 作者：by chris
 * 日期：on 2017/7/4 - 下午3:49.
 * 邮箱：android_cjx@163.com
 */

public class AuthorityMemberListActivity extends BaseActivity implements IXListViewRefreshListener,IXListViewLoadMore,AdapterView.OnItemLongClickListener{

    private XListView mXListView;
    private AuthorityMemberListAdapter mAdapter;
    private int deviceId;
    private TextView tvNoData;
    private List<Friend> list;
    private int delPosition;

//    private Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//
//            mXListView.stopRefresh(new Date());
//            mXListView.stopLoadMore();
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_authority_list_layout,true);

        Intent intent =getIntent();
        deviceId =intent.getIntExtra("deviceId",0);
        initUI();

        reqFriendList();
    }


    private void initUI(){

        setTitle("授权设备给亲友");
        tvNoData =(TextView)findViewById(R.id.tvNoData);
        mXListView =(XListView)findViewById(R.id.authorityListView);
        mXListView.setPullRefreshEnable(this);
        mXListView.disablePullLoad();
        mXListView.setOnItemLongClickListener(this);
//        mXListView.setPullLoadEnable(this);

    }

    @Override
    public void onLoadMore() {
//        handler.sendMessageDelayed(handler.obtainMessage(0),3000);
    }

    @Override
    public void onRefresh() {
        reqFriendList();
//        handler.sendMessageDelayed(handler.obtainMessage(0),3000);
    }

    private void reqFriendList(){

        final ReqFriendsList reqFriendsList = new ReqFriendsList();

        reqFriendsList.setDeviceId(deviceId);
        HttpRequestProxy.get().create(reqFriendsList, new AbsHttpRequestProxy.RequestListener() {
            @Override
            public void onSuccess(Object response) {
                mXListView.stopRefresh(new Date());
                Log.i("info",response.toString());
                RespFriendsList respFriendsList =(RespFriendsList)response;
                if(respFriendsList.getData()!=null && respFriendsList.getData().getListDeviceFriend()!=null && respFriendsList.getData().getListDeviceFriend().size()>0){
                    if(list ==null){
                        list = new ArrayList<Friend>();
                    }else{
                        list.clear();
                    }
                    list.addAll(respFriendsList.getData().getListDeviceFriend());
                    mXListView.setVisibility(View.VISIBLE);
                    tvNoData.setVisibility(View.GONE);
                    mAdapter = new AuthorityMemberListAdapter(AuthorityMemberListActivity.this,list);
                    mXListView.setAdapter(mAdapter);

                }else{

                    mXListView.setVisibility(View.GONE);
                    tvNoData.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailed(VolleyError error) {
                mXListView.stopRefresh(new Date());
                Log.i("info",error.toString());
                ToastUtils.show(AuthorityMemberListActivity.this,error.toString());
            }
        }).tag(this.toString()).build().excute();

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

        CustomDialog dialog = new CustomDialog(AuthorityMemberListActivity.this, new CustomDialog.OnClickListener() {
            @Override
            public void onLeftClick() {

            }

            @Override
            public void onRightClick() {
                delPosition =position -1;

                reqDeleteFriend(list.get(position-1).getDeviceFriendId());
            }
        });

        dialog.setTitle("提示");
        dialog.setContent("确认删除该好友");
        dialog.show();

        return false;
    }

    private void reqDeleteFriend(int friendId){

        final ReqDeleteFriend reqDeleteFriend = new ReqDeleteFriend();
        reqDeleteFriend.setFriendId(friendId);
        HttpRequestProxy.get().create(reqDeleteFriend, new AbsHttpRequestProxy.RequestListener() {
            @Override
            public void onSuccess(Object response) {
                Log.i("info",response.toString());
                RespDeleteFriend respDeleteFriend =(RespDeleteFriend) response;
                if(respDeleteFriend.getCode()==0){
                    ToastUtils.show(AuthorityMemberListActivity.this,"删除好友成功");
                    list.remove(delPosition);
                    mAdapter.notifyDataSetChanged();
                    if(list.size()==0){
                        mXListView.setVisibility(View.GONE);
                        tvNoData.setVisibility(View.VISIBLE);
                    }
                }else{
                    ToastUtils.show(AuthorityMemberListActivity.this,"删除好友失败");
                }
            }

            @Override
            public void onFailed(VolleyError error) {
                Log.i("info",error.toString());
                ToastUtils.show(AuthorityMemberListActivity.this,error.toString());
            }
        }).tag(this.toString()).build().excute();
    }
}
