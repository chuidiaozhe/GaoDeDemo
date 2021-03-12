package com.gaodeamap.test.demo.fence;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.amap.api.fence.GeoFence;


/**
 * Create by Xiangshifu
 * on 2020/9/8 2:36 PM
 *
 *   广播用于接收栅栏的变化情况
 */
public class FenceBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(RoundFenceActivity.GEOFENCE_BROADCAST_ACTIOIN)){
            //解析广播内容

            //获取Bundle
            Bundle bundle = intent.getExtras();
            //获取围栏行为：
            int status = bundle.getInt(GeoFence.BUNDLE_KEY_FENCESTATUS);
            //获取自定义的围栏标识
            String customId = bundle.getString(GeoFence.BUNDLE_KEY_CUSTOMID);
            //获取围栏ID：
            String fenceId = bundle.getString(GeoFence.BUNDLE_KEY_FENCEID);
            //获取当前有触发的围栏对象：
            GeoFence fence = bundle.getParcelable(GeoFence.BUNDLE_KEY_FENCEID);
            Log.e("testtest","  触发栅栏的变化情况 ：  status : " + status + "   customId: " +customId + "    fenceId:" + fenceId);
        }
     }

}
