package com.xiaoguang.xtaobao.presenter;

import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.xiaoguang.xtaobao.application.CustomApplcation;
import com.xiaoguang.xtaobao.bean.Goods;
import com.xiaoguang.xtaobao.bean.Orders;
import com.xiaoguang.xtaobao.contract.IOrdersContract;
import com.xiaoguang.xtaobao.util.LogUtils;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by 11655 on 2016/10/24.
 */

public class ActOrdersPresenterImpl implements IOrdersContract.IOrdersPresenter {
    private IOrdersContract.IOrdersView view;
    //声明一个控件对象
    EditText mEtCount;
    TextView mTvPrice,mTvMoney;
    private Goods goods;

    public ActOrdersPresenterImpl(IOrdersContract.IOrdersView view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void initData() {
        //获取控件
        mEtCount = view.getmActOrderEtCount();
        mTvPrice =view.getmActOrderTvGoodsMoney();
        mTvMoney = view.getmActOrdersTvNumMoney();
        //获取从上一个Activity传递过来的数据
       goods = (Goods) CustomApplcation.getDatas("goods",false);
        view.setData(goods);
        //为按钮设置一个监听事件
        initEvent();
    }

    /**
     * 为按钮设置点击事件
     */
    private void initEvent() {
        mEtCount.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode==KeyEvent.KEYCODE_ENTER){
                    changeSumPrice(Integer.parseInt(mEtCount.getText().toString()),mTvPrice.getText().toString());
                }
                return false;
            }
        });
    }

    @Override
    public void changeSumPrice(int count, String strPrice) {
        //截取文本内容
        String price  = strPrice.substring(2);
        //计算总价格
        double dprice = Double.parseDouble(price);
        double sum = dprice*count;
        //设置需要小数点两位数
        double sum1 = (double)Math.round(sum*100)/100;
        mTvMoney.setText("¥ "+sum1);
    }
    @Override
    public void submit() {
        //提交订单
        //获取数据
        String countStr = mEtCount.getText().toString();
        String sumMoneyStr = mTvMoney.getText().toString();
        String sumMoney  =sumMoneyStr.substring(2);
        double dSumMoney = Double.parseDouble(sumMoney);
        int count = Integer.parseInt(countStr);
        //设置需要小数点两位数
        final double sum = (double)Math.round(dSumMoney*100)/100;
        //设置数据
        Orders orders = new Orders();
        orders.add("goodIds",goods.getObjectId());
        orders.setUserId(CustomApplcation.getInstance().getCurrentUser().getObjectId());
        orders.setOrdersState(0);//设置订单状态为代付款0
        orders.setOerdersMoney(sum);
        orders.setGoodsCount(count);
        orders.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null){
                    view.showMsg("下单成功,将要跳转到支付界面");
                    //将直接跳转到支付页面,将订单号和金额传递到支付activity
                    view.jumpActivity(objectId,sum);
                    LogUtils.i(TAG,"下单成功!订单编号为:"+objectId);
                }else {
                    view.showMsg("下单失败!"+e.getLocalizedMessage());
                    LogUtils.i(TAG,"下单失败"+e.toString());
                }
            }
        });
    }
}
