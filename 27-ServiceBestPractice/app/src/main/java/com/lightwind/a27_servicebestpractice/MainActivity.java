package com.lightwind.a27_servicebestpractice;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DownloadService.DownloadBinder mDownloadBinder;
    /**
     * Interface for monitoring the state of an application service.
     * 用于监听应用程序服务状态的接口。
     */
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mDownloadBinder = (DownloadService.DownloadBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.start_download).setOnClickListener(this);
        findViewById(R.id.pause_download).setOnClickListener(this);
        findViewById(R.id.cancel_download).setOnClickListener(this);

        Intent intent = new Intent(this, DownloadService.class);
        // 启动服务
        startService(intent);
        // 活动绑定服务
        bindService(intent, mConnection, BIND_AUTO_CREATE);
        // SD卡运行时权限
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission
                .WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission
                    .WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_download:
                String url = "http:/192.168.1.6:8080/jdk-8u121-windows-x64.exe";
                mDownloadBinder.startDownload(url);
                break;
            case R.id.pause_download:
                mDownloadBinder.pauseDownload();
                break;
            case R.id.cancel_download:
                mDownloadBinder.cancelDownload();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "拒绝权限将无法使用程序！", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
    }
}
