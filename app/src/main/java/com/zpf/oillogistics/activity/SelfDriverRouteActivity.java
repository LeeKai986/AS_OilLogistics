package com.zpf.oillogistics.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zpf.oillogistics.R;
import com.zpf.oillogistics.base.BaseActivity;
import com.zpf.oillogistics.base.MessageWhat;
import com.zpf.oillogistics.bean.response.DriverRouteResponse;
import com.zpf.oillogistics.net.SimplifyThread;
import com.zpf.oillogistics.net.UrlUtil;
import com.zpf.oillogistics.pulltorefreshlibrary.PullToRefreshBase;
import com.zpf.oillogistics.pulltorefreshlibrary.PullToRefreshListView;
import com.zpf.oillogistics.utils.DateTimeUtil;
import com.zpf.oillogistics.utils.MyShare;
import com.zpf.oillogistics.utils.MyToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/19.
 * <p>
 * 司机发布行程
 */

public class SelfDriverRouteActivity extends BaseActivity {

    // 布局相关
    // lv
    @BindView(R.id.rel_back)
    RelativeLayout relBack;
    @BindView(R.id.self_driver_route_lv)
    PullToRefreshListView lv;
    // adapter
    SelfDriverRouteAdapter adapter;
    List<DriverRouteResponse.DataBeanX.DataBean> mList = new ArrayList();
    int upPage = 1;

    @Override
    protected int setLayout() {
        return R.layout.activity_self_driver_route;
    }

    @Override
    protected void initData() {
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

        lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                mList.clear();
                upPage = 1;
                search();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                upPage++;
                search();
            }
        });

        lv.setScrollingWhileRefreshingEnabled(true);
        lv.setMode(PullToRefreshBase.Mode.BOTH);
    }

    /**
     * 司机行程
     */
    private void search() {
        HashMap<String, String> hp = new HashMap<>();
        hp.put("page", upPage + "");
        hp.put("phone", MyShare.getShared().getString("userPhone", ""));

        SimplifyThread simplifyThread = new SimplifyThread(UrlUtil.URL_DRIVIER_TRIP, hp);
        simplifyThread.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                Message message = new Message();
                message.obj = res;
                message.what = MessageWhat.SELF_DRIVIER_TRIP;
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
            lv.onRefreshComplete();
            switch (msg.what) {
                case MessageWhat.SELF_DRIVIER_TRIP:
                    if (msg.obj != null) {
                        try {
                            DriverRouteResponse have = gson.fromJson(msg.obj.toString(), DriverRouteResponse.class);

                            if (have.getStatus() == 0) {
                                mList.addAll(have.getData().getData());
                                if (adapter == null) {
                                    upPage++;
                                    adapter = new SelfDriverRouteAdapter();
                                    lv.setAdapter(adapter);
                                } else {
                                    adapter.notifyDataSetChanged();
                                }

                            } else {
                                MyToast.show(SelfDriverRouteActivity.this, "暂无数据!");
                            }

                        } catch (Exception e) {
                            MyToast.show(SelfDriverRouteActivity.this, "返回数据异常!");
                        }

                    } else {
                        MyToast.show(SelfDriverRouteActivity.this, "返回数据失败!");
                    }
                    break;
            }
        }
    };

    class SelfDriverRouteAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (mList != null) {
                return mList.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder vh;
            if (view == null) {
                view = LayoutInflater.from(SelfDriverRouteActivity.this).inflate(R.layout.item_self_driver_route, viewGroup, false);
                vh = new ViewHolder(view);
                view.setTag(vh);
            } else {
                vh = (ViewHolder) view.getTag();
            }

            DriverRouteResponse.DataBeanX.DataBean route = mList.get(i);
            //头像
            if (route.getHerder() != null) {
                Glide.with(SelfDriverRouteActivity.this)
                        .load(UrlUtil.IMAGE_URL + route.getHerder())
                        .placeholder(R.mipmap.head_default)
                        .error(R.mipmap.head_default)
                        .into(vh.ivHead);
            }
            //地址
            if (route.getStartplace() != null) {
                vh.tvAdress.setText(route.getStartplace() + "-" + route.getEndplace());
            }

            //类型
            if (route.getClassX() == 1) {
                vh.tvType.setText("类型:" + "第一类");
            } else if (route.getClassX() == 2) {
                vh.tvType.setText("类型:" + "第二类");
            } else if (route.getClassX() == 3) {
                vh.tvType.setText("类型:" + "第三类");
            } else if (route.getClassX() == 4) {
                vh.tvType.setText("类型:" + "第四类");
            } else if (route.getClassX() == 5) {
                vh.tvType.setText("类型:" + "第五类");
            } else if (route.getClassX() == 6) {
                vh.tvType.setText("类型:" + "第六类");
            } else if (route.getClassX() == 7) {
                vh.tvType.setText("类型:" + "第七类");
            } else if (route.getClassX() == 8) {
                vh.tvType.setText("类型:" + "第八类");
            } else if (route.getClassX() == 9) {
                vh.tvType.setText("类型:" + "第九类");
            }

            //载重
            if (route.getLoad() != 0) {
                vh.tvNum.setText("载重:" + route.getLoad() + "吨");
            }

            //时间
            if (route.getTime() != null) {
                vh.tvTime.setText("承运日期:" + DateTimeUtil.stampToDate("yyyy/MM/dd", route.getTime() + "000"));
            }
            return view;
        }

        class ViewHolder {
            @BindView(R.id.iv_head)
            ImageView ivHead;
            @BindView(R.id.tv_adress)
            TextView tvAdress;
            @BindView(R.id.tv_type)
            TextView tvType;
            @BindView(R.id.tv_num)
            TextView tvNum;
            @BindView(R.id.tv_time)
            TextView tvTime;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
