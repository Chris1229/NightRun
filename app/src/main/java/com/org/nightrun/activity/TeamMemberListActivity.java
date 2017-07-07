package com.org.nightrun.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.chris.common.view.BaseActivity;
import com.org.nightrun.R;
import com.org.nightrun.adapter.MemberListAdapter;

import java.util.Date;

import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.IXListViewRefreshListener;
import me.maxwin.view.XListView;

/**
 * 作者：by chris
 * 日期：on 2017/7/4 - 下午2:39.
 * 邮箱：android_cjx@163.com
 */

public class TeamMemberListActivity extends BaseActivity implements IXListViewRefreshListener,IXListViewLoadMore{

    private MemberListAdapter mAdapter;
    private XListView mListView;

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

        initUI();

    }

    private void initUI(){
        setTitle("组队-队员列表");
        mListView =(XListView)findViewById(R.id.memberListView);
        mAdapter = new MemberListAdapter(this);
        mListView.setAdapter(mAdapter);
        mListView.setPullRefreshEnable(this);
        mListView.setPullLoadEnable(this);

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
