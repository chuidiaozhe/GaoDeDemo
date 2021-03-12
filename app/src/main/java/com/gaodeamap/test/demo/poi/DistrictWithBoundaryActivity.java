package com.gaodeamap.test.demo.poi;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
 import com.amap.api.services.district.DistrictItem;
import com.amap.api.services.district.DistrictResult;
import com.amap.api.services.district.DistrictSearch;
import com.amap.api.services.district.DistrictSearchQuery;
import com.gaodeamap.test.demo.R;
import java.util.ArrayList;
import java.util.List;


/**
 * Create by Xiangshifu
 * on 2020/9/4 10:09 AM
 *
 *   行政区划边界查询
 */
public  class DistrictWithBoundaryActivity  extends AppCompatActivity implements  DistrictSearch.OnDistrictSearchListener{

    private MapView mMapView;
    private AMap mAMap;
    private EditText edtSearch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district_with_boundary);
        mMapView = findViewById(R.id.mapview);
        mMapView.onCreate(savedInstanceState);

        mAMap = mMapView.getMap();

        edtSearch = findViewById(R.id.edt_search);

        findViewById(R.id.btn_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSearch();
            }
        });
    }

    private void clickSearch(){
        String searchContent = edtSearch.getText().toString().trim();
        if (TextUtils.isEmpty(searchContent)){
            Toast.makeText(this, "请输入搜索内容", Toast.LENGTH_SHORT).show();
        }else{
            DistrictSearch districtSearch = new DistrictSearch(this);
            DistrictSearchQuery districtSearchQuery = new DistrictSearchQuery();
            districtSearchQuery.setKeywords(searchContent);//传入关键字
            districtSearchQuery.setShowBoundary(true); // 是否返回边界值
            districtSearch.setQuery(districtSearchQuery);
            districtSearch.setOnDistrictSearchListener(this);//绑定监听器
            districtSearch.searchDistrictAsyn(); // 开始搜索

        }
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
    public void onDistrictSearched(DistrictResult districtResult) {
        //在回调函数中解析districtResut获取行政区划信息
        if(districtResult.getAMapException().getErrorCode() == 1000){
          List<DistrictItem> districtItemList =  districtResult.getDistrict();
          for (DistrictItem districtItem : districtItemList){
           String[]  boundary =  districtItem.districtBoundary();
           drawLine(boundary);
           }
        }
    }

    private void drawLine(String[] boundary){
        mAMap.clear();
        for (String s : boundary){
            try {
                List<LatLng> latLngs = new ArrayList<>();

                Log.e("testtest","  s   : "  + s);
                String[] arr = s.split(";");
                for (String s1 : arr){
                    String[] arr1 = s1.split(",");
                    latLngs.add(new LatLng(Double.valueOf(arr1[1]),Double.valueOf(arr1[0])));
                }

                Log.e("testtest"," latLngs.size()========== " + latLngs.size());
                PolylineOptions polylineOptions = new PolylineOptions();
                polylineOptions.addAll(latLngs);
                polylineOptions.color(Color.GREEN);
                polylineOptions.width(15);
                polylineOptions.setDottedLine(true);

                Polyline polyline =  mAMap.addPolyline(polylineOptions);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

      }
}
