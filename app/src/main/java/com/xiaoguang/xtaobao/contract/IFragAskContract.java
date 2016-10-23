package com.xiaoguang.xtaobao.contract;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.xiaoguang.xtaobao.base.BasePresenter;
import com.xiaoguang.xtaobao.base.BaseView;

/**
 * 契约类、使view 和 Presenter 之前的方法清晰
 * Created by 11655 on 2016/10/18.
 */

public class IFragAskContract {
   public interface IFragAskView extends BaseView<IFragAskPresenter>{
       /**
        * 获取当前Fragment中的viewpager
        * @return
        */
       ViewPager getmFragAskVp();

       /**
        * 获取当前Fragment中的FragmetManager
        * @return
        */
       FragmentManager getManager();
   }
    public interface IFragAskPresenter extends BasePresenter<IFragAskView>{
    }
}
