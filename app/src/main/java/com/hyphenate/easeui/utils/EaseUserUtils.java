package com.hyphenate.easeui.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.EaseUI.EaseUserProfileProvider;
import com.hyphenate.easeui.db.PersonInfo;
import com.hyphenate.easeui.domain.EaseUser;
import com.zpf.oillogistics.R;
import com.zpf.oillogistics.base.CyApplication;
import com.zpf.oillogistics.base.MessageWhat;
import com.zpf.oillogistics.bean.response.AddFriendResponse;
import com.zpf.oillogistics.net.SimplifyThread;
import com.zpf.oillogistics.net.UrlUtil;

import java.util.HashMap;

public class EaseUserUtils {
    static EaseUserProfileProvider userProvider;
    private static EaseUserUtils easeUtils;

    public static void init() {
        if (easeUtils == null) {
            easeUtils = new EaseUserUtils();
        }
    }

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
                    getUserDetails(username);
                    if (CyApplication.findUserData(username) != null) {
                        PersonInfo userinfo = CyApplication.findUserData(username);
                        Glide.with(context).load(UrlUtil.IMAGE_URL + userinfo.getHeader()).error(R.drawable.ease_default_avatar).into(imageView);
                    } else {
                        Glide.with(context).load(user.getAvatar()).error(R.drawable.ease_default_avatar).into(imageView);
                    }
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
                getUserDetails(username);
                if (CyApplication.findUserData(username) != null) {
                    PersonInfo userinfo = CyApplication.findUserData(username);
                    Glide.with(context).load(UrlUtil.IMAGE_URL + userinfo.getHeader()).error(R.drawable.ease_default_avatar).into(imageView);
                } else {
                    Glide.with(context).load(R.drawable.ease_default_avatar).into(imageView);
                }
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
                    getUserDetails(username);
                    if (CyApplication.findUserData(username) != null) {
                        PersonInfo userinfo = CyApplication.findUserData(username);
                        textView.setText(userinfo.getRelname());
                    } else {
                        textView.setText(user.getNick());
                    }

                }

            } else {
                if (CyApplication.findUserData(username) != null) {
                    PersonInfo userinfo = CyApplication.findUserData(username);
                    textView.setText(userinfo.getRelname());
                } else {
                    getUserDetails(username);
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

    /**
     * 查找好友
     */
    private static Gson gson = new Gson();

    private static void getUserDetails(String phone) {
        final PersonInfo personInfo = new PersonInfo();
        HashMap<String, String> friendsHp = new HashMap<>();
        friendsHp.put("phone", phone);
        SimplifyThread simplifyThread = new SimplifyThread(UrlUtil.URL_USER_NICK, friendsHp);
        simplifyThread.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                Message message = new Message();
                message.obj = res;
                message.what = MessageWhat.ADDFRIENDS_GET_SELECT;
                handler2.sendMessage(message);
            }

            @Override
            public void errorBody(String error) {
                handler2.sendEmptyMessage(MessageWhat.REQUST_ERROR);
            }
        });
    }

    private static Handler handler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MessageWhat.ADDFRIENDS_GET_SELECT:
                    if (msg.obj != null) {
                        try {
                            AddFriendResponse add = gson.fromJson(msg.obj.toString(), AddFriendResponse.class);
                            if (add.getStatus() == 0) {
                                if (add.getData() != null) {
                                    PersonInfo personInfo = new PersonInfo();
                                    personInfo.setPhone(add.getData().getPhone());
                                    personInfo.setHeader(add.getData().getHeader());
                                    personInfo.setRelname(add.getData().getRelname());
                                    CyApplication.saveUserData(personInfo);

                                }
                            }

                        } catch (Exception e) {
                        }

                    } else {
                    }
                    break;
            }
        }
    };
}
