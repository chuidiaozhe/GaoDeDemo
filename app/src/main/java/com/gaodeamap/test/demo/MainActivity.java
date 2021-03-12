package com.gaodeamap.test.demo;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gaodeamap.test.demo.draw.DrawGraphActivity;
import com.gaodeamap.test.demo.draw.DrawHeatmapTileActivity;
import com.gaodeamap.test.demo.draw.DrawLineActivity;
import com.gaodeamap.test.demo.draw.DrawMarkPointActivity;
import com.gaodeamap.test.demo.draw.MultiPointOverlayActivity;
import com.gaodeamap.test.demo.draw.SmoothMoveActivity;
import com.gaodeamap.test.demo.fence.GeoFenceRoundNewActivity;
import com.gaodeamap.test.demo.fence.RoundFenceActivity;
import com.gaodeamap.test.demo.interaction.GestureActivity;
import com.gaodeamap.test.demo.interaction.MethodActivity;
import com.gaodeamap.test.demo.interaction.ScreenMapActivity;
import com.gaodeamap.test.demo.interaction.WeightActivity;
import com.gaodeamap.test.demo.poi.DistrictWithBoundaryActivity;
import com.gaodeamap.test.demo.poi.GeocodeActivity;
import com.gaodeamap.test.demo.poi.PoiKeyWordsActivity;
import com.gaodeamap.test.demo.show.LocationStyleActivity;
import com.gaodeamap.test.demo.show.ShowActivity;
import com.open.hule.library.entity.AppUpdate;
import com.open.hule.library.utils.UpdateManager;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initClickListener();
    }

    /**
     * 判断是否需要检测，防止不停的弹框
     */
    private boolean isNeedCheck = true;

    @Override
    protected void onResume() {
        super.onResume();
        try {
            super.onResume();
            if (Build.VERSION.SDK_INT >= 23) {
                if (isNeedCheck) {
                    checkPermissions(needPermissions);
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void initClickListener() {
        findViewById(R.id.btn_show).setOnClickListener(this);
        findViewById(R.id.btn_location_style).setOnClickListener(this);

        findViewById(R.id.btn_weight).setOnClickListener(this);
        findViewById(R.id.btn_gestures).setOnClickListener(this);
        findViewById(R.id.btn_screen).setOnClickListener(this);
        findViewById(R.id.btn_method).setOnClickListener(this);

        findViewById(R.id.btn_draw_markpoint).setOnClickListener(this);
        findViewById(R.id.btn_draw_line).setOnClickListener(this);
        findViewById(R.id.btn_draw_graph).setOnClickListener(this);
        findViewById(R.id.btn_draw_heat).setOnClickListener(this);
        findViewById(R.id.btn_smooth_move).setOnClickListener(this);
        findViewById(R.id.btn_multipoint).setOnClickListener(this);

        findViewById(R.id.btn_poi_keyword_search).setOnClickListener(this);
        findViewById(R.id.btn_gencode).setOnClickListener(this);
        findViewById(R.id.btn_district_with_boundary).setOnClickListener(this);

        findViewById(R.id.btn_fence_round).setOnClickListener(this);
        findViewById(R.id.btn_fence_round_new).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_show:
                //显示地图
                clickShow();
                break;
            case R.id.btn_location_style:
                clickLocationStyle();
                break;
            case R.id.btn_weight:
                //控件交互
                clickWeight();
                break;
            case R.id.btn_gestures:
                //手势交互
                clickGesutres();
                break;
            case R.id.btn_screen:
                clickScreen();
                break;
            case R.id.btn_method:
                clickMethod();
                break;
            case R.id.btn_draw_markpoint:
                clickDrawMarkPoint();
                break;
            case R.id.btn_draw_line:
                clickDrawLine();
                break;
            case R.id.btn_draw_graph:
                clickDrawGraph();
                break;
            case R.id.btn_draw_heat:
                clickDrawHeat();
                break;
            case R.id.btn_smooth_move:
                clickSmoothMove();
                break;
            case R.id.btn_multipoint:
                clickDrawMultipoint();
                break;
            case R.id.btn_poi_keyword_search:
                clickPoiKeyWordSearch();
                break;
            case R.id.btn_gencode:
                clickGencode();
                break;
            case R.id.btn_district_with_boundary:
                clickDistrictWithBoundary();
                break;
            case R.id.btn_fence_round:
                clickFenceRound();
                break;
            case R.id.btn_fence_round_new:
                clickFenceRoundNew();
                break;

        }
    }

    private void clickFenceRoundNew() {
        startActivity(GeoFenceRoundNewActivity.class);
    }

    private void clickFenceRound() {
        startActivity(RoundFenceActivity.class);
    }

    private void clickDistrictWithBoundary() {
        startActivity(DistrictWithBoundaryActivity.class);
    }

    private void clickGencode() {
        startActivity(GeocodeActivity.class);
    }

    private void clickPoiKeyWordSearch() {
        startActivity(PoiKeyWordsActivity.class);
    }

    private void clickDrawMultipoint() {
        startActivity(MultiPointOverlayActivity.class);
    }

    private void clickSmoothMove() {
        startActivity(SmoothMoveActivity.class);
    }

    private void clickDrawHeat() {
        startActivity(DrawHeatmapTileActivity.class);
    }

    private void clickDrawGraph() {
        startActivity(DrawGraphActivity.class);
    }

    private void clickDrawLine() {
        startActivity(DrawLineActivity.class);
    }

    private void clickDrawMarkPoint() {
        startActivity(DrawMarkPointActivity.class);
    }

    private void clickMethod() {
        startActivity(MethodActivity.class);
    }

    private void clickScreen() {
        startActivity(ScreenMapActivity.class);
    }

    private void clickGesutres() {
        startActivity(GestureActivity.class);
    }

    private void clickShow() {
        startActivity(ShowActivity.class);
    }

    private void clickLocationStyle() {
        startActivity(LocationStyleActivity.class);
    }

    private void clickWeight() {
        startActivity(WeightActivity.class);
    }

    private void startActivity(Class<?> cl) {
        Intent intent = new Intent(this, cl);
        startActivity(intent);
    }

    /**
     * @param
     * @since 2.5.0
     */
    @TargetApi(23)
    private void checkPermissions(String... permissions) {
        try {
            if (Build.VERSION.SDK_INT >= 23 && getApplicationInfo().targetSdkVersion >= 23) {
                List<String> needRequestPermissonList = findDeniedPermissions(permissions);
                if (null != needRequestPermissonList
                        && needRequestPermissonList.size() > 0) {
                    try {
                        String[] array = needRequestPermissonList.toArray(new String[needRequestPermissonList.size()]);
                        Method method = getClass().getMethod("requestPermissions", new Class[]{String[].class, int.class});
                        method.invoke(this, array, 0);
                    } catch (Throwable e) {

                    }
                }
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取权限集中需要申请权限的列表
     *
     * @param permissions
     * @return
     * @since 2.5.0
     */
    @TargetApi(23)
    private List<String> findDeniedPermissions(String[] permissions) {
        try {
            List<String> needRequestPermissonList = new ArrayList<String>();
            if (Build.VERSION.SDK_INT >= 23 && getApplicationInfo().targetSdkVersion >= 23) {
                for (String perm : permissions) {
                    if (checkMySelfPermission(perm) != PackageManager.PERMISSION_GRANTED
                            || shouldShowMyRequestPermissionRationale(perm)) {
                        if (!needCheckBackLocation
                                && BACK_LOCATION_PERMISSION.equals(perm)) {
                            continue;
                        }
                        needRequestPermissonList.add(perm);
                    }
                }
            }
            return needRequestPermissonList;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    private int checkMySelfPermission(String perm) {
        try {
            Method method = getClass().getMethod("checkSelfPermission", new Class[]{String.class});
            Integer permissionInt = (Integer) method.invoke(this, perm);
            return permissionInt;
        } catch (Throwable e) {
        }
        return -1;
    }

    private boolean shouldShowMyRequestPermissionRationale(String perm) {
        try {
            Method method = getClass().getMethod("shouldShowRequestPermissionRationale", new Class[]{String.class});
            Boolean permissionInt = (Boolean) method.invoke(this, perm);
            return permissionInt;
        } catch (Throwable e) {
        }
        return false;
    }

    //是否需要检测后台定位权限，设置为true时，如果用户没有给予后台定位权限会弹窗提示
    private boolean needCheckBackLocation = false;
    //如果设置了target > 28，需要增加这个权限，否则不会弹出"始终允许"这个选择框
    private static String BACK_LOCATION_PERMISSION = "android.permission.ACCESS_BACKGROUND_LOCATION";
    /**
     * 需要进行检测的权限数组
     */
    protected String[] needPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            BACK_LOCATION_PERMISSION
    };

    private static final int PERMISSON_REQUESTCODE = 0;
}

