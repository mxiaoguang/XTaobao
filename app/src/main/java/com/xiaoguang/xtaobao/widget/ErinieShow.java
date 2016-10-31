package com.xiaoguang.xtaobao.widget;

import android.content.Context;
import android.graphics.Color;
import android.widget.RelativeLayout;

import com.xiaoguang.xtaobao.R;
import com.xiaoguang.xtaobao.util.PhoneUtil;

public class ErinieShow extends RelativeLayout {
    Context context;
    RelativeLayout rubblerBG;
    RubblerShow rubblerShow;

    int rubblerBGId = 10001;
    int getRewardId = 10002;

    int level;

    public ErinieShow(Context context, int level) {
        super(context);
        this.context = context;
        this.level = level;
        getElement();
        setElementLP();
        setElementStyle();
        setElement();
    }

    private void getElement() {
        rubblerBG = new RelativeLayout(context);
        rubblerShow = new RubblerShow(context, null);

        rubblerBG.setId(rubblerBGId);
        rubblerBG.addView(rubblerShow);
        addView(rubblerBG);
    }

    private void setElementLP() {
        int[] resolution = PhoneUtil.getResolution(context);
        RelativeLayout.LayoutParams rubblerBG_LP = new RelativeLayout.LayoutParams(
                resolution[0], PhoneUtil.getFitHeight(context, 125));

        rubblerBG.setLayoutParams(rubblerBG_LP);
        rubblerShow.setLayoutParams(rubblerBG_LP);

        RelativeLayout.LayoutParams getReward_LP = new LayoutParams(-2, -2);
        getReward_LP.addRule(RelativeLayout.CENTER_HORIZONTAL);
        getReward_LP.addRule(RelativeLayout.BELOW, rubblerBGId);

    }

    private void setElementStyle() {
        switch (level) {
            case 0:
                rubblerBG.setBackgroundResource(R.drawable.rewardlevel0);
                break;
            case 1:
                rubblerBG.setBackgroundResource(R.drawable.rewardlevel1);

                break;
            case 2:
                rubblerBG.setBackgroundResource(R.drawable.rewardlevel2);
                break;
            default:
                rubblerBG.setBackgroundResource(R.drawable.rewardlevel3);
                break;
        }
    }

    private void setElement() {
        rubblerShow.beginRubbler(Color.parseColor("#d3d3d3"), 30, 10);
    }
}
