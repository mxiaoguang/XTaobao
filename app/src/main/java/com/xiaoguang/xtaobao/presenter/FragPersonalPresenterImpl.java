package com.xiaoguang.xtaobao.presenter;

import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.xiaoguang.xtaobao.R;
import com.xiaoguang.xtaobao.adapter.SortGridViewAdapter;
import com.xiaoguang.xtaobao.application.CustomApplcation;
import com.xiaoguang.xtaobao.bean.Goods;
import com.xiaoguang.xtaobao.bean.Orders;
import com.xiaoguang.xtaobao.bean.User;
import com.xiaoguang.xtaobao.contract.IFragPersonalContract;
import com.xiaoguang.xtaobao.util.LogUtils;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by 11655 on 2016/10/19.
 */

public class FragPersonalPresenterImpl implements IFragPersonalContract.IFragPersonalPresenter {
    private IFragPersonalContract.IFragPersonalView view;
    private GridView gridViewCenter;
    private GridView gridViewBottom;

    public FragPersonalPresenterImpl(IFragPersonalContract.IFragPersonalView view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void initData() {
        //获取中间的GridView
        gridViewCenter = view.getmFragPersonalGvCenter();
        //获取下部的GridView控件
        gridViewBottom = view.getmFragPersonalGvBottom();
        //初始化数据
        Integer imgCenterIds[] = {R.drawable.frag_my_shoucang_baobei, R.drawable.frag_shoucang_neirong,
                R.drawable.frag_my_guanzhu, R.drawable.frag_my_zuji, R.drawable.frag_my_card,
                R.drawable.frag_my_mayi, R.drawable.frag_my_huiyuan, R.drawable.frag_my_xiaomi, R.drawable.frag_my_qianbao,
                R.drawable.frag_my_phone, R.drawable.frag_my_kuaidi, R.drawable.frag_my_shangjia};
        String strsCenter[] = {"收藏宝贝", "收藏内容", "关注", "足迹", "卡券包", "蚂蚁花呗", "会员中心", "我的小蜜",
                "我要换钱", "我的通信", "我的快递", "我是商家"};
        Integer imgBottomIds[] = {R.drawable.frag_my_quanzi, R.drawable.frag_my_share,
                R.drawable.frag_my_ask, R.drawable.frag_my_bottom_pingjia};
        String strsBottom[] = {"我的圈子", "我的分享", "问大家", "我的评价"};
        //设置适配器
        gridViewCenter.setAdapter(new SortGridViewAdapter(CustomApplcation.getInstance().context,
                imgCenterIds, strsCenter, R.layout.frag_person_gv_sort_item));
        gridViewBottom.setAdapter(new SortGridViewAdapter(CustomApplcation.getInstance().context,
                imgBottomIds, strsBottom, R.layout.frag_person_gv_sort_item));
        initEvent();

    }

    /**
     * 监听事件
     */
    private void initEvent() {
        //为中间控件设置点击事件
        gridViewCenter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0://收藏的宝贝
                        queryLoveGoods();
                        break;
                }
            }
        });
    }

    @Override
    public void queryOrders(int type) {
        view.showLoadingDialog("", "数据加载中...", false);
        BmobQuery<Orders> query = new BmobQuery<>();
        if (type != -1) {
            query.addWhereEqualTo("ordersState", type);
        }
        query.findObjects(new FindListener<Orders>() {
            @Override
            public void done(List<Orders> list, BmobException e) {
                view.canelLoadingDialog();
                if (e == null) {
                    view.showMsg("加载成功!");
                    LogUtils.i(TAG, "查询订单信息成功" + list.toString());
                    //将订单信息保存到内存中
                    CustomApplcation.putDatas("orders", list);
                    view.jumpActivity(1);
                } else {
                    LogUtils.i(TAG, "查询订单信息失败" + e.toString());
                    view.showMsg("加载失败" + e.getLocalizedMessage());
                }
            }
        });
    }

    /**
     * 查询收藏的宝贝
     */
    private void queryLoveGoods() {
        view.showLoadingDialog("","数据加载中...",false);
        //查询用户收藏的宝贝id
        List<String> goodsIds = BmobUser.getCurrentUser(User.class).getLoveGoodsIds();
        LogUtils.i(TAG,"我收藏商品id为"+goodsIds);
        //查询所有收藏的商品信息
        BmobQuery<Goods> query = new BmobQuery<>();
        query.addWhereContainedIn("objectId",goodsIds);
        query.findObjects(new FindListener<Goods>() {
            @Override
            public void done(List<Goods> list, BmobException e) {
                if (e==null){//查询成功
                    view.canelLoadingDialog();
                    LogUtils.i(TAG,"查询数据成功!"+list.toString());
                    //将数据保存到内存中
                    CustomApplcation.putDatas("loveGoods",list);
                    view.jumpActivity(2);
                }else {
                    view.showMsg("查询失败!");
                    LogUtils.i(TAG,"查询失败"+e.toString());
                }
            }
        });
    }
}
