package com.cnki.mybookepubrelease.common;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.cnki.mybookepubrelease.R;
import com.huangfei.library.utils.NetUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * Created by Administrator on 2020/4/5.
 * 自定义ImageLoader
 */
public class MyImageLoader {
    private static volatile MyImageLoader instance = null;

    private MyImageLoader() {
    }

    public static MyImageLoader getInstance() {
        if (instance == null) {
            synchronized (MyImageLoader.class) {
                if (instance == null) {
                    instance = new MyImageLoader();
                }
            }
        }
        return instance;
    }

    public void clearMemoryCache() {
        ImageLoader.getInstance().clearMemoryCache();
    }

    public void clearDiskCache() {
        ImageLoader.getInstance().clearDiskCache();
    }

    public void clearCache() {
        ImageLoader.getInstance().clearMemoryCache();
        ImageLoader.getInstance().clearDiskCache();
    }

    public void displayImage(String uri, ImageView imageView) {
        displayImage(uri, imageView, R.mipmap.ic_empty);
    }

    public void displayImage(String uri, ImageView imageView, int defaultImage) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(defaultImage)
                .showImageForEmptyUri(defaultImage)
                .showImageOnFail(defaultImage)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                .displayer(new SimpleBitmapDisplayer())
                .build();
        if (!NetUtils.isNetworkConnected()&&!uri.startsWith("file://"))
            ImageLoader.getInstance().displayImage("", imageView, options);
        else
            ImageLoader.getInstance().displayImage(uri, imageView, options);
    }

    public void loadImage(String uri, int defaultImage, ImageLoadingListener listener) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(defaultImage)
                .showImageForEmptyUri(defaultImage)
                .showImageOnFail(defaultImage)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.NONE)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new SimpleBitmapDisplayer())
                .build();
        if (!NetUtils.isWifiConnected()&& !uri.startsWith("file://"))
            ImageLoader.getInstance().loadImage("", options, listener);
        else
            ImageLoader.getInstance().loadImage(uri, options, listener);
    }

    public Bitmap loadImageSync(String uri) {
        Bitmap bitmap = ImageLoader.getInstance().loadImageSync(uri);
        return bitmap;
    }
}
