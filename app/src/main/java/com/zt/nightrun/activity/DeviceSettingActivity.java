package com.zt.nightrun.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.chris.common.utils.SharedPreferencesUtil;
import com.chris.common.view.BaseActivity;
import com.zt.nightrun.NightRunApplication;
import com.zt.nightrun.R;
import com.zt.nightrun.blue.model.BlueFunction;
import com.zt.nightrun.blue.utils.BlueUtils;
import com.zt.nightrun.blue.utils.BluetoothController;

/**
 * 作者：by chris
 * 日期：on 2017/8/30 - 下午8:04.
 * 邮箱：android_cjx@163.com
 */

public class DeviceSettingActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener{

    private RadioGroup mRadioGroup;
    private RadioGroup mRadioGroupLight;
    private RadioGroup mRadioGroupDodge;
    private RadioGroup mRadioGroupMove;
    private ImageView selectImg;
    private String lightBright;
    private String lightColor;
    private String lightDodge;
    private String lightMove;
    private BlueFunction blueFunction;
    private BluetoothController controller = BluetoothController.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_device_setting,true);

        blueFunction =BlueUtils.getFunction();
        initView();
        initSelect(blueFunction);
    }

    private void initView(){

        setTitle("设置本设备");
        mRadioGroup =(RadioGroup)findViewById(R.id.radioGroup);
        mRadioGroupLight =(RadioGroup)findViewById(R.id.radioGroupLight);
        mRadioGroupDodge =(RadioGroup)findViewById(R.id.radiogroup_dodge);
        mRadioGroupMove =(RadioGroup)findViewById(R.id.radiogroup_move);
        selectImg =(ImageView)findViewById(R.id.selectImg);
        mRadioGroup.setOnCheckedChangeListener(this);
        mRadioGroupLight.setOnCheckedChangeListener(this);
        mRadioGroupDodge.setOnCheckedChangeListener(this);
        mRadioGroupMove.setOnCheckedChangeListener(this);

    }

    @Override
    public void onCheckedChanged(RadioGroup group,int checkedId) {

        switch (checkedId){

            //亮度
            case R.id.radio_light_1:
                lightBright =BlueUtils.BLUE_LIGHT_BRIGHT_ALL;
                writeCode(lightBright);
                break;

            case R.id.radio_light_2:
                lightBright =BlueUtils.BLUE_LIGHT_BRIGHT_CENTER;
                writeCode(lightBright);
                break;

            case R.id.radio_light_3:
                lightBright =BlueUtils.BLUE_LIGHT_BRIGHT_SMALL;
                writeCode(lightBright);
                break;

            //颜色
            case R.id.radioBt1:
                selectImg.setImageResource(R.mipmap.big_1);
                lightColor =BlueUtils.BLUE_LIGHT_COLOR_1;
                writeCode(lightColor+lightDodge);
                break;

            case R.id.radioBt2:
                selectImg.setImageResource(R.mipmap.big_2);
                lightColor =BlueUtils.BLUE_LIGHT_COLOR_2;
                writeCode(lightColor+lightDodge);
                break;

            case R.id.radioBt3:
                selectImg.setImageResource(R.mipmap.big_3);
                lightColor =BlueUtils.BLUE_LIGHT_COLOR_3;
                writeCode(lightColor+lightDodge);
                break;

            case R.id.radioBt4:
                selectImg.setImageResource(R.mipmap.big_4);
                lightColor =BlueUtils.BLUE_LIGHT_COLOR_4;
                writeCode(lightColor+lightDodge);
                break;

            case R.id.radioBt5:
                selectImg.setImageResource(R.mipmap.big_5);
                lightColor =BlueUtils.BLUE_LIGHT_COLOR_5;
                writeCode(lightColor+lightDodge);
                break;

            case R.id.radioBt6:
                selectImg.setImageResource(R.mipmap.big_6);
                lightColor =BlueUtils.BLUE_LIGHT_COLOR_6;
                writeCode(lightColor+lightDodge);
                break;

            case R.id.radioBt7:
                selectImg.setImageResource(R.mipmap.big_7);
                lightColor =BlueUtils.BLUE_LIGHT_COLOR_7;
                writeCode(lightColor+lightDodge);
                break;

            case R.id.radioBt8:
                selectImg.setImageResource(R.mipmap.big_8);
                lightColor =BlueUtils.BLUE_LIGHT_COLOR_8;
                writeCode(lightColor+lightDodge);
                break;

            case R.id.radioBt9:
                selectImg.setImageResource(R.mipmap.big_9);
                lightColor =BlueUtils.BLUE_LIGHT_COLOR_9;
                writeCode(lightColor+lightDodge);
                break;

            case R.id.radioBt0:
                selectImg.setImageResource(R.mipmap.big_0);
                lightColor =BlueUtils.BLUE_LIGHT_COLOR_0;
                writeCode(lightColor+lightDodge);
                break;

            //频闪
            case R.id.radio_dodge_1:
                lightDodge =BlueUtils.BLUE_LIGHT_DODGE_FAST;
                writeCode(lightColor+lightDodge);
                break;

            case R.id.radio_dodge_2:
                lightDodge =BlueUtils.BLUE_LIGHT_DODGE_SLOW;
                writeCode(lightColor+lightDodge);
                break;

            case R.id.radio_dodge_3:
                lightDodge =BlueUtils.BLUE_LIGHT_DODGE_NO;
                writeCode(lightColor+lightDodge);
                break;

            //震动
            case R.id.radio_move_1:
                lightMove =BlueUtils.BLUE_LIGHT_MOVE_ALL;
                writeCode(lightMove);
                break;

            case R.id.radio_move_2:
                lightMove =BlueUtils.BLUE_LIGHT_MOVE_INTERMISSION;
                writeCode(lightMove);
                break;

            case R.id.radio_move_3:
                lightMove =BlueUtils.BLUE_LIGHT_MOVE_NO;
                writeCode(lightMove);
                break;

        }
    }


    private void initSelect(BlueFunction function){
        if(!TextUtils.isEmpty(function.getLightBright())){
            if(function.getLightBright().equals(BlueUtils.BLUE_LIGHT_BRIGHT_ALL)){
                mRadioGroupLight.check(R.id.radio_light_1);
            }else if(function.getLightBright().equals(BlueUtils.BLUE_LIGHT_BRIGHT_CENTER)){
                mRadioGroupLight.check(R.id.radio_light_2);
            }else if(function.getLightBright().equals(BlueUtils.BLUE_LIGHT_BRIGHT_SMALL)){
                mRadioGroupLight.check(R.id.radio_light_3);
            }
        }

        if(TextUtils.isEmpty(function.getLightDodge())){
            lightDodge =BlueUtils.BLUE_LIGHT_DODGE_NO;
        }else{
            lightDodge =function.getLightDodge();
        }

        if(TextUtils.isEmpty(function.getLightMove())){
            lightMove =BlueUtils.BLUE_LIGHT_MOVE_NO;
        }else{
            lightMove =function.getLightMove();
        }

        if(!TextUtils.isEmpty(function.getLightColor())){
            if(function.getLightColor().equals(BlueUtils.BLUE_LIGHT_COLOR_1)){
                mRadioGroup.check(R.id.radioBt1);
            }else if(function.getLightColor().equals(BlueUtils.BLUE_LIGHT_COLOR_2)){
                mRadioGroup.check(R.id.radioBt2);
            }else if(function.getLightColor().equals(BlueUtils.BLUE_LIGHT_COLOR_3)){
                mRadioGroup.check(R.id.radioBt3);
            }else if(function.getLightColor().equals(BlueUtils.BLUE_LIGHT_COLOR_4)){
                mRadioGroup.check(R.id.radioBt4);
            }else if(function.getLightColor().equals(BlueUtils.BLUE_LIGHT_COLOR_5)){
                mRadioGroup.check(R.id.radioBt5);
            }else if(function.getLightColor().equals(BlueUtils.BLUE_LIGHT_COLOR_6)){
                mRadioGroup.check(R.id.radioBt6);
            }else if(function.getLightColor().equals(BlueUtils.BLUE_LIGHT_COLOR_7)){
                mRadioGroup.check(R.id.radioBt7);
            }else if(function.getLightColor().equals(BlueUtils.BLUE_LIGHT_COLOR_8)){
                mRadioGroup.check(R.id.radioBt8);
            }else if(function.getLightColor().equals(BlueUtils.BLUE_LIGHT_COLOR_9)){
                mRadioGroup.check(R.id.radioBt9);
            }else if(function.getLightColor().equals(BlueUtils.BLUE_LIGHT_COLOR_0)){
                mRadioGroup.check(R.id.radioBt0);
            }
        }else{
            lightColor =BlueUtils.BLUE_LIGHT_COLOR_1;
            mRadioGroup.check(R.id.radioBt1);
        }

        if(lightDodge.equals(BlueUtils.BLUE_LIGHT_DODGE_FAST)){
            mRadioGroupDodge.check(R.id.radio_dodge_1);
        }else if(lightDodge.equals(BlueUtils.BLUE_LIGHT_DODGE_SLOW)){
            mRadioGroupDodge.check(R.id.radio_dodge_2);
        }else if(lightDodge.equals(BlueUtils.BLUE_LIGHT_DODGE_NO)){
            mRadioGroupDodge.check(R.id.radio_dodge_3);
        }

        if(lightMove.equals(BlueUtils.BLUE_LIGHT_MOVE_ALL)){
            mRadioGroupMove.check(R.id.radio_move_1);
        }else if(lightMove.equals(BlueUtils.BLUE_LIGHT_MOVE_INTERMISSION)){
            mRadioGroupMove.check(R.id.radio_move_2);
        }else if(lightMove.equals(BlueUtils.BLUE_LIGHT_MOVE_NO)){
            mRadioGroupMove.check(R.id.radio_move_3);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferencesUtil.saveString(NightRunApplication.getApplicationConext(),"lightBright",lightBright);
        SharedPreferencesUtil.saveString(NightRunApplication.getApplicationConext(),"lightColor",lightColor);
        SharedPreferencesUtil.saveString(NightRunApplication.getApplicationConext(),"lightDodge",lightDodge);
        SharedPreferencesUtil.saveString(NightRunApplication.getApplicationConext(),"lightMove",lightMove);
    }

    private void writeCode(String strCode){
        controller.write("$AWF"+strCode);
        System.out.println("$AWF"+strCode);
    }
}
