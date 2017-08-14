package com.zt.nightrun.activity;

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
import com.zt.nightrun.model.req.ReqModify;
import com.zt.nightrun.model.resp.RespLogin;
import com.zt.nightrun.model.resp.RespModify;
import com.zt.nightrun.model.resp.User;

/**
 * 作者：by chris
 * 日期：on 2017/8/7 - 下午4:06.
 * 邮箱：android_cjx@163.com
 */

public class ModifyNickNameActivity extends BaseActivity {
    private EditText etNickName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_modify_nickname,true);

        initView();
    }

    private void initView(){
        setTitle("编辑昵称");

        etNickName =(EditText)findViewById(R.id.etNickName);

        setRightTv("保存").setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!TextUtils.isEmpty(etNickName.getText())){
                    reqModifyPerson();
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
}
