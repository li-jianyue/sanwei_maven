package com.cnki.mybookepubrelease.protocol;

import android.app.Activity;

import com.cnki.mybookepubrelease.R;
import com.huangfei.library.utils.FileUtils;
import com.huangfei.library.utils.LogUtils;
import com.huangfei.library.utils.NetUtils;
import com.huangfei.library.utils.UIUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import java.io.File;

import okhttp3.Call;

/**
 * 下载文件
 */
public class DownloadFileProtocol {

    private boolean mIsRunning;
    private Activity mContext;
    private NetWorkCallBack<File> mNetWorkCallBack;


    public DownloadFileProtocol(Activity context, NetWorkCallBack<File> callBack) {
        mContext = context;
        mNetWorkCallBack = callBack;
    }

    public void setmIsRunning(boolean mIsRunning) {
        this.mIsRunning = mIsRunning;
    }

    /**
     * @param url         app下载地址
     * @param fileName 文件名称
     */
    public void load(String url, final String fileName) {
        if (!NetUtils.isNetworkConnected()) {
            UIUtils.showToastSafe(R.string.no_net_and_check, mContext);
            return;
        }
        if (mIsRunning)
            return;
        mIsRunning = true;
        OkHttpUtils.get()
                .url(url)
                .build()
                .connTimeOut(60000)
                .execute(new FileCallBack(FileUtils.getDownloadDir(), fileName) {
                    @Override
                    public void inProgress(float progress, long total, int id) {
                        mNetWorkCallBack.inProgress(progress);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        mIsRunning = false;
                        LogUtils.e(e.getMessage());
                        File file = new File(FileUtils.getDownloadDir(), fileName);
                        if (file.exists())
                            file.delete();
                        mNetWorkCallBack.onError(call, e);
                    }

                    @Override
                    public void onResponse(File file, int id) {
                        mIsRunning = false;
                        LogUtils.d(file.getAbsolutePath());
                        mNetWorkCallBack.onResponse(file);
                    }

                });
    }

    public boolean isRunning() {
        return mIsRunning;
    }

}
