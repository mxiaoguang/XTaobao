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

       /**
        * Toast数据
        * @param msg
        */
       void showMsg(String msg);

       /**
        * 展示一个进度条对话框
        * @param title 标题
        * @param msg 显示的内容
        * @param flag 是否可以取消
        */
       void showLoadingDialog(String title, String msg, boolean flag);

       /**
        * 取消进度条
        */
       void canelLoadingDialog();

       /**
        * activity的跳转  1 为跳转到订单页面  2 为跳转到收藏界面
        */
       void jumpActivity(int type);
   }
    public interface IFragPersonalPresenter extends BasePresenter<IFragPersonalView>{

        /**
         * @param type -1为查询全部订单 0 待付款 1 已付款,待发货 2 已发货，待收货 3已收货, 待评价 4退款 5.取消的订单
         */
        void queryOrders(int type);
    }
}
