package com.cnki.mybookepubrelease.protocol;

import android.app.Activity;

import com.cnki.mybookepubrelease.R;
import com.cnki.mybookepubrelease.common.AppLibraryManager;
import com.cnki.mybookepubrelease.common.URLConstants;
import com.huangfei.library.utils.NetUtils;
import com.huangfei.library.utils.UIUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import java.util.HashMap;
import java.util.Map;
import okhttp3.Call;
import okhttp3.Response;
/**
 * Created by Administrator on 2020/4/17.
 * 各个模块统计信息
 */
public class AllTongjiProtocol {
    private boolean mIsRunning;
    private Activity mContext;
    private int mFlag;
    private NetWorkCallBack<String> mNetWorkCallBack;

    public AllTongjiProtocol(Activity context, NetWorkCallBack<String> callBack) {
        mContext = context;
        mNetWorkCallBack = callBack;
    }

    /**
     * @param userName
     * @param title
     * @param moduleType zdjl 指导记录 gzrz工作日志 cctj 测产统计 fatie发帖  huitie回帖  wsp微视频  vedio视频 nlts能力提升
     * @param realName
     * @param zoneCode
     * @param zjUserName
     * @param zjRealName
     * @param level
     */
    public void load(String userName,String title,String moduleType,String realName,String zoneCode,String zjUserName,String zjRealName,String level) {
        if (!NetUtils.isNetworkConnected()) {
            UIUtils.showToastSafe(R.string.no_net_and_check, mContext);
            return;
        }
        if (mIsRunning)
            return;
        mIsRunning = true;
        Map<String, String> params = new HashMap<String, String>();
        params.put("userName", userName);
        params.put("title", title);
        params.put("moduleType", moduleType);
        params.put("realName", realName);
        params.put("zoneCode", zoneCode);
        params.put("zjUserName", zjUserName);
        params.put("zjRealName", zjRealName);
        params.put("level", level);
        OkHttpUtils.post()
                .url(URLConstants.API_ROOT)
                .params(params)
                .build()
                .execute(new Callback<String>() {
                    @Override
                    public String parseNetworkResponse(Response response, int id) throws Exception {
                        try {
                            return "";
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        return "";
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        e.printStackTrace();
                        mIsRunning = false;
                        mNetWorkCallBack.onError(call, e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        mIsRunning = false;
                        mNetWorkCallBack.onResponse(response);
                    }

                });
    }

}
