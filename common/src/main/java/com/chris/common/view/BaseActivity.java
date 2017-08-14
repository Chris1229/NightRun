package com.chris.common.view;

import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chris.common.KeelApplication;
import com.chris.common.R;
import com.quncao.core.http.HttpRequestManager;

/**
 * 作者：by chris
 * 日期：on 2017/6/28 - 下午5:00.
 * 邮箱：android_cjx@163.com
 */

public class BaseActivity extends FragmentActivity {

    private LinearLayout parentLinearLayout;//把父类activity和子类activity的view都add到这里

    public LoadingDialog loadingDialog;

    public ImageView imgAction;
    public TextView tvBack;
    public TextView tvRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //添加Activity到堆栈
        KeelApplication.getApp().addActivity(this);
        //强制竖屏
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        //设置背景
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F1F1F4")));
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        initContentView();

    }

    /**
     * 初始化contentview
     */
    private void initContentView() {
        ViewGroup viewGroup = (ViewGroup) findViewById(android.R.id.content);
        viewGroup.removeAllViews();
        parentLinearLayout = new LinearLayout(this);
        parentLinearLayout.setOrientation(LinearLayout.VERTICAL);
        viewGroup.addView(parentLinearLayout);
    }

    public void setContentView(int layoutResID, boolean isTitleNeed) {
        if (isTitleNeed) {
            LayoutInflater.from(this).inflate(R.layout.title, parentLinearLayout, true);
            setTitleBgAlpha(255);
            tvBack = (TextView) findViewById(R.id.tv_back);
            tvBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        setContentView(layoutResID);
    }

    public TextView getTvBack() {
        return tvBack;
    }

    public void setTitle(String title) {
        ((TextView) findViewById(R.id.tv_title)).setText(title);
    }

    public TextView setRightTv(String title) {
        tvRight =(TextView)findViewById(R.id.tv_right);
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(title);

        return tvRight;
    }

    public ImageView setRightImage(int id) {
        imgAction = ((ImageView) findViewById(R.id.img_action));
        imgAction.setVisibility(View.VISIBLE);
        imgAction.setImageResource(id);
        return imgAction;
    }

    public void setTitleBgAlpha(int alpha) {
        findViewById(R.id.container).getBackground().setAlpha(alpha);
    }

    @Override
    public void setContentView(int layoutResID) {
        LayoutInflater.from(this).inflate(layoutResID, parentLinearLayout, true);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        MobclickAgent.onResume(this);
//        MobclickAgent.onPageStart(this.toString());
        ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).cancelAll();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        MobclickAgent.onPageEnd(this.toString());
//        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HttpRequestManager.getInstance().cancelAll(this.toString());
        //结束Activity&从栈中移除该Activity
        KeelApplication.getApp().destroyActivity(this);

        dismissLoadingDialog();  //fengyongqiang
    }

    public interface LoginFinishListener {
        void onSuccess();

        void onFailure();
    }


    public void showLoadingDialog(boolean isScreenOn, String content) {
        if (isScreenOn) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        showLoadingDialog(content);
    }

    //显示普通的无文字loading对话框
    public void showLoadingDialog() {
        showLoadingDialog("加载中");
    }

    //按字符串资源id显示对话框
    protected void showLoadingDialog(int resId) {
        showLoadingDialog(getString(resId));
    }

    //显示字符串loading对话框
    protected void showLoadingDialog(String message) {
        if (null == loadingDialog) {
            loadingDialog = new LoadingDialog(this);
        }
        loadingDialog.setMessage(message);
        loadingDialog.setCancelable(true);
        loadingDialog.setCanceledOnTouchOutside(false);
        try {
            loadingDialog.show();
        } catch (WindowManager.BadTokenException ignore) {
        }
    }

    //显示红色loading对话框
    public void showRedLoadingDialog() {
        if (null == loadingDialog) {
            loadingDialog = new LoadingDialog(this, R.style.loadingDialogRed);
        }
        loadingDialog.setCancelable(true);
        loadingDialog.setCanceledOnTouchOutside(false);
        try {
            loadingDialog.show();
        } catch (WindowManager.BadTokenException ignore) {
        }
    }

    //当前loading对话框是否显示
    public boolean isLoadingDialogShowing() {
        return loadingDialog != null && loadingDialog.isShowing();
    }

    // 加载框是否可以取消
    protected void showLoadingDialog(String message, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        if (null == loadingDialog) {
            loadingDialog = new LoadingDialog(this);
            loadingDialog.setCancelable(cancelable);
            if (cancelable) {
                loadingDialog.setOnCancelListener(cancelListener);
            }
        }
        loadingDialog.setMessage(message);
        loadingDialog.show();
    }

    public void showLoadingDialogDisableCancelOutside(String message, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        showLoadingDialog(message, cancelable, cancelListener);
        loadingDialog.setCanceledOnTouchOutside(cancelable);
    }

    //取消显示loading对话框
    public void dismissLoadingDialog() {
        try {
            if (loadingDialog != null && loadingDialog.isShowing()) {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                loadingDialog.dismiss();
            }
            loadingDialog = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //取消显示loading对话框(延迟300ms关闭，避免一闪而过)
    public void dismissLoadingDialogDelayed() {
        try {
            if (loadingDialog != null && loadingDialog.isShowing()) {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                loadingDialog.dismissDelayed();
            }
            loadingDialog = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
