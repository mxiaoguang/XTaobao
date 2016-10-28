package com.xiaoguang.xtaobao.presenter;

        import com.xiaoguang.xtaobao.adapter.ActOrderResultAdapter;
        import com.xiaoguang.xtaobao.application.CustomApplcation;
        import com.xiaoguang.xtaobao.bean.Discuss;
        import com.xiaoguang.xtaobao.bean.Orders;
        import com.xiaoguang.xtaobao.contract.IOrdersResultContract;
        import com.xiaoguang.xtaobao.util.LogUtils;
        import com.xiaoguang.xtaobao.widget.XListView;

        import java.text.SimpleDateFormat;
        import java.util.Date;
        import java.util.List;

        import cn.bmob.v3.BmobQuery;
        import cn.bmob.v3.exception.BmobException;
        import cn.bmob.v3.listener.FindListener;
        import cn.bmob.v3.listener.SaveListener;
        import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 11655 on 2016/10/26.
 */

public class ActOrderResultPresenterImpl implements IOrdersResultContract.IOrdersResultPresenter {

    private IOrdersResultContract.IOrdersResultView view;
    private ActOrderResultAdapter mAdapter;

    public ActOrderResultPresenterImpl(IOrdersResultContract.IOrdersResultView view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void initData() {
        //获取控件
        XListView mXlv = view.getmActOrdersResultXlv();
        //获取数据源
        List<Orders> ordersList = (List<Orders>) CustomApplcation.getDatas("orders", false);
        LogUtils.i(TAG, "我获取到数据源为" + ordersList.toString());
        //设置适配器
        mAdapter = new ActOrderResultAdapter(CustomApplcation.getInstance().context, ordersList, this);
        mXlv.setAdapter(mAdapter);
    }

    @Override
    public void sendDiscuss(String text, String goodsId, String userId) {
        view.showLoadingDialog("", "评论发布中", false);
        //获取系统时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss ");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String strTime = formatter.format(curDate);
        final Discuss discuss = new Discuss();
        //设置数据
        discuss.setDiscussUserId(userId);
        discuss.setDiscussTime(strTime);
        discuss.setGoodsId(goodsId);
        discuss.setDiscussText(text);
        //首先在保存评论到评论表中
        discuss.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                view.canelLoadingDialog();
                if (e == null) {
                    view.showMsg("评论发布成功!");
                    //更新订单状态
                } else {
                    view.showMsg("评论发布失败!");
                }
            }
        });
    }

    @Override
    public void updateOrders(int state, String ordersId) {
        view.showLoadingDialog("", "更新订单状态中...", false);
        Orders orders = new Orders();
        //更新订单状态
        orders.setObjectId(ordersId);
        orders.setOrdersState(state);
        orders.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    view.showMsg("更新成功!");
                    //应该更新数据源
                    queryOrders(-1);

                } else {
                    view.canelLoadingDialog();
                    view.showMsg("更新状态失败" + e.getLocalizedMessage());
                    LogUtils.i(TAG, "更新状态失败" + e.toString());
                }
            }
        });
    }

    /**
     * 查询订单信息
     *
     * @param type
     */
    public void queryOrders(int type) {
        BmobQuery<Orders> query = new BmobQuery<>();
        if (type != -1) {//不是-1为查询指定订单 -1查询全部订单
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
                    //更新数据源
                    initData();
                } else {
                    LogUtils.i(TAG, "查询订单信息失败" + e.toString());
                    view.showMsg("加载失败" + e.getLocalizedMessage());
                }
            }
        });
    }

    @Override
    public void pingJia(String goodsObjectId) {
        view.showAlertDialog(goodsObjectId);
    }

    @Override
    public void pay(String objectId, double oerdersMoney) {
        view.jumpActivity(objectId, oerdersMoney);
    }
}
