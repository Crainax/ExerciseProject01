package com.ruffneck.mobilesafer.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.ruffneck.mobilesafer.R;

/**
 * Created by 佛剑分说 on 2015/10/13.
 */
public class Setup4Activity extends BaseSetupActivity{

    SharedPreferences mPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup4);

        mPref = getSharedPreferences("config",MODE_PRIVATE);
    }

    @Override
    public void showNextPage() {
        if(!mPref.getBoolean("configed",false))mPref.edit().putBoolean("configed",true).apply();

        startActivity(new Intent(this, LostFindActivity.class));
        finish();
        overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
    }

    @Override
    public void showPreviousPage() {
        startActivity(new Intent(this,Setup3Activity.class));
        finish();
        overridePendingTransition(R.anim.tran_previous_in, R.anim.tran_previous_out);
    }
}
