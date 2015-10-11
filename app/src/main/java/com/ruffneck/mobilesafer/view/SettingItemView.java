package com.ruffneck.mobilesafer.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.ruffneck.mobilesafer.R;

/**
 * Created by 佛剑分说 on 2015/10/11.
 */
public class SettingItemView extends RelativeLayout {

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

    private void init() {
        View.inflate(getContext(), R.layout.setting_item,this);
    }
}
