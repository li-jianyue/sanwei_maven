package com.cnki.mybookepubrelease.protocol;

import android.app.Activity;

import com.cnki.mybookepubrelease.R;
import com.cnki.mybookepubrelease.common.URLConstants;
import com.cnki.mybookepubrelease.model.SanWei_BookDetailBean;
import com.cnki.mybookepubrelease.utils.ToastUtil;
import com.google.gson.Gson;
import com.huangfei.library.utils.LogUtils;
import com.huangfei.library.utils.NetUtils;
import com.huangfei.library.utils.UIUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2020/4/17.
 * 图书详情页接口
 */
public class GetBookDetailProtocol {
    private boolean mIsRunning;
    private Activity mContext;
    private NetWorkCallBack<SanWei_BookDetailBean> mNetWorkCallBack;

    public GetBookDetailProtocol(Activity context, NetWorkCallBack<SanWei_BookDetailBean> callBack) {
        mContext = context;
        mNetWorkCallBack = callBack;
    }
    /**
     * @param bookId
     */
    public void load(String bookId) {
        if (!NetUtils.isNetworkConnected()) {
            UIUtils.showToastSafe(R.string.no_net_and_check, mContext);
            return;
        }
        if (mIsRunning)
            return;
        mIsRunning = true;
        Map<String, String> params = new HashMap<String, String>();
        params.put("bookId", bookId);
        OkHttpUtils.get()
                .url(URLConstants.API_SANWEI_GETBOOKDETAIL)
                .params(params)
                .build()
                .execute(new Callback<SanWei_BookDetailBean>() {
                    @Override
                    public SanWei_BookDetailBean parseNetworkResponse(Response response, int id) throws Exception {
                        try {
                            String data = response.body().string();
                            LogUtils.d(data);
                            JSONObject jsonObject = new JSONObject(data);
                            if (!jsonObject.isNull("flag")) {
                                int flag = jsonObject.getInt("flag");
                                if (flag == 1) {
                                    data=jsonObject.getString("data");
                                    SanWei_BookDetailBean sanWei_bookDetailBean=new Gson().fromJson(data,SanWei_BookDetailBean.class);
                                    return sanWei_bookDetailBean;
                                } else {
                                    String reason = jsonObject.getString("message");
                                    ToastUtil.showMessage(reason);
                                    return null;
                                }
                            } else {
                                return null;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            return null;
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        e.printStackTrace();
                        mIsRunning = false;
                        mNetWorkCallBack.onError(call, e);
                    }

                    @Override
                    public void onResponse(SanWei_BookDetailBean response, int id) {
                        mIsRunning = false;
                        mNetWorkCallBack.onResponse(response);
                    }

                });
    }

}
