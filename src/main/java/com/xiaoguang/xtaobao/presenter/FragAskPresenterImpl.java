package com.xiaoguang.xtaobao.presenter;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.xiaoguang.xtaobao.adapter.ActHomePagerAdapter;
import com.xiaoguang.xtaobao.contract.IFragAskContract;
import com.xiaoguang.xtaobao.fragment.NewsFragment;
import com.xiaoguang.xtaobao.util.LogUtils;

import java.util.ArrayList;

/**
 * 问大家界面的处理
 * Created by 11655 on 2016/10/19.
 */

public class FragAskPresenterImpl implements IFragAskContract.IFragAskPresenter {
    private final IFragAskContract.IFragAskView view;
    //设置数据源
    private ArrayList<Fragment> fragments;

    public FragAskPresenterImpl(IFragAskContract.IFragAskView view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void initData() {
        LogUtils.i("myTag","frag ask 执行了");
        fragments = new ArrayList<>();
        /*进行数据测试 8个种类*/
        fragments.add(new NewsFragment());
        fragments.add(new NewsFragment());
        fragments.add(new NewsFragment());
        fragments.add(new NewsFragment());
        fragments.add(new NewsFragment());
        fragments.add(new NewsFragment());
        fragments.add(new NewsFragment());
        fragments.add(new NewsFragment());
        //获取ViewPager
        ViewPager viewPager = view.getmFragAskVp();
        //设置适配器
        viewPager.setAdapter(new ActHomePagerAdapter(view.getManager(), fragments));
    }
}
