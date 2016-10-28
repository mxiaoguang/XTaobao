package com.xiaoguang.xtaobao.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.xiaoguang.xtaobao.R;
import com.xiaoguang.xtaobao.base.BaseActivity;
import com.xiaoguang.xtaobao.contract.IHomeContract;
import com.xiaoguang.xtaobao.presenter.ActHomePresenterImpl;
import com.xiaoguang.xtaobao.widget.NotSlipViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.update.BmobUpdateAgent;

public class HomeActivity extends BaseActivity implements IHomeContract.IHomeView {

    //获取控件
    @BindView(R.id.act_home_vp_content)
    NotSlipViewPager mActHomeVpContent;
    @BindView(R.id.act_home_btn_home)
    Button mActHomeBtnHome;
    @BindView(R.id.act_home_btn_we)
    Button mActHomeBtnWe;
    @BindView(R.id.act_home_btn_ask)
    Button mActHomeBtnAsk;
    @BindView(R.id.act_home_btn_shopcar)
    Button mActHomeBtnShopcar;
    @BindView(R.id.act_home_btn_my)
    Button mActHomeBtnMy;
    /**
     * 定义一个button数组，用于改变颜色
     */
    private Button btns[];
    private int btnID[];
    //声明一个Presenter 对象，用于相关逻辑的处理
    private IHomeContract.IHomePresenter presenter;
    //定义FragmentManager对象
    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_home);
        ButterKnife.bind(this);
        new ActHomePresenterImpl(this);

        //初始化按钮ID
        btnID = new int[]{R.id.act_home_btn_home, R.id.act_home_btn_we, R.id.act_home_btn_shopcar, R.id.act_home_btn_my};
        btns  = new Button[]{mActHomeBtnHome,mActHomeBtnWe,mActHomeBtnShopcar,mActHomeBtnMy};
        //默认第一个按钮被选中
        mActHomeBtnHome.setEnabled(false);
        //进行版本判断操作
        BmobUpdateAgent.update(this);
        //进行数据的初始化操作
        presenter.initData();
    }
    @OnClick({R.id.act_home_btn_home,
            R.id.act_home_btn_we, R.id.act_home_btn_ask, R.id.act_home_btn_shopcar, R.id.act_home_btn_my})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.act_home_btn_home:
                changBtnSelectedStatus(0);
                mActHomeVpContent.setCurrentItem(0);
                break;
            case R.id.act_home_btn_we:
                changBtnSelectedStatus(1);
                mActHomeVpContent.setCurrentItem(1);
                break;
            case R.id.act_home_btn_ask://中间问大家按钮,存在两次的点击判断
                mActHomeVpContent.setCurrentItem(2);
                mActHomeBtnAsk.setText("发布");
                mActHomeBtnAsk.setBackgroundColor(Color.parseColor("#ffbe2a"));
                break;
            case R.id.act_home_btn_shopcar:
                changBtnSelectedStatus(2);
                mActHomeVpContent.setCurrentItem(3);
                break;
            case R.id.act_home_btn_my:
                changBtnSelectedStatus(3);
                mActHomeVpContent.setCurrentItem(4);
                break;
        }
    }
    @Override
    public void setPresenter(IHomeContract.IHomePresenter presenter) {
        this.presenter  = presenter;
    }

    @Override
    public ViewPager getmActHomeVpContent() {
        return mActHomeVpContent;
    }

    @Override
    public FragmentManager getManager() {
        manager = getSupportFragmentManager();
        return manager;
    }

    /**
     * 按钮被选中的颜色变化
     *
     * @param position
     */
    public void changBtnSelectedStatus(int position) {
        for (int i = 0; i < 4; i++) {
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
