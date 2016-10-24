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
import com.xiaoguang.xtaobao.bean.Discuss;
import com.xiaoguang.xtaobao.bean.User;
import com.xiaoguang.xtaobao.config.Contracts;
import com.xiaoguang.xtaobao.contract.IGoodsDetailsContract;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 评论的适配器
 *
 * Created by 11655 on 2016/10/14.
 */

public class DiscussXlvAdapter extends BaseAdapter {
    //定义一个数据源
    private List<Discuss> discussList;
    private Context context;
    //    private LayoutInflater inflater;
    //定义p层对象
    private IGoodsDetailsContract.IGoodsDetailsPresenter presenter;
    public DiscussXlvAdapter(List<Discuss> discussList, Context context,
                             IGoodsDetailsContract.IGoodsDetailsPresenter presenter) {
        this.discussList = discussList;
        this.context = context;
        this.presenter = presenter;
    }

    @Override
    public int getCount() {
        return discussList.size();
    }

    @Override
    public Object getItem(int position) {
        return discussList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.act_details_xlv_discuss_item, null);
            convertView.setTag(viewHolder);
            //获取控件
            viewHolder.circleImageViewHead = (CircleImageView) convertView.findViewById(R.id.act_detials_xlv_item_civ_head);
            viewHolder.imgSex = (ImageView) convertView.findViewById(R.id.act_detials_xlv_item_iv_sex);
            viewHolder.tvDiscussText = (TextView) convertView.findViewById(R.id.act_detials_xlv_item_tv_discuss_text);
            viewHolder.tvUname = (TextView) convertView.findViewById(R.id.act_detials_xlv_item_tv_usename);
            viewHolder.tvDiscusTime = (TextView) convertView.findViewById(R.id.act_detials_xlv_item_tv_discuss_time);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //设置数据
        viewHolder.tvDiscussText.setText(discussList.get(position).getDiscussText());
        viewHolder.tvDiscusTime.setText(discussList.get(position).getDiscussTime());
        //查询头像信息
        final ViewHolder finalViewHolder = viewHolder;
        BmobQuery<User> query = new BmobQuery<>();
        //根据用户Id查询用户信息
        query.getObject(discussList.get(position).getDiscussUserId(), new QueryListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    if (user.getUserHead().getUrl()==null){//头像为空,则显示默认头像
                        Picasso.with(context).load(Contracts.DEFALT_HEAD_URL).into(finalViewHolder.circleImageViewHead);
                    }else {
                        //查询成功,设置头像
                        Picasso.with(context).load(user.getUserHead().getUrl()).into(finalViewHolder.circleImageViewHead);
                    }
                    //设置用户名
                    finalViewHolder.tvUname.setText(user.getNickName());
                    //设置性别
                    if(user.getSex()=="男"){
                        finalViewHolder.imgSex.setImageResource(R.drawable.boy);
                    }else {
                        finalViewHolder.imgSex.setImageResource(R.drawable.gril);
                    }
                    //查询信息成功
                    presenter.queryUserSuccess();
                } else {
                    //查询失败
                    presenter.queryUseError(e);
                }
            }
        });
        return convertView;
    }

    class ViewHolder {
        CircleImageView circleImageViewHead;
        ImageView imgSex;
        TextView tvUname, tvDiscussText, tvDiscusTime;
    }
}
