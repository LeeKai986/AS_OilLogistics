package com.zpf.oillogistics.receiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.dream.life.library.throwable.utils.AppManagerUtil;
import com.dream.life.library.throwable.utils.CheckUtil;
import com.dream.life.library.throwable.utils.LogUtil;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.db.InviteMessgeDao;
import com.zpf.oillogistics.activity.LoginActivity;
import com.zpf.oillogistics.base.BaseActivity;
import com.zpf.oillogistics.base.CyApplication;
import com.zpf.oillogistics.utils.MyShare;

/**
 * 作者：夏瑞
 * 2017/9/5 11:18
 * 注释:
 * 邮箱:1970258244@qq.com
 */

public class LoginExitManager {

    private Handler handler;
    private static final String ACTION = "com.zpf.oillogistics.action.";
    private static final String ACTION_LOGOUT = ACTION + "login";//退出登陆

    private LoginExitManager() {
        handler = new Handler(Looper.getMainLooper());
    }

    private static final class ReceiverManagerHolder {
        public static LoginExitManager instance = new LoginExitManager();
    }

    public static LoginExitManager getInstance() {
        return ReceiverManagerHolder.instance;
    }

    /**
     * 退出登陆
     */
    public void logout(final Activity activity, final String title, final String content) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                AlertView conflictBuilder = new AlertView("", content, "确定", null, null, activity,
                        AlertView.Style.Alert, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        EMClient.getInstance().logout(true);
                        new InviteMessgeDao(activity).clearMessage();
                        SharedPreferences.Editor editor = MyShare.getShared().edit();
                        editor.clear();
                        editor.commit();
                        CyApplication.province = "";
                        CyApplication.area = "";
                        CyApplication.adress = "";
                        CyApplication.lat = "";
                        CyApplication.lon = "";
                        Intent intent = new Intent(activity, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.startActivity(intent);
                    }
                });
                conflictBuilder.show();
            }
        });
    }

    /**
     * 接收退出当前账号广播
     */
    public static class LoginReceiver extends BroadcastReceiver {
        private static final String KEY_TITLE = "title";
        private static final String KEY_CONTENT = "content";
        private String title;
        private String content;//内容
        @Override
        public void onReceive(final Context context, Intent intent) {
            if (intent.getAction().equals(ACTION_LOGOUT)) {
                title = intent.getStringExtra(KEY_TITLE);
                content = intent.getStringExtra(KEY_CONTENT);
                title = CheckUtil.isNull(title) ? "提示" : title;
                content = CheckUtil.isNull(content) ? "退出当前账号" : content;

                Activity activity = AppManagerUtil.getInstance().getPopActivity();
                if (activity != null) {
                    if (activity instanceof BaseActivity) {
                        BaseActivity baseActivity = (BaseActivity) activity;
                        LoginExitManager.getInstance().logout(baseActivity, title, content);
                    }
                }

            }
        }
    }
}
