package com.hyphenate.easeui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.utils.EaseSmileUtils;
import com.zpf.oillogistics.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/9/13.
 */

public class GroupsAdapter extends BaseAdapter {
    private Context mContext;
    private List<EMGroup> groups;
    private LayoutInflater inflater;

    public GroupsAdapter(Context mContext, List<EMGroup> groups) {
        this.mContext = mContext;
        this.groups = groups;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        if (groups != null)
            return groups.size();
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return groups.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = inflater.inflate(R.layout.adapter_groups, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        EMGroup group = (EMGroup) getItem(i);

        if (group != null) {
            if (group.getGroupName() != null && !group.getGroupName().equals(""))
                holder.tvName.setText(group.getGroupName());
            else
                holder.tvName.setText(group.getGroupId());

            EMConversation conversation = EMClient.getInstance().chatManager().getConversation(group.getGroupId());

            if (conversation != null && conversation.getAllMsgCount() != 0) {
                // show the content of latest message
                EMMessage lastMessage = conversation.getLastMessage();
                String content = null;

                holder.tvMesg.setText(EaseSmileUtils.getSmiledText(mContext, EaseCommonUtils.getMessageDigest(lastMessage, (mContext))),
                        TextView.BufferType.SPANNABLE);
//                if (content != null) {
//                    holder.tvMesg.setText(conversation.getLastMessage().getFrom() + "" + content);
//                }


                if (conversation.getUnreadMsgCount() > 0) {
                    // show unread message count
                    if (conversation.getUnreadMsgCount() <= 99) {
                        holder.unreadLabel.setText(String.valueOf(conversation.getUnreadMsgCount()));
                    } else {
                        holder.unreadLabel.setText("99+");
                    }
                    holder.unreadLabel.setVisibility(View.VISIBLE);
                } else {
                    holder.unreadLabel.setVisibility(View.INVISIBLE);
                }
            }
        }

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.iv_head_groups_adapter)
        CircleImageView ivHead;
        @BindView(R.id.tv_name_groups_adapter)
        TextView tvName;
        @BindView(R.id.tv_mesg_groups_adapter)
        TextView tvMesg;
        @BindView(R.id.tv_time_groups_adapter)
        TextView tvTime;
        @BindView(R.id.unread_msg_number)
        TextView unreadLabel;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
