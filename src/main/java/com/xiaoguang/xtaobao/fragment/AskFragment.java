package com.xiaoguang.xtaobao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.xiaoguang.xtaobao.R;
import com.xiaoguang.xtaobao.base.BaseFragment;
import com.xiaoguang.xtaobao.contract.IFragAskContract;
import com.xiaoguang.xtaobao.presenter.FragAskPresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 问大家
 * Created by 11655 on 2016/10/19.
 */

public class AskFragment extends BaseFragment implements IFragAskContract.IFragAskView {

    //获取控件
    @BindView(R.id.frag_ask_iv_head)
    CircleImageView mFragAskIvHead;
    @BindView(R.id.frag_ask_ed_search)
    EditText mFragAskEdSearch;
    @BindView(R.id.frag_ask_iv_msg)
    ImageView mFragAskIvMsg;
    @BindView(R.id.frag_ask_btn_hot)
    Button mFragAskBtnHot;
    @BindView(R.id.frag_ask_btn_clothes)
    Button mFragAskBtnClothes;
    @BindView(R.id.frag_ask_btn_buy)
    Button mFragAskBtnBuy;
    @BindView(R.id.frag_ask_btn_child)
    Button mFragAskBtnChild;
    @BindView(R.id.frag_ask_btn_fix)
    Button mFragAskBtnFix;
    @BindView(R.id.frag_ask_btn_beautiful)
    Button mFragAskBtnBeautiful;
    @BindView(R.id.frag_ask_btn_media)
    Button mFragAskBtnMedia;
    @BindView(R.id.frag_ask_btn_heathy)
    Button mFragAskBtnHeathy;
    @BindView(R.id.frag_ask_vp)
    ViewPager mFragAskVp;
    /**
     * 定义一个button数组，用于改变颜色
     */
    private Button btns[];
    private int btnID[];
    private IFragAskContract.IFragAskPresenter presenter;

    @Override
    protected void lazyLoad() {

    }

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container, boolean b) {
        View rootView = inflater.inflate(R.layout.frag_ask, null);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        new FragAskPresenterImpl(this);
        //将控件添加到数组中，用于改变其颜色
        btns = new Button[]{mFragAskBtnHot, mFragAskBtnClothes, mFragAskBtnBuy, mFragAskBtnChild,
                mFragAskBtnFix, mFragAskBtnBeautiful, mFragAskBtnMedia, mFragAskBtnHeathy};
        btnID = new int[]{R.id.frag_ask_btn_hot, R.id.frag_ask_btn_clothes, R.id.frag_ask_btn_buy,
                R.id.frag_ask_btn_child, R.id.frag_ask_btn_fix, R.id.frag_ask_btn_beautiful,
                R.id.frag_ask_btn_media, R.id.frag_ask_btn_heathy};
        //默认第一个按钮被选中
        mFragAskBtnHot.setEnabled(false);
        presenter.initData();
        //给viewpager设置监听事件
        mFragAskVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changBtnSelectedStatus(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public ViewPager getmFragAskVp() {
        return mFragAskVp;
    }

    @Override
    public FragmentManager getManager() {
        return getChildFragmentManager();
    }

    @Override
    public void setPresenter(IFragAskContract.IFragAskPresenter presenter) {
        this.presenter = presenter;
    }

    //设置按钮的点击事件
    @OnClick({R.id.frag_ask_iv_msg, R.id.frag_ask_btn_hot, R.id.frag_ask_btn_clothes,
            R.id.frag_ask_btn_buy, R.id.frag_ask_btn_child, R.id.frag_ask_btn_fix,
            R.id.frag_ask_btn_beautiful, R.id.frag_ask_btn_media, R.id.frag_ask_btn_heathy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.frag_ask_iv_msg:
                break;
            case R.id.frag_ask_btn_hot:
                changBtnSelectedStatus(0);
                mFragAskVp.setCurrentItem(0);
                break;
            case R.id.frag_ask_btn_clothes:
                changBtnSelectedStatus(1);
                mFragAskVp.setCurrentItem(1);
                break;
            case R.id.frag_ask_btn_buy:
                changBtnSelectedStatus(2);
                mFragAskVp.setCurrentItem(2);
                break;
            case R.id.frag_ask_btn_child:
                changBtnSelectedStatus(3);
                mFragAskVp.setCurrentItem(3);
                break;
            case R.id.frag_ask_btn_fix:
                changBtnSelectedStatus(4);
                mFragAskVp.setCurrentItem(4);
                break;
            case R.id.frag_ask_btn_beautiful:
                changBtnSelectedStatus(5);
                mFragAskVp.setCurrentItem(5);
                break;
            case R.id.frag_ask_btn_media:
                changBtnSelectedStatus(6);
                mFragAskVp.setCurrentItem(6);
                break;
            case R.id.frag_ask_btn_heathy:
                changBtnSelectedStatus(7);
                mFragAskVp.setCurrentItem(7);
                break;
        }
    }

    /**
     * 按钮被选中的颜色变化,(三个地方使用此代码，以后可能会优化)
     *
     * @param position
     */

    public void changBtnSelectedStatus(int position) {
        for (int i = 0; i < 8; i++) {
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
