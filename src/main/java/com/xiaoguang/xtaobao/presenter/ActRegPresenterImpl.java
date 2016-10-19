package com.xiaoguang.xtaobao.presenter;

import com.xiaoguang.xtaobao.contract.IRegisterContract;

/**
 * Created by 11655 on 2016/10/19.
 */

public class ActRegPresenterImpl implements IRegisterContract.IIRegisterPrensenter {

    private IRegisterContract.IIRegisterView view;
    public ActRegPresenterImpl(IRegisterContract.IIRegisterView view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void intData() {

    }
}
