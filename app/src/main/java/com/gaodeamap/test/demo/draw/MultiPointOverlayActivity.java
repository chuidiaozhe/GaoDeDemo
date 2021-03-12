package com.gaodeamap.test.demo.draw;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
 import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MultiPointItem;
import com.amap.api.maps.model.MultiPointOverlay;
import com.amap.api.maps.model.MultiPointOverlayOptions;
import com.gaodeamap.test.demo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Create by Xiangshifu
 * on 2020/9/1 11:20 AM
 */
public class MultiPointOverlayActivity extends AppCompatActivity {
    private MapView mapView;
    private AMap mAMap;

    private boolean isDestory = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapView = new MapView(this);
        setContentView(mapView);

        mapView.onCreate(savedInstanceState);

        mAMap = mapView.getMap();

        drawMultiPoint();
    }

    private void drawMultiPoint(){
        //第一步：设置海量点属性
        MultiPointOverlayOptions overlayOptions = new MultiPointOverlayOptions();
        overlayOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.marker_blue));//设置图标
        overlayOptions.anchor(0.5f,0.5f); //设置锚点

        //第二步：添加海量点获取管理对象
        MultiPointOverlay multiPointOverlay = mAMap.addMultiPointOverlay(overlayOptions);

        //第三步：添加海量点
        List<MultiPointItem> list = new ArrayList<MultiPointItem>();
        Random random = new Random();
        double latitude = 34.00283981930922;
        double longitude = 103.93147231162742;
        int count  =10000;
        for (int i  = 0 ;i < count ;i++){
            double lat;
            double lng;
            if (random.nextInt(2) == 1){
               lat  =latitude +  random.nextDouble()*8.0d ;
            }else{
                lat =latitude - random.nextDouble()*8.0d;
            }

            if (random.nextInt(2) == 1){
                lng =longitude +  random.nextDouble()*25.0d ;
            }else{
                lng =longitude - random.nextDouble()*25.0d;
            }


                MultiPointItem item = new MultiPointItem(new LatLng(lat,lng));
                item.setTitle(String.format("%07d",i));
                list.add(item);
         }
        multiPointOverlay.setItems(list); //将规范化的点集交给海量点管理对象设置

        //海量点的点击事件
        AMap.OnMultiPointClickListener multiPointClickListener = new AMap.OnMultiPointClickListener(){

            @Override
            public boolean onPointClick(MultiPointItem multiPointItem) {
                Toast.makeText(MultiPointOverlayActivity.this,multiPointItem.getTitle(),Toast.LENGTH_SHORT).show();
                return true;
            }
        };
        mAMap.setOnMultiPointClickListener(multiPointClickListener);
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
        isDestory = true;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}
