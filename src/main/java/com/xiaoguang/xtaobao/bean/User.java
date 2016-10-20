package com.xiaoguang.xtaobao.bean;

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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public BmobFile getUserHead() {
        if (userHead==null){
            userHead = new BmobFile();
        }
        return userHead;
    }

    public void setUserHead(BmobFile userHead) {
        this.userHead = userHead;
    }
}
