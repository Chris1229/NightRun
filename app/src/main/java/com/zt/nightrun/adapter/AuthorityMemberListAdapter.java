package com.zt.nightrun.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.zt.nightrun.R;

/**
 * 作者：by chris
 * 日期：on 2017/7/4 - 下午3:56.
 * 邮箱：android_cjx@163.com
 */

public class AuthorityMemberListAdapter extends BaseAdapter {
    private Context mContext;

    public AuthorityMemberListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return 8;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView ==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_authority_item_layout,null);
            viewHolder = new ViewHolder();

            viewHolder.tvName =(TextView)convertView.findViewById(R.id.name);
            viewHolder.checkBox =(CheckBox)convertView.findViewById(R.id.checkbox);

            convertView.setTag(viewHolder);
        }else{
            viewHolder =(ViewHolder)convertView.getTag();
        }

        return convertView;
    }


    class ViewHolder{
        private TextView tvName;
        private CheckBox checkBox;
    }
}
