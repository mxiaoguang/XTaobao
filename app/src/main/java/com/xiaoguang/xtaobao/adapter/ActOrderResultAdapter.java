package com.xiaoguang.xtaobao.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xiaoguang.xtaobao.R;
import com.xiaoguang.xtaobao.application.CustomApplcation;
import com.xiaoguang.xtaobao.bean.Goods;
import com.xiaoguang.xtaobao.bean.Orders;
import com.xiaoguang.xtaobao.contract.IOrdersResultContract;
import com.xiaoguang.xtaobao.util.ToastFactory;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * 我的订单页面的适配器
 * Created by 11655 on 2016/10/25.
 */

public class ActOrderResultAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    //数据源
    private List<Orders> ordersList;
    private Context context;
    IOrdersResultContract.IOrdersResultPresenter presenter;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    //订单中的商品
    private Goods goods;

    public ActOrderResultAdapter(Context context, List<Orders> ordersList, IOrdersResultContract.IOrdersResultPresenter presenter) {
        inflater = LayoutInflater.from(context);
        this.ordersList = ordersList;
        this.context = context;
        this.presenter = presenter;
    }

    @Override
    public int getCount() {
        return ordersList.size();
    }

    @Override
    public Object getItem(int position) {
        return ordersList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.act_orders_xlv_item, null);
            viewHolder = new ViewHolder();
            //获取控件
            viewHolder.mIvGoodsImg = (ImageView) convertView.findViewById(R.id.act_order_result_xlv_item_good_img);
            viewHolder.mTvGoodName = (TextView) convertView.findViewById(R.id.act_order_result_item_good_name);
            viewHolder.mTvPrice = (TextView) convertView.findViewById(R.id.act_order_result_xlv_item_good_price);
            viewHolder.mTvCount = (TextView) convertView.findViewById(R.id.act_order_result_xlv_item_good_count);
            viewHolder.mTvOrdersState = (TextView) convertView.findViewById(R.id.act_order_result_xlv_item_state);
            viewHolder.mBtnCanel = (Button) convertView.findViewById(R.id.act_order_result_xlv_item_canel_order);
            viewHolder.mBtnPay = (Button) convertView.findViewById(R.id.act_order_result_xlv_item__pay);
            viewHolder.mBtnShouhuo = (Button) convertView.findViewById(R.id.act_order_result_xlv_item_queren);
            viewHolder.mBtnPingjia = (Button) convertView.findViewById(R.id.act_order_result_xlv_item_pingjia);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //设置数据
        //查询数据，暂时查询订单中每条订单的一个商品信息
        queryDatas(viewHolder, ordersList.get(position).getGoodIds().get(0), position);
        viewHolder.mTvCount.setText(ordersList.get(position).getGoodsCount() + "");
        switch (ordersList.get(position).getOrdersState()) {
            case 0:
                viewHolder.mTvOrdersState.setText("等待买家付款");
                //显示付款和取消订单按钮
                viewHolder.mBtnPay.setVisibility(View.VISIBLE);
                viewHolder.mBtnCanel.setVisibility(View.VISIBLE);
                //隐藏其他按钮
                viewHolder.mBtnShouhuo.setVisibility(View.GONE);
                viewHolder.mBtnPingjia.setVisibility(View.GONE);
                break;
            case 1:
                viewHolder.mTvOrdersState.setText("等待卖家发货");
                //隐藏其他按钮
                viewHolder.mBtnPay.setVisibility(View.GONE);
                viewHolder.mBtnCanel.setVisibility(View.GONE);
                viewHolder.mBtnShouhuo.setVisibility(View.GONE);
                viewHolder.mBtnPingjia.setVisibility(View.GONE);
                break;
            case 2:
                viewHolder.mTvOrdersState.setText("等待买家收货");
                viewHolder.mBtnPay.setVisibility(View.GONE);
                viewHolder.mBtnCanel.setVisibility(View.GONE);
                viewHolder.mBtnShouhuo.setVisibility(View.VISIBLE);
                viewHolder.mBtnPingjia.setVisibility(View.GONE);
                break;
            case 3:
                viewHolder.mTvOrdersState.setText("等待买家评价");
                viewHolder.mBtnPay.setVisibility(View.GONE);
                viewHolder.mBtnCanel.setVisibility(View.GONE);
                viewHolder.mBtnShouhuo.setVisibility(View.GONE);
                viewHolder.mBtnPingjia.setVisibility(View.VISIBLE);
                break;
            case 4:
                viewHolder.mTvOrdersState.setText("退款中");
                viewHolder.mBtnPay.setVisibility(View.GONE);
                viewHolder.mBtnCanel.setVisibility(View.GONE);
                viewHolder.mBtnShouhuo.setVisibility(View.GONE);
                viewHolder.mBtnPingjia.setVisibility(View.GONE);
                break;
            case 5:
                viewHolder.mTvOrdersState.setText("取消的订单");
                viewHolder.mBtnPay.setVisibility(View.GONE);
                viewHolder.mBtnCanel.setVisibility(View.GONE);
                viewHolder.mBtnShouhuo.setVisibility(View.GONE);
                viewHolder.mBtnPingjia.setVisibility(View.GONE);
        }
        //为按钮添加点击事件
        initEvent(viewHolder,position);
        return convertView;
    }

    /**
     * 为按钮设置点击事件
     *
     * @param viewHolder
     */
    private void initEvent(ViewHolder viewHolder, final int position) {
        viewHolder.mBtnCanel.setOnClickListener(new View.OnClickListener() {//取消按钮的点击事件
            @Override
            public void onClick(View v) {
                //取消订单
                presenter.updateOrders(5,ordersList.get(position).getObjectId());
            }
        });
        viewHolder.mBtnPay.setOnClickListener(new View.OnClickListener() {//付款按钮的点击事件
            @Override
            public void onClick(View v) {
                //支付订单
                presenter.pay(ordersList.get(position).getObjectId(),ordersList.get(position).getOerdersMoney());
            }
        });
        viewHolder.mBtnShouhuo.setOnClickListener(new View.OnClickListener() {//收货按钮的点击事件
            @Override
            public void onClick(View v) {
                //确认订单
                presenter.updateOrders(2,ordersList.get(position).getObjectId());
            }
        });

        //为评价按钮设置点击事件
        viewHolder.mBtnPingjia.setOnClickListener(new View.OnClickListener() {//评论按钮的点击事件
            @Override
            public void onClick(View v) {
                presenter.pingJia(ordersList.get(position).getGoodIds().get(0));
            }
        });
    }

    /**
     * 查询商品信息
     *
     * @param goodId
     */
    private void queryDatas(final ViewHolder holder, final String goodId, final int position) {
        BmobQuery<Goods> query = new BmobQuery();
        query.getObject(goodId, new QueryListener<Goods>() {
            @Override
            public void done(final Goods goods, BmobException e) {
                if (e == null) {
                    //显示图片
                    ActOrderResultAdapter.this.goods = goods;
                    Picasso.with(context).load(goods.getGoodsImgs().get(0).getUrl()).into(holder.mIvGoodsImg);
                    //设置价格
                    holder.mTvPrice.setText("¥ " + goods.getGoodsPrice());
                    holder.mTvGoodName.setText(goods.getGoodsName());

                } else {
                    ToastFactory.getToast(CustomApplcation.getInstance().context, "数据加载失败...").show();
                }
            }
        });
    }

    public static class ViewHolder {
        public ImageView mIvGoodsImg;
        public TextView mTvGoodName, mTvPrice, mTvCount, mTvOrdersState;
        public Button mBtnCanel, mBtnPay, mBtnShouhuo, mBtnPingjia;
    }
}
