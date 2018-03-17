package com.zpf.oillogistics.fragment;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zpf.oillogistics.R;
import com.zpf.oillogistics.activity.InforSelfActivity;
import com.zpf.oillogistics.activity.MsgHomeProductActivity;
import com.zpf.oillogistics.activity.MsgHomeQuoteActivity;
import com.zpf.oillogistics.activity.SelfAboutUsActivity;
import com.zpf.oillogistics.activity.SelfHelpActivity;
import com.zpf.oillogistics.activity.SelfPublicProductActivity;
import com.zpf.oillogistics.activity.SelfPublicShopActivity;
import com.zpf.oillogistics.activity.SettingActivity;
import com.zpf.oillogistics.base.CyApplication;
import com.zpf.oillogistics.customview.NavItem;
import com.zpf.oillogistics.net.UrlUtil;
import com.zpf.oillogistics.receiver.DetailsReceiver;
import com.zpf.oillogistics.utils.CallPermission;
import com.zpf.oillogistics.utils.MyIntent;
import com.zpf.oillogistics.utils.MyShare;
import com.zpf.oillogistics.utils.MyToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/9/13.
 */

public class SelfFragment extends Fragment implements View.OnClickListener, DetailsReceiver.ReceiveChangeListener {

    View view;
    @BindView(R.id.tv_phone_self)
    TextView tvPhone;
    @BindView(R.id.criv_head_self)
    CircleImageView crivHead;
    @BindView(R.id.tv_name_self)
    TextView tvName;
    @BindView(R.id.nav_product_self)
    NavItem navProduct;
    @BindView(R.id.nav_shop_self)
    NavItem navShop;
    @BindView(R.id.nav_product_mesg_self)
    NavItem navProductMesg;
    @BindView(R.id.nav_price_mesg_self)
    NavItem navPriceMesg;
    @BindView(R.id.nav_help_self)
    NavItem navHelp;
    @BindView(R.id.nav_about_us_self)
    NavItem navAboutUs;
    @BindView(R.id.nav_service_call_self)
    NavItem navServiceCall;
    @BindView(R.id.nav_setting_self)
    NavItem navSetting;
    @BindView(R.id.lin_mesg_self)
    LinearLayout linMesg;

    private Unbinder unbinder;
    private MyIntent intent;

    private String phone = "05303613333";

    private DetailsReceiver msgReceiver;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_self_main, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        intent = new MyIntent(getActivity());
        Log.d("AAAAAA", MyShare.getShared().getString("userHead", ""));
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

        linMesg.setOnClickListener(this);
        navProduct.setOnClickListener(this);
        navShop.setOnClickListener(this);
        navPriceMesg.setOnClickListener(this);
        navAboutUs.setOnClickListener(this);
        navHelp.setOnClickListener(this);
        navSetting.setOnClickListener(this);
        navServiceCall.setOnClickListener(this);
        navProductMesg.setOnClickListener(this);

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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lin_mesg_self:
                intent.startAct(InforSelfActivity.class);
                break;
            case R.id.nav_product_self:
                intent.startAct(SelfPublicProductActivity.class);
                break;
            case R.id.nav_shop_self:
//                Bundle bundle = new Bundle();
//                bundle.putString("type", "wantBuy");
                intent.startAct(SelfPublicShopActivity.class);
                break;
            case R.id.nav_product_mesg_self:
                intent.startAct(MsgHomeProductActivity.class);
                break;
            case R.id.nav_price_mesg_self:
                intent.startAct(MsgHomeQuoteActivity.class);
                break;
            case R.id.nav_help_self:
                intent.startAct(SelfHelpActivity.class);
                break;
            case R.id.nav_about_us_self:
                intent.startAct(SelfAboutUsActivity.class);
                break;
            case R.id.nav_service_call_self:
                if (phone != null && !phone.equals("")) {
                    //用intent启动拨打电话
//                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
//                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                        return;
//                    }
//                    getActivity().startActivity(intent);
                    CallPermission.callPhone(getActivity(), phone);
                } else {
                    MyToast.show(CyApplication.getCyContext(), "暂无联系电话");
                }
                break;
            case R.id.nav_setting_self:
                intent.startAct(SettingActivity.class);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
                } else {
                    tvName.setText("--");
                }
            }
        });
    }
}
