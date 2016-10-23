package com.xiaoguang.xtaobao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaoguang.xtaobao.R;
import com.xiaoguang.xtaobao.base.BaseFragment;

/**
 * 测试类
 * Created by 11655 on 2016/10/19.
 */

public class NewsFragment extends BaseFragment {
    @Override
    protected void lazyLoad() {

    }

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container, boolean b) {
        return inflater.inflate(R.layout.frag_home,null);
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {

    }
}
