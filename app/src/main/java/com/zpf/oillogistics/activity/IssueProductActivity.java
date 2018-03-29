package com.zpf.oillogistics.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dream.life.library.throwable.utils.CheckUtil;
import com.google.gson.Gson;
import com.lljjcoder.city_20170724.CityPickerView;
import com.lljjcoder.city_20170724.bean.CityBean;
import com.lljjcoder.city_20170724.bean.DistrictBean;
import com.lljjcoder.city_20170724.bean.ProvinceBean;
import com.zpf.oillogistics.R;
import com.zpf.oillogistics.base.BaseActivity;
import com.zpf.oillogistics.base.MessageWhat;
import com.zpf.oillogistics.bean.response.IndexResponse;
import com.zpf.oillogistics.bean.response.OilClassResponse;
import com.zpf.oillogistics.bean.response.ProductClassResponsse;
import com.zpf.oillogistics.diy.DiyDialog;
import com.zpf.oillogistics.diy.DiyToast;
import com.zpf.oillogistics.diy.PickerView;
import com.zpf.oillogistics.net.SimplifyThread;
import com.zpf.oillogistics.net.UrlUtil;
import com.zpf.oillogistics.utils.MyShare;
import com.zpf.oillogistics.utils.MyToast;
import com.zpf.oillogistics.utils.TakePictrueUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/9/14.
 * <p>
 * 发产品
 */

public class IssueProductActivity extends BaseActivity {

    // 布局相关
//    // 大分类
//    @BindView(R.id.issue_product_big_type_rl)
//    RelativeLayout bigTypeRl;
    // 分类
    @BindView(R.id.issue_product_type_rl)
    RelativeLayout typeRl;
    // 提货地点
    @BindView(R.id.issue_product_get_product_distance_rl)
    RelativeLayout getProductDistanceRl;
    // 图片上传
    @BindView(R.id.issue_product_updata_image_rl)
    RelativeLayout updataImageRl;
    // 提交
    @BindView(R.id.issue_product_sub_tv)
    TextView subTv;
    // 返回
    @BindView(R.id.rel_back_issue_product)
    RelativeLayout relBack;
    // 产品类型
    @BindView(R.id.tv_type_issue_product)
    TextView tvType;
    // 填写标题
    @BindView(R.id.edit_title_issue_product)
    EditText editTitle;
    // 填写价格
    @BindView(R.id.edit_price_issue_product)
    EditText editPrice;
    // 填写区域
    @BindView(R.id.tv_area_issue_product)
    TextView tvArea;
    // 填写详细地址
    @BindView(R.id.edit_adress_issue_product)
    EditText editAdress;
    // 图片
    @BindView(R.id.iv_pic_issue_product)
    ImageView ivPic;
    @BindView(R.id.tv_pic_issue_product)
    TextView tvPic;
    //说明
    @BindView(R.id.edit_marker_issue_product)
    EditText editMarker;

    HashMap<String, String> subHp = new HashMap<>();
    ArrayList<OilClassResponse.DataBean> oilList = new ArrayList<>();
    TakePictrueUtils takePictrue;
    List<ProductClassResponsse.DataBean> firstList;
    List<ProductClassResponsse.DataBean.ChildBean> secondList;

    private String adressArea = "";
    private String typeStr = "";


    @Override
    protected int setLayout() {
        return R.layout.activity_issue_product;
    }

    @Override
    protected void initData() {


        //加载地址
        if (MyShare.getShared().getString("province", "") != null) {
            adressArea = MyShare.getShared().getString("province", "");
            tvArea.setText(MyShare.getShared().getString("province", ""));
        }

        //加载市
        if (!MyShare.getShared().getString("city", "").equals("")) {
            tvArea.setText(MyShare.getShared().getString("province", "") +
                    MyShare.getShared().getString("city", ""));
            adressArea += ("-" + MyShare.getShared().getString("city", ""));
        }

        //加载详情地址
        if (MyShare.getShared().getString("toaddress", "") != null) {
            editAdress.setText(MyShare.getShared().getString("toaddress", ""));
        }

        goodsData();
    }

    @Override
    protected void initViewAction(Bundle savedInstanceState) {
        relBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

//        bigTypeRl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                List<String> list = new ArrayList<String>();
//                list.add("石油类");
//                list.add("化工类");
//                DiyDialog.singleSelectDialog(IssueProductActivity.this, list, new DiyDialog.SingleSelectListener() {
//                    @Override
//                    public void SingleSelect(String res) {
//                        bigTypeTv.setText(res);
//                    }
//                });
//            }
//        });

        typeRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tweSelectDialog(IssueProductActivity.this, new DiyDialog.TweSelectListener() {
                    @Override
                    public void tweSelect(String res) {
                        typeStr = res;
                        if (res.contains("-无")) {
                            MyToast.show(IssueProductActivity.this, "此产品类型无具体类型, 请重新选择");
                            return;
                        }
                        tvType.setText(res.replace("-", ""));
                    }
                });
            }
        });

        getProductDistanceRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CityPickerView cityPicker = new CityPickerView.Builder(IssueProductActivity.this)
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
                        tvArea.setText(province.getName() + city.getName() + district.getName());
                    }

                    @Override
                    public void onCancel() {
                    }
                });
            }
        });

        updataImageRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<String> updataTypes = new ArrayList<>();
                updataTypes.add("拍照");
                updataTypes.add("从手机相册选择");
                DiyDialog.singleSelectDialog(IssueProductActivity.this, updataTypes, new DiyDialog.SingleSelectListener() {
                    @Override
                    public void SingleSelect(String res) {
                        takePictrue = new TakePictrueUtils(IssueProductActivity.this, "product");
                        if (res.equals("从手机相册选择")) {
                            takePictrue.startWall();
                        } else {
                            //判断是否开户相册权限
                            if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(IssueProductActivity.this, android.Manifest.permission.CAMERA)) {
                                takePictrue.startCamera();
                            } else {
                                //提示用户开户权限
                                MyToast.show(IssueProductActivity.this, "请赋予应用相机权限");
                            }
//                            takePictrue.startCamera();
                        }
                    }
                });
            }
        });

        subTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toSubmit();
            }
        });
    }

    /**
     * 产品分类
     */
    private void goodsData() {
        SimplifyThread simplifyThread = new SimplifyThread(UrlUtil.URL_PRODUCT_GOODSCLASS, new HashMap<String, String>());
        simplifyThread.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                Message message = new Message();
                message.obj = res;
                message.what = 3;
                handler.sendMessage(message);
            }

            @Override
            public void errorBody(String error) {
                handler.sendEmptyMessage(MessageWhat.REQUST_ERROR);
            }
        });
    }

    /**
     * 产品分类
     */
    private void toSubmit() {

        subHp.put("class", MyShare.getShared().getString("manage", ""));

        if (typeStr == null || typeStr.equals("")) {
            MyToast.show(IssueProductActivity.this, "请选择类型");
            return;
        }

        String[] type = typeStr.split("-");
        if (type.length == 2) {
            subHp.put("classify", type[0]);
            if (!CheckUtil.isNull(type[1])) {
                subHp.put("model", type[1]);
            }
        } else {
            subHp.put("classify", type[0]);
            subHp.put("model", "");
        }
        if (!editTitle.getText().toString().equals("")) {
            subHp.put("title", editTitle.getText().toString());
        } else {
            MyToast.show(IssueProductActivity.this, "请输入标题");
            return;
        }

        if (!editPrice.getText().toString().equals("")) {
            subHp.put("price", editPrice.getText().toString());
        } else {
            MyToast.show(IssueProductActivity.this, "请输入价格");
            return;
        }

        if (!editAdress.getText().toString().equals("")) {
            subHp.put("address", editAdress.getText().toString());
        } else {
            MyToast.show(IssueProductActivity.this, "请输入提货详细地址");
            return;
        }

        if (!adressArea.equals("")) {
            subHp.put("takeaddress", adressArea);
        } else {
            MyToast.show(IssueProductActivity.this, "请选择提货区域");
            return;
        }

        if (ivPic.getDrawable() == null) {
            MyToast.show(IssueProductActivity.this, "请上传产品图片");
            return;
        }

        subHp.put("uid", MyShare.getShared().getString("userId", ""));
        subHp.put("img", "");
        if (takePictrue != null) {
            String imageString = takePictrue.bitmaptoString();
            if (imageString != null)
                subHp.put("img", "data:image/jpg;base64," + imageString);
        }
        subHp.put("intruduce", editMarker.getText().toString());

        /**
         * 3.0之后版本
         */
        FormBody.Builder builder = new FormBody.Builder();

        /**
         * 在这对添加的参数进行遍历，map遍历有四种方式，如果想要了解的可以网上查找
         */
        for (Map.Entry<String, String> map : subHp.entrySet()) {
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
        RequestBody requestBody = builder.build();
        // 请求对象
        final Request request = new Request.Builder()
                .url(UrlUtil.URL_PLUSSIGN_TOGOODS)
                .post(requestBody).build();
        OkHttpClient mClient = new OkHttpClient();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(MessageWhat.REQUST_ERROR);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String ss = response.body().string();
                Message message = new Message();
                message.obj = ss;
                message.what = MessageWhat.PLUSSIGN_TOGOODS;
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
                case MessageWhat.PRODUCT_GOODSCLASS:
                    if (msg.obj != null) {
                        try {
                            OilClassResponse oil = gson.fromJson(msg.obj.toString(), OilClassResponse.class);

                            if (oil.getStatus() == 0) {
                                oilList = (ArrayList<OilClassResponse.DataBean>) oil.getData();
                            } else {
                                MyToast.show(IssueProductActivity.this, "暂无数据!");
                            }

                        } catch (Exception e) {
                            MyToast.show(IssueProductActivity.this, "返回数据异常!");
                        }

                    } else {
                        MyToast.show(IssueProductActivity.this, "返回数据失败!");
                    }
                    break;
                case MessageWhat.PLUSSIGN_TOGOODS:
                    if (msg.obj != null) {
                        try {
                            IndexResponse index = gson.fromJson(msg.obj.toString(), IndexResponse.class);

                            if (index.getStatus() == 0) {
                                DiyToast.centerToast(IssueProductActivity.this, "发布成功");
                                finish();
                            } else {
                                MyToast.show(IssueProductActivity.this, "发布失败!");
                            }

                        } catch (Exception e) {
                            MyToast.show(IssueProductActivity.this, "返回数据异常!");
                        }

                    } else {
                        MyToast.show(IssueProductActivity.this, "返回数据失败!");
                    }
                    break;
                case 3:
                    if (msg.obj != null) {
                        try {
                            ProductClassResponsse product = new Gson().fromJson(msg.obj.toString(), ProductClassResponsse.class);
                            if (product.getStatus() == 0) {
                                firstList = product.getData();
                            } else {
                                MyToast.show(IssueProductActivity.this, "暂无产品分类数据!");
                            }

                        } catch (Exception e) {
                            MyToast.show(IssueProductActivity.this, "返回数据异常!");
                        }

                    } else {
                        MyToast.show(IssueProductActivity.this, "返回数据失败!");
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }


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
                        tvPic.setVisibility(View.GONE);
                        ivPic.setImageBitmap(bitmap);//将图片显示到ImageView上面
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


    public void tweSelectDialog(final Context context, final DiyDialog.TweSelectListener tweSelectListener) {
        final Dialog dialog = new Dialog(context, R.style.diy_dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_product_type, null);
        FrameLayout fl = view.findViewById(R.id.product_fl);
        TextView cancelTv = view.findViewById(R.id.product_cancel_tv);
        TextView confirmTv = view.findViewById(R.id.product_confirm_tv);
        PickerView onePv = view.findViewById(R.id.product_one_pv);
        final PickerView twePv = view.findViewById(R.id.product_twe_pv);
        dialog.setContentView(view);
        final String[] typeArr = {"", ""};

        List<String> oneTypes = new ArrayList<>();
        List<String> firstTweTypes = new ArrayList<>();
        for (int i = 0; firstList != null && i < firstList.size(); i++) {
            oneTypes.add(firstList.get(i).getName());
        }

        for (int k = 0; firstList != null && k < firstList.size(); k++) {
            secondList = firstList.get(k).getChild();
            if (secondList != null) {
                for (int l = 0; secondList != null && l < secondList.size(); l++) {
                    firstTweTypes.add(secondList.get(l).getName());
                }
            }
        }
//        if (firstList != null && firstList.size() != 0) {
//            secondList = firstList.get(0).getChild();
//            if (secondList != null) {
//                for (int l = 0; secondList != null && l < secondList.size(); l++) {
//                    firstTweTypes.add(secondList.get(l).getName());
//                }
//            }
//        }

        onePv.setData(oneTypes);
        twePv.setData(firstTweTypes);

        fl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        onePv.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                typeArr[0] = text;
                for (int i = 0; firstList != null && i < firstList.size(); i++) {
                    if (text.equals(firstList.get(i).getName())) {

                        secondList = firstList.get(i).getChild();
                        List<String> tweTypes = new ArrayList<>();
                        for (int l = 0; secondList != null && l < secondList.size(); l++) {
                            tweTypes.add(secondList.get(l).getName());
                        }
                        if (tweTypes.size() != 0) {
                            twePv.setData(tweTypes);
                            typeArr[1] = tweTypes.get(0);
                        } else {
                            tweTypes.add("");
                            twePv.setData(tweTypes);
                            typeArr[1] = tweTypes.get(0);
                        }
                    }
                }

            }
        });
        twePv.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                typeArr[1] = text;

            }
        });
        confirmTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (typeArr[1] == null || typeArr[1].equals("")) {
//                    MyToast.show(IssueProductActivity.this, "产品分类未选择具体类型,请重新选择");
//                    return;
//                }
                tweSelectListener.tweSelect(typeArr[0] + "-" + typeArr[1]);
                dialog.dismiss();
            }
        });

        if (oneTypes != null && !oneTypes.isEmpty()) {
            setData(typeArr, oneTypes.get(0), twePv);
        }
        dialog.show();
        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (int) (display.getWidth()); //设置宽度
        dialog.getWindow().setAttributes(lp);

    }


    private void setData(String[] typeArr, String text, PickerView twePv) {
        typeArr[0] = text;
        for (int i = 0; firstList != null && i < firstList.size(); i++) {
            if (text.equals(firstList.get(i).getName())) {
                secondList = firstList.get(i).getChild();
                List<String> tweTypes = new ArrayList<>();
                for (int l = 0; secondList != null && l < secondList.size(); l++) {
                    tweTypes.add(secondList.get(l).getName());
                }
                if (tweTypes.size() != 0) {
                    twePv.setData(tweTypes);
                    typeArr[1] = tweTypes.get(0);
                } else {
                    tweTypes.add("");
                    twePv.setData(tweTypes);
                    typeArr[1] = tweTypes.get(0);
                }
            }
        }
    }
}
