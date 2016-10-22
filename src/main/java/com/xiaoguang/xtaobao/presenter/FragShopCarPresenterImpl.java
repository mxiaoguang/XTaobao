package com.xiaoguang.xtaobao.presenter;

import com.xiaoguang.xtaobao.contract.IFragShopCarContract;

/**
 * Created by 11655 on 2016/10/19.
 */

public class FragShopCarPresenterImpl implements IFragShopCarContract.IFragShopCarPresenter {

    private IFragShopCarContract.IFragShopCarView view;
    public FragShopCarPresenterImpl(IFragShopCarContract.IFragShopCarView view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void initData() {
        //判断用户是否登陆

    }
}
