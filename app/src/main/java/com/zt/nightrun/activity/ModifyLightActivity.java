package com.zt.nightrun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.android.volley.error.VolleyError;
import com.chris.common.network.HttpRequestProxy;
import com.chris.common.utils.ToastUtils;
import com.chris.common.view.BaseActivity;
import com.quncao.core.http.AbsHttpRequestProxy;
import com.zt.nightrun.blue.utils.BlueUtils;
import com.zt.nightrun.model.req.ReqModifyColor;
import com.zt.nightrun.model.resp.RespModifyColor;
import com.zt.nightrun.utils.RGBUtils;
import com.zt.nightrun.R;
/**
 * 作者：by chris
 * 日期：on 2017/8/14 - 下午5:19.
 * 邮箱：android_cjx@163.com
 */

public class ModifyLightActivity extends BaseActivity  implements RadioGroup.OnCheckedChangeListener{

    private RadioGroup mRadioGroup;
    private RadioGroup mRadioGroupDodge;
    private ImageView selectImg;
    private int groupId;
    private int lightDodge =-1;
    private int rgbB =-1;
    private int rgbR =-1;
    private int rgbG =-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_light_modify,true);
        Intent intent =getIntent();
        groupId =intent.getIntExtra("groupId",0);
        initView();
    }

    private void initView(){

        setTitle("同步荧光棒");

        setRightTv("提交").setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rgbB>=0){
                    if(rgbB !=100 && rgbB !=101){
                        if(lightDodge >=0){
                            reqModiftyLight();
                        }else{
                            ToastUtils.show(ModifyLightActivity.this,"请先选择频闪");
                        }
                    }else{
                        reqModiftyLight();
                    }
                }else{
                    ToastUtils.show(ModifyLightActivity.this,"请先选择颜色");
                }
            }
        });
        selectImg =(ImageView)findViewById(R.id.selectImg);
        mRadioGroup =(RadioGroup)findViewById(R.id.radioGroup);
        mRadioGroupDodge =(RadioGroup)findViewById(R.id.radiogroup_dodge);
        mRadioGroup.setOnCheckedChangeListener(this);
        mRadioGroupDodge.setOnCheckedChangeListener(this);

    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

        switch (checkedId){
            //颜色
            case R.id.radioBt1:
                selectImg.setImageResource(R.mipmap.big_1);
                rgbR =100;
                rgbG =100;
                rgbB =100;
                break;

            case R.id.radioBt2:
                selectImg.setImageResource(R.mipmap.big_2);
                rgbR =101;
                rgbG =101;
                rgbB =101;
                break;

            case R.id.radioBt3:
                selectImg.setImageResource(R.mipmap.big_3);
                rgbR =RGBUtils.LIGHT_COLOR_2[0];
                rgbG =RGBUtils.LIGHT_COLOR_2[1];
                rgbB =RGBUtils.LIGHT_COLOR_2[2];
                break;

            case R.id.radioBt4:
                selectImg.setImageResource(R.mipmap.big_4);
                rgbR =RGBUtils.LIGHT_COLOR_3[0];
                rgbG =RGBUtils.LIGHT_COLOR_3[1];
                rgbB =RGBUtils.LIGHT_COLOR_3[2];
                break;

            case R.id.radioBt5:
                selectImg.setImageResource(R.mipmap.big_5);
                rgbR =RGBUtils.LIGHT_COLOR_4[0];
                rgbG =RGBUtils.LIGHT_COLOR_4[1];
                rgbB =RGBUtils.LIGHT_COLOR_4[2];
                break;

            case R.id.radioBt6:
                selectImg.setImageResource(R.mipmap.big_6);
                rgbR =RGBUtils.LIGHT_COLOR_5[0];
                rgbG =RGBUtils.LIGHT_COLOR_5[1];
                rgbB =RGBUtils.LIGHT_COLOR_5[2];
                break;

            case R.id.radioBt7:
                selectImg.setImageResource(R.mipmap.big_7);
                rgbR =RGBUtils.LIGHT_COLOR_6[0];
                rgbG =RGBUtils.LIGHT_COLOR_6[1];
                rgbB =RGBUtils.LIGHT_COLOR_6[2];
                break;

            case R.id.radioBt8:
                selectImg.setImageResource(R.mipmap.big_8);
                rgbR =RGBUtils.LIGHT_COLOR_7[0];
                rgbG =RGBUtils.LIGHT_COLOR_7[1];
                rgbB =RGBUtils.LIGHT_COLOR_7[2];
                break;

            case R.id.radioBt9:
                selectImg.setImageResource(R.mipmap.big_9);
                rgbR =RGBUtils.LIGHT_COLOR_8[0];
                rgbG =RGBUtils.LIGHT_COLOR_8[1];
                rgbB =RGBUtils.LIGHT_COLOR_8[2];
                break;

            case R.id.radioBt0:
                selectImg.setImageResource(R.mipmap.big_0);
                rgbR =RGBUtils.LIGHT_COLOR_9[0];
                rgbG =RGBUtils.LIGHT_COLOR_9[1];
                rgbB =RGBUtils.LIGHT_COLOR_9[2];
                break;

            //频闪
            case R.id.radio_dodge_1:
                lightDodge =RGBUtils.LIGHT_MOVE_FAST;
                break;

            case R.id.radio_dodge_2:
                lightDodge =RGBUtils.LIGHT_MOVE_SLOW;
                break;

            case R.id.radio_dodge_3:
                lightDodge = RGBUtils.LIGHT_MOVE_NO;
                break;

        }
    }


    private void reqModiftyLight(){

        final ReqModifyColor reqModifyColor = new ReqModifyColor();
        reqModifyColor.setColorB(rgbB);
        reqModifyColor.setColorR(rgbR);
        reqModifyColor.setColorG(rgbG);
        if(rgbB ==100 && rgbR ==100 && rgbG ==100){
            reqModifyColor.setIsFlash(100);
        }else if(rgbB ==101 && rgbR ==101 && rgbG ==101){
            reqModifyColor.setIsFlash(101);
        }else {
            reqModifyColor.setIsFlash(lightDodge);
        }
        reqModifyColor.setGroupId(groupId);

        HttpRequestProxy.get().create(reqModifyColor, new AbsHttpRequestProxy.RequestListener() {
            @Override
            public void onSuccess(Object response) {

                Log.i("info",response.toString());
                RespModifyColor respModifyColor =(RespModifyColor) response;

                if(respModifyColor.getCode() ==0){
                    ToastUtils.show(ModifyLightActivity.this,"修改成功");
                }else{
                    ToastUtils.show(ModifyLightActivity.this,respModifyColor.getMessage());
                }
            }

            @Override
            public void onFailed(VolleyError error) {
                Log.i("info",error.toString());
                ToastUtils.show(ModifyLightActivity.this,error.toString());
            }
        }).tag(this.toString()).build().excute();
    }
}
