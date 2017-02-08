package com.example.surine.materialdesigndemo.com.surine.CollectorUtil;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by surine on 2017/2/7.
 */

public class ActivityCollector{
     public static List<Activity> sActivities = new ArrayList<>();


    //add one
    public static void addActivity(Activity activity){
        sActivities.add(activity);
    }

    //remove one
    public static void removeActivity(Activity activity){
        sActivities.remove(activity);
    }

    //finish all
    public static void finishAll(){
        for(Activity activity:sActivities){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}
