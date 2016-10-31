package com.xiaoguang.xtaobao.base;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaoguang.xtaobao.application.CustomApplcation;
import com.xiaoguang.xtaobao.bean.User;

import cn.bmob.v3.BmobUser;

/**
 * 所有Fragment的基类
 */

public abstract class BaseFragment extends Fragment {

    //定义一个用于重复view 的回收池
    private View contentView = null;
    /**
     * Fragment当前状态是否可见
     */
    protected boolean isVisible;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private ProgressDialog dialog;

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (contentView == null) {//判断回收池是否为空
            contentView = initLayout(inflater, container, false);
        }
        if (contentView != null) {
            return contentView;
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //初始化数据
        initData(savedInstanceState);
    }

    @Override
    public final void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }


    /**
     * 可见
     */
    protected void onVisible() {
        lazyLoad();
    }


    /**
     * 不可见
     */
    protected void onInvisible() {

    }


    /**
     * 延迟加载
     * 子类必须重写此方法
     */
    protected abstract void lazyLoad();

    @Override
    public final void onDestroyView() {
        //移除当前视图，防止重复加载相同视图使得程序闪退
        ((ViewGroup) contentView.getParent()).removeView(contentView);
        super.onDestroyView();
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


    /**
     *  功能 ：显示一个警告对话框
     */
    protected void showAlertDialog(String title,String text){
        if (builder==null){
            //创建一个构建者对象
            builder = new AlertDialog.Builder(getContext());
            builder.setTitle(title).setMessage(text).setCancelable(false);
            builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //跳转到系统网络设置
                    Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                    startActivity(intent);
                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //退出虚拟机
                    System.exit(0);
                }
            });
        }
        alertDialog = builder.show();
    }

    /**
     * 功能:取消警告对话框
     */
    protected void dismissAlertDialog(){
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
            dialog = new ProgressDialog(getContext());
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
     * 初始化Fragment的布局,当要创建视图时调用
     *
     * @param inflater  布局填充器
     * @param container ViewGroup
     * @param b         标记
     * @return view 返回视图
     */
    public abstract View initLayout(LayoutInflater inflater, ViewGroup container, boolean b);

    /**
     * 初始化数据,当ViewCreate被创建是调用此方法
     */
    protected abstract void initData(@Nullable Bundle savedInstanceState);

}
