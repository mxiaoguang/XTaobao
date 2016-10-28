package com.xiaoguang.xtaobao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.rollviewpager.RollPagerView;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.xiaoguang.xtaobao.R;
import com.xiaoguang.xtaobao.base.BaseActivity;
import com.xiaoguang.xtaobao.contract.IGoodsDetailsContract;
import com.xiaoguang.xtaobao.presenter.ActGoodsDetailsPresenterImpl;
import com.xiaoguang.xtaobao.util.ToastFactory;
import com.xiaoguang.xtaobao.widget.XListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zhangphil.iosdialog.widget.ActionSheetDialog;

/**
 * Created by 11655 on 2016/10/23.
 */

public class GoodsDetailsActivity extends BaseActivity implements IGoodsDetailsContract.IGoodsDetailsView {

    //获取控件
    @BindView(R.id.act_goods_details_iv_back)
    ImageView mActGoodsDetailsIvBack;
    @BindView(R.id.act_goods_details_tv_menu)
    TextView mActGoodsDetailsTvMenu;
    @BindView(R.id.act_goods_details_roll_vp_ad)
    RollPagerView mActGoodsDetailsRollVpAd;
    @BindView(R.id.act_goods_details_tv_goods_name)
    TextView mActGoodsDetailsTvGoodsName;
    @BindView(R.id.act_goods_details_btn_share)
    Button mActGoodsDetailsBtnShare;
    @BindView(R.id.act_goods_details_tv_money)
    TextView mActGoodsDetailsTvMoney;
    @BindView(R.id.act_goods_details_xlv)
    XListView mActGoodsDetailsXlv;
    @BindView(R.id.act_goods_details_btn_kefu)
    Button mActGoodsDetailsBtnKefu;
    @BindView(R.id.act_goods_details_btn_dianpu)
    Button mActGoodsDetailsBtnDianpu;
    @BindView(R.id.act_goods_details_btn_shoucang)
    Button mActGoodsDetailsBtnShoucang;
    @BindView(R.id.act_goods_details_btn_jiaru)
    Button mActGoodsDetailsBtnJiaru;
    @BindView(R.id.act_goods_details_btn_goumai)
    Button mActGoodsDetailsBtnGoumai;
    private IGoodsDetailsContract.IGoodsDetailsPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_goods_details);
        ButterKnife.bind(this);
        new ActGoodsDetailsPresenterImpl(this);
        presenter.initData();
    }

    @Override
    public void showMsg(String msg) {
        ToastFactory.getToast(this, msg).show();
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

    }

    @Override
    public void jumpLogin() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public void setPresenter(IGoodsDetailsContract.IGoodsDetailsPresenter presenter) {
        this.presenter = presenter;
    }

    @OnClick({R.id.act_goods_details_iv_back, R.id.act_goods_details_tv_menu, R.id.act_goods_details_btn_share, R.id.act_goods_details_btn_kefu, R.id.act_goods_details_btn_dianpu, R.id.act_goods_details_btn_shoucang, R.id.act_goods_details_btn_jiaru, R.id.act_goods_details_btn_goumai})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.act_goods_details_iv_back:
                finish();
                break;
            case R.id.act_goods_details_tv_menu:
                break;
            case R.id.act_goods_details_btn_share:
                new ActionSheetDialog(this)
                        .builder()
                        .setCancelable(true)
                        .setCanceledOnTouchOutside(true)
                        .addSheetItem("分享到好友列表", ActionSheetDialog.SheetItemColor.Red
                                , new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        //分享到好友列表
                                        presenter.shareWXAPP(SendMessageToWX.Req.WXSceneSession);
                                    }
                                })
                        .addSheetItem("分享到朋友圈", ActionSheetDialog.SheetItemColor.Blue
                                , new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        //分享到朋友圈
                                        presenter.shareWXAPP(SendMessageToWX.Req.WXSceneTimeline);
                                    }
                                }).show();
                break;
            case R.id.act_goods_details_btn_kefu:
                break;
            case R.id.act_goods_details_btn_dianpu:
                break;
            case R.id.act_goods_details_btn_shoucang://收藏按钮被点击时,暂时不使用
                break;
            case R.id.act_goods_details_btn_jiaru://加入购物车
                if (isLogin()) {
                    presenter.joinShopCar();
                } else {
                    jumpLogin();
                }
                break;
            case R.id.act_goods_details_btn_goumai://购买
                if (isLogin()) {
                    //跳转到下单页面
                    startActivity(new Intent(this, OrdersActivity.class));
                } else {
                    jumpLogin();
                }
                break;
        }
    }
    @Override
    public RollPagerView getmActGoodsDetailsRollVpAd() {
        return mActGoodsDetailsRollVpAd;
    }

    @Override
    public TextView getmActGoodsDetailsTvGoodsName() {
        return mActGoodsDetailsTvGoodsName;
    }

    @Override
    public XListView getmActGoodsDetailsXlv() {
        return mActGoodsDetailsXlv;
    }

    @Override
    public TextView getmActGoodsDetailsTvMoney() {
        return mActGoodsDetailsTvMoney;
    }

    @Override
    public Button getmActGoodsDetailsBtnShoucang() {
        return mActGoodsDetailsBtnShoucang;
    }
}
