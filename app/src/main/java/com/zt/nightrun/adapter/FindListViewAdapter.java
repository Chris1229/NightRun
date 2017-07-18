package com.zt.nightrun.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zt.nightrun.R;

/**
 * 作者：by chris
 * 日期：on 2017/6/30 - 下午3:30.
 * 邮箱：android_cjx@163.com
 */

public class FindListViewAdapter extends BaseAdapter{

    private Context mContext;

    public FindListViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return 4;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.find_list_item_layout,null);
            viewHolder = new ViewHolder();

            viewHolder.imageView =(ImageView)convertView.findViewById(R.id.imageViewId);
            viewHolder.likeImg =(ImageView) convertView.findViewById(R.id.likeImg);
            viewHolder.tvTitle =(TextView)convertView.findViewById(R.id.tvTitle);
            viewHolder.tvReadNum =(TextView)convertView.findViewById(R.id.readNum);

            convertView.setTag(viewHolder);
        }else{
            viewHolder =(ViewHolder)convertView.getTag();
        }

        if(position ==1){
            viewHolder.likeImg.setImageResource(R.mipmap.like_down);
        }else{
            viewHolder.likeImg.setImageResource(R.mipmap.like_up);
        }
        return convertView;
    }

    class ViewHolder{
        private ImageView imageView,likeImg;
        private TextView tvTitle,tvReadNum;
    }
}
