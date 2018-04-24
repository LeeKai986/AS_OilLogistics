package com.zpf.oillogistics.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zpf.oillogistics.R;
import com.zpf.oillogistics.base.BaseActivity;
import com.zpf.oillogistics.base.MessageWhat;
import com.zpf.oillogistics.bean.response.DriverRouteResponse;
import com.zpf.oillogistics.diy.DiyDialog;
import com.zpf.oillogistics.diy.DiySwipeMenuListview;
import com.zpf.oillogistics.net.SimplifyThread;
import com.zpf.oillogistics.net.UrlUtil;
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
    DiySwipeMenuListview lv;
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
        lv.setMenuCreator(new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xff,
                        0x4f, 0x4f)));
                // set item width
                deleteItem.setWidth(200);
                deleteItem.setTitle("删除");
                deleteItem.setTitleSize(15);
                deleteItem.setTitleColor(Color.WHITE);
//                // set a icon
//                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        });
//        lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
//            @Override
//            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
//                mList.clear();
//                upPage = 1;
//                search();
//            }
//
//            @Override
//            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
//                upPage++;
//                search();
//            }
//        });
//
//        lv.setScrollingWhileRefreshingEnabled(true);
//        lv.setMode(PullToRefreshBase.Mode.BOTH);
        lv.setOnRefreshLoadListener(new DiySwipeMenuListview.OnRefreshLoadListener() {
            @Override
            public void onRefresh() {
                mList.clear();
                upPage = 1;
                search();
            }

            @Override
            public void onLoad() {
                upPage++;
                search();
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(SelfDriverRouteActivity.this, DriverDetailsActivity.class);
                intent.putExtra("phone", mList.get(position).getPhone());
                intent.putExtra("id", mList.get(position).getId() + "");
                intent.putExtra("startplace", mList.get(position).getStartplace() + "");
                intent.putExtra("endplace", mList.get(position).getEndplace() + "");
                intent.putExtra("time", mList.get(position).getTime() + "");
                startActivity(intent);
            }
        });
        lv.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                DiyDialog.hintTweBtnDialog(SelfDriverRouteActivity.this, "您确定要删除行程吗", new DiyDialog.HintTweBtnListener() {
                    @Override
                    public void confirm() {
                        deleteRoute(mList.get(position).getId() + "");
                    }
                });
                return false;
            }
        });
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
//            lv.onRefreshComplete();
            lv.onRLStop();
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
                                mList.clear();
                                if (adapter == null) {
                                    adapter = new SelfDriverRouteAdapter();
                                    lv.setAdapter(adapter);
                                } else {
                                    adapter.notifyDataSetChanged();
                                }
                                MyToast.show(SelfDriverRouteActivity.this, "暂无数据!");
                            }

                        } catch (Exception e) {
                            MyToast.show(SelfDriverRouteActivity.this, "返回数据异常!");
                        }

                    } else {
                        MyToast.show(SelfDriverRouteActivity.this, "返回数据失败!");
                    }
                    break;
                case MessageWhat.URL_DELETE_DRIVIER:
                    if (msg.obj != null) {
                        try {
                            DriverRouteResponse have = gson.fromJson(msg.obj.toString(), DriverRouteResponse.class);

                            if (have.getStatus() == 0) {
                                mList.clear();
                                upPage = 1;
                                search();
                            } else {
                                mList.clear();
                                upPage = 1;
                                search();
                                MyToast.show(SelfDriverRouteActivity.this, have.getMsg());
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

            final DriverRouteResponse.DataBeanX.DataBean route = mList.get(i);
            //头像
            if (route.getHerder() != null) {
                Glide.with(SelfDriverRouteActivity.this)
                        .load(UrlUtil.IMAGE_URL + route.getHerder())
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
//            vh.linAll.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(SelfDriverRouteActivity.this, DriverDetailsActivity.class);
//                    intent.putExtra("phone", route.getPhone());
//                    intent.putExtra("id", route.getId() + "");
//                    intent.putExtra("startplace", route.getStartplace() + "");
//                    intent.putExtra("endplace", route.getEndplace() + "");
//                    intent.putExtra("time", route.getTime() + "");
//                    startActivity(intent);
//                }
//            });
//            vh.linAll.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    DiyDialog.hintTweBtnDialog(SelfDriverRouteActivity.this, "您确定要删除行程吗", new DiyDialog.HintTweBtnListener() {
//                        @Override
//                        public void confirm() {
//                            deleteRoute(route.getId() + "");
//                        }
//                    });
//                    return true;
//                }
//            });
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
            @BindView(R.id.have_product_all_ll)
            LinearLayout linAll;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    /**
     * 司机行程
     */
    private void deleteRoute(String id) {
        HashMap<String, String> hp = new HashMap<>();
        hp.put("id", id);
        SimplifyThread simplifyThread = new SimplifyThread(UrlUtil.URL_DELETE_DRIVIER, hp);
        simplifyThread.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                Message message = new Message();
                message.obj = res;
                message.what = MessageWhat.URL_DELETE_DRIVIER;
                handler.sendMessage(message);
            }

            @Override
            public void errorBody(String error) {
                handler.sendEmptyMessage(MessageWhat.REQUST_ERROR);
            }
        });
    }
}
