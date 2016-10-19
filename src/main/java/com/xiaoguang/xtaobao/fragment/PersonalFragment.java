package com.xiaoguang.xtaobao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaoguang.xtaobao.R;
import com.xiaoguang.xtaobao.base.BaseFragment;
import com.xiaoguang.xtaobao.contract.IFragPersonalContract;
import com.xiaoguang.xtaobao.presenter.FragPersonalPresenterImpl;

/**
 * 我的淘宝
 * Created by 11655 on 2016/10/19.
 */

public class PersonalFragment extends BaseFragment implements IFragPersonalContract.IFragPersonalView {
    private IFragPersonalContract.IFragPersonalPrensenter presenter;

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container, boolean b) {
        return inflater.inflate(R.layout.frag_personal,null);
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        new FragPersonalPresenterImpl(this);
    }

    @Override
    public void setPresenter(IFragPersonalContract.IFragPersonalPrensenter presenter) {
        this.presenter = presenter;
    }
}
