package com.xiaoguang.xtaobao.bean;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * 订单
 * Created by 11655 on 2016/10/24.
 */

public class Orders extends BmobObject {
    //商品id的集合
    private List<String> goodIds;
    //用户id
    private String userId;
    //订单状态 0为待付款 1为待发货 2为待收货3位取消订单
    private int ordersState;
    //订单的金额
    private double oerdersMoney;
    //订单商品的数量
    private int goodsCount;

    public List<String> getGoodIds() {
        if (goodIds==null){
            goodIds = new ArrayList<>();
        }
        return goodIds;
    }

    public void setGoodIds(List<String> goodIds) {
        this.goodIds = goodIds;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getOrdersState() {
        return ordersState;
    }

    public void setOrdersState(int ordersState) {
        this.ordersState = ordersState;
    }

    public double getOerdersMoney() {
        return oerdersMoney;
    }

    public void setOerdersMoney(double oerdersMoney) {
        this.oerdersMoney = oerdersMoney;
    }

    public int getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(int goodsCount) {
        this.goodsCount = goodsCount;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "goodIds=" + goodIds +
                ", userId='" + userId + '\'' +
                ", ordersState=" + ordersState +
                ", oerdersMoney=" + oerdersMoney +
                ", goodsCount=" + goodsCount +
                '}';
    }
}

