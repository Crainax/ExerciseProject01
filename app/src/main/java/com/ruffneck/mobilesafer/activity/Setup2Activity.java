package com.ruffneck.mobilesafer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ruffneck.mobilesafer.R;

/**
 * Created by 佛剑分说 on 2015/10/13.
 */
public class Setup2Activity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup2);
    }

    public void next(View v){
        startActivity(new Intent(this, Setup3Activity.class));
        finish();
        overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
    }

    public void previous(View v){
        startActivity(new Intent(this,Setup1Activity.class));
        finish();
    }
}
