package com.ruffneck.mobilesafer.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by 佛剑分说 on 2015/10/14.
 */
public abstract class BaseSetupActivity extends Activity {

    private GestureDetector mDetector;
    public SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPref = getSharedPreferences("config",MODE_PRIVATE);

        mDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

                if(Math.abs(e1.getRawY() - e2.getRawY()) >200){
                    Toast.makeText(BaseSetupActivity.this, "这样滑是不行的哦!", Toast.LENGTH_SHORT).show();
                    return true;
                }

                if (Math.abs(velocityX) < 100) {
                    Toast.makeText(BaseSetupActivity.this, "滑动得太慢了!", Toast.LENGTH_SHORT).show();
                    return true;
                }

                if (e1.getRawX() - e2.getRawX() > 200) {
                    showNextPage();
                    return true;
                }

                if (e1.getRawX() - e2.getRawX() < 200) {
                    showPreviousPage();
                    return true;
                }

                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }

    public void next(View v) {
        showNextPage();
    }

    public abstract void showNextPage();

    public void previous(View v) {
        showPreviousPage();
    }

    public abstract void showPreviousPage();

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
