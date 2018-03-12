package com.zpf.oillogistics.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.zpf.oillogistics.R;
import com.zpf.oillogistics.bean.CompanyOrder;
import com.zpf.oillogistics.utils.DateTimeUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/19.
 */

public class CompanyOrderAdapter extends BaseAdapter {

    private Context context;
    private List<CompanyOrder> mList;
    private LayoutInflater inflater;
    private int flag = 1;

    public CompanyOrderAdapter(Context context, List<CompanyOrder> list) {
        this.context = context;
        this.mList = list;
        inflater = LayoutInflater.from(context);
    }

    public void setmList(List<CompanyOrder> mList) {
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
            view = inflater.inflate(R.layout.adapter_companyorder, viewGroup, false);
            holder = new ViewHolder(view);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        CompanyOrder order = (CompanyOrder) getItem(i);

        //提货地址
        if (order.address != null) {
            holder.tvAdress.setText("提货地址：" + order.address);
        }

        //'0'未交定金未支付','1' '已交定金未支付', '2'已支付','3' '待发货',
        // '4' '待收货', '5' '完成','6' '订单取消',7已送达
        if (order.status == 0 || order.status == 1) {
            holder.linPay.setVisibility(View.VISIBLE);
            holder.linPick.setVisibility(View.GONE);
            holder.tvClear.setVisibility(View.GONE);
            holder.tvCancle.setVisibility(View.VISIBLE);
            holder.tvPay.setVisibility(View.VISIBLE);
            holder.tvDriver.setVisibility(View.GONE);
            holder.tvSubmit.setVisibility(View.GONE);
            holder.tvLogists.setVisibility(View.GONE);

            holder.tvState.setText("未付款");
            holder.tvCancle.setTag(i);
            holder.tvCancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int n = (int) view.getTag();
                    companyOrderListener.cancle(n);
                }
            });
            holder.tvPay.setTag(i);
            holder.tvPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int n = (int) view.getTag();
                    companyOrderListener.pay(n);
                }
            });
        } else if (order.status == 2 || order.status == 3 || order.status == 5) {
            if (order.status == 2) {
                holder.linPick.setVisibility(View.GONE);
            } else {
                holder.linPick.setVisibility(View.VISIBLE);

            }
            if (order.status == 3) {
                holder.tvLogists.setVisibility(View.VISIBLE);
            } else {
                holder.tvLogists.setVisibility(View.GONE);
            }
            if (order.status == 3 || order.status == 5) {
                holder.tvDriver.setVisibility(View.VISIBLE);
            } else {
                holder.tvDriver.setVisibility(View.GONE);
            }
            holder.linPay.setVisibility(View.GONE);
            holder.tvClear.setVisibility(View.GONE);
            holder.tvCancle.setVisibility(View.GONE);
            holder.tvPay.setVisibility(View.GONE);
            holder.tvDriver.setVisibility(View.VISIBLE);
            holder.tvSubmit.setVisibility(View.GONE);
            holder.tvLogists.setVisibility(View.GONE);

            if (order.status == 2) {
                holder.tvState.setText("已付款");
            } else if (order.status == 3) {
                holder.tvState.setText("未提货");
            } else {
                holder.tvState.setText("已完成");
            }

            holder.tvDriver.setTag(i);
            holder.tvDriver.setTextColor(Color.parseColor("#FC4F4F"));
            holder.tvDriver.setBackgroundResource(R.drawable.order_btn_bg_red);
            holder.tvDriver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int n = (int) view.getTag();
                    companyOrderListener.toDriverMsg(n);
                }
            });

            holder.tvLogists.setTag(i);
            holder.tvLogists.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int n = (int) view.getTag();
                    companyOrderListener.toDriverLocation(n);
                }
            });

        } else if (order.status == 7) {
            holder.tvDriver.setVisibility(View.VISIBLE);
            holder.tvLogists.setVisibility(View.GONE);
            holder.tvSubmit.setVisibility(View.VISIBLE);
            holder.linPick.setVisibility(View.VISIBLE);
            holder.tvClear.setVisibility(View.GONE);
            holder.tvCancle.setVisibility(View.GONE);
            holder.tvPay.setVisibility(View.GONE);

            holder.tvState.setText("已送达");
            holder.tvDriver.setTextColor(Color.parseColor("#333333"));
            holder.tvDriver.setBackgroundResource(R.drawable.order_btn_bg_black);


            holder.tvDriver.setTag(i);
            holder.tvDriver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int n = (int) view.getTag();
                    companyOrderListener.toDriverMsg(n);
                }
            });
            holder.tvSubmit.setTag(i);
            holder.tvSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int n = (int) view.getTag();
                    companyOrderListener.config(n);
                }
            });

        } else if (order.status == 6) {
            holder.linPick.setVisibility(View.GONE);
            holder.linPay.setVisibility(View.VISIBLE);
            holder.tvClear.setVisibility(View.VISIBLE);
            holder.tvCancle.setVisibility(View.GONE);
            holder.tvPay.setVisibility(View.GONE);
            holder.tvDriver.setVisibility(View.GONE);
            holder.tvSubmit.setVisibility(View.GONE);
            holder.tvLogists.setVisibility(View.GONE);
            holder.tvState.setText("已取消");

            holder.tvClear.setTag(i);
            holder.tvClear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int n = (int) view.getTag();
                    companyOrderListener.delete(n);
                }
            });
        } else if (order.status == 4) {
            holder.linPick.setVisibility(View.VISIBLE);
            holder.linPay.setVisibility(View.GONE);
            holder.tvClear.setVisibility(View.GONE);
            holder.tvCancle.setVisibility(View.GONE);
            holder.tvPay.setVisibility(View.GONE);
            holder.tvDriver.setVisibility(View.GONE);
            holder.tvSubmit.setVisibility(View.GONE);
            holder.tvLogists.setVisibility(View.VISIBLE);
            holder.tvState.setText("已提货");
            //提货地址
            if (order.toplace != null) {
                holder.tvAdress.setText("送货地址：" + order.toplace + order.toaddress);
            }

            holder.tvLogists.setVisibility(View.VISIBLE);
            holder.tvDriver.setVisibility(View.VISIBLE);

            holder.tvLogists.setTag(i);
            holder.tvLogists.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int n = (int) view.getTag();
                    companyOrderListener.toDriverLocation(n);
                }
            });

            holder.tvDriver.setTag(i);
            holder.tvDriver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int n = (int) view.getTag();
                    companyOrderListener.toDriverMsg(n);
                }
            });
        }


        // 有无司机 司机信息显示
        if (order.driver_id.equals("") || order.driver_id.equals("0")) {
            holder.tvDriver.setVisibility(View.GONE);
        }
        //订单编号
        if (order.no != null) {
            holder.tvOrderNum.setText("订单编号：" + order.no);
        }
        //货物名称
        if (order.getGoodsname() != null) {
            holder.tvName.setText(order.getGoodsname());
            if (order.getNumber() != 0) {
                holder.tvName.setText(order.getGoodsname() + "  " + order.getNumber() + "吨");
            }
        }
        //订单时间
        if (order.time != 0) {
            holder.tvTime.setText(DateTimeUtil.stampToDate("yyyy/MM/dd HH:mm:ss", order.time + "000"));
        }
        //订单编号
        if (order.no != null) {
            holder.tvOrderNum.setText("订单编号：" + order.no);
        }

        // 提货联系人+手机号
        if (order.toname != null) {
            holder.tvClient.setText(order.company + " " + order.phone);
        }
        //提货公司
        if (order.tocompany != null) {
            holder.tvCompany.setText(order.company);
        }

        //订单价格
        if (order.money != null) {
            holder.tvPrice.setText(order.money);
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
                companyOrderListener.details(n);
            }
        });

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tv_orderNum_company_adpater)
        TextView tvOrderNum;
        @BindView(R.id.tv_state_company_adpater)
        TextView tvState;
        @BindView(R.id.tv_name_company_adpater)
        TextView tvName;
        @BindView(R.id.tv_time_company_adpater)
        TextView tvTime;
        @BindView(R.id.tv_client_company_adpater)
        TextView tvClient;
        @BindView(R.id.tv_companyName_company_adpater)
        TextView tvCompany;
        @BindView(R.id.tv_phone_company_adpater)
        TextView tvPhone;
        @BindView(R.id.tv_adress_company_adpater)
        TextView tvAdress;
        @BindView(R.id.tv_price_company_adpater)
        TextView tvPrice;
        @BindView(R.id.tv_clear_company_adpater)
        TextView tvClear;
        @BindView(R.id.tv_cancle_company_adpater)
        TextView tvCancle;
        @BindView(R.id.tv_pay_company_adpater)
        TextView tvPay;
        @BindView(R.id.lin_pay_company_adpater)
        LinearLayout linPay;
        @BindView(R.id.tv_logists_company_adpater)
        TextView tvLogists;
        @BindView(R.id.tv_driver_company_adpater)
        TextView tvDriver;
        @BindView(R.id.tv_submit_company_adpater)
        TextView tvSubmit;
        @BindView(R.id.lin_pick_company_adpater)
        LinearLayout linPick;
        @BindView(R.id.lin_order_details)
        LinearLayout linOrder;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private CompanyOrderListener companyOrderListener;

    public void setCompanyOrderListener(CompanyOrderListener companyOrderListener) {
        this.companyOrderListener = companyOrderListener;
    }

    public interface CompanyOrderListener {
        void toDriverMsg(int n);

        void toDriverLocation(int n);

        void cancle(int n);

        void pay(int n);

        void delete(int n);

        void config(int n);

        void details(int n);
    }

}
