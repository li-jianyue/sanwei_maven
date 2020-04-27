package com.cnki.myaarapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class VedioPlayerActivity extends Activity {

    private JzvdStd jztd;

    public static void startActivity(Context context, String vedioid) {
        Intent intent = new Intent(context, VedioPlayerActivity.class);
        intent.putExtra("vedioid", vedioid);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vedioactivity);
        jztd=findViewById(R.id.jztd);
        jztd.setUp("http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4" ,"title");
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }
}
