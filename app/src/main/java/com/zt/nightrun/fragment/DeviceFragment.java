package com.zt.nightrun.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.android.volley.error.VolleyError;
import com.chris.common.network.HttpRequestProxy;
import com.chris.common.utils.ToastUtils;
import com.chris.common.view.BaseFragment;
import com.quncao.core.http.AbsHttpRequestProxy;
import com.zt.nightrun.R;
import com.zt.nightrun.activity.DeviceDetailsActivity;
import com.zt.nightrun.activity.MainActivity;
import com.zt.nightrun.adapter.DeviceListViewAdapter;
import com.zt.nightrun.model.req.ReqActiveDevice;
import com.zt.nightrun.model.req.ReqDeviceList;
import com.zt.nightrun.model.resp.DeviceItem;
import com.zt.nightrun.model.resp.RespActiveDevice;
import com.zt.nightrun.model.resp.RespDeviceList;
import com.zt.nightrun.view.CustomEditDialog;
import com.zt.nightrun.zxing.activity.CaptureActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.IXListViewRefreshListener;
import me.maxwin.view.XListView;

/**
 * 作者：by chris
 * 日期：on 2017/6/28 - 下午4:59.
 * 邮箱：android_cjx@163.com
 */

public class DeviceFragment extends BaseFragment implements AdapterView.OnItemClickListener, IXListViewRefreshListener, IXListViewLoadMore {

    private XListView mListView;
    private LinearLayout addLinear;
    private DeviceListViewAdapter deviceListViewAdapter;
    private View headView;
    private List<DeviceItem> list = new ArrayList<>();

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mListView.stopRefresh(new Date());
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        headView = LayoutInflater.from(getActivity()).inflate(R.layout.listview_head_foot_layout, null);
    }

    @Override
    public void onResume() {
        super.onResume();

        mListView.startRefresh();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_device, null);
        mListView = (XListView) view.findViewById(R.id.listView);
        addLinear = (LinearLayout) view.findViewById(R.id.addLinear);
        mListView.addHeaderView(headView);
//        mListView.addFooterView(headView);
        mListView.setPullRefreshEnable(this);
        mListView.disablePullLoad();
        deviceListViewAdapter = new DeviceListViewAdapter(getActivity(), list);
        mListView.setAdapter(deviceListViewAdapter);
//        mListView.setPullLoadEnable(this);
        mListView.setOnItemClickListener(this);
        addLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomEditDialog dialog = new CustomEditDialog(getActivity(), "设备uid", "输入一个设备uid", InputType.TYPE_CLASS_NUMBER, new CustomEditDialog.OnOkListener() {
                    @Override
                    public void onOkClick(CustomEditDialog dialog, String string) {

                        if (!TextUtils.isEmpty(string)) {
                            dialog.dismiss();
                            showLoadingDialog();
                            reqActiveDevice(string);
                        } else {
                            ToastUtils.show(getActivity(), "请输入设备uid");
                        }
                    }
                });

                dialog.show();
            }
        });
        return view;

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Log.i("info===--",position+"");
        position = position - 2;
        Intent intent = new Intent(getActivity(), DeviceDetailsActivity.class);
        intent.putExtra("uid", list.get(position).getDevice().getUid());
        intent.putExtra("deviceId", list.get(position).getDevice().getId());
        startActivity(intent);
    }

    @Override
    public void onLoadMore() {
//        handler.sendMessageDelayed(handler.obtainMessage(0),3000);
    }

    @Override
    public void onRefresh() {

        reqDeviceList();
//        handler.sendMessageDelayed(handler.obtainMessage(0),3000);
    }

    private void reqDeviceList() {

        final ReqDeviceList reqDeviceList = new ReqDeviceList();
        reqDeviceList.setMine(true);
        HttpRequestProxy.get().create(reqDeviceList, new AbsHttpRequestProxy.RequestListener() {
            @Override
            public void onSuccess(Object response) {
                dismissLoadingDialog();
                handler.sendEmptyMessageDelayed(0,1000);
                Log.i("info", response.toString());
                RespDeviceList respDeviceList = (RespDeviceList) response;
                if (respDeviceList.getData() != null && respDeviceList.getData().getListDeviceVo() != null && respDeviceList.getData().getListDeviceVo().size() > 0) {
                    list.clear();
                    list.addAll(respDeviceList.getData().getListDeviceVo());
                    addLinear.setVisibility(View.GONE);
                    mListView.setVisibility(View.VISIBLE);
                    deviceListViewAdapter.notifyDataSetChanged();
//                    deviceListViewAdapter = new DeviceListViewAdapter(getActivity(), list);
//                    mListView.setAdapter(deviceListViewAdapter);

                } else {
                    mListView.setVisibility(View.GONE);
                    addLinear.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailed(VolleyError error) {
                Log.i("info", error.toString());
                handler.sendEmptyMessageDelayed(0,1000);
                dismissLoadingDialog();
                ToastUtils.show(getActivity(), error.toString());
            }
        }).tag(this.toString()).build().excute();

    }

    private void reqActiveDevice(String uid) {
        final ReqActiveDevice reqActiveDevice = new ReqActiveDevice();

        reqActiveDevice.setUid(uid);
        reqActiveDevice.setIsActive(1);
        HttpRequestProxy.get().create(reqActiveDevice, new AbsHttpRequestProxy.RequestListener() {
            @Override
            public void onSuccess(Object response) {

                Log.i("info", response.toString());
                RespActiveDevice respActiveDevice = (RespActiveDevice) response;
                dismissLoadingDialog();
                if (respActiveDevice.getData().getResultCode() == 0) {

                    ToastUtils.show(getActivity(), "设备绑定成功");

                } else {

                    ToastUtils.show(getActivity(), "设备绑定失败");
                }

                mListView.startRefresh();
            }

            @Override
            public void onFailed(VolleyError error) {
                dismissLoadingDialog();
                Log.i("info", error.toString());
                ToastUtils.show(getActivity(), error.toString());
            }
        }).tag(this.toString()).build().excute();

    }
}
