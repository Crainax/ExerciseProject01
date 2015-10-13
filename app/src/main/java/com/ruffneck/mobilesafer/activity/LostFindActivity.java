package com.ruffneck.mobilesafer.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

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

        }else{
            finish();
            startActivity(new Intent(this,Setup1Activity.class));
        }

    }
}
