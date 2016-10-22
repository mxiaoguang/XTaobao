package com.xiaoguang.xtaobao.presenter;

import android.widget.GridView;

import com.jude.rollviewpager.RollPagerView;
import com.sunfusheng.marqueeview.MarqueeView;
import com.xiaoguang.xtaobao.R;
import com.xiaoguang.xtaobao.adapter.AdRollPagerViewAdapter;
import com.xiaoguang.xtaobao.adapter.ContentGridViewAdapter;
import com.xiaoguang.xtaobao.adapter.SortGridViewAdapter;
import com.xiaoguang.xtaobao.application.CustomApplcation;
import com.xiaoguang.xtaobao.contract.IFragHomeContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 11655 on 2016/10/19.
 */

public class FragHomePresenterImpl implements IFragHomeContract.IFragHomePresenter {
    //存放轮播图片Id
    private ArrayList<Integer> imgs;
    //存放中间小控件的id
    private Integer[] sortImgs;
    //存放中间的文字
    private String[] sortStrs;
    //存放下部分小图标
    private Integer contentIcoImgs[];
    //存放商品图片
    private Integer contentImgs[];
    //存放小图标对应文字
    private String contentText[];
    //存放下面文字
    private String contentText2[];
    private AdRollPagerViewAdapter adapter;
    private final IFragHomeContract.IFragHomeView view;

    public FragHomePresenterImpl(IFragHomeContract.IFragHomeView view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void initData() {
        //获取显示广告的RollPagerView对象
        RollPagerView rollPagerViewAd = view.getmActHomeVpAd();
        //获取中间显示各个小控件的GridView
        GridView gridViewSort = view.getGridViewSort();
        //获取显示资讯头条的控件
        MarqueeView marqueeViewTop = view.getMarqueeViewTop();
        //获取下部分的GridView空间
        GridView gridViewContent = view.getGridViewContent();
        //初始化数据源
        imgs = new ArrayList<>();
        //添加数据
        imgs.add(R.drawable.pager1);
        imgs.add(R.drawable.pager2);
        imgs.add(R.drawable.pager3);
        imgs.add(R.drawable.pager4);
        imgs.add(R.drawable.pager5);

        sortImgs = new Integer[]{R.drawable.frag_home_sort_tianmao, R.drawable.frag_home_sort_juhuasuan,
                R.drawable.frag_home_sort_jinkou, R.drawable.frag_home_sort_waimai, R.drawable.frag_home_sort_market,
                R.drawable.frag_home_sort_chongzhi, R.drawable.frag_home_sort_travel, R.drawable.frag_home_sort_tao,
                R.drawable.frag_home_sort_daojia, R.drawable.frag_home_sort_type};
        sortStrs = new String[]{"天猫", "聚划算", "天猫国际", "外卖", "天猫超市", "充值中心", "阿里旅行",
                "领金币", "到家", "分类"};
        contentIcoImgs = new Integer[]{R.drawable.frag_home_qianggou,R.drawable.frag_home_haohuo,
                R.drawable.frag_home_guangjie,R.drawable.frag_home_qingdan};
        contentImgs = new Integer[]{R.drawable.xiangj,R.drawable.bookbag,R.drawable.xiangj,R.drawable.bookbag};
        contentText = new String[]{"淘抢购","有好货","爱逛街","必买清单"};
        contentText2 = new String[]{"极速抢购","高颜值美物","时髦流行家","整理好帮手"};

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

        //初始化适配器
        adapter = new AdRollPagerViewAdapter(imgs);
        //为RollPagerView 设置适配器
        rollPagerViewAd.setAdapter(adapter);
        //为gridVIew 设置适配器
        gridViewSort.setAdapter(new SortGridViewAdapter(CustomApplcation.getInstance().context, sortImgs, sortStrs,R.layout.frag_home_gv_sort_item));
        gridViewContent.setAdapter(new ContentGridViewAdapter(CustomApplcation.getInstance().context,
                contentIcoImgs,contentImgs,contentText,contentText2));
    }
}
