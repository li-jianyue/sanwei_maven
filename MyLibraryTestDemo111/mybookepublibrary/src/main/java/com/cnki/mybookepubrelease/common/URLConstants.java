package com.cnki.mybookepubrelease.common;

import com.cnki.mybookepubrelease.utils.ToastUtil;
import com.huangfei.library.utils.LogUtils;
import com.huangfei.library.utils.SharedPreferences;

import org.json.JSONObject;

public class URLConstants {
    public static final String API_ROOT = "http://211.151.247.179:8014/hbnj/api/";
    public static final String API_SANWEI_ROOT = "http://124.193.98.166:8488";
    public static final String API_SANWEI_GETBOOKDETAIL = API_SANWEI_ROOT+"/api/CommonRes/GetBookDetail";
    public static final String API_SANWEI_GETVEDIODETAIL = API_SANWEI_ROOT+"/api/CommonRes/GetVideoDetail";
    public static final String API_SANWEI_GETLISTENDETAIL = API_SANWEI_ROOT+"/api/CommonRes/GetAudioDetail";
    public static final String API_SANWEI_GETEPUBCATELOGS = API_SANWEI_ROOT+"/api/CommonRes/GetContentsByEpub";


    /**
     * 解析网络请求返回的数据
     *
     * @return
     */
    public static String parseData(String data) throws Exception {
        LogUtils.d(data);
        JSONObject jsonObject = new JSONObject(data);
        if (!jsonObject.isNull("flag")) {
            int flag = jsonObject.getInt("flag");
            if (flag == 1) {
                return jsonObject.getString("data");
            } else {
                String reason = jsonObject.getString("message");
                if (jsonObject.isNull("message")) {
                    if (!reason.isEmpty())
                        ToastUtil.showMessage("" + reason);
                }
                throw new Exception(reason);
            }
        } else {
            throw new Exception(data);
        }
    }
}
