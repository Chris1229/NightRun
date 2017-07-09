package com.zt.nightrun.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.chris.common.KeelApplication;
import com.chris.common.R;

/**
 * 作者：by chris
 * 日期：on 2017/7/5 - 下午5:01.
 * 邮箱：android_cjx@163.com
 */
public class CustomEditDialog extends Dialog {

    private OnOkListener onOkListener;
    private TextView tvTitle;
    private EditText etContent;
    private TextView tvLeft;
    private TextView tvRight;

    private Context context;
    private String title = "提示";
    private String hint = "";
    private String left = "取消";
    private String right = "确定";
    private String leftColor = "#929292";
    private String rightColor = "#675BAE";
    private int inputType = 0;
    private View view;

    public CustomEditDialog(Context context,String title,String hint,int inputType, OnOkListener onClickListener) {
        super(context, R.style.dialog);
        this.context = context;
        this.title =title;
        this.hint =hint;
        this.inputType =inputType;
        this.onOkListener = onClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_edit_custom);
        ViewGroup.LayoutParams params = getWindow().getAttributes();
        WindowManager wm = (WindowManager) KeelApplication.getApplicationConext().getSystemService(Context.WINDOW_SERVICE);
        params.width = (int) (wm.getDefaultDisplay().getWidth() * 0.8);
        tvTitle =(TextView)findViewById(R.id.tv_title);
        etContent = (EditText) findViewById(R.id.etTeamNameId);
        tvLeft = (TextView) findViewById(R.id.tv_left);
        tvRight = (TextView) findViewById(R.id.tv_right);
        view = findViewById(R.id.view);

        tvTitle.setText(title);
        etContent.setHint(hint);
        tvLeft.setText(left);
        tvRight.setText(right);
        tvLeft.setTextColor(Color.parseColor(leftColor));
        tvRight.setTextColor(Color.parseColor(rightColor));
        if (inputType != 0) {
            etContent.setInputType(inputType);
        }
        tvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOkListener.onOkClick(CustomEditDialog.this,etContent.getText().toString());
            }
        });
    }

    public interface OnOkListener {
        void onOkClick(CustomEditDialog dialog, String string);
    }

    public CustomEditDialog setTitle(String title) {
        return this;
    }


    public CustomEditDialog setLeft(String left) {
        this.left = left;
        return this;
    }

    public CustomEditDialog setRight(String right) {
        this.right = right;
        return this;
    }

    public CustomEditDialog setHint(String hint) {
        this.hint = hint;
        return this;
    }

    public CustomEditDialog setInputType(int inputType) {
        this.inputType = inputType;
        return this;
    }

}
