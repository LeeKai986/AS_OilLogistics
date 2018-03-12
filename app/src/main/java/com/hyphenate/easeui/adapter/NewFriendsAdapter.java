package com.hyphenate.easeui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zpf.oillogistics.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/13.
 */

public class NewFriendsAdapter extends BaseAdapter {
    private Context mContext;
    private List mList;
    private LayoutInflater inflater;

    public NewFriendsAdapter(Context mContext, List list) {
        this.mContext = mContext;
        this.mList = list;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        if (mList != null)
            return mList.size();
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = inflater.inflate(R.layout.adapter_new_friends, viewGroup, false);
        }
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.iv_head_newfriends_adapter)
        ImageView ivHead;
        @BindView(R.id.tv_name_newfriends_adapter)
        TextView tvName;
        @BindView(R.id.tv_phone_newfriends_adapter)
        TextView tvPhone;
        @BindView(R.id.tv_add_newfriends_adapter)
        TextView tvAdd;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
