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
import com.xiaoguang.xtaobao.bean.GoodsType;

import java.util.List;

/**
 * Created by 11655 on 2016/10/23.
 */

public class ActGoodsTypeAdapter extends BaseAdapter {
    private List<GoodsType> goodsTypeList;
    private LayoutInflater inflater;

    public ActGoodsTypeAdapter(List<GoodsType> goodsTypeList, Context context) {
        this.goodsTypeList = goodsTypeList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return goodsTypeList.size();
    }

    @Override
    public Object getItem(int position) {
        return goodsTypeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder  vh = null;
        if (convertView==null){
            convertView = inflater.inflate(R.layout.act_goods_type_gv_item,null);
            vh = new ViewHolder();
            vh.imageView = (ImageView) convertView.findViewById(R.id.act_goods_type_gv_item_iv);
            vh.textView = (TextView) convertView.findViewById(R.id.act_goods_type_gv_item_tv);
            convertView.setTag(vh);
        }else {
            vh = (ViewHolder) convertView.getTag();
        }
        //设置数据
        GoodsType goodsType = goodsTypeList.get(position);
        vh.textView.setText(goodsType.getTypeName());
        Picasso.with(CustomApplcation.getInstance().context)
                .load(goodsType.getTypeImg().getUrl())
                .into(vh.imageView);
        return convertView;
    }
    class ViewHolder{
        TextView textView;
        ImageView imageView;
    }
}
