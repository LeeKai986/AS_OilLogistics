package com.hyphenate.easeui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.easeui.domain.EaseUser;
import com.zpf.oillogistics.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/14.
 */

public class SelectMemberAdapter extends BaseAdapter {

    private List<EaseUser> mList;
    private Context mContext;
    private LayoutInflater inflater;
    private ArrayList<String> list=new ArrayList<>();

    public SelectMemberAdapter(List<EaseUser> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
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
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder = null;
        if (view == null) {
            view = inflater.inflate(R.layout.adapter_selectmenber, viewGroup, false);
            holder=new ViewHolder(view);
            view.setTag(holder);
        }else {
            holder= (ViewHolder) view.getTag();
        }

        final String name=mList.get(i).getUsername();

        holder.tvName.setText(mList.get(i).getUsername());

        holder.cbSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    list.add(name);
                    buttonView.setButtonDrawable(R.mipmap.check_on_bg);
                }else {
                    list.remove(name);
                    buttonView.setButtonDrawable(R.mipmap.check_off_bg);
                }
            }
        });

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.cb_selectmenber_adapter)
        CheckBox cbSelect;
        @BindView(R.id.iv_head_selectmenber_adapter)
        ImageView ivHead;
        @BindView(R.id.tv_name_selectmenber_adapter)
        TextView tvName;
        @BindView(R.id.tv_phone_selectmenber_adapter)
        TextView tvPhone;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
