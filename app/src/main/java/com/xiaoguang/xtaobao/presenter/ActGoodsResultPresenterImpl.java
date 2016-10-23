package com.xiaoguang.xtaobao.presenter;

import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.xiaoguang.xtaobao.adapter.ActGoodsResultAdapter;
import com.xiaoguang.xtaobao.application.CustomApplcation;
import com.xiaoguang.xtaobao.bean.Goods;
import com.xiaoguang.xtaobao.contract.IGoodsResultContract;
import com.xiaoguang.xtaobao.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by 11655 on 2016/10/23.
 */

public class ActGoodsResultPresenterImpl implements IGoodsResultContract.IGoodsResultPresenter {
    private IGoodsResultContract.IGoodsResultView view;
    private GridView gridView;
    //数据源
    private List<Goods> goodslists;
    public ActGoodsResultPresenterImpl(IGoodsResultContract.IGoodsResultView view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void initData() {
        //获取控件
        gridView = view.getmActGoodsResultGv();
        //获取数据
        String datas = view.getDatas();
        goodslists = new ArrayList<>();
        //查询数据
        queryDatasFromServer(datas);
        //设置item点击事件
        initEvent();
    }

    /**
     * 从服务器上获取数据
     */
    private void queryDatasFromServer(final String datas) {
        LogUtils.i(TAG,"获取到的参数信息为"+datas);
        view.showLoadingDialog("","数据加载中...", false);
        BmobQuery<Goods> query =  new BmobQuery<>();
        //根据商品的类别id查询显示数据
        query.addWhereEqualTo("goodsTypeId",datas);
        query.findObjects(new FindListener<Goods>() {
            @Override
            public void done(List<Goods> list, BmobException e) {
                view.canelLoadingDialog();
                if (e==null){
                    //设置适配器
                    if (list!=null){
                        goodslists = list;
                        gridView.setAdapter(new ActGoodsResultAdapter(goodslists,
                                CustomApplcation.getInstance().context));
                        view.showMsg("数据加载成功");
                    }else {
                        view.showMsg("数据为空>>");
                    }
                    LogUtils.i(TAG,"数据加载成功"+list.toString());
                }else {
                    view.showMsg("数据加载失败"+e.getLocalizedMessage());
                    LogUtils.i(TAG,"数据加载成功"+e.toString());
                }
            }
        });
    }
    /**
     * 初始化点击事件
     */
    private void initEvent() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //将选中的活动保存到内存中
                CustomApplcation.putDatas("goods",goodslists.get(position));
                //跳转到商品详情页面
                ActGoodsResultPresenterImpl.this.view.jumpActivity();
            }
        });
    }

}
