package com.xiaoguang.xtaobao.contract;

import android.widget.TextView;

import com.jude.rollviewpager.RollPagerView;
import com.xiaoguang.xtaobao.base.BasePresenter;
import com.xiaoguang.xtaobao.base.BaseView;
import com.xiaoguang.xtaobao.widget.XListView;

/**
 * 契约类、使view 和 Presenter 之前的方法清晰
 * Created by 11655 on 2016/10/18.
 */

public class IGoodsDetialsContract {
   public interface IGoodsDetialsView extends BaseView<IGoodsDetialsPresenter>{
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

       RollPagerView getmActGoodsDetailsRollVpAd();

       TextView getmActGoodsDetailsTvGoodsName();

       XListView getmActGoodsDetailsXlv();

       TextView getmActGoodsDetailsTvMoney();
   }
    public interface IGoodsDetialsPresenter extends BasePresenter<IGoodsDetialsView>{

    }
}
