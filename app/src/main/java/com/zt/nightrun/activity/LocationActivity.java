package com.zt.nightrun.activity;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.error.VolleyError;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.chris.common.network.HttpRequestProxy;
import com.chris.common.utils.ToastUtils;
import com.chris.common.view.BaseActivity;
import com.quncao.core.http.AbsHttpRequestProxy;
import com.zt.nightrun.R;
import com.zt.nightrun.model.req.ReqLocationHistory;
import com.zt.nightrun.model.resp.Location;
import com.zt.nightrun.model.resp.RespLocationHistory;

import java.util.List;

/**
 * 作者：by chris
 * 日期：on 2017/7/7 - 下午3:05.
 * 邮箱：android_cjx@163.com
 */

public class LocationActivity extends BaseActivity {

    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private LocationClient mBDLocationClient;
    private MyBDLocationListener mBDLocationListener;
    private boolean isFirstLoc=true;
    private MyLocationData locData;
    private static final int MY_PERMISSION_REQUEST_CODE = 10000;
    private int zoom;
    private int deviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_location_layout,true);

        deviceId =getIntent().getIntExtra("deviceId",0);
//        if (Build.VERSION.SDK_INT >= 23) {
//            ActivityCompat.requestPermissions(
//                    this,
//                    new String[] {
//                            Manifest.permission.READ_PHONE_STATE,
//                            Manifest.permission.ACCESS_COARSE_LOCATION,
//                            Manifest.permission.ACCESS_FINE_LOCATION,
//                            Manifest.permission.READ_EXTERNAL_STORAGE,
//                            Manifest.permission.WRITE_EXTERNAL_STORAGE
//                    },
//                    MY_PERMISSION_REQUEST_CODE
//            );
//        }

        setTitle("设备当前位置");

        mMapView =(MapView)findViewById(R.id.mapViewId);
        initMapView();

        reqLocation();
    }

    private void initMapView() {
        mMapView.showZoomControls(false);
        mMapView.showScaleControl(false);
        mMapView.removeViewAt(1); //隐藏百度logo
        mMapView.setLongClickable(true);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true); // 开启定位图层
        mBaiduMap.setTrafficEnabled(false); // 开启交通图

        initBaiduLocationListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }


    private void initBaiduLocationListener() {
        if (mBDLocationListener == null) {
            mBDLocationListener = new MyBDLocationListener();
        }
        mBDLocationClient = new LocationClient(this);
        mBDLocationClient.registerLocationListener(mBDLocationListener);

        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setOpenGps(true);
        option.setIsNeedAddress(true);
        option.setCoorType("bd09ll");
        option.setScanSpan(0);//0表示定位一次
        mBDLocationClient.setLocOption(option);

        mBDLocationClient.start();
    }


    private class MyBDLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null || mMapView == null) {
                return;
            }

            locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    .direction(location.getDirection())
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude())
                    .build();

            mBaiduMap.setMyLocationData(locData);

            if (isFirstLoc) {
                LatLng pointLocation = new LatLng(location.getLatitude(), location.getLongitude());

                MapStatus mMapStatus = new MapStatus.Builder()
                        .target(pointLocation)
                        .zoom(10)
                        .build();
                //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
                MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                mBaiduMap.animateMapStatus(mMapStatusUpdate);
            }

            location.setGpsAccuracyStatus(0);
        }


        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        if (mBDLocationClient != null && mBDLocationClient.isStarted()) {
            mBDLocationClient.stop();
        }
        mBDLocationClient = null;
        mBDLocationListener = null;
    }

    private void reqLocation(){

        final ReqLocationHistory reqLocationHistory = new ReqLocationHistory();
        reqLocationHistory.setDeviceId(deviceId);
        HttpRequestProxy.get().create(reqLocationHistory, new AbsHttpRequestProxy.RequestListener() {
            @Override
            public void onSuccess(Object response) {

                Log.i("info",response.toString());
                RespLocationHistory respLocationHistory =(RespLocationHistory) response;
                if(respLocationHistory.getData()!=null){
                    if(respLocationHistory.getData().getHistory().size()>0){
                        List<Location> locations = respLocationHistory.getData().getHistory();
                        Log.i("location====",locations.get(locations.size()-1).getLat()+","+locations.get(locations.size()-1).getLng());
                        initMarker(locations.get(locations.size()-1).getLat(),locations.get(locations.size()-1).getLng());

                    }else{
                        ToastUtils.show(LocationActivity.this,"该设备暂无历史记录");
                    }
                }

            }

            @Override
            public void onFailed(VolleyError error) {
                Log.i("info",error.toString());
                ToastUtils.show(LocationActivity.this,error.toString());
            }
        }).tag(this.toString()).build().excute();

    }

    private void initMarker(double lat,double lng){

        //定义Maker坐标点
//        LatLng point = new LatLng(lat, lng);
//        LatLng point = new LatLng(116.421028, 39.928832);
        LatLng point = new LatLng(39.928832,116.421028);
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.map_icon_pin);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);

        MapStatus mMapStatus = new MapStatus.Builder()
                .target(point)
                .zoom(12)
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        mBaiduMap.setMapStatus(mMapStatusUpdate);
    }

//    public int getZoom(double maxLat, double maxLng, double minLat, double minLng) {
//
//        int zoom[] = new int[]{50, 100, 200, 500, 1000, 2000, 5000, 10000, 20000, 25000, 50000, 100000, 200000, 500000, 1000000, 2000000};
//
//        double distance = BaiduMapUtil.getDistanceWithoutUtil(maxLat, maxLng, minLat, minLng);
//        for (int i = 0; i < zoom.length; i++) {
//            if (zoom[i] - distance > 0) {
//
//                return 18 - i + 3;
//
//            }
//
//        }
//
//        return 13;
//
//    }
}
