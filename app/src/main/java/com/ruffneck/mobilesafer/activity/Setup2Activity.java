package com.ruffneck.mobilesafer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;

import com.ruffneck.mobilesafer.R;
import com.ruffneck.mobilesafer.view.SettingItemView;

/**
 * Created by 佛剑分说 on 2015/10/13.
 */
public class Setup2Activity extends BaseSetupActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup2);

        final SettingItemView siv_sim = (SettingItemView) findViewById(R.id.siv_sim);

        String sivSim = mPref.getString("sim",null);

        if(!TextUtils.isEmpty(sivSim)){
            siv_sim.setChecked(true);
        }else{
            siv_sim.setChecked(false);
        }
        siv_sim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(siv_sim.isChecked()){
                    siv_sim.setChecked(false);
                    mPref.edit().remove("sim").apply();
                }else{
                    siv_sim.setChecked(true);
                    TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                    String simSerialNumber = telephonyManager.getSimSerialNumber();
                    mPref.edit().putString("sim",simSerialNumber).apply();
                    System.out.println(simSerialNumber);
                }

            }
        });
    }


    @Override
    public void showNextPage() {
        startActivity(new Intent(this, Setup3Activity.class));
        finish();
        overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
    }


    @Override
    public void showPreviousPage() {
        startActivity(new Intent(this, Setup1Activity.class));
        finish();
        overridePendingTransition(R.anim.tran_previous_in, R.anim.tran_previous_out);
    }

}
