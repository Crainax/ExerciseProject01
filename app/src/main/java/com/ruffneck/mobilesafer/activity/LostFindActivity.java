package com.ruffneck.mobilesafer.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruffneck.mobilesafer.R;

/**
 * Created by 佛剑分说 on 2015/10/13.
 */
public class LostFindActivity extends Activity{

    private SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lostfind);

        mPref = getSharedPreferences("config",MODE_PRIVATE);

        if(mPref.getBoolean("configed",false)){

            TextView tv_phone = (TextView) findViewById(R.id.tv_phone);
            ImageView iv_lock = (ImageView) findViewById(R.id.iv_lock);

            String phone = mPref.getString("sim_phone","");
            boolean protect = mPref.getBoolean("protect",false);

            tv_phone.setText(phone);
            if(protect)iv_lock.setImageResource(R.drawable.lock);
            else iv_lock.setImageResource(R.drawable.unlock);

        }else{
            finish();
            startActivity(new Intent(this,Setup1Activity.class));
        }

    }

    public void reenter(View view) {
        startActivity(new Intent(this,Setup1Activity.class));
        finish();
    }
}
