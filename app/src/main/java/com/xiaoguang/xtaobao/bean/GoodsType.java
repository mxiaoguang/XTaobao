package com.xiaoguang.xtaobao.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 商品类别
 * Created by 11655 on 2016/10/23.
 */

public class GoodsType extends BmobObject {
    //商品类别的名称
    private String typeName;
    //商品类别的图片
    private BmobFile typeImg;

    public BmobFile getTypeImg() {
        return typeImg;
    }

    public void setTypeImg(BmobFile typeImg) {
        this.typeImg = typeImg;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
