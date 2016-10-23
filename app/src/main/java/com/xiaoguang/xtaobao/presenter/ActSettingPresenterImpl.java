package com.xiaoguang.xtaobao.presenter;

import com.xiaoguang.xtaobao.contract.ISettingContract;

import cn.bmob.v3.BmobUser;

/**
 * Created by 11655 on 2016/10/21.
 */

public class ActSettingPresenterImpl implements ISettingContract.ISettingPresenter {

    private ISettingContract.ISettingView view;

    public ActSettingPresenterImpl(ISettingContract.ISettingView view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void logingOut() {
        BmobUser.logOut();   //清除缓存用户对象
        BmobUser currentUser = BmobUser.getCurrentUser(); // 现在的currentUser是null了
    }
}
