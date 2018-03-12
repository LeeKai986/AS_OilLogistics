package com.zpf.oillogistics.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.DemoHelper;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.hyphenate.easeui.ui.FriendsChatActivity;
import com.hyphenate.easeui.ui.GroupChatActivity;
import com.hyphenate.exceptions.HyphenateException;
import com.zpf.oillogistics.R;
import com.zpf.oillogistics.base.CyApplication;
import com.zpf.oillogistics.utils.MyShare;
import com.zpf.oillogistics.utils.MyToast;

import java.util.List;

/**
 * Created by Administrator on 2017/9/13.
 */

public class ChatFragment extends EaseConversationListFragment {

    Dialog dg;
    FragmentActivity FgAty;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
//            if (dg != null && dg.isShowing()) {
//                dg.dismiss();
//            }
            switch (message.what) {
                case 1:
                    EMClient.getInstance().login(MyShare.getShared().getString("userPhone", ""), "yyt123456", new EMCallBack() {//回调
                        @Override
                        public void onSuccess() {
                            EMClient.getInstance().groupManager().loadAllGroups();
                            EMClient.getInstance().chatManager().loadAllConversations();
                            Log.i("main", "登录聊天服务器成功！--");
                        }

                        @Override
                        public void onProgress(int progress, String status) {
                            Log.i("main", "登录聊天服务器失败！--");
                        }

                        @Override
                        public void onError(int code, String message) {
                            Log.i("main", "登录聊天服务器失败！--");
                        }
                    });

                    EMClient.getInstance().chatManager().addMessageListener(mMessageListener);
                    //注册一个监听连接状态的listener
                    EMClient.getInstance().addConnectionListener(new MyConnectionListener());
                    break;
            }
            return false;
        }
    });


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        FgAty = (FragmentActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setConversationListItemClickListener(new EaseConversationListItemClickListener() {
            @Override
            public void onListItemClicked(EMConversation conversation) {
                String username = conversation.conversationId();
                EMConversation.EMConversationType type = conversation.getType();
                if (username.equals(EMClient.getInstance().getCurrentUser()))
                    Toast.makeText(getActivity(), R.string.Cant_chat_with_yourself, Toast.LENGTH_SHORT).show();
                else {
                    if (type == EMConversation.EMConversationType.Chat) {
                        EMMessage msg = conversation.getLastMessage();
                        Intent in = new Intent(getActivity(), FriendsChatActivity.class);
                        in.putExtra("username", username);
                        startActivity(in);
                    } else {
                        Intent in = new Intent(getActivity(), GroupChatActivity.class);
                        in.putExtra("groupid", username);
                        startActivity(in);
                    }
                }
            }
        });


        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void initV() {
//        dg = ProgressDialog.show(getActivity(), "", "加载好友数据...");
//        dg.setCanceledOnTouchOutside(false);
//        dg.setCancelable(false);
//        dg.show();
        new Thread() {
            @Override
            public void run() {
                //注册失败会抛出HyphenateException
                try {
                    EMClient.getInstance().createAccount(MyShare.getShared().getString("userPhone", ""), "yyt123456");//同步方法
                    handler.sendEmptyMessage(1);
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(1);
                }
            }
        }.start();
    }

    // 环信的消息监听
    private EMMessageListener mMessageListener = new EMMessageListener() {

        // 收到消息
        @Override
        public void onMessageReceived(List<EMMessage> list) {


        }

        // 收到透传消息
        @Override
        public void onCmdMessageReceived(List<EMMessage> list) {

        }

        // 收到已读回执
        @Override
        public void onMessageRead(List<EMMessage> list) {

        }

        // 收到已送达回执
        @Override
        public void onMessageDelivered(List<EMMessage> list) {

        }

        @Override
        public void onMessageRecalled(List<EMMessage> list) {

        }

        // 消息状态变动
        @Override
        public void onMessageChanged(EMMessage emMessage, Object o) {
            FgAty.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                }
            });
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();

        DemoHelper.getInstance().logout(true, new EMCallBack() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError(int i, String s) {
            }

            @Override
            public void onProgress(int i, String s) {
            }
        });
    }

    //实现ConnectionListener接口
    private class MyConnectionListener implements EMConnectionListener {
        @Override
        public void onConnected() {
        }

        @Override
        public void onDisconnected(final int error) {
            FgAty.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (error == EMError.USER_REMOVED) {
                        // 显示帐号已经被移除
                        MyToast.show(CyApplication.getCyContext(), "显示帐号已经被移除");
                    } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                        // 显示帐号在其他设备登录
//                        MyToast.show(CyApplication.getCyContext(), "显示帐号在其他设备登录");
                    } else {
//                        if (NetUtils.hasNetwork(getActivity()))
                        //连接不到聊天服务器
//                            MyToast.show(CyApplication.getCyContext(),"连接不到聊天服务器");
//                        else
                        //当前网络不可用，请检查网络设置
//                            MyToast.show(CyApplication.getCyContext(),"当前网络不可用，请检查网络设置");
                    }
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initV();
    }
}
