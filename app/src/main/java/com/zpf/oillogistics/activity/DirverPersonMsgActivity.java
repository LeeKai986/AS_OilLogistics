package com.zpf.oillogistics.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lljjcoder.city_20170724.CityPickerView;
import com.lljjcoder.city_20170724.bean.CityBean;
import com.lljjcoder.city_20170724.bean.DistrictBean;
import com.lljjcoder.city_20170724.bean.ProvinceBean;
import com.zpf.oillogistics.R;
import com.zpf.oillogistics.base.BaseActivity;
import com.zpf.oillogistics.base.CyApplication;
import com.zpf.oillogistics.base.MessageWhat;
import com.zpf.oillogistics.bean.response.SelfChangeResponse;
import com.zpf.oillogistics.customview.NavEditItem;
import com.zpf.oillogistics.customview.NavItem;
import com.zpf.oillogistics.customview.SelectPicPopupWindow;
import com.zpf.oillogistics.net.UrlUtil;
import com.zpf.oillogistics.utils.MyShare;
import com.zpf.oillogistics.utils.MyToast;
import com.zpf.oillogistics.utils.TakePictrueUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/9/18.
 * <p>
 * 司机个人信息
 */

public class DirverPersonMsgActivity extends BaseActivity implements View.OnClickListener {

    //返回
    @BindView(R.id.rel_back_inforself)
    RelativeLayout relBack;
    //返回
    @BindView(R.id.rel_location)
    RelativeLayout relLocation;
    //头像
    @BindView(R.id.cir_head_inforself)
    CircleImageView cirHead;
    //昵称
    @BindView(R.id.nav_name_inforself)
    NavEditItem navName;
    //联系号码
    @BindView(R.id.nai_phone_inforself)
    NavItem navPhone;
    //city
    @BindView(R.id.nav_city_inforself)
    NavItem navCity;
    //详细地址
    @BindView(R.id.edit_adress_inforself)
    EditText editAdress;
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
    //提交
    @BindView(R.id.tv_config_inforself)
    TextView tvConfig;


    //图片处理类
    private TakePictrueUtils takePictrue;
    private String adressArea = "";

    @Override
    protected int setLayout() {
        return R.layout.activity_driver_person_msg;
    }

    @Override
    protected void initData() {

        //加载头像
        if (!MyShare.getShared().getString("userHead", "").equals("")) {
            Glide.with(DirverPersonMsgActivity.this)
                    .load(UrlUtil.IMAGE_URL + MyShare.getShared().getString("userHead", ""))
                    .error(R.mipmap.head_default)
                    .into(cirHead);
        }
        //加载昵称
        if (!MyShare.getShared().getString("relname", "").equals("")) {
            navName.setTvActionState(MyShare.getShared().getString("relname", ""));
        }

        //加载电话
        if (!MyShare.getShared().getString("userPhone", "").equals("")) {
            navPhone.setTvActionState(MyShare.getShared().getString("userPhone", ""));
            navPhone.setMesgVisibility(View.GONE);
        }

        //加载省
        if (!MyShare.getShared().getString("province", "").equals("")) {
            navCity.setTvActionState(MyShare.getShared().getString("province", ""));
            adressArea = MyShare.getShared().getString("province", "");
            CyApplication.province = MyShare.getShared().getString("province", "");
        }

        //加载市
        if (!MyShare.getShared().getString("city", "").equals("")) {
            navCity.setTvActionState(MyShare.getShared().getString("province", "") +
                    MyShare.getShared().getString("city", ""));
            adressArea += ("-" + MyShare.getShared().getString("city", ""));
        }
        //加载区
        if (!MyShare.getShared().getString("area", "").equals("")) {
            navCity.setTvActionState(MyShare.getShared().getString("province", "") +
                    MyShare.getShared().getString("city", "") + MyShare.getShared().getString("area", ""));
            adressArea += "-" + MyShare.getShared().getString("area", "");
        }
        CyApplication.area = adressArea;

        //加载详情地址
        if (!MyShare.getShared().getString("toaddress", "").equals("")) {
            CyApplication.adress = MyShare.getShared().getString("toaddress", "");
            editAdress.setText(MyShare.getShared().getString("toaddress", ""));
        }

        // 加载常跑路线
        if (!MyShare.getShared().getString("route", "").equals("")) {
            String route = MyShare.getShared().getString("route", "");
            String[] routeArr = route.split(",");
            if (routeArr.length != 0) {
                String[] placeArr;
                if (routeArr.length >= 1) {
                    placeArr = routeArr[0].split("-");
                    oneStTv.setText(placeArr[0]);
                    oneEdTv.setText(placeArr[1]);
                }
                if (routeArr.length >= 2) {
                    placeArr = routeArr[1].split("-");
                    tweStTv.setText(placeArr[0]);
                    tweEdTv.setText(placeArr[1]);
                }
                if (routeArr.length >= 3) {
                    placeArr = routeArr[2].split("-");
                    thrStTv.setText(placeArr[0]);
                    thrEdTv.setText(placeArr[1]);
                }
            }
        }

        navCity.setTvActionState(CyApplication.area.replace("-", ""));
        editAdress.setText(CyApplication.adress);
    }

    @Override
    protected void initViewAction(Bundle savedInstanceState) {
        relBack.setOnClickListener(this);
        navCity.setOnClickListener(this);
        cirHead.setOnClickListener(this);
        relLocation.setOnClickListener(this);
        tvConfig.setOnClickListener(this);

        routeListener();
    }

    private void routeListener() {
        oneStTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CityPickerView cityPicker = new CityPickerView.Builder(DirverPersonMsgActivity.this)
                        .textSize(20)
                        .title("  ")
                        .backgroundPop(0xa0000000)
                        .titleBackgroundColor("#FFFFFF")
                        .titleTextColor("#000000")
                        .confirTextColor("#FF6571")
                        .cancelTextColor("#C9C9C9")
                        .province("北京市")
                        .city("北京")
                        .district("东城区")
                        .textColor(Color.parseColor("#000000"))
                        .provinceCyclic(true)
                        .cityCyclic(false)
                        .districtCyclic(false)
                        .visibleItemsCount(5)
                        .itemPadding(10)
                        .onlyShowProvinceAndCity(false)
                        .build();
                cityPicker.show();

                //监听方法，获取选择结果
                cityPicker.setOnCityItemClickListener(new CityPickerView.OnCityItemClickListener() {
                    @Override
                    public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                        //返回结果
                        //ProvinceBean 省份信息
                        //CityBean     城市信息
                        //DistrictBean 区县信息
                        oneStTv.setText(province + "\n" + city + district);
                    }

                    @Override
                    public void onCancel() {
                    }
                });
            }
        });

        oneEdTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CityPickerView cityPicker = new CityPickerView.Builder(DirverPersonMsgActivity.this)
                        .textSize(20)
                        .title("  ")
                        .backgroundPop(0xa0000000)
                        .titleBackgroundColor("#FFFFFF")
                        .titleTextColor("#000000")
                        .confirTextColor("#FF6571")
                        .cancelTextColor("#C9C9C9")
                        .province("北京市")
                        .city("北京")
                        .district("东城区")
                        .textColor(Color.parseColor("#000000"))
                        .provinceCyclic(true)
                        .cityCyclic(false)
                        .districtCyclic(false)
                        .visibleItemsCount(5)
                        .itemPadding(10)
                        .onlyShowProvinceAndCity(false)
                        .build();
                cityPicker.show();

                //监听方法，获取选择结果
                cityPicker.setOnCityItemClickListener(new CityPickerView.OnCityItemClickListener() {
                    @Override
                    public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                        //返回结果
                        //ProvinceBean 省份信息
                        //CityBean     城市信息
                        //DistrictBean 区县信息
                        oneEdTv.setText(province + "\n" + city + district);
                    }

                    @Override
                    public void onCancel() {
                    }
                });
            }
        });

        tweStTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CityPickerView cityPicker = new CityPickerView.Builder(DirverPersonMsgActivity.this)
                        .textSize(20)
                        .title("  ")
                        .backgroundPop(0xa0000000)
                        .titleBackgroundColor("#FFFFFF")
                        .titleTextColor("#000000")
                        .confirTextColor("#FF6571")
                        .cancelTextColor("#C9C9C9")
                        .province("北京市")
                        .city("北京")
                        .district("东城区")
                        .textColor(Color.parseColor("#000000"))
                        .provinceCyclic(true)
                        .cityCyclic(false)
                        .districtCyclic(false)
                        .visibleItemsCount(5)
                        .itemPadding(10)
                        .onlyShowProvinceAndCity(false)
                        .build();
                cityPicker.show();

                //监听方法，获取选择结果
                cityPicker.setOnCityItemClickListener(new CityPickerView.OnCityItemClickListener() {
                    @Override
                    public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                        //返回结果
                        //ProvinceBean 省份信息
                        //CityBean     城市信息
                        //DistrictBean 区县信息
                        tweStTv.setText(province + "\n" + city + district);
                    }

                    @Override
                    public void onCancel() {
                    }
                });
            }
        });

        tweEdTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CityPickerView cityPicker = new CityPickerView.Builder(DirverPersonMsgActivity.this)
                        .textSize(20)
                        .title("  ")
                        .backgroundPop(0xa0000000)
                        .titleBackgroundColor("#FFFFFF")
                        .titleTextColor("#000000")
                        .confirTextColor("#FF6571")
                        .cancelTextColor("#C9C9C9")
                        .province("北京市")
                        .city("北京")
                        .district("东城区")
                        .textColor(Color.parseColor("#000000"))
                        .provinceCyclic(true)
                        .cityCyclic(false)
                        .districtCyclic(false)
                        .visibleItemsCount(5)
                        .itemPadding(10)
                        .onlyShowProvinceAndCity(false)
                        .build();
                cityPicker.show();

                //监听方法，获取选择结果
                cityPicker.setOnCityItemClickListener(new CityPickerView.OnCityItemClickListener() {
                    @Override
                    public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                        //返回结果
                        //ProvinceBean 省份信息
                        //CityBean     城市信息
                        //DistrictBean 区县信息
                        tweEdTv.setText(province + "\n" + city + district);
                    }

                    @Override
                    public void onCancel() {
                    }
                });
            }
        });

        thrStTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CityPickerView cityPicker = new CityPickerView.Builder(DirverPersonMsgActivity.this)
                        .textSize(20)
                        .title("  ")
                        .backgroundPop(0xa0000000)
                        .titleBackgroundColor("#FFFFFF")
                        .titleTextColor("#000000")
                        .confirTextColor("#FF6571")
                        .cancelTextColor("#C9C9C9")
                        .province("北京市")
                        .city("北京")
                        .district("东城区")
                        .textColor(Color.parseColor("#000000"))
                        .provinceCyclic(true)
                        .cityCyclic(false)
                        .districtCyclic(false)
                        .visibleItemsCount(5)
                        .itemPadding(10)
                        .onlyShowProvinceAndCity(false)
                        .build();
                cityPicker.show();

                //监听方法，获取选择结果
                cityPicker.setOnCityItemClickListener(new CityPickerView.OnCityItemClickListener() {
                    @Override
                    public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                        //返回结果
                        //ProvinceBean 省份信息
                        //CityBean     城市信息
                        //DistrictBean 区县信息
                        thrStTv.setText(province + "\n" + city + district);
                    }

                    @Override
                    public void onCancel() {
                    }
                });
            }
        });

        thrEdTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CityPickerView cityPicker = new CityPickerView.Builder(DirverPersonMsgActivity.this)
                        .textSize(20)
                        .title("  ")
                        .backgroundPop(0xa0000000)
                        .titleBackgroundColor("#FFFFFF")
                        .titleTextColor("#000000")
                        .confirTextColor("#FF6571")
                        .cancelTextColor("#C9C9C9")
                        .province("北京市")
                        .city("北京")
                        .district("东城区")
                        .textColor(Color.parseColor("#000000"))
                        .provinceCyclic(true)
                        .cityCyclic(false)
                        .districtCyclic(false)
                        .visibleItemsCount(5)
                        .itemPadding(10)
                        .onlyShowProvinceAndCity(false)
                        .build();
                cityPicker.show();

                //监听方法，获取选择结果
                cityPicker.setOnCityItemClickListener(new CityPickerView.OnCityItemClickListener() {
                    @Override
                    public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                        //返回结果
                        //ProvinceBean 省份信息
                        //CityBean     城市信息
                        //DistrictBean 区县信息
                        thrEdTv.setText(province + "\n" + city + district);
                    }

                    @Override
                    public void onCancel() {
                    }
                });
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rel_back_inforself:
                finish();
                break;
            case R.id.rel_location:
                String city = "";
                String adress = "";
                if (navCity.getTvActionState().toString().equals("")) {
                    MyToast.show(DirverPersonMsgActivity.this, "请选择区域地址");
                    break;
                } else {
                    city = navCity.getTvActionState().toString();
                }

                adress = editAdress.getText().toString();

                Intent intent = new Intent(DirverPersonMsgActivity.this, MapActivity.class);
                intent.putExtra("city", city);
                intent.putExtra("adress", adress);
                intent.putExtra("nav", false);//是否导航
                startActivityForResult(intent, 999);

                break;
            case R.id.cir_head_inforself:
                picWindow = new SelectPicPopupWindow(DirverPersonMsgActivity.this, picItemsClick);
                picWindow.showAtLocation(findViewById(R.id.contanor_layout),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.nav_city_inforself:
                CityPickerView cityPicker = new CityPickerView.Builder(DirverPersonMsgActivity.this)
                        .textSize(20)
                        .title("  ")
                        .backgroundPop(0xa0000000)
                        .titleBackgroundColor("#FFFFFF")
                        .titleTextColor("#000000")
                        .confirTextColor("#FF6571")
                        .cancelTextColor("#C9C9C9")
                        .province("北京市")
                        .city("北京")
                        .district("东城区")
                        .textColor(Color.parseColor("#000000"))
                        .provinceCyclic(true)
                        .cityCyclic(false)
                        .districtCyclic(false)
                        .visibleItemsCount(5)
                        .itemPadding(10)
                        .onlyShowProvinceAndCity(false)
                        .build();
                cityPicker.show();

                //监听方法，获取选择结果
                cityPicker.setOnCityItemClickListener(new CityPickerView.OnCityItemClickListener() {
                    @Override
                    public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                        //返回结果
                        //ProvinceBean 省份信息
                        //CityBean     城市信息
                        //DistrictBean 区县信息
                        if (district.getName() != null && !district.getName().equals(""))
                            adressArea = province.getName() + "-" + city.getName() + "-" + district.getName();
                        else
                            adressArea = province.getName() + "-" + city.getName();
                        navCity.setTvActionState(province.getName() + city.getName() + district.getName());
                    }

                    @Override
                    public void onCancel() {
                    }
                });
                break;
            case R.id.tv_config_inforself:
                inner_postAsync();
                break;
        }
    }

    private SelectPicPopupWindow picWindow;
    private View.OnClickListener picItemsClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            picWindow.dismiss();

            takePictrue = new TakePictrueUtils(DirverPersonMsgActivity.this, "head");
            switch (v.getId()) {
                case R.id.takePhotoBtn:
                    //判断是否开户相册权限
                    if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(DirverPersonMsgActivity.this, android.Manifest.permission.CAMERA)) {
                        takePictrue.startCamera();
                    } else {
                        //提示用户开户权限
                        MyToast.show(DirverPersonMsgActivity.this, "请赋予应用相机权限");
                    }
                    break;
                case R.id.pickPhotoBtn:
                    takePictrue.startWall();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TakePictrueUtils.PHOTO_CAMERA:
                //表示从相机获得的照片，需要进行裁剪
                // 由于可以调起多个相机先进行文件大小确认
                if (takePictrue.tempFile.exists()) {
                    try {
                        if (new FileInputStream(takePictrue.tempFile).available() != 0) {
                            takePictrue.startPhotoCut(takePictrue.imageUri, 300, true);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case TakePictrueUtils.PHOTO_WALL:
                if (null != data) {
                    takePictrue.startPhotoCut(data.getData(), 300, true);
                }
                break;
            case TakePictrueUtils.PHOTO_STORE:
                if (null != data) {
                    Bitmap bitmap = takePictrue.setPictureToImageView(data, true);
                    if (bitmap != null) {
                        cirHead.setImageBitmap(bitmap);//将图片显示到ImageView上面
                    }
                }
                break;
            case TakePictrueUtils.PHOTO_NOT_STORE:
                if (null != data) {
                    takePictrue.setPictureToImageView(data, false);
                }
                break;
            case 999:
                navCity.setTvActionState(CyApplication.area.replace("-", ""));
                editAdress.setText(CyApplication.adress);
                break;
            default:
                break;
        }
    }


    /**
     * 修改信息
     */
    private void inner_postAsync() {
        if (navName.getTvActionState().equals("")) {
            MyToast.show(DirverPersonMsgActivity.this, "请输入姓名");
            return;
        }
        if (navPhone.getTvActionState().equals("")) {
            MyToast.show(DirverPersonMsgActivity.this, "请输入联系方式");
            return;
        } else if (navPhone.getTvActionState().length() < 11) {
            MyToast.show(DirverPersonMsgActivity.this, "手机号码不正确");
            return;
        }
        if (navCity.getTvActionState().equals("")) {
            MyToast.show(DirverPersonMsgActivity.this, "请选择区域");
            return;
        }
        if (editAdress.getText().toString().equals("")) {
            MyToast.show(DirverPersonMsgActivity.this, "请输入详细地址");
            return;
        }
        if ((!oneStTv.getText().toString().equals("") && oneEdTv.getText().toString().equals("")) ||
                (oneStTv.getText().toString().equals("") && !oneEdTv.getText().toString().equals(""))) {
            MyToast.show(DirverPersonMsgActivity.this, "请填写完整常跑路线01中的起点终点");
            return;
        }
        if ((!tweStTv.getText().toString().equals("") && tweEdTv.getText().toString().equals("")) ||
                (tweStTv.getText().toString().equals("") && !tweEdTv.getText().toString().equals(""))) {
            MyToast.show(DirverPersonMsgActivity.this, "请填写完整常跑路线02中的起点终点");
            return;
        }
        if ((!thrStTv.getText().toString().equals("") && thrEdTv.getText().toString().equals("")) ||
                (thrStTv.getText().toString().equals("") && !thrEdTv.getText().toString().equals(""))) {
            MyToast.show(DirverPersonMsgActivity.this, "请填写完整常跑路线03中的起点终点");
            return;
        }

        String normalRoute = "";
        if (!oneStTv.getText().toString().equals("")) {
            normalRoute = oneStTv.getText().toString() + "-" + oneEdTv.getText().toString();
        }
        if (!tweStTv.getText().toString().equals("")) {
            if (normalRoute.equals("")) {
                normalRoute = tweStTv.getText().toString() + "-" + tweEdTv.getText().toString();
            } else {
                normalRoute = normalRoute + "," + tweStTv.getText().toString() + "-" + tweEdTv.getText().toString();
            }
        }
        if (!thrStTv.getText().toString().equals("")) {
            if (normalRoute.equals("")) {
                normalRoute = thrStTv.getText().toString() + "-" + thrEdTv.getText().toString();
            } else {
                normalRoute = normalRoute + "," + thrStTv.getText().toString() + "-" + thrEdTv.getText().toString();
            }
        }

        Map<String, String> params = new HashMap<>();
        params.put("id", MyShare.getShared().getString("userId", ""));
        params.put("relname", navName.getTvActionState());

        params.put("address", navCity.getTvActionState());
        params.put("toaddress", editAdress.getText().toString());
        params.put("phone", navPhone.getTvActionState());
        params.put("route", normalRoute);


        //图片Base64编码上传
        String imageString = "";
        if (takePictrue != null && takePictrue.bitmaptoString() != null) {
            params.put("face", "data:image/jpg;base64," + takePictrue.bitmaptoString());
        } else {
            params.put("face", imageString);
        }

        RequestBody requestBody = null;
        /**
         * 3.0之后版本
         */
        FormBody.Builder builder = new FormBody.Builder();

        /**
         * 在这对添加的参数进行遍历，map遍历有四种方式，如果想要了解的可以网上查找
         */
        for (Map.Entry<String, String> map : params.entrySet()) {
            String key = map.getKey().toString();
            String value = null;
            /**
             * 判断值是否是空的
             */
            if (map.getValue() == null) {
                value = "";
            } else {
                value = map.getValue();
            }
            /**
             * 把key和value添加到formbody中
             */
            builder.add(key, value);
        }
        requestBody = builder.build();
        // 请求对象
        final Request request = new Request.Builder().url(UrlUtil.URL_USER_PERSONAL).post(requestBody).build();
        OkHttpClient mClient = new OkHttpClient();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(MessageWhat.REQUST_ERROR);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Message message = new Message();
                message.obj = result;
                message.what = MessageWhat.SELF_USER_PERSONAL;
                handler.sendMessage(message);
            }
        });
    }


    private Gson gson = new Gson();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MessageWhat.SELF_USER_PERSONAL:
                    if (msg.obj != null) {
                        try {
                            SelfChangeResponse change = gson.fromJson(msg.obj.toString(), SelfChangeResponse.class);
                            if (change.getStatus() == 0) {
                                SharedPreferences sp = getSharedPreferences("SHARE_OIL_USER", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                SelfChangeResponse.DataBean data = change.getData();
                                editor.putString("userPhone", data.getPhone());
                                editor.putString("userHead", data.getFace());
                                editor.putString("province", data.getProvince());
                                editor.putString("city", data.getCity());
                                editor.putString("niname", data.getNiname());
                                editor.putString("area", data.getArea());
                                editor.putString("card", data.getCard());
                                editor.putString("manage", data.getManage());
                                editor.putString("toaddress", data.getToaddress());
                                editor.putString("relname", data.getRelname());
                                editor.putString("longitude", data.getLongitude());
                                editor.putString("latitude", data.getLatitude());
                                editor.putString("statuss", data.getStatuss());
                                editor.putString("route", data.getRoute());
                                editor.commit();

                                sendBroadcast(new Intent("com.zpf.oillogistics.receiver.DetailsReceiver"));

                                MyToast.show(DirverPersonMsgActivity.this, "个人信息提交成功!");
                                startActivity(new Intent(DirverPersonMsgActivity.this, DriverIdenConfirmActivity.class));
                                finish();


                            } else {
                                MyToast.show(DirverPersonMsgActivity.this, "暂无数据!");
                            }

                        } catch (Exception e) {
                            MyToast.show(DirverPersonMsgActivity.this, "返回数据异常!");
                        }

                    } else {
                        MyToast.show(DirverPersonMsgActivity.this, "返回数据失败!");
                    }
                    break;
            }
        }
    };

}
