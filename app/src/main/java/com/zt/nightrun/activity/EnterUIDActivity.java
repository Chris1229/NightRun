package com.zt.nightrun.activity;

import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.error.VolleyError;
import com.chris.common.network.HttpRequestProxy;
import com.chris.common.utils.ToastUtils;
import com.chris.common.view.BaseActivity;
import com.quncao.core.http.AbsHttpRequestProxy;
import com.zt.nightrun.R;
import com.zt.nightrun.model.req.ReqActiveDevice;
import com.zt.nightrun.model.resp.RespActiveDevice;
import com.zt.nightrun.view.CustomEditDialog;

/**
 * 作者：by chris
 * 日期：on 2017/9/4 - 下午4:07.
 * 邮箱：android_cjx@163.com
 */

public class EnterUIDActivity extends BaseActivity {
    private EditText edUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uid_layout,true);
        initView();
    }

    private void initView(){

        setTitle("手动输入UID");

        edUid =(EditText)findViewById(R.id.etUid);

        setRightTv("提交").setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if(!TextUtils.isEmpty(edUid.getText().toString())){
                   CustomEditDialog dialog = new CustomEditDialog(EnterUIDActivity.this, "设备昵称", "请为设备设置一个昵称", InputType.TYPE_CLASS_TEXT, new CustomEditDialog.OnOkListener() {
                       @Override
                       public void onOkClick(CustomEditDialog dialog, String string) {

                           if(!TextUtils.isEmpty(string)){
                               dialog.dismiss();
                               showLoadingDialog();
                               reqActiveDevice(edUid.getText().toString(),"",string);
                           }else{
                               ToastUtils.show(EnterUIDActivity.this,"请输入设备昵称");
                           }
                       }
                   });

                   dialog.show();
               }else{
                   ToastUtils.show(EnterUIDActivity.this,"请输入设备UID");
               }
            }
        });

    }

    private void reqActiveDevice(String uid,String key,String name){
        final ReqActiveDevice reqActiveDevice = new ReqActiveDevice();

        reqActiveDevice.setUid(uid);
        if(!TextUtils.isEmpty(key)){
            reqActiveDevice.setQrcode(key);
        }
        reqActiveDevice.setName(name);
        reqActiveDevice.setIsActive(1);
        HttpRequestProxy.get().create(reqActiveDevice, new AbsHttpRequestProxy.RequestListener() {
            @Override
            public void onSuccess(Object response) {

                Log.i("info",response.toString());
                RespActiveDevice respActiveDevice =(RespActiveDevice) response;
                dismissLoadingDialog();
                if(respActiveDevice.getCode()==0){

                    ToastUtils.show(EnterUIDActivity.this,"设备绑定成功");
                    finish();

                }else{
                    ToastUtils.show(EnterUIDActivity.this,"设备绑定失败");
                }
            }

            @Override
            public void onFailed(VolleyError error) {
                dismissLoadingDialog();
                Log.i("info",error.toString());
                ToastUtils.show(EnterUIDActivity.this,error.toString());
            }
        }).tag(this.toString()).build().excute();

    }

}
