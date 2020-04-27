package com.cnki.mybookepubrelease.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.cnki.mybookepubrelease.R;
import com.cnki.mybookepubrelease.utils.StatusBarUtil;
import com.koolearn.android.kooreader.KooReader;
import com.koolearn.klibrary.libraryService.BookCollectionShadow;
import com.koolearn.kooreader.book.Book;
import com.koolearn.kooreader.book.BookEvent;
import com.koolearn.kooreader.book.Bookmark;
import com.koolearn.kooreader.book.IBookCollection;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends AppCompatActivity implements IBookCollection.Listener<Book> {
    private Button btn_read;
    private Button btn_pdfread;
    private Button btn_local;
    private final BookCollectionShadow bookCollectionShadow = new BookCollectionShadow();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sanweihomemain);
        RelativeLayout rl_header=findViewById(R.id.layout_header);
        StatusBarUtil.setTransparentForWindow(this);
        rl_header.setPadding(0, 96, 0, 10);
        btn_read = findViewById(R.id.btn_read);
        btn_pdfread = findViewById(R.id.btn_pdfread);
        btn_local = findViewById(R.id.btn_local);

//        bookCollectionShadow.bindToService(this, new Runnable() {
//            public void run() {
//                setProgressBarIndeterminateVisibility(!bookCollectionShadow.status().IsComplete);
//                bookCollectionShadow.addListener(MainActivity.this);
//            }
//        });

        btn_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookCollectionShadow.bindToService(MainActivity.this, new Runnable() {
                    public void run() {
                        Book book = bookCollectionShadow.getBookByFile("/storage/emulated/0/book.epub");
                        Bookmark bookmark = null;
                        KooReader.openBookActivity(MainActivity.this, book, bookmark,-1);
                        MainActivity.this.overridePendingTransition(R.anim.tran_fade_in, R.anim.tran_fade_out);
                    }
                });

            }
        });
        btn_pdfread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PDFDatabaseActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        btn_local.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                AssetOnSDActivity.open(MainActivity.this);
                Intent intent = new Intent(MainActivity.this, PDFLocalActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

    }

    /**
     * 字体拷贝
     */
    private void copyFonts(String fontName) {
        File destFile = new File(getFilesDir(), fontName);
        if (destFile.exists()) {
            return;
        }

        FileOutputStream out = null;
        InputStream in = null;
        try {
            in = getAssets().open(fontName);
            out = new FileOutputStream(destFile);
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bookCollectionShadow.removeListener(this);
        bookCollectionShadow.unbind();
    }

    @Override
    public void onBookEvent(BookEvent event, Book book) {


    }

    @Override
    public void onBuildEvent(IBookCollection.Status status) {
        setProgressBarIndeterminateVisibility(!status.IsComplete);
    }
}
