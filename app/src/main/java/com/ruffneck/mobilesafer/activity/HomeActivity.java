package com.ruffneck.mobilesafer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruffneck.mobilesafer.R;

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

    private GridView gv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        gv = (GridView) findViewById(R.id.gv);
        gv.setAdapter(new HomeAdapter());

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 8:
                        startActivity(new Intent(HomeActivity.this,SettingActivity.class));
                        break;
                }
            }
        });
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
