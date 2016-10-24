package com.xiaoguang.xtaobao.bean;

import cn.bmob.v3.BmobObject;

/**
 * 评论的bean对象
 * Created by 11655 on 2016/10/14.
 */

public class Discuss extends BmobObject {
    //商品id
    private String goodsId;
    //评论内容
    private String discussText;
    //评论的人
    private String discussUserId;
    //评论时间
    private String discussTime;
    public String getDiscussText() {
        return discussText;
    }

    public void setDiscussText(String discussText) {
        this.discussText = discussText;
    }

    public String getDiscussUserId() {
        return discussUserId;
    }

    public void setDiscussUserId(String discussUserId) {
        this.discussUserId = discussUserId;
    }

    public String getDiscussTime() {
        return discussTime;
    }
    public void setDiscussTime(String discussTime) {
        this.discussTime = discussTime;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }
}
