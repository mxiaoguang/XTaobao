package com.xiaoguang.xtaobao.presenter;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ArrayAdapter;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.xiaoguang.xtaobao.R;
import com.xiaoguang.xtaobao.application.CustomApplcation;
import com.xiaoguang.xtaobao.bean.User;
import com.xiaoguang.xtaobao.contract.IAddressContract;
import com.xiaoguang.xtaobao.util.LogUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

import static cn.bmob.v3.Bmob.getApplicationContext;

/**
 * Created by 11655 on 2016/10/22.
 */

public class ActAddressPersenterImpl implements IAddressContract.IAddressPresenter {

    private IAddressContract.IAddressView view;
    private SwipeMenuListView maddressLv;
    //数据源
    private List<String> datas;
    //适配器
    private ArrayAdapter<String> arrayAdapter;
    //保存当前选中的位置
    private int position;

    public ActAddressPersenterImpl(IAddressContract.IAddressView view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void initData() {
        //获取控件
        maddressLv = view.getmActAddressLv();
        //初始化数据源,以下数据应为网络获取
        datas = new ArrayList<>();
        for (int i = 0; i < BmobUser.getCurrentUser(User.class).getAddressLists().size(); i++) {
            //获取并设置用户的收货地址
            datas.add(BmobUser.getCurrentUser(User.class).getAddressLists().get(i));
        }
        //初始化适配器
      arrayAdapter = new ArrayAdapter<String>(CustomApplcation.getInstance().context,
                R.layout.act_address_change_lv_item, datas);
        // step 1. create a MenuCreator
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
        maddressLv.setMenuCreator(creator);
        //设置适配器
        maddressLv.setAdapter(arrayAdapter);
        // step 2. listener item click event
        maddressLv.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                String item = datas.get(position);
                LogUtils.i("myTag", "我选中" + position);
                switch (index) {
                    case 0:
                        LogUtils.i(TAG, "我选中了修改过按钮" + position);
                        // 修改收货地址
                        ActAddressPersenterImpl.this.position = position;
                        open(item);
                        break;
                    case 1:
                        LogUtils.i(TAG, "我选中了删除按钮" + position);
                        ActAddressPersenterImpl.this.position = position;
                        // 删除收货地址
                        delete(item);
                        datas.remove(position);
                        arrayAdapter.notifyDataSetChanged();
                        break;
                }
                return false;
            }
        });
    }

    /**
     * 删除收货地址的方法
     *
     * @param item 收货地址
     */
    private void delete(String item) {
        view.showLoadingDialog("","更新信息中...",false);
        User user = new User();
        user.setObjectId(BmobUser.getCurrentUser().getObjectId());
        user.removeAll("addressLists", Arrays.asList(item));
        user.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                view.canelLoadingDialog();
                if (e==null){
                    view.showMsg("更新信息成功!");
                }
                else {
                    view.showMsg("更新数据失败");
                }
            }
        });
    }

    /**
     * 修改收货地址的方法
     *
     * @param item
     */
    private void open(String item) {
        //显示一警告对话框
        view.showAlerDialog(null, null, true);
    }

    @Override
    public void updateAddress(String text, int type) {
        view.showLoadingDialog("","更新信息中...",false);
        User user = new User();
        if (type==1){//新增地址
            datas.add(text);
        }else if (type==2){//修改地址
            //先将选中位置移除
            datas.remove(position);
            //在将已有位置进行替换
            datas.add(position,text);
        } else{
            return;
        }
        user.setAddressLists(datas);
        //更新数据
        user.update(BmobUser.getCurrentUser().getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                view.canelLoadingDialog();
                if (e==null){
                    view.showMsg("更新信息成功!");
                    arrayAdapter.notifyDataSetChanged();

                }else {
                    view.showMsg("更新信息失败!"+e.getLocalizedMessage());
                }
            }
        });
    }
}

