package com.xiaoguang.xtaobao.presenter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jude.rollviewpager.RollPagerView;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXTextObject;
import com.xiaoguang.xtaobao.R;
import com.xiaoguang.xtaobao.adapter.DiscussXlvAdapter;
import com.xiaoguang.xtaobao.adapter.GoodsRollPagerViewAdapter;
import com.xiaoguang.xtaobao.application.CustomApplcation;
import com.xiaoguang.xtaobao.bean.Discuss;
import com.xiaoguang.xtaobao.bean.Goods;
import com.xiaoguang.xtaobao.bean.ShopCar;
import com.xiaoguang.xtaobao.bean.User;
import com.xiaoguang.xtaobao.config.Contracts;
import com.xiaoguang.xtaobao.contract.IGoodsDetailsContract;
import com.xiaoguang.xtaobao.util.LogUtils;
import com.xiaoguang.xtaobao.widget.XListView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 11655 on 2016/10/23.
 */

public class ActGoodsDetailsPresenterImpl implements IGoodsDetailsContract.IGoodsDetailsPresenter {

    //定义控件
    private RollPagerView mRollVpAd;
    private TextView mTvGoodsName;
    private TextView mTvGoodsMoney;
    private XListView mXlvPl;
    private Button mBtnShoucang;
    private IGoodsDetailsContract.IGoodsDetailsView view;
    //商品
    private Goods goods;
    //微信Api
    private IWXAPI api;

    public ActGoodsDetailsPresenterImpl(IGoodsDetailsContract.IGoodsDetailsView view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void initData() {

        //获取控件
        mRollVpAd = view.getmActGoodsDetailsRollVpAd();
        mTvGoodsName = view.getmActGoodsDetailsTvGoodsName();
        mTvGoodsMoney = view.getmActGoodsDetailsTvMoney();
        mXlvPl = view.getmActGoodsDetailsXlv();
        mBtnShoucang = view.getmActGoodsDetailsBtnShoucang();

        //获取数据
        goods = (Goods) CustomApplcation.getDatas("goods", false);
        /*设置数据*/
        mRollVpAd.setAdapter(new GoodsRollPagerViewAdapter(goods.getGoodsImgs()));
        mTvGoodsName.setText(goods.getGoodsName());
        mTvGoodsMoney.setText("¥ " + goods.getGoodsPrice());

        /*评论功能*/
        //查询根据获取商品的id查询所有评论,并显示
        queryDiscuss(goods.getObjectId());
        //收藏与取消收藏
        showShoucang(goods);

        /*初始化微信分享功能*/
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(CustomApplcation.getInstance().context, Contracts.WX_APP_ID, false);
        // 将该app注册到微信
        api.registerApp(Contracts.WX_APP_ID );

    }

    /**
     * 查询评论并设置评论
     *
     * @param objectId 当前商品的id
     */
    private void queryDiscuss(String objectId) {
        LogUtils.i(TAG, "查询评论的参数为" + objectId);
        BmobQuery<Discuss> query = new BmobQuery<>();
        //添加查询条件
        query.addWhereEqualTo("goodsId", objectId + "");
        query.findObjects(new FindListener<Discuss>() {
            @Override
            public void done(List<Discuss> list, BmobException e) {
                if (e == null) {
                    LogUtils.i(TAG, "评论查询成功");
                    LogUtils.i(TAG, "评论信息为" + list.toString());
                    //加载成功，设置适配器
                    mXlvPl.setAdapter(new DiscussXlvAdapter(list,
                            CustomApplcation.getInstance().context, ActGoodsDetailsPresenterImpl.this));
                } else {
                    view.showMsg("评论加载失败 !" + e.getLocalizedMessage());
                    LogUtils.i(TAG, "查询数据失败" + e.toString());
                }
            }
        });
    }

    /**
     * 收藏和取消收藏
     *
     * @param goods
     */
    private void showShoucang(final Goods goods) {
        final Holder holder = new Holder();

        //首先判断用户是否登陆过
        if (BmobUser.getCurrentUser(User.class) == null) {//没有登陆过
            mBtnShoucang.setOnClickListener(new View.OnClickListener() {//当按钮点击时就跳转到登陆界面
                @Override
                public void onClick(View v) {
                    view.jumpLogin();
                }
            });
        } else {//当前用户登陆过
            //将当前用户信息保存到内存中
            CustomApplcation.getInstance().setCurrentUser(BmobUser.getCurrentUser(User.class));
            //用于标记是否点击过
            holder.flag = goods.getLoveUserIds().contains(
                    CustomApplcation.getInstance().getCurrentUser().getObjectId());
            //获取当前活动收藏的用户,判断是否出现过
            LogUtils.i(TAG, "从服务器获取的点赞为" + holder.flag);
            if (holder.flag) {//收藏了
                //显示红色按下效果
                mBtnShoucang.setCompoundDrawablesWithIntrinsicBounds(0,
                        R.drawable.act_goods_details_shoucang_press, 0, 0);
            } else {
                //显示灰心效果
                mBtnShoucang.setCompoundDrawablesWithIntrinsicBounds(0,
                        R.drawable.act_goods_details_shoucang, 0, 0);
            }
            //为点赞按钮添加点击事件
            mBtnShoucang.setOnClickListener(new View.OnClickListener() {
                //用于标记是否点击过
                @Override
                public void onClick(final View v) {
                    if (holder.flag) {//为true为点赞过,取消点赞
                        LogUtils.i(TAG, "我正在执行取消点赞 ");
                        final ArrayList<String> uids = new ArrayList<String>();
                        uids.add(CustomApplcation.getInstance().getCurrentUser().getObjectId());
                        Goods newGoods = new Goods();
                        newGoods.removeAll("loveUserIds", uids);
                        //更新服务器数据
                        newGoods.update(goods.getObjectId(),
                                new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null) {
                                            LogUtils.i(TAG, "当前商品中移除用户成功");
                                            //将user表移除
                                            User user = new User();
                                            ArrayList<String> goodIds = new ArrayList<String>();
                                            goodIds.add(goods.getObjectId());
                                            user.removeAll("loveGoodsIds", goodIds);
                                            user.update(CustomApplcation.getInstance().getCurrentUser().getObjectId(),
                                                    new UpdateListener() {
                                                        @Override
                                                        public void done(BmobException e) {
                                                            if (e == null) {
                                                                LogUtils.i(TAG, "当前用户移除当前活动成功");
                                                                view.showMsg("取消收藏成功");
                                                                //显示灰心效果
                                                                mBtnShoucang.setCompoundDrawablesWithIntrinsicBounds(0,
                                                                        R.drawable.act_goods_details_shoucang, 0, 0);
                                                            } else {
                                                                LogUtils.i(TAG, "当前用户移除当前商品失败" + e.toString());
                                                                view.showMsg("取消收藏失败");
                                                            }
                                                        }
                                                    });
                                        } else {
                                            LogUtils.i(TAG, "当前商品中移除用户成功" + e.toString());
                                            view.showMsg("取消点赞失败，原因是" + e.getLocalizedMessage());
                                        }
                                    }
                                });
                    } else {//点赞
                        LogUtils.i(TAG, "我正在进行点赞操作 ");
                        //将当前用户Id保存
                        Goods newGoods = new Goods();
                        newGoods.add("loveUserIds", CustomApplcation.getInstance().getCurrentUser().getObjectId());

                        //更新数据到服务器
                        newGoods.update(goods.getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    LogUtils.i(TAG, "当前商品添加用户成功");
                                    //向user表添加活动
                                    User user = new User();
                                    //将当前的活动的Id添加到用户表中
                                    user.add("loveGoodsIds", goods.getObjectId());
                                    user.update(CustomApplcation.getInstance().getCurrentUser().getObjectId(),
                                            new UpdateListener() {
                                                @Override
                                                public void done(BmobException e) {
                                                    if (e == null) {
                                                        LogUtils.i(TAG, "当前用户添加当前活动成功");
                                                        view.showMsg("收藏成功!");
                                                        //设置图片
                                                        //显示灰心效果
                                                        mBtnShoucang.setCompoundDrawablesWithIntrinsicBounds(0,
                                                                R.drawable.act_goods_details_shoucang_press, 0, 0);
                                                    } else {
                                                        LogUtils.i(TAG, "当前用户添加当前活动失败" + e.toString());
                                                        view.showMsg("收藏失败!");
                                                    }
                                                }
                                            });
                                } else {
                                    LogUtils.i(TAG, "当前商品添加当前用户失败，原因 " + e.toString());
                                    view.showMsg("收藏失败!");
                                }
                            }
                        });
                    }
                    holder.flag = !holder.flag;
                }
            });
        }
    }

    @Override
    public void queryUserSuccess() {
        LogUtils.i(TAG, "查询数据成功");
    }

    @Override
    public void queryUseError(BmobException e) {
        LogUtils.i(TAG, "查询数据失败" + e.toString());
    }

    @Override
    public void joinShopCar() {
        view.showLoadingDialog("", "数据加载中...", false);
        //获取传递过来的商品信息
        goods = (Goods) CustomApplcation.getDatas("goods", false);
        //将商品信息添加到购物车
        ShopCar shopCar = new ShopCar();
        shopCar.setGoodId(goods.getObjectId());
        //设置数量默认为1
        shopCar.setCount(1);
        shopCar.setUserId(CustomApplcation.getInstance().getCurrentUser().getObjectId());
        shopCar.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                view.canelLoadingDialog();
                if (e == null) {
                    view.showMsg("加入购物车成功!");
                    LogUtils.i(TAG, "加入购车功能!购物车编号为:" + objectId);
                } else {
                    view.showMsg("加入购物车失败!" + e.getLocalizedMessage());
                    LogUtils.i(TAG, "加入购物车失败" + e.toString());
                }
            }
        });
    }

    @Override
    public void shareWXAPP(int type) {
        LogUtils.i(TAG,"我要进行微信分享");
        String text = "[筱淘APP]:\r\n用户:"+CustomApplcation.getInstance().getCurrentUser().getNickName()
                +" 分享商品 \r\n"+goods.getGoodsName()+"\r\n商品价格为:"+goods.getGoodsPrice();
        // 初始化一个WXTextObject对象
        WXTextObject textObj = new WXTextObject();
        textObj.text = text;

        // 用WXTextObject对象初始化一个WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        // 发送文本类型的消息时，title字段不起作用
        // msg.title = "Will be ignored";
        msg.description = text;

        // 构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = System.currentTimeMillis()+"text"; // transaction字段用于唯一标识一个请求
        req.message = msg;
        req.scene = type;
        // 调用api接口发送数据到微信
        api.sendReq(req);
    }

    class Holder {
        /**
         * 用于标记当前活动的状态，false 为没有点赞过，true 为点赞过，默认为false
         */
        boolean flag = false;
    }
}
