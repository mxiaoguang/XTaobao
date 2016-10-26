package com.xiaoguang.xtaobao.presenter;

import android.widget.GridView;

import com.xiaoguang.xtaobao.adapter.ActGoodsResultAdapter;
import com.xiaoguang.xtaobao.application.CustomApplcation;
import com.xiaoguang.xtaobao.bean.Goods;
import com.xiaoguang.xtaobao.contract.ILoveGoodsContract;
import com.xiaoguang.xtaobao.contract.ILoveGoodsContract.ILoveGoodsPresenter;

import java.util.List;

/**
 * 收藏商品的处理类
 * Created by 11655 on 2016/10/26.
 */

public class ActLoveGoodsPresenterImpl implements ILoveGoodsPresenter {
    private ILoveGoodsContract.ILoveGoodsView view;

    public ActLoveGoodsPresenterImpl(ILoveGoodsContract.ILoveGoodsView view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void initData() {
        //获取收藏的宝贝
        List<Goods> goodsList = (List<Goods>) CustomApplcation.getDatas("loveGoods",false);
        //获取控件，设置适配器
        GridView gridView = view.getmActGoodsResultGv();
        gridView.setAdapter(new ActGoodsResultAdapter(goodsList,CustomApplcation.getInstance().context));

    }
}
