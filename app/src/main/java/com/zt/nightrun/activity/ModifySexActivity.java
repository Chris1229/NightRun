package com.zt.nightrun.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

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

public class ModifySexActivity extends BaseActivity implements View.OnClickListener{

    private RelativeLayout menLayout,womenLayout;
    private ImageView menSelected,womenSelected;
    private int sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_modify_sex,true);

        sex = NightRunApplication.getInstance().gender;
        initView();
    }


    private void initView(){
        menLayout =(RelativeLayout)findViewById(R.id.layout_men);
        womenLayout =(RelativeLayout)findViewById(R.id.layout_women);
        menSelected =(ImageView)findViewById(R.id.men_selected);
        womenSelected =(ImageView)findViewById(R.id.women_selected);
        menLayout.setOnClickListener(this);
        womenLayout.setOnClickListener(this);

        if(sex ==1){
            menSelected.setVisibility(View.VISIBLE);
            womenSelected.setVisibility(View.INVISIBLE);
        }else{
            menSelected.setVisibility(View.INVISIBLE);
            womenSelected.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_men:
                sex =1;
                menSelected.setVisibility(View.VISIBLE);
                womenSelected.setVisibility(View.INVISIBLE);
                reqModifySex();
                break;

            case R.id.layout_women:
                sex =0;
                menSelected.setVisibility(View.INVISIBLE);
                womenSelected.setVisibility(View.VISIBLE);
                reqModifySex();
                break;
        }
    }

    private void reqModifySex(){
        final ReqModify reqModify = new ReqModify();
        reqModify.setGender(sex);
        reqModify.setNick(NightRunApplication.getInstance().nick);
        reqModify.setImage(NightRunApplication.getInstance().image);
        HttpRequestProxy.get().create(reqModify, new AbsHttpRequestProxy.RequestListener() {
            @Override
            public void onSuccess(Object response) {

                Log.i("info",response.toString());
                RespModify respModify =(RespModify)response;
                if(respModify.getData()!=null && respModify.getData().getUser()!=null){
                    ToastUtils.show(ModifySexActivity.this,"性别修改成功");
                    User user =respModify.getData().getUser();
                    SharedPreferencesUtil.saveInteger(KeelApplication.getApplicationConext(),"gender",user.getGender());
                    NightRunApplication.getInstance().gender =user.getGender();
                    finish();
                }else{
                    ToastUtils.show(ModifySexActivity.this,respModify.getMessage());
                }
            }

            @Override
            public void onFailed(VolleyError error) {
                Log.i("info",error.toString());
                ToastUtils.show(ModifySexActivity.this,error.toString());
            }
        }).tag(this.toString()).build().excute();
    }
}
