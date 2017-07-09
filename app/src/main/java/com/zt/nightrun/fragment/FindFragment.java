package com.zt.nightrun.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chris.common.view.BaseFragment;
import com.zt.nightrun.R;
import com.zt.nightrun.adapter.FindListViewAdapter;

import java.util.Date;

import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.IXListViewRefreshListener;
import me.maxwin.view.XListView;

/**
 * 作者：by chris
 * 日期：on 2017/6/28 - 下午4:59.
 * 邮箱：android_cjx@163.com
 */

public class FindFragment extends BaseFragment implements IXListViewRefreshListener,IXListViewLoadMore{

    private XListView mXListView;
    private FindListViewAdapter mAdapter;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            mXListView.stopRefresh(new Date());
            mXListView.stopLoadMore();
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

        View view =inflater.inflate(R.layout.fragment_find,null);

        mXListView =(XListView)view.findViewById(R.id.findListView);
        mXListView.setPullRefreshEnable(this);
        mXListView.setPullLoadEnable(this);
        mAdapter = new FindListViewAdapter(getActivity());
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
}
