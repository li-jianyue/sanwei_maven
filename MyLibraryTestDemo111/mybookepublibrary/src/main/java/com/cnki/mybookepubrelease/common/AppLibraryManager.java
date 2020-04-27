package com.cnki.mybookepubrelease.common;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;

import com.cnki.mybookepubrelease.model.DownLoadHistory;
import com.huangfei.library.activeandroid.ActiveAndroid;
import com.huangfei.library.activeandroid.Configuration;
import com.huangfei.library.commom.BaseApplication;
import com.huangfei.library.utils.LogUtils;
import com.koolearn.android.kooreader.config.ConfigShadow;
import com.koolearn.klibrary.api.KooReaderIntents;
import com.koolearn.klibrary.ui.android.image.ZLAndroidImageManager;
import com.koolearn.klibrary.ui.android.library.ZLAndroidLibrary;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class AppLibraryManager {

    private static ZLAndroidLibrary myLibrary;
    private static ConfigShadow myConfig;
    public static Application mContext;
    // 获取到主线程的handler
    private static Handler mMainThreadHandler = null;

    // 获取到主线程
    private static Thread mMainThread = null;
    // 获取到主线程的id
    private static int mMainThreadId;
    // 获取到主线程的looper
    private static Looper mMainThreadLooper = null;

    // 对外暴露主线程的handler
    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    // 对外暴露主线程
    public static Thread getMainThread() {
        return mMainThread;
    }

    // 对外暴露主线程id
    public static int getMainThreadId() {
        return mMainThreadId;
    }

    // 对外暴露主线程的looper
    public static Looper getMainThreadLooper() {
        return mMainThreadLooper;
    }


    public static void initEPubLibrary(Application context){
        mContext=context;

        //初始化ActiveAndroid
        Configuration.Builder config = new Configuration.Builder(context);
        config.addModelClasses(DownLoadHistory.class);
        ActiveAndroid.initialize(config.create());
        String packagename=getPackageName(context);
        if (packagename==null){
            KooReaderIntents.DEFAULT_PACKAGE="com.wbb.broadcasr";
        }else {
            KooReaderIntents.DEFAULT_PACKAGE=packagename;
        }
//        LogUtils.e("packagename::"+KooReaderIntents.DEFAULT_PACKAGE);
        myConfig = new ConfigShadow(context); // 绑定服务,创建config.db
        new ZLAndroidImageManager();
        myLibrary = new ZLAndroidLibrary(context); // 一些获取屏幕宽高,版本号的方法
        initImageLoader();
        mMainThreadHandler = new Handler();
        mMainThread = Thread.currentThread();
        mMainThreadId = android.os.Process.myTid();
        mMainThreadLooper = context.getMainLooper();
    }
    public static final ZLAndroidLibrary library() {
        return myLibrary;
    }
    /**

     * [获取应用程序版本名称信息]
     * @param context
     * @return 当前应用的版本名称

     */
    public static synchronized String getPackageName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();

            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.packageName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
    /**
     * UIL图片加载配置
     */
    private static void initImageLoader() {
        // 个性化参数配置
//        File file = FileStorageUtils.getImageLoaderCacheDir(this);
//        file = new ImageLoaderConfiguration.Builder(this).memoryCacheExtraOptio ns(480, 800).threadPoolSize(20).threadPriority(3).denyCacheImageMultipleSizesInMemory().memoryCache(new UsingFreqLimitedMemoryCache(2097152)).discCacheSize(52428800).discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).discCacheFileCount(100).discCache(new UnlimitedDiscCache((File) file)).defaultDisplayImageOptions(DisplayImageOptions.createSimple()).imageDownloader(new OkHttpImageDownLoader(this, 5000, 30000)).writeDebugLogs().build();
//        ImageLoader.getInstance().init((ImageLoaderConfiguration) file);
        //创建默认的ImageLoader配置参数
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(mContext);
        //Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(configuration);
    }
}
