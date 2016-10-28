package com.xiaoguang.xtaobao.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kale.lib.photo.GetSimplePhotoHelper;
import com.kale.lib.photo.SimplePhoto;
import com.xiaoguang.xtaobao.R;
import com.xiaoguang.xtaobao.base.BaseActivity;
import com.xiaoguang.xtaobao.contract.IPersiDetialsContract;
import com.xiaoguang.xtaobao.presenter.ActPersonDetialsPresenterImpl;
import com.xiaoguang.xtaobao.util.LogUtils;
import com.xiaoguang.xtaobao.util.ToastFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import de.hdodenhof.circleimageview.CircleImageView;
import zhangphil.iosdialog.widget.ActionSheetDialog;

/**
 * 个人信息详情
 * Created by 11655 on 2016/10/21.
 */

public class PersonDetialsActivity extends BaseActivity implements IPersiDetialsContract.IPersiDetialsView {

    //获取控件
    @BindView(R.id.act_person_details_iv_back)
    ImageView mActPersonDetailsIvBack;
    @BindView(R.id.act_setting_tv_menu)
    TextView mActSettingTvMenu;
    @BindView(R.id.act_setting_ln_nav)
    LinearLayout mActSettingLnNav;
    @BindView(R.id.act_person_details_iv_head)
    CircleImageView mActPersonDetailsIvHead;
    @BindView(R.id.act_person_details_ln_head)
    LinearLayout mActPersonDetailsLnHead;
    @BindView(R.id.act_person_details_tv_huiyan_name)
    TextView mActPersonDetailsTvHuiyanName;
    @BindView(R.id.act_person_details_ln_huiyuan_name)
    LinearLayout mActPersonDetailsLnHuiyuanName;
    @BindView(R.id.act_person_details_tv_nick_name)
    TextView mActPersonDetailsTvNickName;
    @BindView(R.id.act_person_details_ln_nick_name)
    LinearLayout mActPersonDetailsLnNickName;
    @BindView(R.id.textView)
    TextView mTextView;
    @BindView(R.id.act_person_details_tv_sex)
    TextView mActPersonDetailsTvSex;
    @BindView(R.id.act_person_details_ln_sex)
    LinearLayout mActPersonDetailsLnSex;
    @BindView(R.id.act_person_details_ln_address)
    LinearLayout mActPersonDetailsLnAddress;
    @BindView(R.id.act_person_details_ln_ercode)
    LinearLayout mActPersonDetailsLnErcode;

    private IPersiDetialsContract.IPersiDetialsPresenter presenter;
    //获取图片的帮助类
    private GetSimplePhotoHelper mPhotoHelper;
    //获取弹出对话框构造者对象
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_person_detials);
        ButterKnife.bind(this);
        new ActPersonDetialsPresenterImpl(this);
        if (BmobUser.getCurrentUser() == null) {//判断当前用户是否登陆
            startActivity(new Intent(this, LoginActivity.class));
        } else {
            presenter.initData();
        }
        mPhotoHelper = GetSimplePhotoHelper.getInstance(this);
    }

    @Override
    public void setPresenter(IPersiDetialsContract.IPersiDetialsPresenter presenter) {
        this.presenter = presenter;
    }

    @OnClick({R.id.act_person_details_iv_back, R.id.act_setting_tv_menu, R.id.act_setting_ln_nav,
            R.id.act_person_details_ln_head, R.id.act_person_details_ln_huiyuan_name,
            R.id.act_person_details_ln_nick_name, R.id.act_person_details_ln_sex, R.id.act_person_details_ln_ercode,
            R.id.act_person_details_ln_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.act_person_details_iv_back:
                finish();
                break;
            case R.id.act_setting_tv_menu:
                break;
            case R.id.act_setting_ln_nav:
                break;
            case R.id.act_person_details_ln_head:
                new ActionSheetDialog(this)
                        .builder()
                        .setCancelable(true)
                        .setCanceledOnTouchOutside(true)
                        .addSheetItem("拍照", ActionSheetDialog.SheetItemColor.Red
                                , new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        //进行拍照
                                        mPhotoHelper.choicePhoto(GetSimplePhotoHelper.FROM_CAMERA, null, new MyListener());
                                    }
                                })
                        .addSheetItem("从相册中选择", ActionSheetDialog.SheetItemColor.Blue
                                , new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        //从图库中获取
                                        mPhotoHelper.choicePhoto(GetSimplePhotoHelper.FROM_ALBUM, null, new MyListener());
                                    }
                                }).show();
                break;
            case R.id.act_person_details_ln_huiyuan_name:
                showMsg("会员名为当前的登陆名,不允许更改!");
                break;
            case R.id.act_person_details_ln_nick_name:
                showMyDialog(1, "更改淘宝昵称");
                break;
            case R.id.act_person_details_ln_sex:
                showMyDialog(2, "更改性别");
                break;
            case R.id.act_person_details_ln_ercode:
                //暂时显示提示，以后可以改为显示一张二维码
                showMsg("二维码不支持更改");
                break;
            case R.id.act_person_details_ln_address://修改收货地址
                startActivity(new Intent(this, AddressChangeActivity.class));
                break;
        }
    }

    /**
     * 弹出对话框
     *
     * @param type
     * @param msg
     */
    void showMyDialog(final int type, String msg) {
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
        mDiscussEr.setHint("输入信息信息");
        Button mSendBtn = (Button) v.findViewById(R.id.write_btn_send);
        //给按钮设置点击事件
        mSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //隐藏评论框
                PersonDetialsActivity.this.dismissAlertDialog(alertDialog);
                String text = mDiscussEr.getText().toString();
                LogUtils.i("myTag", "我点击了修改的按钮，文本内容为" + text);
                presenter.updateUserInfo(text, type);
            }
        });
    }

    @Override
    public CircleImageView getmActPersonDetailsIvHead() {
        return mActPersonDetailsIvHead;
    }

    @Override
    public TextView getmActPersonDetailsTvHuiyanName() {
        return mActPersonDetailsTvHuiyanName;
    }

    @Override
    public TextView getmActPersonDetailsTvNickName() {
        return mActPersonDetailsTvNickName;
    }

    @Override
    public TextView getmActPersonDetailsTvSex() {
        return mActPersonDetailsTvSex;
    }

    @Override
    public void showMsg(String msg) {
        ToastFactory.getToast(this, msg).show();
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

    /**
     * 获取图片后的监听事件
     */
    class MyListener implements GetSimplePhotoHelper.OnSelectedPhotoListener {


        @Override
        public void onSelectedPhoto(int fromWay, SimplePhoto photo) {
            if (photo != null) {
                Log.d("myTag", "uri = " + photo.uri.toString());
                Log.d("myTag", "photo's degree = " + photo.degree);
                //上传头像
                presenter.upload(photo.uri);
            }
        }
    }

}
