package com.xiaoguang.xtaobao.presenter;

import com.xiaoguang.xtaobao.adapter.ActOrderResultAdapter;
import com.xiaoguang.xtaobao.application.CustomApplcation;
import com.xiaoguang.xtaobao.bean.Orders;
import com.xiaoguang.xtaobao.contract.IOrdersResultContract;
import com.xiaoguang.xtaobao.util.LogUtils;
import com.xiaoguang.xtaobao.widget.XListView;

import java.util.List;

/**
 * Created by 11655 on 2016/10/26.
 */

public class ActOrderResultPresenterImpl implements IOrdersResultContract.IOrdersResultPresenter {

    private IOrdersResultContract.IOrdersResultView view;

    public ActOrderResultPresenterImpl(IOrdersResultContract.IOrdersResultView view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void initData() {
        //获取控件
        XListView mXlv = view.getmActOrdersResultXlv();
        //获取数据源
        List<Orders> ordersList = (List<Orders>) CustomApplcation.getDatas("orders",false);
        LogUtils.i(TAG,"我获取到数据源为"+ordersList.toString());
        //设置适配器
        mXlv.setAdapter(new ActOrderResultAdapter(CustomApplcation.getInstance().context,ordersList,this));
    }
}
