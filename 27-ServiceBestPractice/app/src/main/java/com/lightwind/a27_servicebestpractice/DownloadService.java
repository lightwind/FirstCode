package com.lightwind.a27_servicebestpractice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.io.File;

/**
 * 自定义Service，保证DownloadTask能够一直运行在后台
 */
public class DownloadService extends Service {

    // 下载任务
    private DownloadTask mDownloadTask;
    // 下载地址
    private String downloadUrl;

    /**
     * 实现DownloadListener里面具体的方法
     */
    private DownloadListener mListener = new DownloadListener() {
        @Override
        public void onProgress(int progress) {
            getNotificationManager().notify(1, getNotification("Downloading...", progress));
        }

        @Override
        public void onSuccess() {
            mDownloadTask = null;
            // 下载成功将前台服务通知关闭，并创建一个下载成功的通知
            stopForeground(true);
            getNotificationManager().notify(1, getNotification("Download Success", -1));
            Toast.makeText(DownloadService.this, "Download Success", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailed() {
            mDownloadTask = null;
            // 下载失败将前台服务通知关闭，并创建一个下载失败的通知
            stopForeground(true);
            getNotificationManager().notify(1, getNotification("Download Failed", -1));
            Toast.makeText(DownloadService.this, "Download Failed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPause() {
            mDownloadTask = null;
            Toast.makeText(DownloadService.this, "Paused", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCanceled() {
            mDownloadTask = null;
            stopForeground(true);
            Toast.makeText(DownloadService.this, "Canceled", Toast.LENGTH_SHORT).show();
        }
    };

    private DownloadBinder mBinder = new DownloadBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    class DownloadBinder extends Binder {
        /**
         * 开始下载
         */
        public void startDownload(String url) {
            if (mDownloadTask == null) {
                downloadUrl = url;
                mDownloadTask = new DownloadTask(mListener);
                mDownloadTask.execute(downloadUrl);
                startForeground(1, getNotification("Downloading...", 0));
                Toast.makeText(DownloadService.this, "Downloading...", Toast.LENGTH_SHORT).show();
            }
        }

        /**
         * 暂停下载
         */
        public void pauseDownload() {
            if (mDownloadTask != null) {
                mDownloadTask.pauseDownload();
            }
        }

        /**
         * 取消下载
         */
        public void cancelDownload() {
            if (mDownloadTask != null) {
                mDownloadTask.cancelDownload();
            } else {
                if (downloadUrl != null) {
                    // 取消下载需要将文件删除，并将通知关闭
                    String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
                    String directory = Environment.getExternalStoragePublicDirectory(Environment
                            .DIRECTORY_DOWNLOADS).getPath();
                    File file = new File(directory + fileName);
                    if (file.exists()) {
                        file.delete();
                    }
                    getNotificationManager().cancel(1);
                    stopForeground(true);
                    Toast.makeText(DownloadService.this, "Canceled", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * 获取Notification管理类
     */
    private NotificationManager getNotificationManager() {
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    /**
     * 获取广播
     */
    private Notification getNotification(String title, int progress) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap
                .ic_launcher_round));
        builder.setContentIntent(pendingIntent);
        builder.setContentTitle(title);
        if (progress > 0) {
            // 当progress大于等于0的时候，才显示下载进度
            builder.setContentText(progress + "%");
            builder.setProgress(100, progress, false);
        }
        return builder.build();
    }
}
