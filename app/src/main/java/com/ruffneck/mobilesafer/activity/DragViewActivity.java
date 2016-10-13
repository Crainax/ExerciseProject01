package com.ruffneck.mobilesafer.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ruffneck.mobilesafer.R;

public class DragViewActivity extends Activity {

    private TextView tv_top;
    private TextView tv_bottom;
    private ImageView iv_drag;
    private SharedPreferences mPref;
    private int startX;
    private int startY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dragview);

        mPref = getSharedPreferences("config", MODE_PRIVATE);

        tv_top = (TextView) findViewById(R.id.tv_top);
        tv_bottom = (TextView) findViewById(R.id.tv_bottom);

        iv_drag = (ImageView) findViewById(R.id.iv_drag);

        int lastX = mPref.getInt("lastX", 0);
        int lastY = mPref.getInt("lastY", 0);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)iv_drag.getLayoutParams();
        params.leftMargin = lastX;
        params.topMargin = lastY;
        iv_drag.setLayoutParams(params);

        iv_drag.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:

                        startX = (int) event.getRawX();
                        startY = (int) event.getRawY();

                        break;
                    case MotionEvent.ACTION_MOVE:

                        int endX = (int) event.getRawX();
                        int endY = (int) event.getRawY();

                        int dx = endX - startX;
                        int dy = endY - startY;

                        int left = iv_drag.getLeft() + dx;
                        int right = iv_drag.getRight() + dx;
                        int top = iv_drag.getTop() + dy;
                        int bottom = iv_drag.getBottom() + dy;

                        iv_drag.layout(left,top,right,bottom);

                        startX = endX;
                        startY = endY;
                        break;

                    case MotionEvent.ACTION_UP:

                        SharedPreferences.Editor editor = mPref.edit();
                        editor.putInt("lastX",iv_drag.getLeft());
                        editor.putInt("lastY",iv_drag.getTop());
                        editor.apply();

                        break;
                }


                return true;
            }
        });

    }
}
