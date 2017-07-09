package com.zt.nightrun.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.chris.common.view.BaseFragment;
import com.zt.nightrun.R;
import com.zt.nightrun.activity.DeviceDetailsActivity;
import com.zt.nightrun.adapter.DeviceListViewAdapter;

import java.util.Date;

import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.IXListViewRefreshListener;
import me.maxwin.view.XListView;

/**
 * 作者：by chris
 * 日期：on 2017/6/28 - 下午4:59.
 * 邮箱：android_cjx@163.com
 */

public class DeviceFragment extends BaseFragment implements AdapterView.OnItemClickListener,IXListViewRefreshListener,IXListViewLoadMore {

    private XListView mListView;
    private DeviceListViewAdapter deviceListViewAdapter;
    private View headView;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            mListView.stopRefresh(new Date());
            mListView.stopLoadMore();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        headView =LayoutInflater.from(getActivity()).inflate(R.layout.listview_head_foot_layout,null);
//        showLoadingDialog();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_device,null);
        mListView =(XListView) view.findViewById(R.id.listView);
        deviceListViewAdapter = new DeviceListViewAdapter(getActivity());
        mListView.addHeaderView(headView);
//        mListView.addFooterView(headView);
        mListView.setPullRefreshEnable(this);
        mListView.setPullLoadEnable(this);
        mListView.setAdapter(deviceListViewAdapter);
        mListView.setOnItemClickListener(this);
        return view;

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent(getActivity(), DeviceDetailsActivity.class));
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
