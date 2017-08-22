package com.lightwind.a13_broadcastbestpractice;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：用于管理活动
 * 作者：刘洋
 * 时间：2017/8/22
 */

public class ActivityCollector {

    // 创建一个集合用于将开启的Activity放到集合中
    public static List<Activity> activities = new ArrayList<>();

    /**
     * 将活动放到集合中的方法
     */
    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    /**
     * 将Activity从集合中移除
     */
    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    /**
     * 关闭活动
     */
    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
