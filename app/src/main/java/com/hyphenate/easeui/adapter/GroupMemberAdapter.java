package com.hyphenate.easeui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.hyphenate.easeui.utils.EaseUserUtils;
import com.zpf.oillogistics.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/9/15.
 */

public class GroupMemberAdapter extends BaseAdapter {

    private List<String> mList;
    private Context context;
    private LayoutInflater inflater;
    private boolean isCanAdd;

    public GroupMemberAdapter(List<String> mList, Context context, boolean isCanAdd) {
        this.mList = mList;
        this.context = context;
        this.isCanAdd = isCanAdd;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (mList != null) {
            if (isCanAdd)
                return mList.size() + 2;
            else
                return mList.size();
        }
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = inflater.inflate(R.layout.adapter_goups_menber, viewGroup, false);
            holder = new ViewHolder(view);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        if (isCanAdd) {

            if (i == getCount() - 1) {
                holder.ivHead.setTag(i);
                holder.ivHead.setBackgroundResource(R.mipmap.kick_menber_icon);
            } else if (i == getCount() - 2) {
                holder.ivHead.setTag(i);
                holder.ivHead.setBackgroundResource(R.mipmap.add_menber_icon);
            } else {
                EaseUserUtils.setUserAvatar(context, mList.get(i), holder.ivHead);
            }

            holder.ivHead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    int n = (int) view.getTag();
                    if (i == getCount() - 1) {
                        memberClickListener.kickMember();
                    } else if (i == getCount() - 2) {
                        memberClickListener.addMenber();
                    }
                }
            });
        } else {
            EaseUserUtils.setUserAvatar(context, mList.get(i), holder.ivHead);
        }

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.iv_head_groupmenber)
        CircleImageView ivHead;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    MemberClickListener memberClickListener;

    public void setMemberClickListener(MemberClickListener memberClickListener) {
        this.memberClickListener = memberClickListener;
    }

    public interface MemberClickListener {

        void kickMember();

        void addMenber();

    }
}
