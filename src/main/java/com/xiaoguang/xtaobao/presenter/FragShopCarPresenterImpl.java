package com.xiaoguang.xtaobao.presenter;

import com.xiaoguang.xtaobao.contract.IFragShopCarContract;

/**
 * Created by 11655 on 2016/10/19.
 */

public class FragShopCarPresenterImpl implements IFragShopCarContract.IFragShopCarPrensenter {

    private IFragShopCarContract.IFragShopCarView view;
    public FragShopCarPresenterImpl(IFragShopCarContract.IFragShopCarView view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void intData() {
        //判断用户是否登陆

    }
}
