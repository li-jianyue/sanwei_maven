package com.cnki.mybookepubrelease.adapter;

import android.app.Activity;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cnki.mybookepubrelease.R;
import com.cnki.mybookepubrelease.model.SanWeiPingLunBean;
import com.cnki.mybookepubrelease.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Administrator on 2020/4/23
 * 评论列表适配器
 */
public class SanWei_PingLunAdapter extends BaseAdapter {
    private Activity mContext;
    private List<SanWeiPingLunBean> mList;
    private LayoutInflater mInflater;

    public SanWei_PingLunAdapter(Activity context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mList = new ArrayList<SanWeiPingLunBean>();
    }
    public void addData(List<SanWeiPingLunBean> list, boolean isFirstPage) {
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
            view = mInflater.inflate(R.layout.item_sanweipinglun, viewGroup, false);
            hold.civ_head =view.findViewById(R.id.civ_head);
            hold.tv_username = view.findViewById(R.id.tv_username);
            hold.tv_pingluntitle = view.findViewById(R.id.tv_pingluntitle);
            hold.tv_pingluninfo =view.findViewById(R.id.tv_pingluninfo);
            view.setTag(hold);
        } else {
            hold = (ViewHold) view.getTag();
        }
        SanWeiPingLunBean sanWeiPingLunBean = mList.get(position);
        hold.tv_username.setText(sanWeiPingLunBean.getUsername());
        hold.tv_pingluntitle.setText(sanWeiPingLunBean.getPinglun_title());
        hold.tv_pingluninfo.setText(sanWeiPingLunBean.getPinglun_info());
        return view;
    }

    class ViewHold {
        CircleImageView civ_head;
        TextView tv_username;
        TextView tv_pingluntitle;
        TextView tv_pingluninfo;
    }
}
