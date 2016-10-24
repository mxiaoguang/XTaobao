package com.xiaoguang.xtaobao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaoguang.xtaobao.R;
import com.xiaoguang.xtaobao.base.BaseActivity;
import com.xiaoguang.xtaobao.contract.IGoodsTypeContract;
import com.xiaoguang.xtaobao.presenter.ActGoodsTypePresenterImpl;
import com.xiaoguang.xtaobao.util.ToastFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 商品类别
 * Created by 11655 on 2016/10/23.
 */

public class GoodsTypeActivity extends BaseActivity implements IGoodsTypeContract.IGoodsTypeView {

    @BindView(R.id.act_goods_type_iv_back)
    ImageView mActGoodsTypeIvBack;
    @BindView(R.id.act_goods_type_tv_menu)
    TextView mActGoodsTypeTvMenu;
    @BindView(R.id.act_goods_type_gv)
    GridView mActGoodsTypeGv;
    @BindView(R.id.act_goods_type_ln_content)
    LinearLayout mActGoodsTypeLnContent;
    private IGoodsTypeContract.IGoodsTypePresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_goods_type);
        ButterKnife.bind(this);
        new ActGoodsTypePresenterImpl(this);
        presenter.initData();
    }

    @Override
    public void showMsg(String msg) {
        ToastFactory.getToast(this,msg).show();
    }

    @Override
    public void showLoadingDialog(String title, String msg, boolean flag) {
        super.showProcessDialog(title, msg, flag);
    }

    @Override
    public void canelLoadingDialog() {
        super.dismissProcessDialog();
    }

    @Override
    public void jumpActivity(String datas) {
        //跳转到商品展示的页面
        Intent intent = new Intent(this,GoodsResultActivity.class);
        intent.putExtra("datas",datas);
        startActivity(intent);
    }

    @Override
    public void setPresenter(IGoodsTypeContract.IGoodsTypePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public GridView getmActGoodsTypeGv() {
        return mActGoodsTypeGv;
    }

    @Override
    public LinearLayout getmActGoodsTypeLnContent() {
        return mActGoodsTypeLnContent;
    }

    @OnClick({R.id.act_goods_type_iv_back, R.id.act_goods_type_tv_menu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.act_goods_type_iv_back:
                finish();
                break;
            case R.id.act_goods_type_tv_menu:
                break;
        }
    }
}
