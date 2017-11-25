package com.zt.nightrun.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zt.nightrun.R;
import com.zt.nightrun.model.resp.DeviceItem;

import java.util.List;

/**
 * 作者：by chris
 * 日期：on 2017/6/30 - 下午3:30.
 * 邮箱：android_cjx@163.com
 */

public class DeviceListViewAdapter extends BaseAdapter{

    private Context mContext;
    private List<DeviceItem> lists;

    public DeviceListViewAdapter(Context mContext,List<DeviceItem> list) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_device_item,null);
            viewHolder = new ViewHolder();

            viewHolder.mItemLinear =(LinearLayout)convertView.findViewById(R.id.itemLinear);
            viewHolder.tvNickName =(TextView)convertView.findViewById(R.id.tvNickName);
            viewHolder.tvDeviceName =(TextView)convertView.findViewById(R.id.tvDeviceName);
            viewHolder.tvPower =(TextView)convertView.findViewById(R.id.powerId);
            viewHolder.powerImg =(ImageView) convertView.findViewById(R.id.powerImage);
            viewHolder.tvType =(TextView)convertView.findViewById(R.id.type);
            viewHolder.tvStatus =(TextView)convertView.findViewById(R.id.statusId);
            convertView.setTag(viewHolder);
        }else{
            viewHolder =(ViewHolder)convertView.getTag();
        }
        final DeviceItem deviceItem =lists.get(position);
        if(deviceItem.getUser()!=null){
            if(!TextUtils.isEmpty(deviceItem.getUser().getNick())){
                viewHolder.tvNickName.setText(deviceItem.getUser().getNick());
            }
        }
        if(!TextUtils.isEmpty(deviceItem.getDevice().getName())){
            viewHolder.tvDeviceName.setText(deviceItem.getDevice().getName());
        }
        if(deviceItem.getDevice().getPower()>20){
            viewHolder.powerImg.setImageResource(R.mipmap.battery_normal);
            viewHolder.tvPower.setText(deviceItem.getDevice().getPower()+"%");
        }else{
            viewHolder.powerImg.setImageResource(R.mipmap.battery_red);
            viewHolder.tvPower.setText("80%");
        }

        viewHolder.tvType.setText("网络版");
        if(deviceItem.getDevice().getStatus()==0){
            viewHolder.tvStatus.setText("报警");
            viewHolder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.color_DE5454));
            viewHolder.mItemLinear.setBackgroundResource(R.drawable.device_bg_orange);
        }else{
            viewHolder.tvStatus.setText("在线");
            viewHolder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.color_white));
            viewHolder.mItemLinear.setBackgroundResource(R.drawable.device_bg_blue);
        }
//        if(position % 4 ==0){
//            viewHolder.mItemLinear.setBackgroundResource(R.drawable.device_bg_blue);
//        }else if (position % 4 ==1){
//            viewHolder.mItemLinear.setBackgroundResource(R.drawable.device_bg_orange);
//        }else if (position % 4 ==2){
//            viewHolder.mItemLinear.setBackgroundResource(R.drawable.device_bg_pink);
//        }else if(position % 4 ==3) {
//            viewHolder.mItemLinear.setBackgroundResource(R.drawable.device_bg_blue_green);
//        }
        return convertView;
    }

    class ViewHolder{
        private LinearLayout mItemLinear;
        private TextView tvNickName,tvDeviceName,tvPower,tvStatus,tvType;
        private ImageView powerImg;
    }
}
