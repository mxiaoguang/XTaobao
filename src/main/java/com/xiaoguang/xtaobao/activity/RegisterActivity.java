package com.xiaoguang.xtaobao.activity;

import android.content.Intent;

import com.xiaoguang.xtaobao.base.BaseActivity;
import com.xiaoguang.xtaobao.contract.IRegisterContract;
import com.xiaoguang.xtaobao.presenter.ActRegPresenterImpl;

/**
 * Created by 11655 on 2016/10/19.
 */

public class RegisterActivity extends BaseActivity implements IRegisterContract.IIRegisterView{
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        new ActRegPresenterImpl(this);
    }

    private IRegisterContract.IIRegisterPrensenter presenter ;

    @Override
    public void setPresenter(IRegisterContract.IIRegisterPrensenter presenter) {
        this.presenter = presenter;
    }
}
