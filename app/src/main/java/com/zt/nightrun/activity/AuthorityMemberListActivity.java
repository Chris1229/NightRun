package com.zt.nightrun.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.chris.common.view.BaseActivity;
import com.zt.nightrun.R;
import com.zt.nightrun.adapter.AuthorityMemberListAdapter;

import java.util.Date;

import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.IXListViewRefreshListener;
import me.maxwin.view.XListView;

/**
 * 作者：by chris
 * 日期：on 2017/7/4 - 下午3:49.
 * 邮箱：android_cjx@163.com
 */

public class AuthorityMemberListActivity extends BaseActivity implements IXListViewRefreshListener,IXListViewLoadMore{

    private XListView mXListView;
    private AuthorityMemberListAdapter mAdapter;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            mXListView.stopRefresh(new Date());
            mXListView.stopLoadMore();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_authority_list_layout,true);

        initUI();
    }


    private void initUI(){

        setTitle("授权设备给亲友");
        setRightImage(R.mipmap.share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mXListView =(XListView)findViewById(R.id.authorityListView);
        mXListView.setPullRefreshEnable(this);
        mXListView.setPullLoadEnable(this);
        mAdapter = new AuthorityMemberListAdapter(this);
        mXListView.setAdapter(mAdapter);

    }

    @Override
    public void onLoadMore() {
        handler.sendMessageDelayed(handler.obtainMessage(0),3000);
    }

    @Override
    public void onRefresh() {
        handler.sendMessageDelayed(handler.obtainMessage(0),3000);
    }
}
