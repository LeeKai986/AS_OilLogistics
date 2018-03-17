package com.hyphenate.easeui.utils;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.EaseUI.EaseUserProfileProvider;
import com.hyphenate.easeui.db.PersonInfo;
import com.hyphenate.easeui.domain.EaseUser;
import com.zpf.oillogistics.R;
import com.zpf.oillogistics.base.CyApplication;
import com.zpf.oillogistics.net.UrlUtil;

public class EaseUserUtils {

    static EaseUserProfileProvider userProvider;

    static {
        userProvider = EaseUI.getInstance().getUserProfileProvider();
    }

    /**
     * get EaseUser according username
     *
     * @param username
     * @return
     */
    public static EaseUser getUserInfo(String username) {
        if (userProvider != null)
            return userProvider.getUser(username);

        return null;
    }

    /**
     * set user avatar
     *
     * @param username
     */
    public static void setUserAvatar(Context context, String username, ImageView imageView) {
        EaseUser user = getUserInfo(username);
        if (user != null && user.getAvatar() != null) {
            try {
                if (CyApplication.findUserData(username) != null) {
                    PersonInfo userinfo = CyApplication.findUserData(username);
                    Glide.with(context).load(UrlUtil.IMAGE_URL + userinfo.getHeader()).error(R.drawable.ease_default_avatar).into(imageView);
                } else {
                    Glide.with(context).load(user.getAvatar()).error(R.drawable.ease_default_avatar).into(imageView);
                }

//                int avatarResId = Integer.parseInt(user.getAvatar());
            } catch (Exception e) {
                //use default avatar
                Glide.with(context).load(user.getAvatar()).diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.ease_default_avatar).into(imageView);
            }
        } else {
            if (CyApplication.findUserData(username) != null) {
                PersonInfo userinfo = CyApplication.findUserData(username);
                Glide.with(context).load(UrlUtil.IMAGE_URL + userinfo.getHeader()).error(R.drawable.ease_default_avatar).into(imageView);
            } else {
                Glide.with(context).load(R.drawable.ease_default_avatar).into(imageView);
            }

        }
    }

    /**
     * set user's nickname
     */
    public static void setUserNick(String username, TextView textView) {
        if (textView != null) {
            EaseUser user = getUserInfo(username);
            if (user != null && user.getNick() != null) {
                if (CyApplication.findUserData(username) != null) {
                    PersonInfo userinfo = CyApplication.findUserData(username);
                    textView.setText(userinfo.getRelname());
                } else {
                    textView.setText(user.getNick());
                }

            } else {
                if (CyApplication.findUserData(username) != null) {
                    PersonInfo userinfo = CyApplication.findUserData(username);
                    textView.setText(userinfo.getRelname());
                } else {
                    textView.setText(username);
                }

            }
        }
    }

}
