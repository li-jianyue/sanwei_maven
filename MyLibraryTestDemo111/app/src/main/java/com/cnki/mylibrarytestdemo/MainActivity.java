package com.cnki.mylibrarytestdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.Button;

import com.cnki.mybookepubrelease.activity.SanWeiBookRoomDetailActivity;
import com.cnki.mybookepubrelease.activity.SanWeiListenBookDetailActivity;
import com.cnki.mybookepubrelease.activity.SanWeiLoadListActivity;
import com.cnki.mybookepubrelease.activity.SanWeiVedioRoomDetailActivity;
import com.cnki.mybookepubrelease.utils.StatusBarUtil;
import com.koolearn.android.kooreader.KooReader;
import com.koolearn.klibrary.libraryService.BookCollectionShadow;
import com.koolearn.kooreader.book.Book;
import com.koolearn.kooreader.book.BookEvent;
import com.koolearn.kooreader.book.Bookmark;
import com.koolearn.kooreader.book.IBookCollection;

public class MainActivity extends Activity implements IBookCollection.Listener<Book> {
    private Button btn_readdetail,btn_read,btn_webview;
    private Button btn_vediodetail,btn_listen;

    private final BookCollectionShadow bookCollectionShadow = new BookCollectionShadow();
    private WebView webview;
    public static void startActivity(Context context){
        Intent intent=new Intent(context,MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarUtil.setTransparentForWindow(this);
        btn_vediodetail= findViewById(R.id.btn_vediodetail);
        btn_readdetail= findViewById(R.id.btn_readdetail);
        btn_read = findViewById(R.id.btn_read);
        btn_webview= findViewById(R.id.btn_webview);
        btn_listen=findViewById(R.id.btn_listen);
        webview = findViewById(R.id.webview);
        //可以执行javascript
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webview.addJavascriptInterface(this, "js");
//        String url = "file:////android_asset/1.html";
//        String url="http://124.193.98.166:8488/bcyhtest/index.html#/xx";
        //http://124.193.98.166:8488/bcyh/index.html#/xx
        String url="http://124.193.98.166:8488/bcyh/index.html#/ks";
        webview.loadUrl(url);
        //android添加javascript代码，让H5页面能够调用，第二个参数对应的是H5的
        btn_readdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SanWeiBookRoomDetailActivity.startActivity(MainActivity.this,"53");
//                SanWeiLoadListActivity.startActivity(MainActivity.this,"看书");

            }
        });

        btn_vediodetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                webview.loadUrl("javascript:jsGetStyle('#FF00FF')");
//                webview.evaluateJavascript("javascript:getstyle('来自原生')", new ValueCallback<String>() {
//                    @Override
//                    public void onReceiveValue(String str) {
//                        android.util.Log.i("tag", "js返回的结果:"+str);
//                    }
//                });
                SanWeiVedioRoomDetailActivity.startActivity(MainActivity.this,"21");
//                SanWeiLoadListActivity.startActivity(MainActivity.this,"学习");
            }
        });
        btn_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("click", "the click event");
                SanWeiLoadListActivity.startActivity(MainActivity.this,"少儿");
            }
        });
        btn_webview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SanWeiLoadListActivity.startActivity(MainActivity.this,"听书");
            }
        });
        btn_listen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SanWeiListenBookDetailActivity.startActivity(MainActivity.this,"3");
            }
        });
    }

    @Override
    public void onBookEvent(BookEvent event, Book book) {

    }

    @Override
    public void onBuildEvent(IBookCollection.Status status) {
        setProgressBarIndeterminateVisibility(!status.IsComplete);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bookCollectionShadow.removeListener(this);
        bookCollectionShadow.unbind();
    }
    @JavascriptInterface
    public void finishActivity() {
        finish();
    }
    @JavascriptInterface
    public void jumpBookActivity(String param) {
        SanWeiBookRoomDetailActivity.startActivity(MainActivity.this,param);
    }
    @JavascriptInterface
    public void jumpVedioActivity(String param) {
        SanWeiVedioRoomDetailActivity.startActivity(MainActivity.this,param);
    }
    @JavascriptInterface
    public void jumpAudioActivity(String param) {
        SanWeiListenBookDetailActivity.startActivity(MainActivity.this,param);
    }
}
