package com.ruffneck.mobilesafer.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ruffneck.mobilesafer.R;
import com.ruffneck.mobilesafer.utils.MD5Utils;

/**
 * Created by 佛剑分说 on 2015/10/9.
 */
public class HomeActivity extends Activity {


    private String[] mItems = new String[]{"手机防盗", "通讯卫士", "软件管理", "进程管理",
            "流量统计", "手机杀毒", "缓存清理", "高级工具", "设置中心"};
    private int[] mResources = new int[]{R.drawable.home_safe,
            R.drawable.home_callmsgsafe, R.drawable.home_apps,
            R.drawable.home_taskmanager, R.drawable.home_netmanager,
            R.drawable.home_trojan, R.drawable.home_sysoptimize,
            R.drawable.home_tools, R.drawable.home_settings};
    private SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        GridView gv = (GridView) findViewById(R.id.gv);
        gv.setAdapter(new HomeAdapter());

        mPref = getSharedPreferences("config",MODE_PRIVATE);

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        showPasswordDialog();
                        break;
                    case 7:
                        startActivity(new Intent(HomeActivity.this,AToolsActivity.class));
                        break;
                    case 8:
                        startActivity(new Intent(HomeActivity.this, SettingActivity.class));
                        break;
                }
            }
        });
    }


    /**
     * 判断逻辑出现哪个对话框
     */
    private void showPasswordDialog() {
        if(TextUtils.isEmpty(mPref.getString("password",null))){
            showPasswordSetDialog();
        }else{
            showPasswordInputDialog();
        }

    }


    /**
     * 弹出输入密码的对话框
     */
    private void showPasswordInputDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        final AlertDialog dialog = builder.create();

        View view = View.inflate(this, R.layout.dialog_input_password, null);
        dialog.setView(view, 0, 0, 0, 0);
        Button bt_ok = (Button) view.findViewById(R.id.bt_ok);
        Button bt_cancel = (Button) view.findViewById(R.id.bt_cancel);

        final EditText et_password = (EditText) view.findViewById(R.id.et_password);

        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = et_password.getText().toString();

                if (!TextUtils.isEmpty(password)) {
                    if(MD5Utils.encode(password).equals(mPref.getString("password", null))){
                        Toast.makeText(HomeActivity.this, "登录成功!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        startActivity(new Intent(HomeActivity.this,LostFindActivity.class));
                    }else{
                        Toast.makeText(HomeActivity.this, "密码错误!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(HomeActivity.this, "输入框不能为空!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    /**
     * 弹出设置输入密码的对话框
     */
    private void showPasswordSetDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        final AlertDialog dialog = builder.create();

        View view = View.inflate(this, R.layout.dialog_set_password, null);
        dialog.setView(view, 0, 0, 0, 0);
        Button bt_ok = (Button) view.findViewById(R.id.bt_ok);
        Button bt_cancel = (Button) view.findViewById(R.id.bt_cancel);

        final EditText et_password = (EditText) view.findViewById(R.id.et_password);
        final EditText et_password_confirm = (EditText) view.findViewById(R.id.et_password_confirm);

        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = et_password.getText().toString();
                String password_confirm = et_password_confirm.getText().toString();

                if ((!TextUtils.isEmpty(password)) && (!TextUtils.isEmpty(password_confirm))) {

                    if (password.equals(password_confirm)) {
                        mPref.edit().putString("password", MD5Utils.encode(password)).apply();
                        Toast.makeText(HomeActivity.this, "成功设置密码!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        startActivity(new Intent(HomeActivity.this, LostFindActivity.class));
                    } else {
                        Toast.makeText(HomeActivity.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(HomeActivity.this, "输入框不能为空!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    /**
     * Grid布局的适配器内部类对象
     */
    private class HomeAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mItems.length;
        }

        @Override
        public Object getItem(int position) {
            return mItems[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = View.inflate(HomeActivity.this, R.layout.home_list_item, null);

            ImageView iv_item = (ImageView) v.findViewById(R.id.iv_item);
            TextView tv_item = (TextView) v.findViewById(R.id.tv_item);

            tv_item.setText(mItems[position]);
            iv_item.setImageResource(mResources[position]);
            return v;
        }
    }
}
