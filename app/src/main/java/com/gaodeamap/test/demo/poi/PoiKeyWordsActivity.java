package com.gaodeamap.test.demo.poi;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.amap.api.maps.MapView;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
 import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.gaodeamap.test.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by Xiangshifu
 * on 2020/9/2 10:16 AM
 *
 *  获取POI数据
 */
public  class PoiKeyWordsActivity extends AppCompatActivity implements PoiSearch.OnPoiSearchListener , Inputtips.InputtipsListener, View .OnClickListener{

    private MapView mapView;
    private EditText edtKeyWords;

    private PoiSearch mPoiSearch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poi_keywords);
        mapView = findViewById(R.id.mapview);

        mapView.onCreate(savedInstanceState);

        edtKeyWords = findViewById(R.id.edt_search);
        
        //点击搜索按钮
        findViewById(R.id.btn_search).setOnClickListener(this);

        edtKeyWords.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //构造InputtipsQuery对象，第一个参数传入关键词，第二个参数传入null或者""代表全国进行搜索，否则按照传入的city进行检索
                InputtipsQuery inputtipsQuery = new InputtipsQuery(s.toString(),"北京");
                inputtipsQuery.setCityLimit(false); //限制在当前城市

                //构造Inputtips对象，并设置监听
                Inputtips inputtips = new Inputtips(PoiKeyWordsActivity.this,inputtipsQuery);
                inputtips.setInputtipsListener(PoiKeyWordsActivity.this);

                //调用PoiSearch的requestInputtipsAsyn()方法发送请求
                inputtips.requestInputtipsAsyn();
            }
        });
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
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int errorcode) {
       //返回POI搜索异步处理的结果
        if (errorcode == 1000){
            //搜索成功
            Log.e("testtest"," =======onPoiSearched  搜索成功====== " + errorcode);
            List<PoiItem> searchPosis =  poiResult.getPois(); //获取POIItem列表
            Log.e("testtest","   搜索结果： " + searchPosis.toString() +"\n");
            for (PoiItem poiItem : searchPosis){
                Log.e("testtest" ," poiItem Info : " +  getString(poiItem));
             }


            List<SuggestionCity> suggestionCityList =  poiResult.getSearchSuggestionCitys();
            Log.e("testtest","  获取建议城市   ："  + suggestionCityList.toString());

           List<String> keyWordList =  poiResult.getSearchSuggestionKeywords();
            Log.e("testtest"," 获取建议关键词： " + keyWordList.toString());
         }else{
            //搜索失败
            Log.e("testtest"," =======onPoiSearched  搜索失败====== " + errorcode);
         }
    }

    private String getString(PoiItem item){
        StringBuilder stringBuilder = new StringBuilder();
        if (item != null){
            stringBuilder.append("\n");
            stringBuilder.append("getAdCode  行政区划代码：" + item.getAdCode());
            stringBuilder.append("\n");
            stringBuilder.append("getAdName  行政区划名称：" + item.getAdName());
            stringBuilder.append("\n");
            stringBuilder.append("getBusinessArea  所在商区：" + item.getBusinessArea());
            stringBuilder.append("\n");
            stringBuilder.append("getProvinceName  行政区名称：" + item.getProvinceName());
            stringBuilder.append("\n");
         }

        return stringBuilder.toString();
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int errorcode) {
      //poi id 搜索的结果回调
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_search){
           clickSearch();
        }
    }

    private void clickSearch(){
        String keyWords = edtKeyWords.getText().toString();
        if (TextUtils.isEmpty(keyWords)){
            Toast.makeText(this, "请输入搜索内容", Toast.LENGTH_SHORT).show();
        }else{
                 //第一步：构造 PoiSearch.Query 对象
                 PoiSearch.Query query = new PoiSearch.Query(keyWords,"","北京");
                query.setPageSize(10);
                query.setPageNum(1);
                //第二步：构造PoiSearch对象，并设置监听
                mPoiSearch = new PoiSearch(this,query);
                mPoiSearch.setOnPoiSearchListener(this);

                //设置周边搜索，设置圆形查询范围，半径为1000米
//                mPoiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(39.913985100439554,116.33988330416695),1000));

                //设置多边形内检索
                List<LatLonPoint> points = new ArrayList<>();
                points.add(new LatLonPoint( 39.927713992452745,116.32953802417349));
                points.add(new LatLonPoint(39.92719977432083,116.36113042251493));
                points.add(new LatLonPoint(39.89522942252891,116.35570163156167));
                points.add(new LatLonPoint(39.901285379528595,116.32918263148507));
                points.add(new LatLonPoint(39.91542312213313,116.3219125040746));

                mPoiSearch.setBound(new PoiSearch.SearchBound(points));

                //第三步： 调用PoiSearch的searchPOIAsyn()方法发送请求
                mPoiSearch.searchPOIAsyn();

        }
    }

    @Override
    public void onGetInputtips(List<Tip> list, int i) {
        //关键字自动提醒
        for (Tip tip : list ){
            Log.e("testtest"," \n" +
                    "区域 ： " + tip.getDistrict() + "\n"+
                     "名称：" +  tip.getName() + "\n"+
                      "地址：" + tip.getAddress());
        }

    }
}
