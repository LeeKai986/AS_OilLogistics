package com.zpf.oillogistics.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.baidu.mapapi.search.sug.SuggestionResult;
import com.zpf.oillogistics.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/17.
 */

public class SearchAdressAdapter extends BaseAdapter {
    private Context context;
    private List<SuggestionResult.SuggestionInfo> mList;
    private LayoutInflater mInflater;

    public SearchAdressAdapter(Context context, List<SuggestionResult.SuggestionInfo> list) {
        this.context = context;
        this.mList = list;
        mInflater = LayoutInflater.from(context);
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
            view = mInflater.inflate(R.layout.adapter_searchadress, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.tvAdress.setText(mList.get(i).key);


        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tv_adress_search_adapter)
        TextView tvAdress;


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
