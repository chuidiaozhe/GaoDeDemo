package com.gaodeamap.test.demo.interaction;

import android.os.Bundle;
import android.view.Gravity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.gaodeamap.test.demo.R;

/**
 * Create by Xiangshifu
 * on 2020/8/26 4:59 PM
 *
 *   与地图交互   -》控件的交互
 */
public  class WeightActivity extends AppCompatActivity  implements LocationSource {
    private MapView mapView;
    private AMap aMap;
    private UiSettings uiSettings; //定义一个UiSettings对象
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);
        mapView = findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);

        aMap = mapView.getMap();
        uiSettings = aMap.getUiSettings(); //实例化UiSettings对象

        //缩放按钮的显示
        uiSettings.setZoomControlsEnabled(true);//设置是否显示缩放按钮
         uiSettings.setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_CENTER);//设置显示缩放按钮的位置

        //指南针
        uiSettings.setCompassEnabled(true); // 是否显示指南针，默认不显示

        //定位按钮
        aMap.setLocationSource(this); // 通过aMap对象设置定位数据源的监听
        uiSettings.setMyLocationButtonEnabled(true); // 显示默认的定位按钮
        aMap.setMyLocationEnabled(true);//可触发定位并显示位置

        //比例尺
        uiSettings.setScaleControlsEnabled(true); //控制比例尺控件是否显示

        //地图logo
        uiSettings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_RIGHT);

    }

    @Override
    protected void onResume() {
        super.onResume();
       mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        //激活定位源
    }

    @Override
    public void deactivate() {
       //停止定位
    }
}
