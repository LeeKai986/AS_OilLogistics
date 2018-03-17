package com.zpf.oillogistics.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.DemoHelper;
import com.hyphenate.easeui.db.InviteMessgeDao;
import com.hyphenate.util.NetUtils;
import com.zpf.oillogistics.R;
import com.zpf.oillogistics.base.BaseActivity;
import com.zpf.oillogistics.base.CyApplication;
import com.zpf.oillogistics.diy.DiyDialog;
import com.zpf.oillogistics.fragment.ChatFragment;
import com.zpf.oillogistics.fragment.HomeFragment;
import com.zpf.oillogistics.fragment.PriceFragment;
import com.zpf.oillogistics.fragment.SelfCompanyFragment;
import com.zpf.oillogistics.fragment.SelfDriverFragment;
import com.zpf.oillogistics.fragment.SelfFragment;
import com.zpf.oillogistics.net.SimplifyThread;
import com.zpf.oillogistics.net.UrlUtil;
import com.zpf.oillogistics.sv.DriverPositionUpData;
import com.zpf.oillogistics.utils.MyShare;
import com.zpf.oillogistics.utils.MyToast;
import com.zpf.oillogistics.utils.NormalUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2017/9/13.
 * <p>
 * 主activity
 */

public class MainActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.main_content)
    FrameLayout mainContent;
    @BindView(R.id.lin_jia_main)
    LinearLayout linJia;
    @BindView(R.id.iv_home_boom_main)
    ImageView ivHomeBoom;
    @BindView(R.id.rel_home_boom_main)
    RelativeLayout relHomeBoom;
    @BindView(R.id.iv_price_boom_main)
    ImageView ivPriceBoom;
    @BindView(R.id.rel_price_boom_main)
    RelativeLayout relPriceBoom;
    @BindView(R.id.rel_jia_boom_main)
    RelativeLayout relJiaBoom;
    @BindView(R.id.iv_chat_boom_main)
    ImageView ivChatBoom;
    @BindView(R.id.rel_chat_boom_main)
    RelativeLayout relChatBoom;
    @BindView(R.id.iv_self_boom_main)
    ImageView ivSelfBoom;
    @BindView(R.id.rel_self_boom_main)
    RelativeLayout relSelfBoom;

    // 发产品
    @BindView(R.id.lin_jia_product_main)
    LinearLayout productLl;
    // 发求购
    @BindView(R.id.lin_jia_buy_main)
    LinearLayout buyLl;
    // 发货运
    @BindView(R.id.lin_jia_incar_main)
    LinearLayout incarLl;
    @BindView(R.id.iv_jia_boom_main)
    ImageView ivJiaBoom;
    @BindView(R.id.view1)
    View view1;

    /*首页fragment*/
    private HomeFragment homeFragment;
    /*报价fragment*/
    private PriceFragment priceFragment;
    /*聊天fragment*/
    private ChatFragment chatFragment;
    /*我的fragment*/
    private SelfFragment selfFragment;
    /*我的fragment*/
    private SelfCompanyFragment selfCompanyFragment;
    /*我的fragmenty*/
    private SelfDriverFragment selfDriverFragment;

    private String selfFlag = "1";
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    break;
                case 1:
                    JSONObject jo = null;
                    try {
                        jo = new JSONObject(message.obj.toString());
                        if (jo.getInt("status") == 0) {
                            JSONObject data = jo.getJSONObject("data");
                            if (data.getString("statuss").equals("5")) {
//                                MyToast.show(MainActivity.this, "您的账号处于禁用状态，请更换账号重新登录");
                                DiyDialog.hintOneBtnDialog(MainActivity.this, "您的账号处于禁用状态，请更换账号重新登录", new DiyDialog.HintTweBtnListener() {
                                    @Override
                                    public void confirm() {
                                        EMClient.getInstance().logout(true);
                                        new InviteMessgeDao(MainActivity.this).clearMessage();
                                        SharedPreferences.Editor editor = MyShare.getShared().edit();
                                        editor.clear();
                                        editor.commit();
                                        CyApplication.province = "";
                                        CyApplication.area = "";
                                        CyApplication.adress = "";
                                        CyApplication.lat = "";
                                        CyApplication.lon = "";
                                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    }
                                });
                                break;
                            } else {//登陆成功
                                SharedPreferences sp = getSharedPreferences("SHARE_OIL_USER", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("userId", data.getString("id"));
                                editor.putString("userPhone", data.getString("phone"));
                                editor.putString("niname", data.getString("niname"));
                                editor.putString("userHead", data.getString("face"));
                                editor.putString("add_time", data.getString("add_time"));
                                editor.putString("userType", data.getString("status"));//1个人,2企业,3司机
                                editor.putString("province", data.getString("province"));
                                editor.putString("city", data.getString("city"));
                                if (!data.getString("area").equals("null")) {
                                    editor.putString("area", data.getString("area"));
                                }
                                editor.putString("userToken", data.getString("logintoken"));
                                editor.putString("searchlog", data.getString("searchlog"));
                                editor.putString("logintime", data.getString("logintime"));
                                editor.putString("relname", data.getString("relname"));
                                editor.putString("card", data.getString("card"));
                                editor.putString("telphone", data.getString("telphone"));
                                editor.putString("manage", data.getInt("manage") + "");
                                editor.putString("toaddress", data.getString("toaddress"));
                                if (!data.getString("suggest").equals("null")) {
                                    editor.putString("suggest", data.getString("suggest"));
                                }
                                editor.putString("companyname", data.getString("companyname"));
                                editor.putString("enterprise", data.getString("enterprise"));
                                editor.putString("license", data.getString("license"));
                                editor.putString("place", data.getString("place"));
                                editor.putString("adds_time", data.getString("adds_time"));
                                editor.putString("img", data.getString("img"));
                                editor.putString("cartcode", data.getString("cartcode"));
                                editor.putString("car_type", data.getString("car_type"));
                                editor.putString("load", data.getString("load"));
                                editor.putString("driverpic", data.getString("driverpic"));
                                editor.putString("runpic", data.getString("runpic"));
                                editor.putString("operatepic", data.getString("operatepic"));
                                editor.putString("supercargopic", data.getString("supercargopic"));
                                editor.putString("myorder", data.getString("myorder"));
                                editor.putString("stroke", data.getString("stroke"));
                                if (!data.getString("longitude").equals("null")) {
                                    editor.putString("longitude", data.getString("longitude"));
                                }
                                if (!data.getString("latitude").equals("null")) {
                                    editor.putString("latitude", data.getString("latitude"));
                                }
                                editor.putString("statuss", data.getString("statuss"));
                                if (!data.getString("route").equals("null")) {
                                    editor.putString("route", data.getString("route"));
                                }
                                editor.commit();

                                EMClient.getInstance().login(MyShare.getShared().getString("userPhone", ""), "yyt123456", new EMCallBack() {//回调
                                    @Override
                                    public void onSuccess() {
                                        runOnUiThread(new Runnable() {
                                            public void run() {
                                                EMClient.getInstance().groupManager().loadAllGroups();
                                                EMClient.getInstance().chatManager().loadAllConversations();
                                                EMClient.getInstance().chatManager().addMessageListener(mMessageListener);
                                                //注册一个监听连接状态的listener
                                                EMClient.getInstance().addConnectionListener(new MyConnectionListener());
                                            }
                                        });
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
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
            return false;
        }
    });
    private Fragment currentFragment;
    private Fragment newFragment;

    //实现ConnectionListener接口
    private class MyConnectionListener implements EMConnectionListener {
        @Override
        public void onConnected() {
        }

        @Override
        public void onDisconnected(final int error) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (error == EMError.USER_REMOVED) {
                        // 显示帐号已经被移除
                        MyToast.show(CyApplication.getCyContext(), "显示帐号已经被移除");
                    } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                        // 显示帐号在其他设备登录
                        MyToast.show(CyApplication.getCyContext(), "显示帐号在其他设备登录");
                    } else {
                        if (NetUtils.hasNetwork(MainActivity.this))
//                            连接不到聊天服务器
                            MyToast.show(CyApplication.getCyContext(), "连接不到聊天服务器");
                        else
//                        当前网络不可用，请检查网络设置
                            MyToast.show(CyApplication.getCyContext(), "当前网络不可用，请检查网络设置");
                    }
                }
            });
        }
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
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                }
            });
        }
    };

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        initView();
    }

    @Override
    protected void initViewAction(Bundle savedInstanceState) {


    }


    protected void initView() {
        // 当登陆者为司机时启动上传坐标服务
        if (!MyShare.getShared().getString("userType", "").equals("") &&
                MyShare.getShared().getString("userType", "").equals("3")) {
            Log.i("DriverPosition-->", "open");
            startService(new Intent(MainActivity.this, DriverPositionUpData.class));
        }

        JPushInterface.setAlias(MainActivity.this, 11, NormalUtils.userPhone());


        selfFlag = MyShare.getShared().getString("userType", "1");

        relHomeBoom.setOnClickListener(this);
        relChatBoom.setOnClickListener(this);
        relJiaBoom.setOnClickListener(this);
        relPriceBoom.setOnClickListener(this);
        relSelfBoom.setOnClickListener(this);

        productLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, IssueProductActivity.class));
                ivJiaBoom.setBackgroundResource(R.mipmap.home_jia);
                linJia.setVisibility(View.GONE);
            }
        });

        buyLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, IssueBuyActivity.class));
                ivJiaBoom.setBackgroundResource(R.mipmap.home_jia);
                linJia.setVisibility(View.GONE);
            }
        });

        incarLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, IssueIncarActivity.class));
                ivJiaBoom.setBackgroundResource(R.mipmap.home_jia);
                linJia.setVisibility(View.GONE);
            }
        });
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivJiaBoom.setBackgroundResource(R.mipmap.home_jia);
                linJia.setVisibility(View.GONE);
            }
        });
        setFragmentData();
        if (NormalUtils.personDataPass(MainActivity.this)) {//已登录
            login();
        }
    }

    //初始化 5个主页面
    private void setFragmentData() {
        homeFragment = new HomeFragment();
        currentFragment = homeFragment;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_content, homeFragment).commitAllowingStateLoss();
        setTabSelection(0);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(MainActivity.this, DriverPositionUpData.class));
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rel_home_boom_main:
                setTabSelection(0);
                break;
            case R.id.rel_price_boom_main:
                setTabSelection(1);
                break;
            case R.id.rel_jia_boom_main:

                if (!NormalUtils.personDataPass(MainActivity.this)) {
//                    MyToast.show(MainActivity.this, "请先验证身份");
//                    NormalUtils.personDataVerify(MainActivity.this);
                    return;
                }

                if (!selfFlag.equals("3")) {
                    if (linJia.getVisibility() == View.GONE) {
                        ivJiaBoom.setBackgroundResource(R.mipmap.x_red);
                        view1.setVisibility(View.VISIBLE);
                        linJia.setVisibility(View.VISIBLE);
                    } else {
                        ivJiaBoom.setBackgroundResource(R.mipmap.home_jia);
                        view1.setVisibility(View.GONE);
                        linJia.setVisibility(View.GONE);
                    }
                } else {
                    startActivity(new Intent(MainActivity.this, IssueRouteActivity.class));
                }
                break;
            case R.id.rel_chat_boom_main:
                setTabSelection(2);
                break;
            case R.id.rel_self_boom_main:
                setTabSelection(3);
                break;

        }
    }

    // 是否被禁用判断
    private void login() {
        if (MyShare.getShared().getString("userPhone", "").equals("")) {
            return;
        }
        if (MyShare.getShared().getString("password", "").equals("")) {
            return;
        }
        HashMap<String, String> loginMap = new HashMap<>();
        loginMap.put("phone", MyShare.getShared().getString("userPhone", ""));
        loginMap.put("password", MyShare.getShared().getString("password", ""));
        SimplifyThread loginThread = new SimplifyThread(UrlUtil.LOGIN_LOGIN, loginMap);
        loginThread.setOnNoKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(final String res) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message msg = new Message();
                        msg.what = 1;
                        msg.obj = res;
                        handler.sendMessage(msg);
                    }
                }).start();

            }

            @Override
            public void errorBody(final String error) {


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message msg = new Message();
                        msg.what = 0;
                        msg.obj = error;
                        handler.sendMessage(msg);
                    }
                }).start();

            }
        });
    }


    /**
     * 根据传入的index参数来设置选中的tab页。
     *
     * @param index 每个tab页对应的下标。
     *              0-首页,1-报价,2-聊天,3-我的
     */
    public void setTabSelection(int index) {
        // 每次选中之前先清楚掉上次的选中状态
        reset();
        switch (index) {
            case 0:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                }
                newFragment = homeFragment;
                replaceFragment();
                ivHomeBoom.setBackgroundResource(R.mipmap.main_on);
                break;
            case 1:
                if (!NormalUtils.personDataPass(MainActivity.this)) {
//                    MyToast.show(MainActivity.this, "请先验证身份");
//                    NormalUtils.personDataVerify(MainActivity.this);
                    return;
                }
                if (priceFragment == null) {
                    priceFragment = new PriceFragment();
                }
                newFragment = priceFragment;
                replaceFragment();
                ivPriceBoom.setBackgroundResource(R.mipmap.price_on);
                break;
            case 2:
                if (!NormalUtils.personDataPass(MainActivity.this)) {
//                    MyToast.show(MainActivity.this, "请先验证身份");
//                    NormalUtils.personDataVerify(MainActivity.this);
                    return;
                }
                if (chatFragment == null) {
                    chatFragment = new ChatFragment();
                }
                newFragment = chatFragment;
                replaceFragment();
                ivChatBoom.setBackgroundResource(R.mipmap.char_on);
                break;
            case 3:
                if (!NormalUtils.personDataPass(MainActivity.this)) {
//                    MyToast.show(MainActivity.this, "请先验证身份");
//                    NormalUtils.personDataVerify(MainActivity.this);
                    return;
                }
                if (selfFlag.equals("1")) {
                    if (selfFragment == null) {
                        selfFragment = new SelfFragment();
                    }
                    newFragment = selfFragment;
                } else if (selfFlag.equals("2")) {
                    if (selfCompanyFragment == null) {
                        selfCompanyFragment = new SelfCompanyFragment();
                    }
                    newFragment = selfCompanyFragment;
                } else {
                    if (selfDriverFragment == null) {
                        selfDriverFragment = new SelfDriverFragment();
                    }
                    newFragment = selfDriverFragment;
                }
                replaceFragment();
                ivSelfBoom.setBackgroundResource(R.mipmap.self_on);
                break;
            default:
                break;
        }
    }

    private void replaceFragment() {
        if (newFragment != currentFragment) {
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction();
            if (newFragment.isAdded()) {
//                newFragment.onResume();
                transaction.show(newFragment);
            } else {
                transaction.add(R.id.main_content, newFragment);
            }
            transaction.hide(currentFragment).commitAllowingStateLoss();
            currentFragment = newFragment;
        }
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (priceFragment != null) {
            transaction.hide(priceFragment);
        }
        if (chatFragment != null) {
            transaction.hide(chatFragment);
        }
        if (selfFragment != null) {
            transaction.hide(selfFragment);
        }
        if (selfCompanyFragment != null) {
            transaction.hide(selfCompanyFragment);
        }
    }

    /**
     * 重置下标
     */
    private void reset() {
        linJia.setVisibility(View.GONE);
        ivJiaBoom.setBackgroundResource(R.mipmap.home_jia);
        ivHomeBoom.setBackgroundResource(R.mipmap.main_off);
        ivPriceBoom.setBackgroundResource(R.mipmap.price_off);
        ivChatBoom.setBackgroundResource(R.mipmap.chat_off);
        ivSelfBoom.setBackgroundResource(R.mipmap.self_off);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click();      //调用双击退出函数
        }
        return false;
    }

    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
//            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            finish();
            System.exit(0);
        }
    }

}
