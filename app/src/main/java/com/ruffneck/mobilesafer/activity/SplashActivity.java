package com.ruffneck.mobilesafer.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.ruffneck.mobilesafer.R;
import com.ruffneck.mobilesafer.utils.StreamUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SplashActivity extends Activity {


    private static final int CODE_UPDATE_DIALOG = 1;
    private static final int CODE_ERROR_NET = 2;
    private static final int CODE_ERROR_JSON = 3;
    private static final int CODE_ENTER_HOME = 4;
    private static final int CODE_ERROR_URL = 5;

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
                    enterHome();
                    break;
                case CODE_UPDATE_DIALOG:
                    showUpdateDialog();
                    break;
                case CODE_ERROR_NET:
                    Toast.makeText(SplashActivity.this, "网络异常!", Toast.LENGTH_SHORT).show();
                    enterHome();
                    break;
                case CODE_ERROR_JSON:
                    Toast.makeText(SplashActivity.this, "JSON异常!", Toast.LENGTH_SHORT).show();
                    enterHome();
                    break;
                case CODE_ENTER_HOME:
                    enterHome();
                    break;
            }


        }
    };


    private void enterHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
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

        final long startTime = System.currentTimeMillis();
        final Message message = Message.obtain();
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                HttpURLConnection conn = null;
                try {
                    url = new URL("http://172.18.162.1:8080/update.json");
                    conn = (HttpURLConnection) url.openConnection();
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
                            message.what = CODE_UPDATE_DIALOG;
                        } else {
                            message.what = CODE_ENTER_HOME;
                        }

                    }


                } catch (MalformedURLException e) {
                    message.what = CODE_ERROR_URL;
                } catch (IOException e) {
                    message.what = CODE_ERROR_NET;
                } catch (JSONException e) {
                    message.what = CODE_ERROR_JSON;
                } finally {
                    long endTime = System.currentTimeMillis();
                    long timeUsed = endTime - startTime;
                    if (timeUsed < 2000) {
                        try {
                            Thread.sleep(2000 - timeUsed);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    mHandler.sendMessage(message);
                    if (conn != null) conn.disconnect();
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
                downloadApk();
            }
        });
        builder.setNegativeButton("以后再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                enterHome();
            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                enterHome();
            }
        });
        builder.show();
    }


    /**
     * 下载新版本APK文件
     */
    private void downloadApk() {

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String target = Environment.getExternalStorageDirectory().getPath()+"/update.apk";

            final TextView tv_progress = (TextView) findViewById(R.id.tv_progress);
            final ProgressBar pb_download = (ProgressBar) findViewById(R.id.pb_download);

            HttpUtils http = new HttpUtils();
             http.download(mDownloadUrl,target,
                    true, // 如果目标文件存在，接着未完成的部分继续下载。服务器不支持RANGE时将从新下载。
                    true, // 如果从请求返回信息中获取到文件名，下载完成后自动重命名。
                    new RequestCallBack<File>() {

                        @Override
                        public void onStart() {
                            tv_progress.setVisibility(View.VISIBLE);
                            pb_download.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onLoading(long total, long current, boolean isUploading) {
                            tv_progress.setText("下载进度:" + (current * 100 / total));
                            pb_download.setMax((int) total);
                            pb_download.setProgress((int)current);
                        }

                        @Override
                        public void onSuccess(ResponseInfo<File> responseInfo) {
                            Toast.makeText(SplashActivity.this, "下载成功!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            intent.setAction("android.intent.action.VIEW");
                            intent.addCategory("android.intent.category.DEFAULT");
                            intent.setDataAndType(Uri.fromFile(responseInfo.result), "application/vnd.android.package-archive");
                            startActivityForResult(intent,0);
                        }


                        @Override
                        public void onFailure(HttpException error, String msg) {
                            Toast.makeText(SplashActivity.this, "下载失败!", Toast.LENGTH_SHORT).show();
                            enterHome();
                        }

                    });

        } else {
            Toast.makeText(SplashActivity.this, "没有找到sd卡", Toast.LENGTH_SHORT).show();
            enterHome();
        }

    }


    /**
     * 用户取消安装后进入主界面
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        enterHome();
    }
}
