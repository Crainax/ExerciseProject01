package com.ruffneck.mobilesafer.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ruffneck.mobilesafer.R;

/**
 * Created by 佛剑分说 on 2015/10/11.
 */
public class SettingItemView extends RelativeLayout {

    private TextView mtvTitle;
    private TextView mtvDesc;
    private CheckBox mcb;

    public SettingItemView(Context context) {
        super(context);
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setTitle(String title){
        mtvTitle.setText(title);
    }

    public void setDesc(String desc){
        mtvDesc.setText(desc);
    }

    private void init() {
        View.inflate(getContext(), R.layout.setting_item,this);

        mtvDesc = (TextView) findViewById(R.id.tv_desc);
        mtvTitle = (TextView) findViewById(R.id.tv_title);
        mcb = (CheckBox) findViewById(R.id.cb);

    }

    public boolean isChecked(){
        return mcb.isChecked();
    }

    public void setChecked(boolean b){
        mcb.setChecked(b);

        if(isChecked()){
            mtvDesc.setText("自动更新已经开启");
        }else {
            mtvDesc.setText("自动更新已经关闭");
        }
    }


}
