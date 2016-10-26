package com.xiaoguang.xtaobao.contract;

import android.widget.GridView;

import com.xiaoguang.xtaobao.base.BasePresenter;
import com.xiaoguang.xtaobao.base.BaseView;

/**
 * 契约类、使view 和 Presenter 之前的方法清晰
 * Created by 11655 on 2016/10/18.
 */

public class ILoveGoodsContract {
   public interface ILoveGoodsView extends BaseView<ILoveGoodsPresenter>{
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

       GridView getmActGoodsResultGv();
   }
    public interface ILoveGoodsPresenter extends BasePresenter<ILoveGoodsView>{

    }
}
