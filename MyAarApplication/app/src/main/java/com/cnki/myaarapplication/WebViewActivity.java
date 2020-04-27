package com.cnki.myaarapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.cnki.mybookepubrelease.activity.SanWeiBookRoomDetailActivity;
import com.cnki.mybookepubrelease.activity.SanWeiListenBookDetailActivity;
import com.cnki.mybookepubrelease.activity.SanWeiVedioRoomDetailActivity;

public class WebViewActivity extends Activity {
    private WebView wb_content;
    public static void startActivity(Context context, String vedioid) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("vedioid", vedioid);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        wb_content=findViewById(R.id.wb_content);
        //可以执行javascript
        wb_content.getSettings().setJavaScriptEnabled(true);
        wb_content.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        wb_content.addJavascriptInterface(this, "js");
//        String url = "file:////android_asset/1.html";
//        String url="http://124.193.98.166:8488/bcyhtest/index.html#/xx";
        //http://124.193.98.166:8488/bcyh/index.html#/xx
        String url="http://124.193.98.166:8488/bcyh/index.html#/xx";
        wb_content.loadUrl(url);

    }

    @JavascriptInterface
    public void finishActivity() {
        finish();
    }
    @JavascriptInterface
    public void jumpBookActivity(String param) {
        SanWeiBookRoomDetailActivity.startActivity(WebViewActivity.this,param);
    }
    @JavascriptInterface
    public void jumpVedioActivity(String param) {
        SanWeiVedioRoomDetailActivity.startActivity(WebViewActivity.this,param);
    }
    @JavascriptInterface
    public void jumpAudioActivity(String param) {
        SanWeiListenBookDetailActivity.startActivity(WebViewActivity.this,param);
    }
}
