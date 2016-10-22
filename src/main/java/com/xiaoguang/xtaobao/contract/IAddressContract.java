package com.xiaoguang.xtaobao.contract;

import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.xiaoguang.xtaobao.base.BasePresenter;
import com.xiaoguang.xtaobao.base.BaseView;

/**
 * 契约类、使view 和 Presenter 之前的方法清晰
 * Created by 11655 on 2016/10/18.
 */

public class IAddressContract {
   public interface IAddressView extends BaseView<IAddressPresenter>{
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
        * activity的跳转
        */
       void jumpActivity();

       SwipeMenuListView getmActAddressLv();

       /**
        * 弹出提示框
        * @param title 标题
        * @param msg 内容
        * @param flag 标记是否可以取消
        */
       void showAlerDialog(String title, String  msg, boolean flag);
   }
    public interface IAddressPresenter extends BasePresenter<IAddressView>{
        /**
         * 更新用户的收货地址
         * @param text
         * @param type 1 为新增   2
         */
        void updateAddress(String text,int type);

    }
}
