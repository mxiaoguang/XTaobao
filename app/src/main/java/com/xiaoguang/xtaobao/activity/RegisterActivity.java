package com.xiaoguang.xtaobao.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoguang.xtaobao.R;
import com.xiaoguang.xtaobao.base.BaseActivity;
import com.xiaoguang.xtaobao.contract.IRegisterContract;
import com.xiaoguang.xtaobao.presenter.ActRegPresenterImpl;
import com.xiaoguang.xtaobao.util.ToastFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 11655 on 2016/10/19.
 */

public class RegisterActivity extends BaseActivity implements IRegisterContract.IIRegisterView {

    //获取控件
    @BindView(R.id.frag_tv_help)
    TextView mFragTvHelp;
    @BindView(R.id.act_home_et_phone)
    EditText mActHomeEtPhone;
    @BindView(R.id.frag_register_tv_getcode)
    TextView mFragRegisterTvGetcode;
    @BindView(R.id.act_home_et_sms_code)
    EditText mActHomeEtSmsCode;
    @BindView(R.id.act_reg_et_nick_name)
    EditText mActHomeEtNickName;
    @BindView(R.id.act_reg_et_pwd)
    EditText mActRegEtPwd;
    @BindView(R.id.act_reg_et_pwd2)
    EditText mActRegEtPwd2;
    @BindView(R.id.act_reg_tv_reg)
    TextView mActRegTvReg;
    @BindView(R.id.frag_register_tv_back)
    ImageView mFragRegisterTvBack;
    private IRegisterContract.IIRegisterPrensenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_register);
        ButterKnife.bind(this);
        new ActRegPresenterImpl(this);
        presenter.initData();
    }

    @Override
    public void setPresenter(IRegisterContract.IIRegisterPrensenter presenter) {
        this.presenter = presenter;
    }

    @OnClick({R.id.frag_register_tv_back, R.id.frag_tv_help, R.id.frag_register_tv_getcode, R.id.act_reg_tv_reg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.frag_register_tv_back://返回上一个Activity
                finish();
                break;
            case R.id.frag_tv_help:
                break;
            case R.id.frag_register_tv_getcode://获取验证码
                presenter.getCode();
                break;
            case R.id.act_reg_tv_reg://注册
                presenter.register();
                break;
        }
    }
    @Override
    public EditText getmActHomeEtPhone() {
        return mActHomeEtPhone;
    }
    @Override
    public EditText getmActHomeEtSmsCode() {
        return mActHomeEtSmsCode;
    }
    @Override
    public EditText getmActHomeEtNickName() {
        return mActHomeEtNickName;
    }

    @Override
    public EditText getmActRegEtPwd() {
        return mActRegEtPwd;
    }
    @Override
    public EditText getmActRegEtPwd2() {
        return mActRegEtPwd2;
    }
    @Override
    public TextView getmFragRegisterTvGetcode() {
        return mFragRegisterTvGetcode;
    }

    @Override
    public void showMsg(String msg) {
        ToastFactory.getToast(this,msg).show();
    }
    @Override
    public void showLoadingDialog(String title, String msg, boolean flag){
        super.showProcessDialog(title, msg, flag);
    }
    @Override
    public void canelLoadingDialog(){
        super.dismissProcessDialog();
    }

    @Override
    public void jumpActivity() {
        //结束当前窗体，返回登陆页面
        finish();
    }
}
