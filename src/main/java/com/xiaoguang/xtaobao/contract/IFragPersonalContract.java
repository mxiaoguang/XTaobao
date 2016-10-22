package com.xiaoguang.xtaobao.contract;

import android.widget.GridView;

import com.xiaoguang.xtaobao.base.BasePresenter;
import com.xiaoguang.xtaobao.base.BaseView;

/**
 * 契约类、使view 和 Presenter 之前的方法清晰
 * Created by 11655 on 2016/10/18.
 */

public class IFragPersonalContract {
   public interface IFragPersonalView extends BaseView<IFragPersonalPresenter>{

       GridView getmFragPersonalGvBottom();

       GridView getmFragPersonalGvCenter();
   }
    public interface IFragPersonalPresenter extends BasePresenter<IFragPersonalView>{

    }
}
