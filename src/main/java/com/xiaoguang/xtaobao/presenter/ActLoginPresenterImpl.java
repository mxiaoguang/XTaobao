package com.xiaoguang.xtaobao.presenter;

import android.widget.EditText;

import com.xiaoguang.xtaobao.application.CustomApplcation;
import com.xiaoguang.xtaobao.bean.User;
import com.xiaoguang.xtaobao.contract.ILoginContract;
import com.xiaoguang.xtaobao.util.LogUtils;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

/**
 * Created by 11655 on 2016/10/20.
 */

public class ActLoginPresenterImpl implements ILoginContract.ILoginPresenter {
    ILoginContract.ILoginView view;
    private EditText mEtPhone, mEtPwd;

    public ActLoginPresenterImpl(ILoginContract.ILoginView view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void initData() {
        //获取控件
        mEtPhone = view.getmActHomeEtPhone();
        mEtPwd = view.getmActLoginEtPwd();
    }

    @Override
    public void login() {
        //获取控件数据
        String strUserInput = mEtPhone.getText().toString().trim();
        String strPwd = mEtPwd.getText().toString().trim();
        //数据正确性判断
        if (strUserInput.isEmpty()) {
            view.showMsg("手机/会员名/邮箱不能为空!");
        } else if (strPwd.isEmpty()) {
            view.showMsg("密码不能为空!");
        } else {
            //进行登陆
            //显示加载进度条
            view.showLoadingDialog("", "登陆中...", true);
            BmobUser.loginByAccount(strUserInput, strPwd, new LogInListener<User>() {

                @Override
                public void done(User user, BmobException e) {
                    //隐藏进度条
                    view.canelLoadingDialog();
                    if (e == null) {
                        view.showMsg("登陆成功!");
                        //将登陆成功的用户保存起来
                        CustomApplcation.getInstance().setCurrentUser(user);
                        view.jumpActivity();
                    } else {
                        view.showMsg("登陆失败!\r\n" + e.getLocalizedMessage());
                        LogUtils.i("myTag", "登陆失败" + e.toString());
                    }
                }
            });
        }
    }
}
