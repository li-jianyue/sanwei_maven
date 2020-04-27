package com.cnki.mybookepubrelease.widget;

import android.app.Activity;
import android.app.Dialog;
import android.widget.TextView;

import com.cnki.mybookepubrelease.R;

/**
 * Created by Administrator on 2016/5/12.
 * 加载对话框
 */
public class LoadDialog {
    private Dialog mDialog;
    private TextView mContentTextView;
    private boolean isCancel;
    private Activity activity;

    public LoadDialog(Activity context) {
        mDialog = new Dialog(context, R.style.progress_dialog);
        mDialog.setContentView(R.layout.dialog_progress);
        mContentTextView = (TextView) mDialog.findViewById(R.id.tv_content);
    }

    public Activity getActivity() {

        return mDialog.getOwnerActivity();
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public boolean isCancel() {
        return isCancel;
    }

    public void setCancel(boolean cancel) {
        isCancel = cancel;
        mDialog.setCanceledOnTouchOutside(isCancel);
    }

    public void show() {
        mDialog.show();
    }

    public void dismiss() {
        mDialog.dismiss();
    }

    public void setContent(String content) {
        mContentTextView.setText(content);
    }

    public void setContent(int content) {
        mContentTextView.setText(content);
    }
}
