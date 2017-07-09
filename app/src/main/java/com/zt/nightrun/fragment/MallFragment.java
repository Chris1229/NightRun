package com.zt.nightrun.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.chris.common.view.BaseFragment;
import com.zt.nightrun.R;
import com.zt.nightrun.adapter.MallGridViewAdapter;

/**
 * 作者：by chris
 * 日期：on 2017/6/28 - 下午4:59.
 * 邮箱：android_cjx@163.com
 */

public class MallFragment extends BaseFragment {
    private GridView gridView;
    private MallGridViewAdapter mAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        showLoadingDialog();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_mall,null);

        gridView =(GridView)view.findViewById(R.id.mallGridView);
        mAdapter = new MallGridViewAdapter(getActivity());
        gridView.setAdapter(mAdapter);

        return view;

    }
}
