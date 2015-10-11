package com.ruffneck.mobilesafer.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.ruffneck.mobilesafer.R;
import com.ruffneck.mobilesafer.view.SettingItemView;

/**
 * Created by 佛剑分说 on 2015/10/11.
 */
public class SettingActivity extends Activity {

    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        sp = getSharedPreferences("config",MODE_PRIVATE);

        final SettingItemView sivUpdate = (SettingItemView) findViewById(R.id.siv);

        sivUpdate.setTitle("自动更新设置");
        sivUpdate.setChecked(sp.getBoolean("autoUpdate",true));

        sivUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sivUpdate.setChecked(!sivUpdate.isChecked());
                sp.edit().putBoolean("autoUpdate",sivUpdate.isChecked()).apply();
            }
        });

    }
}
