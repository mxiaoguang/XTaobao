package com.xiaoguang.xtaobao.presenter;

import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.xiaoguang.xtaobao.R;
import com.xiaoguang.xtaobao.adapter.ActGoodsTypeAdapter;
import com.xiaoguang.xtaobao.application.CustomApplcation;
import com.xiaoguang.xtaobao.bean.GoodsType;
import com.xiaoguang.xtaobao.contract.IGoodsTypeContract;
import com.xiaoguang.xtaobao.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by 11655 on 2016/10/23.
 */

public class ActGoodsTypePresenterImpl implements IGoodsTypeContract.IGoodsTypePresenter {
    private IGoodsTypeContract.IGoodsTypeView view;
    //数据源
    private List<GoodsType> goodsTypes;
    //
    private GridView gridView;
    private LinearLayout lnContent;

    public ActGoodsTypePresenterImpl(IGoodsTypeContract.IGoodsTypeView view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void initData() {
        //获取控件
        gridView = view.getmActGoodsTypeGv();
        lnContent = view.getmActGoodsTypeLnContent();
        //初始化数据源
        goodsTypes = new ArrayList<>();
        //从服务器获取数据
        queryDatasFromServer();
        //为控件设置点击事件
        initEvent();
    }
    /**
     * 从服务器上获取数据，并显示
     */
    private void queryDatasFromServer() {
        view.showLoadingDialog("","数据加载中....",false);
        BmobQuery<GoodsType> query = new BmobQuery<GoodsType>();
        query.findObjects(new FindListener<GoodsType>() {
            @Override
            public void done(List<GoodsType> list, BmobException e) {
                view.canelLoadingDialog();
                if (e==null){
                    LogUtils.i(TAG,"查询到的数据为"+list.toString());
                    goodsTypes = list;
                    //设置适配器
                    gridView.setAdapter(new ActGoodsTypeAdapter(goodsTypes, CustomApplcation.getInstance().context));
                    view.showMsg("数据更新成功!");
                }else {
                    LogUtils.i(TAG,"查询失败"+e.toString());
                    lnContent.setBackgroundResource(R.drawable.error);
                }
            }
        });
    }

    /**
     * 初始化控件的点击事件
     */
    private void initEvent() {
        //给一项被点击时的事件
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //获取选中的item
                GoodsType goodsTypeItem =goodsTypes.get(position);
                //跳转activity，将数据选中的item的id传递到下一个activity
                ActGoodsTypePresenterImpl.this.view.jumpActivity(goodsTypeItem.getObjectId());
            }
        });
    }
}
