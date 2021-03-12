package com.gaodeamap.test.demo.draw;

import android.graphics.Color;
import android.os.Bundle;
import android.provider.SyncStateContract;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Polygon;
import com.amap.api.maps.model.PolygonOptions;
import com.gaodeamap.test.demo.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by Xiangshifu
 * on 2020/8/28 9:36 AM
 *  绘制面
 */
public  class DrawGraphActivity  extends AppCompatActivity {
    private MapView mapView;
    private AMap aMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapView = new MapView(this);
        setContentView(mapView);

        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();

//        drawaCircle();
//        drawEllopse();
//        drawRectangle();
        drawOtherGraph();
    }

    /**
     * 绘制圆形
     * */
    private void drawaCircle(){
        LatLng latLng = new LatLng(39.90928406062746,116.39807785106301);
      Circle circle =  aMap.addCircle(new CircleOptions()
          .center(latLng) //圆心
          .radius(1000) //半径,单位是米
              .fillColor(Color.BLUE) //填充的颜色
              .strokeColor(Color.RED) // 线条的颜色
              .strokeWidth(15) // 线条宽度，单位是像素
      );
    }

    /**
     * 绘制其它多边形形状
     * */
    private void drawOtherGraph(){
        List<LatLng> latLngList = new ArrayList<>();
        latLngList.add(new LatLng(39.94116662304082,116.39700496747541));
        latLngList.add(new LatLng(39.90893018374205,116.36501157889306));
        latLngList.add(new LatLng(39.906797625321666,116.44432852142008));
        PolygonOptions polygonOptions = new PolygonOptions();
        polygonOptions.addAll(latLngList);
        polygonOptions.fillColor(Color.YELLOW);
        polygonOptions.strokeColor(Color.BLACK);
        polygonOptions.strokeWidth(5);

        aMap.addPolygon(polygonOptions);
    }

    /**
     * 绘制矩形
     * */
    private void drawRectangle(){
        List<LatLng> latLngList = new ArrayList<>();
        latLngList.add(new LatLng(39.90928406062746,116.39807785106301));
        latLngList.add(new LatLng(39.93928406062746,116.39807785106301));
        latLngList.add(new LatLng(39.93928406062746,116.49807785106301));
        latLngList.add(new LatLng(39.90928406062746,116.49907785106301));

      Polygon polygon =   aMap.addPolygon(new PolygonOptions().addAll(latLngList)
        .fillColor(Color.RED)
        .strokeColor(Color.GREEN)
        .strokeWidth(5));
    }

    /**
     * 绘制椭圆
     * */
    private void drawEllopse(){
        List<LatLng> latLngList = new ArrayList<>();
        latLngList.add(new LatLng(39.90928406062746,116.39807785106301));
        latLngList.add(new LatLng(39.91928406062746,116.39807785106301));
        latLngList.add(new LatLng(39.91928406062746,116.39907785106301));
        latLngList.add(new LatLng(39.90928406062746,116.39907785106301));

        //绘制一个矩形
        aMap.addPolygon(new PolygonOptions()
        .addAll(latLngList)
        .fillColor(Color.GREEN)
        .strokeColor(Color.RED)
        .strokeWidth(1));

        //绘制椭圆
        PolygonOptions options = new PolygonOptions();
        int numPoints = 400;
        float semiHorizontalAxis = 0.05f; //控制经度的圈的大小
        float semiVerticalAxis = 0.025f; //控制纬度的圈的大小
        double phase = 2 * Math.PI / numPoints;
        for (int i = 0 ;i <= numPoints ;i++){
            options.add(new LatLng( Constants.BEIJING.latitude
                    + semiVerticalAxis * Math.sin(i * phase),
                     Constants.BEIJING.longitude + semiHorizontalAxis
                            * Math.cos(i * phase)));
        }
      Polygon polygon = aMap.addPolygon(options.strokeWidth(25)
        .strokeColor(Color.argb(50,1,1,1))
                .fillColor(Color.argb(50,1,1,1)));
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
