package com.zt.nightrun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chris.common.view.BaseActivity;
import com.zt.nightrun.R;

/**
 * 作者：by chris
 * 日期：on 2017/7/9 - 上午11:30.
 * 邮箱：android_cjx@163.com
 */

public class TeamDetailsActivity extends BaseActivity implements View.OnClickListener{

    private RelativeLayout teamListLayout,colorLayout,locationLayout,codeLayout;
    private TextView tvOverTeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_team_detail,true);

        initUI();
    }

    private void initUI(){
        setRightImage(R.mipmap.share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        teamListLayout =(RelativeLayout)findViewById(R.id.teamListId);
        colorLayout =(RelativeLayout)findViewById(R.id.colorId);
        locationLayout =(RelativeLayout)findViewById(R.id.memberLocationId);
        codeLayout =(RelativeLayout)findViewById(R.id.teamCodeId);
        tvOverTeam =(TextView)findViewById(R.id.overTeam);

        teamListLayout.setOnClickListener(this);
        colorLayout.setOnClickListener(this);
        locationLayout.setOnClickListener(this);
        codeLayout.setOnClickListener(this);
        tvOverTeam.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.teamListId:
                startActivity(new Intent(this,TeamMemberListActivity.class));
                break;

            case R.id.colorId:

                break;

            case R.id.memberLocationId:

                break;


            case R.id.teamCodeId:

                break;

            case R.id.overTeam:

                break;


        }

    }
}
