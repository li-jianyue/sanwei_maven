package com.cnki.mybookepubrelease.activity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;

import com.cnki.mybookepubrelease.R;
import com.cnki.mybookepubrelease.utils.ToastUtil;
import com.cnki.mybookepubrelease.widget.LoadDialog;
import com.huangfei.library.activity.BaseActivity;
import com.util.LogUtil;


public class SanWeiListenBookOnPlayingActivity extends BaseActivity implements View.OnClickListener {

    //    private JzvdStd jz_mp3;
    private TextView tv_m_title, tv_starttime, tv_endtime, tv_play;

    private boolean isMusicLoadSuccess = false;
    private int mDuration;
    private MediaPlayer mediaPlayer;
    private SeekBar seek_bar;
    private boolean isPlay=false;
    private boolean isFirstPlay=true;
    private LoadDialog loadDialog;

    private String musicpath="";


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 123:
                    seek_bar.setProgress((int) mediaPlayer.getCurrentPosition());
                    tv_starttime.setText(formatDuring(mediaPlayer.getCurrentPosition()));
                    mHandler.sendEmptyMessage(123);
                    break;
            }
        }
    };

    public static void startActivity(Context context, String musicpath) {
        Intent intent = new Intent(context, SanWeiListenBookOnPlayingActivity.class);
        intent.putExtra("musicpath", musicpath);
        context.startActivity(intent);

    }

    @Override
    protected void init() {

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_listenbookplaying);
//        jz_mp3 = findViewById(R.id.jz_mp3);
//        jz_mp3.setUp("http://m8.music.126.net/20200426152204/08eca5b76bbeda17e8c94f092dca91b2/ymusic/obj/w5zDlMODwrDDiGjCn8Ky/2246804789/d5af/a5c5/9efb/79381bdc6cafb71f4ba0b5561d095c1b.mp3", "听书详情");
//        MyImageLoader.getInstance().displayImage("https://f0.mc.0sm.com/node0/2020/03/85E83141648F476D-337c1696b749a086.jpg",jz_mp3.thumbImageView);
//        jz_mp3.thumbImageView.setScaleType(ImageView.ScaleType.FIT_XY);
//    jzvdStdMp3.blurbg("https://f0.mc.0sm.com/node0/2020/03/85E83141648F476D-337c1696b749a086.jpg");

        musicpath=getIntent().getStringExtra("musicpath");
        loadDialog = new LoadDialog(this);
        loadDialog.setContent("请稍等，正在缓冲...");
        tv_starttime = findViewById(R.id.tv_starttime);
        tv_endtime = findViewById(R.id.tv_endtime);
        tv_play = findViewById(R.id.tv_play);
        seek_bar = findViewById(R.id.seek_bar);
        initPlayer(musicpath);
    }

    public void initPlayer(String mPath) {
        loadDialog.show();
        try {
            if (mediaPlayer == null) {
                mediaPlayer = new MediaPlayer();
            }
            if (mPath != null && !mPath.equals("")) {
                mediaPlayer.setDataSource(mPath);
                mediaPlayer.prepareAsync();    // 异步加载音频
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        // 异步加载结束后再回调
                        isMusicLoadSuccess = true;
                        loadDialog.dismiss();
                        mDuration = mediaPlayer.getDuration();
                        tv_endtime.setText(formatDuring(mDuration));
                        seek_bar.setMax(mDuration);   // 设置进度条最大值
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void setListener() {

        tv_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMusicLoadSuccess) {
                    if (mediaPlayer.isPlaying()) {
                        tv_play.setBackgroundResource(R.drawable.ic_start);
                        mediaPlayer.pause();
                        mHandler.removeMessages(123);
                    } else {
                        mHandler.sendEmptyMessage(123);
                        mediaPlayer.start();
                        tv_play.setBackgroundResource(R.drawable.ic_pause);
                        if (isFirstPlay){
                            isFirstPlay=false;
                            isPlay=true;
                        }
                    }
                } else {
                    ToastUtil.showMessage("正在加载...");
                    tv_play.setBackgroundResource(R.drawable.ic_start);
                    mHandler.removeMessages(123);
                }
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                LogUtil.i("播放完了");
                mHandler.removeMessages(123);
                if (mp.getDuration() == 0) {
                    LogUtil.i("播放完了");
                } else {
                    if (isMusicLoadSuccess) {
//                        changeNext();
                    } else {
                        ToastUtil.showMessage("音频未完成加载");
                    }
                }
            }
        });

        seek_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tv_starttime.setText(formatDuring(seekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
    }


    @Override
    protected void loadData() {

    }


    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeMessages(123);
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            tv_play.setBackgroundResource(R.drawable.ic_start);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_back) {
            finish();
        }
    }

    /**
     * 换算时间
     *
     * @param time 时间
     * @return
     */
    public static String formatDuring(long time) {
        long minutes = (time % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (time % (1000 * 60)) / 1000;
        String min = minutes < 10 ? "0" + minutes : minutes + "";
        String sec = seconds < 10 ? "0" + seconds : seconds + "";
        return min + ":" + sec;
    }

}
