package com.xiaoguang.xtaobao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xiaoguang.xtaobao.R;
import com.xiaoguang.xtaobao.application.CustomApplcation;
import com.xiaoguang.xtaobao.bean.Goods;
import com.xiaoguang.xtaobao.util.LogUtils;

import java.util.List;

/**
 * 商品展示的适配器
 * Created by 11655 on 2016/10/23.
 */

public class ActGoodsResultAdapter extends BaseAdapter {
    private List<Goods> goodsList;
    private LayoutInflater inflater;

    public ActGoodsResultAdapter(List<Goods> goodsList, Context context) {
        this.goodsList = goodsList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return goodsList.size();
    }

    @Override
    public Object getItem(int position) {
        return goodsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder  vh = null;
        if (convertView==null){
            convertView = inflater.inflate(R.layout.act_goods_result_gv_item,null);
            vh = new ViewHolder();
            vh.imageView = (ImageView) convertView.findViewById(R.id.act_goods_result_gv_item_iv);
            vh.textView = (TextView) convertView.findViewById(R.id.act_goods_result_gv_item_tv_name);
            vh.mTvMoney = (TextView) convertView.findViewById(R.id.act_goods_result_gv_item_tv_money);
            convertView.setTag(vh);
        }else {
            vh = (ViewHolder) convertView.getTag();
        }
        //设置数据
        Goods goods = goodsList.get(position);
        LogUtils.i("myTag","我的适配器中的数据"+goods.toString());
        vh.textView.setText(goods.getGoodsName());
        vh.mTvMoney.setText("¥ "+goods.getGoodsPrice());
        //默认显示第一张图片
        Picasso.with(CustomApplcation.getInstance().context)
                .load(goods.getGoodsImgs().get(0).getUrl())
                .into(vh.imageView);
        return convertView;
    }
    class ViewHolder{
        TextView textView,mTvMoney;
        ImageView imageView;
    }
}
