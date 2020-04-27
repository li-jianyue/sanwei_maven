package com.cnki.mybookepubrelease.protocol;

import android.content.Context;

import com.cnki.mybookepubrelease.common.Page;
import com.cnki.mybookepubrelease.common.URLConstants;
import com.cnki.mybookepubrelease.model.BookCatelogsBean;
import com.huangfei.library.utils.NetUtils;
import com.huangfei.library.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2020/4/22.
 *获取图书目录
 */
public class SanWeiBookCatelogsListProtocol {

    private Context mContext;
    private boolean mIsFirstPage;
    private boolean mIsRunning;
    private int mPageSize = 15;
    private Page<BookCatelogsBean> mPage;

    public SanWeiBookCatelogsListProtocol(Context context, Page.NetWorkCallBack<BookCatelogsBean> callBack) {
        mContext = context;
        mPage = new Page<BookCatelogsBean>(mPageSize, 0, URLConstants.API_SANWEI_GETEPUBCATELOGS, BookCatelogsBean.class, callBack);
    }
    /**
     * @param isFirstPage 是否第一页
     *  @param path epub路径
     *  @param flag 获取标识，获取目录时传值titles
     */
    public void load(boolean isFirstPage, String path, String flag) {
        if (mIsRunning || !NetUtils.isNetworkConnected())
            return;
        mIsRunning = true;
        mIsFirstPage = isFirstPage;

        if (mIsFirstPage) {
            Map<String, String> params = new HashMap<String, String>();
            params.put("path", StringUtils.isEmpty(path) ? "" : path);
            params.put("flag", StringUtils.isEmpty(flag) ? "" : flag);
            mPage.init(false, params);
        } else {
            mPage.nextPage();
        }
    }
    /**
     * 判断是否是第一页
     */
    public boolean isFirstPage() {
        return mIsFirstPage;
    }

    /**
     * 判断是否是正在请求
     */
    public void setRunning(boolean isRunning) {
        this.mIsRunning = isRunning;
    }

    /**
     * 判断是否是最后一页
     */
    public boolean isLastPage() {
        return mPage.isLastPage();
    }
}
