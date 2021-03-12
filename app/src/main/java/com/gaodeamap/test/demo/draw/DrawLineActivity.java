package com.gaodeamap.test.demo.draw;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by Xiangshifu
 * on 2020/8/27 5:27 PM
 *
 *    绘制线条
 */
public  class DrawLineActivity  extends AppCompatActivity {
    private MapView mapView;
    private AMap aMap;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapView = new MapView(this);
        setContentView(mapView);
        mapView.onCreate(savedInstanceState);

        aMap = mapView.getMap();

        drawLine();
    }

    private void drawLine(){
        List<LatLng> latLngList = new ArrayList<>();
        latLngList.add(new LatLng(39.999391,116.135972));
        latLngList.add(new LatLng(39.898323,116.057694));
        latLngList.add(new LatLng(39.900430,116.265061));
        latLngList.add(new LatLng(39.955192,116.140092));

        Polyline polyline = aMap.addPolyline(new PolylineOptions()
        .addAll(latLngList) //添加全部的点
                .width(20) //线条的宽度
                .useGradient(true) //设置是否使用渐变色
                .setDottedLine(false) //设置是否画虚线，默认为false，画实线
                .color(Color.BLUE) //线条的颜色
                );


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
