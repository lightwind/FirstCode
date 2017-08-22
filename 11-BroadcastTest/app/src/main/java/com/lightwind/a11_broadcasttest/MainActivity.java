package com.lightwind.a11_broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private IntentFilter mIntentFilter;
    private LocalReceiver mLocalReceiver;
    private LocalBroadcastManager mBroadcastManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 获取LocalBroadcastManager管理器实例
        mBroadcastManager = LocalBroadcastManager.getInstance(this);
        Button button = (Button) findViewById(R.id.send_broadcast);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.lightwind.a11_broadcasttest.LOCAL_BROADCAST");
                mBroadcastManager.sendBroadcast(intent);
            }
        });
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("com.lightwind.a11_broadcasttest.LOCAL_BROADCAST");
        mLocalReceiver = new LocalReceiver();
        // 注册本地广播接收器
        mBroadcastManager.registerReceiver(mLocalReceiver, mIntentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBroadcastManager.unregisterReceiver(mLocalReceiver);

    }

    class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "received in local broadcast", Toast.LENGTH_SHORT).show();
        }
    }
}
