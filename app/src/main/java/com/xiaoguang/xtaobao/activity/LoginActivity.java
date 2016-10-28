package com.xiaoguang.xtaobao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoguang.xtaobao.R;
import com.xiaoguang.xtaobao.base.BaseActivity;
import com.xiaoguang.xtaobao.contract.ILoginContract;
import com.xiaoguang.xtaobao.presenter.ActLoginPresenterImpl;
import com.xiaoguang.xtaobao.util.ToastFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 11655 on 2016/10/20.
 */

public class LoginActivity extends BaseActivity implements ILoginContract.ILoginView {

    //获取控件
    @BindView(R.id.frag_login_iv_back)
    ImageView mFragLoginIvBack;
    @BindView(R.id.frag_login_tv_help)
    TextView mFragLoginTvHelp;
    @BindView(R.id.act_home_et_phone)
    EditText mActHomeEtPhone;
    @BindView(R.id.act_login_et_pwd)
    EditText mActLoginEtPwd;
    @BindView(R.id.act_login_tv_login)
    TextView mActLoginTvLogin;
    @BindView(R.id.act_login_tv_forget)
    TextView mActLoginTvForget;
    @BindView(R.id.act_login_tv_reg)
    TextView mActLoginTvReg;
    private ILoginContract.ILoginPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        ButterKnife.bind(this);
        new ActLoginPresenterImpl(this);
        presenter.initData();
    }

    //点击事件
    @OnClick({R.id.frag_login_iv_back, R.id.frag_login_tv_help, R.id.act_login_tv_login, R.id.act_login_tv_forget, R.id.act_login_tv_reg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.frag_login_iv_back:
                jumpActivity();
                break;
            case R.id.frag_login_tv_help:
                break;
            case R.id.act_login_tv_login://进行登陆
                presenter.login();
                break;
            case R.id.act_login_tv_forget://跳转到忘记密码页面
                startActivity(new Intent(this,ForgetPwdActivity.class));
                break;
            case R.id.act_login_tv_reg://跳转到注册页面
                startActivity(new Intent(this,RegisterActivity.class));
                break;
        }
    }

    @Override
    public void setPresenter(ILoginContract.ILoginPresenter presenter) {
        this.presenter = presenter;
    }
    @Override
    public EditText getmActHomeEtPhone() {
        return mActHomeEtPhone;
    }
    @Override
    public EditText getmActLoginEtPwd() {
        return mActLoginEtPwd;
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
        setResult(200);
        finish();
    }
}
