package com.xiaoguang.xtaobao.contract;

import android.net.Uri;
import android.widget.TextView;

import com.xiaoguang.xtaobao.base.BasePresenter;
import com.xiaoguang.xtaobao.base.BaseView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 契约类、使view 和 Presenter 之前的方法清晰
 * Created by 11655 on 2016/10/18.
 */

public class IPersiDetialsContract {
   public interface IPersiDetialsView extends BaseView<IPersiDetialsPresenter>{

       CircleImageView getmActPersonDetailsIvHead();

       TextView getmActPersonDetailsTvHuiyanName();

       TextView getmActPersonDetailsTvNickName();

       TextView getmActPersonDetailsTvSex();
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
   }
    public interface IPersiDetialsPresenter extends BasePresenter<IPersiDetialsView>{


        /**
         * 上传头像
         * @param uri
         */
        void upload(Uri uri);

        /**
         * 更改用户的个人信息
         * @param text  更改后的数据
         * @param type 类型 1 :更改昵称 2:更改性别 3;
         */
        void updateUserInfo(String text, int type);
    }
}
