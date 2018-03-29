package com.zpf.oillogistics.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.hyphenate.util.DensityUtil;
import com.zpf.oillogistics.R;
import com.zpf.oillogistics.base.BaseActivity;
import com.zpf.oillogistics.bean.FirmDetailsWantBuyBean;
import com.zpf.oillogistics.diy.DiyDialog;
import com.zpf.oillogistics.net.JsonUtil;
import com.zpf.oillogistics.net.SimplifyThread;
import com.zpf.oillogistics.net.UrlUtil;
import com.zpf.oillogistics.utils.CallPermission;
import com.zpf.oillogistics.utils.DateTimeUtil;
import com.zpf.oillogistics.utils.MyToast;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/15.
 * <p>
 * 求购详情
 */

public class FirmDetailsActivity extends BaseActivity {

    // 布局相关
    @BindView(R.id.rel_back)
    RelativeLayout relBack;
    // 商品名
    @BindView(R.id.firm_details_product_name_tv)
    TextView productNameTv;
    // 供求关系
    @BindView(R.id.firm_details_relation_tv)
    TextView relationTv;
    // 单价
    @BindView(R.id.firm_details_unit_price_tv)
    TextView unitPriceTv;
    // 发单时间
    @BindView(R.id.firm_details_c_time_tv)
    TextView cTimeTv;
    // 公司名称
    @BindView(R.id.firm_details_company_name_tv)
    TextView companyNameTv;
    // 公司名称
    @BindView(R.id.tv_company)
    TextView tvCompany;
    // 取货点
    @BindView(R.id.firm_details_get_product_diatance_tv)
    TextView getProductDiatamceTv;
    // 联系电话
    @BindView(R.id.firm_details_company_phone_tv)
    TextView companyPhoneTv;
    // 电话联系公司
    @BindView(R.id.firm_details_call_company_phone_iv)
    ImageView callCompanyIv;
    // 详细描述
    @BindView(R.id.firm_details_content_tv)
    TextView contentTv;
    // 图示
    @BindView(R.id.firm_details_image_iv)
    ImageView imageIv;
//    // 图示外框
//    @BindView(R.id.firm_details_image_ll)
//    LinearLayout imageLl;

    // 数据相关
    // 企业详情
    SimplifyThread firmDetailsThread;
    // 企业详情传参
    HashMap<String, String> firmDetailsMap;
    // 企业详情返回
    FirmDetailsWantBuyBean firmDetailsWantBuyBean;

    String inforId = "";

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    MyToast.show(FirmDetailsActivity.this, message.obj.toString());
                    break;
                case 1:
                    if (firmDetailsWantBuyBean != null) {
                        if (firmDetailsWantBuyBean.getData() != null) {
                            setData();
                        } else {
                            MyToast.show(FirmDetailsActivity.this, firmDetailsWantBuyBean.getMsg());
                        }
                    } else {
                        MyToast.show(FirmDetailsActivity.this, "未能获取数据");
                    }
                    break;
                case 2:
                    break;
            }
            return false;
        }
    });


    @Override
    protected int setLayout() {
        return R.layout.activity_firm_details;
    }

    @Override
    protected void initData() {

        inforId = getIntent().getExtras().getString("inforId");
        firmDetails();
    }

    @Override
    protected void initViewAction(Bundle savedInstanceState) {
        relBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setData() {
        final FirmDetailsWantBuyBean.DataBeanX.DataBean data = firmDetailsWantBuyBean.getData().getData();
        final FirmDetailsWantBuyBean.DataBeanX.InfoBean info = firmDetailsWantBuyBean.getData().getInfo();

        if (info != null && info.getTitle() != null) {
            productNameTv.setText(info.getTitle());
        }
//        if (getIntent().getStringExtra("").equals("")) {
//            relationTv.setText("求购价格");
//            relationTv.setBackgroundResource(R.drawable.bg_circle_blue_platform);
//        } else {
//            relationTv.setText("供应价格");
//            relationTv.setBackgroundResource(R.drawable.bg_circle_red_platform);
//        }
        if (info != null && info.getPrice() != null) {
            unitPriceTv.setText("价格：" + info.getPrice() + "元/吨");
        }
        if (info != null && info.getTime() != 0) {
            cTimeTv.setText(DateTimeUtil.stampToDate("yyyy,MM/dd HH:mm:ss", info.getTime() + "000"));
        }

        if (info.getIs_user() != 2) {
            tvCompany.setText("联 系 人");
        }

        if (info != null && info.getName() != null) {
            companyNameTv.setText(info.getName());
        }

        if (info != null && info.getAddress() != null) {
            getProductDiatamceTv.setText(info.getAddress());
        }
        if (info != null && info.getPhone() != null) {
            companyPhoneTv.setText(info.getPhone());
            callCompanyIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DiyDialog.hintTweBtnDialog(FirmDetailsActivity.this, "是否拨打电话?", new DiyDialog.HintTweBtnListener() {
                        @Override
                        public void confirm() {
//                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + info.getPhone()));
//                            startActivity(intent);
                            CallPermission.callPhone(FirmDetailsActivity.this, info.getPhone());
                        }
                    });
                }
            });
        }
        if (info != null && info.getIntruduce() != null) {
            contentTv.setText(info.getIntruduce());
        }
        if (info != null && info.getIntruduce() != null) {
            Glide.with(FirmDetailsActivity.this)
                    .load(UrlUtil.IMAGE_URL + info.getImg())
                    .placeholder(R.mipmap.default_goodsdetails)
                    .error(R.mipmap.default_goodsdetails)
                    .fitCenter()
                    .into(new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            imageIv.setImageDrawable(resource);

                        }
                    });
            int width;
            WindowManager wm = (WindowManager) FirmDetailsActivity.this.getSystemService(Context.WINDOW_SERVICE);
            width = wm.getDefaultDisplay().getWidth();
            ViewGroup.LayoutParams layoutParams = imageIv.getLayoutParams();
            layoutParams.width = width;
            layoutParams.height = width - DensityUtil.dip2px(FirmDetailsActivity.this, 40);
            Log.i("Pwinth-->", width + "");
            imageIv.setLayoutParams(layoutParams);
        }
    }

    private void firmDetails() {
        firmDetailsMap = new HashMap<>();
        firmDetailsMap.put("id", inforId);
        firmDetailsThread = new SimplifyThread(UrlUtil.TOBUY_TOBUYINFO, firmDetailsMap);
        firmDetailsThread.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                firmDetailsWantBuyBean = JsonUtil.firmDetailsWantBuyBeanResolve(res);
                handler.sendEmptyMessage(1);
            }

            @Override
            public void errorBody(String error) {
                Message msg = new Message();
                msg.what = 0;
                msg.obj = error;
                handler.sendMessage(msg);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
