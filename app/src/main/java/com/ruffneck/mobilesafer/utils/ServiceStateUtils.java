package com.ruffneck.mobilesafer.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * Created by 佛剑分说 on 2015/10/30.
 */
public class ServiceStateUtils {

    public static boolean isServiceRunning(Context context,String serviceName){
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        List<ActivityManager.RunningServiceInfo> serviceList = am.getRunningServices(100);

        for (ActivityManager.RunningServiceInfo info : serviceList) {
            if(info.service.getClassName().equals(serviceName)){
                return true;
            }
        }

        return false;
    }
}
