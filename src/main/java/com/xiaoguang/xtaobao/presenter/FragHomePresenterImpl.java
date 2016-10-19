package com.xiaoguang.xtaobao.presenter;

import android.widget.GridView;

import com.jude.rollviewpager.RollPagerView;
import com.sunfusheng.marqueeview.MarqueeView;
import com.xiaoguang.xtaobao.R;
import com.xiaoguang.xtaobao.adapter.AdRollPagerViewAdapter;
import com.xiaoguang.xtaobao.adapter.GridViewAdapter;
import com.xiaoguang.xtaobao.application.CustomApplcation;
import com.xiaoguang.xtaobao.contract.IFragHomeContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 11655 on 2016/10/19.
 */

public class FragHomePresenterImpl implements IFragHomeContract.IFragHomePrensenter {
    //存放轮播图片Id
    private ArrayList<Integer> imgs;
    //存放中间小控件的id
    private Integer[] sortImgs;
    //存放中间的文字
    private String[] sortStrs;
    private AdRollPagerViewAdapter adapter;
    private final IFragHomeContract.IFragHomeView view;

    public FragHomePresenterImpl(IFragHomeContract.IFragHomeView view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void intData() {
        //获取显示广告的RollPagerView对象
        RollPagerView rollPagerViewAd = view.getmActHomeVpAd();
        //获取中间显示各个小控件的GridView
        GridView gridViewSort = view.getGridViewSort();
        //获取显示资讯头条的控件
        MarqueeView marqueeViewTop = view.getMarqueeViewTop();
        //初始化数据源
        imgs = new ArrayList<>();
        sortImgs = new Integer[]{R.drawable.frag_home_sort_tianmao, R.drawable.frag_home_sort_juhuasuan,
                R.drawable.frag_home_sort_jinkou, R.drawable.frag_home_sort_waimai, R.drawable.frag_home_sort_market,
                R.drawable.frag_home_sort_chongzhi, R.drawable.frag_home_sort_travel, R.drawable.frag_home_sort_tao,
                R.drawable.frag_home_sort_daojia, R.drawable.frag_home_sort_type};
        sortStrs = new String[]{"天猫", "聚划算", "天猫国际", "外卖", "天猫超市", "充值中心", "阿里旅行",
                "领金币", "到家", "分类"};
        //添加数据
        imgs.add(R.drawable.pager1);
        imgs.add(R.drawable.pager2);
        imgs.add(R.drawable.pager3);
        imgs.add(R.drawable.pager4);
        imgs.add(R.drawable.pager5);


        //初始化适配器
        adapter = new AdRollPagerViewAdapter(imgs);
        //为RollPagerView 设置适配器
        rollPagerViewAd.setAdapter(adapter);
        //为gridVIew 设置适配器
        gridViewSort.setAdapter(new GridViewAdapter(CustomApplcation.getInstance().context,sortImgs,sortStrs));

        //此处数据，应该为从网络上获取
        List<String> info = new ArrayList<>();
        info.add("1. 大家好，我是C陈志广。");
        info.add("2. 这是我的二期项目！");
        info.add("3. GitHub帐号：mmengchen");
        info.add("4. 淘宝双11.11");
        info.add("5. 个人博客");
        info.add("6. 消息进行测试");
        //启动滚动
        marqueeViewTop.startWithList(info);
    }
}
