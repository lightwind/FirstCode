package com.lightwind.a27_servicebestpractice;

/**
 * 功能：对下载过程中的各种状态进行监听和回调
 * 作者：刘洋
 * 时间：2017/8/27
 */

public interface DownloadListener {

    // 通知当前的下载进度
    void onProgress(int progress);

    // 通知下载成功事件
    void onSuccess();

    // 通知下载失败事件
    void onFailed();

    // 通知下载暂停事件
    void onPause();

    // 通知下载取消事件
    void onCanceled();
}
