package com.ruffneck.mobilesafer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ruffneck.mobilesafer.R;

/**
 * Created by 佛剑分说 on 2015/10/13.
 */
public class Setup3Activity extends BaseSetupActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);
    }


    @Override
    public void showNextPage() {
        startActivity(new Intent(this, Setup4Activity.class));
        finish();
        overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
    }


    @Override
    public void showPreviousPage() {
        startActivity(new Intent(this,Setup2Activity.class));
        finish();
        overridePendingTransition(R.anim.tran_previous_in, R.anim.tran_previous_out);
    }

    public void chooseContact(View v){
        startActivity(new Intent(this,ContactsActivity.class));
    }
}
