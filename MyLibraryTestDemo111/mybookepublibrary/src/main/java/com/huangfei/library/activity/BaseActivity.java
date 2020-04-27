package com.huangfei.library.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.huangfei.library.commom.AppManager;

public abstract class BaseActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        // 添加Activity到堆栈
        AppManager.getAppManager().addActivity(this);

        init();
        initView();
        setListener();
        loadData();
    }

    /**
     * 初始化数据
     */
    protected abstract void init();

    /**
     * 初始化界面
     */
    protected abstract void initView();

    /**
     * 初始化监听
     */
    protected abstract void setListener();

    /**
     * 加载数据
     */
    protected abstract void loadData();

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 结束Activity从堆栈中移除
        AppManager.getAppManager().finishActivity(this);
    }
}
