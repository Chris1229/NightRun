package com.zt.nightrun.activity;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;

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
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.chris.common.view.BaseActivity;
import com.zt.nightrun.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_location_layout,true);

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

//        setRightImage(R.mipmap.share).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//            }
//        });

        mMapView =(MapView)findViewById(R.id.mapViewId);
        initMapView();
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

//            if (isFirstLoc) {
//                isFirstLoc = false;
//                LatLng ll = new LatLng(location.getLatitude(),
//                        location.getLongitude());
//                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
//                mBaiduMap.animateMapStatus(u);
//            }
//
            //设置用户自定义定位图标
//            BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
//                    .fromResource(R.mipmap.map_icon_point);
//            MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, mCurrentMarker);
//            mBaiduMap.setMyLocationConfigeration(config);

            if (isFirstLoc) {
                LatLng pointLocation = new LatLng(location.getLatitude(), location.getLongitude());

                MapStatus mMapStatus = new MapStatus.Builder()
                        .target(pointLocation)
                        .zoom(15)
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

}
