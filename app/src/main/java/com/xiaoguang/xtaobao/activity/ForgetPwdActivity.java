package com.xiaoguang.xtaobao.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoguang.xtaobao.R;
import com.xiaoguang.xtaobao.base.BaseActivity;
import com.xiaoguang.xtaobao.contract.IForgetPwdContract;
import com.xiaoguang.xtaobao.presenter.ActForgetPwdPresenterImpl;
import com.xiaoguang.xtaobao.util.ToastFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 忘记密码
 * Created by 11655 on 2016/10/28.
 */

public class ForgetPwdActivity extends BaseActivity implements IForgetPwdContract.IForgetView {
    @BindView(R.id.frag_forget_tv_back)
    ImageView mFragForgetTvBack;
    @BindView(R.id.act_forget_tv_help)
    TextView mActForgetTvHelp;
    @BindView(R.id.act_forget_et_phone)
    EditText mActForgetEtPhone;
    @BindView(R.id.act_forget_tv_getcode)
    TextView mActForgetTvGetcode;
    @BindView(R.id.act_forget_et_sms_code)
    EditText mActForgetEtSmsCode;
    @BindView(R.id.act_forget_et_pwd)
    EditText mActForgetEtPwd;
    @BindView(R.id.act_forget_et_pwd2)
    EditText mActForgetEtPwd2;
    @BindView(R.id.act_forget_tv_reset)
    TextView mActForgetTvReset;
    private IForgetPwdContract.IForgetPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_forget);
        ButterKnife.bind(this);
        new ActForgetPwdPresenterImpl(this);
        presenter.initData();
    }

    @Override
    public void showMsg(String msg) {
        ToastFactory.getToast(this,msg).show();
    }

    @Override
    public void showLoadingDialog(String title, String msg, boolean flag) {
        super.showProcessDialog(title, msg, flag);
    }

    @Override
    public void canelLoadingDialog() {
        super.dismissProcessDialog();
    }

    @Override
    public void jumpActivity() {
        finish();
    }

    @Override
    public void setPresenter(IForgetPwdContract.IForgetPresenter presenter) {
        this.presenter = presenter;
    }

    @OnClick({R.id.frag_forget_tv_back, R.id.act_forget_tv_help, R.id.act_forget_tv_getcode,R.id.act_forget_tv_reset})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.frag_forget_tv_back:
                finish();
                break;
            case R.id.act_forget_tv_help:
                break;
            case R.id.act_forget_tv_getcode:
                presenter.getCode();
                break;
            case R.id.act_forget_tv_reset:
                presenter.reset();
                break;
        }
    }

    @Override
    public EditText getmActForgetEtPhone() {
        return mActForgetEtPhone;
    }

    @Override
    public EditText getmActForgetEtPwd() {
        return mActForgetEtPwd;
    }

    @Override
    public EditText getmActForgetEtPwd2() {
        return mActForgetEtPwd2;
    }

    @Override
    public EditText getmActForgetEtSmsCode() {
        return mActForgetEtSmsCode;
    }

    @Override
    public TextView getmActForgetTvGetcode() {
        return mActForgetTvGetcode;
    }

}
