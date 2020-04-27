package com.cnki.myaarapplication;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;

import com.cnki.mybookepubrelease.utils.ToastUtil;
import com.huangfei.library.activity.BaseActivity;
import com.yxp.permission.util.lib.PermissionUtil;
import com.yxp.permission.util.lib.callback.PermissionResultCallBack;


public class PermissionActivity extends BaseActivity {
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            new AlertDialog.Builder(PermissionActivity.this)
                    .setIcon(com.cnki.mybookepubrelease.R.mipmap.ic_launcher)
                    .setMessage("部分权限被禁止了，需要手动开启后才能正常使用")
                    .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent= getAppDetailSettingIntent(PermissionActivity.this);
                            startActivity(intent);

                        }
                    })
                    .setNegativeButton("退出", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            PermissionActivity.this.finish();
                        }
                    }).show();
        }
    };

    public static void startActivity(Context context, String bookid) {
        Intent intent = new Intent(context, PermissionActivity.class);
        intent.putExtra("bookid", bookid);
        context.startActivity(intent);
    }
    @Override
    protected void init() {

    }

    @Override
    protected void initView() {
        PermissionUtil.getInstance().request(this, new String[]{ Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                new PermissionResultCallBack() {
                    @Override
                    public void onPermissionGranted() {
                        // 当所有权限的申请被用户同意之后,该方法会被调用
                        MainActivity.startActivity(PermissionActivity.this);
                        PermissionActivity.this.finish();
                    }

                    @Override
                    public void onPermissionGranted(String... strings) {
                        // 当所有权限的申请被用户同意之后,该方法会被调用
                    }

                    @Override
                    public void onPermissionDenied(String... permissions) {
                        ToastUtil.showMessage("部分权限被禁止需要手动开启");
                        // 当权限申请中的某一个或多个权限,被用户曾经否定了,并确认了不再提醒时,也就是权限的申请窗口不能再弹出时,该方法将会被调用
                        mHandler.sendEmptyMessage(0);
                    }

                    @Override
                    public void onRationalShow(String... permissions) {
                        ToastUtil.showMessage("部分权限被禁止需要手动开启");
                        // 当权限申请中的某一个或多个权限,被用户否定了,但没有确认不再提醒时,也就是权限窗口申请时,但被否定了之后,该方法将会被调用.
                        mHandler.sendEmptyMessage(0);
                    }
                });
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void loadData() {

    }
    /**
     * 获取应用详情页面intent
     * @return
     */
    public static Intent getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        return localIntent;
    }
}
