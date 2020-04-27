package com.cnki.mybookepubrelease.protocol;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/4/28.
 * 网络请求回调接口
 */
public abstract class NetWorkCallBack<T> {
    public abstract void onError(Call call, Exception e);

    public abstract void onResponse(T t);

    public void inProgress(float progress) {
    }
}
