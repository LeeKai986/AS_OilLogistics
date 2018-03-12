package com.zpf.oillogistics.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zpf.oillogistics.R;
import com.zpf.oillogistics.adapter.AddPictureGridAdapter;
import com.zpf.oillogistics.base.BaseActivity;
import com.zpf.oillogistics.base.Constant;
import com.zpf.oillogistics.base.MessageWhat;
import com.zpf.oillogistics.bean.response.SelfChangeResponse;
import com.zpf.oillogistics.customview.SelectPicPopupWindow;
import com.zpf.oillogistics.net.UrlUtil;
import com.zpf.oillogistics.utils.MyIntent;
import com.zpf.oillogistics.utils.MyShare;
import com.zpf.oillogistics.utils.MyToast;
import com.zpf.oillogistics.utils.TakePictrueUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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
 */

public class SelfFeedBackActivity extends BaseActivity implements View.OnClickListener, AddPictureGridAdapter.AddpictureListener {
    @BindView(R.id.rel_back_feedback_self)
    RelativeLayout relBack;
    @BindView(R.id.tv_feedbacked_feedback_self)
    TextView tvFeedbacked;
    @BindView(R.id.edit_contant_feedback_self)
    EditText editContant;
    @BindView(R.id.grid_feedback_self)
    GridView gridFeed;
    @BindView(R.id.tv_config_feedback_self)
    TextView tvConfig;

    private AddPictureGridAdapter adapter;

    private List<Bitmap> list = new ArrayList<>();
    private TakePictrueUtils takePictrue;

    private String imgBase64="";

    @Override
    protected int setLayout() {
        return R.layout.activity_feedback_self;
    }

    @Override
    protected void initData() {
        relBack.setOnClickListener(this);
        tvFeedbacked.setOnClickListener(this);
        tvConfig.setOnClickListener(this);

        adapter = new AddPictureGridAdapter(list, SelfFeedBackActivity.this);
        adapter.setAddpictureListener(SelfFeedBackActivity.this);
        gridFeed.setAdapter(adapter);
    }

    @Override
    protected void initViewAction(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rel_back_feedback_self:
                finish();
                break;
            case R.id.tv_feedbacked_feedback_self:
                new MyIntent(SelfFeedBackActivity.this).startAct(FeedbackListActivity.class);
                break;
            case R.id.tv_config_feedback_self:
                inner_postAsync();
                break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void addPicture() {

        requestRunTimePermissions(new String[]{
                Manifest.permission.RECEIVE_BOOT_COMPLETED,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.CAMERA
        }, new PermissionCall() {
            @Override
            public void requestSuccess() {
                new Thread() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                picWindow = new SelectPicPopupWindow(SelfFeedBackActivity.this, picItemsClick);
                                picWindow.showAtLocation(findViewById(R.id.contanor_layout),
                                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                            }
                        });
                    }
                }.start();
            }

            @Override
            public void refused() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        MyToast.show(SelfFeedBackActivity.this, "请授予相关权限!");
                    }
                });
            }
        });
    }

    private SelectPicPopupWindow picWindow;
    private View.OnClickListener picItemsClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            picWindow.dismiss();

            takePictrue=new TakePictrueUtils(SelfFeedBackActivity.this,"feed");

            switch (v.getId()) {
                case R.id.takePhotoBtn:
                    takePictrue.startCamera();
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
                takePictrue.startPhotoCut(takePictrue.imageUri, 300, true);
                break;
            case TakePictrueUtils.PHOTO_WALL:
                if (null != data) {
                    takePictrue.startPhotoCut(data.getData(), 300, true);
                }
                break;
            case TakePictrueUtils.PHOTO_STORE:
                if (null != data) {
                    Bitmap bitmap=takePictrue.setPictureToImageView(data, true);
                    if(bitmap!=null) {
                        imgBase64+=("data:image/jpg;base64,"+toBase64Str(takePictrue.imageUri.getPath()).trim()+"#");
                        list.add(bitmap);
                        adapter.notifyDataSetChanged();
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

        Map<String, String> params=new HashMap<>();
        params.put("id", MyShare.getShared().getString("userId",""));
        params.put("feedback",editContant.getText().toString());

        //图片Base64编码上传
        if(!imgBase64.equals("")) {
            imgBase64=imgBase64.substring(0,imgBase64.length()-1);
            params.put("img",imgBase64);
        }else {
            params.put("img",imgBase64);
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
        final Request request = new Request.Builder().url(UrlUtil.URL_FEEDBACK_FEEDBACK).post(requestBody).build();
        OkHttpClient mClient =new OkHttpClient();
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

                                MyToast.show(SelfFeedBackActivity.this, "提交成功!");
                                startActivity(new Intent(SelfFeedBackActivity.this,FeedbackListActivity.class));
                                finish();

                            } else {
                                MyToast.show(SelfFeedBackActivity.this, "暂无数据!");
                            }

                        } catch (Exception e) {
                            MyToast.show(SelfFeedBackActivity.this, "返回数据异常!");
                        }

                    } else {
                        MyToast.show(SelfFeedBackActivity.this, "返回数据失败!");
                    }
                    break;
            }
        }
    };

    private String toBase64Str(String url){

        Bitmap bitmap = BitmapFactory.decodeFile(url);
        //第一步:将Bitmap压缩至字节数组输出流ByteArrayOutputStream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        //第二步:利用Base64将字节数组输出流中的数据转换成字符串String
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return new String(Base64.encodeToString(byteArray, Base64.DEFAULT));

    }
}
