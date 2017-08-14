package com.zt.nightrun.activity;

import android.os.Bundle;

import com.chris.common.view.BaseActivity;
import com.zt.nightrun.R;

import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.IXListViewRefreshListener;
import me.maxwin.view.XListView;

/**
 * 作者：by chris
 * 日期：on 2017/8/5 - 下午6:40.
 * 邮箱：android_cjx@163.com
 */

public class MessageActivity extends BaseActivity implements IXListViewRefreshListener,IXListViewLoadMore {

    private XListView mXListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_message_layout);

        initView();

    }

    private void initView(){
//        setTitle("消息"+"");
        mXListView =(XListView)findViewById(R.id.messageListView);
        mXListView.setPullRefreshEnable(this);
        mXListView.setPullLoadEnable(this);

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onRefresh() {

    }
}
