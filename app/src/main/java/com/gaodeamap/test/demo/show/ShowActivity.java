package com.gaodeamap.test.demo.show;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.AMapPara;
import com.gaodeamap.test.demo.R;

/**
 * Create by Xiangshifu
 * on 2020/8/25 1:45 PM
 */
public class ShowActivity  extends AppCompatActivity implements View.OnClickListener {
    MapView mapView;
    //地图控制器对象
    AMap aMap;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        //地图图层切换按钮
        findViewById(R.id.tv_navi).setOnClickListener(this);
        findViewById(R.id.tv_night).setOnClickListener(this);
        findViewById(R.id.tv_normal).setOnClickListener(this);
        findViewById(R.id.tv_satellite).setOnClickListener(this);

        mapView = findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);

        aMap = mapView.getMap();//初始化地图控制器对象

        showIndoorMap(true);
     }

    private void showIndoorMap(boolean isShow){
        if (aMap != null){
            //显示室内地图，若果是false不显示
            //缩放级别 >= 17级时，地图上显示室内地图
            //缩放级别 >= 18级时，不仅可看到室内地图效果，还允许操作切换楼层，显示精细化室内地图
            aMap.showIndoorMap(isShow);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_navi:
                //导航地图
                if (aMap != null){
                    aMap.setMapType(AMap.MAP_TYPE_NAVI);
                }
                break;
            case R.id.tv_night:
                //夜景地图
                if (aMap != null){
                    aMap.setMapType(AMap.MAP_TYPE_NIGHT);
                }
                break;
            case R.id.tv_normal :
                //普通地图
                if (aMap != null){
                    aMap.setMapType(AMap.MAP_TYPE_NORMAL);
                }
                break;
            case R.id.tv_satellite:
                //卫星地图
                if (aMap != null){
                    aMap.setMapType(AMap.MAP_TYPE_SATELLITE);
                }
                break;
        }
    }
}
