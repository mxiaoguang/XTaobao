package com.xiaoguang.xtaobao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.xiaoguang.xtaobao.R;
import com.xiaoguang.xtaobao.base.BaseActivity;

/**
 * 预先加载数据的SplashActivity
 */
public class SplashActivity extends BaseActivity {

    private static final int SHOW_TIME_MIN = 3000;// 最小显示时间
    private long mStartTime;// 开始时间

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
//            switch (msg.what) {
//                case Application.CITY_LIST_SCUESS:// 如果城市列表加载完毕，就发送此消息
//                    long loadingTime = System.currentTimeMillis() - mStartTime;// 计算一下总共花费的时间
//                    if (loadingTime < SHOW_TIME_MIN) {// 如果比最小显示时间还短，就延时进入MainActivity，否则直接进入
//                        mHandler.postDelayed(goToMainActivity, SHOW_TIME_MIN
//                                - loadingTime);
//                    } else {
//                        mHandler.post(goToMainActivity);
//                    }
//                    break;
//                default:
//                    break;
//            }
            //暂时设置3秒后进入系统
            mHandler.postDelayed(goToMainActivity,SHOW_TIME_MIN);
        }
    };
    //进入下一个Activity
    Runnable goToMainActivity = new Runnable() {

        @Override
        public void run() {
            SplashActivity.this.startActivity(new Intent(SplashActivity.this,
                    HomeActivity.class));
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_splash);
        //实现沉浸式通知栏
        immersiveNotification();
        mStartTime = System.currentTimeMillis();//记录开始时间，
//        Application.getInstance().initData(mHandler);//开始加载数据
        mHandler.sendMessage(new Message());
    }
}
