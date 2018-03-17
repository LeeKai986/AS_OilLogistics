package com.zpf.oillogistics.fragment;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zpf.oillogistics.R;
import com.zpf.oillogistics.activity.DirverPersonMsgActivity;
import com.zpf.oillogistics.activity.DriverIdenConfirmActivity;
import com.zpf.oillogistics.activity.SelfAboutUsActivity;
import com.zpf.oillogistics.activity.SelfDriverOneKeyIssueOrderActivity;
import com.zpf.oillogistics.activity.SelfDriverOrderActivity;
import com.zpf.oillogistics.activity.SelfDriverRouteActivity;
import com.zpf.oillogistics.activity.SelfHelpActivity;
import com.zpf.oillogistics.activity.SettingActivity;
import com.zpf.oillogistics.base.CyApplication;
import com.zpf.oillogistics.customview.NavItem;
import com.zpf.oillogistics.net.UrlUtil;
import com.zpf.oillogistics.receiver.DetailsReceiver;
import com.zpf.oillogistics.utils.CallPermission;
import com.zpf.oillogistics.utils.MyShare;
import com.zpf.oillogistics.utils.MyToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/9/18.
 * <p>
 * 司机个人页面
 */

public class SelfDriverFragment extends Fragment implements DetailsReceiver.ReceiveChangeListener {
    //联系电话
    @BindView(R.id.tv_phone_self)
    TextView tvPhone;
    //头像
    @BindView(R.id.criv_head_self)
    CircleImageView crivHead;
    //名字
    @BindView(R.id.tv_name_self)
    TextView tvName;
    //个人信息
    @BindView(R.id.lin_mesg_self)
    LinearLayout linMesg;
    //身份验证
    @BindView(R.id.nav_driver_iden_self)
    NavItem navDriverIden;
    //我的订单
    @BindView(R.id.nav_order_self)
    NavItem navOrder;
    //我的行程
    @BindView(R.id.nav_route_self)
    NavItem navRoute;
    //一键下单
    @BindView(R.id.nav_creOrders_self)
    NavItem navCreOrder;
    //帮助
    @BindView(R.id.nav_help_self)
    NavItem navHelp;
    //关于我们
    @BindView(R.id.nav_about_us_self)
    NavItem navAboutUs;
    @BindView(R.id.nav_service_call_self)
    NavItem navServiceCall;
    //设置
    @BindView(R.id.nav_setting_self)
    NavItem navSetting;

    // 客服电话
    String phone = "05303613333";


    //信息修改反馈Receiver
    private DetailsReceiver msgReceiver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_self_driver, container, false);
        ButterKnife.bind(this, view);
        intiViewAction(view, savedInstanceState);
        intiData();
        return view;
    }

    protected void intiData() {

        //加载头像
        if (!MyShare.getShared().getString("userHead", "").equals("")) {
            Glide.with(getActivity())
                    .load(UrlUtil.IMAGE_URL + MyShare.getShared().getString("userHead", ""))
                    .error(R.mipmap.head_default)
                    .into(crivHead);
        }

        //加载昵称
        if (!MyShare.getShared().getString("relname", "").equals("")) {
            tvName.setText(MyShare.getShared().getString("relname", ""));
        } else {
            tvName.setText("--");
        }

        //动态注册广播接收器
        msgReceiver = new DetailsReceiver();
        msgReceiver.setReceiveChangeListener(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.zpf.oillogistics.receiver.DetailsReceiver");
        getActivity().registerReceiver(msgReceiver, intentFilter);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(msgReceiver);
    }

    protected void intiViewAction(View view, Bundle savedInstanceState) {
        navServiceCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phone != null && !phone.equals("")) {
                    //用intent启动拨打电话
//                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
//                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                        return;
//                    }
//                    getActivity().startActivity(intent);
                    CallPermission.callPhone(getActivity(), phone);
                } else {
                    MyToast.show(CyApplication.getCyContext(), "暂无联系电话");
                }
            }
        });
        linMesg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), DirverPersonMsgActivity.class));
            }
        });
        navDriverIden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), DriverIdenConfirmActivity.class));
            }
        });
        navOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SelfDriverOrderActivity.class));
            }
        });
        navRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SelfDriverRouteActivity.class));
            }
        });
        navCreOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SelfDriverOneKeyIssueOrderActivity.class));
            }
        });
        navHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SelfHelpActivity.class));
            }
        });

        navAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SelfAboutUsActivity.class));
            }
        });

        navSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SettingActivity.class));
            }
        });
        navAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SelfAboutUsActivity.class));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onChange() {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //加载头像
                if (!MyShare.getShared().getString("userHead", "").equals("")) {
                    Glide.with(getActivity())
                            .load(UrlUtil.IMAGE_URL + MyShare.getShared().getString("userHead", ""))
                            .error(R.mipmap.head_default)
                            .into(crivHead);
                }
                //加载昵称
                if (!MyShare.getShared().getString("relname", "").equals("")) {
                    tvName.setText(MyShare.getShared().getString("relname", ""));
                }
            }
        });

    }

}
