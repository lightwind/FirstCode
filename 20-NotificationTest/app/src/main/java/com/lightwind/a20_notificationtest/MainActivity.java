package com.lightwind.a20_notificationtest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button sendNotice = (Button) findViewById(R.id.send_notice);
        sendNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0,
                        intent, 0);
                NotificationManager manager = (NotificationManager) getSystemService(Context
                        .NOTIFICATION_SERVICE);
                // 设置取消通知的第二种方法
                manager.cancel(1);
                Notification notification = new NotificationCompat.Builder(MainActivity.this)
                        .setContentTitle("This is content title")
                        .setContentText("This is content text")
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        // 设置内容为长文字
//                        .setContentText("Learn how to build notification, send and sync data,
// and use voice actions. Get the official Android IED and developer tools to build apps for
// Android")
//                        .setStyle(new NotificationCompat.BigTextStyle().bigText("Learn how to " +
//                                "build notification, send and sync data, and use voice actions.
// " +
//                                "Get the official Android IED and developer tools to build apps
// " +
//                                "for Android"))
                        // 设置大图片
//                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture
//                                (BitmapFactory.decodeResource(getResources(), R.drawable.pic)))
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap
                                .ic_launcher_round))
                        // 设置页面跳转
                        .setContentIntent(pendingIntent)
                        // 设置取消通知的第一种方法
                        .setAutoCancel(true)
                        // 播放音频文件
                        .setSound(Uri.fromFile(new File("/system/media/audio/ringtones/Luna.ogg")))
                        // 设置手机振动
                        .setVibrate(new long[]{0, 1000, 1000, 1000})
                        // LED灯以绿色灯光一闪一闪的效果
                        .setLights(Color.GREEN, 1000, 1000)
                        .build();
                manager.notify(1, notification);
            }
        });
    }
}
