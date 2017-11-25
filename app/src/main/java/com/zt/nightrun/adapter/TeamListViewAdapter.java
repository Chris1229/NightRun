package com.zt.nightrun.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chris.common.image.ImageUtils;
import com.zt.nightrun.R;
import com.zt.nightrun.model.resp.GroupVos;

import java.util.List;

/**
 * 作者：by chris
 * 日期：on 2017/6/30 - 下午3:30.
 * 邮箱：android_cjx@163.com
 */

public class TeamListViewAdapter extends BaseAdapter{

    private Context mContext;
    private List<GroupVos> lists;

    public TeamListViewAdapter(Context mContext,List<GroupVos> list) {
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

        final GroupVos groupVos =lists.get(position);

        viewHolder.teamName.setText(groupVos.getGroup().getName());
        viewHolder.teamLeader.setText("队长"+ groupVos.getUser().getNick());
        if(groupVos.getGroup().getStatus() ==1){
            viewHolder.teamStatus.setText("已激活");
            viewHolder.teamStatus.setTextColor(mContext.getResources().getColor(R.color.color_1EA557));
            viewHolder.teamStatus.setBackgroundResource(R.mipmap.yijihuo);
        }else{
            viewHolder.teamStatus.setText("激活");
            viewHolder.teamStatus.setTextColor(mContext.getResources().getColor(R.color.color_9489D7));
            viewHolder.teamStatus.setBackgroundResource(R.mipmap.jihuo);
        }

        viewHolder.teamNumAndDate.setText(groupVos.getGroup().getCreateTime());
        if(!TextUtils.isEmpty(groupVos.getGroup().getImage())){
            ImageUtils.loadCircleImage(mContext,groupVos.getGroup().getImage()+"!thumb",R.mipmap.default_avtar,viewHolder.teamImg);
        }else{
            viewHolder.teamImg.setImageResource(R.mipmap.default_avtar);
        }
        return convertView;
    }

    class ViewHolder{
        private ImageView teamImg;
        private TextView teamName,teamLeader,teamNumAndDate,teamStatus;
    }
}
