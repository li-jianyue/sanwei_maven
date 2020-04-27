package com.cnki.mybookepubrelease.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cnki.mybookepubrelease.R;
import com.cnki.mybookepubrelease.model.SanWei_VedioDetailBean;
import com.cnki.mybookepubrelease.protocol.GetVedioDetailProtocol;
import com.cnki.mybookepubrelease.protocol.NetWorkCallBack;
import com.cnki.mybookepubrelease.utils.StatusBarUtil;
import com.cnki.mybookepubrelease.utils.ToastUtil;
import com.huangfei.library.activity.BaseActivity;
import com.huangfei.library.utils.NetUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import okhttp3.Call;

public class SanWeiVedioRoomDetailActivity extends BaseActivity implements View.OnClickListener{
    private GetVedioDetailProtocol getVedioDetailProtocol;
    private String vedioid="";
    private JzvdStd jzvdStd;
    private TextView tv_title;

    public static void startActivity(Context context,String vedioid){
        Intent intent=new Intent(context,SanWeiVedioRoomDetailActivity.class);
        intent.putExtra("vedioid",vedioid);
        context.startActivity(intent);

    }
    @Override
    protected void init() {

    }
    @Override
    protected void initView() {
        setContentView(R.layout.activity_sanweivediodetail);

        RelativeLayout rl_header=findViewById(R.id.layout_header);
        StatusBarUtil.setTransparentForWindow(this);
        rl_header.setPadding(0, 96, 0, 10);
        vedioid=getIntent().getStringExtra("vedioid");
        jzvdStd=findViewById(R.id.jz_video);
        tv_title=findViewById(R.id.tv_title);
        //http://gsbcy.startimes.com.cn/VOD/hd/oneday.m3u8
        //http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4
    }

    @Override
    protected void setListener() {
        findViewById(R.id.iv_back).setOnClickListener(this);

    }

    @Override
    protected void loadData() {
        getVedioDetailProtocol=new GetVedioDetailProtocol(this, new NetWorkCallBack<SanWei_VedioDetailBean>() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(SanWei_VedioDetailBean sanWei_vedioDetailBean) {
                if (sanWei_vedioDetailBean!=null){
                    String vediopath=sanWei_vedioDetailBean.getVideoItems().get(0).getFilePath();
                    String vediotitle=sanWei_vedioDetailBean.getVideoName();

                    jzvdStd.setUp(vediopath, vediotitle);
                    tv_title.setText(sanWei_vedioDetailBean.getVideoName());
                    ImageLoader.getInstance().displayImage(sanWei_vedioDetailBean.getCover(),jzvdStd.thumbImageView);
                    jzvdStd.thumbImageView.setScaleType(ImageView.ScaleType.FIT_XY);//去掉黑框
                }else {
                    ToastUtil.showMessage("未获取到详情信息");
                }
            }
        });

        if (NetUtils.isNetworkConnected()) {
            getVedioDetailProtocol.load(vedioid);
        } else {
            ToastUtil.showMessage("网络未连接，请重试");
        }
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

    @Override
    public void onClick(View view) {
        int id=view.getId();
        if (id==R.id.iv_back){
            finish();
        }
    }
}
