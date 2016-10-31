package com.xiaoguang.xtaobao.presenter;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.xiaoguang.xtaobao.R;
import com.xiaoguang.xtaobao.adapter.FragShopcarAdapter;
import com.xiaoguang.xtaobao.application.CustomApplcation;
import com.xiaoguang.xtaobao.bean.Orders;
import com.xiaoguang.xtaobao.bean.ShopCar;
import com.xiaoguang.xtaobao.contract.IFragShopCarContract;
import com.xiaoguang.xtaobao.util.LogUtils;
import com.xiaoguang.xtaobao.util.ToastFactory;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import static cn.bmob.v3.Bmob.getApplicationContext;
import static com.xiaoguang.xtaobao.adapter.FragShopcarAdapter.getIsSelected;

/**
 * Created by 11655 on 2016/10/19.
 */

public class FragShopCarPresenterImpl implements IFragShopCarContract.IFragShopCarPresenter {

    private IFragShopCarContract.IFragShopCarView view;
    //声明控件对象
    private SwipeMenuListView mLv;
    private CheckBox mCb;
    private FragShopcarAdapter adapter;
    private TextView mTvMoney;
    //数据源
    private List<ShopCar> listShopCas;
    //存放购物车中商品的Id
    List<String> listGoodsIds  = new ArrayList<>();
    //存放选中的购物车id
    List<String> checkedShopCars = new ArrayList<>();

    public FragShopCarPresenterImpl(IFragShopCarContract.IFragShopCarView view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void initData() {
        //获取控件
        mLv = view.getmFragShopcarLv();
        mCb = view.getmFragShopCarCb();
        mTvMoney = view.getmFragShopCarTvMoney();
        //设置适配器
        HashMap<Integer, Boolean> isChecked = new HashMap<>();
        isChecked.put(0, false);
        isChecked.put(1, false);
        isChecked.put(2, false);
        isChecked.put(3, false);
        isChecked.put(4, false);
        listShopCas = new ArrayList<>();
        FragShopcarAdapter.setIsSelected(isChecked);

        //添加侧滑按钮
        addItemButton();
        //初始化监听事件
        initEvent();
    }

    /**
     * 从网络上查询购物车的信息，
     */
    @Override
    public void queryDatasFromServer() {
        view.showLoadingDialog("", "数据加载中...", false);
        BmobQuery<ShopCar> query = new BmobQuery<>();
        //根据当前用户查询购物车信息
        query.addWhereEqualTo("userId", BmobUser.getCurrentUser().getObjectId());
        query.findObjects(new FindListener<ShopCar>() {
            @Override
            public void done(List<ShopCar> list, BmobException e) {
                if (e == null) {
                    view.canelLoadingDialog();
                    if (list!=null){
                        listShopCas = list;
                        LogUtils.i(TAG, "我查询到的购物车" + list.toString());
                        //初始化适配器
                        adapter = new FragShopcarAdapter(CustomApplcation.getInstance().context,
                                listShopCas, FragShopCarPresenterImpl.this);
                        //设置适配器
                        mLv.setAdapter(adapter);
                    }else {
                        view.showMsg("购物车为空!");
                    }

                } else {
                    ToastFactory.getToast(CustomApplcation.getInstance().context, "数据加载失败...");
                }
            }
        });
    }

    /**
     * 数据改变
     */
    private void dataChanged() {
        // 通知listView刷新
        adapter.notifyDataSetChanged();
    }


    @Override
    public void changeMoney(int count, double price) {
        //显示为原来的加上选中的
        double sum = count * price;
        double oldSum = Double.parseDouble(mTvMoney.getText().toString());
        double newSum = oldSum + sum;
        //保留小数点后两位
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(2);
        String strMoney = numberFormat.format(newSum);
        if (newSum >0) {
            mTvMoney.setText(strMoney);
        } else {
            mTvMoney.setText("0.00");
        }
    }

    @Override
    public void submit() {
        view.showLoadingDialog("","提交中....",false);
        //提交订单
        //l
        //获取数据
        final double sum = Double.parseDouble(mTvMoney.getText().toString());
        //设置数据
        Orders orders = new Orders();
        orders.addAll("goodIds",listGoodsIds);
        orders.setUserId(CustomApplcation.getInstance().getCurrentUser().getObjectId());
        orders.setOrdersState(0);//设置订单状态为代付款0
        orders.setOerdersMoney(sum);
        //设置默认商品数量为1
        orders.setGoodsCount(1);
        orders.save(new SaveListener<String>() {
            @Override
            public void done(final String objectId, BmobException e) {
                if (e == null){
                    LogUtils.i(TAG,"下单成功");
                    //删除已经购买的购物车中的数据
                    List<BmobObject> shopCarLists = new ArrayList<BmobObject>();
                    for (int i = 0; i < checkedShopCars.size(); i++) {
                        ShopCar shopCar = new ShopCar();
                        shopCar.setObjectId(checkedShopCars.get(i));
                        shopCarLists.add(shopCar);
                    }
                    adapter.notifyDataSetChanged();
                    //批量删除数据
                    new BmobBatch().deleteBatch(shopCarLists).doBatch(new QueryListListener<BatchResult>() {
                        @Override
                        public void done(List<BatchResult> list, BmobException e) {
                            view.canelLoadingDialog();
                            if (e==null){
                                //跳转到支付页面
                                view.showMsg("下单成功,将跳转到支付页面");
                                view.jumpActivity(objectId,sum);
                                LogUtils.i(TAG,"删除数据成功");
                            }else {
                                LogUtils.i(TAG,"删除数据成功"+e.toString());
                            }
                        }
                    });
                }else {
                    view.showMsg("下单失败!"+e.getLocalizedMessage());
                    LogUtils.i(TAG,"下单失败"+e.toString());
                }
            }
        });
    }

    /**
     * 为listview添加侧滑删除功能
     */
    private void addItemButton() {
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                openItem.setWidth(90);
                // set item title
                openItem.setTitle("修改");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(90);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        mLv.setMenuCreator(creator);
    }
    /**
     * 初始化监听事件
     */
    private void initEvent() {
        //给check 设置选中事件
        mCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {//全选
                    // 遍历list的长度，将MyAdapter中的map值全部设为true
                    for (int i = 0; i < 5; i++) {
                        getIsSelected().put(i, true);
                    }
                    // 刷新listview和TextView的显示
                    dataChanged();
                    mCb.setText("取消全选");
                } else {//取消全选
                    // 遍历list的长度，将已选的按钮设为未选
                    for (int i = 0; i < 5; i++) {
                        if (FragShopcarAdapter.getIsSelected().get(i)) {
                            FragShopcarAdapter.getIsSelected().put(i, false);
                        }
                    }
                    // 刷新listview和TextView的显示
                    dataChanged();
                    mCb.setText("全选");
                }
            }
        });

        mLv.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                //获取选中的购物车中的id
                //
                LogUtils.i(TAG,"我的购物车中的"+listShopCas.size());
                String itemObjectId = listShopCas.get(position).getObjectId();
                LogUtils.i("myTag", "我选中" + position);
                switch (index) {
                    case 0:
                        LogUtils.i(TAG, "我选中了修改过按钮" + position);
                        // 修改收货地址
                        open(itemObjectId);
                        break;
                    case 1:
                        LogUtils.i(TAG, "我选中了删除按钮" + itemObjectId);
                        // 删除收货地址
                        delete(itemObjectId);
                        listShopCas.remove(position);
                        adapter.notifyDataSetChanged();
                        break;
                }
                return false;
            }
        });
    }

    /**
     * 删除购物车中的商品
     * @param item
     */
    private void delete(String item) {
        view.showLoadingDialog("","数据更新中...",false);
        ShopCar shopCar = new ShopCar();
        shopCar.setObjectId(item);
        shopCar.delete(new UpdateListener() {

            @Override
            public void done(BmobException e) {
                view.canelLoadingDialog();
                if(e==null){
                    Log.i(TAG,"成功");
                    view.showMsg("更新成功.");
                }else{
                    Log.i(TAG,"失败："+e.getMessage()+","+e.getErrorCode());
                    view.showMsg("更新失败..");
                }
            }
        });
    }
    @Override
    public void saveGoodIds(String goodId) {
        listGoodsIds.add(goodId);
    }

    @Override
    public void removeGoodIds(String goodId) {
        listGoodsIds.remove(goodId);
    }

    @Override
    public void saveShopCarIds(String objectId) {
        checkedShopCars.add(objectId);
    }

    @Override
    public void removShopCarIds(String objectId) {
        checkedShopCars.remove(objectId);
    }

    /**
     * 修改购物车中的商品
     * @param item
     */
    private void open(String item) {

    }
}
