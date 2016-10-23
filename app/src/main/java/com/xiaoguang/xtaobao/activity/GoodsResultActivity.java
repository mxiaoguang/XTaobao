package com.xiaoguang.xtaobao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoguang.xtaobao.R;
import com.xiaoguang.xtaobao.base.BaseActivity;
import com.xiaoguang.xtaobao.contract.IGoodsResultContract;
import com.xiaoguang.xtaobao.presenter.ActGoodsResultPresenterImpl;
import com.xiaoguang.xtaobao.util.ToastFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 查询结果显示的页面
 * Created by 11655 on 2016/10/23.
 */

public class GoodsResultActivity extends BaseActivity implements IGoodsResultContract.IGoodsResultView {

    //获取控件
    @BindView(R.id.act_goods_result_iv_back)
    ImageView mActGoodsResultIvBack;
    @BindView(R.id.act_goods_result_tv_menu)
    TextView mActGoodsResultTvMenu;
    @BindView(R.id.act_goods_result_gv)
    GridView mActGoodsResultGv;
    private IGoodsResultContract.IGoodsResultPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_goods_result);
        ButterKnife.bind(this);
        new ActGoodsResultPresenterImpl(this);
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
    public void jumpActivity() {
        startActivity(new Intent(this,GoodsDetailsActivity.class));
    }

    @Override
    public void setPresenter(IGoodsResultContract.IGoodsResultPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public String getDatas() {
        //获取intent对象
        Intent intent = getIntent();
        //获取传递过来的数据
        return intent.getStringExtra("datas");
    }
    @Override
    public GridView getmActGoodsResultGv() {
        return mActGoodsResultGv;
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
}
