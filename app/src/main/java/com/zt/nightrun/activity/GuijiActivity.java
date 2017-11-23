package com.zt.nightrun.activity;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.error.VolleyError;
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
import com.chris.common.network.HttpRequestProxy;
import com.chris.common.utils.ToastUtils;
import com.chris.common.view.BaseActivity;
import com.quncao.core.http.AbsHttpRequestProxy;
import com.zt.nightrun.R;
import com.zt.nightrun.model.req.ReqLocationHistory;
import com.zt.nightrun.model.resp.RespLocationHistory;

import java.util.ArrayList;
import java.util.List;

import static android.media.CamcorderProfile.get;

/**
 * 作者：by chris
 * 日期：on 2017/8/8 - 下午5:39.
 * 邮箱：android_cjx@163.com
 */

public class GuijiActivity extends BaseActivity{

    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private List<LatLng> points = new ArrayList<>();

    private BitmapDescriptor icon = null;
    private Marker mMarker;//标注
    private int index = 0;// 第几个点
    private int numberOfPoints;
    private LatLng llMiddle;
    private int deviceId;
    private int lastId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_guiji_layout,true);

        deviceId =getIntent().getIntExtra("deviceId",0);
        initView();

        reqLocationHistory();
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
        OverlayOptions ooPolyline = new PolylineOptions().width(10).color(0xAAFF0000).points(points);
        mBaiduMap.addOverlay(ooPolyline);
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(middleLatLng);
        mBaiduMap.setMapStatus(u);
        icon = BitmapDescriptorFactory.fromResource(R.mipmap.map_icon_point);
        OverlayOptions ooA = new MarkerOptions().position(points.get(points.size()-1)).zIndex(7)
                .icon(icon).draggable(false);
        mMarker = (Marker) (mBaiduMap.addOverlay(ooA));

    }


    private void reqLocationHistory(){

        final ReqLocationHistory reqLocationHistory = new ReqLocationHistory();
            reqLocationHistory.setDeviceId(deviceId);
        HttpRequestProxy.get().create(reqLocationHistory, new AbsHttpRequestProxy.RequestListener() {
            @Override
            public void onSuccess(Object response) {

                Log.i("info",response.toString());
                RespLocationHistory respLocationHistory =(RespLocationHistory) response;
                if(respLocationHistory.getData()!=null){
                    if(respLocationHistory.getData().getHistory().size()>0){

                        initLocationData();

                    }else{
                        ToastUtils.show(GuijiActivity.this,"该设备暂无历史记录");
                    }
                }

            }

            @Override
            public void onFailed(VolleyError error) {
                Log.i("info",error.toString());
                ToastUtils.show(GuijiActivity.this,error.toString());
            }
        }).tag(this.toString()).build().excute();

    }

    private void initLocationData(){
        LocationBean bean1 = new LocationBean();
        bean1.setLat(39.933306);
        bean1.setLng(116.395932);

        LocationBean bean2 = new LocationBean();
        bean2.setLat(39.940387);
        bean2.setLng(116.403406);

        LocationBean bean3 = new LocationBean();
        bean3.setLat(39.951008);
        bean3.setLng(116.401107);

        LocationBean bean4 = new LocationBean();
        bean4.setLat(39.955432);
        bean4.setLng(116.41433);

        LocationBean bean5 = new LocationBean();
        bean5.setLat(39.967157);
        bean5.setLng(116.400819);

        LocationBean bean6 = new LocationBean();
        bean6.setLat(39.968484);
        bean6.setLng(116.425253);

        LocationBean bean7 = new LocationBean();
        bean7.setLat(39.960742);
        bean7.setLng(116.451412);

        LocationBean bean8 = new LocationBean();
        bean8.setLat(39.948574);
        bean8.setLng(116.456874);

        LocationBean bean9 = new LocationBean();
        bean9.setLat(39.916097);
        bean9.setLng(116.496112);

        LocationBean bean10 = new LocationBean();
        bean10.setLat(39.909788);
        bean10.setLng(116.518533);

        LocationBean bean11 = new LocationBean();
        bean11.setLat(39.910342);
        bean11.setLng(116.509478);

        LocationBean bean12 = new LocationBean();
        bean12.setLat(39.904143);
        bean12.setLng(116.458886);

        LocationBean bean13 = new LocationBean();
        bean13.setLat(39.905028);
        bean13.setLng(116.461185);

        LocationBean bean14 = new LocationBean();
        bean14.setLat(39.905028);
        bean14.setLng(116.476133);

        LocationBean bean15 = new LocationBean();
        bean15.setLat(39.874469);
        bean15.setLng(116.522126);

        points.add(0,new LatLng(bean1.getLat(),bean1.getLng()));
        points.add(1,new LatLng(bean2.getLat(),bean2.getLng()));
        points.add(2,new LatLng(bean3.getLat(),bean3.getLng()));
        points.add(3,new LatLng(bean4.getLat(),bean4.getLng()));
        points.add(4,new LatLng(bean5.getLat(),bean5.getLng()));
        points.add(5,new LatLng(bean6.getLat(),bean6.getLng()));
        points.add(6,new LatLng(bean7.getLat(),bean7.getLng()));
        points.add(7,new LatLng(bean8.getLat(),bean8.getLng()));
        points.add(8, new LatLng(bean9.getLat(),bean9.getLng()));
        points.add(9,new LatLng(bean10.getLat(),bean10.getLng()));
        points.add(10,new LatLng(bean11.getLat(),bean11.getLng()));
        points.add(11,new LatLng(bean12.getLat(),bean12.getLng()));
        points.add(12,new LatLng(bean13.getLat(),bean13.getLng()));
        points.add(13,new LatLng(bean14.getLat(),bean14.getLng()));
        points.add(14,new LatLng(bean15.getLat(),bean15.getLng()));

        Log.i("location---",points.toString());

        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLngZoom(points.get(7), 12.0f);
        mBaiduMap.setMapStatus(msu);

        LatLng llStart = new LatLng(points.get(0).latitude, points.get(0).longitude);
        OverlayOptions ooStart = new MarkerOptions().position(points.get(0)).icon(BitmapDescriptorFactory
                .fromResource(R.mipmap.chufa_pic)).zIndex(4).draggable(false);

        if (mBaiduMap != null) {
            mBaiduMap.addOverlay(ooStart);
        }

        LatLng llA = new LatLng(points.get(points.size() - 1).latitude, points.get(points.size() - 1).longitude);

//        OverlayOptions ooEnd = new MarkerOptions().position(points.get(points.size()-1)).icon(BitmapDescriptorFactory
//                .fromResource(R.mipmap.chufa_pic)).zIndex(4).draggable(false);
//
//        if (mBaiduMap != null) {
//            mBaiduMap.addOverlay(ooEnd);
//        }


        llMiddle = new LatLng((points.get(0).latitude + points.get(points.size() - 1).latitude) / 2, (points.get(0).longitude + points.get(points.size() - 1).longitude) / 2);
        initOverlay(llMiddle);

    }

    public class LocationBean{
        private double lat;
        private double lng;

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        @Override
        public String toString() {
            return "LocationBean{" +
                    "lat=" + lat +
                    ", lng=" + lng +
                    '}';
        }
    }

}
