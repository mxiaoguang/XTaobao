package com.xiaoguang.xtaobao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xiaoguang.xtaobao.R;
import com.xiaoguang.xtaobao.application.CustomApplcation;
import com.xiaoguang.xtaobao.bean.Goods;
import com.xiaoguang.xtaobao.bean.ShopCar;
import com.xiaoguang.xtaobao.contract.IFragShopCarContract;
import com.xiaoguang.xtaobao.util.LogUtils;
import com.xiaoguang.xtaobao.util.ToastFactory;

import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by 11655 on 2016/10/25.
 */

public class FragShopcarAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    //数据源
    private List<ShopCar> shopCars;
    // 用来控制CheckBox的选中状况
    private static HashMap<Integer, Boolean> isSelected;
    IFragShopCarContract.IFragShopCarPresenter presenter;

    private Context context;

    public FragShopcarAdapter(Context context, List<ShopCar> shopCars, IFragShopCarContract.IFragShopCarPresenter presenter) {
        inflater = LayoutInflater.from(context);
        this.shopCars = shopCars;
        isSelected = new HashMap<Integer, Boolean>();
        this.context = context;
        this.presenter = presenter;

        // 初始化数据
        initDate();
    }

    @Override
    public int getCount() {
        return shopCars.size();
    }

    @Override
    public Object getItem(int position) {
        return shopCars.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.frag_shop_car_xlv_item, null);
            viewHolder = new ViewHolder();
            viewHolder.mIvGoodsImg = (ImageView) convertView.findViewById(R.id.frag_shop_car_xlv_item_good_img);
            viewHolder.mTvGoodName = (TextView) convertView.findViewById(R.id.frag_shop_car_xlv_item_good_name);
            viewHolder.mTvPrice = (TextView) convertView.findViewById(R.id.frag_shop_car_xlv_item_good_price);
            viewHolder.mTvCount = (TextView) convertView.findViewById(R.id.frag_shop_car_xlv_item_good_count);
            viewHolder.mcb = (CheckBox) convertView.findViewById(R.id.frag_shop_car_xlv_item_cb);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //设置数据
        // 根据isSelected来设置checkbox的选中状况
        viewHolder.mcb.setChecked(getIsSelected().get(position));
        final ViewHolder finalViewHolder = viewHolder;
        //查询id查询商品信息
        queryDatas(finalViewHolder, shopCars.get(position).getGoodId(), position);
        //设置数量
        finalViewHolder.mTvCount.setText(shopCars.get(position).getCount() + "");
        //---------------------------------------------

        //---------------------------------------------


        return convertView;
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
                    Picasso.with(context).load(goods.getGoodsImgs().get(0).getUrl()).into(holder.mIvGoodsImg);
                    //设置价格
                    holder.mTvPrice.setText("¥ " + goods.getGoodsPrice());
                    holder.mTvGoodName.setText(goods.getGoodsName());
                    holder.mcb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                //将选中的item的钱显示到下面
                                LogUtils.i("myTag","我被选中了n");
                                presenter.changeMoney(shopCars.get(position).getCount(), goods.getGoodsPrice());
                                //将选中的商品Id保存起来
                                presenter.saveGoodIds(shopCars.get(position).getGoodId());
                                //将选中的item的购物车id保存起来
                                presenter.saveShopCarIds(shopCars.get(position).getObjectId());
                            } else {
                                //取消选中的则剔除
                                LogUtils.i("myTag","我取消选中了呢");
                                presenter.changeMoney(shopCars.get(position).getCount(),-goods.getGoodsPrice());
                                //移除商品id
                                presenter.removeGoodIds(shopCars.get(position).getGoodId());
                                //移除取消选中的itmd的购物车id
                                presenter.removShopCarIds(shopCars.get(position).getObjectId());
                            }
                        }
                    });

                } else {
                    ToastFactory.getToast(CustomApplcation.getInstance().context, "数据加载失败...").show();
                }
            }
        });
    }

    // 初始化isSelected的数据i<5暂时固定数据测试
    private void initDate() {
        for (int i = 0; i < 5; i++) {
            getIsSelected().put(i, false);
        }
    }

    public static HashMap<Integer, Boolean> getIsSelected() {
        return isSelected;
    }

    public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
        FragShopcarAdapter.isSelected = isSelected;
    }

    public static class ViewHolder {
        public ImageView mIvGoodsImg;
        public TextView mTvGoodName, mTvPrice, mTvCount;
        public CheckBox mcb;
    }
}
