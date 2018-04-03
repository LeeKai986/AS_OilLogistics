package com.hyphenate.easeui.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hyphenate.easeui.DemoHelper;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.zpf.oillogistics.bean.response.FriendsListResponse;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/14.
 */

public class MyFriendsFragment extends EaseContactListFragment {


    public void setFriendsList(List<FriendsListResponse.DataBean> friendsList) {
        this.friendsList = friendsList;
    }

    private List<FriendsListResponse.DataBean> friendsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setContactListItemClickListener(listItemClickListener);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    public void onStart() {
        super.onStart();

        Map<String, EaseUser> m = DemoHelper.getInstance().getContactList();
        m.clear();
        for (int i = 0; i < friendsList.size(); i++) {
            FriendsListResponse.DataBean friendsBean = friendsList.get(i);
            EaseUser user = new EaseUser(friendsBean.getPhone());
            user.setAvatar(friendsBean.getFace());
            user.setPhone(friendsBean.getPhone());
            EaseCommonUtils.setUserInitialLetter(user);
            m.put(friendsBean.getPhone(), user);
        }
        if (m instanceof Hashtable<?, ?>) {
            //noinspection unchecked
            m = (Map<String, EaseUser>) ((Hashtable<String, EaseUser>) m).clone();
        }
        setContactsMap(m);

    }


    @Override
    public void onViewClink(View v) {

    }

    EaseContactListItemClickListener listItemClickListener = new EaseContactListItemClickListener() {
        @Override
        public void onListItemClicked(EaseUser user) {
            Intent intent = new Intent(getActivity(), FriendsChatActivity.class);
            intent.putExtra("username", user.getUsername());
            getActivity().startActivity(intent);
        }
    };
}
