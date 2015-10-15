package com.ruffneck.mobilesafer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ruffneck.mobilesafer.R;

/**
 * Created by 佛剑分说 on 2015/10/13.
 */
public class Setup3Activity extends BaseSetupActivity{

    private EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);
        et = (EditText) findViewById(R.id.et_phone);

        String sim_phone = mPref.getString("sim_phone", "");
        et.setText(sim_phone);
    }


    @Override
    public void showNextPage() {

        String sim_phone = et.getText().toString();

        if(TextUtils.isEmpty(sim_phone)){
            Toast.makeText(Setup3Activity.this, "电话号码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        mPref.edit().putString("sim_phone",sim_phone).apply();

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
        startActivityForResult(new Intent(this, ContactsActivity.class), 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == RESULT_OK){
            et.setText(data.getStringExtra("phone").trim().replaceAll("-","").replaceAll(" ",""));
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
