package com.cnki.mybookepubrelease.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.cnki.mybookepubrelease.R;
import com.cnki.mybookepubrelease.utils.StatusBarUtil;
import com.huangfei.library.activity.BaseActivity;

public class SanWeiLoadListActivity extends BaseActivity implements View.OnClickListener{
    private WebView webview;
    private String type;
    private ProgressBar mProgressBar;

    public static void startActivity(Context context,String type){
        Intent intent=new Intent(context,SanWeiLoadListActivity.class);
        intent.putExtra("type",type);
        context.startActivity(intent);
    }
    @Override
    protected void init() {

    }
    @Override
    protected void initView() {
        setContentView(R.layout.activity_sanweiloadlist);
        webview=findViewById(R.id.webview);
        mProgressBar = (ProgressBar) findViewById(R.id.progress);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.yellow),0);

        type=getIntent().getStringExtra("type");
        WebSettings webSettings=webview.getSettings();
        //可以执行javascript
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webview.addJavascriptInterface(this, "js");
//        String url = "file:////android_asset/1.html";
//        String url="http://124.193.98.166:8488/bcyhtest/index.html#/xx";
        //http://124.193.98.166:8488/bcyh/index.html#/xx
        String url="http://124.193.98.166:8488/bcyh/index.html#/xx";

        if (type.equals("看书")){
            url="http://124.193.98.166:8488/bcyh/index.html#/ks";
        }else if (type.equals("听书")){
            url="http://124.193.98.166:8488/bcyh/index.html#/ts";
        }else if (type.equals("学习")){
            url="http://124.193.98.166:8488/bcyh/index.html#/xx";
        }else if (type.equals("少儿")){
            url="http://124.193.98.166:8488/bcyh/index.html#/se";
        }
        //不使用缓存
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                    webSettings.setBlockNetworkImage(false);
                } else {
                    if (View.GONE == mProgressBar.getVisibility()) {
                        mProgressBar.setVisibility(View.VISIBLE);
                    }
                    mProgressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
        webview.loadUrl(url);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void loadData() {
    }

    @JavascriptInterface
    public void finishActivity() {
        finish();
    }
    @JavascriptInterface
    public void jumpBookActivity(String param) {
        SanWeiBookRoomDetailActivity.startActivity(SanWeiLoadListActivity.this,param);
    }
    @JavascriptInterface
    public void jumpVedioActivity(String param) {
        SanWeiVedioRoomDetailActivity.startActivity(SanWeiLoadListActivity.this,param);
    }
    @JavascriptInterface
    public void jumpAudioActivity(String param) {
        SanWeiListenBookDetailActivity.startActivity(SanWeiLoadListActivity.this,param);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.webview){

        }
    }
}
