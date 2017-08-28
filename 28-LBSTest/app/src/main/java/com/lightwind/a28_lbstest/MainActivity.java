package com.lightwind.a28_lbstest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // 定位的类
    LocationClient mLocationClient;
    // 显示定位的信息
    private TextView positionTextView;
    // 地图
    private MapView mMapView;
    // BaiduMap对象
    private BaiduMap baiduMap;
    // 是否是第一次定位
    private boolean isFirstLocate = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        setContentView(R.layout.activity_main);
        // 初始化MapView
        mMapView = (MapView) findViewById(R.id.baidu_map);
        baiduMap = mMapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        positionTextView = (TextView) findViewById(R.id.position_text_view);
        // 保存权限的类
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission
                .ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        } else if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission
                .READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        } else if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission
                .WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(MainActivity.this, permissions, 1);
        } else {
            requestLocation();
        }
    }

    /**
     * 在地图上定位的方法
     */
    private void navigateTo(BDLocation location) {
        if (isFirstLocate) {
            LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(latlng);
            baiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomTo(16f);
            baiduMap.animateMapStatus(update);
            isFirstLocate = false;
        }
        MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
        locationBuilder.latitude(location.getLatitude());
        locationBuilder.longitude(location.getLongitude());
        MyLocationData locationData = locationBuilder.build();
        baiduMap.setMyLocationData(locationData);
    }

    /**
     * 开启定位功能
     */
    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(5000);
        // 设置使用GSP定位
        option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);
        // 表示需要获取当前位置的详细地址信息
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
        mMapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0) {
                for (int result : grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "必须同意所有权限才能使用本程序！", Toast.LENGTH_SHORT).show();
                        finish();
                        return;
                    }
                }
                requestLocation();
            } else {
                Toast.makeText(this, "发生未知错误！", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    /**
     * 用于获取位置的信息
     */
    private class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation.getLocType() == BDLocation.TypeGpsLocation || bdLocation.getLocType()
                    == BDLocation.TypeNetWorkLocation) {
                navigateTo(bdLocation);
            }
//            StringBuilder currentPosition = new StringBuilder();
//            currentPosition.append("纬度：").append(bdLocation.getLatitude()).append("\n");
//            currentPosition.append("经度：").append(bdLocation.getLongitude()).append("\n");
//            currentPosition.append("国家：").append(bdLocation.getCountry()).append("\n");
//            currentPosition.append("省：").append(bdLocation.getProvince()).append("\n");
//            currentPosition.append("市：").append(bdLocation.getCity()).append("\n");
//            currentPosition.append("区：").append(bdLocation.getDistrict()).append("\n");
//            currentPosition.append("街道：").append(bdLocation.getStreet()).append("\n");
//            currentPosition.append("定位方式：");
//            if (bdLocation.getLocType() == BDLocation.TypeGpsLocation) {
//                currentPosition.append("GPS");
//            } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
//                currentPosition.append("网络");
//            }
//            positionTextView.setText(currentPosition);
        }
    }
}
