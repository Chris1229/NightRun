package com.zt.nightrun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.error.VolleyError;
import com.chris.common.KeelApplication;
import com.chris.common.network.HttpRequestProxy;
import com.chris.common.utils.SharedPreferencesUtil;
import com.chris.common.utils.ToastUtils;
import com.chris.common.view.BaseActivity;
import com.quncao.core.http.AbsHttpRequestProxy;
import com.zt.nightrun.NightRunApplication;
import com.zt.nightrun.R;
import com.zt.nightrun.eventbus.TeamName;
import com.zt.nightrun.eventbus.UserImg;
import com.zt.nightrun.model.req.ReqModify;
import com.zt.nightrun.model.req.ReqModifyTeamInfo;
import com.zt.nightrun.model.resp.Group;
import com.zt.nightrun.model.resp.RespModify;
import com.zt.nightrun.model.resp.RespModifyTeamInfo;
import com.zt.nightrun.model.resp.User;

import org.greenrobot.eventbus.EventBus;

/**
 * 作者：by chris
 * 日期：on 2017/8/7 - 下午4:06.
 * 邮箱：android_cjx@163.com
 */

public class ModifyNickNameActivity extends BaseActivity {
    private EditText etNickName;
    private int type;
    private int groupId;
    private String teamNick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_modify_nickname,true);

        Intent intent =getIntent();
        type =intent.getIntExtra("type",0);
        groupId =intent.getIntExtra("groupId",0);
        teamNick =intent.getStringExtra("teamNick");
        initView();
    }

    private void initView(){

        etNickName =(EditText)findViewById(R.id.etNickName);

        if(type ==1){
            setTitle("编辑组队昵称");
            etNickName.setText(teamNick);
        }else{
            setTitle("编辑昵称");
            if(!TextUtils.isEmpty(NightRunApplication.getInstance().nick)){
                etNickName.setText(NightRunApplication.getInstance().nick);
            }
        }

        etNickName.setSelection(etNickName.getText().length());
        setRightTv("保存").setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!TextUtils.isEmpty(etNickName.getText())){
                    if(type ==1){
                        reqTeamName();
                    }else{
                        reqModifyPerson();
                    }
                }else{
                    ToastUtils.show(ModifyNickNameActivity.this,"昵称不能为空");
                }
            }
        });
    }


    private void reqModifyPerson(){

        final ReqModify reqModify = new ReqModify();
        reqModify.setNick(etNickName.getText().toString());
        reqModify.setGender(NightRunApplication.getInstance().gender);
        reqModify.setImage(NightRunApplication.getInstance().image);
        HttpRequestProxy.get().create(reqModify, new AbsHttpRequestProxy.RequestListener() {
            @Override
            public void onSuccess(Object response) {

                Log.i("info",response.toString());
                RespModify respModify =(RespModify)response;
                if(respModify.getData()!=null && respModify.getData().getUser()!=null){
                    ToastUtils.show(ModifyNickNameActivity.this,"昵称修改成功");
                    User user =respModify.getData().getUser();
                    SharedPreferencesUtil.saveString(KeelApplication.getApplicationConext(),"nick",user.getNick());
                    NightRunApplication.getInstance().nick =user.getNick();
                    EventBus.getDefault().post(new UserImg(user.getImage()));
                    finish();
                }else{
                    ToastUtils.show(ModifyNickNameActivity.this,respModify.getMessage());
                }
            }

            @Override
            public void onFailed(VolleyError error) {
                Log.i("info",error.toString());
                ToastUtils.show(ModifyNickNameActivity.this,error.toString());
            }
        }).tag(this.toString()).build().excute();
    }

    private void reqTeamName() {
        ReqModifyTeamInfo reqModify = new ReqModifyTeamInfo();
        reqModify.setGroupId(groupId);
        reqModify.setName(etNickName.getText().toString());
        HttpRequestProxy.get().create(reqModify, new AbsHttpRequestProxy.RequestListener() {
            @Override
            public void onSuccess(Object response) {

                Log.i("info", response.toString());
                RespModifyTeamInfo respModify = (RespModifyTeamInfo) response;
                if (respModify.getData() != null && respModify.getData().getGroup() != null) {
                    ToastUtils.show(ModifyNickNameActivity.this, "组队昵称修改成功");
                    Group group = respModify.getData().getGroup();
                    EventBus.getDefault().post(new TeamName(group.getName()));
                    finish();
                } else {
                    ToastUtils.show(ModifyNickNameActivity.this, respModify.getMessage());
                }
            }

            @Override
            public void onFailed(VolleyError error) {
                Log.i("info", error.toString());
                ToastUtils.show(ModifyNickNameActivity.this, error.toString());
            }
        }).tag(this.toString()).build().excute();
    }
}
