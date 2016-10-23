package com.xiaoguang.xtaobao.presenter;

import android.widget.TextView;

import com.jude.rollviewpager.RollPagerView;
import com.xiaoguang.xtaobao.adapter.GoodsRollPagerViewAdapter;
import com.xiaoguang.xtaobao.application.CustomApplcation;
import com.xiaoguang.xtaobao.bean.Goods;
import com.xiaoguang.xtaobao.contract.IGoodsDetialsContract;
import com.xiaoguang.xtaobao.widget.XListView;

/**
 * Created by 11655 on 2016/10/23.
 */

public class ActGoodsDetailsPresenterImpl implements IGoodsDetialsContract.IGoodsDetialsPresenter {
    private IGoodsDetialsContract.IGoodsDetialsView view;
    private RollPagerView mRollVpAd;
    private TextView mTvGoodsName;
    private TextView mTvGoodsMoney;
    private XListView mXlvPl;

    public ActGoodsDetailsPresenterImpl(IGoodsDetialsContract.IGoodsDetialsView view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void initData() {
        //获取控件
       mRollVpAd = view.getmActGoodsDetailsRollVpAd();
       mTvGoodsName =  view.getmActGoodsDetailsTvGoodsName();
        mTvGoodsMoney =view.getmActGoodsDetailsTvMoney();
        mXlvPl = view.getmActGoodsDetailsXlv();
        //获取数据
        Goods goods = (Goods) CustomApplcation.getDatas("goods",true);
        //设置数据
        mRollVpAd.setAdapter(new GoodsRollPagerViewAdapter(goods.getGoodsImgs()));
        mTvGoodsName.setText(goods.getGoodsName());
        mTvGoodsMoney.setText("¥ "+goods.getGoodsPrice());
        //评论暂不设置
    }
}
