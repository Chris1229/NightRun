package com.org.nightrun.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.chris.common.view.BaseFragment;
import com.org.nightrun.R;
import com.org.nightrun.activity.TeamMemberListActivity;
import com.org.nightrun.adapter.TeamListViewAdapter;

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

        View view =inflater.inflate(R.layout.fragment_team,null);

        mXListView =(XListView) view.findViewById(R.id.teamListView);
        mTextViewCreateTeam =(TextView)view.findViewById(R.id.createTeam);
        mTextViewCreateTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



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
        startActivity(new Intent(getActivity(), TeamMemberListActivity.class));
    }
}
