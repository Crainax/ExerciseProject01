package com.ruffneck.mobilesafer.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.ruffneck.mobilesafer.R;

/**
 * Created by 佛剑分说 on 2015/10/13.
 */
public class Setup4Activity extends Activity{

    SharedPreferences mPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup4);

        mPref = getSharedPreferences("config",MODE_PRIVATE);
    }

    public void next(View v){

        if(!mPref.getBoolean("configed",false))mPref.edit().putBoolean("configed",true).apply();

        startActivity(new Intent(this, LostFindActivity.class));
        finish();
    }

    public void previous(View v){
        startActivity(new Intent(this,Setup3Activity.class));
        finish();
    }
}
