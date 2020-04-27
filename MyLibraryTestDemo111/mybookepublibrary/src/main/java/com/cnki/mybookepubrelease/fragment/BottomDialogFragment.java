package com.cnki.mybookepubrelease.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.format.DateUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cnki.mybookepubrelease.R;
import com.cnki.mybookepubrelease.adapter.SanWei_BookCatelogsAdapter;
import com.cnki.mybookepubrelease.common.Page;
import com.cnki.mybookepubrelease.model.BookCatelogsBean;
import com.cnki.mybookepubrelease.protocol.SanWeiBookCatelogsListProtocol;
import com.cnki.mybookepubrelease.utils.ToastUtil;
import com.huangfei.library.utils.NetUtils;
import com.huangfei.library.utils.UIUtils;
import com.ljy.library.PullToRefreshBase;
import com.ljy.library.PullToRefreshListView;

import java.util.List;

import okhttp3.Call;

public class BottomDialogFragment extends DialogFragment {
    private View frView;
    private Window window;
    private SanWeiBookCatelogsListProtocol getSanWeiBookCatelogsListProtocol;

    private PullToRefreshListView lv_catelogs;
    private SanWei_BookCatelogsAdapter sanWei_bookCatelogsAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        frView = inflater.inflate(R.layout.dialog_fr_bottom, null);
        Bundle bundle=getArguments();
        String path=bundle.getString("sanwei_path");

        lv_catelogs=frView.findViewById(R.id.lv_catelogs);
        sanWei_bookCatelogsAdapter=new SanWei_BookCatelogsAdapter(getActivity());
        lv_catelogs.setAdapter(sanWei_bookCatelogsAdapter);

        getSanWeiBookCatelogsListProtocol=new SanWeiBookCatelogsListProtocol(getActivity(), new Page.NetWorkCallBack<BookCatelogsBean>() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(List<BookCatelogsBean> list) {
                if (list == null || list.size() == 0) {
                    if (getSanWeiBookCatelogsListProtocol.isFirstPage()) {
                        sanWei_bookCatelogsAdapter.clear();
                    }
                }else {
                    sanWei_bookCatelogsAdapter.addData(list, getSanWeiBookCatelogsListProtocol.isFirstPage());
                }
                lv_catelogs.onRefreshComplete();
                lv_catelogs.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                getSanWeiBookCatelogsListProtocol.setRunning(false);
            }
        });
        if (NetUtils.isNetworkConnected()){
            getSanWeiBookCatelogsListProtocol.load(true,path,"titles");
        }else {
            ToastUtil.showMessage("网络未连接");
        }

        lv_catelogs.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        lv_catelogs.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                if (!NetUtils.isNetworkConnected()) {
                    lv_catelogs.onRefreshComplete();
                    UIUtils.showToastSafe(R.string.no_net_and_check, getActivity());
                    return;
                }

                String label = DateUtils.formatDateTime(getActivity().getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                getSanWeiBookCatelogsListProtocol.load(true,path,"titles");
            }
        });

        lv_catelogs.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                if (!NetUtils.isNetworkConnected()) {
                    UIUtils.showToastSafe(R.string.no_net_and_check, getActivity());
                    return;
                }
                if (getSanWeiBookCatelogsListProtocol.isLastPage()) {
                    lv_catelogs.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                    return;
                }
                lv_catelogs.setMode(PullToRefreshBase.Mode.MANUAL_REFRESH_ONLY);
                lv_catelogs.setRefreshing(false);
                getSanWeiBookCatelogsListProtocol.load(false,path,"titles");
            }
        });

        lv_catelogs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToastUtil.showMessage("目录只供浏览哦，详情请下载观看");
            }
        });

        return frView;
    }

    @Override
    public void onStart() {
        super.onStart();
        // 下面这些设置必须在此方法(onStart())中才有效

        window = getDialog().getWindow();
        // 如果不设置这句代码, 那么弹框就会与四边都有一定的距离
        window.setBackgroundDrawableResource(android.R.color.transparent);
        // 设置动画
        window.setWindowAnimations(R.style.bottomDialog);

        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        // 如果不设置宽度,那么即使你在布局中设置宽度为 match_parent 也不会起作用
        params.width = getResources().getDisplayMetrics().widthPixels;
        params.height=1200;
        window.setAttributes(params);
    }
}
