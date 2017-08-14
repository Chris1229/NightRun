package com.zt.nightrun.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.zt.nightrun.R;
import com.zt.nightrun.model.resp.Friend;

import java.util.List;

/**
 * 作者：by chris
 * 日期：on 2017/7/4 - 下午3:56.
 * 邮箱：android_cjx@163.com
 */

public class AuthorityMemberListAdapter extends BaseAdapter {
    private Context mContext;
    private List<Friend> lists;

    public AuthorityMemberListAdapter(Context mContext,List<Friend> list) {
        this.mContext = mContext;
        this.lists =list;
    }

    @Override
    public int getCount() {
        return lists.size();
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

        final Friend friend =lists.get(position);
        viewHolder.tvName.setText("好友-"+friend.getMobile());

        return convertView;
    }


    class ViewHolder{
        private TextView tvName;
        private CheckBox checkBox;
    }
}
