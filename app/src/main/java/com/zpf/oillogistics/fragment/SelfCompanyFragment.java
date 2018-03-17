package com.zpf.oillogistics.fragment;

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
import com.zpf.oillogistics.activity.InforSelfCompanyActivity;
import com.zpf.oillogistics.activity.MsgHomeProductActivity;
import com.zpf.oillogistics.activity.MsgHomeQuoteActivity;
import com.zpf.oillogistics.activity.SelfAboutUsActivity;
import com.zpf.oillogistics.activity.SelfCompanyOrderActivity;
import com.zpf.oillogistics.activity.SelfHelpActivity;
import com.zpf.oillogistics.activity.SelfPublicProductActivity;
import com.zpf.oillogistics.activity.SelfPublicResorceActivity;
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
 * 公司个人中心
 */

public class SelfCompanyFragment extends Fragment implements View.OnClickListener, DetailsReceiver.ReceiveChangeListener {

    View view;
    MyIntent intent;
    @BindView(R.id.tv_phone_self_company)
    TextView tvPhone;
    @BindView(R.id.criv_head_self_company)
    CircleImageView crivHead;
    @BindView(R.id.tv_name_self_company)
    TextView tvName;
    @BindView(R.id.lin_mesg_self_company)
    LinearLayout linMesg;
    @BindView(R.id.nav_product_self_company)
    NavItem navProductMesg;
    @BindView(R.id.nav_shop_self_company)
    NavItem navShop;
    @BindView(R.id.nav_resource_mesg_self_company)
    NavItem navResource;
    @BindView(R.id.nav_product_mesg_self_company)
    NavItem navProduct;
    @BindView(R.id.nav_price_mesg_self_company)
    NavItem navPriceMesg;
    @BindView(R.id.nav_order_self_company)
    NavItem navOrder;
    @BindView(R.id.nav_help_self_company)
    NavItem navHelp;
    @BindView(R.id.nav_about_us_self_company)
    NavItem navAboutUs;
    @BindView(R.id.nav_service_call_self_company)
    NavItem navServiceCall;
    @BindView(R.id.nav_setting_self_company)
    NavItem navSetting;

    Unbinder unbinder;
    private String phone = "05303613333";
    private DetailsReceiver msgReceiver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_self_company_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();

        return view;
    }

    private void initView() {
        intent = new MyIntent(getActivity());

        linMesg.setOnClickListener(this);
        navProduct.setOnClickListener(this);
        navShop.setOnClickListener(this);
        navPriceMesg.setOnClickListener(this);
        navAboutUs.setOnClickListener(this);
        navHelp.setOnClickListener(this);
        navSetting.setOnClickListener(this);
        navServiceCall.setOnClickListener(this);
        navProductMesg.setOnClickListener(this);
        navOrder.setOnClickListener(this);
        navResource.setOnClickListener(this);

        //加载头像
        if (!MyShare.getShared().getString("userHead", "").equals("")) {
            Glide.with(getActivity())
                    .load(UrlUtil.IMAGE_URL + MyShare.getShared().getString("userHead", ""))
                    .error(R.mipmap.head_default)
                    .into(crivHead);
        }

        //加载昵称
        if (!MyShare.getShared().getString("companyname", "").equals("")) {
            tvName.setText(MyShare.getShared().getString("companyname", ""));
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lin_mesg_self_company:
                intent.startAct(InforSelfCompanyActivity.class);
                break;
            case R.id.nav_product_self_company:
                intent.startAct(SelfPublicProductActivity.class);
                break;
            case R.id.nav_shop_self_company:
                intent.startAct(SelfPublicShopActivity.class);
                break;
            case R.id.nav_product_mesg_self_company:
                Bundle bundle = new Bundle();
                bundle.putString("isMsg", "2");
                intent.startAct(MsgHomeProductActivity.class, bundle);
                break;
            case R.id.nav_price_mesg_self_company:
                intent.startAct(MsgHomeQuoteActivity.class);
                break;
            case R.id.nav_resource_mesg_self_company:
                intent.startAct(SelfPublicResorceActivity.class);
                break;
            case R.id.nav_order_self_company:
                intent.startAct(SelfCompanyOrderActivity.class);
                break;
            case R.id.nav_help_self_company:
                intent.startAct(SelfHelpActivity.class);
                break;
            case R.id.nav_about_us_self_company:
                intent.startAct(SelfAboutUsActivity.class);
                break;
            case R.id.nav_service_call_self_company:
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
            case R.id.nav_setting_self_company:
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
                if (!MyShare.getShared().getString("companyname", "").equals("")) {
                    tvName.setText(MyShare.getShared().getString("companyname", ""));
                } else {
                    tvName.setText("--");
                }
            }
        });
    }
}
