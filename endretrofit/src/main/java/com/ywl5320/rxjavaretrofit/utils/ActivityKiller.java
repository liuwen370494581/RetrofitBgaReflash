package com.ywl5320.rxjavaretrofit.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuwen on 2016/12/15.
 */
public class ActivityKiller {

    private static ActivityKiller activityKiller = null;
    private List<Activity> activityList = new ArrayList<>();


    public static ActivityKiller getInstance() {
        if (activityKiller != null) {
            activityKiller = new ActivityKiller();
        }
        return activityKiller;
    }


    public void addActivity(Activity activity) {
        if (!activityList.contains(activity)) {
            activityList.add(activity);
        }
    }

    public void removeActivity(Activity activity) {
        if (activityList.contains(activity)) {
            activityList.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    public void exitActivityInList() {
        for (Activity activity : activityList) {
            activity.finish();
            activity = null;
        }
        activityList.clear();
    }

    public static void hindActivity(Context context) {
        Intent home = new Intent(Intent.ACTION_MAIN);
        home.addCategory(Intent.CATEGORY_HOME);
        context.startActivity(home);
    }
}