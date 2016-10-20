package com.xiaoguang.xtaobao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.jude.rollviewpager.RollPagerView;
import com.sunfusheng.marqueeview.MarqueeView;
import com.xiaoguang.xtaobao.R;
import com.xiaoguang.xtaobao.base.BaseFragment;
import com.xiaoguang.xtaobao.contract.IFragHomeContract;
import com.xiaoguang.xtaobao.presenter.FragHomePresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 主页中间内容
 * Created by 11655 on 2016/10/19.
 */

public class HomeFragment extends BaseFragment implements IFragHomeContract.IFragHomeView {
    @BindView(R.id.act_home_btn_scan)
    Button mActHomeBtnScan;
    @BindView(R.id.act_home_btn_msg)
    Button mActHomeBtnMsg;
    @BindView(R.id.frag_home_roll_vp_ad)
    RollPagerView mFragHomeRollVpAd;
    @BindView(R.id.frag_home_gv_sort)
    GridView gridViewSort;
    @BindView(R.id.frag_home_ln_top)
    LinearLayout linearLayoutTop;
    @BindView(R.id.frag_home_marqueeView)
    MarqueeView marqueeViewTop;
    @BindView(R.id.frag_home_gv_content)
    GridView gridViewContent;
    private IFragHomeContract.IFragHomePrensenter prenseter;

    @Override
    protected void lazyLoad() {

    }

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container, boolean b) {
        View rootView = inflater.inflate(R.layout.frag_home, null);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        new FragHomePresenterImpl(this);
        prenseter.intData();
    }
    @Override
    public RollPagerView getmActHomeVpAd() {
        return mFragHomeRollVpAd;
    }

    @Override
    public GridView getGridViewSort() {
        return gridViewSort;
    }

    @Override
    public GridView getGridViewContent() {
        return gridViewContent;
    }

    @Override
    public MarqueeView getMarqueeViewTop() {
        return marqueeViewTop;
    }

    @Override
    public void setPresenter(IFragHomeContract.IFragHomePrensenter presenter) {
        this.prenseter = presenter;
    }

    /**
     * 点击事件
     * @param view
     */
    @OnClick({R.id.act_home_btn_scan, R.id.act_home_btn_msg,R.id.frag_home_ln_top})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.act_home_btn_scan:
                break;
            case R.id.act_home_btn_msg:
                break;
            case R.id.frag_home_ln_top://添加资讯头条
                break;
        }
    }
}
