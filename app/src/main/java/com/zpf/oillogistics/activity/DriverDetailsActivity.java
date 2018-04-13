package com.zpf.oillogistics.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zpf.oillogistics.R;
import com.zpf.oillogistics.base.BaseActivity;
import com.zpf.oillogistics.bean.DriverDetailsBean;
import com.zpf.oillogistics.net.JsonUtil;
import com.zpf.oillogistics.net.SimplifyThread;
import com.zpf.oillogistics.net.UrlUtil;
import com.zpf.oillogistics.utils.CallPermission;
import com.zpf.oillogistics.utils.DateTimeUtil;
import com.zpf.oillogistics.utils.MyToast;

import java.util.HashMap;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/9/18.
 * <p>
 * 司机详情
 */

public class DriverDetailsActivity extends BaseActivity {

    // 布局相关
    // lv
    @BindView(R.id.rel_back)
    RelativeLayout relBack;
    //    // lv
//    @BindView(R.id.driver_details_mormal_route_lv)
//    ListView normalRouteLv;
    // 头像
    @BindView(R.id.driver_details_header_iv)
    ImageView headerIv;
    // 姓名
    @BindView(R.id.driver_details_name_tv)
    TextView nameTv;
    // 车牌号
    @BindView(R.id.driver_details_plate_tv)
    TextView plateTv;
    // 类型
    @BindView(R.id.driver_details_car_type_tv)
    TextView carTypeTv;
    // 载重
    @BindView(R.id.driver_details_load_tv)
    TextView loadTv;
    // 常跑路线
    @BindView(R.id.driver_person_msg_one_st_tv)
    TextView oneStTv;
    @BindView(R.id.driver_person_msg_one_ed_tv)
    TextView oneEdTv;
    @BindView(R.id.driver_person_msg_twe_st_tv)
    TextView tweStTv;
    @BindView(R.id.driver_person_msg_twe_ed_tv)
    TextView tweEdTv;
    @BindView(R.id.driver_person_msg_thr_st_tv)
    TextView thrStTv;
    @BindView(R.id.driver_person_msg_thr_ed_tv)
    TextView thrEdTv;
    // 常跑路线总体
    @BindView(R.id.driver_details_one_ll)
    LinearLayout oneLl;
    @BindView(R.id.driver_details_twe_ll)
    LinearLayout tweLl;
    @BindView(R.id.driver_details_thr_ll)
    LinearLayout thrLl;
    // 拨打电话
    @BindView(R.id.driver_details_call_tv)
    TextView callTv;
    @BindView(R.id.driver_start)
    TextView driver_start;
    @BindView(R.id.driver_end)
    TextView driver_end;
    @BindView(R.id.driver_time)
    TextView driver_time;

    // 数据相关
    // 司机详情
    SimplifyThread driverInfoThread;
    // 司机详情传参
    HashMap<String, String> driverInfoMap;
    // 司机详情返回
    DriverDetailsBean driverDetailsBean;

    private String id = "";
    private String phone = "";
    private String startplace = "";
    private String endplace = "";
    private String time = "";

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    MyToast.show(DriverDetailsActivity.this, message.obj.toString());
                    break;
                case 1:
                    if (driverDetailsBean != null) {
                        if (driverDetailsBean.getData() == null) {
                            MyToast.show(DriverDetailsActivity.this, driverDetailsBean.getMsg());
                        } else {
                            setData();
                        }
                    } else {
                        MyToast.show(DriverDetailsActivity.this, "未能获取数据");
                    }
                    break;
                case 2:
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onDestroy() {
        if (handler != null) {
            handler = null;
        }
        super.onDestroy();
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_driver_details;
    }

    @Override
    protected void initData() {

        id = getIntent().getExtras().getString("id");
        phone = getIntent().getExtras().getString("phone");
        startplace = getIntent().getExtras().getString("startplace");
        endplace = getIntent().getExtras().getString("endplace");
        time = getIntent().getExtras().getString("time");
        driverInfo();
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
        driver_start.setText(startplace);
        driver_end.setText(endplace);
        driver_time.setText(DateTimeUtil.stampToDate("yyyy/MM/dd", time + "000"));
        if (driverDetailsBean.getData().getInfo() != null && driverDetailsBean.getData().getInfo().getFace() != null
                && !driverDetailsBean.getData().getInfo().getFace().equals("")) {
            Glide.with(DriverDetailsActivity.this)
                    .load(UrlUtil.IMAGE_URL + driverDetailsBean.getData().getInfo().getFace())
                    .error(R.mipmap.head_default)
                    .into(headerIv);
        }
        if (driverDetailsBean.getData().getInfo() != null && driverDetailsBean.getData().getInfo().getRelname() != null
                && !driverDetailsBean.getData().getInfo().getRelname().equals("")) {
            nameTv.setText(driverDetailsBean.getData().getInfo().getRelname());
        }
        if (driverDetailsBean.getData().getInfo() != null && driverDetailsBean.getData().getInfo().getCartcode() != null
                && !driverDetailsBean.getData().getInfo().getCartcode().equals("")) {
            plateTv.setText(driverDetailsBean.getData().getInfo().getCartcode());
        }
        if (driverDetailsBean.getData().getInfo() != null && driverDetailsBean.getData().getInfo().getCar_type() != null) {
//            String carType = "";
//            for (int i = 0; i < driverDetailsBean.getData().getInfo().getCar_type().length(); i++) {
//                switch (driverDetailsBean.getData().getInfo().getCar_type().indexOf(i)) {
//                    case 1:
//                        carType = carType + "第一类  ";
//                        break;
//                    case 2:
//                        carType = carType + "第二类  ";
//                        break;
//                    case 3:
//                        carType = carType + "第三类  ";
//                        break;
//                    case 4:
//                        carType = carType + "第四类  ";
//                        break;
//                    case 5:
//                        carType = carType + "第五类  ";
//                        break;
//                    case 6:
//                        carType = carType + "第六类  ";
//                        break;
//                    case 7:
//                        carType = carType + "第七类  ";
//                        break;
//                    case 8:
//                        carType = carType + "第八类  ";
//                        break;
//                    case 9:
//                        carType = carType + "第九类  ";
//                        break;
//                }
//            }
            carTypeTv.setText(driverDetailsBean.getData().getInfo().getCar_type());
        }
        if (driverDetailsBean.getData().getInfo() != null && driverDetailsBean.getData().getInfo().getLoad() != null
                && !driverDetailsBean.getData().getInfo().getLoad().equals("")) {
            loadTv.setText(driverDetailsBean.getData().getInfo().getLoad() + "吨");
        }
        if (driverDetailsBean.getData().getInfo() != null && driverDetailsBean.getData().getRoute() != null) {
//            RoteAdapter adapter = new RoteAdapter();
//            adapter.setRouteBeans(driverDetailsBean.getData().getRoute());
//            normalRouteLv.setAdapter(adapter);
        }
        if (driverDetailsBean.getData().getInfo() != null && driverDetailsBean.getData().getInfo().getPhone() != null
                && !driverDetailsBean.getData().getInfo().getPhone().equals("")) {
            callTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    DiyDialog.hintTweBtnDialog(DriverDetailsActivity.this, "是否向司机拨打电话?", new DiyDialog.HintTweBtnListener() {
//                        @Override
//                        public void confirm() {
//                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + driverDetailsBean.getData().getInfo().getPhone()));
//                    if (ActivityCompat.checkSelfPermission(DriverDetailsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                        return;
//                    }
//                    startActivity(intent);
                    CallPermission.callPhone(DriverDetailsActivity.this, driverDetailsBean.getData().getInfo().getPhone());
//                        }
//                    });
                }
            });
        }

        // 加载常跑路线
        if (driverDetailsBean.getData().getInfo() != null && driverDetailsBean.getData().getInfo().getRoute() != null &&
                !driverDetailsBean.getData().getInfo().getRoute().equals("")) {
            String route = driverDetailsBean.getData().getInfo().getRoute();
            String[] routeArr = route.split(",");
            if (routeArr.length != 0) {
                String[] placeArr;
                if (routeArr.length >= 1) {
                    oneLl.setVisibility(View.VISIBLE);
                    placeArr = routeArr[0].split("-");
                    oneStTv.setText(placeArr[0]);
                    oneEdTv.setText(placeArr[1]);
                }
                if (routeArr.length >= 2) {
                    tweLl.setVisibility(View.VISIBLE);
                    placeArr = routeArr[1].split("-");
                    tweStTv.setText(placeArr[0]);
                    tweEdTv.setText(placeArr[1]);
                }
                if (routeArr.length >= 3) {
                    thrLl.setVisibility(View.VISIBLE);
                    placeArr = routeArr[2].split("-");
                    thrStTv.setText(placeArr[0]);
                    thrEdTv.setText(placeArr[1]);
                }
            }
        }
    }

    private void driverInfo() {
        driverInfoMap = new HashMap<>();
        driverInfoMap.put("id", id);
        driverInfoMap.put("phone", phone);
        driverInfoThread = new SimplifyThread(UrlUtil.RUN_DRIVERINFO, driverInfoMap);
        driverInfoThread.setOnGetResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                driverDetailsBean = JsonUtil.driverDetailsBeanResolve(res);
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

//    class RoteAdapter extends BaseAdapter {
//
//        private ArrayList<DriverDetailsBean.DataBean.RouteBean> routeBeans;
//
//        public void setRouteBeans(List<DriverDetailsBean.DataBean.RouteBean> routeBeans) {
//            this.routeBeans = (ArrayList<DriverDetailsBean.DataBean.RouteBean>) routeBeans;
//            notifyDataSetChanged();
//        }
//
//        @Override
//        public int getCount() {
//            return 0;
//        }
//
//        @Override
//        public Object getItem(int i) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int i) {
//            return 0;
//        }
//
//        @Override
//        public View getView(int i, View view, ViewGroup viewGroup) {
//            RoteViewHolder vh;
//            if (view == null) {
//                view = LayoutInflater.from(DriverDetailsActivity.this).inflate(R.layout.item_driver_details_normal_route, viewGroup, false);
//                vh = new RoteViewHolder();
//                vh.stTv = view.findViewById(R.id.item_driver_details_normal_route_st_tv);
//                vh.edTv = view.findViewById(R.id.item_driver_details_normal_route_ed_tv);
//                view.setTag(vh);
//            } else {
//                vh = (RoteViewHolder) view.getTag();
//            }
//            if (routeBeans.get(i).getStartplace() != null) {
//                vh.stTv.setText(routeBeans.get(i).getStartplace());
//            }
//            if (routeBeans.get(i).getEndplace() != null) {
//                vh.edTv.setText(routeBeans.get(i).getEndplace());
//            }
//            return view;
//        }
//
//        class RoteViewHolder {
//            TextView stTv, edTv;
//        }
//    }
}
