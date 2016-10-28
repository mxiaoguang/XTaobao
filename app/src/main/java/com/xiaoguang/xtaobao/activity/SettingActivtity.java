package com.xiaoguang.xtaobao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaoguang.xtaobao.R;
import com.xiaoguang.xtaobao.base.BaseActivity;
import com.xiaoguang.xtaobao.contract.ISettingContract;
import com.xiaoguang.xtaobao.presenter.ActSettingPresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zhangphil.iosdialog.widget.ActionSheetDialog;

/**
 * Created by 11655 on 2016/10/21.
 */

public class SettingActivtity extends BaseActivity implements ISettingContract.ISettingView {

    @BindView(R.id.act_setting_iv_back)
    ImageView mActSettingIvBack;
    @BindView(R.id.act_setting_tv_menu)
    TextView mActSettingTvMenu;
    @BindView(R.id.act_personal_setting_tv_exit)
    TextView mActPersonalSettingTvExit;
    @BindView(R.id.act_setting_ln_person)
    LinearLayout mActSettingLnPerson;
    @BindView(R.id.act_setting_ln_self)
    LinearLayout mActSettingLnSelf;
    @BindView(R.id.act_setting_ln_new_msg)
    LinearLayout mActSettingLnNewMsg;
    @BindView(R.id.act_setting_ln_count_msg)
    LinearLayout mActSettingLnCountMsg;
    @BindView(R.id.act_setting_ln_shezhi)
    LinearLayout mActSettingLnShezhi;
    @BindView(R.id.act_setting_ln_xiaomi)
    LinearLayout mActSettingLnXiaomi;
    @BindView(R.id.act_setting_ln_about)
    LinearLayout mActSettingLnAbout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_personal_setting);
        ButterKnife.bind(this);
        new ActSettingPresenterImpl(this);
    }

    private ISettingContract.ISettingPresenter presenter;

    @Override
    public void setPresenter(ISettingContract.ISettingPresenter presenter) {
        this.presenter = presenter;
    }

    @OnClick({R.id.act_setting_iv_back, R.id.act_setting_tv_menu, R.id.act_personal_setting_tv_exit,
            R.id.act_setting_ln_person, R.id.act_setting_ln_self, R.id.act_setting_ln_new_msg,
            R.id.act_setting_ln_count_msg, R.id.act_setting_ln_shezhi, R.id.act_setting_ln_xiaomi,
            R.id.act_setting_ln_about})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.act_setting_iv_back:
                finish();
                break;
            case R.id.act_setting_tv_menu:
                break;
            case R.id.act_personal_setting_tv_exit:
                new ActionSheetDialog(SettingActivtity.this)
                        .builder()
                        .setTitle("确定要退出登录吗?")
                        .setCancelable(true)
                        .setCanceledOnTouchOutside(true)
                        .addSheetItem("确定", ActionSheetDialog.SheetItemColor.Red
                                , new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        //退出登录
                                        presenter.logingOut();
                                    }
                                }).show();
                break;
            case R.id.act_setting_ln_person:
                startActivity(new Intent(this,PersonDetialsActivity.class));
                break;
            case R.id.act_setting_ln_self:
                break;
            case R.id.act_setting_ln_new_msg:
                break;
            case R.id.act_setting_ln_count_msg:
                break;
            case R.id.act_setting_ln_shezhi:
                break;
            case R.id.act_setting_ln_xiaomi:
                break;
            case R.id.act_setting_ln_about:
                break;
        }
    }
}
