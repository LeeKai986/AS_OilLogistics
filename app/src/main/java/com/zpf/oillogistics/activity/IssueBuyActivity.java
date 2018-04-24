package com.zpf.oillogistics.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zpf.oillogistics.R;
import com.zpf.oillogistics.base.BaseTakePhotoActivity;
import com.zpf.oillogistics.base.CustomHelper;
import com.zpf.oillogistics.base.MessageWhat;
import com.zpf.oillogistics.bean.response.IndexResponse;
import com.zpf.oillogistics.diy.DiyDialog;
import com.zpf.oillogistics.net.UrlUtil;
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
 * Created by Administrator on 2017/9/14.
 * <p>
 * 发布求购
 */

public class IssueBuyActivity extends BaseTakePhotoActivity {

    // 布局相关
    // 产品图片
    @BindView(R.id.edit_title_issue_buy)
    EditText editTitle;
    @BindView(R.id.edit_num_issue_buy)
    EditText editNum;
    @BindView(R.id.edit_companyName_issue_buy)
    EditText editCompanyName;
    @BindView(R.id.edit_companyAdress_issue_buy)
    EditText editCompanyAdress;
    @BindView(R.id.edit_phone_issue_buy)
    EditText editPhone;
    @BindView(R.id.tv_pic_issue_buy)
    TextView tvPic;
    @BindView(R.id.iv_pic_issue_buy)
    ImageView ivPic;
    @BindView(R.id.issue_buy_updata_image_rl)
    RelativeLayout updataImageRl;
    @BindView(R.id.edit_marker_issue_buy)
    EditText editMarker;
    @BindView(R.id.issue_buy_sub_tv)
    TextView tvSub;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.tv_adress)
    TextView tvAdress;
    @BindView(R.id.rel_back_issue_buy)
    RelativeLayout relBack;

    // 公司名称
    @BindView(R.id.ll_companyName_issue_buy)
    LinearLayout companyNameLl;
    // 公司地址
    @BindView(R.id.ll_companyAdress_issue_buy)
    LinearLayout companyAdressLl;

    HashMap<String, String> subHp = new HashMap<>();
    private String adressArea = "";
    private CustomHelper customHelper;
    private String imageString;


    @Override
    protected int setLayout() {
        return R.layout.activity_issue_buy;
    }

    @Override
    protected void initData() {
        if (!MyShare.getShared().getString("userType", "1").equals("2")) {
            tvCompany.setText("联 系 人");
            tvAdress.setText("联系地址");
        }


        if (!MyShare.getShared().getString("userPhone", "").equals("")) {
            editPhone.setText(MyShare.getShared().getString("userPhone", ""));
        }

        //加载昵称
        if (MyShare.getShared().getString("userType", "").equals("1")) {
            //加载昵称
            if (MyShare.getShared().getString("relname", "") != null) {
                editCompanyName.setText(MyShare.getShared().getString("relname", ""));
            }
        } else {

            if (MyShare.getShared().getString("companyname", "") != null) {
                editCompanyName.setText(MyShare.getShared().getString("companyname", ""));
            }
        }

        //加载地址
        if (MyShare.getShared().getString("province", "") != null) {
            String ss = MyShare.getShared().getString("province", "");
            //加载详情地址
            if (MyShare.getShared().getString("toaddress", "") != null) {
                ss += MyShare.getShared().getString("toaddress", "");
            }
            editCompanyAdress.setText(ss);
        }

        //加载地址
        if (MyShare.getShared().getString("province", "") != null && !MyShare.getShared().getString("province", "").equals("")) {
            adressArea = MyShare.getShared().getString("province", "");
            editCompanyAdress.setText(MyShare.getShared().getString("province", ""));
        }

        //加载市
        if (MyShare.getShared().getString("city", "") != null && !MyShare.getShared().getString("city", "").equals("")) {
            editCompanyAdress.setText(MyShare.getShared().getString("province", "") +
                    MyShare.getShared().getString("city", ""));
            adressArea += ("-" + MyShare.getShared().getString("city", ""));
        }
        //加载区
        if (MyShare.getShared().getString("area", "") != null && !MyShare.getShared().getString("area", "").equals("")) {
            editCompanyAdress.setText(MyShare.getShared().getString("province", "") +
                    MyShare.getShared().getString("city", "") + MyShare.getShared().getString("area", ""));
            adressArea += "-" + MyShare.getShared().getString("area", "");
        }
        String adressArea2 = adressArea.replace("null", "");
        //加载详情地址
        if (MyShare.getShared().getString("toaddress", "") != null) {
            editCompanyAdress.setText(adressArea2.replace("-", "") + MyShare.getShared().getString("toaddress", ""));
        }

    }

    @Override
    protected void initViewAction(Bundle savedInstanceState) {
        customHelper = CustomHelper.init();
        relBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tvSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvSub.setClickable(false);
                toSubmit();
            }
        });
        updataImageRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> updataTypes = new ArrayList<>();
                updataTypes.add("拍照");
                updataTypes.add("从手机相册选择");
                DiyDialog.singleSelectDialog(IssueBuyActivity.this, updataTypes, new DiyDialog.SingleSelectListener() {
                    @Override
                    public void SingleSelect(String res) {
                        if (res.equals("从手机相册选择")) {
                            customHelper.onClick(getTakePhoto(), 1, 1, 1);
                        } else {
                            customHelper.onClick(getTakePhoto(), 2, 1, 1);
                        }
                    }
                });
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
                    tvPic.setVisibility(View.GONE);
                    Bitmap bitmap = BitmapFactory.decodeFile(images.get(0).getCompressPath());//filePath
                    ivPic.setImageBitmap(bitmap);
                    imageString = bitmaptoString(bitmap);
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    private Gson gson = new Gson();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MessageWhat.PLUSSIGN_TOBUY:

                    if (msg.obj != null) {
                        try {
                            IndexResponse index = gson.fromJson(msg.obj.toString(), IndexResponse.class);

                            if (index.getStatus() == 0) {
                                MyToast.show(IssueBuyActivity.this, "发布成功!");
                                finish();
                            } else {
                                MyToast.show(IssueBuyActivity.this, index.getMsg());
                                tvSub.setClickable(true);
                            }

                        } catch (Exception e) {
                            MyToast.show(IssueBuyActivity.this, "返回数据异常!");
                            tvSub.setClickable(true);
                        }

                    } else {
                        MyToast.show(IssueBuyActivity.this, "返回数据失败!");
                        tvSub.setClickable(true);
                    }

                    break;
            }
        }
    };

    /**
     * 图片Base64编码上传
     */
    private void toSubmit() {
        if (editTitle.getText().toString().equals("")) {
            MyToast.show(IssueBuyActivity.this, "请输入求购标题");
            return;
        }
        if (editNum.getText().toString().equals("")) {
            MyToast.show(IssueBuyActivity.this, "请输入求购数量");
            return;
        }
        if (editCompanyName.getText().toString().equals("")) {
            MyToast.show(IssueBuyActivity.this, "请输入公司名称");
            return;
        }
        if (editCompanyAdress.getText().toString().equals("")) {
            MyToast.show(IssueBuyActivity.this, "请输入公司地址");
            return;
        }
        if (editPhone.getText().toString().equals("")) {
            MyToast.show(IssueBuyActivity.this, "请输入联系电话");
            return;
        }
//        if (ivPic.getDrawable() == null) {
//            MyToast.show(IssueBuyActivity.this, "请上传实物图片");
//            return;
//        }

        RequestBody requestBody = null;
        Map<String, String> params = new HashMap<>();

//        if (takePictrue != null) {
//            String imageString = takePictrue.bitmaptoString();
//            if (imageString != null)
//                params.put("img", "data:image/jpg;base64," + imageString);
//        }
        if (!TextUtils.isEmpty(imageString)) {
            params.put("img", "data:image/jpg;base64," + imageString);
        } else {
            params.put("img", "");
        }
        params.put("class", MyShare.getShared().getString("userType", ""));
        params.put("title", editTitle.getText().toString());
        params.put("address", editCompanyAdress.getText().toString());
        params.put("uid", MyShare.getShared().getString("userId", ""));
        params.put("number", editNum.getText().toString());
        params.put("phone", editPhone.getText().toString());
        params.put("intruduce", editMarker.getText().toString());

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
        final Request request = new Request.Builder()
                .url(UrlUtil.URL_PLUSSIGN_TOBUY)
                .post(requestBody).build();
        OkHttpClient mClient = new OkHttpClient();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(MessageWhat.REQUST_ERROR);
                tvSub.setClickable(true);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String ss = response.body().string();
                Message message = new Message();
                message.obj = ss;
                message.what = MessageWhat.PLUSSIGN_TOBUY;
                handler.sendMessage(message);
            }
        });
    }
}
