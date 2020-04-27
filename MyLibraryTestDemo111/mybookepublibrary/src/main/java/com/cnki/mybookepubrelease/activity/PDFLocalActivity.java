package com.cnki.mybookepubrelease.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cnki.mybookepubrelease.R;
import com.cnki.mybookepubrelease.utils.StatusBarUtil;
import es.voghdev.pdfviewpager.library.PDFViewPager;

public class PDFLocalActivity extends Activity {
    private PDFViewPager pdfViewPager;
    private TextView tv_progress;
    private RelativeLayout rl_pdflayout;

    public static void startActivity(Context context,String title,String path){
        Intent intent=new Intent(context,PDFLocalActivity.class);
        intent.putExtra("pdf_path",path);
        intent.putExtra("pdf_title",title);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sanweipdflocal);
        RelativeLayout rl_header=findViewById(R.id.layout_header);
        StatusBarUtil.setTransparentForWindow(this);
        rl_header.setPadding(0, 96, 0, 10);
        TextView tv_title=findViewById(R.id.tv_title);
        String title=getIntent().getStringExtra("pdf_title");

        tv_title.setText(title);
        tv_progress=findViewById(R.id.tv_progress);
        rl_pdflayout=findViewById(R.id.rl_pdflayout);

        String filepath=getIntent().getStringExtra("pdf_path");
        pdfViewPager=new PDFViewPager(this,filepath);
        rl_pdflayout.addView(pdfViewPager);

        pdfViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tv_progress.setText("当前页："+(position+1)+"/"+pdfViewPager.getAdapter().getCount());
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
