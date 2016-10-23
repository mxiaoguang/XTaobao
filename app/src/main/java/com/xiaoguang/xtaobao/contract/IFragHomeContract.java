package com.xiaoguang.xtaobao.contract;

import android.widget.EditText;
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
    public interface IFragHomeView extends BaseView<IFragHomePresenter> {

        /**
         * Toast数据
         *
         * @param msg
         */
        void showMsg(String msg);

        /**
         * 展示一个进度条对话框
         *
         * @param title 标题
         * @param msg   显示的内容
         * @param flag  是否可以取消
         */
        void showLoadingDialog(String title, String msg, boolean flag);

        /**
         * 取消进度条
         */
        void canelLoadingDialog();

        /**
         * 获取显示广告的ViewPager
         *
         * @return viewpager 对象
         */
        RollPagerView getmActHomeVpAd();

        /**
         * 获取显示的
         *
         * @return
         */
        GridView getGridViewSort();

        GridView getGridViewContent();

        MarqueeView getMarqueeViewTop();

        /**
         * 跳转Activity
         *
         * @param type 类型 1-9
         */
        void jumpActivity(int type, String datas);

        EditText getmFragHomeEtSearch();
    }

    public interface IFragHomePresenter extends BasePresenter<IFragHomeView> {

    }
}
