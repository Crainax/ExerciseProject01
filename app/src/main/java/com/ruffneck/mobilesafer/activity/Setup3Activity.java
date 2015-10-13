package com.ruffneck.mobilesafer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ruffneck.mobilesafer.R;

/**
 * Created by 佛剑分说 on 2015/10/13.
 */
public class Setup3Activity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);
    }

    public void next(View v){
        startActivity(new Intent(this, Setup4Activity.class));
        finish();
    }

    public void previous(View v){
        startActivity(new Intent(this,Setup2Activity.class));
        finish();
    }
}
