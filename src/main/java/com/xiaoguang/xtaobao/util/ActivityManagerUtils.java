package com.xiaoguang.xtaobao.util;

import android.app.Activity;

import java.util.ArrayList;

/**
 * Activity的管理工具类
 * Created by 11655 on 2016/10/18.
 */

public class ActivityManagerUtils {
    private ArrayList<Activity> activityList = new ArrayList<Activity>();

    private static ActivityManagerUtils activityManagerUtils;

    private ActivityManagerUtils(){

    }

    /**
     * 获取Activity 的管理类的对象,使用单例设计模式
     * @return Activity 的管理类的对象
     */
    public static ActivityManagerUtils getInstance(){
        if(null == activityManagerUtils){
            activityManagerUtils = new ActivityManagerUtils();
        }
        return activityManagerUtils;
    }

    /**
     * 获取处于栈顶的Activity
     * @return
     */
    public Activity getTopActivity(){
        return activityList.get(activityList.size()-1);
    }

    /**
     * 添加Activity
     * @param ac
     */
    public void addActivity(Activity ac){
        activityList.add(ac);
    }

    /**
     * 移除activity
     */
    public void removeAllActivity(){
        for(Activity ac:activityList){
            if(null != ac){
                if(!ac.isFinishing()){
                    ac.finish();
                }
                ac = null;
            }
        }
        activityList.clear();
    }
}
