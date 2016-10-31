package com.xiaoguang.xtaobao.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaoguang.xtaobao.R;
import com.xiaoguang.xtaobao.base.BaseActivity;
import com.xiaoguang.xtaobao.bean.Orders;
import com.xiaoguang.xtaobao.config.Contracts;
import com.xiaoguang.xtaobao.util.LogUtils;
import com.xiaoguang.xtaobao.util.ToastFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import c.b.BP;
import c.b.PListener;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;


/**
 * 支付功能的Activity，暂时用于支付测试,存在没有自动安装支付插件的功能
 * 微信支付测试成功，支付宝，由于官方问题导致付款失败
 */
public class PayActivity extends BaseActivity {

    //通过绑定方式获取控件

    ProgressDialog dialog;
    @BindView(R.id.act_pay_iv_back)
    ImageView mActPayIvBack;
    @BindView(R.id.act_goods_result_tv_menu)
    TextView mActGoodsResultTvMenu;
    @BindView(R.id.act_pay_tv_goods_title)
    TextView mActPayTvGoodsTitle;
    @BindView(R.id.act_pay_tv_money)
    TextView mActPayTvMoney;
    @BindView(R.id.act_pay_btn_weixin)
    Button mActPayBtnWeixin;
    @BindView(R.id.act_pay_alipay)
    Button mActPayAlipay;
    //订单编号
    private String orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_pay);
        ButterKnife.bind(this);
        int pluginVersion = BP.getPluginVersion();
        if (pluginVersion < Contracts.PLUGINVERSION) {
            ToastFactory.getToast(this, pluginVersion == 0 ? "监测到本机尚未安装支付插件,无法进行支付,请先安装插件(无流量消耗)"
                    : "监测到本机的支付插件不是最新版,最好进行更新,请先更新插件(无流量消耗)");// 为0说明未安装支付插件, 否则就是支付插件的版本低于官方最新版
            installBmobPayPlugin("bp.db");
        }
        initDatas();
    }

    /**
     * 初始化数据
     */
    private void initDatas() {
        //获取intent对象
        Intent intent = getIntent();
        //获取传递过来的数据
        String orderId = intent.getStringExtra("orderId");
        double sumMoney = intent.getDoubleExtra("sumMoney",0.02);
        //为控件设置数据
        mActPayTvGoodsTitle.setText(orderId);
        mActPayTvMoney.setText(sumMoney+"");
    }


    /**
     * 调用支付
     *
     * @param alipayOrWechatPay 支付类型，true为支付宝支付,false为微信支付
     */
    void pay(final boolean alipayOrWechatPay) {
        showDialog("正在获取订单...");
        final String name = getName();

        BP.pay(name, getBody(), getPrice(), alipayOrWechatPay, new PListener() {

            // 因为网络等原因,支付结果未知(小概率事件),出于保险起见稍后手动查询
            @Override
            public void unknow() {
                ToastFactory.getToast(PayActivity.this, "支付结果未知,请稍后手动查询");
                LogUtils.i(TAG, name + "'s pay status is unknow\n\n");
                hideDialog();
                finish();
            }

            // 支付成功,如果金额较大请手动查询确认
            @Override
            public void succeed() {
                Toast.makeText(PayActivity.this, "支付成功!", Toast.LENGTH_SHORT).show();
                LogUtils.i("myTag", name + "'s pay status is success\n\n");
                hideDialog();
                //更改订单表的订单状态,为1支付成功
                Orders orders = new Orders();
                orders.setOrdersState(1);
                orders.update(mActPayTvGoodsTitle.getText().toString(), new UpdateListener() {

                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            Log.i("bmob","更新成功");
                            ToastFactory.getToast(PayActivity.this,"支付成功").show();
                            //跳转到购买成功抽奖页面
                            startActivity(new Intent(PayActivity.this,LotteriesActivity.class));
                        }else{
                            Log.i("bmob","更新失败："+e.getMessage()+","+e.getErrorCode());
                            ToastFactory.getToast(PayActivity.this,"支付成功").show();
                        }
                    }
                });
                finish();

            }

            // 无论成功与否,返回订单号
            @Override
            public void orderId(String orderId) {
                // 此处应该保存订单号,比如保存进数据库等,以便以后查询
                LogUtils.i("myTag", name + "'s orderid is " + orderId + "\n\n");
                showDialog("获取订单成功!请等待跳转到支付页面~");
                PayActivity.this.orderId = orderId;
            }

            // 支付失败,原因可能是用户中断支付操作,也可能是网络原因
            @Override
            public void fail(int code, String reason) {

                // 当code为-2,意味着用户中断了操作
                // code为-3意味着没有安装BmobPlugin插件
                if (code == -3) {
                    ToastFactory.getToast(PayActivity.this, "监测到你尚未安装支付插件,无法进行支付,请先安装插件(已打包在本地,无流量消耗),安装结束后重新支付");
                    installBmobPayPlugin("bp.db");
                } else {
                    ToastFactory.getToast(PayActivity.this, "支付中断！");
                }
                LogUtils.i("myTag", name + "'s pay status is fail, error code is \n"
                        + code + " ,reason is " + reason + "\n\n");
                hideDialog();
                setResult(Contracts.RESULT_FAIL);
                finish();
            }
        });
    }

    // 默认为0.02
    double getPrice() {
        double price = 0.02;
        try {
            price = Double.parseDouble(mActPayTvMoney.getText().toString());
        } catch (NumberFormatException e) {
        }
        return price;
    }

    // 商品详情(可不填)
    String getName() {
        return "[筱广Taobao]购买商品:" + mActPayTvGoodsTitle.getText().toString() + " 费用";
    }

    // 商品详情(可不填)
    String getBody() {
        return "[筱广Taobao]购买商品:" + mActPayTvGoodsTitle.getText().toString() + " 费用";
    }

    void showDialog(String message) {
        try {
            if (dialog == null) {
                dialog = new ProgressDialog(this);
                dialog.setCancelable(true);
            }
            dialog.setMessage(message);
            dialog.show();
        } catch (Exception e) {
            // 在其他线程调用dialog会报错
        }
    }

    void hideDialog() {
        if (dialog != null && dialog.isShowing())
            try {
                dialog.dismiss();
            } catch (Exception e) {
            }
    }

    /**
     * 安装支付插件
     *
     * @param fileName 插件名称默认为bp.dp
     */
    void installBmobPayPlugin(String fileName) {
        try {
            InputStream is = getAssets().open(fileName);
            LogUtils.i("myTag", "文件路径为" + Environment.getExternalStorageDirectory()
                    + File.separator + fileName + ".apk");
            File file = new File(Environment.getExternalStorageDirectory()
                    + File.separator + fileName + ".apk");
            if (file.exists())
                file.delete();
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            byte[] temp = new byte[1024];
            int i = 0;
            while ((i = is.read(temp)) > 0) {
                fos.write(temp, 0, i);
            }
            fos.close();
            is.close();

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.parse("file://" + file),
                    "application/vnd.android.package-archive");
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.act_pay_iv_back, R.id.act_goods_result_tv_menu, R.id.act_pay_btn_weixin, R.id.act_pay_alipay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.act_pay_iv_back:
                finish();
                break;
            case R.id.act_goods_result_tv_menu:
                break;
            case R.id.act_pay_btn_weixin:
                pay(false);
                break;
            case R.id.act_pay_alipay:
                pay(true);
                break;
        }
    }
}
