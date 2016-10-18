package com.xiaoguang.xtaobao.base;

/**
 * Created by 11655 on 2016/10/18.
 */

public interface BaseView<T extends BasePresenter> {
    /**
     * 为视图设置一个控制层
     */
  void setPresenter(T presenter);
}
