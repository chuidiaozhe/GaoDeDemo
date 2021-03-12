package com.gaodeamap.test.demo.draw;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.HeatmapTileProvider;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.TileOverlayOptions;

import java.util.Arrays;

/**
 * Create by Xiangshifu
 * on 2020/8/28 11:11 AM
 */
public  class DrawHeatmapTileActivity extends AppCompatActivity {
    private MapView mapView;
    private AMap aMap;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapView = new MapView(this);
        setContentView(mapView);

        mapView.onCreate(savedInstanceState);

        aMap = mapView.getMap();

        drawHeat();
    }

    /**
     * 绘制热力图
     * */
    private void drawHeat(){
        //第一步 组织热力图数据
        LatLng[] latLngs = new LatLng[500];

        double x = 39.904979;
        double y = 116.40964;

        for (int i = 0 ; i  < 500 ;i ++){
            double x_ = 0;
            double y_ = 0;
            x_ = Math.random()*0.5 - 0.25;
            y_ = Math.random()*0.5 - 0.25;
            latLngs[i] = new LatLng(x+x_,y+y_);
        }

        //第二步 构建热力图 HeatmapTileProvider

        HeatmapTileProvider.Builder builder = new HeatmapTileProvider.Builder();
        builder.data(Arrays.asList(latLngs));//设置热力图绘制的数据
        builder.gradient(HeatmapTileProvider.DEFAULT_GRADIENT); // 设置热力图渐变，有默认值DEFAULT_GRADIENT
        HeatmapTileProvider heatmapTileProvider = builder.build();

        //第三步 绘制热力图图层
        TileOverlayOptions tileOverlayOptions = new TileOverlayOptions();
        tileOverlayOptions.tileProvider(heatmapTileProvider); // 设置瓦片图层的提供者
        aMap.addTileOverlay(tileOverlayOptions);//向地图上添加TileOverlayOptions类对象

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}
