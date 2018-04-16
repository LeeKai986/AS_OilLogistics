package com.zpf.oillogistics.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zpf.oillogistics.R;
import com.zpf.oillogistics.base.BaseTakePhotoActivity;
import com.zpf.oillogistics.base.CustomHelper;
import com.zpf.oillogistics.base.CyApplication;
import com.zpf.oillogistics.base.MessageWhat;
import com.zpf.oillogistics.bean.DriverIdenBean;
import com.zpf.oillogistics.bean.response.SelfChangeResponse;
import com.zpf.oillogistics.diy.DiyDialog;
import com.zpf.oillogistics.net.UrlUtil;
import com.zpf.oillogistics.utils.IDCardUtil;
import com.zpf.oillogistics.utils.MyShare;
import com.zpf.oillogistics.utils.MyToast;

import org.devio.takephoto.model.TImage;
import org.devio.takephoto.model.TResult;

import java.io.ByteArrayOutputStream;
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
 * Created by Administrator on 2017/9/19.
 * <p>
 * 司机个人信息确认
 */

public class DriverIdenConfirmActivity extends BaseTakePhotoActivity implements View.OnClickListener {


    //返回
    @BindView(R.id.rel_back_driver)
    RelativeLayout relBack;
    //司机名字
    @BindView(R.id.edit_name_driver)
    EditText editName;
    //司机电话
    @BindView(R.id.edit_IDCard_driver)
    EditText editIDCard;
    //身份证
    @BindView(R.id.iv_IDCard_driver)
    ImageView ivIDCard;
    //第一个下一步
    @BindView(R.id.tv_next1_driver)
    TextView tvNext1;
    //省简称
    @BindView(R.id.tv_provice_driver)
    TextView tvProvice;
    //车牌号
    @BindView(R.id.edit_carNum_driver)
    EditText editCarNum;
    //车辆类型
    @BindView(R.id.tv_carType_driver)
    TextView tvCarType;
    //车辆载重
    @BindView(R.id.edit_carLoad_driver)
    EditText editCarLoad;
    //驾驶证
    @BindView(R.id.iv_driLicence1_driver)
    ImageView ivLicence1;
    //行驶证
    @BindView(R.id.iv_licence2_driver)
    ImageView ivLicence2;
    //运营证
    @BindView(R.id.iv_licence3_driver)
    ImageView ivLicence3;
    //押运证
    @BindView(R.id.iv_licence4_driver)
    ImageView ivLicence4;
    //第二个下一步
    @BindView(R.id.tv_next2_driver)
    TextView tvNext2;
    //侧滑lv
    @BindView(R.id.driver_iden_confirm_lv)
    ListView lvConfirm;
    @BindView(R.id.lin_confirm_driver)
    LinearLayout linConfirm;
    //第一步界面
    @BindView(R.id.lin_contant1_driver)
    LinearLayout linContant1;
    //第二部界面
    @BindView(R.id.lin_contant2_driver)
    LinearLayout linContant2;
    //类型
    @BindView(R.id.rel_type_driver)
    RelativeLayout relType;
    @BindView(R.id.driver_iden_confirm_fl)
    FrameLayout fl;

    //图片标签
    private String imgFlag = "";
    //身份证base64
    private String IDCardStr = "";
    //驾驶证base64
    private String jsStr = "";
    //行驶证base64
    private String xsStr = "";
    //运营证base64
    private String yunyingStr = "";
    //押运证base64
    private String yayunStr = "";

    // 已有图片判断
    //身份证base64
    private String IDCardHaveStr = "";
    //驾驶证base64
    private String jsHaveStr = "";
    //行驶证base64
    private String xsHaveStr = "";
    //运营证base64
    private String yunyingHaveStr = "";
    //押运证base64
    private String yayunHaveStr = "";

    //省级单位简称
    private String[] pro = {"京", "津", "沪", "渝", "宁", "藏", "桂", "新", "蒙", "港", "澳", "黑", "云", "吉", "皖", "鲁", "晋", "粤", "桂", "苏", "赣", "冀", "豫", "浙", "琼", "鄂", "湘", "甘", "闽", "川", "黔", "贵", "辽", "陕", "青", "台"};
    private String[] type = {"第一类", "第二类", "第三类", "第四类", "第五类", "第六类", "第七类", "第八类", "第九类"};
    private boolean isType = false;
    private String proStr = "京";

    Map<String, String> params;
    private CustomHelper customHelper;

    @Override
    protected int setLayout() {
        return R.layout.activity_driver_iden_confirm;
    }

    @Override
    protected void initData() {
        params = new HashMap<>();
        params.put("id", MyShare.getShared().getString("userId", ""));
        inner_postAsync();
    }

    @Override
    protected void initViewAction(Bundle savedInstanceState) {
        relBack.setOnClickListener(this);
        tvNext1.setOnClickListener(this);
        ivLicence1.setOnClickListener(this);
        ivLicence2.setOnClickListener(this);
        ivLicence3.setOnClickListener(this);
        ivLicence4.setOnClickListener(this);
        ivIDCard.setOnClickListener(this);
        tvNext2.setOnClickListener(this);
        tvProvice.setOnClickListener(this);
        relType.setOnClickListener(this);
        fl.setOnClickListener(this);
        customHelper = CustomHelper.init();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rel_back_driver:

                if (linContant1.getVisibility() == View.VISIBLE)
                    finish();
                else {
                    linContant1.setVisibility(View.VISIBLE);
                    linContant2.setVisibility(View.GONE);
                }

                break;
            case R.id.iv_IDCard_driver:
                imgFlag = "IDCard";
                takePic();
                break;
            case R.id.tv_next1_driver:
                linContant1.setVisibility(View.GONE);
                linContant2.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_next2_driver:
                if (editName.getText().toString().equals("")) {
                    MyToast.show(DriverIdenConfirmActivity.this, "请输入姓名");
                    break;
                }
                if (editIDCard.getText().toString().equals("")) {
                    MyToast.show(DriverIdenConfirmActivity.this, "请输入身份证号码");
                    break;
                }
                if (!IDCardUtil.isIdCard(editIDCard.getText().toString())) {
                    break;
                }
                if (IDCardStr.equals("") && IDCardHaveStr.equals("")) {
                    MyToast.show(DriverIdenConfirmActivity.this, "请上传身份证");
                    break;
                }
                if (tvProvice.getText().toString().equals("") || tvProvice.getText().toString().equals("0")) {
                    MyToast.show(DriverIdenConfirmActivity.this, "请选择车牌所属省份");
                    break;
                }
                if (editCarNum.getText().toString().equals("")) {
                    MyToast.show(DriverIdenConfirmActivity.this, "请输入车牌号");
                    break;
                }
                if (tvCarType.getText().toString().equals("")) {
                    MyToast.show(DriverIdenConfirmActivity.this, "请选择车辆类型");
                    break;
                }
                if (editCarLoad.getText().toString().equals("")) {
                    MyToast.show(DriverIdenConfirmActivity.this, "请输入车辆载重");
                    break;
                }
                if (jsStr.equals("") && jsHaveStr.equals("")) {
                    MyToast.show(DriverIdenConfirmActivity.this, "请上传驾驶证");
                    break;
                }
                if (xsStr.equals("") && xsHaveStr.equals("")) {
                    MyToast.show(DriverIdenConfirmActivity.this, "请上传行驶证");
                    break;
                }
//                if (yunyingStr.equals("")) {
//                    MyToast.show(DriverIdenConfirmActivity.this, "请上传运营证");
//                    break;
//                }
//                if (yayunStr.equals("")) {
//                    MyToast.show(DriverIdenConfirmActivity.this, "请上传押运证");
//                    break;
//                }
                params = new HashMap<>();
                params.put("id", MyShare.getShared().getString("userId", ""));
                params.put("relname", editName.getText().toString());
                params.put("card", editIDCard.getText().toString());
                params.put("img", IDCardStr);
                params.put("driverpic", jsStr);
                params.put("runpic", xsStr);
                params.put("operatepic", yunyingStr);
                params.put("supercargopic", yayunStr);
                params.put("cartcode", tvProvice.getText().toString() + editCarNum.getText().toString());
                params.put("car_type", tvCarType.getText().toString());
                params.put("load", editCarLoad.getText().toString());
                params.put("type", "1");
                inner_postAsync();
                break;
            case R.id.iv_driLicence1_driver:
                imgFlag = "jiashi";
                takePic();
                break;
            case R.id.iv_licence2_driver:
                imgFlag = "xingsshi";
                takePic();
                break;
            case R.id.iv_licence3_driver:
                imgFlag = "yunying";
                takePic();
                break;
            case R.id.iv_licence4_driver:
                imgFlag = "yayun";
                takePic();
                break;
            case R.id.tv_provice_driver:
                linConfirm.setVisibility(View.VISIBLE);
                lvConfirm.setAdapter(new MyAdapter(pro));
                lvConfirm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        tvProvice.setText(pro[i]);
                        linConfirm.setVisibility(View.GONE);
                        proStr = pro[i];
                    }
                });
                break;
            case R.id.rel_type_driver:
                linConfirm.setVisibility(View.VISIBLE);
                lvConfirm.setAdapter(new MyAdapter(type));
                lvConfirm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        tvCarType.setText(type[i]);
                        linConfirm.setVisibility(View.GONE);
                        proStr = type[i];
                    }
                });
                break;
            case R.id.driver_iden_confirm_fl:
                linConfirm.setVisibility(View.GONE);
                break;
        }
    }

    private void takePic() {
        List<String> updataTypes = new ArrayList<>();
        updataTypes.add("拍照");
        updataTypes.add("从手机相册选择");
        DiyDialog.singleSelectDialog(DriverIdenConfirmActivity.this, updataTypes, new DiyDialog.SingleSelectListener() {
            @Override
            public void SingleSelect(String res) {
                if (res.equals("从手机相册选择")) {
                    customHelper.onClick(getTakePhoto(), 1, 9, 5);
                } else {
                    //判断是否开户相册权限
                    if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(DriverIdenConfirmActivity.this, android.Manifest.permission.CAMERA)) {
                        customHelper.onClick(getTakePhoto(), 2, 9, 5);
                    } else {
                        //提示用户开户权限
                        MyToast.show(DriverIdenConfirmActivity.this, "请赋予应用相机权限");
                    }
                }
            }
        });
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        final ArrayList<TImage> images = result.getImages();
        if (images != null && images.size() > 0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Bitmap bitmap = BitmapFactory.decodeFile(images.get(0).getCompressPath());//filePath
                    if (bitmap != null) {
                        if (imgFlag.equals("IDCard")) {
                            IDCardStr = "data:image/jpg;base64," + bitmaptoString(bitmap);
                            ivIDCard.setImageBitmap(bitmap);//将图片显示到ImageView上面
                        }
                        if (imgFlag.equals("jiashi")) {
                            jsStr = "data:image/jpg;base64," + bitmaptoString(bitmap);
                            ivLicence1.setImageBitmap(bitmap);//将图片显示到ImageView上面
                        }
                        if (imgFlag.equals("xingsshi")) {
                            xsStr = "data:image/jpg;base64," + bitmaptoString(bitmap);
                            ivLicence2.setImageBitmap(bitmap);//将图片显示到ImageView上面
                        }
                        if (imgFlag.equals("yunying")) {
                            yunyingStr = "data:image/jpg;base64," + bitmaptoString(bitmap);
                            ivLicence3.setImageBitmap(bitmap);//将图片显示到ImageView上面
                        }
                        if (imgFlag.equals("yayun")) {
                            yayunStr = "data:image/jpg;base64," + bitmaptoString(bitmap);
                            ivLicence4.setImageBitmap(bitmap);//将图片显示到ImageView上面
                        }
                    }
                }
            });
        }
    }

    /**
     * Bitmap转码
     *
     * @return
     */

    public String bitmaptoString(Bitmap bitmap) {
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        if (bitmap != null) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bStream);
            byte[] bytes = bStream.toByteArray();
            return Base64.encodeToString(bytes, Base64.DEFAULT);
        }
        return null;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//            case TakePictrueUtils.PHOTO_CAMERA:
//                //表示从相机获得的照片，需要进行裁剪
//                //表示从相机获得的照片，需要进行裁剪
//                // 由于可以调起多个相机先进行文件大小确认
//                if (takePictrue.tempFile.exists()) {
//                    try {
//                        if (new FileInputStream(takePictrue.tempFile).available() != 0) {
//                            takePictrue.startPhotoCut(takePictrue.imageUri, 720, 400, true);
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//                break;
//            case TakePictrueUtils.PHOTO_WALL:
//                if (null != data) {
//                    takePictrue.startPhotoCut(data.getData(), 720, 400, true);
//                }
//                break;
//            case TakePictrueUtils.PHOTO_STORE:
//                if (null != data) {
//                    Bitmap bitmap = takePictrue.setPictureToImageView(data, true);
//                    if (bitmap != null) {
//                        if (imgFlag.equals("IDCard")) {
//                            IDCardStr = "data:image/jpg;base64," + takePictrue.bitmaptoString();
//                            ivIDCard.setImageBitmap(bitmap);//将图片显示到ImageView上面
//                        }
//                        if (imgFlag.equals("jiashi")) {
//                            jsStr = "data:image/jpg;base64," + takePictrue.bitmaptoString();
//                            ivLicence1.setImageBitmap(bitmap);//将图片显示到ImageView上面
//                        }
//                        if (imgFlag.equals("xingsshi")) {
//                            xsStr = "data:image/jpg;base64," + takePictrue.bitmaptoString();
//                            ivLicence2.setImageBitmap(bitmap);//将图片显示到ImageView上面
//                        }
//                        if (imgFlag.equals("yunying")) {
//                            yunyingStr = "data:image/jpg;base64," + takePictrue.bitmaptoString();
//                            ivLicence3.setImageBitmap(bitmap);//将图片显示到ImageView上面
//                        }
//                        if (imgFlag.equals("yayun")) {
//                            yayunStr = "data:image/jpg;base64," + takePictrue.bitmaptoString();
//                            ivLicence4.setImageBitmap(bitmap);//将图片显示到ImageView上面
//                        }
//                    }
//                }
//                break;
//            case TakePictrueUtils.PHOTO_NOT_STORE:
//                if (null != data) {
//                    takePictrue.setPictureToImageView(data, false);
//                }
//                break;
//            default:
//                break;
//        }
    }


    /**
     * 修改信息
     */
    private void inner_postAsync() {
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
        final Request request = new Request.Builder().url(UrlUtil.URL_DRIVIER_IDENTITY).post(requestBody).build();
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
                message.what = MessageWhat.SELF_DRIVIER_IDENTITY;
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
                case MessageWhat.SELF_DRIVIER_IDENTITY:
                    if (msg.obj != null) {
                        if (params.size() == 1) {
                            DriverIdenBean driverIdenBean = new Gson().fromJson(msg.obj.toString(), DriverIdenBean.class);
                            if (driverIdenBean != null && driverIdenBean.getData() != null) {
                                editName.setText(driverIdenBean.getData().getRelname());
                                editIDCard.setText(driverIdenBean.getData().getCard());
                                if (!driverIdenBean.getData().getImg().equals("0") &&
                                        !driverIdenBean.getData().getImg().equals("")) {
                                    IDCardHaveStr = "1";
                                    Glide.with(DriverIdenConfirmActivity.this)
                                            .load(UrlUtil.IMAGE_URL + driverIdenBean.getData().getImg())
                                            .error(R.mipmap.shenfenzheng)
                                            .into(ivIDCard);
                                }
                                if (!driverIdenBean.getData().getDriverpic().equals("0") &&
                                        !driverIdenBean.getData().getDriverpic().equals("")) {
                                    jsHaveStr = "1";
                                    Glide.with(DriverIdenConfirmActivity.this)
                                            .load(UrlUtil.IMAGE_URL + driverIdenBean.getData().getDriverpic())
                                            .error(R.mipmap.shenfenzheng)
                                            .into(ivLicence1);
                                }
                                if (!driverIdenBean.getData().getRunpic().equals("0") &&
                                        !driverIdenBean.getData().getRunpic().equals("")) {
                                    xsHaveStr = "1";
                                    Glide.with(DriverIdenConfirmActivity.this)
                                            .load(UrlUtil.IMAGE_URL + driverIdenBean.getData().getRunpic())
                                            .error(R.mipmap.shenfenzheng)
                                            .into(ivLicence2);
                                }
                                if (!driverIdenBean.getData().getOperatepic().equals("0") &&
                                        !driverIdenBean.getData().getOperatepic().equals("")) {
                                    Glide.with(DriverIdenConfirmActivity.this)
                                            .load(UrlUtil.IMAGE_URL + driverIdenBean.getData().getOperatepic())
                                            .error(R.mipmap.shenfenzheng)
                                            .into(ivLicence3);
                                }
                                if (!driverIdenBean.getData().getSupercargopic().equals("0") &&
                                        !driverIdenBean.getData().getSupercargopic().equals("")) {
                                    Glide.with(DriverIdenConfirmActivity.this)
                                            .load(UrlUtil.IMAGE_URL + driverIdenBean.getData().getSupercargopic())
                                            .error(R.mipmap.shenfenzheng)
                                            .into(ivLicence4);
                                }
                                tvProvice.setText(driverIdenBean.getData().getCartcode().substring(0, 1));
                                editCarNum.setText(driverIdenBean.getData().getCartcode().substring(1, driverIdenBean.getData().getCartcode().length()));
                                tvCarType.setText(driverIdenBean.getData().getCar_type());
                                editCarLoad.setText(driverIdenBean.getData().getLoad());
                            }
                        } else {

                            try {
                                SelfChangeResponse change = gson.fromJson(msg.obj.toString(), SelfChangeResponse.class);
                                if (change.getStatus() == 0) {

                                    MyToast.show(DriverIdenConfirmActivity.this, "提交验证成功,请重新登陆");
                                    MyShare.getShared().edit().clear().commit();
                                    CyApplication.province = "";
                                    CyApplication.area = "";
                                    CyApplication.adress = "";
                                    CyApplication.lat = "";
                                    CyApplication.lon = "";
                                    Intent intent = new Intent(DriverIdenConfirmActivity.this, LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);

                                } else {
                                    MyToast.show(DriverIdenConfirmActivity.this, "暂无数据!");
                                }

                            } catch (Exception e) {
                                MyToast.show(DriverIdenConfirmActivity.this, "返回数据异常!");
                            }
                        }
                    } else {
                        MyToast.show(DriverIdenConfirmActivity.this, "返回数据失败!");
                    }
                    break;
            }
        }
    };

    private String getNumber(String number) {
        String fStr = "";
        switch (Integer.parseInt(number)) {
            case 1:
                fStr = "第一类";
                break;
            case 2:
                fStr = "第二类";
                break;
            case 3:
                fStr = "第三类";
                break;
            case 4:
                fStr = "第四类";
                break;
            case 5:
                fStr = "第五类";
                break;
            case 6:
                fStr = "第六类";
                break;
            case 7:
                fStr = "第七类";
                break;
            case 8:
                fStr = "第八类";
                break;
            case 9:
                fStr = "第九类";
                break;
            default:
                fStr = "请选择类型";
                break;
        }
        return fStr;
    }


    class MyAdapter extends BaseAdapter {

        String[] ss;
        LayoutInflater inflater;

        public MyAdapter(String[] ss) {
            this.ss = ss;
            inflater = LayoutInflater.from(DriverIdenConfirmActivity.this);
        }

        @Override
        public int getCount() {
            return ss.length;
        }

        @Override
        public Object getItem(int i) {
            return ss[i];
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder = null;
            if (view == null) {
                holder = new ViewHolder();
                view = inflater.inflate(R.layout.single_tv_item, viewGroup, false);
                holder.tv = view.findViewById(R.id.tv_single);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            holder.tv.setText(ss[i]);

            if (proStr.equals(ss[i])) {
                holder.tv.setTextColor(Color.parseColor("#FC4F4F"));
            } else
                holder.tv.setTextColor(Color.parseColor("#7B7B7B"));
            return view;
        }

        class ViewHolder {
            TextView tv;
        }
    }

}