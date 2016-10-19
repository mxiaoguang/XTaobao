package com.xiaoguang.xtaobao.presenter;

import com.xiaoguang.xtaobao.contract.IFragPersonalContract;

/**
 * Created by 11655 on 2016/10/19.
 */

public class FragPersonalPresenterImpl implements IFragPersonalContract.IFragPersonalPrensenter {
    private IFragPersonalContract.IFragPersonalView view;
    public FragPersonalPresenterImpl(IFragPersonalContract.IFragPersonalView view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void intData() {

    }
}
