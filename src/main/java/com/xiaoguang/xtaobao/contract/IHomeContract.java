package com.xiaoguang.xtaobao.contract;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.xiaoguang.xtaobao.base.BasePresenter;
import com.xiaoguang.xtaobao.base.BaseView;

/**
 * 契约类、使view 和 Presenter 之前的方法清晰
 * Created by 11655 on 2016/10/18.
 */

public class IHomeContract {
    public interface IHomeView extends BaseView<IHomePresenter>{

        /**
         * 获取显示内容的Viewpager
         * @return viewpager 对象
         */
        ViewPager getmActHomeVpContent();

        /**
         * 获取当前activity的Fragment管理器
         * @return  Fragment管理器
         */
        FragmentManager getManager();
    }
    public interface IHomePresenter extends BasePresenter<IHomeView>{

    }
}
