package com.gaodeamap.test.demo.poi;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.gaodeamap.test.demo.R;

import java.util.List;

/**
 * Create by Xiangshifu
 * on 2020/9/2 2:52 PM
 *
 *  获取地址描述数据
 *  1.地理编码（地址转坐标）
 *  2.逆地理编码（坐标转地址）
 *
 */
public class GeocodeActivity extends AppCompatActivity  implements View .OnClickListener , GeocodeSearch.OnGeocodeSearchListener{

    private MapView mMapView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geocode);

        mMapView = findViewById(R.id.mapview);
        mMapView.onCreate(savedInstanceState);

        findViewById(R.id.btn_gencode).setOnClickListener(this);
        findViewById(R.id.btn_regeocode).setOnClickListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_gencode){
            //地址转坐标
            clickGencode();
        }else if(v.getId() == R.id.btn_regeocode){
            //坐标转地址
            clickRegeocode();
        }
    }

    private void clickGencode(){
        //第一个参数表示地址，第二个参数表示查询的城市
        GeocodeQuery geocodeQuery = new GeocodeQuery("月坛南街69号楼","北京");
        //构造GeocodeSearch对象，并设置监听
        GeocodeSearch geocodeSearch = new GeocodeSearch(this);
        geocodeSearch.setOnGeocodeSearchListener(this);
        //通过调用GeocodeSearch的getFromLocationNameAsyn()方法发起请求
        geocodeSearch.getFromLocationNameAsyn(geocodeQuery);

    }

    private void clickRegeocode(){
        //构造GeocodeSearch对象，并设置监听
        GeocodeSearch geocodeSearch = new GeocodeSearch(this);
        geocodeSearch.setOnGeocodeSearchListener(this);
        LatLonPoint latLng =  new LatLonPoint(39.91542312213313,116.3219125040746);
        //第一个参数表示一个Latlng,第二个参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        RegeocodeQuery regeocodeQuery = new RegeocodeQuery(latLng,200,GeocodeSearch.AMAP);
        //发起请求
        geocodeSearch.getFromLocationAsyn(regeocodeQuery);
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int code) {
        //逆编码成功
        if (code == 1000){
              RegeocodeAddress regeocodeAddress =  regeocodeResult.getRegeocodeAddress();
              if (regeocodeAddress == null){
                  Toast.makeText(this, "逆编码成功  regeocodeAddress is null  ", Toast.LENGTH_SHORT).show();
              }else{
                  Toast.makeText(this, "逆编码成功 ： " + regeocodeAddress.getFormatAddress(), Toast.LENGTH_SHORT).show();
              }
        }else{
            Toast.makeText(this, "逆编码失败：" + code, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int code) {
          //地理编码请求成功
        if (code == 1000){
          List<GeocodeAddress> addressList =  geocodeResult.getGeocodeAddressList();
          if (addressList != null && addressList.size() > 0){
              GeocodeAddress geocodeAddress = addressList.get(0);
              Toast.makeText(this, "编码成功 lat:" + geocodeAddress.getLatLonPoint().getLatitude() + "  lng: " + geocodeAddress.getLatLonPoint().getLongitude(), Toast.LENGTH_SHORT).show();
          }else{
              Toast.makeText(this, "编码成功，列表为空", Toast.LENGTH_SHORT).show();
          }
        }else{
            Toast.makeText(this, "编码失败：" + code, Toast.LENGTH_SHORT).show();
        }
    }
}
