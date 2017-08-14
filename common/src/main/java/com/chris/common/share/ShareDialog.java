package com.chris.common.share;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chris.common.R;
import com.chris.common.utils.ViewFindUtils;
import com.chris.common.view.ActionItem;

import java.util.ArrayList;

/**
 * Created by chris on 16/5/23.
 * 分享对话框
 */
public class ShareDialog extends Dialog implements View.OnClickListener {

    private GridView gridView;
    private TextView tvTitle;
    private TextView tvCancel;
    private String title;
    private Context context;
    private OnItemOnClickListener onItemOnClickListener;
    private int type; //1 不带举报删除的  2 带举报删除的
    //定义弹窗子类项列表
    private ArrayList<ActionItem> mActionItems = new ArrayList<>();

    public ShareDialog(Context context, String title, int type) {
        super(context, R.style.dialog);
        this.title = title;
        this.context = context;
        this.type = type;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_share);

        ViewGroup.LayoutParams params = getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        gridView = (GridView) findViewById(R.id.grid_view);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        tvCancel.setOnClickListener(this);
        tvTitle.setText(title);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onItemOnClickListener.onItemClick(mActionItems.get(position), position);
                dismiss();
            }
        });



        setShareAdapter();

    }

    public void showDialog() {
        Window w = getWindow();
        w.setWindowAnimations(R.style.customAnimation);
        WindowManager.LayoutParams lp = w.getAttributes();
        lp.gravity = Gravity.BOTTOM;
        onWindowAttributesChanged(lp);
        setCanceledOnTouchOutside(true);
        show();
    }

    /**
     * 添加子类项
     */
    public void addAction(ActionItem action) {
        if (action != null) {
            mActionItems.add(action);
        }
    }

    private void setShareAdapter() {

        //设置列表的适配器
        if (type == 1) {
            gridView.setNumColumns(3);
            gridView.setVerticalSpacing(0);
        } else {
            gridView.setNumColumns(4);
            gridView.setVerticalSpacing(context.getResources().getDimensionPixelSize(R.dimen.value_24dp));
        }
        gridView.setAdapter(new BaseAdapter() {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = LinearLayout.inflate(context, R.layout.share_item, null);
                }

                TextView textView = ViewFindUtils.hold(convertView, R.id.tv);
                View view = ViewFindUtils.hold(convertView, R.id.view);
                ActionItem item = mActionItems.get(position);

                if (type == 1) {
                    view.setVisibility(View.GONE);
                } else {
                    if (position > 3) {
                        view.setVisibility(View.GONE);
                    } else {
                        view.setVisibility(View.VISIBLE);
                    }
                }
                textView.setText(item.mTitle);
                textView.setCompoundDrawablePadding((int) context.getResources().getDimension(R.dimen.value_8dp));
                textView.setCompoundDrawablesWithIntrinsicBounds(null, item.mDrawable, null, null);

                return convertView;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public Object getItem(int position) {
                return mActionItems.get(position);
            }

            @Override
            public int getCount() {
                return mActionItems.size();
            }
        });
    }

    public void setOnItemClickListener(OnItemOnClickListener onItemOnClickListener) {
        this.onItemOnClickListener = onItemOnClickListener;
    }

    public interface OnItemOnClickListener {
        void onItemClick(ActionItem item, int position);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_cancel) {
            dismiss();
        }
    }
}
