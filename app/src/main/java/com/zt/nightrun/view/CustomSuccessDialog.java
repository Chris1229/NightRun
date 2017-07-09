package com.zt.nightrun.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.chris.common.KeelApplication;
import com.zt.nightrun.R;

/**
 * 作者：by chris
 * 日期：on 2017/7/5 - 下午5:01.
 * 邮箱：android_cjx@163.com
 */
public class CustomSuccessDialog extends Dialog {

    private OnClickListener clickListener;
    private TextView tvTitle;
    private TextView tvContent;
    private TextView tvLeft;
    private TextView tvRight;
    private ImageView codeImg;

    private Context context;
    private String title = "创建成功";
    private String content="";
    private String left = "返回";
    private String right = "分享到微信";
    private String leftColor = "#929292";
    private String rightColor = "#675BAE";
    private View view;

    public CustomSuccessDialog(Context context, String content,OnClickListener onClickListener) {
        super(context, R.style.dialog);
        this.context = context;
        this.content =content;
        this.clickListener = onClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog_success);
        ViewGroup.LayoutParams params = getWindow().getAttributes();
        WindowManager wm = (WindowManager) KeelApplication.getApplicationConext().getSystemService(Context.WINDOW_SERVICE);
        params.width = (int) (wm.getDefaultDisplay().getWidth() * 0.8);
        tvTitle =(TextView)findViewById(R.id.tv_title);
        tvContent = (TextView) findViewById(R.id.tvContext);
        tvLeft = (TextView) findViewById(R.id.tv_left);
        tvRight = (TextView) findViewById(R.id.tv_right);
        codeImg =(ImageView)findViewById(R.id.codeImg);
        view = findViewById(R.id.view);

        tvTitle.setText(title);
        tvContent.setText(content);
        tvLeft.setText(left);
        tvRight.setText(right);
        tvLeft.setTextColor(Color.parseColor(leftColor));
        tvRight.setTextColor(Color.parseColor(rightColor));
        tvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onLeftClick(CustomSuccessDialog.this);
                dismiss();
            }
        });
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onLeftClick(CustomSuccessDialog.this);
            }
        });
    }

    public interface OnClickListener {
        void onLeftClick(CustomSuccessDialog dialog);

        void onRightClick(CustomSuccessDialog dialog);
    }

    public CustomSuccessDialog setTitle(String title) {
        return this;
    }


    public CustomSuccessDialog setLeft(String left) {
        this.left = left;
        return this;
    }

    public CustomSuccessDialog setRight(String right) {
        this.right = right;
        return this;
    }
}
