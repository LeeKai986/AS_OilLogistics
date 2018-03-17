package com.hyphenate.easeui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zpf.oillogistics.R;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseAvatarOptions;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.hyphenate.easeui.widget.EaseImageView;
import com.hyphenate.util.EMLog;
import com.zpf.oillogistics.base.CyApplication;
import com.zpf.oillogistics.bean.FriendDetailsBean;
import com.zpf.oillogistics.net.UrlUtil;

import java.util.ArrayList;
import java.util.List;

public class EaseContactAdapter extends ArrayAdapter<EaseUser> implements SectionIndexer {
    private static final String TAG = "ContactAdapter";
    private Context context;
    List<String> list;
    ArrayList<String> listname = new ArrayList<>();
    List<EaseUser> userList;
    List<EaseUser> copyUserList;
    FriendDetailsBean friendDetailsBean;
    private LayoutInflater layoutInflater;
    private SparseIntArray positionOfSection;
    private SparseIntArray sectionOfPosition;
    private int res;
    private MyFilter myFilter;
    private boolean notiyfyByFilter;

    public EaseContactAdapter(Context context, int resource, List<EaseUser> objects, FriendDetailsBean friendDetailsBean) {
        super(context, resource, objects);
        this.context = context;
        this.res = resource;
        this.userList = objects;
        this.friendDetailsBean = friendDetailsBean;
        copyUserList = new ArrayList<EaseUser>();
        copyUserList.addAll(objects);
        layoutInflater = LayoutInflater.from(context);
    }

    private static class ViewHolder {
        ImageView avatar;
        TextView nameView;
        TextView headerView;
        TextView phone;
        CheckBox checkBox;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            if (res == 0) {
                convertView = layoutInflater.inflate(R.layout.ease_row_contact, parent, false);
                holder.avatar = (ImageView) convertView.findViewById(R.id.avatar);
                holder.nameView = (TextView) convertView.findViewById(R.id.name);
                holder.headerView = (TextView) convertView.findViewById(R.id.header);
                holder.phone = (TextView) convertView.findViewById(R.id.signature);
            } else {
                convertView = layoutInflater.inflate(res, parent, false);
                holder.checkBox = convertView.findViewById(R.id.cb_selectmenber_adapter);
                holder.avatar = (ImageView) convertView.findViewById(R.id.iv_head_selectmenber_adapter);
                holder.nameView = (TextView) convertView.findViewById(R.id.tv_name_selectmenber_adapter);
                holder.phone = (TextView) convertView.findViewById(R.id.tv_phone_selectmenber_adapter);
                holder.headerView = (TextView) convertView.findViewById(R.id.header);
            }

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        EaseUser user = getItem(position);

        if (user == null)
            Log.d("ContactAdapter", position + "");
        final String username = user.getUsername();
        String header = user.getInitialLetter();

        if (position == 0 || header != null && !header.equals(getItem(position - 1).getInitialLetter())) {
            if (TextUtils.isEmpty(header)) {
                holder.headerView.setVisibility(View.GONE);
            } else {
                holder.headerView.setVisibility(View.VISIBLE);
                holder.headerView.setText(header);
            }
        } else {
            holder.headerView.setVisibility(View.GONE);
        }

        EaseAvatarOptions avatarOptions = EaseUI.getInstance().getAvatarOptions();
        if (avatarOptions != null && holder.avatar instanceof EaseImageView) {
            EaseImageView avatarView = ((EaseImageView) holder.avatar);
            if (avatarOptions.getAvatarShape() != 0)
                avatarView.setShapeType(avatarOptions.getAvatarShape());
            if (avatarOptions.getAvatarBorderWidth() != 0)
                avatarView.setBorderWidth(avatarOptions.getAvatarBorderWidth());
            if (avatarOptions.getAvatarBorderColor() != 0)
                avatarView.setBorderColor(avatarOptions.getAvatarBorderColor());
            if (avatarOptions.getAvatarRadius() != 0)
                avatarView.setRadius(avatarOptions.getAvatarRadius());
        }

        EaseUserUtils.setUserNick(username, holder.nameView);
//        EaseUserUtils.setUserAvatar(getContext(), username, holder.avatar);


        if (primaryColor != 0)
            holder.nameView.setTextColor(primaryColor);
        if (primarySize != 0)
            holder.nameView.setTextSize(TypedValue.COMPLEX_UNIT_PX, primarySize);
        if (initialLetterBg != null)
            holder.headerView.setBackgroundDrawable(initialLetterBg);
        if (initialLetterColor != 0)
            holder.headerView.setTextColor(initialLetterColor);


        if (holder.checkBox != null) {
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        listname.add(username);
                        buttonView.setButtonDrawable(R.mipmap.check_on_bg);
                    } else {
                        listname.remove(username);
                        buttonView.setButtonDrawable(R.mipmap.check_off_bg);
                    }
                }
            });
        }
        if (CyApplication.findUserData(username) != null) {
            holder.phone.setVisibility(View.VISIBLE);
            holder.phone.setText(CyApplication.findUserData(username).getPhone());
            EaseUserUtils.setUserAvatar(context, username, holder.avatar);
        } else {
            FriendDetailsBean.DataBean dataBean = null;
            if (friendDetailsBean != null && friendDetailsBean.getData() != null && position < friendDetailsBean.getData().size()) {
                dataBean = friendDetailsBean.getData().get(position);
                if (holder.phone != null && dataBean != null) {
                    holder.phone.setVisibility(View.VISIBLE);
                    holder.phone.setText(dataBean.getRelname());
                }

                if (holder.avatar != null && dataBean != null) {
                    Glide.with(context)
                            .load(UrlUtil.IMAGE_URL + dataBean.getFace())
                            .dontAnimate()
                            .error(R.mipmap.head_default)
                            .fitCenter()
                            .into(holder.avatar);
                }
            }
        }


        return convertView;
    }

    @Override
    public EaseUser getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public int getPositionForSection(int section) {
        return positionOfSection.get(section);
    }

    @Override
    public int getSectionForPosition(int position) {
        return sectionOfPosition.get(position);
    }

    @Override
    public Object[] getSections() {
        positionOfSection = new SparseIntArray();
        sectionOfPosition = new SparseIntArray();
        int count = getCount();
        list = new ArrayList<String>();
        list.add(getContext().getString(R.string.search_header));
        positionOfSection.put(0, 0);
        sectionOfPosition.put(0, 0);
        for (int i = 1; i < count; i++) {

            String letter = getItem(i).getInitialLetter();
            int section = list.size() - 1;
            if (list.get(section) != null && !list.get(section).equals(letter)) {
                list.add(letter);
                section++;
                positionOfSection.put(section, i);
            }
            sectionOfPosition.put(i, section);
        }
        return list.toArray(new String[list.size()]);
    }

    @Override
    public Filter getFilter() {
        if (myFilter == null) {
            myFilter = new MyFilter(userList);
        }
        return myFilter;
    }

    protected class MyFilter extends Filter {
        List<EaseUser> mOriginalList = null;

        public MyFilter(List<EaseUser> myList) {
            this.mOriginalList = myList;
        }

        @Override
        protected synchronized FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();
            if (mOriginalList == null) {
                mOriginalList = new ArrayList<EaseUser>();
            }
            EMLog.d(TAG, "contacts original size: " + mOriginalList.size());
            EMLog.d(TAG, "contacts copy size: " + copyUserList.size());

            if (prefix == null || prefix.length() == 0) {
                results.values = copyUserList;
                results.count = copyUserList.size();
            } else {

                if (copyUserList.size() > mOriginalList.size()) {
                    mOriginalList = copyUserList;
                }
                String prefixString = prefix.toString();
                final int count = mOriginalList.size();
                final ArrayList<EaseUser> newValues = new ArrayList<EaseUser>();
                for (int i = 0; i < count; i++) {
                    final EaseUser user = mOriginalList.get(i);
                    String username = user.getUsername();

                    if (username.startsWith(prefixString)) {
                        newValues.add(user);
                    } else {
                        final String[] words = username.split(" ");
                        final int wordCount = words.length;

                        // Start at index 0, in case valueText starts with space(s)
                        for (String word : words) {
                            if (word.startsWith(prefixString)) {
                                newValues.add(user);
                                break;
                            }
                        }
                    }
                }
                results.values = newValues;
                results.count = newValues.size();
            }
            EMLog.d(TAG, "contacts filter results size: " + results.count);
            return results;
        }

        @Override
        protected synchronized void publishResults(CharSequence constraint,
                                                   FilterResults results) {
            userList.clear();
            userList.addAll((List<EaseUser>) results.values);
            EMLog.d(TAG, "publish contacts filter results size: " + results.count);
            if (results.count > 0) {
                notiyfyByFilter = true;
                notifyDataSetChanged();
                notiyfyByFilter = false;
            } else {
                notifyDataSetInvalidated();
            }
        }
    }


    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        if (!notiyfyByFilter) {
            copyUserList.clear();
            copyUserList.addAll(userList);
        }
    }

    protected int primaryColor;
    protected int primarySize;
    protected Drawable initialLetterBg;
    protected int initialLetterColor;

    public EaseContactAdapter setPrimaryColor(int primaryColor) {
        this.primaryColor = primaryColor;
        return this;
    }


    public EaseContactAdapter setPrimarySize(int primarySize) {
        this.primarySize = primarySize;
        return this;
    }

    public EaseContactAdapter setInitialLetterBg(Drawable initialLetterBg) {
        this.initialLetterBg = initialLetterBg;
        return this;
    }

    public EaseContactAdapter setInitialLetterColor(int initialLetterColor) {
        this.initialLetterColor = initialLetterColor;
        return this;
    }

    public ArrayList<String> getListname() {
        return listname;
    }

    public void setListname(ArrayList<String> listname) {
        this.listname = listname;
    }
}
