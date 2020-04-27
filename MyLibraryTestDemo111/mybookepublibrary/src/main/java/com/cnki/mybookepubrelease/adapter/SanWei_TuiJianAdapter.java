package com.cnki.mybookepubrelease.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cnki.mybookepubrelease.R;
import com.cnki.mybookepubrelease.model.SanWeiBookCategoryTuis;
import com.cnki.mybookepubrelease.widget.RoundAngleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2020/4/23
 * 推荐图书列表适配器
 */
public class SanWei_TuiJianAdapter extends BaseAdapter {
    private Activity mContext;
    private List<SanWeiBookCategoryTuis> mList;
    private LayoutInflater mInflater;

    public SanWei_TuiJianAdapter(Activity context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mList = new ArrayList<SanWeiBookCategoryTuis>();
    }
    public void addData(List<SanWeiBookCategoryTuis> list, boolean isFirstPage) {
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
            view = mInflater.inflate(R.layout.item_sanweibooktuijian, viewGroup, false);
            hold.civ_cover =view.findViewById(R.id.civ_cover);
            hold.tv_booktitle = view.findViewById(R.id.tv_booktitle);
            view.setTag(hold);
        } else {
            hold = (ViewHold) view.getTag();
        }
        SanWeiBookCategoryTuis sanWeiPingLunBean = mList.get(position);
        ImageLoader.getInstance().displayImage(sanWeiPingLunBean.getCover(), hold.civ_cover);
        hold.tv_booktitle.setText(sanWeiPingLunBean.getBookName());
        return view;
    }

    class ViewHold {
        RoundAngleImageView civ_cover;
        TextView tv_booktitle;
    }
}
