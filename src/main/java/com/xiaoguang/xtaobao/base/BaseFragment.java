package com.xiaoguang.xtaobao.base;

import android.os.Bundle;
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
    public void setUserVisibleHint(boolean isVisibleToUser) {
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
