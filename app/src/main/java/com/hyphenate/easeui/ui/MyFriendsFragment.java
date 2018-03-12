package com.hyphenate.easeui.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hyphenate.easeui.DemoHelper;
import com.hyphenate.easeui.domain.EaseUser;

import java.util.Hashtable;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/14.
 */

public class MyFriendsFragment extends EaseContactListFragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setContactListItemClickListener(listItemClickListener);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();

        Map<String, EaseUser> m = DemoHelper.getInstance().getContactList();
        if (m instanceof Hashtable<?, ?>) {
            //noinspection unchecked
            m = (Map<String, EaseUser>) ((Hashtable<String, EaseUser>)m).clone();
        }
        setContactsMap(m);
    }

    @Override
    public void onViewClink(View v) {

    }

    EaseContactListItemClickListener listItemClickListener=new EaseContactListItemClickListener() {
        @Override
        public void onListItemClicked(EaseUser user) {
            Intent intent=new Intent(getActivity(), FriendsChatActivity.class);
            intent.putExtra("username",user.getUsername());
            getActivity().startActivity(intent);
        }
    };
}
