package com.xiaoguang.xtaobao.presenter;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.xiaoguang.xtaobao.adapter.ActHomePagerAdapter;
import com.xiaoguang.xtaobao.contract.IFragWeContract;
import com.xiaoguang.xtaobao.fragment.NewsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 11655 on 2016/10/19.
 */

public class FragWePresenterImpl implements IFragWeContract.IFragWePresenter{

    private final IFragWeContract.IFragWeView view;
    //定义一个数据源
    private List<Fragment> fragments;
    public FragWePresenterImpl(IFragWeContract.IFragWeView view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void initData() {
        fragments = new ArrayList<>();
        /*进行数据测试*/
        fragments.add(new NewsFragment());
        fragments.add(new NewsFragment());
        fragments.add(new NewsFragment());
        fragments.add(new NewsFragment());
        fragments.add(new NewsFragment());
        //获取ViewPager
        ViewPager viewPager = view.getmFragWeVp();
        //设置适配器
        viewPager.setAdapter(new ActHomePagerAdapter(view.getManager(),fragments));
    }
}
