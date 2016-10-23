package com.xiaoguang.xtaobao.presenter;

import android.widget.GridView;

import com.xiaoguang.xtaobao.R;
import com.xiaoguang.xtaobao.adapter.SortGridViewAdapter;
import com.xiaoguang.xtaobao.application.CustomApplcation;
import com.xiaoguang.xtaobao.contract.IFragPersonalContract;

/**
 * Created by 11655 on 2016/10/19.
 */

public class FragPersonalPresenterImpl implements IFragPersonalContract.IFragPersonalPresenter {
    private IFragPersonalContract.IFragPersonalView view;
    public FragPersonalPresenterImpl(IFragPersonalContract.IFragPersonalView view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void initData() {
        //获取中间的GridView
        GridView gridViewCenter = view.getmFragPersonalGvCenter();
        //获取下部的GridView控件
        GridView gridViewBottom = view.getmFragPersonalGvBottom();
        //初始化数据
        Integer imgCenterIds []= {R.drawable.frag_my_shoucang_baobei,R.drawable.frag_shoucang_neirong,
                R.drawable.frag_my_guanzhu, R.drawable.frag_my_zuji,R.drawable.frag_my_card,
                R.drawable.frag_my_mayi,R.drawable.frag_my_huiyuan,R.drawable.frag_my_xiaomi,R.drawable.frag_my_qianbao,
                R.drawable.frag_my_phone,R.drawable.frag_my_kuaidi,R.drawable.frag_my_shangjia};
        String strsCenter [] = {"收藏宝贝","收藏内容","关注","足迹","卡券包","蚂蚁花呗","会员中心","我的小蜜",
                "我要换钱","我的通信","我的快递","我是商家"};
        Integer imgBottomIds [] = {R.drawable.frag_my_quanzi,R.drawable.frag_my_share,
                R.drawable.frag_my_ask,R.drawable.frag_my_bottom_pingjia};
        String strsBottom [] = {"我的圈子","我的分享","问大家","我的评价"};
        //设置适配器
        gridViewCenter.setAdapter(new SortGridViewAdapter(CustomApplcation.getInstance().context,
                imgCenterIds,strsCenter,R.layout.frag_person_gv_sort_item));
        gridViewBottom.setAdapter(new SortGridViewAdapter(CustomApplcation.getInstance().context,
                imgBottomIds,strsBottom,R.layout.frag_person_gv_sort_item));

    }
}
