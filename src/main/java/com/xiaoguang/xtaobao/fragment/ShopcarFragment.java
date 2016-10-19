package com.xiaoguang.xtaobao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaoguang.xtaobao.R;
import com.xiaoguang.xtaobao.base.BaseFragment;
import com.xiaoguang.xtaobao.contract.IFragShopCarContract;
import com.xiaoguang.xtaobao.presenter.FragShopCarPresenterImpl;

/**
 * 购物车
 * Created by 11655 on 2016/10/19.
 */

public class ShopcarFragment extends BaseFragment implements IFragShopCarContract.IFragShopCarView {

    private IFragShopCarContract.IFragShopCarPrensenter presenter;

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container, boolean b) {
        return inflater.inflate(R.layout.frag_shopcar, null);
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        new FragShopCarPresenterImpl(this);
    }

    @Override
    public void setPresenter(IFragShopCarContract.IFragShopCarPrensenter presenter) {
        this.presenter = presenter;
    }
}
