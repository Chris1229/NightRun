package com.chris.common.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chris.common.KeelApplication;
import com.chris.common.R;

/**
 * 作者：by chris
 * 日期：on 2017/8/6 - 上午9:17.
 * 邮箱：android_cjx@163.com
 */

public class CustomDialog extends Dialog{
    private OnClickListener onClickListener;
    private TextView tvTitle;
    private TextView tvContent;
    private TextView tvLeft;
    private TextView tvRight;

    private Context context;
    private String title = "提示";
    private String content = "";
    private String left = "取消";
    private String right = "确定";
    private String leftColor = "#929292";
    private String rightColor = "#675BAE";
    private boolean oneBtn = false;
    private int image = 0;

    private View view;

    public CustomDialog(Context context, OnClickListener onClickListener) {
        super(context, R.style.dialog);
        this.context = context;
        this.onClickListener = onClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_custom);
        ViewGroup.LayoutParams params = getWindow().getAttributes();
        WindowManager wm = (WindowManager) KeelApplication.getApplicationConext().getSystemService(Context.WINDOW_SERVICE);
        params.width = (int) (wm.getDefaultDisplay().getWidth() * 0.8);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvContent = (TextView) findViewById(R.id.tv_content);
        tvLeft = (TextView) findViewById(R.id.tv_left);
        tvRight = (TextView) findViewById(R.id.tv_right);
        view = findViewById(R.id.view);

        tvTitle.setText(title);
        tvContent.setText(content);
        tvLeft.setText(left);
        tvRight.setText(right);
        tvLeft.setTextColor(Color.parseColor(leftColor));
        tvRight.setTextColor(Color.parseColor(rightColor));

        if (oneBtn) {
            view.setVisibility(View.GONE);
            tvLeft.setVisibility(View.GONE);
            tvRight.setBackgroundResource(R.drawable.item_bg_bottom);
        } else {
            view.setVisibility(View.VISIBLE);
            tvLeft.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(content)) {
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tvTitle.getLayoutParams();
//            lp.topMargin = context.getResources().getDimensionPixelSize(R.dimen.value_24dp);
            tvContent.setVisibility(View.VISIBLE);
        } else {
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tvTitle.getLayoutParams();
            lp.topMargin = 0;
            tvContent.setVisibility(View.GONE);
        }
        if (image != 0) {
            tvTitle.setCompoundDrawablesWithIntrinsicBounds(image, 0, 0, 0);
            tvTitle.setCompoundDrawablePadding(context.getResources().getDimensionPixelSize(R.dimen.value_8dp));
        }
        tvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onLeftClick();
                dismiss();
            }
        });
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onRightClick();
                dismiss();
            }
        });
    }

    public interface OnClickListener {
        void onLeftClick();

        void onRightClick();
    }

    public CustomDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public CustomDialog setContent(String content) {
        this.content = content;
        return this;
    }

    public CustomDialog setLeft(String left) {
        this.left = left;
        return this;
    }

    public CustomDialog setRight(String right) {
        this.right = right;
        return this;
    }

    public CustomDialog setLeftColor(String color) {
        this.leftColor = color;
        return this;
    }

    public CustomDialog setRightColor(String color) {
        this.rightColor = color;
        return this;
    }

    public CustomDialog setOneBtn(boolean oneBtn){
        this.oneBtn = oneBtn;
        return this;
    }

    public CustomDialog setTitleImage(int image) {
        this.image = image;
        return this;
    }
}
