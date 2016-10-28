package com.xiaoguang.xtaobao.contract;

import android.widget.CheckBox;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.xiaoguang.xtaobao.base.BasePresenter;
import com.xiaoguang.xtaobao.base.BaseView;

/**
 * 契约类、使view 和 Presenter 之前的方法清晰
 * Created by 11655 on 2016/10/18.
 */

public class IFragShopCarContract {
   public interface IFragShopCarView extends BaseView<IFragShopCarPresenter>{

       SwipeMenuListView getmFragShopcarLv();

       CheckBox getmFragShopCarCb();

       TextView getmFragShopCarTvMoney();

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
   }
    public interface IFragShopCarPresenter extends BasePresenter<IFragShopCarView>{
        void queryDatasFromServer();

        /**
         * 更改文本呢文字
         * @param count 数量
         * @param price 价格
         */

        void changeMoney(int count, double price);

        /**
         * 下单支付
         */
        void submit();

        /**
         * 保存选中商品的id
         * @param goodId
         */
        void saveGoodIds( String goodId);

        /**
         * 移除选中商品的id
         */
        void removeGoodIds(String goodId);

        /**
         * 保存选中的购物车的id
         * @param objectId
         */
        void saveShopCarIds(String objectId);

        /**
         * 移除取消购物车的id
         * @param objectId
         */
        void removShopCarIds(String objectId);
    }
}
