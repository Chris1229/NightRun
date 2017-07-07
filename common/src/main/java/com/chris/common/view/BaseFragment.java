package com.chris.common.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.WindowManager;

import com.quncao.core.http.HttpRequestManager;

/**
 * 作者：by chris
 * 日期：on 2017/6/28 - 下午5:01.
 * 邮箱：android_cjx@163.com
 */

public class BaseFragment extends Fragment {
    public LoadingDialog loadingDialog;

    public void show(FragmentManager fragmentManager) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.add(android.R.id.content, this);
        fragmentTransaction.show(this);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onResume() {
        super.onResume();
//        MobclickAgent.onResume(getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
//        MobclickAgent.onPause(getActivity());
    }

    @Override
    public void onDestroy() {
        HttpRequestManager.getInstance().cancelAll(this.toString());
        super.onDestroy();
    }

    public void finish() {
        if (!getFragmentManager().popBackStackImmediate()) {
            getActivity().finish();
        }
    }


    //显示普通的无文字loading对话框
    protected void showLoadingDialog() {
        showLoadingDialog("加载中");
    }

    //显示字符串loading对话框
    protected void showLoadingDialog(String message) {
        if (null == loadingDialog) {
            loadingDialog = new LoadingDialog(getActivity());
        }
        loadingDialog.setMessage(message);
        loadingDialog.setCancelable(true);
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.show();
    }

    //显示字符串loading对话框
    protected void showLoadingDialog(String message, boolean canCancelOnTouchOutside) {
        if (null == loadingDialog) {
            loadingDialog = new LoadingDialog(getActivity());
        } else {
            loadingDialog = null;
//            loadingDialog = new LoadingDialog(getActivity(), R.style.loadingDialog);
        }
        loadingDialog.setMessage(message);
        loadingDialog.setCancelable(false);
        loadingDialog.setCanceledOnTouchOutside(canCancelOnTouchOutside);
        loadingDialog.show();
    }


    //显示红色loading对话框
    protected void showRedLoadingDialog() {
        if (null == loadingDialog) {
//            loadingDialog = new LoadingDialog(getActivity(), R.style.loadingDialogRed);
        } else {
            loadingDialog = null;
//            loadingDialog = new LoadingDialog(getActivity(), R.style.loadingDialogRed);
        }
        loadingDialog.setCancelable(true);
        loadingDialog.setCanceledOnTouchOutside(false);
        try {
            loadingDialog.show();
        } catch (WindowManager.BadTokenException ignore) {
        }
    }

    public void showLoadingDialog(boolean isScreenOn, String content) {
        if (isScreenOn) {
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        showLoadingDialog(content);
    }

    //取消显示loading对话框
    protected void dismissLoadingDialog() {
        try {
            if (loadingDialog != null && loadingDialog.isShowing()) {
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                loadingDialog.dismiss();
                loadingDialog = null;
            }
        } catch (Exception ignore) {

        }
    }

    //当前loading对话框是否显示
    public boolean isLoadingDialogShowing() {
        if (loadingDialog != null) {
            return loadingDialog.isShowing();
        } else {
            return false;
        }
    }

    //某些fragment需要手动触发加载数据，所以加了个方法
    public void loadData() {
    }

}
