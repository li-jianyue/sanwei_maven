package com.cnki.myaarapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.cnki.mybookepubrelease.activity.SanWeiBookRoomDetailActivity;
import com.cnki.mybookepubrelease.activity.SanWeiLoadListActivity;
import com.cnki.mybookepubrelease.activity.SanWeiVedioRoomDetailActivity;

public class MainActivity extends Activity {

    private TextView tv_play_pdf,tv_play_epub,tv_play_vedio,tv_xx_webview,tv_se_webview,tv_ks_webview;

    public static void startActivity(Context context){
        Intent intent=new Intent(context,MainActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_play_pdf=findViewById(R.id.tv_play_pdf);
        tv_play_epub=findViewById(R.id.tv_play_epub);
        tv_play_vedio=findViewById(R.id.tv_play_vedio);
        tv_xx_webview=findViewById(R.id.tv_xx_webview);
        tv_se_webview=findViewById(R.id.tv_se_webview);
        tv_ks_webview=findViewById(R.id.tv_ks_webview);
        tv_play_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SanWeiBookRoomDetailActivity.startActivity(MainActivity.this,"23");//图书详情类 id传23可测pdf,id传53可测epub
            }
        });
        tv_play_epub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SanWeiBookRoomDetailActivity.startActivity(MainActivity.this,"53");//图书详情类 id传23可测pdf,id传53可测epub
            }
        });
        tv_play_vedio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                VedioPlayerActivity.startActivity(MainActivity.this,"");
                SanWeiVedioRoomDetailActivity.startActivity(MainActivity.this,"3");//视频详情类
            }
        });

        tv_xx_webview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               WebViewActivity.startActivity(MainActivity.this,"");
                //type传值 看书，听书，学习，少儿
                SanWeiLoadListActivity.startActivity(MainActivity.this,"学习");
            }
        });
        tv_ks_webview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               WebViewActivity.startActivity(MainActivity.this,"");
                //type传值 看书，听书，学习，少儿
                SanWeiLoadListActivity.startActivity(MainActivity.this,"看书");
            }
        });
        tv_se_webview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               WebViewActivity.startActivity(MainActivity.this,"");
                //type传值 看书，听书，学习，少儿
                SanWeiLoadListActivity.startActivity(MainActivity.this,"少儿");
            }
        });
    }
}
