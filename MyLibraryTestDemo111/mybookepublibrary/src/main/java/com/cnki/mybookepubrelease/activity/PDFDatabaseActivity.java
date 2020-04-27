package com.cnki.mybookepubrelease.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cnki.mybookepubrelease.R;
import es.voghdev.pdfviewpager.library.RemotePDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;
import es.voghdev.pdfviewpager.library.util.FileUtil;

public class PDFDatabaseActivity extends Activity implements DownloadFile.Listener{
    private RelativeLayout pdf_root;
    private ProgressBar pb_bar;
    private RemotePDFViewPager remotePDFViewPager;
    private String mUrl = "http://partners.adobe.com/public/developer/en/xml/AdobeXMLFormsSamples.pdf";
    private String url="http://61.147.174.111/res/file/9/89ac184aa5754ad5ad1397b89fe2d08d/7f1aaa5009f84fb58b52d754e889de3b/temp/book/book.pdf";
    private PDFPagerAdapter adapter;
    private TextView iv_back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sanweipdfdatabase);

        pdf_root = (RelativeLayout) findViewById(R.id.remote_pdf_root);
        pb_bar = (ProgressBar) findViewById(R.id.pb_bar);
        iv_back = (TextView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final DownloadFile.Listener listener = this;
        remotePDFViewPager = new RemotePDFViewPager(this, url, listener);
        remotePDFViewPager.setId(R.id.pdfViewPager);

    }

    @Override
    public void onSuccess(String url, String destinationPath) {
        pb_bar.setVisibility(View.GONE);
        adapter = new PDFPagerAdapter(this, FileUtil.extractFileNameFromURL(url));
        remotePDFViewPager.setAdapter(adapter);
        updateLayout();
    }
    /*更新视图*/
    private void updateLayout() {
        pdf_root.removeAllViewsInLayout();
        pdf_root.addView(remotePDFViewPager, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }
    @Override
    public void onFailure(Exception e) {
        pb_bar.setVisibility(View.GONE);
        Toast.makeText(this,"数据加载错误",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProgressUpdate(int progress, int total) {


    }
}
