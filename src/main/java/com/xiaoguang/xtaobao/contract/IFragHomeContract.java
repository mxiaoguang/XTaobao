package com.xiaoguang.xtaobao.contract;

import android.widget.GridView;

import com.jude.rollviewpager.RollPagerView;
import com.sunfusheng.marqueeview.MarqueeView;
import com.xiaoguang.xtaobao.base.BasePresenter;
import com.xiaoguang.xtaobao.base.BaseView;

/**
 * 契约类、使view 和 Presenter 之前的方法清晰
 * Created by 11655 on 2016/10/18.
 */

public class IFragHomeContract {
   public interface IFragHomeView extends BaseView<IFragHomePrensenter>{
       /**
        * 获取显示广告的ViewPager
        * @return viewpager 对象
        */
       RollPagerView getmActHomeVpAd();

       GridView getGridViewSort();

       MarqueeView getMarqueeViewTop();
   }
    public interface IFragHomePrensenter extends BasePresenter<IFragHomeView>{

    }
}
