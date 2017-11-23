package com.zt.nightrun.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chris.common.view.BaseActivity;
import com.zt.db.MessageDao;
import com.zt.nightrun.R;
import com.zt.nightrun.adapter.MessageListAdapter;
import com.zt.nightrun.db.DbManager;
import com.zt.nightrun.db.Message;

import java.util.ArrayList;
import java.util.List;

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
    private List<Message> pushMessageList = new ArrayList<>();
    private MessageListAdapter adapter;
    private TextView tvNoData;
    private MessageDao mMessageDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_message_layout,true);

        mMessageDao = DbManager.getDaoSession(this).getMessageDao();

        pushMessageList =mMessageDao.loadAll();

        System.out.println(pushMessageList.toString());

        initView();

    }

    private void initView(){
        setTitle("消息");
        mXListView =(XListView)findViewById(R.id.messageListView);
        tvNoData =(TextView)findViewById(R.id.tvNoData);
        mXListView.setPullRefreshEnable(this);
        mXListView.setPullLoadEnable(this);
        mXListView.disablePullLoad();

        initPushData();
    }


    private void initPushData(){

        if(pushMessageList.size()>0){
            adapter = new MessageListAdapter(this,pushMessageList);
            mXListView.setAdapter(adapter);
        }else{
            mXListView.setVisibility(View.GONE);
            tvNoData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onRefresh() {

    }
}
