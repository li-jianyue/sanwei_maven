package com.cnki.mybookepubrelease.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cnki.mybookepubrelease.R;
import com.cnki.mybookepubrelease.adapter.SanWei_PingLunAdapter;
import com.cnki.mybookepubrelease.adapter.SanWei_TuiJianAdapter;
import com.cnki.mybookepubrelease.common.MyImageLoader;
import com.cnki.mybookepubrelease.common.URLConstants;
import com.cnki.mybookepubrelease.dao.DownLoadHistoryDao;
import com.cnki.mybookepubrelease.fragment.BottomDialogFragment;
import com.cnki.mybookepubrelease.model.SanWeiBookCategoryTuis;
import com.cnki.mybookepubrelease.model.DownLoadHistory;
import com.cnki.mybookepubrelease.model.SanWeiPingLunBean;
import com.cnki.mybookepubrelease.model.SanWei_BookDetailBean;
import com.cnki.mybookepubrelease.protocol.DownloadFileProtocol;
import com.cnki.mybookepubrelease.protocol.GetBookDetailProtocol;
import com.cnki.mybookepubrelease.protocol.NetWorkCallBack;
import com.cnki.mybookepubrelease.utils.StatusBarUtil;
import com.cnki.mybookepubrelease.utils.ToastUtil;
import com.cnki.mybookepubrelease.widget.LoadDialog;
import com.cnki.mybookepubrelease.widget.MultiListView;
import com.cnki.mybookepubrelease.widget.MyGridView;
import com.cnki.mybookepubrelease.widget.RoundAngleImageView;
import com.huangfei.library.activity.BaseActivity;
import com.huangfei.library.utils.NetUtils;
import com.koolearn.android.kooreader.KooReader;
import com.koolearn.klibrary.libraryService.BookCollectionShadow;
import com.koolearn.kooreader.book.Book;
import com.koolearn.kooreader.book.Bookmark;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;

public class SanWeiBookRoomDetailActivity extends BaseActivity implements View.OnClickListener {

    private String bookid;
    private RoundAngleImageView iv_cover;
    private TextView tv_book_title, tv_author, tv_book_info, tv_unit,tv_wordcount, tv_geshi;
    private TextView tv_pinpai, tv_transsouce, tv_bookpublishtime, tv_bookcopyright;
    private TextView tv_sanweibookdownload,tv_sanweibookreadnow,tv_sanweibookstore;
    private SanWei_BookDetailBean curSanWei_bookDetailBean;
    private GetBookDetailProtocol getBookDetailProtocol;
    private DownloadFileProtocol mDownloadFileProtocol;
    private LoadDialog mDialog;
    private final BookCollectionShadow bookCollectionShadow = new BookCollectionShadow();

    private MyGridView mgv_sanweituijian;
    private MultiListView lv_pinglun;
    private SanWei_PingLunAdapter sanWei_pingLunAdapter;
    private SanWei_TuiJianAdapter sanWei_tuiJianAdapter;
    private LinearLayout ll_catelog;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x0013:
                    int progress = msg.arg1;
                    mDialog.setContent("请稍后" + progress + "%");
                    break;
                case 0x0014:
                    mDialog.dismiss();
                    final File file = (File) msg.obj;
                    DownLoadHistoryDao.getInstance().save(curSanWei_bookDetailBean.getId() + "", curSanWei_bookDetailBean.getBookName(), file.getAbsolutePath());
                    if (file.getAbsolutePath().endsWith(".epub")) {
                        bookCollectionShadow.bindToService(SanWeiBookRoomDetailActivity.this, new Runnable() {
                            public void run() {
                                Book book = bookCollectionShadow.getBookByFile(file.getAbsolutePath());
                                Bookmark bookmark = null;
                                KooReader.openBookActivity(SanWeiBookRoomDetailActivity.this, book, bookmark,-1);
                                SanWeiBookRoomDetailActivity.this.overridePendingTransition(R.anim.tran_fade_in, R.anim.tran_fade_out);
                            }
                        });
                    } else if (file.getAbsolutePath().endsWith(".pdf")) {
                        PDFLocalActivity.startActivity(SanWeiBookRoomDetailActivity.this, curSanWei_bookDetailBean.getBookName(), file.getAbsolutePath());
                    }

                    break;
            }
        }
    };

    public static void startActivity(Context context, String bookid) {
        Intent intent = new Intent(context, SanWeiBookRoomDetailActivity.class);
        intent.putExtra("bookid", bookid);
        context.startActivity(intent);
    }

    @Override
    protected void init() {

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_sanweibookroomdetail);

        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("详情页");
        RelativeLayout rl_header = findViewById(R.id.layout_header);
        StatusBarUtil.setTransparentForWindow(this);
        rl_header.setPadding(0, 96, 0, 10);
        bookid = getIntent().getStringExtra("bookid");
        iv_cover = findViewById(R.id.iv_cover);
        tv_book_title = findViewById(R.id.tv_book_title);
        tv_author = findViewById(R.id.tv_book_author);
        tv_unit = findViewById(R.id.tv_book_unit);
        tv_wordcount= findViewById(R.id.tv_wordcount);
        tv_book_info = findViewById(R.id.tv_book_info);
        tv_geshi = findViewById(R.id.tv_geshi);

        tv_pinpai = findViewById(R.id.tv_pinpai);
        tv_transsouce = findViewById(R.id.tv_transsouce);
        tv_bookpublishtime = findViewById(R.id.tv_bookpublishtime);
        tv_bookcopyright = findViewById(R.id.tv_bookcopyright);
        tv_sanweibookdownload=findViewById(R.id.tv_sanweibookdownload);
        tv_sanweibookreadnow=findViewById(R.id.tv_sanweibookreadnow);
        tv_sanweibookstore=findViewById(R.id.tv_sanweibookstore);
        ll_catelog=findViewById(R.id.ll_catelog);


        lv_pinglun = findViewById(R.id.lv_pinglun);
        sanWei_pingLunAdapter = new SanWei_PingLunAdapter(this);
        lv_pinglun.setAdapter(sanWei_pingLunAdapter);

        mgv_sanweituijian = findViewById(R.id.mgv_sanweituijian);
        sanWei_tuiJianAdapter = new SanWei_TuiJianAdapter(this);
        mgv_sanweituijian.setAdapter(sanWei_tuiJianAdapter);


        mDialog = new LoadDialog(this);
        mDialog.setCancel(false);
    }

    @Override
    protected void setListener() {
        findViewById(R.id.iv_back).setOnClickListener(this);
        iv_cover.setOnClickListener(this);
        tv_sanweibookdownload.setOnClickListener(this);
        tv_sanweibookreadnow.setOnClickListener(this);
        tv_sanweibookstore.setOnClickListener(this);
        ll_catelog.setOnClickListener(this);

        mgv_sanweituijian.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SanWeiBookCategoryTuis sanWeiBookCategoryTuis = (SanWeiBookCategoryTuis) sanWei_tuiJianAdapter.getItem(position);
                SanWeiBookRoomDetailActivity.startActivity(SanWeiBookRoomDetailActivity.this, sanWeiBookCategoryTuis.getId());
            }
        });
    }

    @Override
    protected void loadData() {
        List<SanWeiPingLunBean> datas = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            SanWeiPingLunBean sanWeiPingLunBean = new SanWeiPingLunBean();
            sanWeiPingLunBean.setPinglun_title("我相信这本书将对我今后的人生产生巨大的影响影响影响");
            sanWeiPingLunBean.setPinglun_info("我相信这本书将对我今后的人生产生巨大的影响影响影响我相信这本书将对我今后的人生产生巨大的影响影响影响我相信这本书将对我今后的人生产生巨大的影响影响影响");
            sanWeiPingLunBean.setUsername("大国");
            datas.add(sanWeiPingLunBean);
        }
        sanWei_pingLunAdapter.addData(datas, true);

        getBookDetailProtocol = new GetBookDetailProtocol(this, new NetWorkCallBack<SanWei_BookDetailBean>() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(SanWei_BookDetailBean sanWei_bookDetailBean) {
                curSanWei_bookDetailBean = sanWei_bookDetailBean;
                if (sanWei_bookDetailBean != null) {
                    String imagurl = sanWei_bookDetailBean.getCover();
                    if (!imagurl.contains("http")) {
                        imagurl = URLConstants.API_SANWEI_ROOT + imagurl;
                    }
                    MyImageLoader.getInstance().displayImage(imagurl, iv_cover);
                    tv_book_title.setText(sanWei_bookDetailBean.getBookName());
                    tv_author.setText("作者：" + sanWei_bookDetailBean.getAuthor());
                    tv_unit.setText("出版社：" + sanWei_bookDetailBean.getPublisher());
                    tv_wordcount.setText("总字数："+sanWei_bookDetailBean.getWordCount());
                    tv_book_info.setText(sanWei_bookDetailBean.getDescription());
                    tv_geshi.setText("格式：" + sanWei_bookDetailBean.getFileFormat());

                    tv_pinpai.setText("品牌："+sanWei_bookDetailBean.getCopyright());
                    tv_transsouce.setText("翻译来源："+sanWei_bookDetailBean.getSource());
                    tv_bookpublishtime.setText("上架时间："+sanWei_bookDetailBean.getPublishDate());
                    tv_bookcopyright.setText("版权："+sanWei_bookDetailBean.getCopyright());

                    sanWei_tuiJianAdapter.addData(sanWei_bookDetailBean.getSanWeiBookCategoryTuis(), true);
                } else {


                }
            }
        });
        mDownloadFileProtocol = new DownloadFileProtocol(this, new NetWorkCallBack<File>() {
            @Override
            public void onError(Call call, Exception e) {
                ToastUtil.showMessage("失败了");
                mHandler.sendEmptyMessage(0x0012);
            }

            @Override
            public void onResponse(File file) {
                ToastUtil.showMessage("加载成功");
                Message msg = new Message();
                msg.what = 0x0014;
                msg.obj = file;
                mHandler.sendMessage(msg);
            }

            @Override
            public void inProgress(float progress) {
                Message msg = new Message();
                msg.what = 0x0013;
                msg.arg1 = (int) (progress * 100);
                mHandler.sendMessage(msg);
            }
        });
        if (NetUtils.isNetworkConnected()) {
            getBookDetailProtocol.load(bookid);
        } else {
            ToastUtil.showMessage("网络链接失败，请重试");
        }


    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_back) {
            finish();
        } else if (id == R.id.iv_cover) {
            readAndDownload();
        }else if (id == R.id.tv_sanweibookdownload){
            readAndDownload();
        }else if (id == R.id.tv_sanweibookreadnow){
            readAndDownload();

        }else if (id == R.id.tv_sanweibookstore){
            ToastUtil.showMessage("加入书架");
        }else if (id==R.id.ll_catelog){
            if (curSanWei_bookDetailBean.getFileFormat().equals("pdf")){
                ToastUtil.showMessage("当前文件没有提供目录");
                return;
            }
            BottomDialogFragment bottomDialogFr = new BottomDialogFragment();
            Bundle bundle=new Bundle();
            bundle.putString("sanwei_path",curSanWei_bookDetailBean.getFilePath());
            bottomDialogFr.setArguments(bundle);
            bottomDialogFr.show(getSupportFragmentManager(), "DF");
        }
    }
    private void readAndDownload(){
        List<DownLoadHistory> histories = DownLoadHistoryDao.getInstance().selectById(curSanWei_bookDetailBean.getId() + "");
        if (histories != null && histories.size() > 0) {//判断当前文章是否已经下载了，如果是 直接打开
            final DownLoadHistory downLoadHistory = histories.get(0);
            String path = downLoadHistory.getSavepath();
//                    String path=FileUtils.getExternalStoragePath()+"/download/b101200011.pdf";
            if (path.endsWith(".epub")) {
                bookCollectionShadow.bindToService(SanWeiBookRoomDetailActivity.this, new Runnable() {
                    public void run() {
                        Book book = bookCollectionShadow.getBookByFile(downLoadHistory.getSavepath());
                        Bookmark bookmark = null;
                        KooReader.openBookActivity(SanWeiBookRoomDetailActivity.this, book, bookmark,-1);
                        SanWeiBookRoomDetailActivity.this.overridePendingTransition(R.anim.tran_fade_in, R.anim.tran_fade_out);
                    }
                });
            } else if (path.endsWith(".pdf")) {
                PDFLocalActivity.startActivity(SanWeiBookRoomDetailActivity.this, downLoadHistory.getBookname(), path);
            }
        } else {//否则执行下载 之后打开
            if (NetUtils.isWifiConnected()) {
                mDialog.show();
                String path = curSanWei_bookDetailBean.getFilePath();
                String filename = "";
                if (path.endsWith(".epub")) {
                    filename = System.currentTimeMillis() + curSanWei_bookDetailBean.getId() + ".epub";
                } else if (path.endsWith(".pdf")) {
                    filename = System.currentTimeMillis() + curSanWei_bookDetailBean.getId() + ".pdf";
                }
                mDownloadFileProtocol.load(path, filename);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(SanWeiBookRoomDetailActivity.this);
                builder.setTitle("网络提示");
                builder.setMessage("当前在非WIFI网络环境，该阅读操作将消耗一定流量，建议更换无线网络后阅读，确定继续阅读吗？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mDialog.show();
                        String path = curSanWei_bookDetailBean.getFilePath();
                        String filename = "";
                        if (path.endsWith(".epub")) {
                            filename = System.currentTimeMillis() + curSanWei_bookDetailBean.getId() + ".epub";
                        } else if (path.endsWith(".pdf")) {
                            filename = System.currentTimeMillis() + curSanWei_bookDetailBean.getId() + ".pdf";
                        }
                        mDownloadFileProtocol.load(path, filename);
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        }
    }
}
