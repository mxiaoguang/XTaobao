package com.xiaoguang.xtaobao.contract;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.xiaoguang.xtaobao.base.BasePresenter;
import com.xiaoguang.xtaobao.base.BaseView;

/**
 * 契约类、使view 和 Presenter 之前的方法清晰
 * Created by 11655 on 2016/10/18.
 */

public class IFragWeContract {
   public interface IFragWeView extends BaseView<IFragWePresenter>{
       /**
        * 获取ViewPager对象
        * @return ViewPager对象
        */
       ViewPager getmFragWeVp();

       /**
        * 获取FragmentManger对象
        * @return
        */
       FragmentManager getManager();
   }
    public interface IFragWePresenter extends BasePresenter<IFragWeView>{

    }
}
