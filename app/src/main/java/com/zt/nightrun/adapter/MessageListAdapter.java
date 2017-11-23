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
import com.zt.nightrun.db.Message;
import com.zt.nightrun.model.resp.GroupVos;
import com.zt.nightrun.model.resp.PushMessage;

import java.util.List;

/**
 * 作者：by chris
 * 日期：on 2017/6/30 - 下午3:30.
 * 邮箱：android_cjx@163.com
 */

public class MessageListAdapter extends BaseAdapter{

    private Context mContext;
    private List<Message> lists;

    public MessageListAdapter(Context mContext,List<Message> list) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.message_list_item,null);
            viewHolder = new ViewHolder();

            viewHolder.tvName =(TextView)convertView.findViewById(R.id.message_name);
            viewHolder.tvContent =(TextView)convertView.findViewById(R.id.message_con);
            viewHolder.tvTime =(TextView)convertView.findViewById(R.id.message_time);

            convertView.setTag(viewHolder);
        }else{
            viewHolder =(ViewHolder)convertView.getTag();
        }

        final Message message =lists.get(position);
        viewHolder.tvName.setText(message.getTitle());
        viewHolder.tvContent.setText(message.getContent());
        viewHolder.tvTime.setText(message.getTimeStamp()+"");
        return convertView;
    }

    class ViewHolder{
        private TextView tvName,tvContent,tvTime;
    }
}
