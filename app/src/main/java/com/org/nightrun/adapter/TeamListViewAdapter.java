package com.org.nightrun.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.org.nightrun.R;

/**
 * 作者：by chris
 * 日期：on 2017/6/30 - 下午3:30.
 * 邮箱：android_cjx@163.com
 */

public class TeamListViewAdapter extends BaseAdapter{

    private Context mContext;

    public TeamListViewAdapter(Context mContext) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_team_item,null);
            viewHolder = new ViewHolder();

            viewHolder.teamImg =(ImageView)convertView.findViewById(R.id.teamImgHead);
            viewHolder.teamName =(TextView)convertView.findViewById(R.id.teamName);
            viewHolder.teamLeader =(TextView)convertView.findViewById(R.id.teamLeader);
            viewHolder.teamNumAndDate =(TextView)convertView.findViewById(R.id.teamNumAnddate);
            viewHolder.teamStatus =(TextView)convertView.findViewById(R.id.teamStatus);

            convertView.setTag(viewHolder);
        }else{
            viewHolder =(ViewHolder)convertView.getTag();
        }

        if(position ==2){
            viewHolder.teamStatus.setText("已激活");
            viewHolder.teamStatus.setTextColor(mContext.getResources().getColor(R.color.color_1EA557));
            viewHolder.teamStatus.setBackgroundResource(R.mipmap.yijihuo);
        }else{
            viewHolder.teamStatus.setText("激活");
            viewHolder.teamStatus.setTextColor(mContext.getResources().getColor(R.color.color_9489D7));
            viewHolder.teamStatus.setBackgroundResource(R.mipmap.jihuo);
        }

        return convertView;
    }

    class ViewHolder{
        private ImageView teamImg;
        private TextView teamName,teamLeader,teamNumAndDate,teamStatus;
    }
}
