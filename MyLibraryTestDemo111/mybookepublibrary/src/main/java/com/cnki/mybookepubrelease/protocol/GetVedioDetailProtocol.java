package com.cnki.mybookepubrelease.protocol;

import android.app.Activity;

import com.cnki.mybookepubrelease.R;
import com.cnki.mybookepubrelease.common.URLConstants;
import com.cnki.mybookepubrelease.model.SanWei_VedioDetailBean;
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
 * 视频详情页接口
 */
public class GetVedioDetailProtocol {
    private boolean mIsRunning;
    private Activity mContext;
    private NetWorkCallBack<SanWei_VedioDetailBean> mNetWorkCallBack;

    public GetVedioDetailProtocol(Activity context, NetWorkCallBack<SanWei_VedioDetailBean> callBack) {
        mContext = context;
        mNetWorkCallBack = callBack;
    }

    /**
     * @param videoId
     */
    public void load(String videoId) {
        if (!NetUtils.isNetworkConnected()) {
            UIUtils.showToastSafe(R.string.no_net_and_check, mContext);
            return;
        }
        if (mIsRunning)
            return;
        mIsRunning = true;
        Map<String, String> params = new HashMap<String, String>();
        params.put("videoId", videoId);
        OkHttpUtils.get()
                .url(URLConstants.API_SANWEI_GETVEDIODETAIL)
                .params(params)
                .build()
                .execute(new Callback<SanWei_VedioDetailBean>() {
                    @Override
                    public SanWei_VedioDetailBean parseNetworkResponse(Response response, int id) throws Exception {
                        try {
                            String data = response.body().string();
                            LogUtils.d(data);
                            JSONObject jsonObject = new JSONObject(data);
                            if (!jsonObject.isNull("flag")) {
                                int flag = jsonObject.getInt("flag");
                                if (flag == 1) {
                                    data=jsonObject.getString("data");
                                    SanWei_VedioDetailBean sanWei_bookDetailBean=new Gson().fromJson(data,SanWei_VedioDetailBean.class);
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
                    public void onResponse(SanWei_VedioDetailBean response, int id) {
                        mIsRunning = false;
                        mNetWorkCallBack.onResponse(response);
                    }

                });
    }

}
