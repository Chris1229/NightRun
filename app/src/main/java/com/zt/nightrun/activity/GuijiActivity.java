package com.zt.nightrun.activity;

import android.os.Bundle;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.chris.common.view.BaseActivity;
import com.zt.nightrun.R;

import java.util.List;

/**
 * 作者：by chris
 * 日期：on 2017/8/8 - 下午5:39.
 * 邮箱：android_cjx@163.com
 */

public class GuijiActivity extends BaseActivity{

    private MapView mMapView;
    private BaiduMap mBaiduMap;
//    private List<GuiJiObj> list = new ArrayList<GuiJiObj>();

    private List<LatLng> points = null;
    private BitmapDescriptor icon = null;
    private Marker mMarker;//标注
    private int index = 0;// 第几个点
    private int numberOfPoints;
    private LatLng llMiddle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_guiji_layout,true);

        initView();
    }

    private void initView(){
        setTitle("设备历史轨迹");

        // 地图初始化
        mMapView = (MapView) findViewById(R.id.mapViewId);

        initMapView();


    }

    private void initMapView() {
        mMapView.showZoomControls(false);
        mMapView.showScaleControl(false);
        mMapView.removeViewAt(1); //隐藏百度logo
        mMapView.setLongClickable(true);
        mBaiduMap = mMapView.getMap();
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLngZoom(new LatLng(22.553719, 113.925328), 15.0f);
        mBaiduMap.setMapStatus(msu);
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, null));
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                return false;
            }
        });
    }

    // 初始化
    public void initOverlay(LatLng middleLatLng) {
        OverlayOptions ooPolyline = new PolylineOptions().width(5).color(0xAAFF0000).points(points);
        mBaiduMap.addOverlay(ooPolyline);
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(middleLatLng);
        mBaiduMap.setMapStatus(u);
        icon = BitmapDescriptorFactory.fromResource(R.mipmap.mudi);
        OverlayOptions ooA = new MarkerOptions().position(points.get(index)).zIndex(7)
                .icon(icon).draggable(false);
        mMarker = (Marker) (mBaiduMap.addOverlay(ooA));
    }

}
