package com.zpf.oillogistics.activity;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zpf.oillogistics.R;
import com.zpf.oillogistics.base.BaseActivity;
import com.zpf.oillogistics.base.MessageWhat;
import com.zpf.oillogistics.bean.NormalBean;
import com.zpf.oillogistics.bean.response.PriceInforResponse;
import com.zpf.oillogistics.bean.response.PriceInforTweResponse;
import com.zpf.oillogistics.diy.DiyDialog;
import com.zpf.oillogistics.net.SimplifyThread;
import com.zpf.oillogistics.net.UrlUtil;
import com.zpf.oillogistics.utils.DateTimeUtil;
import com.zpf.oillogistics.utils.MyShare;
import com.zpf.oillogistics.utils.MyToast;
import com.zpf.oillogistics.utils.NormalUtils;

import java.io.File;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/18.
 * <p>
 * 资讯详情
 */

public class InformationDetailsActivity extends BaseActivity {

    @BindView(R.id.rel_back_infordetails)
    RelativeLayout relBack;
    @BindView(R.id.tv_attention_infordetails)
    TextView tvAttention;
    @BindView(R.id.tv_title_infordetails)
    TextView tvTitle;
    @BindView(R.id.tv_date_infordetails)
    TextView tvDate;
    @BindView(R.id.tv_press_infordetails)
    TextView tvPress;
    //    @BindView(R.id.iv_Icon_infordetails)
//    ImageView ivIcon;
//    @BindView(R.id.tv_content_infordetails)
//    TextView tvContent;
    @BindView(R.id.tv_excel_infordetails)
    TextView tvExcel;
    @BindView(R.id.web)
    WebView webInfo;

    private String inforID = "";
    private String flag = "";
    private String uid = "";
    private String URL = UrlUtil.OFFER_INDEX;
    private PriceInforResponse mation;


//    // 获取excel
//    // 传参
//    private HashMap<String, String> excelMap;
//    // 线程
//    private SimplifyThread excelThread;


    // 布局相关
    @Override
    protected int setLayout() {
        return R.layout.activity_information_details;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViewAction(Bundle savedInstanceState) {
        relBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
//        tvAttention.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                attention();
//            }
//        });

        inforID = getIntent().getExtras().getString("inforID");
        flag = getIntent().getExtras().getString("flag");
        uid = getIntent().getExtras().getString("uid");

        if (flag == null) {
            if (!MyShare.getShared().getString("userType", "").equals("3")) {
                tvAttention.setVisibility(View.VISIBLE);
            }
//            tvExcel.setVisibility(View.VISIBLE);
            tvExcel.setVisibility(View.GONE);
            tvExcel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String filePath = Environment.getExternalStorageDirectory() + "/";
                    if (new File(filePath, mation.getData().getData().get(0).getTitle() + ".xls").exists()) {
                        getExcelFileIntent(filePath + mation.getData().getData().get(0).getTitle() + ".xls");
                        return;
                    }
                    DiyDialog.hintTweBtnDialog(InformationDetailsActivity.this, "是否下载当前Excel表?", new DiyDialog.HintTweBtnListener() {
                        @Override
                        public void confirm() {
                            MyToast.show(InformationDetailsActivity.this, "正在下载...");
                            String downLoadUrl = UrlUtil.IMAGE_URL + mation.getData().getData().get(0).getExcel();
                            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downLoadUrl));
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                            request.setTitle(mation.getData().getData().get(0).getTitle() + ".xls");
                            request.setDescription("");

                            // 设置下载文件的保存位置
                            File saveFile = new File(Environment.getExternalStorageDirectory(), mation.getData().getData().get(0).getTitle() + ".xls");
                            request.setDestinationUri(Uri.fromFile(saveFile));

                            /*
                             * 2. 获取下载管理器服务的实例, 添加下载任务
                             */
                            DownloadManager manager = (DownloadManager) InformationDetailsActivity.this.getSystemService(Context.DOWNLOAD_SERVICE);

                            // 将下载请求加入下载队列, 返回一个下载ID
                            long downloadId = manager.enqueue(request);

                            // 如果中途想取消下载, 可以调用remove方法, 根据返回的下载ID取消下载, 取消下载后下载保存的文件将被删除
//                            manager.remove(downloadId);

                            /**
                             * 打开excel
                             */
//                            Intent intent = new Intent("android.intent.action.VIEW");
//                            intent.addCategory("android.intent.category.DEFAULT");
//                            intent.addFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
//                            Uri uri = Uri.fromFile(new File("/mnt/sdcard/Book1.xls"));
//                            intent.setDataAndType (uri, "application/vnd.ms-excel");
//                            startActivity(intent);

                            // 系统下载
//                            Intent intent = new Intent();
//                            intent.setAction(Intent.ACTION_VIEW);
//                            intent.addCategory(Intent.CATEGORY_BROWSABLE);
////                            intent.setData(Uri.parse(infor.getData().getData().get(0).ge));
//                            startActivity(intent);
                        }
                    });
                }
            });
            getPrice();
        } else {
            getInformation();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }


    /**
     * 主页轮播和资讯
     */
    private void getInformation() {
        HashMap<String, String> hp = new HashMap<>();
        hp.put("id", inforID);

        SimplifyThread simplifyThread = new SimplifyThread(UrlUtil.URL_SLIDESHOW_INFO, hp);
        simplifyThread.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                Message message = new Message();
                message.obj = res;
                message.what = MessageWhat.SLIDESHOW_INFO;
                handler.sendMessage(message);
            }

            @Override
            public void errorBody(String error) {
                handler.sendEmptyMessage(MessageWhat.REQUST_ERROR);
            }
        });
    }

    /**
     * 报价
     */
    private void getPrice() {
        HashMap<String, String> hp = new HashMap<>();
        hp.put("id", inforID);
        hp.put("uid", MyShare.getShared().getString("userId", ""));

        SimplifyThread simplifyThread = new SimplifyThread(UrlUtil.OFFER_INDEX, hp);
        simplifyThread.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                Message message = new Message();
                message.obj = res;
                message.what = MessageWhat.TOBUY_INDEX;
                handler.sendMessage(message);
            }

            @Override
            public void errorBody(String error) {
                handler.sendEmptyMessage(MessageWhat.REQUST_ERROR);
            }
        });
    }

    /**
     * 关注
     */
    private void attention() {
        HashMap<String, String> hp = new HashMap<>();
        hp.put("uid", MyShare.getShared().getString("userId", ""));
        hp.put("source", uid);
        hp.put("status", "2");

        SimplifyThread simplifyThread = new SimplifyThread(UrlUtil.URL_FOLLOW_OFFER, hp);
        simplifyThread.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                Message message = new Message();
                message.obj = res;
                message.what = MessageWhat.FOLLOW_FOLLOW;
                handler.sendMessage(message);
            }

            @Override
            public void errorBody(String error) {
                handler.sendEmptyMessage(MessageWhat.REQUST_ERROR);
            }
        });
    }

    /**
     * 取消关注
     * @param param
     */
    /**
     * 取消关注
     */
    private void delData(String id) {
        HashMap hp = new HashMap<>();
        hp.put("id", NormalUtils.userId());
        hp.put("source", id);
        hp.put("status", "2");
        SimplifyThread inforCancel = new SimplifyThread(UrlUtil.USER_FOLLOW_CANCEL, hp);
        inforCancel.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                Message message = new Message();
                message.obj = res;
                message.what = 2;
                handler.sendMessage(message);
            }

            @Override
            public void errorBody(String error) {
                handler.sendEmptyMessage(0);
            }
        });

    }

    //android获取一个用于打开Excel文件的intent
    public void getExcelFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/vnd.ms-excel");
        startActivity(intent);
    }

    private Gson gson = new Gson();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MessageWhat.SLIDESHOW_INFO:
                    if (msg.obj != null) {
                        try {
//                            if (flag == null) {
//                                PriceInforResponse mation = gson.fromJson(msg.obj.toString(), PriceInforResponse.class);
//
//                                if (mation.getStatus() == 0 && mation.getData() != null && mation.getData().getData() != null && mation.getData().getData().size() != 0) {
//
//                                    tvTitle.setText(mation.getData().getData().get(0).getTitle());
//                                    tvDate.setText(DateTimeUtil.stampToDate("yyyy/MM/dd", mation.getData().getData().get(0).getTime() + "000"));
////                                tvContent.setText(mation.getData().get(0).getContent());
//                                    tvPress.setText(mation.getData().getData().get(0).getAuthor());
//                                    webInfo.getSettings().setDomStorageEnabled(true);
//                                    webInfo.loadDataWithBaseURL(null, mation.getData().getData().get(0).getContent(), "text/html", "utf-8", null);
//
//
////                                Glide.with(InformationDetailsActivity.this)
////                                        .load(UrlUtil.IMAGE_URL + mation.getData().get(0).getImg())
////                                        .into(ivIcon);
//
//                                } else {
//                                    MyToast.show(InformationDetailsActivity.this, "暂无数据!");
//                                }
//                            } else {
                            PriceInforTweResponse inforTwe = gson.fromJson(msg.obj.toString(), PriceInforTweResponse.class);
                            if (inforTwe.getStatus() == 0 && inforTwe.getData() != null && inforTwe.getData() != null
                                    && inforTwe.getData().size() != 0) {
                                tvTitle.setText(inforTwe.getData().get(0).getTitle());
                                tvDate.setText(DateTimeUtil.stampToDate("yyyy/MM/dd", inforTwe.getData().get(0).getTime() + "000"));
//                                tvContent.setText(mation.getData().get(0).getContent());
                                tvPress.setText(inforTwe.getData().get(0).getAuthor());
//                                webInfo.loadDataWithBaseURL(null, "<html><head><style>img{width:100%;}</style></head><div id='test' style='width:100%;overflow:hidden'>"
//                                        + inforTwe.getData().get(0).getContent() + "</div>"
//                                        + "</html>", "text/html", "utf-8", null);
                                webInfo.getSettings().setDomStorageEnabled(true);
                                webInfo.getSettings().setJavaScriptEnabled(true);
//                             settings.setLoadWithOverviewMode(true);//设置WebView是否使用预览模式加载界面。
//                                webInfo.getSettings().setTextSize(WebSettings.TextSize.SMALLER);//通过设置WebSettings，改变HTML中文字的大小
                                webInfo.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//支持通过JS打开新窗口
                                webInfo.setWebViewClient(new WebViewClient());
                                webInfo.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//支持内容重新布局
//                                webInfo.loadDataWithBaseURL(null, "<html><head><style>img{width:100%;}</style></head><div id='test' style='width:100%;overflow:hidden'>"
//                                        + mation.getData().getData().get(0).getContent() + "</div>"
//                                        + "</html>", "text/html", "utf-8", null);
                                webInfo.loadDataWithBaseURL(null, "</div><head><style>img{ width:100% !important;}</style></head>"
                                        + inforTwe.getData().get(0).getContent(), "text/html", "utf-8", null);

//                                Glide.with(InformationDetailsActivity.this)
//                                        .load(UrlUtil.IMAGE_URL + mation.getData().get(0).getImg())
//                                        .into(ivIcon);

                            } else {
                                MyToast.show(InformationDetailsActivity.this, "暂无数据!");
                            }
//                            }
                        } catch (Exception e) {
                            MyToast.show(InformationDetailsActivity.this, "返回数据异常!");
                        }

                    } else {
                        MyToast.show(InformationDetailsActivity.this, "返回数据失败!");
                    }
                    break;
                case MessageWhat.TOBUY_INDEX:
                    if (msg.obj != null) {
                        try {

//                            infor = gson.fromJson(msg.obj.toString(), PriceInforResponse.class);
//
//                            if (infor.getStatus() == 0) {
//
//                                tvTitle.setText(infor.getData().getData().get(0).getTitle());
//                                tvDate.setText(DateTimeUtil.stampToDate("yyyy/MM/dd", infor.getData().getData().get(0).getTime() + "000"));
////                                tvContent.setText(infor.getData().getData().get(0).getContent());
//                                tvPress.setText(infor.getData().getData().get(0).getAuthor());
//
////                                Glide.with(InformationDetailsActivity.this)
////                                        .load(UrlUtil.IMAGE_URL + infor.getData().getData().get(0).getImg())
////                                        .into(ivIcon);
//
//                            } else {
//                                MyToast.show(InformationDetailsActivity.this, "暂无数据!");
//                            }

                            mation = gson.fromJson(msg.obj.toString(), PriceInforResponse.class);

                            if (mation.getStatus() == 0 && mation.getData() != null && mation.getData().getData() != null && mation.getData().getData().size() != 0) {
                                tvTitle.setText(mation.getData().getData().get(0).getTitle());
                                tvDate.setText(DateTimeUtil.stampToDate("yyyy/MM/dd", mation.getData().getData().get(0).getTime() + "000"));
//                                tvContent.setText(mation.getData().get(0).getContent());
                                tvPress.setText(mation.getData().getData().get(0).getAuthor());
                                webInfo.getSettings().setDomStorageEnabled(true);
                                webInfo.getSettings().setJavaScriptEnabled(true);
//                             settings.setLoadWithOverviewMode(true);//设置WebView是否使用预览模式加载界面。
//                              webInfo.getSettings().setTextSize(WebSettings.TextSize.SMALLER);//通过设置WebSettings，改变HTML中文字的大小
                                webInfo.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//支持通过JS打开新窗口
                                webInfo.setWebViewClient(new WebViewClient());
                                webInfo.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//支持内容重新布局
//                                webInfo.loadDataWithBaseURL(null, "<html><head><style>img{width:100%;}</style></head><div id='test' style='width:100%;overflow:hidden'>"
//                                        + mation.getData().getData().get(0).getContent() + "</div>"
//                                        + "</html>", "text/html", "utf-8", null);
                                webInfo.loadDataWithBaseURL(null, "</div><head><style>img{ width:100% !important;}</style></head>"
                                        + mation.getData().getData().get(0).getContent(), "text/html", "utf-8", null);
                                if (mation.getData().getData().get(0).getFollow() != null && !mation.getData().getData().get(0).getFollow().equals("")) {
                                    if (mation.getData().getData().get(0).getFollow().equals("1")) {
                                        tvAttention.setText("已关注");
                                        tvAttention.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                DiyDialog.hintTweBtnDialog(InformationDetailsActivity.this, "取消关注请点击确定", new DiyDialog.HintTweBtnListener() {
                                                    @Override
                                                    public void confirm() {
                                                        delData(mation.getData().getData().get(0).getUid() + "");
                                                    }
                                                });
                                            }
                                        });
                                    } else {
                                        tvAttention.setText("关注");
                                        tvAttention.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                attention();
                                            }
                                        });
                                    }
                                }


//                                Glide.with(InformationDetailsActivity.this)
//                                        .load(UrlUtil.IMAGE_URL + mation.getData().get(0).getImg())
//                                        .into(ivIcon);

                            } else {
                                MyToast.show(InformationDetailsActivity.this, "暂无数据!");
                            }
                        } catch (Exception e) {
                            MyToast.show(InformationDetailsActivity.this, "返回数据异常!");
                        }

                    } else {
                        MyToast.show(InformationDetailsActivity.this, "返回数据失败!");
                    }
                    break;
                case MessageWhat.FOLLOW_FOLLOW:
                    if (msg.obj != null) {
                        try {
                            PriceInforResponse infor = gson.fromJson(msg.obj.toString(), PriceInforResponse.class);

                            if (infor.getStatus() == 0) {
                                MyToast.show(InformationDetailsActivity.this, "关注成功!");
                                getPrice();
                            } else {
                                MyToast.show(InformationDetailsActivity.this, "暂无数据!");
                            }

                        } catch (Exception e) {
                            MyToast.show(InformationDetailsActivity.this, "返回数据异常!");
                        }

                    } else {
                        MyToast.show(InformationDetailsActivity.this, "返回数据失败!");
                    }
                    break;
                case 2:
                    NormalBean normalBean = new Gson().fromJson(msg.obj.toString(), NormalBean.class);
                    if (normalBean.getStatus() == 0) {
                        MyToast.show(InformationDetailsActivity.this, "已取消关注");
                        setResult(22);
                        finish();
                    } else {
                        MyToast.show(InformationDetailsActivity.this, normalBean.getMsg());
                    }
                    break;
            }
        }
    };


    private String getHtml(String body) {
        StringBuilder sb = new StringBuilder();
        //拼接一段HTML代码
        sb.append("<html>");
        sb.append("<body>");
        sb.append(body);
        sb.append("</body>");
        sb.append("</html>");
        return sb.toString();
    }
}
