package com.lightwind.a22_playeraudiotest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MediaPlayer mMediaPlayer = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button play = (Button) findViewById(R.id.play);
        Button pause = (Button) findViewById(R.id.pause);
        Button stop = (Button) findViewById(R.id.stop);
        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        stop.setOnClickListener(this);

        // Android 6.0运行时权限：SD卡
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission
                .WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission
                    .WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            // 初始化MediaPlayer
            initMediaPlayer();
        }
    }

    /**
     * 初始化MediaPlayer
     */
    private void initMediaPlayer() {
        try {
            File file = new File(Environment.getExternalStorageDirectory(), "music.mp3");
            // 指定音频文件的路径
            mMediaPlayer.setDataSource(file.getPath());
            // 让MediaPlayer进入准备状态
            mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 运行时权限
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager
                        .PERMISSION_GRANTED) {
                    initMediaPlayer();
                } else {
                    Toast.makeText(this, "拒接权限，无法使用程序！", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 按钮的点击监听
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play:
                if (!mMediaPlayer.isPlaying()) {
                    // 开始播放
                    mMediaPlayer.start();
                }
                break;
            case R.id.pause:
                if (mMediaPlayer.isPlaying()) {
                    // 暂停播放
                    mMediaPlayer.pause();
                }
                break;
            case R.id.stop:
                if (mMediaPlayer.isPlaying()) {
                    // 停止播放
                    mMediaPlayer.reset();
                    initMediaPlayer();
                }
                break;
        }
    }

    /**
     * 销毁后停止播放，释放资源
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
    }
}
