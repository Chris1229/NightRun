package com.zt.nightrun.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.android.volley.error.VolleyError;
import com.baidu.mapapi.map.Text;
import com.chris.common.network.HttpRequestProxy;
import com.chris.common.utils.ToastUtils;
import com.chris.common.view.BaseActivity;
import com.chris.common.view.CustomDialog;
import com.quncao.core.http.AbsHttpRequestProxy;
import com.zt.nightrun.R;
import com.zt.nightrun.adapter.MemberListAdapter;
import com.zt.nightrun.model.req.ReqActiveDevice;
import com.zt.nightrun.model.req.ReqGroupMember;
import com.zt.nightrun.model.resp.GroupUser;
import com.zt.nightrun.model.resp.RespActiveDevice;
import com.zt.nightrun.model.resp.RespGroupMember;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.IXListViewRefreshListener;
import me.maxwin.view.XListView;

/**
 * 作者：by chris
 * 日期：on 2017/7/4 - 下午2:39.
 * 邮箱：android_cjx@163.com
 */

public class TeamMemberListActivity extends BaseActivity implements IXListViewRefreshListener,IXListViewLoadMore,AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener{

    private MemberListAdapter mAdapter;
    private XListView mListView;
    private List<GroupUser> lists = new ArrayList<>();
    private int groupId;
    private TextView tvNoData;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            mListView.stopRefresh(new Date());
            mListView.stopLoadMore();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_member_list_layout,true);

        Intent intent =getIntent();
        groupId =intent.getIntExtra("groupId",0);
        initUI();
        showLoadingDialog();
    }

    private void initUI(){
        setTitle("组队-队员列表");
        mListView =(XListView)findViewById(R.id.memberListView);
        tvNoData =(TextView) findViewById(R.id.tvNoData);
        mListView.setPullRefreshEnable(this);
        mListView.setPullLoadEnable(this);
        mListView.disablePullLoad();
        mListView.setOnItemClickListener(this);
        mListView.setOnItemLongClickListener(this);
        reqGroupMember();

    }

    @Override
    public void onLoadMore() {
//        handler.sendMessageDelayed(handler.obtainMessage(0),3000);
    }

    @Override
    public void onRefresh() {
        reqGroupMember();
    }

    private void reqGroupMember(){
        final ReqGroupMember reqGroupMember = new ReqGroupMember();

        reqGroupMember.setGroupId(groupId);
        HttpRequestProxy.get().create(reqGroupMember, new AbsHttpRequestProxy.RequestListener() {
            @Override
            public void onSuccess(Object response) {
                dismissLoadingDialog();
                mListView.stopRefresh(new Date());
                Log.i("info",response.toString());
                RespGroupMember respGroupMember =(RespGroupMember) response;
                if(respGroupMember.getCode()==0){
                    if(respGroupMember.getData()!=null && respGroupMember.getData().getListGroupUser()!=null && respGroupMember.getData().getListGroupUser().size()>0){
                        lists.clear();
                        lists.addAll(respGroupMember.getData().getListGroupUser());
                        mListView.setVisibility(View.VISIBLE);
                        tvNoData.setVisibility(View.GONE);
                        mAdapter = new MemberListAdapter(TeamMemberListActivity.this,lists,groupId);
                        mListView.setAdapter(mAdapter);
                    }else{
                        mListView.setVisibility(View.GONE);
                        tvNoData.setVisibility(View.VISIBLE);
                    }

                }else{
                    ToastUtils.show(TeamMemberListActivity.this,"获取组队成员失败");
                }
            }

            @Override
            public void onFailed(VolleyError error) {
                Log.i("info",error.toString());
                dismissLoadingDialog();
                mListView.stopRefresh(new Date());
                ToastUtils.show(TeamMemberListActivity.this,error.toString());
            }
        }).tag(this.toString()).build().excute();


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        CustomDialog customDialog = new CustomDialog(this, new CustomDialog.OnClickListener() {
            @Override
            public void onLeftClick() {

            }

            @Override
            public void onRightClick() {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+lists.get(position-1).getUser().getMobile()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        customDialog.setTitle("拨打电话");
        customDialog.setContent("拨打该队员电话:"+lists.get(position-1).getUser().getMobile());
        customDialog.show();
        return false;
    }
}
