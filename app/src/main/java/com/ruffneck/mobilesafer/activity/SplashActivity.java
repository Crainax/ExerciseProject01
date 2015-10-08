package com.ruffneck.mobilesafer.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;

import com.ruffneck.mobilesafer.R;
import com.ruffneck.mobilesafer.utils.StreamUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SplashActivity extends Activity {


    private static final int CODE_ERROR_URL = 0;
    private static final int CODE_UPDATE_DIALOG = 1;
    private static final int CODE_ERROR_NET = 2;
    private static final int CODE_ERROR_JSON = 3;
    private TextView tvVersion;

    private String mVersionName;
    private int mVersionCode;
    private String mDescription;
    private String mDownloadUrl;


    /**
     * 处理其他线程的Handler
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case CODE_ERROR_URL:
                    Toast.makeText(SplashActivity.this, "URL异常!", Toast.LENGTH_SHORT).show();
                    break;
                case CODE_UPDATE_DIALOG:
                    showUpdateDialog();
                    break;
                case CODE_ERROR_NET:
                    Toast.makeText(SplashActivity.this, "网络异常!", Toast.LENGTH_SHORT).show();
                    break;
                case CODE_ERROR_JSON:
                    Toast.makeText(SplashActivity.this, "JSON异常!", Toast.LENGTH_SHORT).show();
                    break;
            }


        }
    };


    /**
     * 创建一个提示更新的对话框
     */
    private void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("更新到" + mVersionName);
        builder.setMessage(mDescription);
        builder.setPositiveButton("确认更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("以后再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tvVersion = (TextView) findViewById(R.id.tv_version);
        tvVersion.setText("版本号:" + getVersionName());

        checkUpdate();
    }

    /**
     * 检查是否有更新
     */
    private void checkUpdate() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                try {
                    url = new URL("http://172.18.162.1:8080/update.json");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(5000);
                    conn.setConnectTimeout(5000);
                    conn.connect();

                    int respondCode = conn.getResponseCode();
                    if (respondCode == 200) {
                        InputStream is = conn.getInputStream();

                        String jsonString = StreamUtils.readFromStream(is);
                        JSONObject jsonObject = new JSONObject(jsonString);
                        mVersionCode = jsonObject.getInt("versionCode");
                        mVersionName = jsonObject.getString("versionName");
                        mDescription = jsonObject.getString("description");
                        mDownloadUrl = jsonObject.getString("downloadUrl");

//                        System.out.println(mDescription);
                        if (mVersionCode > getVersionCode()) {
                            mHandler.sendEmptyMessage(CODE_UPDATE_DIALOG);
                            System.out.println("有更新!");

                        }

                    }


                } catch (MalformedURLException e) {

                    mHandler.sendEmptyMessage(CODE_ERROR_URL);
                } catch (IOException e) {
                    mHandler.sendEmptyMessage(CODE_ERROR_NET);
                } catch (JSONException e) {
                    mHandler.sendEmptyMessage(CODE_ERROR_JSON);
                }
            }

        }).start();
    }

    /**
     * 获取当前版本的名字
     *
     * @return
     */

    private String getVersionName() {
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);

            String versionName = packageInfo.versionName;
//            System.out.println("versionName = " + versionName + ";versionCode = " + versionCode);
            return versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return "";

    }

    /**
     * 获取当前版本号
     *
     * @return
     */
    private int getVersionCode() {

        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);

            int versionCode = packageInfo.versionCode;
//            System.out.println("versionName = " + versionName + ";versionCode = " + versionCode);
            return versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return -1;
    }


}
