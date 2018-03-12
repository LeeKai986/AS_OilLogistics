package com.zpf.oillogistics.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zpf.oillogistics.R;
import com.zpf.oillogistics.adapter.CompanyOrderAdapter;
import com.zpf.oillogistics.base.BaseActivity;
import com.zpf.oillogistics.base.MessageWhat;
import com.zpf.oillogistics.bean.response.CompanyOrderResponse;
import com.zpf.oillogistics.bean.response.DriverResponse;
import com.zpf.oillogistics.fragment.AllOrderCompanyFragment;
import com.zpf.oillogistics.net.SimplifyThread;
import com.zpf.oillogistics.net.UrlUtil;
import com.zpf.oillogistics.utils.MyShare;
import com.zpf.oillogistics.utils.MyToast;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/9/19.
 */

public class DriverMsgActivity extends BaseActivity {

    @BindView(R.id.rel_back_driver_infor)
    RelativeLayout relBack;
    @BindView(R.id.cir_head_driver_infor)
    CircleImageView cirHead;
    @BindView(R.id.tv_name_driver_infor)
    TextView tvName;
    @BindView(R.id.tv_atest_driver_infor)
    TextView tvAtest;
    @BindView(R.id.tv_carNum_driver_infor)
    TextView tvCarNum;

    private String driverId="";
    @Override
    protected int setLayout() {
        return R.layout.activity_driver_infor;
    }

    @Override
    protected void initData() {
        driverId=getIntent().getExtras().getString("driverId","");
        search();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }


    /**
     * 请求数据接口
     */
    private void search() {
        HashMap<String,String> hp=new HashMap<>();
        hp.put("id", driverId);

        SimplifyThread simplifyThread = new SimplifyThread(UrlUtil.URL_DRIVER_INFO, hp);
        simplifyThread.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                Message message = new Message();
                message.obj = res;
                message.what = MessageWhat.SELF_DRIVER_MSG;
                handler.sendMessage(message);
            }

            @Override
            public void errorBody(String error) {
                handler.sendEmptyMessage(MessageWhat.REQUST_ERROR);
            }
        });
    }

    private Gson gson = new Gson();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MessageWhat.SELF_DRIVER_MSG:
                    if (msg.obj != null) {
                        try {
                            DriverResponse order = gson.fromJson(msg.obj.toString(), DriverResponse.class);

                            if (order.getStatus() == 0) {
                                DriverResponse.DataBean bean=order.getData().get(0);

                                tvName.setText(bean.getRelname());
                                tvCarNum.setText(bean.getCartcode());

                                //加载头像
                                if(bean.getFace()!=null&&!bean.getFace().equals("")) {
                                    Glide.with(DriverMsgActivity.this)
                                            .load(UrlUtil.IMAGE_URL + bean.getFace())
                                            .into(cirHead);
                                }
                                //1已认证,2未认证,3未通过

//                                if("".equals("1")){
//                                    tvAtest.setText("已认证");
//                                }else if("".equals("2")){
//                                    tvAtest.setText("未认证");
//                                }else if("".equals("3")){
//                                    tvAtest.setText("未通过");
//                                }

                            } else {
                                MyToast.show(DriverMsgActivity.this, "暂无数据!");
                            }


                        } catch (Exception e) {
                            MyToast.show(DriverMsgActivity.this, "返回数据异常!");
                        }

                    } else {
                        MyToast.show(DriverMsgActivity.this, "返回数据失败!");
                    }
                    break;

            }
        }
    };

}
