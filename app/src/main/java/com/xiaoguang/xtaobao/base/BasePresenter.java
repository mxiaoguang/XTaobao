package com.xiaoguang.xtaobao.base;

/**
 * Created by 11655 on 2016/10/18.
 */

public interface BasePresenter<T extends BaseView> {

    String TAG = "myTag";
    /**
     * 初始化操作
     */
    void initData();


}
