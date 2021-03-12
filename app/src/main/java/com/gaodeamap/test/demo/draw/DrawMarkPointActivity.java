package com.gaodeamap.test.demo.draw;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.RotateAnimation;
import com.gaodeamap.test.demo.R;

/**
 * Create by Xiangshifu
 * on 2020/8/27 3:37 PM
 *
 *   绘制点标记
 */
public  class DrawMarkPointActivity extends AppCompatActivity {
    private MapView mapView;
    private AMap aMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //改变地图的默认显示区域
        LatLng latLng = new LatLng(34.341568,108.940174);
        AMapOptions mapOptions = new AMapOptions();
        mapOptions.camera(new CameraPosition(latLng,10.f,0,0));
        mapView = new MapView(this,mapOptions);

        setContentView(mapView);
         mapView.onCreate(savedInstanceState);

        aMap = mapView.getMap();
        aMap.setInfoWindowAdapter(new CustonerInfoWindow()); //绘制自定义的InfoWindow

         addMark();
        addCustomMarker();
    }

    private void addMark(){
        //绘制默认Marker
        LatLng latLng = new LatLng(39.906901,116.397972);
        Marker marker = aMap.addMarker(new MarkerOptions().position(latLng)//在地图上标记位置的经纬度值，必填参数
                .title("北京") //点标记的标题
                .snippet("DefaultMaker") //点标记的内容
                .draggable(true) //点标记是否可拖拽
                .visible(true) //点标记是否可见
                .anchor(0.2f,0.4f)//点标记的锚点
                .alpha(0.9f)); //点的透明度

        LatLng latLng2 = new LatLng(39.918901,116.399972);
        Marker marker2 = aMap.addMarker(new MarkerOptions().position(latLng2)//在地图上标记位置的经纬度值，必填参数
                .title("北京2") //点标记的标题
                .snippet("DefaultMaker2") //点标记的内容
                .draggable(true) //点标记是否可拖拽
                .visible(true) //点标记是否可见
                .anchor(0.2f,0.4f)//点标记的锚点
                .alpha(0.9f)); //点的透明度

    }

    private void addCustomMarker(){
        MarkerOptions markerOptions = new MarkerOptions();
        LatLng xianLatlng = new LatLng(34.341568,108.940174);
              markerOptions.position(xianLatlng)
                .title("西安市")
                .snippet("西安市大唐不夜城")
                .draggable(true)
                .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.poi_marker_7))) //设置图标
                .setFlat(true);//设置marker平贴地图效果
    final     Marker marker = aMap.addMarker(markerOptions);

        //添加动画
        Animation animation = new RotateAnimation(marker.getRotateAngle(),marker.getRotateAngle()+360);
        animation.setDuration(2000);
        marker.setAnimation(animation);
       marker.startAnimation();

       //添加点击事件
        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker m) {
                if (  m.equals(marker)){
                    Toast.makeText(DrawMarkPointActivity.this, "点击了大唐不夜城", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });

        //添加拖拽事件
        aMap.setOnMarkerDragListener(new AMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
                /**
                 * 当marker 开始拖拽时回调这个方法，这个marker的位置可以通过getPosition（）方法返回
                 * */
                Log.e("testtest"," ==========开始拖拽========== ");
            }

            @Override
            public void onMarkerDrag(Marker marker) {
                /**
                 * 在marker拖动过程中回调此方法, 这个marker的位置可以通过getPosition()方法返回
                 * */
             }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                /**
                 * 在marker拖动完成后回调此方法, 这个marker的位置可以通过getPosition()方法返回。
                 * */
             LatLng curLatLng =  marker.getPosition();
                Log.e("testtest"," ==========拖拽结束========== " );
                if (curLatLng != null){
                    Log.e("testtest"," ============== latitude : " + curLatLng.latitude + "  longitude: "+ curLatLng.longitude);
                }
            }
        });
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

    class CustonerInfoWindow implements AMap.InfoWindowAdapter{

        View infoWindowView;
        @Override
        public View getInfoWindow(Marker marker) {
            if (infoWindowView == null){
                infoWindowView = getLayoutInflater().inflate(R.layout.view_customer_infowindow,null,false);
            }
          TextView tvWindow =   infoWindowView.findViewById(R.id.tv_window);
            tvWindow.setText(System.currentTimeMillis() + "");
            return infoWindowView;
        }

        View infoContentsView;
        @Override
        public View getInfoContents(Marker marker) {
            if (infoContentsView == null){
                infoContentsView = new View(DrawMarkPointActivity.this);
                infoContentsView.setBackgroundResource(R.drawable.ic_launcher_background);
            }
            return infoContentsView;
        }
    }
}
