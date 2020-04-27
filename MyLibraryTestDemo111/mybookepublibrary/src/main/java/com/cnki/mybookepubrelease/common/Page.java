package com.cnki.mybookepubrelease.common;

import com.cnki.mybookepubrelease.utils.ToastUtil;
import com.google.gson.Gson;
import com.huangfei.library.utils.LogUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2020/4/22.
 * 分页加载
 */
public class Page<T> {

    private static final String PAGE_SIZE_KEY = "pageSize";
    private static final String PAGE_INDEX_KEY = "pageIndex";
    private static final String TOTAL_PAGE_KEY = "totalPage";
    private static final String BOOKPAGE_INDEX_KEY = "pageNo";

    private int mPageSize;//每页数
    public int mPageIndex;//当前页
    private int mTotalPage;//总页数
    private String mUrl;
    private int misbook;
    private NetWorkCallBack<T> mNetWorkCallBack;
    private Class<T> mClass;
    private Map<String, String> mParams;
    /**
     * @param pageSize
     * @param isbook   0非教材页，也非奖品页，1教材页，2其他 奖品页 3是慕课页 5，6，7分别对应活动三页
     * @param url
     * @param cls
     * @param callBack
     */
    public Page(int pageSize, int isbook, String url, Class<T> cls, NetWorkCallBack<T> callBack) {
        mPageSize = pageSize;
        mUrl = url;
        mClass = cls;
        mNetWorkCallBack = callBack;
        misbook = isbook;
    }


    public void init() {
        init(null);
    }


    public void init(Map<String, String> params) {
        init(false, params);
    }

    public void init(boolean isLogin) {
        init(isLogin, null);
    }

    /**
     * 初始化加载数据
     *
     * @param isLogin 是否需要登录
     * @param params
     * @return
     */
    public void init(boolean isLogin, Map<String, String> params) {
        mPageIndex = 1;
        if (params == null)
            mParams = new HashMap<String, String>();
        else
            mParams = params;
        mParams.put(PAGE_SIZE_KEY, mPageSize + "");
        loadData();
    }

    /**
     * 加载下一页数据数据
     *
     * @return
     */
    public void nextPage() {
        loadData();
    }

    /**
     * 判断是否是最后一页
     */
    public boolean isLastPage() {
        return mPageIndex > mTotalPage;
    }

    private void loadData() {
        if (misbook == 1) {//教材页
            mParams.put(BOOKPAGE_INDEX_KEY, mPageIndex + "");
        } else {
            mParams.put(PAGE_INDEX_KEY, mPageIndex + "");
        }
        OkHttpUtils.get().url(mUrl)
                .params(mParams)
                .build()
                .execute(new Callback<List<T>>() {
                    @Override
                    public List<T> parseNetworkResponse(Response response, int id) throws Exception {
                        String data = response.body().string();
                        LogUtils.e("get the data:" + data);
                        JSONObject jsonObject = new JSONObject(data);
                        if (!jsonObject.isNull(PAGE_SIZE_KEY)) {
                            mTotalPage = jsonObject.getInt(PAGE_SIZE_KEY);
                        } else if (!jsonObject.isNull(TOTAL_PAGE_KEY)) {
                            mTotalPage = jsonObject.getInt(TOTAL_PAGE_KEY);
                        } else if (!jsonObject.isNull("totalCount")) {
                            mTotalPage = getTotalPage(jsonObject.getInt("totalCount"));
                        } else if (jsonObject.isNull("totalCount")) {
                            if (misbook == 4)
                                mTotalPage = 100;
                        }
                        List list = new ArrayList();
                        Gson gson = new Gson();
                        if (misbook == 0) {
                            data = URLConstants.parseData(data);
                            JSONArray jsonArray = new JSONArray(data);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                T t = gson.fromJson(jsonArray.getString(i), mClass);
                                list.add(t);
                            }
                            return list;
                        }
                        return null;
                    }
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.e(e);
                        if (e.toString().contains("SocketTimeoutException")){
                            ToastUtil.showMessage("请求信息超时，请重试");
                        }
                        mNetWorkCallBack.onError(call, e);
                    }

                    @Override
                    public void onResponse(List<T> list, int id) {
                        mPageIndex++;
                        mNetWorkCallBack.onResponse(list);
                    }
                });
    }

    private int getTotalPage(int totalcount) {
        if (totalcount % mPageSize == 0) {
            return totalcount / mPageSize;
        } else {
            return totalcount / mPageSize + 1;
        }
    }

    public interface NetWorkCallBack<T> {
        void onError(Call call, Exception e);

        void onResponse(List<T> list);
    }
}
