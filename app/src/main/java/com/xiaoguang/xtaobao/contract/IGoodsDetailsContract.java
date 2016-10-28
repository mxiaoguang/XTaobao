package com.xiaoguang.xtaobao.contract;

import android.widget.Button;
import android.widget.TextView;

import com.jude.rollviewpager.RollPagerView;
import com.xiaoguang.xtaobao.base.BasePresenter;
import com.xiaoguang.xtaobao.base.BaseView;
import com.xiaoguang.xtaobao.widget.XListView;

import cn.bmob.v3.exception.BmobException;

/**
 * 契约类、使view 和 Presenter 之前的方法清晰
 * Created by 11655 on 2016/10/18.
 */

public class IGoodsDetailsContract {
   public interface IGoodsDetailsView extends BaseView<IGoodsDetailsPresenter>{
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

       /**
        * 跳转到登陆页面
        */
       void jumpLogin();

       RollPagerView getmActGoodsDetailsRollVpAd();

       TextView getmActGoodsDetailsTvGoodsName();

       XListView getmActGoodsDetailsXlv();

       TextView getmActGoodsDetailsTvMoney();

       Button getmActGoodsDetailsBtnShoucang();

   }
    public interface IGoodsDetailsPresenter extends BasePresenter<IGoodsDetailsView>{
        /**
         * 查询信息成功的回调方法
         */
        void queryUserSuccess();

        /**
         * 查询信息失败的回调方法
         * @param e
         */
        void queryUseError(BmobException e);

        /**
         * 加入购物车
         */
        void joinShopCar();

        /**
         * 分享到微信
         * @param type 1:分享到好友列表 2 分享到朋友圈
         */
        void shareWXAPP(int type);
    }
}
