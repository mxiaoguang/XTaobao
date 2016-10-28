package com.xiaoguang.xtaobao.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoguang.xtaobao.R;
import com.xiaoguang.xtaobao.application.CustomApplcation;
import com.xiaoguang.xtaobao.base.BaseActivity;
import com.xiaoguang.xtaobao.contract.IOrdersResultContract;
import com.xiaoguang.xtaobao.presenter.ActOrderResultPresenterImpl;
import com.xiaoguang.xtaobao.util.LogUtils;
import com.xiaoguang.xtaobao.util.ToastFactory;
import com.xiaoguang.xtaobao.widget.XListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 11655 on 2016/10/26.
 */

public class OrdersResultActivity extends BaseActivity implements IOrdersResultContract.IOrdersResultView {
    @BindView(R.id.act_order_result_iv_back)
    ImageView mActOrderResultIvBack;
    @BindView(R.id.act_orders_result_tv_menu)
    TextView mActOrdersResultTvMenu;
    @BindView(R.id.act_orders_result_xlv)
    XListView mActOrdersResultXlv;
    private IOrdersResultContract.IOrdersResultPresenter presenter;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_orders_result);
        ButterKnife.bind(this);
        new ActOrderResultPresenterImpl(this);
        presenter.initData();
    }

    @OnClick({R.id.act_order_result_iv_back, R.id.act_orders_result_tv_menu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.act_order_result_iv_back:
                finish();
                break;
            case R.id.act_orders_result_tv_menu:
                break;
        }
    }

    @Override
    public void showMsg(String msg) {
        ToastFactory.getToast(this,msg).show();
    }

    @Override
    public void showLoadingDialog(String title, String msg, boolean flag) {
        super.showProcessDialog(title, msg, flag);
    }

    @Override
    public void canelLoadingDialog() {
        super.dismissProcessDialog();
    }

    @Override
    public void jumpActivity(String objectId, double oerdersMoney) {
        //创建intent对象
        Intent intent = new Intent(this, PayActivity.class);
        //传递数据到支付页面
        intent.putExtra("orderId", objectId);
        double sumMoney = intent.getDoubleExtra("sumMoney", 0.02);
        intent.putExtra("sumMoney", oerdersMoney);
        //跳转到支付页面
        startActivity(intent);
    }

    @Override
    public void setPresenter(IOrdersResultContract.IOrdersResultPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public XListView getmActOrdersResultXlv() {
        return mActOrdersResultXlv;
    }

    @Override
    public void showAlertDialog(final String goodsObjectId) {
        builder = super.showAlertDialog(null, null, true);
        View v = LayoutInflater.from(this).inflate(R.layout.act_address_change_text, null);
        builder.setView(v);
        alertDialog = builder.show();
        //获取控件
        final EditText mDiscussEr = (EditText) v.findViewById(R.id.write_et_content);
        Button mSendBtn = (Button) v.findViewById(R.id.write_btn_send);
        TextView mTvTitle = (TextView) v.findViewById(R.id.writer_tv_title);
        mTvTitle.setText("发表评论");
        mSendBtn.setText("发布");
        //给按钮设置点击事件
        mSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //隐藏评论框
                OrdersResultActivity.this.dismissAlertDialog(alertDialog);
                String text = mDiscussEr.getText().toString();
                LogUtils.i(TAG, "我点击了发送评论的按钮，文本内容为" + text);
                presenter.sendDiscuss(text,
                        goodsObjectId,CustomApplcation.getInstance().getCurrentUser().getObjectId());
            }
        });
    }
}
