package com.ruffneck.mobilesafer.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/**
 * Created by 佛剑分说 on 2015/10/14.
 */
public class BootCompleteReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences mPref = context.getSharedPreferences("config", Context.MODE_PRIVATE);

        if (mPref.getBoolean("protect", false)) {
            String sim = mPref.getString("sim", null);
            if (!TextUtils.isEmpty(sim)) {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

                String currentSim = telephonyManager.getSimSerialNumber();

                if (currentSim.equals(sim)) {
                    System.out.println("手机安全");
                } else {
                    System.out.println("手机被盗了!");
                }

            }
        }
    }

}
