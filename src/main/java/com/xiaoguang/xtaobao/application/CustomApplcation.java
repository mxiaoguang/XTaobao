package com.xiaoguang.xtaobao.application;

import android.app.Activity;
import android.app.Application;

import com.xiaoguang.xtaobao.bean.User;
import com.xiaoguang.xtaobao.util.ActivityManagerUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义的全部的Applcation类
 * Created by 11655 on 2016/10/18.
 */

public class CustomApplcation extends Application {

    //一个标记
    public static String TAG;
    //当前的用户
    private static User currentUser;
    //用于存放数据
    private Map datas = new HashMap();
    private static CustomApplcation customApplcation = null;

    public static CustomApplcation getInstance() {
        return customApplcation;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public Map getDatas() {
        return datas;
    }

    public void setDatas(Map datas) {
        this.datas = datas;
    }

    /**
     * 获取当前的用户对象
     *
     * @return
     */
    public User getCurrentUser() {
        User user = currentUser;
        if (user != null) {
            return user;
        }
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        TAG = this.getClass().getSimpleName();
        //由于Application类本身已经单例，所以直接按以下处理即可。
        customApplcation = this;
    }

    public void addActivity(Activity ac) {
        ActivityManagerUtils.getInstance().addActivity(ac);
    }

    public void exit() {
        ActivityManagerUtils.getInstance().removeAllActivity();
    }

    public Activity getTopActivity() {
        return ActivityManagerUtils.getInstance().getTopActivity();
    }
}
