package com.xiaoguang.xtaobao.presenter;

import android.net.Uri;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xiaoguang.xtaobao.application.CustomApplcation;
import com.xiaoguang.xtaobao.bean.User;
import com.xiaoguang.xtaobao.config.Contracts;
import com.xiaoguang.xtaobao.contract.IPersiDetialsContract;
import com.xiaoguang.xtaobao.util.LogUtils;

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 11655 on 2016/10/21.
 */

public class ActPersonDetialsPresenterImpl implements IPersiDetialsContract.IPersiDetialsPresenter {
    private IPersiDetialsContract.IPersiDetialsView view;
    private CircleImageView circleImageViewHead;
    private TextView mTvHuiyunName, mTvNickName, mTvSex;

    public ActPersonDetialsPresenterImpl(IPersiDetialsContract.IPersiDetialsView view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void initData() {
        //获取控件
        circleImageViewHead = view.getmActPersonDetailsIvHead();
        mTvHuiyunName = view.getmActPersonDetailsTvHuiyanName();
        mTvNickName = view.getmActPersonDetailsTvNickName();
        mTvSex = view.getmActPersonDetailsTvSex();
        //设置数据
        User user = BmobUser.getCurrentUser(User.class);
        //设置头像
        String url = "";
        if (user.getUserHead().getUrl() == null) {
            url = Contracts.DEFALT_HEAD_URL;
        } else {
            url = user.getUserHead().getUrl();
        }
        Picasso.with(CustomApplcation.getInstance().context).load(url).into(circleImageViewHead);
        mTvHuiyunName.setText(user.getUsername());
        mTvNickName.setText(user.getNickName());
        mTvSex.setText(user.getSex());

    }

    @Override
    public void upload(Uri uri) {
        view.showLoadingDialog("", "头像上传中....", false);
        LogUtils.i("myTag", "我在图库中获取的文件路径为：" + uri.getPath());
        final BmobFile bmobFile = new BmobFile(new File(uri.getPath()));
        bmobFile.upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                view.canelLoadingDialog();
                if (e == null) {//文件上传成功
                    view.showMsg("头像上传成功!");
                    view.showLoadingDialog("", "更新信息中....", false);
                    User user = new User();
                    user.setUserHead(bmobFile);
                    //更新用户数据
                    user.update(CustomApplcation.getInstance().getCurrentUser().getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            view.canelLoadingDialog();
                            if (e == null) {//更新成功
                                //设置用户头像的显示
                                Picasso.with(CustomApplcation.getInstance().context)
                                        .load(bmobFile.getUrl()).into(circleImageViewHead);
                            } else {
                                //将上传的文件上传
                                BmobFile file = new BmobFile();
                                file.setUrl(bmobFile.getUrl());//此url是上传文件成功之后通过bmobFile.getUrl()方法获取的
                                file.delete(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        view.showMsg("更新用户信息失败!");
                                    }
                                });
                            }
                        }
                    });
                } else {
                    view.showMsg("头像上传失败!" + e.getLocalizedMessage());
                }
            }
        });
    }

    @Override
    public void updateUserInfo(String text, int type) {
        //显示一个进度条
        view.showLoadingDialog("","更新数据中...",false);
        User user = new User();
        switch (type){
            case 1://更改淘宝昵称
                user.setNickName(text);
                break;
            case 2://更改性别
                user.setSex(text);
                break;
        }
        user.update(BmobUser.getCurrentUser().getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                view.canelLoadingDialog();
                if (e==null){
                    view.showMsg("更新数据成功!");
                    //更新显示数据
                    initData();
                }
                else {
                    view.showMsg("更新数据失败"+e.getLocalizedMessage());
                }
            }
        });
    }
}
