package com.xiaoguang.xtaobao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoguang.xtaobao.R;

/**
 * Created by 11655 on 2016/10/20.
 */

public class ContentGridViewAdapter extends BaseAdapter {
    //初始化数据源
    private Integer[] contentIcoImgs;
    private Integer[] contentImgs;
    private String[] contentText;
    private String[] contentText2;
    private LayoutInflater inflater;
    public ContentGridViewAdapter(Context context, Integer[] contentIcoImgs, Integer[] contentImgs, String[] contentText, String[] contentText2) {
        this.contentIcoImgs = contentIcoImgs;
        this.contentImgs = contentImgs;
        this.contentText = contentText;
        this.contentText2 = contentText2;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return contentIcoImgs.length;
    }

    @Override
    public Object getItem(int position) {
        return contentIcoImgs[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView==null){
            convertView = inflater.inflate(R.layout.frag_home_gv_content_item,null);
            vh =  new ViewHolder();
            vh.ivIco = (ImageView) convertView.findViewById(R.id.frag_home_gv_content_item_iv_ico);
            vh.ivGood1 = (ImageView) convertView.findViewById(R.id.frag_home_gv_content_item_iv_good1);
            vh.ivGood2 = (ImageView) convertView.findViewById(R.id.frag_home_gv_content_item_iv_good2);
            vh.tv1 = (TextView) convertView.findViewById(R.id.frag_home_gv_content_item_tv1);
            vh.tv2 = (TextView) convertView.findViewById(R.id.frag_home_gv_content_item_tv2);
            convertView.setTag(vh);
        }else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.ivIco.setImageResource(contentIcoImgs[position]);
        vh.ivGood1.setImageResource(contentImgs[position]);
        vh.ivGood2.setImageResource(contentImgs[position]);
        vh.tv1.setText(contentText[position]);
        vh.tv2.setText(contentText2[position]);
        return convertView;
    }
    class ViewHolder{
        ImageView ivIco,ivGood1,ivGood2;
        TextView tv1,tv2;
    }
}
