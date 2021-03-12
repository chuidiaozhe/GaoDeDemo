package com.gaodeamap.test.demo.interaction;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.gaodeamap.test.demo.R;

/**
 * Create by Xiangshifu
 * on 2020/8/27 3:02 PM
 *
 *  调用方法交互
 */
public class MethodActivity extends AppCompatActivity {
    private MapView mapView;
    private AMap aMap;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_method);
        mapView = findViewById(R.id.mapview);

        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();


        //设置缩放级别为10
        CameraUpdate mCameraUpdate = CameraUpdateFactory.zoomTo(13);
        aMap.animateCamera(mCameraUpdate);

        //限制地图的显示范围
        LatLng southwestLatLng = new LatLng(33.789925, 104.838326);
        LatLng northeastLatlng = new LatLng(38.740688, 114.647472);
        LatLngBounds latLngBounds = new LatLngBounds(southwestLatLng,northeastLatlng);
        aMap.setMapStatusLimits(latLngBounds);

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
}
