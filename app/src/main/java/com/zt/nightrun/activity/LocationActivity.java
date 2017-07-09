package com.zt.nightrun.activity;

import android.os.Bundle;
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

    private MyLocationData locData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_location_layout,true);

        setTitle("设备当前位置");

        setRightImage(R.mipmap.share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        mMapView =(MapView)findViewById(R.id.mapViewId);
        initMapView();
    }

    private void initMapView() {
        mMapView.showZoomControls(false);
        mMapView.showScaleControl(false);
        mMapView.removeViewAt(1); //隐藏百度logo
        mMapView.setLongClickable(true);
        mBaiduMap = mMapView.getMap();
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

        mBaiduMap.setMyLocationEnabled(true); // 开启定位图层
        mBaiduMap.setTrafficEnabled(false); // 开启交通图

        LatLng ll = new LatLng(100,
                100);
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(ll).zoom(18.0f);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, null));

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

            // 设置用户自定义定位图标
            BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
                    .fromResource(R.mipmap.map_icon_point);
            MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, mCurrentMarker);
            mBaiduMap.setMyLocationConfigeration(config);

            LatLng pointLocation = new LatLng(location.getLatitude(), location.getLongitude());

            MapStatus mMapStatus = new MapStatus.Builder()
                    .target(pointLocation)
                    .zoom(18)
                    .build();
            //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
            MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
            mBaiduMap.animateMapStatus(mMapStatusUpdate);
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
