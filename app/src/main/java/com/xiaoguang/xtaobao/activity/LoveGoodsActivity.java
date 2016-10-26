package com.xiaoguang.xtaobao.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoguang.xtaobao.R;
import com.xiaoguang.xtaobao.base.BaseActivity;
import com.xiaoguang.xtaobao.contract.ILoveGoodsContract;
import com.xiaoguang.xtaobao.presenter.ActLoveGoodsPresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 收藏商品的页面
 * Created by 11655 on 2016/10/26.
 */

public class LoveGoodsActivity extends BaseActivity implements ILoveGoodsContract.ILoveGoodsView {

    @BindView(R.id.act_goods_result_iv_back)
    ImageView mActGoodsResultIvBack;
    @BindView(R.id.act_goods_result_tv_menu)
    TextView mActGoodsResultTvMenu;
    @BindView(R.id.act_goods_result_gv)
    GridView mActGoodsResultGv;
    private ILoveGoodsContract.ILoveGoodsPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_goods_result);
        ButterKnife.bind(this);
        new ActLoveGoodsPresenterImpl(this);
        presenter.initData();
    }

    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void showLoadingDialog(String title, String msg, boolean flag) {

    }

    @Override
    public void canelLoadingDialog() {

    }

    @Override
    public void jumpActivity() {

    }

    @Override
    public void setPresenter(ILoveGoodsContract.ILoveGoodsPresenter presenter) {
        this.presenter = presenter;
    }

    @OnClick({R.id.act_goods_result_iv_back, R.id.act_goods_result_tv_menu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.act_goods_result_iv_back:
                finish();
                break;
            case R.id.act_goods_result_tv_menu:
                break;
        }
    }
    @Override
    public GridView getmActGoodsResultGv() {
        return mActGoodsResultGv;
    }
}
