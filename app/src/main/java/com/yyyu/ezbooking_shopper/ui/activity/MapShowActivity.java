package com.yyyu.ezbooking_shopper.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.yyyu.barbecue.ezbooking_base.annotate.ViewInject;
import com.yyyu.barbecue.ezbooking_base.ui.activity.BaseActivity;
import com.yyyu.barbecue.ezbooking_base.utils.DimensChange;
import com.yyyu.ezbooking_shopper.R;
import com.yyyu.ezbooking_shopper.bean.foo.MapMarkInfoBean;

/**
 * 功能：地图展示
 * <p/>
 * <p/>
 * Created by yyyu on 2016/9/14.
 */
public class MapShowActivity extends BaseActivity {

    private static final String TAG = "MapShowActivity";

    @ViewInject(R.id.tv_back)
    private TextView tv_back;
    private MapView map_seller;

    private LocationClient mLocationClient;
    private MyLocationListener mLocationListener;
    private BaiduMap mBaiduMap;
    private float mLastOrientationX;
    private MyOrientationListener myOrientationListener;
    private BDLocation mLocation;
    private MapMarkInfoBean markBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_map_show;
    }

    @Override
    protected boolean isSwipeBack() {
        return false;
    }

    @Override
    protected void beforeInit() {
        super.beforeInit();
        Intent intent = getIntent();
        if (intent ==null){
            throw new UnsupportedOperationException("没有传入mark标注信息");
        }
        markBean = (MapMarkInfoBean) intent.getSerializableExtra("markBean");
    }

    @Override
    protected void initView() {
        initMap();
        initLocation();
        initDirectionSensor();
        initMark();
    }

    /**
     * 初始化基本地图
     */
    private void initMap() {
        map_seller = getView(R.id.map_seller);
        //---不显示百度logo
        map_seller.removeViewAt(1);
        //---得到 baidu map对象
        mBaiduMap = map_seller.getMap();
        //--- 改变地图状态，使地图显示在恰当的缩放大小
      /*  MapStatus mMapStatus = new MapStatus.Builder().zoom(15).build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        mBaiduMap.setMapStatus(mMapStatusUpdate);*/
    }

    /**
     * 初始化位置
     */
    private void initLocation() {
        //定位客户端的设置
        mLocationClient = new LocationClient(this);
        mLocationListener = new MyLocationListener();
        //注册监听
        mLocationClient.registerLocationListener(mLocationListener);
        //配置定位
        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll");//坐标类型
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//打开Gps
        option.setScanSpan(1000);//1000毫秒定位一次
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        mLocationClient.setLocOption(option);

    }

    /**
     * 初始化方向传感器
     */
    private void initDirectionSensor() {
        //---方向图标
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.icon_arrow);
        //---配置定位图层显示方式，使用自己的定位图标
        MyLocationConfiguration configuration = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, bitmapDescriptor);
        mBaiduMap.setMyLocationConfigeration(configuration);
        //---方向传感器监听
        myOrientationListener = new MyOrientationListener(this);
        myOrientationListener.setOnOrientationListener(new MyOrientationListener.OnOrientationListener() {
            @Override
            public void onOrientationChanged(float x) {
                mLastOrientationX = x;
                if (mLocation == null) return;
                //将获取的location信息给百度map
                MyLocationData data = new MyLocationData.Builder()
                        .accuracy(mLocation.getRadius())
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(mLastOrientationX)
                        .latitude(mLocation.getLatitude())
                        .longitude(mLocation.getLongitude())
                        .build();
                mBaiduMap.setMyLocationData(data);
            }
        });
    }

    /**
     * 初始化地图标注
     */
    private void initMark() {
        //清空地图
        mBaiduMap.clear();
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.icon_location);
        Marker marker;
        OverlayOptions options;
        LatLng latLng = new LatLng(markBean.getLatitue(), markBean.getLongitude());
        //设置marker
        options = new MarkerOptions()
                .position(latLng)//设置位置
                .icon(bitmap)//设置图标样式
                .zIndex(9) // 设置marker所在层级
                .draggable(true); // 设置手势拖拽;
        //添加marker
        marker = (Marker) mBaiduMap.addOverlay(options);
        //使用marker携带info信息，当点击事件的时候可以通过marker获得info信息
        Bundle bundle = new Bundle();
        //info必须实现序列化接口
        bundle.putSerializable("markBean", markBean);
        marker.setExtraInfo(bundle);
        //将地图显示在最后一个marker的位置
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
        mBaiduMap.setMapStatus(msu);

        //----添加mark的点击事件
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //从marker中获取info信息
                Bundle bundle = marker.getExtraInfo();
                MapMarkInfoBean markBean = (MapMarkInfoBean) bundle.getSerializable("markBean");
                //infowindow中的布局
                TextView tv = new TextView(MapShowActivity.this);
                tv.setBackgroundColor(Color.parseColor("#77000000"));
                tv.setPadding( DimensChange.dp2px(MapShowActivity.this, 20),
                        DimensChange.dp2px(MapShowActivity.this, 10),
                        DimensChange.dp2px(MapShowActivity.this, 20),
                        DimensChange.dp2px(MapShowActivity.this, 10));
                tv.setGravity(Gravity.CENTER);
                tv.setTextColor(android.graphics.Color.WHITE);
                tv.setText(markBean.getName());
                BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromView(tv);
                //infowindow位置
                LatLng latLng = new LatLng(markBean.getLatitue(), markBean.getLongitude());
                //infowindow点击事件
                InfoWindow.OnInfoWindowClickListener listener = new InfoWindow.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick() {
                        //隐藏infowindow
                        mBaiduMap.hideInfoWindow();
                    }
                };
                //显示infowindow，50dp向上偏移量
                InfoWindow infoWindow = new InfoWindow(bitmapDescriptor, latLng,
                        DimensChange.dp2px(MapShowActivity.this, -20),
                        listener);
                mBaiduMap.showInfoWindow(infoWindow);
                return true;
            }
        });
    }

    @Override
    protected void initListener() {
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapShowActivity.this.finish();
            }
        });
    }

    boolean isOnce = true;

    /**
     * 位置监听
     */
    private class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            mLocation = location;
            //将获取的location信息给百度map
            MyLocationData data = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mLastOrientationX)
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude())
                    .build();
            mBaiduMap.setMyLocationData(data);
            if(isOnce){
                //获取经纬度
                LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(ll);
                mBaiduMap.animateMapStatus(status);//动画的方式到中间
                float zoom = getZoomByDis(mLocation.getLatitude() , markBean.getLatitue() , mLocation.getLongitude() , markBean.getLongitude());
                MapStatus mMapStatus = new MapStatus.Builder().zoom(zoom).build();
                MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                mBaiduMap.setMapStatus(mMapStatusUpdate);
                isOnce = false;
            }
        }

    }

    /**
     * 方向传感器监听
     */
    public static class MyOrientationListener implements SensorEventListener {
        //传感器管理者
        private SensorManager mSensorManager;
        //上下文
        private Context mContext;
        //传感器
        private Sensor mSensor;
        //方向传感器有三个坐标，现在只关注X
        private float mLastX;

        //构造函数
        public MyOrientationListener(Context context) {
            this.mContext = context;
        }

        //开始监听
        @SuppressWarnings("deprecation")
        public void start() {
            //获得传感器管理者
            mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
            if (mSensorManager != null) {//是否支持
                //获得方向传感器
                mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
            }
            if (mSensor != null) {//如果手机有方向传感器，精度可以自己去设置，注册方向传感器
                mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_UI);
            }
        }

        //结束监听
        public void stop() {
            //取消注册的方向传感器
            mSensorManager.unregisterListener(this);
        }

        //传感器发生改变时
        @SuppressWarnings("deprecation")
        @Override
        public void onSensorChanged(SensorEvent event) {
            //判断返回的传感器类型是不是方向传感器
            if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
                //只获取x的值
                float x = event.values[SensorManager.DATA_X];
                //为了防止经常性的更新
                if (Math.abs(x - mLastX) > 1.0) {
                    if (onOrientationListener != null) {
                        onOrientationListener.onOrientationChanged(x);
                    }
                }
                mLastX = x;

            }
        }

        //当传感器精度发生改变，当前不用
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        private OnOrientationListener onOrientationListener;

        public void setOnOrientationListener(OnOrientationListener onOrientationListener) {
            this.onOrientationListener = onOrientationListener;
        }

        //回调方法
        public interface OnOrientationListener {
            void onOrientationChanged(float x);
        }

    }


    /**
     * 根据两点的坐标得到缩放值
     * @param maxlat
     * @param minlat
     * @param maxlon
     * @param minlon
     * @return
     */
    private float  getZoomByDis(double maxlat , double minlat , double maxlon , double minlon){
        int zoomLevel[] = {2000000,1000000,500000,200000,100000,
                50000,25000,20000,10000,5000,2000,1000,500,100,50,20,0};
        int jl = (int) DistanceUtil.getDistance(new LatLng(maxlat, maxlon), new LatLng(minlat, minlon));
        int i;
        for(i=0;i<17;i++){
            if(zoomLevel[i]<jl){
                break;
            }
        }
        return i+4;
    }


    @Override
    protected void onStart() {
        super.onStart();
        //---开启定位
        mBaiduMap.setMyLocationEnabled(true);
        if (!mLocationClient.isStarted()) {
            mLocationClient.start();
        }
        //---方向传感器
        myOrientationListener.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        map_seller.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        map_seller.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //---关闭方向传感器监听
        myOrientationListener.stop();
        //---关闭定位
        mBaiduMap.setMyLocationEnabled(false);
        if (mLocationClient.isStarted()) {
            mLocationClient.stop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        map_seller.onDestroy();
    }

    public static void startAction(Activity activity , MapMarkInfoBean markBean) {
        Intent intent = new Intent(activity, MapShowActivity.class);
        intent.putExtra("markBean" , markBean );
        activity.startActivity(intent);
    }

}
