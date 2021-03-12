package com.gaodeamap.test.demo.fence;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.amap.api.fence.GeoFence;
import com.amap.api.fence.GeoFenceClient;
import com.amap.api.fence.GeoFenceListener;
import com.amap.api.location.DPoint;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.gaodeamap.test.demo.R;

import java.util.List;

/**
 * Create by Xiangshifu
 * on 2020/9/7 10:09 AM
 *  圆形栅栏
 */
public class RoundFenceActivity extends AppCompatActivity   {
    //定义接收广播的action字符串
    public static final String GEOFENCE_BROADCAST_ACTIOIN = "com.location.apis.geofencedemo.broadcast";

    private MapView mMapView;
    private AMap mAMap;

    private CheckBox cbIn;
    private CheckBox cbOut;
    private CheckBox cbStayed;

    private EditText edtRadius;
    private EditText edtId;

    private FenceBroadcastReceiver fenceBroadcastReceiver;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fence_round);

        mMapView = findViewById(R.id.mapview);
        mMapView.onCreate(savedInstanceState);

        mAMap = mMapView.getMap();

        cbIn = findViewById(R.id.cb_in);
        cbOut = findViewById(R.id.cb_out);
        cbStayed = findViewById(R.id.cb_stayed);

        edtRadius = findViewById(R.id.edt_radius);
        edtId = findViewById(R.id.edt_id);


        findViewById(R.id.btn_add_fence).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               addFence();
            }
        });

        mAMap.addOnMarkerDragListener(new AMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
              //拖动结束
                 curLatLng =  marker.getPosition();
            }
        });

        addMarker();

        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        intentFilter.addAction(GEOFENCE_BROADCAST_ACTIOIN);
        fenceBroadcastReceiver = new FenceBroadcastReceiver();
        registerReceiver(fenceBroadcastReceiver,intentFilter);
     }

    /**
     * 添加Marker标记点
     * */
    private Marker mMarker;
    private LatLng curLatLng;
    private void addMarker(){
        if (mMarker == null){
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.poi_marker_pressed));
            markerOptions.draggable(true);
            markerOptions.anchor(0.5f,0.5f);
            curLatLng = new LatLng(39.906901,116.397972);
            markerOptions.position(curLatLng);
          mMarker = mAMap.addMarker(markerOptions);

        }

    }

    /**
     * 添加栅栏
     * */
    private GeoFenceClient mGeoFenceClient;
    private void addFence(){
        if (mGeoFenceClient == null){
            mGeoFenceClient = new GeoFenceClient(this);
            //创建回调监听
            mGeoFenceClient.setGeoFenceListener(new GeoFenceListener() {
                @Override
                public void onGeoFenceCreateFinished(List<GeoFence> list, int errorCode, String s) {
                    //栅栏监听

                    if (errorCode == GeoFence.ADDGEOFENCE_SUCCESS){
                        //围栏创建成功
                        Toast.makeText(RoundFenceActivity.this, "围栏创建成功", Toast.LENGTH_SHORT).show();
                        //创建设置PendingIntent
                        mGeoFenceClient.createPendingIntent(GEOFENCE_BROADCAST_ACTIOIN);
                    }else{
                        //围栏创建失败
                        Toast.makeText(RoundFenceActivity.this, "围栏创建失败："+ s, Toast.LENGTH_SHORT).show();
                    }
                }
            });
         }else{
            //清除所有围栏
            mGeoFenceClient.removeGeoFence();
        }



        if ( cbIn.isChecked() ||  cbOut.isChecked() ||  cbStayed.isChecked() ){

        }else{
            //没有选择出发行为
            Toast.makeText(this,"请选择触发范围",Toast.LENGTH_SHORT).show();
            return;
        }

        String radius = edtRadius.getText().toString();
        String ids = edtId.getText().toString();
        if (TextUtils.isEmpty(radius)){
            Toast.makeText(this, "请输入距离", Toast.LENGTH_SHORT).show();
           return;
        }

        if (cbIn.isChecked()){
            mGeoFenceClient.setActivateAction(GeoFenceClient.GEOFENCE_IN);
        }
        if (cbOut.isChecked()){
            mGeoFenceClient.setActivateAction(GeoFenceClient.GEOFENCE_OUT);
        }
        if (cbStayed.isChecked()){
            mGeoFenceClient.setActivateAction(GeoFenceClient.GEOFENCE_STAYED);
        }

        DPoint dPoint = new DPoint();
        dPoint.setLatitude(curLatLng.latitude);
        dPoint.setLongitude(curLatLng.longitude);
        mGeoFenceClient.addGeoFence(dPoint,Float.valueOf(radius),ids);


    }

    @Override
    protected void onResume() {
        super.onResume();
       mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       mMapView.onDestroy();
       if (fenceBroadcastReceiver != null){
           unregisterReceiver(fenceBroadcastReceiver);
       }
    }

}
