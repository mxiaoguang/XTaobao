package com.xiaoguang.xtaobao.presenter;

import android.os.CountDownTimer;
import android.widget.EditText;
import android.widget.TextView;

import com.xiaoguang.xtaobao.application.CustomApplcation;
import com.xiaoguang.xtaobao.contract.IForgetPwdContract;
import com.xiaoguang.xtaobao.util.DataProcessingUtils;
import com.xiaoguang.xtaobao.util.LogUtils;

import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.exception.BmobException;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 11655 on 2016/10/28.
 */

public class ActForgetPwdPresenterImpl implements IForgetPwdContract.IForgetPresenter {

    private IForgetPwdContract.IForgetView view;
    private EditText mEtPhone, mEtCode, mEtPwd, mEtPwd2;
    private TextView mTvGetCode;

    public ActForgetPwdPresenterImpl(IForgetPwdContract.IForgetView view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void initData() {
        //获取控件
        mEtPhone = view.getmActForgetEtPhone();
        mEtCode = view.getmActForgetEtSmsCode();
        mEtPwd = view.getmActForgetEtPwd();
        mEtPwd2 = view.getmActForgetEtPwd2();
        mTvGetCode = view.getmActForgetTvGetcode();
    }

    @Override
    public void getCode() {
        //获取文本框中的数据
        String strPhone = mEtPhone.getText().toString().trim();
        //判断数据的正确性
        if (strPhone.isEmpty()) {//判断文本框内容是否为空
            view.showMsg("手机号码不能为空!");
        } else if (DataProcessingUtils.isPhone(strPhone)) {//判断是否为手机号码
            //进行倒计时,并获取验证码
            startCountdown();
            //向服务器发送请求短信验证码
            BmobSMS.requestSMSCode(CustomApplcation.getInstance().context, strPhone, "忘记密码", new RequestSMSCodeListener() {
                @Override
                public void done(Integer integer, BmobException e) {
                    if (e==null){
                        view.showMsg("验证码发送成功!");
                    }else {
                        view.showMsg("验证码发送失败!");
                    }
                }
            });
        } else {
            view.showMsg("请输入11位正确的手机号码!");
            return;
        }
    }

    @Override
    public void reset() {
        //获取数据
        final String strPhone = mEtPhone.getText().toString().trim();
        String strCode = mEtCode.getText().toString().trim();
        final String pwd1 = mEtPwd.getText().toString().toString().trim();
        String pwd2 = mEtPwd2.getText().toString().toString().trim();
        //验证数据的正确性
        if (strPhone.isEmpty()) {
            view.showMsg("手机号码不能为空!");
        } else if (!DataProcessingUtils.isPhone(strPhone)) {
            view.showMsg("请输入11位正确的手机号码!");
        } else if (strCode.isEmpty()) {
            view.showMsg("请输入验证码!");
        } else if (pwd1.isEmpty()) {
            view.showMsg("请输入密码!");
        } else if (pwd2.isEmpty()) {
            view.showMsg("请重复输入密码!");
        } else if (!pwd1.equals(pwd2)) {
            view.showMsg("两次输入密码不一致,请检查!");
        } else {//进行验证码，正确性验证
            view.showLoadingDialog("", "重置中...", false);
            BmobUser.resetPasswordBySMSCode(strCode, pwd1, new UpdateListener() {
                @Override
                public void done(cn.bmob.v3.exception.BmobException e) {
                    if (e==null){
                        view.showMsg("密码重置成功,你可以使用新的密码登陆!");
                        view.jumpActivity();
                    }else {
                        view.showMsg("密码重置失败,请检查网络"+e.getLocalizedMessage());
                        LogUtils.i(TAG,"密码重置失败"+e.toString());

                    }
                }
            });
        }

    }


    /**
     * 功能：开启一个倒计时
     */
    private void startCountdown() {
        //开启一个倒计时
        final CountDownTimer timer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //设置倒计时
                mTvGetCode.setText(millisUntilFinished / 1000 + "s");
                //设置手机号码为不可更改状态
                mEtPhone.setEnabled(false);
                LogUtils.i("myTag", "" + millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                mTvGetCode.setText("重新获取");
                //设置按钮为可以改变状态
                mEtPhone.setEnabled(true);
                LogUtils.i("myTag", "倒计时完成，重新获取验证码！");
            }
        };
        //开启倒计时
        timer.start();
    }
}
