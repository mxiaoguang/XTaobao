package com.xiaoguang.xtaobao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.xiaoguang.xtaobao.R;
import com.xiaoguang.xtaobao.base.BaseFragment;
import com.xiaoguang.xtaobao.contract.IFragWeContract;
import com.xiaoguang.xtaobao.presenter.FragWePresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.xiaoguang.xtaobao.R.id.frag_home_btn_good_new;

/**
 * 微淘页面
 * Created by 11655 on 2016/10/19.
 */

public class WeFragment extends BaseFragment implements IFragWeContract.IFragWeView {
    @BindView(R.id.frag_we_iv_search)
    ImageView mFragWeIvSearch;
    @BindView(R.id.frag_we_vp)
    ViewPager mFragWeVp;
    @BindView(R.id.frag_we_btn_news)
    Button mFragWeBtnNews;
    @BindView(frag_home_btn_good_new)
    Button mFragHomeBtnGoodNew;
    @BindView(R.id.frag_we_btn_video)
    Button mFragWeBtnVideo;
    @BindView(R.id.frag_we_btn_hot_title)
    Button mFragWeBtnHotTitle;
    @BindView(R.id.frag_we_btn_medial)
    Button mFragWeBtnMedial;
    private IFragWeContract.IFragWePresenter presenter;
    //Fragment的管理器
    private FragmentManager manager;
    /**
     * 定义一个button数组，用于改变颜色
     */
    private Button btns[];
    private int btnID[];

    @Override
    protected void lazyLoad() {

    }

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container, boolean b) {
        View rootView = inflater.inflate(R.layout.frag_we, null);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        new FragWePresenterImpl(this);
        btns = new Button[]{mFragWeBtnNews, mFragHomeBtnGoodNew, mFragWeBtnVideo,
                mFragWeBtnHotTitle, mFragWeBtnMedial};
        btnID = new int[]{R.id.frag_we_btn_news, R.id.frag_home_btn_good_new,
                R.id.frag_we_btn_video, R.id.frag_we_btn_hot_title, R.id.frag_we_btn_medial};
        //默认第一个按钮被选中
        mFragWeBtnNews.setEnabled(false);
        //初始化数据
        presenter.initData();
        //为vp设置监听事件
        mFragWeVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            //当滑动时,改变按钮颜色
            @Override
            public void onPageSelected(int position) {
                changBtnSelectedStatus(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick(R.id.frag_we_iv_search)
    public void onClick() {
    }

    @Override
    public void setPresenter(IFragWeContract.IFragWePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public ViewPager getmFragWeVp() {
        return mFragWeVp;
    }

    @Override
    public FragmentManager getManager() {
        return getChildFragmentManager();
    }

    /**
     * 头部按钮设置点击事件
     *
     * @param view
     */
    @OnClick({R.id.frag_we_btn_news, frag_home_btn_good_new, R.id.frag_we_btn_video, R.id.frag_we_btn_hot_title, R.id.frag_we_btn_medial})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.frag_we_btn_news:
                changBtnSelectedStatus(0);
                mFragWeVp.setCurrentItem(0);
                break;
            case frag_home_btn_good_new:
                changBtnSelectedStatus(1);
                mFragWeVp.setCurrentItem(1);
                break;
            case R.id.frag_we_btn_video:
                changBtnSelectedStatus(2);
                mFragWeVp.setCurrentItem(2);
                break;
            case R.id.frag_we_btn_hot_title:
                changBtnSelectedStatus(3);
                mFragWeVp.setCurrentItem(3);
                break;
            case R.id.frag_we_btn_medial:
                changBtnSelectedStatus(4);
                mFragWeVp.setCurrentItem(4);
                break;
        }
    }

    /**
     * 按钮被选中的颜色变化
     *
     * @param position
     */

    public void changBtnSelectedStatus(int position) {
        for (int i = 0; i < 5; i++) {
            if (i == position) {
                //将选中的设置背景颜色为其他色
                btns[i].setEnabled(false);
            } else {
                //将为选中的设置背景颜色为默认
                btns[i].setEnabled(true);
            }
        }
    }
}
