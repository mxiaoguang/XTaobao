package com.xiaoguang.xtaobao.bean;

import cn.bmob.v3.BmobObject;

/**
 * 购物车
 * Created by 11655 on 2016/10/25.
 */

public class ShopCar  extends BmobObject{
    //商品id
   private String goodId;
    //当前用户
    private String userId;
    //商品的数量
    private int count;

    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
