package com.ruffneck.mobilesafer.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.ruffneck.mobilesafer.R;

/**
 * Created by 佛剑分说 on 2015/10/13.
 */
public class Setup4Activity extends BaseSetupActivity{

    SharedPreferences mPref;
    private CheckBox box;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup4);
        mPref = getSharedPreferences("config",MODE_PRIVATE);



        box = (CheckBox) findViewById(R.id.cb_protect);
        if(mPref.getBoolean("protect",false)) {
            box.setChecked(true);
            box.setText("防盗保护已经开启");
        }else{
            box.setChecked(false);
            box.setText("防盗保护没有开启");
        }
        box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    mPref.edit().putBoolean("protect",true).apply();
                    box.setText("防盗保护已经开启");
                }else{
                    mPref.edit().remove("protect").apply();
                    box.setText("防盗保护没有开启");
                }

            }
        });


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
