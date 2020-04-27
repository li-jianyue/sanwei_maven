package com.cnki.mybookepubrelease.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cnki.mybookepubrelease.R;
import com.cnki.mybookepubrelease.model.BookCatelogsBean;
import com.cnki.mybookepubrelease.model.SanWeiPingLunBean;
import com.cnki.mybookepubrelease.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2020/4/23
 * 评论列表适配器
 */
public class SanWei_BookCatelogsAdapter extends BaseAdapter {
    private Activity mContext;
    private List<BookCatelogsBean> mList;
    private LayoutInflater mInflater;

    public SanWei_BookCatelogsAdapter(Activity context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mList = new ArrayList<BookCatelogsBean>();
    }
    public void addData(List<BookCatelogsBean> list, boolean isFirstPage) {
        if (isFirstPage)
            mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHold hold;
        if (view == null) {
            hold = new ViewHold();
            view = mInflater.inflate(R.layout.item_bookcatelogs, viewGroup, false);
            hold.tv_catelog =view.findViewById(R.id.tv_catelog);
            view.setTag(hold);
        } else {
            hold = (ViewHold) view.getTag();
        }
        BookCatelogsBean sanWeiPingLunBean = mList.get(position);
        hold.tv_catelog.setText(sanWeiPingLunBean.getIndex()+"."+sanWeiPingLunBean.getTitle());
        return view;
    }

    class ViewHold {
        TextView tv_catelog;
    }
}
