package com.xiaoguang.xtaobao.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.jude.rollviewpager.RollPagerView;
import com.sunfusheng.marqueeview.MarqueeView;
import com.xiaoguang.xtaobao.R;
import com.xiaoguang.xtaobao.activity.GoodsResultActivity;
import com.xiaoguang.xtaobao.activity.GoodsTypeActivity;
import com.xiaoguang.xtaobao.activity.WebViewActivity;
import com.xiaoguang.xtaobao.base.BaseFragment;
import com.xiaoguang.xtaobao.config.Contracts;
import com.xiaoguang.xtaobao.contract.IFragHomeContract;
import com.xiaoguang.xtaobao.presenter.FragHomePresenterImpl;
import com.xiaoguang.xtaobao.util.ToastFactory;

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
    @BindView(R.id.frag_home_et_search)
    EditText mFragHomeEtSearch;
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
    private IFragHomeContract.IFragHomePresenter prenseter;

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
        prenseter.initData();
    }

    @Override
    public void showMsg(String msg) {
        ToastFactory.getToast(getContext(), msg);
    }

    @Override
    public void showLoadingDialog(String title, String msg, boolean flag) {

    }

    @Override
    public void canelLoadingDialog() {

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
    public void jumpActivity(int type, String datas) {
        Intent intent = null;
        switch (type) {
            case 0:
                intent = new Intent(getContext(), WebViewActivity.class);
                intent.putExtra("URL", Contracts.TIANMAO_URL);
                break;
            case 1:
                intent = new Intent(getContext(), WebViewActivity.class);
                intent.putExtra("URL", Contracts.JUHUASUAN_URL);
                break;
            case 2:
                intent = new Intent(getContext(), WebViewActivity.class);
                intent.putExtra("URL", Contracts.TIANMAO_GUOJI_URL);
                break;
            case 3:
                intent = new Intent(getContext(), WebViewActivity.class);
                intent.putExtra("URL", Contracts.WAIMAI);
                break;
            case 4:
                intent = new Intent(getContext(), WebViewActivity.class);
                intent.putExtra("URL", Contracts.TIANMAO_SUPERMARKET);
                break;
            case 5:
                intent = new Intent(getContext(), WebViewActivity.class);
                intent.putExtra("URL", Contracts.CHONGZHI_CENTER);
                break;
            case 6:
                intent = new Intent(getContext(), WebViewActivity.class);
                intent.putExtra("URL", Contracts.ALI_TRAVEL);
                break;
            case 7:
                intent = new Intent(getContext(), WebViewActivity.class);
                intent.putExtra("URL", Contracts.LING_MONEY);

                break;
            case 8:
                intent = new Intent(getContext(), WebViewActivity.class);
                intent.putExtra("URL", Contracts.DAO_JIA);
                break;
            case 9:
                intent = new Intent(getContext(), GoodsTypeActivity.class);
                break;
            case 10://搜索框
                intent = new Intent(getContext(), GoodsResultActivity.class);
                //将数据传递到下一个activity
                intent.putExtra("datas", datas);
        }
        startActivity(intent);
    }

    @Override
    public void setPresenter(IFragHomeContract.IFragHomePresenter presenter) {
        this.prenseter = presenter;
    }

    /**
     * 点击事件
     *
     * @param view
     */
    @OnClick({R.id.act_home_btn_scan, R.id.act_home_btn_msg, R.id.frag_home_ln_top})
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

    @Override
    public EditText getmFragHomeEtSearch() {
        return mFragHomeEtSearch;
    }
}
