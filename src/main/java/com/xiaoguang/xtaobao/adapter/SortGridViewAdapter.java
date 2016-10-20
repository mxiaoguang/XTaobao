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
 * Created by 11655 on 2016/10/19.
 */

public class SortGridViewAdapter extends BaseAdapter {
    //图片资源id
    private Integer[] sortImgs;
    //文字资源
    private String[] sortStrs;
    //布局文件
    private int resourse;
    //布局填充器
    LayoutInflater inflater;
    public SortGridViewAdapter(Context context, Integer[] sortImgs, String[] sortStrs,int resourse) {
        this.sortImgs = sortImgs;
        this.sortStrs = sortStrs;
        this.resourse = resourse;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return sortImgs.length;
    }

    @Override
    public Object getItem(int position) {
        return sortImgs[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView==null){
            convertView = inflater.inflate(resourse,null);
            vh = new ViewHolder();
            vh.imageView = ((ImageView) convertView.findViewById(R.id.grid_view_iv));
            vh.textView = ((TextView) convertView.findViewById(R.id.grid_view_tv));
            convertView.setTag(vh);
        }else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.imageView.setImageResource(sortImgs[position]);
        vh.textView.setText(sortStrs[position]);
        return convertView;
    }
    class ViewHolder{
        ImageView imageView;
        TextView textView;
    }
}
