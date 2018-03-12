package com.zpf.oillogistics.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zpf.oillogistics.R;
import com.zpf.oillogistics.activity.PlatformIssueOrderActivity;
import com.zpf.oillogistics.bean.DriverOrder;
import com.zpf.oillogistics.utils.CallPermission;
import com.zpf.oillogistics.utils.DateTimeUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/19.
 */

public class DriverOrderAdapter extends BaseAdapter {

    private Context context;
    private List<DriverOrder> mList;
    private LayoutInflater inflater;
    private int flag = 1;

    public DriverOrderAdapter(Context context, List<DriverOrder> list) {
        this.context = context;
        this.mList = list;
        inflater = LayoutInflater.from(context);
    }

    public void setmList(List<DriverOrder> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mList != null)
            return mList.size();
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = inflater.inflate(R.layout.adapter_driverorder, viewGroup, false);
            holder = new ViewHolder(view);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        DriverOrder order = (DriverOrder) getItem(i);

        // 咨询客服
        holder.tvCallService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "05303613333"));
//                context.startActivity(intent);
                CallPermission.callPhone(context, "05303613333");
            }
        });
        //订单编号
        if (order.no != null) {
            holder.tvOrderNum.setText("订单编号：" + order.no);
        }
        //货物名称
        if (order.getGoodsname() != null) {
            holder.tvName.setText(order.getGoodsname() + "  " + order.number + "吨");
        }
        //订单时间
        if (order.time != 0) {
            holder.tvTime.setText(DateTimeUtil.stampToDate("yyyy/MM/dd HH:mm:ss", order.time + "000"));
        }
        //订单编号
        if (order.no != null) {
            holder.tvOrderNum.setText("订单编号：" + order.no);
        }
        //'0'未交定金未支付','1' '已交定金未支付', '2'已支付','3' '待发货',
        // '4' '待收货', '5' '完成','6' '订单取消',7已送达
        if (order.status >= 4) {
            //收货人+手机号
            if (order.toname != null) {
                holder.tvClient.setText(order.toname + " " + order.tophone);
            }
            //收货公司
            if (order.tocompany != null) {
                holder.tvCompany.setText(order.tocompany);
            }
            //收货地址
            if (order.toaddress != null) {
                String adress = order.toplace;
                if (order.toaddress != null)
                    adress += order.toaddress;
                holder.tvAdress.setText("送达地址：" + adress);
            }
        } else {
            //提货联系人+手机号
            if (order.company != null && order.phone != null) {
                holder.tvClient.setText(order.company + " " + order.phone);
            }
            //提货公司
            if (order.company != null) {
                holder.tvCompany.setText(order.company);
            }
            //提货地址
            if (order.address != null) {
                holder.tvAdress.setText("提货地址：" + order.address);
            }
        }


        //订单编号
        if (order.no != null) {
            holder.tvOrderNum.setText("订单编号：" + order.no);
        }
        holder.linOrder.setTag(i);
        holder.linOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = (int) view.getTag();
                driverOrderListener.details(n);
            }
        });

        holder.relLocation.setTag(i);
        holder.relLocation.setVisibility(View.VISIBLE);
        holder.tvPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = (int) view.getTag();
                driverOrderListener.config(n);
            }
        });

        //'0'未交定金未支付','1' '已交定金未支付', '2'已支付','3' '待发货',
//        // '4' '待收货', '5' '完成','6' '订单取消',7已送达
        if (order.status == 3 || order.status == 0) {
            holder.linPick.setVisibility(View.GONE);
            holder.tvPick.setVisibility(View.VISIBLE);

            holder.tvState.setText("未提货");
            if (order.status == 0) {
                holder.tvState.setText("未审核");
                holder.tvPick.setVisibility(View.GONE);
            }
            holder.tvPick.setTag(i);
            holder.tvPick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int n = (int) view.getTag();
                    driverOrderListener.config(n);
                }
            });

            holder.relLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int n = (int) view.getTag();
                    driverOrderListener.toDriverLocation(n, 1);
                }
            });


//            //收货人+手机号
//            if (order.phone != null) {
//                holder.tvClient.setText(order.toname + " " + order.tophone);
//            }
//            //收货公司
//            if (order.company != null) {
//                holder.tvCompany.setText(order.company);
//            }

        } else if (order.status == 4) {
            holder.linPick.setVisibility(View.VISIBLE);
            holder.tvLogists.setVisibility(View.GONE);
            holder.tvPick.setVisibility(View.GONE);

            holder.tvState.setText("已提货");
//            holder.tvLogists.setTag(i);
//            holder.tvLogists.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int n = (int) view.getTag();
//                    driverOrderListener.toDriverLocation(n, 2);
//                }
//            });
            holder.tvArrive.setTag(i);
            holder.tvArrive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int n = (int) view.getTag();
                    driverOrderListener.arrive(n);
                }
            });

            //收货地址
            if (order.toplace != null) {
                String adress = order.toplace;
                if (order.toaddress != null)
                    adress += order.toaddress;
                holder.tvAdress.setText("送达地址：" + adress);
            }

            holder.relLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int n = (int) view.getTag();
                    driverOrderListener.toDriverLocation(n, 2);
                }
            });

        } else if (order.status == 5) {
            holder.linPick.setVisibility(View.GONE);
            holder.tvPick.setVisibility(View.GONE);
            holder.tvState.setText("已完成");
            holder.relLocation.setVisibility(View.GONE);


        } else if (order.status == 7) {
            holder.linPick.setVisibility(View.GONE);
            holder.tvPick.setVisibility(View.GONE);
            holder.tvState.setText("已送达");
            holder.relLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int n = (int) view.getTag();
                    driverOrderListener.toDriverLocation(n, 1);
                }
            });
        }

        return view;
    }

    private DriverOrderListener driverOrderListener;

    public void setDriverOrderListener(DriverOrderListener driverOrderListener) {
        this.driverOrderListener = driverOrderListener;
    }

    public interface DriverOrderListener {

        //flag 1,定位 2,提货地址 3送货地址
        void toDriverLocation(int n, int flag);

        void config(int n);

        void details(int n);

        void arrive(int n);
    }

    static class ViewHolder {
        @BindView(R.id.tv_orderNum_driver_adpater)
        TextView tvOrderNum;
        @BindView(R.id.tv_state_driver_adpater)
        TextView tvState;
        @BindView(R.id.tv_name_driver_adpater)
        TextView tvName;
        @BindView(R.id.tv_time_driver_adpater)
        TextView tvTime;
        @BindView(R.id.tv_client_driver_adpater)
        TextView tvClient;
        @BindView(R.id.tv_companyName_driver_adpater)
        TextView tvCompany;
        @BindView(R.id.tv_phone_driver_adpater)
        TextView tvPhone;
        @BindView(R.id.tv_adress_driver_adpater)
        TextView tvAdress;
        @BindView(R.id.lin_order_details)
        LinearLayout linOrder;
        @BindView(R.id.tv_logists_driver_adpater)
        TextView tvLogists;
        @BindView(R.id.tv_arrive_driver_adpater)
        TextView tvArrive;
        @BindView(R.id.lin_pick_driver_adpater)
        LinearLayout linPick;
        @BindView(R.id.tv_pick_driver_adpater)
        TextView tvPick;
        @BindView(R.id.rel_boom_driver_adpater)
        RelativeLayout relBoom;
        @BindView(R.id.rel_location)
        RelativeLayout relLocation;
        @BindView(R.id.lin_call_service_driver_adpater)
        TextView tvCallService;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
