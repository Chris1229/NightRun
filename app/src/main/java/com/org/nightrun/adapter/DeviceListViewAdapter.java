package com.org.nightrun.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.org.nightrun.R;

/**
 * 作者：by chris
 * 日期：on 2017/6/30 - 下午3:30.
 * 邮箱：android_cjx@163.com
 */

public class DeviceListViewAdapter extends BaseAdapter{

    private Context mContext;

    public DeviceListViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return 6;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_device_item,null);
            viewHolder = new ViewHolder();

            viewHolder.mItemLinear =(LinearLayout)convertView.findViewById(R.id.itemLinear);

            convertView.setTag(viewHolder);
        }else{
            viewHolder =(ViewHolder)convertView.getTag();
        }

        if(position % 4 ==0){
            viewHolder.mItemLinear.setBackgroundResource(R.drawable.device_bg_blue);
        }else if (position % 4 ==1){
            viewHolder.mItemLinear.setBackgroundResource(R.drawable.device_bg_orange);
        }else if (position % 4 ==2){
            viewHolder.mItemLinear.setBackgroundResource(R.drawable.device_bg_pink);
        }else if(position % 4 ==3) {
            viewHolder.mItemLinear.setBackgroundResource(R.drawable.device_bg_blue_green);
        }
        return convertView;
    }

    class ViewHolder{
        private LinearLayout mItemLinear;
    }
}
