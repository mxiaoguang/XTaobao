package com.xiaoguang.xtaobao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jude.rollviewpager.adapter.StaticPagerAdapter;

import java.util.List;

/**
 * 显示广告轮播图片的适配器
 * Created by 11655 on 2016/10/19.
 */

public class AdRollPagerViewAdapter extends StaticPagerAdapter {
    //数据源
    List<Integer> imgIds;
    //上下文对象
    Context context;
    //布局填充器
    LayoutInflater inflater;

    public AdRollPagerViewAdapter(List<Integer> imgIds) {
        this.imgIds = imgIds;
    }

    @Override
    public View getView(ViewGroup container, int position) {
        ImageView view = new ImageView(container.getContext());
        //设置图片
        view.setImageResource(imgIds.get(position));
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        return view;
    }

    @Override
    public int getCount() {
        return imgIds.size();
    }
}
