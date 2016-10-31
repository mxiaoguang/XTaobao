package com.xiaoguang.xtaobao.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaoguang.xtaobao.R;
import com.xiaoguang.xtaobao.application.CustomApplcation;
import com.xiaoguang.xtaobao.base.BaseActivity;
import com.xiaoguang.xtaobao.util.ToastFactory;
import com.xiaoguang.xtaobao.widget.ErinieShow;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 刮奖的页面
 * Created by 11655 on 2016/10/31.
 */

public class LotteriesActivity extends BaseActivity {

    //获取控件
    @BindView(R.id.container)
    RelativeLayout mContainer;
    ErinieShow erinieShow;
    @BindView(R.id.act_lotteries_tv_title)
    TextView mActLotteriesTvTitle;
    @BindView(R.id.act_lotteries_btn_lingqu)
    Button mActLotteriesBtnLingqu;
    @BindView(R.id.act_lotteries_tv_finish)
    TextView mActLotteriesTvFinish;
    private int level = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_lotteries);
        ButterKnife.bind(this);
        showErnie();
    }

    /**
     * 显示刮奖图层
     */
    private void showErnie() {
        mContainer.removeAllViews();

        level = getLevel();
        erinieShow = new ErinieShow(this, level);
        mContainer.addView(erinieShow, new ViewGroup.LayoutParams(-2, -2));
    }

    /**
     * 获取奖励等级
     *
     * @return
     */
    public int getLevel() {
        //随机，看看几等奖
        double d = Math.random() * 100;
        if (d < 50) {
            return 0;
        }
        if (d < 80) {
            return 3;
        }
        if (d < 95) {
            return 2;
        }
        return 1;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.act_lotteries_tv_finish, R.id.act_lotteries_btn_lingqu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.act_lotteries_tv_finish:
                finish();
                break;
            case R.id.act_lotteries_btn_lingqu:
                if (level == 0) {
                    ToastFactory.getToast(CustomApplcation.getInstance().context, "很遗憾，此次未中奖，再接再厉吧！").show();
                } else {
                    String money = "";
                    switch (level) {
                        case 1:
                            money = "10";
                            break;

                        case 2:
                            money = "20";
                            break;

                        case 3:
                            money = "50";
                            break;
                    }
                    ToastFactory.getToast(CustomApplcation.getInstance().context, "恭喜您，获得了" + money+ "元优惠券。").show();
                }
                break;
        }
    }
}
