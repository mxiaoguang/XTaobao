package com.xiaoguang.xtaobao.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.xiaoguang.xtaobao.R;
import com.xiaoguang.xtaobao.activity.LoginActivity;
import com.xiaoguang.xtaobao.activity.PayActivity;
import com.xiaoguang.xtaobao.base.BaseFragment;
import com.xiaoguang.xtaobao.contract.IFragShopCarContract;
import com.xiaoguang.xtaobao.presenter.FragShopCarPresenterImpl;
import com.xiaoguang.xtaobao.util.LogUtils;
import com.xiaoguang.xtaobao.util.ToastFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 购物车
 * Created by 11655 on 2016/10/19.
 */

public class ShopcarFragment extends BaseFragment implements IFragShopCarContract.IFragShopCarView {

    @BindView(R.id.frag_shop_car_msg)
    ImageView mFragShopCarMsg;
    @BindView(R.id.frag_shopcar_lv)
    SwipeMenuListView mFragShopcarLv;
    @BindView(R.id.frag_shop_car_cb)
    CheckBox mFragShopCarCb;
    @BindView(R.id.frag_shop_car_tv_money)
    TextView mFragShopCarTvMoney;
    @BindView(R.id.frag_shop_car_btn_submit)
    Button mFragShopCarBtnSubmit;
    @BindView(R.id.frag_shopcar_ln)
    RelativeLayout mFragShopcarLn;
    private IFragShopCarContract.IFragShopCarPresenter presenter;
    /**
     * 标志位，标志已经初始化完成
     */

    private boolean isPrepared = false;
    /**
     * 是否已被加载过一次，第二次就不再去请求数据了
     */
    private boolean mHasLoadedOnce;

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        if (!isLogin()) {//判断是否登陆
            startActivityForResult(new Intent(getContext(), LoginActivity.class), 600);
        } else {//已经登陆,查询并加载数据
            presenter.queryDatasFromServer();
        }
    }

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container, boolean b) {
        View rootView = inflater.inflate(R.layout.frag_shopcar, null);
        ButterKnife.bind(this, rootView);
        new FragShopCarPresenterImpl(this);
        isPrepared = true;
        lazyLoad();
        return rootView;
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        presenter.initData();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 600 & requestCode == 200) {
            //执行登陆后的返回操作
            LogUtils.i("myTag", "我被调用了呢");
        }

    }

    @Override
    public void setPresenter(IFragShopCarContract.IFragShopCarPresenter presenter) {
        this.presenter = presenter;
    }

    @OnClick({R.id.frag_shop_car_msg, R.id.frag_shop_car_btn_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.frag_shop_car_msg:
                break;
            case R.id.frag_shop_car_btn_submit:
                //下单支付
                presenter.submit();
                break;
        }
    }

    @Override
    public SwipeMenuListView getmFragShopcarLv() {
        return mFragShopcarLv;
    }

    @Override
    public CheckBox getmFragShopCarCb() {
        return mFragShopCarCb;
    }

    @Override
    public TextView getmFragShopCarTvMoney() {
        return mFragShopCarTvMoney;
    }

    @Override
    public void showMsg(String msg) {
        ToastFactory.getToast(getContext(), msg).show();
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
    public void jumpActivity(String orderId, double sumMoney) {
        //跳转到支付
        Intent intent = new Intent(getContext(), PayActivity.class);
        //将订单编号和订单金额出传递到下一个activity
        intent.putExtra("orderId", orderId);
        intent.putExtra("sumMoney", sumMoney);
        startActivity(intent);
    }
}
