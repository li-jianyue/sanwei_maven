package com.cnki.mybookepubrelease.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cnki.mybookepubrelease.R;
import com.cnki.mybookepubrelease.adapter.SanWei_ListenTuiJianAdapter;
import com.cnki.mybookepubrelease.adapter.SanWei_PingLunAdapter;
import com.cnki.mybookepubrelease.common.MyImageLoader;
import com.cnki.mybookepubrelease.common.URLConstants;
import com.cnki.mybookepubrelease.model.SanWeiListenBookCategoryTuis;
import com.cnki.mybookepubrelease.model.SanWeiPingLunBean;
import com.cnki.mybookepubrelease.model.SanWei_ListenBookDetailBean;
import com.cnki.mybookepubrelease.protocol.GetListenBookDetailProtocol;
import com.cnki.mybookepubrelease.protocol.NetWorkCallBack;
import com.cnki.mybookepubrelease.utils.StatusBarUtil;
import com.cnki.mybookepubrelease.utils.ToastUtil;
import com.cnki.mybookepubrelease.widget.MultiListView;
import com.cnki.mybookepubrelease.widget.MyGridView;
import com.cnki.mybookepubrelease.widget.RoundAngleImageView;
import com.huangfei.library.activity.BaseActivity;
import com.huangfei.library.utils.NetUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class SanWeiListenBookDetailActivity extends BaseActivity implements View.OnClickListener{
    private TextView tv_player;
    private RoundAngleImageView iv_cover;
    private TextView tv_book_title, tv_author, tv_book_info, tv_unit,tv_wordcount;
    private TextView tv_pinpai, tv_transsouce, tv_bookpublishtime, tv_bookcopyright;
    private TextView tv_sanweibookdownload,tv_sanweibookreadnow,tv_sanweibookstore;
    private GetListenBookDetailProtocol getListenBookDetailProtocol;
    private String audioId;
    private SanWei_ListenBookDetailBean curSanWei_listenBookDetailBean;
    private MyGridView mgv_sanweituijian;
    private MultiListView lv_pinglun;
    private SanWei_ListenTuiJianAdapter sanWei_listenTuiJianAdapter;
    private SanWei_PingLunAdapter sanWei_pingLunAdapter;

    public static void startActivity(Context context,String audioId){
        Intent intent=new Intent(context,SanWeiListenBookDetailActivity.class);
        intent.putExtra("audioId",audioId);
        context.startActivity(intent);

    }
    @Override
    protected void init() {

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_listenbookdetail);
        RelativeLayout rl_header = findViewById(R.id.layout_header);
        StatusBarUtil.setTransparentForWindow(this);
        rl_header.setPadding(0, 96, 0, 10);
        audioId=getIntent().getStringExtra("audioId");

        TextView tv_title=findViewById(R.id.tv_title);
        tv_title.setText("听书详情页");

        tv_player=findViewById(R.id.tv_book_title);

        iv_cover = findViewById(R.id.iv_cover);
        tv_book_title = findViewById(R.id.tv_book_title);
        tv_author = findViewById(R.id.tv_book_author);
        tv_unit = findViewById(R.id.tv_book_unit);
        tv_wordcount= findViewById(R.id.tv_wordcount);
        tv_book_info = findViewById(R.id.tv_book_info);

        tv_pinpai = findViewById(R.id.tv_pinpai);
        tv_transsouce = findViewById(R.id.tv_transsouce);
        tv_bookpublishtime = findViewById(R.id.tv_bookpublishtime);
        tv_bookcopyright = findViewById(R.id.tv_bookcopyright);
        tv_sanweibookdownload=findViewById(R.id.tv_sanweibookdownload);
        tv_sanweibookreadnow=findViewById(R.id.tv_sanweibookreadnow);
        tv_sanweibookstore=findViewById(R.id.tv_sanweibookstore);

        mgv_sanweituijian=findViewById(R.id.mgv_sanweituijian);
        sanWei_listenTuiJianAdapter=new SanWei_ListenTuiJianAdapter(this);
        mgv_sanweituijian.setAdapter(sanWei_listenTuiJianAdapter);

        lv_pinglun = findViewById(R.id.lv_pinglun);
        sanWei_pingLunAdapter = new SanWei_PingLunAdapter(this);
        lv_pinglun.setAdapter(sanWei_pingLunAdapter);

    }

    @Override
    protected void setListener() {
        findViewById(R.id.iv_back).setOnClickListener(this);
        tv_player.setOnClickListener(this);

        mgv_sanweituijian.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SanWeiListenBookCategoryTuis sanWeiListenBookCategoryTuis= (SanWeiListenBookCategoryTuis) sanWei_listenTuiJianAdapter.getItem(position);
                SanWeiListenBookDetailActivity.startActivity(SanWeiListenBookDetailActivity.this,sanWeiListenBookCategoryTuis.getId());
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

        getListenBookDetailProtocol=new GetListenBookDetailProtocol(this, new NetWorkCallBack<SanWei_ListenBookDetailBean>() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(SanWei_ListenBookDetailBean sanWei_listenBookDetailBean) {
                curSanWei_listenBookDetailBean=sanWei_listenBookDetailBean;
                if (sanWei_listenBookDetailBean!=null){
                    String imagurl = sanWei_listenBookDetailBean.getCover();
                    if (!imagurl.contains("http")) {
                        imagurl = URLConstants.API_SANWEI_ROOT + imagurl;
                    }
                    MyImageLoader.getInstance().displayImage(imagurl, iv_cover);
                    tv_book_title.setText(sanWei_listenBookDetailBean.getBookName());
                    tv_author.setText("作者：" + sanWei_listenBookDetailBean.getAuthor());
                    tv_wordcount.setText("时长："+sanWei_listenBookDetailBean.getDuration());
                    tv_book_info.setText(sanWei_listenBookDetailBean.getDescription());

                    tv_pinpai.setText("品牌："+sanWei_listenBookDetailBean.getCopyright());
                    tv_transsouce.setText("翻译来源："+sanWei_listenBookDetailBean.getSource());
                    tv_bookpublishtime.setText("上架时间："+sanWei_listenBookDetailBean.getPublishDate());
                    tv_bookcopyright.setText("版权："+sanWei_listenBookDetailBean.getCopyright());
                    List<SanWeiListenBookCategoryTuis> tuis=sanWei_listenBookDetailBean.getSanWeiBookCategoryTuis();
                    if(tuis!=null&&tuis.size()>0){
                        sanWei_listenTuiJianAdapter.addData(tuis,true);
                    }
                }else {

                }
            }
        });

        if (NetUtils.isNetworkConnected()) {
            getListenBookDetailProtocol.load(audioId);
        } else {
            ToastUtil.showMessage("网络请求失败，请重试");
        }

    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (id==R.id.iv_back){
            finish();
        }else if (id==R.id.tv_book_title){
            SanWeiListenBookOnPlayingActivity.startActivity(this,"http://ftp.ppmbook.com/mp3/20190621/a44fcb05be5844e69b2ecc53f2eb2fdc.mp3");
        }
    }
}
