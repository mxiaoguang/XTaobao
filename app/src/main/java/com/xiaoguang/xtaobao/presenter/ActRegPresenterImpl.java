package com.xiaoguang.xtaobao.presenter;

import android.os.CountDownTimer;
import android.widget.EditText;
import android.widget.TextView;

import com.xiaoguang.xtaobao.application.CustomApplcation;
import com.xiaoguang.xtaobao.bean.User;
import com.xiaoguang.xtaobao.contract.IRegisterContract;
import com.xiaoguang.xtaobao.util.DataProcessingUtils;
import com.xiaoguang.xtaobao.util.LogUtils;
import com.xiaoguang.xtaobao.util.RandomUtils;

import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.exception.BmobException;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.sms.listener.VerifySMSCodeListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by 11655 on 2016/10/19.
 */

public class ActRegPresenterImpl implements IRegisterContract.IIRegisterPrensenter {

    private IRegisterContract.IIRegisterView view;
    private EditText mEtPhone, mEtCode, mEtNickName, mEtPwd, mEtPwd2;
    private TextView mTvGetCode;

    public ActRegPresenterImpl(IRegisterContract.IIRegisterView view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void initData() {
        //获取控件
        mEtPhone = view.getmActHomeEtPhone();
        mEtCode = view.getmActHomeEtSmsCode();
        mEtNickName = view.getmActHomeEtNickName();
        mEtPwd = view.getmActRegEtPwd();
        mEtPwd2 = view.getmActRegEtPwd2();
        mTvGetCode = view.getmFragRegisterTvGetcode();
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
            BmobSMS.requestSMSCode(CustomApplcation.getInstance().context, strPhone, "注册模板", new RequestSMSCodeListener() {
                @Override
                public void done(Integer smsId, BmobException ex) {
                    if (ex == null) {//验证码发送成功
                        LogUtils.i("myTag", "验证码发送成功+短信id：" + smsId);//用于查询本次短信发送详情
                        view.showMsg("发送成功");
                    } else {
                        LogUtils.i("myTag", "验证码发送失败" + ex.toString());
                        view.showMsg("发送失败,请检查网络");
                    }
                }
            });
        } else {
            view.showMsg("请输入11位正确的手机号码!");
            return;
        }
    }

    @Override
    public void register() {
        //获取数据
        final String strPhone = mEtPhone.getText().toString().trim();
        String strCode = mEtCode.getText().toString().trim();
        final String strNickname = mEtNickName.getText().toString().trim();
        final String pwd1 = mEtPwd.getText().toString().toString().trim();
        String pwd2 = mEtPwd2.getText().toString().toString().trim();
        //验证数据的正确性
        if (strPhone.isEmpty()) {
            view.showMsg("手机号码不能为空!");
        } else if (!DataProcessingUtils.isPhone(strPhone)) {
            view.showMsg("请输入11位正确的手机号码!");
        } else if (strCode.isEmpty()) {
            view.showMsg("请输入验证码!");
        } else if (strNickname.isEmpty()) {
            view.showMsg("请输入昵称!");
        } else if (pwd1.isEmpty()) {
            view.showMsg("请输入密码!");
        } else if (pwd2.isEmpty()) {
            view.showMsg("请重复输入密码!");
        } else if (!pwd1.equals(pwd2)) {
            view.showMsg("两次输入密码不一致,请检查!");
        } else {//进行验证码，正确性验证
            view.showLoadingDialog("", "注册中...", false);
            //短信验证码验证
            BmobSMS.verifySmsCode(CustomApplcation.getInstance().context, strPhone, strCode, new VerifySMSCodeListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {//验证码验证成功
                        User user = new User();
                        //设置数据
                        String defaultUserName = RandomUtils.getRandoStr(5);
                        user.setUsername("tb_" + defaultUserName);//设置默认的用户名
                        user.setNickName(strNickname);//设置用户的昵称
                        user.setMobilePhoneNumber(strPhone);//设置用户手机号码
                        user.setMobilePhoneNumberVerified(true);//设置手机号码验证通过
                        user.setPassword(pwd1);//设置密码
                        //进行注册
                        user.signUp(new SaveListener<User>() {

                            @Override
                            public void done(User user, cn.bmob.v3.exception.BmobException e) {
                                //隐藏进度条
                                view.canelLoadingDialog();
                                if (e == null) {
                                    view.showMsg("注册成功!");
                                    LogUtils.i("myTag", "注册成功");
                                    view.jumpActivity();
                                } else {
                                    view.showMsg("注册失败!" + e.getLocalizedMessage());
                                    LogUtils.i("myTag", "注册失败" + e.toString());
                                }

                            }
                        });
                    } else {
                        //隐藏进度条
                        view.canelLoadingDialog();
                        view.showMsg("验证码验证失败，请重新获取并验证");
                        LogUtils.i("myTag", "验证码验证失败" + e.toString());
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
