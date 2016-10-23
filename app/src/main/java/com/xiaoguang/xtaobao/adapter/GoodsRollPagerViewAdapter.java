package com.xiaoguang.xtaobao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * 显示商品轮播图片的适配器
 * Created by 11655 on 2016/10/19.
 */

public class GoodsRollPagerViewAdapter extends StaticPagerAdapter {
    //数据源
    List<BmobFile> imgs;
    //上下文对象
    Context context;
    //布局填充器
    LayoutInflater inflater;

    public GoodsRollPagerViewAdapter(List<BmobFile> imgUrls) {
        this.imgs = imgUrls;
    }

    @Override
    public View getView(ViewGroup container, int position) {
        ImageView view = new ImageView(container.getContext());
        //设置图片
        Picasso.with(context).load(imgs.get(position).getUrl()).into(view);
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        return view;
    }

    @Override
    public int getCount() {
        return imgs.size();
    }
}
