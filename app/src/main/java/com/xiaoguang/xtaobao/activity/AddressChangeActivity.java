package com.xiaoguang.xtaobao.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.xiaoguang.xtaobao.R;
import com.xiaoguang.xtaobao.base.BaseActivity;
import com.xiaoguang.xtaobao.contract.IAddressContract;
import com.xiaoguang.xtaobao.presenter.ActAddressPersenterImpl;
import com.xiaoguang.xtaobao.util.LogUtils;
import com.xiaoguang.xtaobao.util.ToastFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 更改收货地址
 * Created by 11655 on 2016/10/22.
 */

public class AddressChangeActivity extends BaseActivity implements IAddressContract.IAddressView {

    @BindView(R.id.act_address_iv_back)
    ImageView mActAddressIvBack;
    @BindView(R.id.act_address_tv_add)
    TextView mActAddressTvAdd;
    @BindView(R.id.act_address_lv)
    SwipeMenuListView mActAddressLv;
    private IAddressContract.IAddressPresenter presenter;
    //创建一个警告对话框对象
    private AlertDialog alertDialog;
    //创建一个弹出框的builder对象
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_address);
        ButterKnife.bind(this);
        new ActAddressPersenterImpl(this);
        presenter.initData();

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
    public void jumpActivity() {

    }

    @Override
    public void setPresenter(IAddressContract.IAddressPresenter presenter) {
        this.presenter = presenter;
    }

    @OnClick({R.id.act_address_iv_back, R.id.act_address_tv_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.act_address_iv_back:
                finish();
                break;
            case R.id.act_address_tv_add:
                showMyDialog(1,"新增收货地址");
                break;
        }
    }
    @Override
    public SwipeMenuListView getmActAddressLv() {
        return mActAddressLv;
    }

    @Override
    public void showAlerDialog(String title, String msg, boolean flag) {
        /*以下代码可能将会被重用，可以提升为赢方法或工具类*/
        showMyDialog(2,"修改收货地址");
    }

    /**
     * 弹出对话框
     * @param type
     * @param msg
     */
    void showMyDialog(final int type, String msg){
        //获取一个警告对话框的builder对象
        builder = super.showAlertDialog(null, null, true);
        View v = LayoutInflater.from(this).inflate(R.layout.act_address_change_text, null);
        //设置弹出的布局
        builder.setView(v);
        alertDialog = builder.show();
        //获取控件
        final EditText mDiscussEr = (EditText) v.findViewById(R.id.write_et_content);
        final TextView mTvTile = (TextView) v.findViewById(R.id.writer_tv_title);
        //设置按钮控件文字
        mTvTile.setText(msg);
        mDiscussEr.setHint("输入新的收货地址");
        Button mSendBtn = (Button) v.findViewById(R.id.write_btn_send);
        //给按钮设置点击事件
        mSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //隐藏评论框
                AddressChangeActivity.this.dismissAlertDialog(alertDialog);
                String text = mDiscussEr.getText().toString();
                LogUtils.i("myTag","我点击了修改的按钮，文本内容为"+text);
                presenter.updateAddress(text,type);
            }
        });
    }
}
