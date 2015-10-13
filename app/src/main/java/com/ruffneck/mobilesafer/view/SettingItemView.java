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

    private static final String NAMESPACE = "http://schemas.android.com/apk/res-auto";
    private TextView mtvTitle;
    private TextView mtvDesc;
    private CheckBox mcb;
    private String mDescOn;
    private String mDescOff;
    private String mTitle;

    public SettingItemView(Context context) {
        super(context);
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initAttrs(attrs);

        init();
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initAttrs(attrs);

        init();

    }

    private void initAttrs (AttributeSet attrs){

        mTitle = attrs.getAttributeValue(NAMESPACE, "item_title");
        mDescOn = attrs.getAttributeValue(NAMESPACE,"desc_on");
        mDescOff = attrs.getAttributeValue(NAMESPACE,"desc_off");

    }

/*    public void setTitle(String title){
        mtvTitle.setText(title);
    }*/

/*    public void setDesc(String desc){
        mtvDesc.setText(desc);
    }*/

    private void init() {
        View.inflate(getContext(), R.layout.setting_item,this);

        mtvDesc = (TextView) findViewById(R.id.tv_desc);
        mtvTitle = (TextView) findViewById(R.id.tv_title);
        mcb = (CheckBox) findViewById(R.id.cb);

        if(mTitle != null)mtvTitle.setText(mTitle);

        if(mDescOff != null)mtvDesc.setText(mDescOff);

    }

    public boolean isChecked(){
        return mcb.isChecked();
    }

    public void setChecked(boolean b){
        mcb.setChecked(b);

        if(isChecked()){
            mtvDesc.setText(mDescOn);
        }else {
            mtvDesc.setText(mDescOff);
        }
    }


}
