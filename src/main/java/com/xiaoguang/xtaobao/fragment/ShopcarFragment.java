package com.xiaoguang.xtaobao.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaoguang.xtaobao.R;
import com.xiaoguang.xtaobao.activity.LoginActivity;
import com.xiaoguang.xtaobao.base.BaseFragment;
import com.xiaoguang.xtaobao.contract.IFragShopCarContract;
import com.xiaoguang.xtaobao.presenter.FragShopCarPresenterImpl;

/**
 * 购物车
 * Created by 11655 on 2016/10/19.
 */

public class ShopcarFragment extends BaseFragment implements IFragShopCarContract.IFragShopCarView {

    private IFragShopCarContract.IFragShopCarPrensenter presenter;
    /**
     * 标志位，标志已经初始化完成
     */

    private boolean isPrepared;
    /**
     * 是否已被加载过一次，第二次就不再去请求数据了
     */
    private boolean mHasLoadedOnce;

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        if (!isLogin()) {
            startActivityForResult(new Intent(getContext(), LoginActivity.class),600);
        } else {//设置数据

        }
    }

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container, boolean b) {
        isPrepared = true;
        lazyLoad();
        return inflater.inflate(R.layout.frag_shopcar, null);
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        new FragShopCarPresenterImpl(this);
        presenter.intData();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==600&requestCode==200){
            //执行登陆后的返回操作
        }

    }

    @Override
    public void setPresenter(IFragShopCarContract.IFragShopCarPrensenter presenter) {
        this.presenter = presenter;
    }
}
