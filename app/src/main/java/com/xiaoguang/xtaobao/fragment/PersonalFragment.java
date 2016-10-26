package com.xiaoguang.xtaobao.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xiaoguang.xtaobao.R;
import com.xiaoguang.xtaobao.activity.LoginActivity;
import com.xiaoguang.xtaobao.activity.LoveGoodsActivity;
import com.xiaoguang.xtaobao.activity.OrdersResultActivity;
import com.xiaoguang.xtaobao.activity.PersonDetialsActivity;
import com.xiaoguang.xtaobao.activity.SettingActivtity;
import com.xiaoguang.xtaobao.application.CustomApplcation;
import com.xiaoguang.xtaobao.base.BaseFragment;
import com.xiaoguang.xtaobao.config.Contracts;
import com.xiaoguang.xtaobao.contract.IFragPersonalContract;
import com.xiaoguang.xtaobao.presenter.FragPersonalPresenterImpl;
import com.xiaoguang.xtaobao.util.ToastFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.xiaoguang.xtaobao.R.id.frag_personal_iv_head;

/**
 * 我的淘宝
 * Created by 11655 on 2016/10/19.
 */

public class PersonalFragment extends BaseFragment implements IFragPersonalContract.IFragPersonalView {

    //获取控件
    @BindView(R.id.frag_personal_tv_setting)
    TextView mFragPersonalTvetting;
    @BindView(R.id.frag_personal_iv_msg)
    ImageView mFragPersonalIvMsg;
    @BindView(frag_personal_iv_head)
    CircleImageView mFragPersonalIvHead;
    @BindView(R.id.frag_personal_tv_nick_name)
    TextView mFragPersonalTvNickName;
    @BindView(R.id.frag_personal_rl_show_dingdan)
    RelativeLayout mFragPersonalRlShowDingdan;
    @BindView(R.id.frag_personal_btn_pay)
    Button mFragPersonalBtnPay;
    @BindView(R.id.frag_personal_btn_daifahuo)
    Button mFragPersonalBtnDaifahuo;
    @BindView(R.id.frag_personal_btn_daishouhuo)
    Button mFragPersonalBtnDaishouhuo;
    @BindView(R.id.frag_home_personal_daipingjia)
    Button mFragHomePersonalDaipingjia;
    @BindView(R.id.frag_personal_btn_tuikuan)
    Button mFragPersonalBtnTuikuan;
    @BindView(R.id.frag_personal_rl_show_jianzhi)
    RelativeLayout mFragPersonalRlShowJianzhi;
    @BindView(R.id.frag_personal_gv_center)
    GridView mFragPersonalGvCenter;
    @BindView(R.id.frag_personal_gv_bottom)
    GridView mFragPersonalGvBottom;
    private IFragPersonalContract.IFragPersonalPresenter presenter;
    /**
     * 标志位，标志已经初始化完成
     */
    private boolean isPrepared;

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        if (!isLogin()) {
            startActivityForResult(new Intent(getContext(), LoginActivity.class), 500);
        } else {//设置数据
            setUserData();

        }
    }

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container, boolean b) {
        View rootView = inflater.inflate(R.layout.frag_personal, null);
        ButterKnife.bind(this, rootView);
        isPrepared = true;
        lazyLoad();
        return rootView;
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        new FragPersonalPresenterImpl(this);
        presenter.initData();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 500 && resultCode == 200) {
            setUserData();
        }
    }

    @Override
    public void setPresenter(IFragPersonalContract.IFragPersonalPresenter presenter) {
        this.presenter = presenter;
    }

    //点击事件
    @OnClick({R.id.frag_personal_tv_setting,R.id.frag_personal_iv_head, R.id.frag_personal_iv_msg, R.id.frag_personal_rl_show_dingdan, R.id.frag_personal_btn_pay, R.id.frag_personal_btn_daifahuo, R.id.frag_personal_btn_daishouhuo, R.id.frag_home_personal_daipingjia, R.id.frag_personal_btn_tuikuan, R.id.frag_personal_rl_show_jianzhi})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.frag_personal_tv_setting:
                //跳转到设置界面
                startActivity(new Intent(getContext(), SettingActivtity.class));
                break;
            case R.id.frag_personal_iv_msg:
                break;
            case R.id.frag_personal_iv_head:
                //跳转到个人详情页面
                startActivity(new Intent(getContext(), PersonDetialsActivity.class));
                break;
            case R.id.frag_personal_rl_show_dingdan:
                presenter.queryOrders(-1);
                break;
            case R.id.frag_personal_btn_pay:
                presenter.queryOrders(0);
                break;
            case R.id.frag_personal_btn_daifahuo:
                presenter.queryOrders(1);
                break;
            case R.id.frag_personal_btn_daishouhuo:
                presenter.queryOrders(2);
                break;
            case R.id.frag_home_personal_daipingjia:
                presenter.queryOrders(3);
                break;
            case R.id.frag_personal_btn_tuikuan:
                presenter.queryOrders(4);
                break;
            case R.id.frag_personal_rl_show_jianzhi:
                break;
        }
    }


    @Override
    public GridView getmFragPersonalGvBottom() {
        return mFragPersonalGvBottom;
    }

    @Override
    public GridView getmFragPersonalGvCenter() {
        return mFragPersonalGvCenter;
    }

    @Override
    public void showMsg(String msg) {
        ToastFactory.getToast(getContext(),msg).show();
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
    public void jumpActivity(int type) {
        switch (type){
            case 1://跳转到我的订单页面
                startActivity(new Intent(getContext(), OrdersResultActivity.class));
                break;
            case 2://跳转到收藏页面
                startActivity(new Intent(getContext(), LoveGoodsActivity.class));
                break;
        }
    }

    /**
     * 设置与用户相关的数据
     */
    private void setUserData() {
        mFragPersonalTvNickName.setText(CustomApplcation.getInstance().getCurrentUser().getNickName());
        String url = "";
        if (CustomApplcation.getInstance().getCurrentUser().getUserHead().getUrl() == null) {//如果头像为空,则加载默认头像
            url = Contracts.DEFALT_HEAD_URL;
        } else {
            url = CustomApplcation.getInstance().getCurrentUser().getUserHead().getUrl();
        }
        //设置头像
        Picasso.with(getContext()).load(url).into(mFragPersonalIvHead);
    }
}
