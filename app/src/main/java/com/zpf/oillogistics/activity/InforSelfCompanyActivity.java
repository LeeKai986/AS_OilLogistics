package com.zpf.oillogistics.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.zpf.oillogistics.diy.DiyDialog;
import com.zpf.oillogistics.net.UrlUtil;
import com.zpf.oillogistics.utils.MyShare;
import com.zpf.oillogistics.utils.MyToast;
import com.zpf.oillogistics.utils.TakePictrueUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
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
 */

public class InforSelfCompanyActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.rel_back_inforself_company)
    RelativeLayout relBack;
    @BindView(R.id.cir_head_inforself_company)
    CircleImageView cirHead;
    @BindView(R.id.nav_name_inforself_company)
    NavEditItem navName;
    @BindView(R.id.nav_project_inforself_company)
    NavItem navProject;
    @BindView(R.id.iv_license_inforself_company)
    ImageView ivLicense;
    @BindView(R.id.rel_license_inforself_company)
    RelativeLayout relLicense;
    @BindView(R.id.iv_permission_inforself_company)
    ImageView ivPermission;
    @BindView(R.id.rel_permission_inforself_company)
    RelativeLayout relPermission;
    @BindView(R.id.rel_location)
    RelativeLayout relLocation;
    @BindView(R.id.nav_city_inforself_company)
    NavItem navCityInfor;
    @BindView(R.id.edit_adress_inforself_company)
    EditText editAdress;
    @BindView(R.id.tv_config_inforself_company)
    TextView tvConfig;

    private String imageFlag = "head";
    //头像本地图片地址
    private String headUrl = "";
    //经营许可本地图片地址
    private String licenseUrl = "";
    //营业执照本地图片地址
    private String enterUrl = "";

    private TakePictrueUtils takePictrue;

    //区域
    private String adressArea = "";
    private String longitude = "";
    private String latitude = "";

    @Override
    protected int setLayout() {
        return R.layout.activity_inforself_company;
    }

    @Override
    protected void initData() {

        cirHead.setOnClickListener(this);
        relBack.setOnClickListener(this);
        navProject.setOnClickListener(this);
        tvConfig.setOnClickListener(this);
        relLicense.setOnClickListener(this);
        relPermission.setOnClickListener(this);
        navCityInfor.setOnClickListener(this);
        relLocation.setOnClickListener(this);

        //加载头像
        if (!MyShare.getShared().getString("userHead", "").equals("")) {
            Glide.with(InforSelfCompanyActivity.this)
                    .load(UrlUtil.IMAGE_URL + MyShare.getShared().getString("userHead", ""))
                    .error(R.mipmap.head_default)
                    .into(cirHead);
        }

        //加载执照
        if (!MyShare.getShared().getString("enterprise", "").equals("")) {
            Glide.with(InforSelfCompanyActivity.this)
                    .load(UrlUtil.IMAGE_URL + MyShare.getShared().getString("enterprise", ""))
                    .placeholder(R.mipmap.default_goods)
                    .error(R.mipmap.default_goods)
                    .into(ivLicense);
        }

        //加载经营许可
        if (!MyShare.getShared().getString("license", "").equals("")) {
            Glide.with(InforSelfCompanyActivity.this)
                    .load(UrlUtil.IMAGE_URL + MyShare.getShared().getString("license", ""))
                    .placeholder(R.mipmap.default_goods)
                    .error(R.mipmap.default_goods)
                    .into(ivPermission);
        }

        //加载昵称
        if (!MyShare.getShared().getString("companyname", "").equals("")) {
            navName.setTvActionState(MyShare.getShared().getString("companyname", ""));
        }

        //加载省
        if (!MyShare.getShared().getString("province", "").equals("")) {
            navCityInfor.setTvActionState(MyShare.getShared().getString("province", ""));
            adressArea = MyShare.getShared().getString("province", "");
            CyApplication.province = MyShare.getShared().getString("province", "");
        }

        //加载市
        if (!MyShare.getShared().getString("city", "").equals("") &&
                !MyShare.getShared().getString("city", "").equals("null")) {
            navCityInfor.setTvActionState(MyShare.getShared().getString("province", "") +
                    MyShare.getShared().getString("city", ""));
            adressArea += ("-" + MyShare.getShared().getString("city", ""));

        }
        CyApplication.area = adressArea;

        //加载详情地址
        if (!MyShare.getShared().getString("toaddress", "").equals("")) {
            CyApplication.adress = MyShare.getShared().getString("toaddress", "");
            editAdress.setText(MyShare.getShared().getString("toaddress", ""));
        }
        //加载项目
        if (MyShare.getShared().getString("manage", "").equals("1")) {
            navProject.setTvActionState("石油类");
        } else if (MyShare.getShared().getString("manage", "").equals("2")) {
            navProject.setTvActionState("化工类");
        }

        if (!MyShare.getShared().getString("longitude", "").equals(""))
            longitude = MyShare.getShared().getString("longitude", "");

        if (!MyShare.getShared().getString("latitude", "").equals(""))
            latitude = MyShare.getShared().getString("latitude", "");

    }

    @Override
    protected void initViewAction(Bundle savedInstanceState) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!CyApplication.area.equals("")) {
            navCityInfor.setTvActionState(CyApplication.area.replace("-", ""));
        }
        if (!CyApplication.adress.equals("")) {
            editAdress.setText(CyApplication.adress);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rel_location:
                String city = "";
                String adress = "";
                if (navCityInfor.getTvActionState().toString().equals("")) {
                    MyToast.show(InforSelfCompanyActivity.this, "请选择区域地址");
                    break;
                } else {
                    city = navCityInfor.getTvActionState().toString();
                }

                adress = editAdress.getText().toString();

                Intent intent = new Intent(InforSelfCompanyActivity.this, MapActivity.class);
                intent.putExtra("city", city);
                intent.putExtra("adress", adress);
                intent.putExtra("nav", false);//是否导航
                startActivity(intent);

                break;
            case R.id.rel_back_inforself_company:
                finish();
                break;
            case R.id.nav_city_inforself_company:
                CityPickerView cityPicker = new CityPickerView.Builder(InforSelfCompanyActivity.this)
                        .textSize(20)
                        .title("  ")
                        .backgroundPop(0xa0000000)
                        .titleBackgroundColor("#FFFFFF")
                        .titleTextColor("#000000")
                        .confirTextColor("#FF6571")
                        .cancelTextColor("#C9C9C9")
                        .province("北京市")
                        .city("北京")
                        .textColor(Color.parseColor("#000000"))
                        .provinceCyclic(true)
                        .cityCyclic(false)
                        .districtCyclic(false)
                        .visibleItemsCount(5)
                        .itemPadding(10)
                        .onlyShowProvinceAndCity(true)
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
                        adressArea = province.getName() + "-" + city.getName();
                        navCityInfor.setTvActionState(province.getName() + city.getName());
                    }

                    @Override
                    public void onCancel() {
                    }
                });
                break;
            case R.id.cir_head_inforself_company:
                imageFlag = "head";
                picWindow = new SelectPicPopupWindow(InforSelfCompanyActivity.this, picItemsClick);
                picWindow.showAtLocation(findViewById(R.id.contanor_layout),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

                break;
            case R.id.nav_project_inforself_company:
                ArrayList<String> list = new ArrayList<String>();
                list.add("石油类");
                list.add("化工类");

                DiyDialog.singleSelectDialog(InforSelfCompanyActivity.this,
                        list, new DiyDialog.SingleSelectListener() {
                            @Override
                            public void SingleSelect(String res) {
                                navProject.setTvActionState(res);
                            }
                        });
                break;
            case R.id.rel_license_inforself_company:
                imageFlag = "license";
                picWindow = new SelectPicPopupWindow(InforSelfCompanyActivity.this, picItemsClick);
                picWindow.showAtLocation(findViewById(R.id.contanor_layout),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.rel_permission_inforself_company:
                imageFlag = "perm";
                picWindow = new SelectPicPopupWindow(InforSelfCompanyActivity.this, picItemsClick);
                picWindow.showAtLocation(findViewById(R.id.contanor_layout),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.tv_config_inforself_company:
                inner_postAsync();
                break;
        }
    }

    private SelectPicPopupWindow picWindow;
    private View.OnClickListener picItemsClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            picWindow.dismiss();

            takePictrue = new TakePictrueUtils(InforSelfCompanyActivity.this, imageFlag);

            //图片分类
            if (imageFlag.equals("perm"))
                licenseUrl = takePictrue.imageUri.getPath();
            else if (imageFlag.equals("license"))
                enterUrl = takePictrue.imageUri.getPath();
            else
                headUrl = takePictrue.imageUri.getPath();

            switch (v.getId()) {
                case R.id.takePhotoBtn:
                    //判断是否开户相册权限
                    if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(InforSelfCompanyActivity.this, android.Manifest.permission.CAMERA)) {
                        takePictrue.startCamera();
                    } else {
                        //提示用户开户权限
                        MyToast.show(InforSelfCompanyActivity.this, "请赋予应用相机权限");
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
                        if (imageFlag.equals("head")) {
                            cirHead.setImageBitmap(bitmap);//将图片显示到ImageView上面
                        } else if (imageFlag.equals("license")) {
                            ivLicense.setImageBitmap(bitmap);//将图片显示到ImageView上面
                        } else if (imageFlag.equals("perm")) {
                            ivPermission.setImageBitmap(bitmap);//将图片显示到ImageView上面
                        }
                    }
                }
                break;
            case TakePictrueUtils.PHOTO_NOT_STORE:
                if (null != data) {
                    takePictrue.setPictureToImageView(data, false);
                }
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
            MyToast.show(InforSelfCompanyActivity.this, "请输入企业名称");
            return;
        }
        if (navProject.getTvActionState().equals("")) {
            MyToast.show(InforSelfCompanyActivity.this, "请选择经营项目");
            return;
        }
        if (ivLicense.getDrawable() == null) {
            MyToast.show(InforSelfCompanyActivity.this, "请传入营业执照");
            return;
        }
        if (navCityInfor.getTvActionState().equals("")) {
            MyToast.show(InforSelfCompanyActivity.this, "请选择区域");
            return;
        }
        if (editAdress.getText().toString().equals("")) {
            MyToast.show(InforSelfCompanyActivity.this, "请输入详细地址");
            return;
        }

//        if (ivPermission.getBackground() == null) {
//            MyToast.show(InforSelfCompanyActivity.this, "请传入营业执照");
//            return;
//        }


        Map<String, String> params = new HashMap<>();
        params.put("id", MyShare.getShared().getString("userId", ""));
        params.put("companyname", navName.getTvActionState());
        if (navProject.getTvActionState().equals("石油类"))
            params.put("manage", "1");
        else if (navProject.getTvActionState().equals("化工类"))
            params.put("manage", "2");
        else {
            MyToast.show(InforSelfCompanyActivity.this, "请选择经营项目");
            return;
        }
        params.put("address", navCityInfor.getTvActionState());
        params.put("toaddress", editAdress.getText().toString());
        if (longitude.equals(""))
            longitude = CyApplication.lon;
        if (latitude.equals(""))
            latitude = CyApplication.lat;

        params.put("longitude", longitude);
        params.put("latitude", latitude);
        //头像图片Base64编码上传
        if (!headUrl.equals("")) {
            params.put("face", "data:image/jpg;base64," + toBase64Str(headUrl));
        } else {
//            params.put("face",MyShare.getShared().getString("userHead", ""));
            params.put("face", "");
        }

        //营业执照图片Base64编码上传
        if (!enterUrl.equals("")) {
            params.put("enterprise", "data:image/jpg;base64," + toBase64Str(enterUrl));
        } else {
//            params.put("enterprise",MyShare.getShared().getString("enterprise", ""));
            params.put("enterprise", "");
        }

        //经营许可图片Base64编码上传
        if (!licenseUrl.equals("")) {
            params.put("license", "data:image/jpg;base64," + toBase64Str(licenseUrl));
        } else {
//            params.put("license",MyShare.getShared().getString("license", ""));
            params.put("license", "");
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

    private String toBase64Str(String url) {

        Bitmap bitmap = BitmapFactory.decodeFile(url);
        //第一步:将Bitmap压缩至字节数组输出流ByteArrayOutputStream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        //第二步:利用Base64将字节数组输出流中的数据转换成字符串String
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return new String(Base64.encodeToString(byteArray, Base64.DEFAULT));

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
                                editor.putString("companyname", data.getCompanyname());
                                editor.putString("enterprise", data.getEnterprise());
                                editor.putString("license", data.getLicense());
                                editor.putString("longitude", data.getLongitude());
                                editor.putString("latitude", data.getLatitude());
                                editor.putString("statuss", data.getStatuss());
                                editor.commit();

                                sendBroadcast(new Intent("com.zpf.oillogistics.receiver.DetailsReceiver"));

                                MyToast.show(InforSelfCompanyActivity.this, "提交成功!");
                                startActivity(new Intent(InforSelfCompanyActivity.this, MainActivity.class));
                                finish();

                            } else {
                                MyToast.show(InforSelfCompanyActivity.this, "暂无数据!");
                            }

                        } catch (Exception e) {
                            MyToast.show(InforSelfCompanyActivity.this, "返回数据异常!");
                        }

                    } else {
                        MyToast.show(InforSelfCompanyActivity.this, "返回数据失败!");
                    }
                    break;
            }
        }
    };
}
