package com.xiaoguang.xtaobao.contract;

import com.xiaoguang.xtaobao.base.BasePresenter;
import com.xiaoguang.xtaobao.base.BaseView;
import com.xiaoguang.xtaobao.widget.XListView;

/**
 * 契约类、使view 和 Presenter 之前的方法清晰
 * Created by 11655 on 2016/10/18.
 */

public class IOrdersResultContract {
    public interface IOrdersResultView extends BaseView<IOrdersResultPresenter> {
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
         * activity的跳转
         *
         * @param objectId
         * @param oerdersMoney
         */
        void jumpActivity(String objectId, double oerdersMoney);

        /**
         * 评论的弹出框
         *
         * @param goodsObjectId 选中的商品id
         */
        void showAlertDialog(String goodsObjectId);

        /**
         * 获取xlistview 控件
         *
         * @return
         */
        XListView getmActOrdersResultXlv();

    }

    public interface IOrdersResultPresenter extends BasePresenter<IOrdersResultView> {

        /**
         * 发布评论
         *
         * @param text    评论内容
         * @param goodsId 商品id
         * @param userId  评论人的id
         */
        void sendDiscuss(String text, String goodsId, String userId);

        /**
         * 更新订单详情
         *
         * @param i        -1为查询全部订单 0 待付款 1 已付款,待发货 2 已发货，待收货 3已收货, 待评价 4退款 5 取消的订单
         * @param ordersId 订单id
         */
        void updateOrders(int i, String ordersId);

        /**
         * 评价商品
         *
         * @param goodsObjectId 选中商品的id
         */
        void pingJia(String goodsObjectId);

        /**
         * 支付订单
         *
         * @param objectId     订单编号
         * @param oerdersMoney 订单金额
         */
        void pay(String objectId, double oerdersMoney);
    }
}
