package com.xiaoguang.xtaobao.presenter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;

import com.xiaoguang.xtaobao.adapter.ActHomePagerAdapter;
import com.xiaoguang.xtaobao.contract.IHomeContract;
import com.xiaoguang.xtaobao.fragment.AskFragment;
import com.xiaoguang.xtaobao.fragment.HomeFragment;
import com.xiaoguang.xtaobao.fragment.PersonalFragment;
import com.xiaoguang.xtaobao.fragment.ShopcarFragment;
import com.xiaoguang.xtaobao.fragment.WeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 11655 on 2016/10/19.
 */

public class ActHomePresenterImpl implements IHomeContract.IHomePresenter {
    private final IHomeContract.IHomeView view;
    private FragmentPagerAdapter pagerAdapter;
    //声明一个集合用于存放Fragment
    private List<Fragment> fragments;

    public ActHomePresenterImpl(IHomeContract.IHomeView view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void initData() {
        //获取显示内容的viewpager
        ViewPager viewPagerContent = view.getmActHomeVpContent();
        //初始化数据
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new WeFragment());
        fragments.add(new AskFragment());
        fragments.add(new ShopcarFragment());
        fragments.add(new PersonalFragment());
        pagerAdapter = new ActHomePagerAdapter(view.getManager(), fragments);
        //为viewpager设置适配器
        viewPagerContent.setAdapter(pagerAdapter);
        //设置viewPager为不可滑动状态,存在主Fragment无法切换左右切换
        viewPagerContent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;  //修改为true
            }

        });

    }
}
