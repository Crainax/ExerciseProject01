package com.ruffneck.mobilesafer.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.ruffneck.mobilesafer.R;
import com.ruffneck.mobilesafer.service.AddressService;
import com.ruffneck.mobilesafer.utils.ServiceStateUtils;
import com.ruffneck.mobilesafer.view.SettingItemView;

/**
 * Created by 佛剑分说 on 2015/10/11.
 */
public class SettingActivity extends Activity {

    SharedPreferences sp;
    private SettingItemView sivUpdate;
    private SettingItemView sivAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        sp = getSharedPreferences("config",MODE_PRIVATE);

        initAddress();
        initUpdate();

    }

    private void initAddress() {
        sivAddress = (SettingItemView) findViewById(R.id.siv_address);

        if(ServiceStateUtils.isServiceRunning(this,"com.ruffneck.mobilesafer.service.AddressService"))
            sivAddress.setChecked(true);
        else sivAddress.setChecked(false);

        sivAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sivAddress.isChecked()){
                    sivAddress.setChecked(false);
                    stopService(new Intent(SettingActivity.this, AddressService.class));
                } else {
                    sivAddress.setChecked(true);
                    startService(new Intent(SettingActivity.this, AddressService.class));
                }
            }
        });

    }

    private void initUpdate() {
        sivUpdate = (SettingItemView) findViewById(R.id.siv_autoupdate);

//        sivUpdate.setTitle("自动更新设置");
        sivUpdate.setChecked(sp.getBoolean("autoUpdate", true));

        sivUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sivUpdate.setChecked(!sivUpdate.isChecked());
                sp.edit().putBoolean("autoUpdate", sivUpdate.isChecked()).apply();
            }
        });
    }
}
