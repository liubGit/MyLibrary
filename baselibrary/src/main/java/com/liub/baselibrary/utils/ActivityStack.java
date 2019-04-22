package com.liub.baselibrary.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * Created by liub on 2018/4/14.
 */

@SuppressWarnings({"WeakerAccess", "unused"})
public final class ActivityStack {
    static ActivityStack instance;

    private ActivityStack() {
    }

    public static ActivityStack getInstance() {
        if (instance == null)
            synchronized (ActivityStack.class) {
                if (instance == null)
                    instance = new ActivityStack();
            }
        return instance;
    }

    private List<Activity> activityList = new LinkedList<>();

    /**
     * 存放 Activity 到 List 中
     */
    public void addActivity(Activity activity) {
        activityList.add(activity);
        // 向路由表注册新增页面Class
    }

    /**
     * 是否存在目标class
     */
    public boolean hasActivity(Class<Activity> targetActivity) {
        for (Activity activity : activityList) {
            if (activity.getClass() == targetActivity) {
                return true;
            }
        }

        return false;
    }

    /**
     * 从 List 中移除activity
     */
    public void removeActivity(Activity activity) {
        if (!isLock)
            activityList.remove(activity);
    }

    //锁定，主动销毁activity的时候，不执行
    private boolean isLock = false;

    /**
     * 结束指定的 {@link Activity}
     */
    public void finish(Class<?> cls) {
        List<Activity> temp = new ArrayList<>(1);
        for (Activity activity : activityList) {
            if (activity.getClass().equals(cls)) {
                temp.add(activity);
            }
        }
        for (Activity activity : temp) {
            activity.finish();
            activityList.remove(activity);
        }
    }

    /**
     * 关闭全部activity
     */
    public void finishAllActivity() {
        isLock = true;
        try {
            int size = activityList.size();
            for (int i = 0; i < size; i++)
                activityList.get(i).finish();
            activityList.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        isLock = false;
    }

    public Class getTopActivityClass() {
        return activityList.get(activityList.size() - 1).getClass();
    }

    public Activity getTopActivity() {
        return activityList.get(activityList.size() - 1);
    }

}
