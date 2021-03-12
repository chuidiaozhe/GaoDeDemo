package com.gaodeamap.test.demo.draw;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Pair;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.maps.model.Text;
import com.amap.api.maps.utils.SpatialRelationUtil;
import com.amap.api.maps.utils.overlay.MovingPointOverlay;
import com.amap.api.maps.utils.overlay.SmoothMoveMarker;
import com.gaodeamap.test.demo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Create by Xiangshifu
 * on 2020/8/31 2:33 PM
 *
 * 点平滑移动（例如汽车移动路线）
 */
public  class SmoothMoveActivity  extends AppCompatActivity  implements View .OnClickListener{
    private MapView mapView;
    private AMap aMap;

    private MovingPointOverlay smoothMarker;
    private Marker marker;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smooth_move);

        mapView = findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);

        aMap = mapView.getMap();

        findViewById(R.id.btn_set).setOnClickListener(this);
        findViewById(R.id.btn_start).setOnClickListener(this);
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
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_set:
                if (mPolyLine == null){
                    addPolylineInPlayGround();
                }
                break;
            case R.id.btn_start:
//                startMove();
                startMove2();
                break;
        }

    }

    /**
     * 点击开始移动按钮
     *
     *  方法二：是根据demo里面的代码写的
     * */
    private void startMove2(){
        if (mPolyLine == null){
            Toast.makeText(this, "请先设置滑动路径", Toast.LENGTH_SHORT).show();
            return ;
        }

        //读取轨迹点
        List<LatLng> points = readLatlngs();
        //构建轨迹的显示区域
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(points.get(0));
        builder.include(points.get(points.size() - 2));
        aMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(),50));

        //实例MovingPointOverlay对象
        if (smoothMarker == null){
            //设置平滑移动的图标
            marker = aMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_car)).anchor(0.5f,0.5f));
            smoothMarker = new MovingPointOverlay(aMap,marker);
        }

        //取轨迹点的第一个点作为平滑移动的启动
        LatLng drivePoint = points.get(0);
        Pair<Integer,LatLng> pair = SpatialRelationUtil.calShortestDistancePoint(points,drivePoint);
        points.set(pair.first,drivePoint);
        List<LatLng> subList = points.subList(pair.first,points.size());
        //设置轨迹点
        smoothMarker.setPoints(subList);
        //设置平滑移动的总时间，单位：秒
        smoothMarker.setTotalDuration(40);

        aMap.setInfoWindowAdapter(infoWindowAdapter);
        marker.showInfoWindow();

        //设置移动的监听事件，返回距终点的距离，单位：米
        smoothMarker.setMoveListener(new MovingPointOverlay.MoveListener() {
            @Override
            public void move(final double distance) {
               try {
                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           if (infoWindowLayout != null && title != null){
                               title.setText("距离终点还有： " + (int) distance + "米");
                           }
                       }
                   });
               }catch (Exception e){
                   e.printStackTrace();
               }
            }
        });

        //开始移动
        smoothMarker.startSmoothMove();
    }

    AMap.InfoWindowAdapter infoWindowAdapter = new AMap.InfoWindowAdapter(){

        @Override
        public View getInfoWindow(Marker marker) {
            return getInfoWindowView(marker);
        }

        @Override
        public View getInfoContents(Marker marker) {
            return getInfoWindowView(marker);
        }
    };

    LinearLayout infoWindowLayout;
    TextView title;
    TextView snippet;
    /**
     * 自定义View并且绑定数据方法
     * @param marker 点击marker对象
     * @return  返回自定义窗口的视图
     * */
    private View getInfoWindowView(Marker marker){
        if (infoWindowLayout == null){
            infoWindowLayout = new LinearLayout(this);
            infoWindowLayout.setOrientation(LinearLayout.VERTICAL);
            title = new TextView(this);
            snippet = new TextView(this);
            title.setText("距离展示：");
            title.setTextColor(Color.BLACK);
            snippet.setTextColor(Color.BLACK);
            infoWindowLayout.setBackgroundResource(R.drawable.infowindow_bg);

            infoWindowLayout.addView(title);
            infoWindowLayout.addView(snippet);
        }
        return infoWindowLayout;
    }


    /***
     * 点击开始移动按钮
     *
     *  方法一：这个是根据文档写的
     * */
    private void startMove(){
        if (mPolyLine == null){
            Toast.makeText(this,"请先设置滑动路径",Toast.LENGTH_SHORT).show();
            return;
        }

        //获取轨迹坐标点
        List<LatLng> points = readLatlngs();
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(points.get(0));
        builder.include(points.get(points.size() - 1));
         aMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(),50));

        SmoothMoveMarker smoothMarker = new SmoothMoveMarker(aMap);
        //设置滑动的图标
        smoothMarker.setDescriptor(BitmapDescriptorFactory.fromResource(R.mipmap.icon_car));

        LatLng drivePoint = points.get(0);
        Pair<Integer,LatLng> pair = SpatialRelationUtil.calShortestDistancePoint(points,drivePoint);
        points.set(pair.first,drivePoint);
        List<LatLng> subList = points.subList(pair.first,points.size());

        //设置滑动的轨迹左边点
        smoothMarker.setPoints(subList);
        //设置滑动的总时间
        smoothMarker.setTotalDuration(40);
        //开始滑动
        smoothMarker.startSmoothMove();
    }


    private Polyline mPolyLine;
    /**
     * 添加轨迹线
     * */
    private void addPolylineInPlayGround(){
        List<LatLng> list = readLatlngs();
        List<Integer> colorList = new ArrayList<>();
        List<BitmapDescriptor> bitmapDescriptors = new ArrayList<>();

        int[] colors = new int[]{Color.argb(255, 0, 255, 0),Color.argb(255, 255, 255, 0),Color.argb(255, 255, 0, 0)};

        //用一个数组来存放纹理
        List<BitmapDescriptor> textureList = new ArrayList<>();
        textureList.add(BitmapDescriptorFactory.fromResource(R.drawable.custtexture));

        List<Integer> texIndexList = new ArrayList<>();
        texIndexList.add(0);//对应上面的第0个纹理
        texIndexList.add(1);
        texIndexList.add(2);

        Random random = new Random();
        for (int i = 0 ; i < list.size() ;i ++){
            colorList.add(colors[random.nextInt(3)]);
            bitmapDescriptors.add(textureList.get(0));
        }

        mPolyLine = aMap.addPolyline(new PolylineOptions()
                .setCustomTexture(BitmapDescriptorFactory.fromResource(R.drawable.custtexture))
//                .setCustomTextureList(bitmapDescriptors)
//                .setCustomTextureIndex(texIndexList)
                .addAll(list)
                .useGradient(true)
                .width(18)
        );

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(list.get(0));
        builder.include(list.get(list.size() - 2));

        aMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(),100));
    }

    private List<LatLng> readLatlngs(){
        List<LatLng> latLngs = new ArrayList<>();
        for (int i = 0; i < coords.length ; i += 2){
            latLngs.add(new LatLng(coords[i+1],coords[i]));
        }
        return latLngs;
    }

    /**
     * 坐标点数组数据
     */
    private double[] coords = { 116.3499049793749, 39.97617053371078,
            116.34978804908442, 39.97619854213431, 116.349674596623,
            39.97623045687959, 116.34955525200917, 39.97626931100656,
            116.34943728748914, 39.976285626595036, 116.34930864705592,
            39.97628129172198, 116.34918981582413, 39.976260803938594,
            116.34906721558868, 39.97623535890678, 116.34895185151584,
            39.976214717128855, 116.34886935936889, 39.976280148755315,
            116.34873954611332, 39.97628182112874, 116.34860763527448,
            39.97626038855863, 116.3484658907622, 39.976306080391836,
            116.34834585430347, 39.976358252119745, 116.34831166130878,
            39.97645709321835, 116.34827643560175, 39.97655231226543,
            116.34824186261169, 39.976658372925556, 116.34825080406188,
            39.9767570732376, 116.34825631960626, 39.976869087779995,
            116.34822111635201, 39.97698451764595, 116.34822901510276,
            39.977079745909876, 116.34822234337618, 39.97718701787645,
            116.34821627457707, 39.97730766147824, 116.34820593515043,
            39.977417746816776, 116.34821013897107, 39.97753930933358
            ,116.34821304891533, 39.977652209132174, 116.34820923399242,
            39.977764016531076, 116.3482045955917, 39.97786190186833,
            116.34822159449203, 39.977958856930286, 116.3482256370537,
            39.97807288885813, 116.3482098441266, 39.978170063673524,
            116.34819564465377, 39.978266951404066, 116.34820541974412,
            39.978380693859116, 116.34819672351216, 39.97848741209275,
            116.34816588867105, 39.978593409607825, 116.34818489339459,
            39.97870216883567, 116.34818473446943, 39.978797222300166,
            116.34817728972234, 39.978893492422685, 116.34816491505472,
            39.978997133775266, 116.34815408537773, 39.97911413849568,
            116.34812908154862, 39.97920553614499, 116.34809495907906,
            39.979308267469264, 116.34805113358091, 39.97939658036473,
            116.3480310509613, 39.979491697188685, 116.3480082124968,
            39.979588529006875, 116.34799530586834, 39.979685789111635,
            116.34798818413954, 39.979801430587926, 116.3479996420353,
            39.97990758587515, 116.34798697544538, 39.980000796262615,
            116.3479912988137, 39.980116318796085, 116.34799204219203,
            39.98021407403913, 116.34798535084123, 39.980325006125696,
            116.34797702460183, 39.98042511477518, 116.34796288754136,
            39.98054129336908, 116.34797509821901, 39.980656820423505,
            116.34793922017285, 39.98074576792626, 116.34792586413015,
            39.98085620772756, 116.3478962642899, 39.98098214824056,
            116.34782449883967, 39.98108306010269, 116.34774758827285,
            39.98115277119176, 116.34761476652932, 39.98115430642997,
            116.34749135408349, 39.98114590845294, 116.34734772765582,
            39.98114337322547, 116.34722082902628, 39.98115066909245,
            116.34708205250223, 39.98114532232906, 116.346963237696,
            39.98112245161927, 116.34681500222743, 39.981136637759604,
            116.34669622104072, 39.981146248090866, 116.34658043260109,
            39.98112495260716, 116.34643721418927, 39.9811107163792,
            116.34631638374302, 39.981085081075676, 116.34614782996252,
            39.98108046779486, 116.3460256053666, 39.981049089345206,
            116.34588814050122, 39.98104839362087, 116.34575119741586,
            39.9810544889668, 116.34562885420186, 39.981040940565734,
            116.34549232235582, 39.98105271658809, 116.34537348820508,
            39.981052294975264, 116.3453513775533, 39.980956549928244
    };

}
