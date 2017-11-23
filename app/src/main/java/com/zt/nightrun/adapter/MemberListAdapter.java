package com.zt.nightrun.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.error.VolleyError;
import com.chris.common.image.ImageUtils;
import com.chris.common.network.HttpRequestProxy;
import com.chris.common.utils.ToastUtils;
import com.quncao.core.http.AbsHttpRequestProxy;
import com.zt.nightrun.R;
import com.zt.nightrun.activity.TeamMemberListActivity;
import com.zt.nightrun.model.req.ReqGroupMember;
import com.zt.nightrun.model.req.ReqSign;
import com.zt.nightrun.model.resp.GroupUser;
import com.zt.nightrun.model.resp.RespGroupMember;
import com.zt.nightrun.model.resp.RespSign;

import java.util.Date;
import java.util.List;

import static com.zt.nightrun.R.id.tvNoData;

/**
 * 作者：by chris
 * 日期：on 2017/6/30 - 下午3:30.
 * 邮箱：android_cjx@163.com
 */

public class MemberListAdapter extends BaseAdapter{

    private Context mContext;
    private List<GroupUser> lists;
    private int groupId;

    public MemberListAdapter(Context mContext,List<GroupUser> list,int groupid) {
        this.mContext = mContext;
        this.lists =list;
        this.groupId =groupid;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView ==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_team_item,null);
            viewHolder = new ViewHolder();

            viewHolder.teamImg =(ImageView)convertView.findViewById(R.id.teamImgHead);
            viewHolder.teamName =(TextView)convertView.findViewById(R.id.teamName);
            viewHolder.teamLeader =(TextView)convertView.findViewById(R.id.teamLeader);
            viewHolder.teamNumAndDate =(TextView)convertView.findViewById(R.id.teamNumAnddate);
            viewHolder.teamStatus =(TextView)convertView.findViewById(R.id.teamStatus);
            viewHolder.tvSign =(TextView)convertView.findViewById(R.id.tvSign);
            convertView.setTag(viewHolder);
        }else{
            viewHolder =(ViewHolder)convertView.getTag();
        }

        final GroupUser groupUser =lists.get(position);

        viewHolder.teamLeader.setVisibility(View.GONE);
        viewHolder.teamStatus.setVisibility(View.GONE);
        viewHolder.tvSign.setVisibility(View.VISIBLE);
        viewHolder.tvSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reqSign(position);
            }
        });

        if(groupUser.isBlnCheckIn()){
            viewHolder.tvSign.setText("已签到");
            viewHolder.teamStatus.setTextColor(mContext.getResources().getColor(R.color.color_1EA557));
            viewHolder.tvSign.setClickable(false);
        }else{
            viewHolder.tvSign.setText("签到");
            viewHolder.tvSign.setTextColor(mContext.getResources().getColor(R.color.color_9489D7));
            viewHolder.tvSign.setClickable(true);
        }
//        viewHolder.teamStatus.setBackgroundResource(R.mipmap.jihuo);

        if(!TextUtils.isEmpty(groupUser.getUser().getImage())){
            ImageUtils.loadCircleImage(mContext,groupUser.getUser().getImage(),R.mipmap.default_avtar,viewHolder.teamImg);
        }
        viewHolder.teamName.setText(groupUser.getUser().getNick());
        viewHolder.teamNumAndDate.setText(groupUser.getDevice().getLastTime());
        return convertView;
    }

    class ViewHolder{
        private ImageView teamImg;
        private TextView teamName,teamLeader,teamNumAndDate,teamStatus,tvSign;
    }

    private void reqSign(final int position){
        ReqSign sign = new ReqSign();
        sign.setGroupId(groupId);
        sign.setUserId(lists.get(position).getUser().getId());
        HttpRequestProxy.get().create(sign, new AbsHttpRequestProxy.RequestListener() {
            @Override
            public void onSuccess(Object response) {
                Log.i("info",response.toString());
                RespSign respSign =(RespSign) response;
                if(respSign.getCode()==0){
                    GroupUser groupUser =lists.get(position);
                    groupUser.setBlnCheckIn(true);
                    notifyDataSetChanged();

                }else{
                    ToastUtils.show(mContext,"签到失败");
                }
            }

            @Override
            public void onFailed(VolleyError error) {
                Log.i("info",error.toString());
                ToastUtils.show(mContext,error.toString());
            }
        }).tag(this.toString()).build().excute();

    }

}
