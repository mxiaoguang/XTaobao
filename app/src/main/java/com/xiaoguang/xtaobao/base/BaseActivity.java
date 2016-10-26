package com.xiaoguang.xtaobao.base;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.WindowManager;

import com.xiaoguang.xtaobao.application.CustomApplcation;
import com.xiaoguang.xtaobao.bean.User;

import cn.bmob.v3.BmobUser;

/**
 * 所有Activity的基类
 * Created by 11655 on 2016/10/18.
 */

public class BaseActivity extends FragmentActivity {

    //声明一个构建着对象，用于创建警告对话框
    private AlertDialog.Builder builder;
    //用于创建一个进度条对话框
    private ProgressDialog dialog;
    //用于打印log
    public final String TAG = "myTag";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //固定屏幕方向
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //设置在activity启动的时候输入法默认是不开启的
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    /**
     *  功能：实现沉浸式通知栏，使通知栏和APP的标题颜色一样
     */
    protected void immersiveNotification(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 功能：显示一个警告对话框,无按钮，需要自己设置
     * @param title  标题
     * @param msg 内容
     * @param flag 是否可以取消
     * @return AlertDialog.Builder 对象
     */
    protected AlertDialog.Builder showAlertDialog(String title,String msg,boolean flag){
        if (builder==null){
            //创建一个构建者对象
            builder = new AlertDialog.Builder(this);
            builder.setTitle(title).setMessage(msg).setCancelable(flag);
        }
        return builder;
    }

    /**
     * 功能:取消警告对话框
     */
    protected void dismissAlertDialog(AlertDialog alertDialog){
        if (alertDialog!=null){
            //取消警告对话框
            alertDialog.dismiss();
        }
    }
    /**
     * 功能 ：显示一个进度条对话框
     */
    protected void showProcessDialog(String title,String msg,boolean falg){
        if(dialog==null){
            dialog = new ProgressDialog(this);
        }
        dialog.setTitle(title);
        dialog.setMessage(msg);
        dialog.setCancelable(falg);
        dialog.show();
    }

    /**
     * 功能 ：取消一个进度条对话框
     */
    protected void dismissProcessDialog(){
        if(dialog!=null){
            dialog.dismiss();
        }
    }

    /**
     * 判断用户是否登陆过
     *
     * @return true 为登陆成功 false 为没有登陆过
     */
    protected boolean isLogin() {
        if (BmobUser.getCurrentUser() == null) {
            return false;
        }
        CustomApplcation.getInstance().setCurrentUser(BmobUser.getCurrentUser(User.class));
        return true;
    }
}
