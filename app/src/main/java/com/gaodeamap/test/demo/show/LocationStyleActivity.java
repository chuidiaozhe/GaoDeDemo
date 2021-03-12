package com.gaodeamap.test.demo.show;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
 import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.MyLocationStyle;
import com.gaodeamap.test.demo.R;

/**
 * Create by Xiangshifu
 * on 2020/8/25 3:41 PM
 *
 *  高德地图实现定位蓝点
 */
public  class LocationStyleActivity  extends AppCompatActivity {
    private static final int STROKE_COLOR = Color.argb(180, 3, 145, 255);
    private static final int FILL_COLOR = Color.argb(10, 0, 0, 180);
    private MapView mapView;
    //地图控制器对象
    private AMap aMap;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_style);
        mapView = findViewById(R.id.mapview);

        mapView.onCreate(savedInstanceState);

        aMap = mapView.getMap();

//        //实现定位蓝点
        MyLocationStyle locationStyle;
        locationStyle = new MyLocationStyle();//初始化定位蓝点样式
        locationStyle.interval(2000);//设置连续定位模式下的的定位间隔，值在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒
        locationStyle.showMyLocation(true);
        locationStyle.strokeColor(Color.BLUE); //外圈颜色
        locationStyle.radiusFillColor(Color.RED);//内圈颜色
        locationStyle.strokeWidth(4); //外圈的宽度
        locationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.gps_point));
         locationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE);
        aMap.setMyLocationStyle(locationStyle); //设置定位蓝点的style
        aMap.getUiSettings().setMyLocationButtonEnabled(false); // 设置默认定位按钮是否显示，非必须设置

        locationStyle.anchor(0.0f,1.0f);
        aMap.setMyLocationEnabled(true);//设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false
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

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}
