package com.xiaoguang.xtaobao.contract;

import android.widget.EditText;
import android.widget.TextView;

import com.xiaoguang.xtaobao.base.BasePresenter;
import com.xiaoguang.xtaobao.base.BaseView;
import com.xiaoguang.xtaobao.bean.Goods;

/**
 * 契约类、使view 和 Presenter 之前的方法清晰
 * Created by 11655 on 2016/10/18.
 */

public class IOrdersContract {
   public interface IOrdersView extends BaseView<IOrdersPresenter>{
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
        * @param objectId  订单号
        * @param sum  订单金额
        */
       void jumpActivity(String objectId, double sum);
       /**
        * 将数据显示到控件上
        * @param goods
        */
       void setData(Goods goods);

       EditText getmActOrderEtCount();

       TextView getmActOrdersTvNumMoney();

       TextView getmActOrderTvGoodsMoney();

   }
    public interface IOrdersPresenter extends BasePresenter<IOrdersView>{

        /**
         * 计算总价格
         * @param count 商品数量
         * @param strPrice 商品价格
         */
        void changeSumPrice(int count, String strPrice);

        /**
         * 提交订单
         */
        void submit();
    }
}
