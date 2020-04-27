package com.cnki.mybookepubrelease.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.cnki.mybookepubrelease.R;

import cn.jzvd.JzvdStd;

/**
 * 这个本质上就是播放的时候不隐藏缩略图
 */
public class JzvdStdMp3 extends JzvdStd {


    public JzvdStdMp3(Context context) {
        super(context);
    }

    public JzvdStdMp3(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutId() {
        return R.layout.jz_layout_standard_mp3;
    }

    public void blurbg(String url){
//        ImageView imageView=findViewById(R.id.iv_bgblur);
        //url为网络图片的url，10 是缩放的倍数（越大模糊效果越高）
//        final String pattern = 10+"";
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                int scaleRatio = 0;
//                if (TextUtils.isEmpty(pattern)) {
//                    scaleRatio = 0;
//                } else if (scaleRatio < 0) {
//                    scaleRatio = 10;
//                } else {
//                    scaleRatio = Integer.parseInt(pattern);
//                }
//                //                        下面的这个方法必须在子线程中执行
//                final Bitmap blurBitmap2 = FastBlurUtil.GetUrlBitmap(url, scaleRatio);
//
//                //                        刷新ui必须在主线程中执行
//                UIUtils.runInMainThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                        imageView.setImageBitmap(blurBitmap2);
//                    }
//                });
//            }
//        }).start();

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.thumb &&
                (state == STATE_PLAYING ||
                        state == STATE_PAUSE)) {
            onClickUiToggle();
        } else if (v.getId() == R.id.fullscreen) {

        } else {
            super.onClick(v);
        }
    }

    //changeUiTo 真能能修改ui的方法
    @Override
    public void changeUiToNormal() {
        super.changeUiToNormal();

    }

    @Override
    public void changeUiToPreparing() {
        super.changeUiToPreparing();
    }

    @Override
    public void changeUiToPlayingShow() {
        super.changeUiToPlayingShow();
        thumbImageView.setVisibility(View.VISIBLE);
        startButton.setVisibility(View.GONE);
    }

    @Override
    public void changeUiToPlayingClear() {
        super.changeUiToPlayingClear();
        thumbImageView.setVisibility(View.VISIBLE);
        startButton.setVisibility(View.GONE);
    }

    @Override
    public void changeUiToPauseShow() {
        super.changeUiToPauseShow();
        thumbImageView.setVisibility(View.VISIBLE);
        startButton.setVisibility(View.GONE);
    }

    @Override
    public void changeUiToPauseClear() {
        super.changeUiToPauseClear();
        thumbImageView.setVisibility(View.VISIBLE);
        startButton.setVisibility(View.GONE);
    }

    @Override
    public void changeUiToComplete() {
        super.changeUiToComplete();
    }

    @Override
    public void changeUiToError() {
        super.changeUiToError();
    }
}
