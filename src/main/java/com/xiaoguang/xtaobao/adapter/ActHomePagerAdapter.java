package com.xiaoguang.xtaobao.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Frament的适配器
 * Created by 11655 on 2016/10/19.
 */

public class ActHomePagerAdapter extends FragmentPagerAdapter {
    //定一个Fragment数据源
    private List<Fragment> fragments;
    public ActHomePagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
