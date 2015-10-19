package com.ruffneck.mobilesafer.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
    }


    public void query(View v) {

        String number = et_number.getText().toString().trim();

        if (!TextUtils.isEmpty(number)) {
            String address = AddressDAO.getAddress(number);
            tv_result.setText(address);
        }
    }
}
