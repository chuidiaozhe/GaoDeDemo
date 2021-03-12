package com.gaodeamap.test.demo.interaction;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.gaodeamap.test.demo.R;

/**
 * Create by Xiangshifu
 * on 2020/8/27 11:00 AM
 *  高德地图手势交互
 */
public  class GestureActivity  extends AppCompatActivity {
    private MapView mapView;
    private AMap aMap;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture);
        mapView = findViewById(R.id.mapview);

        mapView.onCreate(savedInstanceState);

        aMap = mapView.getMap();

        aMap.getUiSettings().setMyLocationButtonEnabled(true);

        setZoomGesturesEnabled(true);
        setScrollGesturesEnabled(true);
        setRotateGesturesEnabled(true);
        setTiltGesturesEnabled(true);


        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        //在对地图进行手势操作时（滑动手势除外），可以指定屏幕中心点后执行相应手势,指定屏幕中心点的方法
        aMap.setPointToCenter(displayMetrics.widthPixels/4,displayMetrics.heightPixels/5);
        //开启关闭中心点进行手势操作的方法
        aMap.getUiSettings().setGestureScaleByMapCenter(true);

    }

    /***
     * 设置缩放手势
     * 缩放手势可以改变地图的缩放级别，地图响应的手势如下：
     *   1.双击地图可以使缩放级别增加1（放大）
     *   2.两个手指捏/拉伸
     * 也可以禁用或启用缩放手势。禁用缩放手势不会影响用户使用地图上的缩放控制按钮
     * */
    private void setZoomGesturesEnabled(boolean enabled){
        aMap.getUiSettings().setZoomGesturesEnabled(enabled);
    }

    /**
     * 你可以用手指拖动地图四处滚动（平移）或用手指滑动地图（动画效果），也可以禁用或开启
     * 平移（滑动）手势
     * */
    private  void setScrollGesturesEnabled(boolean enabled){
        aMap.getUiSettings().setScrollGesturesEnabled(enabled);
     }

     /**
      * 你可以用两个手指在地图上转动，可以旋转3D矢量地图，有可以禁用旋转手势
      * */
     private void setRotateGesturesEnabled(boolean enabled){
        aMap.getUiSettings().setRotateGesturesEnabled(enabled);
     }

     /**
      * 用户可以在地图上放置两个手指，移动它们一起向下或向上去增加或减少倾斜角度，
      * 也可以禁用倾斜手势
      * */
     private void setTiltGesturesEnabled(boolean enabled){
         aMap.getUiSettings().setTiltGesturesEnabled(enabled);
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
