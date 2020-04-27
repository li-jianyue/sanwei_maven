package com.cnki.myaarapplication;

import android.app.Application;
import android.util.Log;

import com.cnki.mybookepubrelease.common.AppLibraryManager;

public class MyDemoApplication extends Application {

    // 获取到主线程的上下文
    private static MyDemoApplication mContext = null;  // 对外暴露上下文
    public static MyDemoApplication getApplication() {
        return mContext;
    }
    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        Log.e("MyDemoApplication","MyDemoApplication onCreate");

        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        AppLibraryManager.initEPubLibrary(this);
    }
}
