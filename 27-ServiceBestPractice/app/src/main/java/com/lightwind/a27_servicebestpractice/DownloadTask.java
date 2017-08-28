package com.lightwind.a27_servicebestpractice;

import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 功能：实现下载功能
 * 作者：刘洋
 * 时间：2017/8/27
 */

public class DownloadTask extends AsyncTask<String, Integer, Integer> {

    private static final int TYPE_SUCCESS = 0;
    private static final int TYPE_FAILED = 1;
    private static final int TYPE_PAUSED = 2;
    private static final int TYPE_CANCELED = 3;

    private DownloadListener mListener;
    private Boolean isCanceled = false;
    private Boolean isPaused = false;

    private int lastProgress;

    public DownloadTask(DownloadListener listener) {
        mListener = listener;
    }

    @Override
    protected Integer doInBackground(String... params) {
        InputStream inputStream = null;
        File file = null;
        RandomAccessFile savedFile = null;
        try {
            // 记录当前已经下载文件的长度
            long downloadLength = 0;
            String downloadUrl = params[0];
            String firstName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
            String directory = Environment.getExternalStoragePublicDirectory(Environment
                    .DIRECTORY_DOWNLOADS).getPath();
            file = new File(directory + firstName);
            if (file.exists()) {
                downloadLength = file.length();
            }
            long contentLength = getContentLength(downloadUrl);
            if (contentLength == 0) {
                return TYPE_FAILED;
            } else if (contentLength == downloadLength) {
                // 已经下载的字节数和总字节数相等，说明已经下载完毕
                return TYPE_SUCCESS;
            }
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    // 断点下载，指定从哪个字节开始下载
                    .addHeader("RANGE", "bytes=" + downloadLength + "-")
                    .url(downloadUrl)
                    .build();
            Response response = client.newCall(request).execute();
            if (response != null) {
                inputStream = response.body().byteStream();
                savedFile = new RandomAccessFile(file, "rw");
                // 跳过已经下载字节
                savedFile.seek(downloadLength);
                byte[] bytes = new byte[1024];
                int total = 0;
                int len;
                while ((len = inputStream.read(bytes)) != -1) {
                    if (isCanceled) {
                        return TYPE_CANCELED;
                    } else if (isPaused) {
                        return TYPE_PAUSED;
                    } else {
                        total += len;
                        savedFile.write(bytes, 0, len);
                        // 计算已经下载百分比
                        int progress = (int) ((total + downloadLength) * 100 / contentLength);
                        publishProgress(progress);
                    }
                }
                response.close();
                return TYPE_SUCCESS;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (savedFile != null) {
                try {
                    savedFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (isCanceled && file != null) {
                file.delete();
            }
        }
        return TYPE_FAILED;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        int progress = values[0];
        if (progress > lastProgress) {
            mListener.onProgress(progress);
            lastProgress = progress;
        }
    }

    @Override
    protected void onPostExecute(Integer status) {
        switch (status) {
            case TYPE_SUCCESS:
                mListener.onSuccess();
                break;
            case TYPE_FAILED:
                mListener.onFailed();
                break;
            case TYPE_PAUSED:
                mListener.onPause();
                break;
            case TYPE_CANCELED:
                mListener.onCanceled();
                break;
        }
    }

    public void pauseDownload() {
        isPaused = true;
    }

    public void cancelDownload() {
        isCanceled = true;
    }

    private long getContentLength(String downloadUrl) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(downloadUrl)
                .build();
        Response response = client.newCall(request).execute();
        if (response != null && response.isSuccessful()) {
            long contentLength = response.body().contentLength();
            response.close();
            return contentLength;
        }
        return 0;
    }
}
