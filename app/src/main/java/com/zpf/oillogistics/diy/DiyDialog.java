package com.zpf.oillogistics.diy;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.zpf.oillogistics.R;
import com.zpf.oillogistics.base.CyApplication;
import com.zpf.oillogistics.utils.MyShareFriend;
import com.zpf.oillogistics.utils.MyToast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/13.
 * <p>
 * 自定义dialog
 */

public class DiyDialog {

    public static void singleSelectDialog(Context context, List<String> list, final SingleSelectListener singleSelectListener) {
        final Dialog dialog = new Dialog(context, R.style.diy_dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_identity_register, null);
        FrameLayout fl = view.findViewById(R.id.identity_fl);
        TextView cancelTv = view.findViewById(R.id.identity_cancel_tv);
        TextView confirmTv = view.findViewById(R.id.identity_confirm_tv);
        PickerView pv = view.findViewById(R.id.identity_pv);
        dialog.setContentView(view);
        pv.setData(list);
        final String[] identity = {list.get(0)};
        fl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        pv.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                identity[0] = text;
            }
        });
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        confirmTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleSelectListener.SingleSelect(identity[0]);
                dialog.dismiss();
            }
        });
        dialog.show();
        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (int) (display.getWidth()); //设置宽度
        dialog.getWindow().setAttributes(lp);
    }

    public static void tweSelectDialog(Context context, int oneArrResources, int tweArrResources, final TweSelectListener tweSelectListener) {
        final Dialog dialog = new Dialog(context, R.style.diy_dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_product_type, null);
        FrameLayout fl = view.findViewById(R.id.product_fl);
        TextView cancelTv = view.findViewById(R.id.product_cancel_tv);
        TextView confirmTv = view.findViewById(R.id.product_confirm_tv);
        PickerView onePv = view.findViewById(R.id.product_one_pv);
        final PickerView twePv = view.findViewById(R.id.product_twe_pv);
        dialog.setContentView(view);
        final String[] typeArr = {"", ""};
        final String[] oneTypeArr = context.getResources().getStringArray(oneArrResources);
        final String[] tweAllTypeArr = context.getResources().getStringArray(tweArrResources);
        List<String> oneTypes = new ArrayList<>();
        List<String> firstTweTypes = new ArrayList<>();
        for (int i = 0; i < oneTypeArr.length; i++) {
            oneTypes.add(oneTypeArr[i]);
        }
        String[] firstTypeArr = tweAllTypeArr[0].split(",");
        for (int l = 0; l < firstTypeArr.length; l++) {
            firstTweTypes.add(firstTypeArr[l]);
        }
        onePv.setData(oneTypes);
        twePv.setData(firstTweTypes);
        typeArr[0] = oneTypes.get(0);
        typeArr[1] = firstTweTypes.get(0);
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
                for (int i = 0; i < oneTypeArr.length; i++) {
                    if (text.equals(oneTypeArr[i])) {
                        String[] tweTypeArr = tweAllTypeArr[i].split(",");
                        List<String> tweTypes = new ArrayList<>();
                        for (int l = 0; l < tweTypeArr.length; l++) {
                            tweTypes.add(tweTypeArr[l]);
                        }
                        twePv.setData(tweTypes);
                        typeArr[1] = tweTypes.get(0);
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
                tweSelectListener.tweSelect(typeArr[0] + "  " + typeArr[1]);
                dialog.dismiss();
            }
        });
        dialog.show();
        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (int) (display.getWidth()); //设置宽度
        dialog.getWindow().setAttributes(lp);
    }

    public static void dateSelectDialog(Context context, final GetTimeListener getTimeListener) {
        final Dialog dialog = new Dialog(context, R.style.diy_dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_time_select, null);
        FrameLayout fl = view.findViewById(R.id.product_fl);
        TextView cancelTv = view.findViewById(R.id.product_cancel_tv);
        TextView confirmTv = view.findViewById(R.id.product_confirm_tv);
        final DatePicker datePicker = view.findViewById(R.id.dialog_dp);
        dialog.setContentView(view);
        fl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
//        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
//            @Override
//            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
//                dialog.dismiss();
//                getTimeListener.getTime(i + "/" + (i1 + 1) + "/" + i2);
//            }
//        });
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        confirmTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTimeListener.getTime(datePicker.getYear() + "/" + (datePicker.getMonth() + 1) + "/" + datePicker.getDayOfMonth());
                dialog.dismiss();
            }
        });
        dialog.show();
        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (int) (display.getWidth()); //设置宽度
        dialog.getWindow().setAttributes(lp);
    }

    public static void routeTypeDialog(Context context, final RouteTypeListener routeTypeListener) {
        final Dialog dialog = new Dialog(context, R.style.diy_dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_route_type, null);
        FrameLayout fl = view.findViewById(R.id.product_fl);
        TextView cancelTv = view.findViewById(R.id.product_cancel_tv);
        TextView confirmTv = view.findViewById(R.id.product_confirm_tv);
        GridView gv = view.findViewById(R.id.dialog_route_gv);
        final String[] res = {""};

        final ArrayList<String> list = new ArrayList<>();
        list.add("第一类");
        list.add("第二类");
        list.add("第三类");
        list.add("第四类");
        list.add("第五类");
        list.add("第六类");
        list.add("第七类");
        list.add("第八类");
        list.add("第九类");
        final RouteTypeAdapter adapter = new RouteTypeAdapter(list, context);
        gv.setAdapter(adapter);
        dialog.setContentView(view);

        fl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                res[0] = list.get(i);
                adapter.setItem(res[0]);
                adapter.notifyDataSetChanged();
            }
        });
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        confirmTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                routeTypeListener.routeType(res[0]);
                dialog.dismiss();
            }
        });

        dialog.show();
        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (int) (display.getWidth()); //设置宽度
        dialog.getWindow().setAttributes(lp);
    }

    public static void hintTweBtnDialog(Context context, String content, final HintTweBtnListener hintTweBtnListener) {
        final Dialog dialog = new Dialog(context, R.style.diy_dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_hint_twe_btn, null);
        TextView titleTv = view.findViewById(R.id.dialog_title_tv);
        TextView cancelTv = view.findViewById(R.id.dialog_cancel_tv);
        TextView confirmTv = view.findViewById(R.id.dialog_confirm_tv);
        titleTv.setText(content);
        dialog.setContentView(view);
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        confirmTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hintTweBtnListener.confirm();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void hintOneBtnDialog(Context context, String content, final HintTweBtnListener hintTweBtnListener) {
        final Dialog dialog = new Dialog(context, R.style.diy_dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_hint_twe_btn, null);
        TextView titleTv = view.findViewById(R.id.dialog_title_tv);
        TextView cancelTv = view.findViewById(R.id.dialog_cancel_tv);
        TextView confirmTv = view.findViewById(R.id.dialog_confirm_tv);
        cancelTv.setVisibility(View.GONE);
        titleTv.setText(content);
        dialog.setContentView(view);
//        cancelTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
        confirmTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hintTweBtnListener.confirm();
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
    }

    public static void shareDialog(final Context context, final ShareListener shareListener) {
        final Dialog dialog = new Dialog(context, R.style.diy_dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_share, null);
        TextView friendIv = view.findViewById(R.id.share_friend_tv);
        TextView circleTv = view.findViewById(R.id.share_circle_tv);
        TextView cancelTv = view.findViewById(R.id.share_cancel_tv);
        dialog.setContentView(view);
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        friendIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyShareFriend.weixinFriend(context, new MyShareFriend.OnMyShareFriendListener() {
                    @Override
                    public void success() {
                        shareListener.friend();
                    }

                    @Override
                    public void error() {
                        MyToast.show(context, "分享失败");
                    }
                });
            }
        });
        circleTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyShareFriend.weixinCircle(context, new MyShareFriend.OnMyShareFriendListener() {
                    @Override
                    public void success() {
                        shareListener.circle();
                    }

                    @Override
                    public void error() {
                        MyToast.show(context, "分享失败");
                    }
                });
            }
        });
        dialog.show();
        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (int) (display.getWidth()); //设置宽度
        dialog.getWindow().setAttributes(lp);
    }

    /**
     * adater
     */
    // 行程类型
    static class RouteTypeAdapter extends BaseAdapter {

        private ArrayList<String> list;
        private Context context;
        private String item = "";

        public RouteTypeAdapter(ArrayList<String> list, Context context) {
            this.list = list;
            this.context = context;
        }

        @Override
        public int getCount() {
            return list.size();
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
            RouteTypeViewHolder vh;
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.item_route_type, viewGroup, false);
                vh = new RouteTypeViewHolder();
                vh.routeTypeTv = view.findViewById(R.id.item_route_type_tv);
                view.setTag(vh);
            } else {
                vh = (RouteTypeViewHolder) view.getTag();
            }
            if (list.get(i).equals(item))
                vh.routeTypeTv.setTextColor(Color.parseColor("#FC4F4F"));
            else
                vh.routeTypeTv.setTextColor(Color.parseColor("#333333"));
            vh.routeTypeTv.setText(list.get(i));
            return view;
        }

        public void setItem(String item) {
            this.item = item;
        }

        class RouteTypeViewHolder {
            TextView routeTypeTv;
        }
    }

    /**
     * dialog接口
     */
    // 单选器接口
    public interface SingleSelectListener {
        void SingleSelect(String res);
    }

    // 双选器接口
    public interface TweSelectListener {
        void tweSelect(String res);
    }

    // 日期选择器
    public interface GetTimeListener {
        void getTime(String res);
    }

    // 行程分类
    public interface RouteTypeListener {
        void routeType(String res);
    }

    // 仿苹果提示框
    public interface HintTweBtnListener {
        void confirm();
    }

    // 分享选择器
    public interface ShareListener {
        void friend();

        void circle();
    }
}
