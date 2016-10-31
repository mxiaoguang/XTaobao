package com.xiaoguang.xtaobao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoguang.xtaobao.R;
import com.xiaoguang.xtaobao.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 11655 on 2016/10/20.
 */

public class WebViewActivity extends BaseActivity {
    @BindView(R.id.act_webview_iv_back)
    ImageView mActWebviewIvBack;
    @BindView(R.id.act_webview_title)
    TextView mActWebviewTitle;
    @BindView(R.id.act_webview_iv_goto)
    ImageView mActWebviewIvGoto;
    @BindView(R.id.act_webview_wv)
    WebView mActWebviewWv;
    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_webview);
        ButterKnife.bind(this);
        initData();
    }

    @OnClick({R.id.act_webview_iv_back, R.id.act_webview_iv_goto})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.act_webview_iv_back:
//                mActWebviewWv.goBack(); //goBack()表示返回WebView的上一页面
                finish();
                break;
            case R.id.act_webview_iv_goto:
                mActWebviewWv.goForward();//前进
                break;
        }
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //获取传递过来的数据
        Intent intent = getIntent();
        url = intent.getStringExtra("URL");
        //使用系统内置的浏览器打开网页
        mActWebviewWv.loadUrl(url);
        //支持js
        mActWebviewWv.getSettings().setJavaScriptEnabled(true);

        mActWebviewWv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                mActWebviewWv.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        mActWebviewWv.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                mActWebviewTitle.setText(title);
            }
        });
    }

    @Override
    //设置回退
    //覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mActWebviewWv.canGoBack()) {
            mActWebviewWv.goBack(); //goBack()表示返回WebView的上一页面
            return true;
        }
        return false;
    }
}

