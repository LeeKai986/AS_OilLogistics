package com.zpf.oillogistics.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.db.InviteMessgeDao;
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
import com.zpf.oillogistics.utils.NormalUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
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
    LinearLayout mainContent;
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

    private FragmentManager fragmentManager;
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
                            } else {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Thread.sleep(10000);
                                            login();
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();
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

        fragmentManager = getSupportFragmentManager();

        setTabSelection(0);

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

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(10000);
//                    login();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
        login();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(MainActivity.this, DriverPositionUpData.class));
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
        // clearSelection();
        // 开启一个Fragment事务

        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        hideFragments(transaction);
        reset();
        switch (index) {
            case 0:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    transaction.replace(R.id.main_content, homeFragment);
                } else {
                    transaction.replace(R.id.main_content, homeFragment);
//                    homeFragment.onResume();
                }
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
                    transaction.replace(R.id.main_content, priceFragment);
                } else {
                    transaction.replace(R.id.main_content, priceFragment);
                }
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
                    transaction.replace(R.id.main_content, chatFragment);
                } else {
                    transaction.replace(R.id.main_content, chatFragment);
                }
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
                        transaction.replace(R.id.main_content, selfFragment);
                    } else {
                        transaction.replace(R.id.main_content, selfFragment);
                    }
                } else if (selfFlag.equals("2")) {
                    if (selfCompanyFragment == null) {
                        selfCompanyFragment = new SelfCompanyFragment();
                        transaction.replace(R.id.main_content, selfCompanyFragment);
                    } else {
                        transaction.replace(R.id.main_content, selfCompanyFragment);
                    }
                } else {
                    if (selfDriverFragment == null) {
                        selfDriverFragment = new SelfDriverFragment();
                        transaction.replace(R.id.main_content, selfDriverFragment);
                    } else {
                        transaction.replace(R.id.main_content, selfDriverFragment);
                    }
                }
                ivSelfBoom.setBackgroundResource(R.mipmap.self_on);
                break;
            default:
                break;
        }
        transaction.commit();
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
