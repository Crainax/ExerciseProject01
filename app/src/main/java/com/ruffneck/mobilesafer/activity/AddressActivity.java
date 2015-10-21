package com.ruffneck.mobilesafer.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ruffneck.mobilesafer.R;
import com.ruffneck.mobilesafer.db.dao.AddressDAO;

/**
 * Created by 佛剑分说 on 2015/10/19.
 */
public class AddressActivity extends Activity {

    private EditText et_number;
    private TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        et_number = (EditText) findViewById(R.id.et_number);
        tv_result = (TextView) findViewById(R.id.tv_result);

        et_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println(s);
                String address = AddressDAO.getAddress(s.toString());
                tv_result.setText(address);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    public void query(View v) {

        String number = et_number.getText().toString().trim();

        if (!TextUtils.isEmpty(number)) {
            String address = AddressDAO.getAddress(number);
            tv_result.setText(address);
        }else{
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.shake);

            BounceInterpolator al;
            et_number.startAnimation(animation);
            Toast.makeText(AddressActivity.this, "输入框不能为空", Toast.LENGTH_SHORT).show();
        }
    }
}
