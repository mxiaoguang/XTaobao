package com.xiaoguang.xtaobao.bean;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by 11655 on 2016/10/18.
 */

public class User extends BmobUser {
    //昵称
    private String nickName;
    //头像
    private BmobFile userHead;
    //性别
    private String sex;
    //收货地址的集合
    private List<String> addressLists;
    /**
     * 收藏的商品的Id
     */
    private List<String> loveGoodsIds;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public BmobFile getUserHead() {
        if (userHead == null) {
            userHead = new BmobFile();
        }
        return userHead;
    }

    public void setUserHead(BmobFile userHead) {
        this.userHead = userHead;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public List<String> getAddressLists() {
        if (addressLists == null) {
            addressLists = new ArrayList<>();
        }
        return addressLists;
    }

    public void setAddressLists(List<String> addressLists) {
        this.addressLists = addressLists;
    }

    public List<String> getLoveGoodsIds() {
        if (loveGoodsIds ==null){
            loveGoodsIds = new ArrayList<>();
        }
        return loveGoodsIds;
    }

    public void setLoveGoodsIds(List<String> loveGoodsIds) {
        this.loveGoodsIds = loveGoodsIds;
    }
}
