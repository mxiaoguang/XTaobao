package com.xiaoguang.xtaobao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xiaoguang.xtaobao.R;
import com.xiaoguang.xtaobao.application.CustomApplcation;
import com.xiaoguang.xtaobao.base.BaseActivity;
import com.xiaoguang.xtaobao.bean.Goods;
import com.xiaoguang.xtaobao.contract.IOrdersContract;
import com.xiaoguang.xtaobao.presenter.ActOrdersPresenterImpl;
import com.xiaoguang.xtaobao.util.ToastFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 11655 on 2016/10/24.
 */

public class OrdersActivity extends BaseActivity implements IOrdersContract.IOrdersView {

    //获取控件
    @BindView(R.id.act_orders_iv_back)
    ImageView mActOrdersIvBack;
    @BindView(R.id.act_order_tv_top)
    TextView mActOrderTvTop;
    @BindView(R.id.act_order_tv_shouhuoName)
    TextView mActOrderTvShouhuoName;
    @BindView(R.id.act_order_tv_shouhuoPhone)
    TextView mActOrderTvShouhuoPhone;
    @BindView(R.id.act_order_tv_shouhuo_address)
    TextView mActOrderTvShouhuoAddress;
    @BindView(R.id.act_order_iv_goods_img)
    ImageView mActOrderIvGoodsImg;
    @BindView(R.id.act_order_tv_goods_name)
    TextView mActOrderTvGoodsName;
    @BindView(R.id.act_order_tv_goods_money)
    TextView mActOrderTvGoodsMoney;
    @BindView(R.id.act_order_ln)
    LinearLayout mActOrderLn;
    @BindView(R.id.act_order_iv_line)
    ImageView mActOrderIvLine;
    @BindView(R.id.act_order_btn_add)
    Button mActOrderBtnAdd;
    @BindView(R.id.act_order_et_count)
    EditText mActOrderEtCount;
    @BindView(R.id.act_order_btn_jian)
    Button mActOrderBtnJian;
    @BindView(R.id.act_order_tv_shouhou)
    TextView mActOrderTvShouhou;
    @BindView(R.id.act_order_rl_shouhou)
    RelativeLayout mActOrderRlShouhou;
    @BindView(R.id.act_order_tv_peisong)
    TextView mActOrderTvPeisong;
    @BindView(R.id.act_order_rl_peisong)
    RelativeLayout mActOrderRlPeisong;
    @BindView(R.id.act_order_tv_yunfei)
    TextView mActOrderTvYunfei;
    @BindView(R.id.act_order_rl_yunfei)
    RelativeLayout mActOrderRlYunfei;
    @BindView(R.id.act_order_tv_liuyan)
    TextView mActOrderTvLiuyan;
    @BindView(R.id.act_order_rl_liuyan)
    RelativeLayout mActOrderRlLiuyan;
    @BindView(R.id.act_orders_tv_num_money)
    TextView mActOrdersTvNumMoney;
    @BindView(R.id.act_orders_btn_submit)
    Button mActOrdersBtnSubmit;
    private IOrdersContract.IOrdersPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_orders);
        ButterKnife.bind(this);
        new ActOrdersPresenterImpl(this);
        presenter.initData();

    }

    @OnClick({R.id.act_orders_iv_back, R.id.act_order_btn_add, R.id.act_order_btn_jian, R.id.act_order_rl_shouhou, R.id.act_order_rl_peisong, R.id.act_order_rl_yunfei, R.id.act_order_rl_liuyan, R.id.act_orders_btn_submit})
    public void onClick(View view) {
        //数量
        int count = Integer.parseInt(mActOrderEtCount.getText().toString());
        //获取价格
        String strPrice = mActOrderTvGoodsMoney.getText().toString();
        switch (view.getId()) {
            case R.id.act_orders_iv_back:
                finish();
                break;
            case R.id.act_order_btn_add:
                count++;
                mActOrderEtCount.setText(count+"");
                presenter.changeSumPrice(count,strPrice);
                break;
            case R.id.act_order_btn_jian:
                if (count>0){
                    count--;
                    mActOrderEtCount.setText(count+"");
                    presenter.changeSumPrice(count,strPrice);
                }
                break;
            case R.id.act_order_rl_shouhou:
                showMsg("暂时无法修改");
                break;
            case R.id.act_order_rl_peisong:
                showMsg("暂时无法修改");
                break;
            case R.id.act_order_rl_yunfei:
                showMsg("暂时无法修改");
                break;
            case R.id.act_order_rl_liuyan:
                showMsg("暂时无法修改");
                break;
            case R.id.act_orders_btn_submit:
                //判断数量是否为空,为空则默认为1
                if (mActOrderEtCount.getText().toString().isEmpty()){
                    mActOrderEtCount.setText("1");
                }
                presenter.submit();
                break;
        }
    }
    @Override
    public void setData(Goods goods) {
        //设置收货人
        mActOrderTvShouhuoName.setText(CustomApplcation.getInstance().getCurrentUser().getNickName());
        //设置默认收货地址
        mActOrderTvShouhuoAddress.setText("收货地址："+CustomApplcation.getInstance().getCurrentUser().
                getAddressLists().get(0));
        //设置电话号码(默认为注册时的电话号码)
        mActOrderTvShouhuoPhone.setText(CustomApplcation.getInstance().getCurrentUser().getMobilePhoneNumber()+"");
        //设置商品图片(默认为第一张图片)
        Picasso.with(this).load(goods.getGoodsImgs().get(0).getUrl()).into(mActOrderIvGoodsImg);
        //设置商品标题
        mActOrderTvGoodsName.setText(goods.getGoodsName());
        //设置价格
        mActOrderTvGoodsMoney.setText("¥ "+goods.getGoodsPrice());
        //设置数量默认为1
        mActOrderEtCount.setText("1");
        //设置总结金额
        mActOrdersTvNumMoney.setText("¥ "+goods.getGoodsPrice());
    }
    @Override
    public void jumpActivity(String orderId,double sumMoney) {
        //跳转到支付
        Intent intent = new Intent(this,PayActivity.class);
        //将订单编号和订单金额出传递到下一个activity
        intent.putExtra("orderId",orderId);
        intent.putExtra("sumMoney",sumMoney);
        startActivity(intent);
    }
    @Override
    public void showMsg(String msg) {
        ToastFactory.getToast(this, msg).show();
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
    public void setPresenter(IOrdersContract.IOrdersPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public EditText getmActOrderEtCount(){
        return mActOrderEtCount;
    }
    @Override
    public TextView getmActOrdersTvNumMoney(){
        return mActOrdersTvNumMoney;
    }
    @Override
    public TextView getmActOrderTvGoodsMoney() {
        return mActOrderTvGoodsMoney;
    }
}
