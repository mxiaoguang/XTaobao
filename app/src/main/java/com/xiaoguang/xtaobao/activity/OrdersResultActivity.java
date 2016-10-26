package com.xiaoguang.xtaobao.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoguang.xtaobao.R;
import com.xiaoguang.xtaobao.base.BaseActivity;
import com.xiaoguang.xtaobao.contract.IOrdersResultContract;
import com.xiaoguang.xtaobao.presenter.ActOrderResultPresenterImpl;
import com.xiaoguang.xtaobao.widget.XListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 11655 on 2016/10/26.
 */

public class OrdersResultActivity extends BaseActivity implements IOrdersResultContract.IOrdersResultView{
    @BindView(R.id.act_order_result_iv_back)
    ImageView mActOrderResultIvBack;
    @BindView(R.id.act_orders_result_tv_menu)
    TextView mActOrdersResultTvMenu;
    @BindView(R.id.act_orders_result_xlv)
    XListView mActOrdersResultXlv;
    private IOrdersResultContract.IOrdersResultPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_orders_result);
        ButterKnife.bind(this);
        new ActOrderResultPresenterImpl(this);
        presenter.initData();
    }

    @OnClick({R.id.act_order_result_iv_back, R.id.act_orders_result_tv_menu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.act_order_result_iv_back:
                break;
            case R.id.act_orders_result_tv_menu:
                break;
        }
    }

    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void showLoadingDialog(String title, String msg, boolean flag) {
        showLoadingDialog(title, msg, flag);
    }

    @Override
    public void canelLoadingDialog() {
        dismissProcessDialog();
    }

    @Override
    public void jumpActivity() {

    }

    @Override
    public void setPresenter(IOrdersResultContract.IOrdersResultPresenter presenter) {
        this.presenter = presenter;
    }
    @Override
    public XListView getmActOrdersResultXlv() {
        return mActOrdersResultXlv;
    }
}
