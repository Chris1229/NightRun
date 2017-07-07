package com.org.nightrun.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.chris.common.view.BaseFragment;
import com.org.nightrun.R;

/**
 * 作者：by chris
 * 日期：on 2017/6/28 - 下午4:59.
 * 邮箱：android_cjx@163.com
 */

public class MineFragment extends BaseFragment implements View.OnClickListener{

    private RelativeLayout mTuiJianLayout,mWeixinLayout,mRevisePassWordLayout,mRefundLayout,mHelpLayout,mAboutLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        showLoadingDialog();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_mine,null);

        mTuiJianLayout =(RelativeLayout)view.findViewById(R.id.tuijianId);
        mWeixinLayout =(RelativeLayout)view.findViewById(R.id.bangdingweixinId);
        mRevisePassWordLayout =(RelativeLayout)view.findViewById(R.id.revisepasswordId);
        mRefundLayout =(RelativeLayout)view.findViewById(R.id.refundId);
        mHelpLayout =(RelativeLayout)view.findViewById(R.id.helpId);
        mAboutLayout =(RelativeLayout)view.findViewById(R.id.aboutId);
        mTuiJianLayout.setOnClickListener(this);
        mWeixinLayout.setOnClickListener(this);
        mRevisePassWordLayout.setOnClickListener(this);
        mRefundLayout.setOnClickListener(this);
        mHelpLayout.setOnClickListener(this);
        mAboutLayout.setOnClickListener(this);

        return view;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.tuijianId:

                break;


            case R.id.bangdingweixinId:

                break;


            case R.id.revisepasswordId:

                break;


            case R.id.refundId:

                break;


            case R.id.helpId:

                break;


            case R.id.aboutId:

                break;

        }
    }
}
