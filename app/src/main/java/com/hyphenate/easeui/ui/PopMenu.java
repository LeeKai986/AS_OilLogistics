package com.hyphenate.easeui.ui;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hyphenate.easeui.domain.MenuItem;
import com.zpf.oillogistics.R;

import java.util.ArrayList;

public class PopMenu {
    private ArrayList<MenuItem> itemList;
    private Context context;
    private PopupWindow popupWindow;
    private ListView listView;
    public boolean isShowing = false;

    // private OnItemClickListener listener;

    public PopMenu(Context context) {
        // TODO Auto-generated constructor stub
        this.context = context;

        itemList = new ArrayList<MenuItem>();

        MenuItem menu1 = new MenuItem();
        menu1.name = "添加好友";
        menu1.drID = R.mipmap.add_friends;
        itemList.add(menu1);
        MenuItem menu2 = new MenuItem();
        menu2.name = "创建群组";
        menu2.drID = R.mipmap.group_create;
        itemList.add(menu2);

        View view = LayoutInflater.from(context).inflate(
                R.layout.list_screen_child, null);

        // 设置 listview
        listView = (ListView) view.findViewById(R.id.lv_sreen_child);
        listView.setAdapter(new PopAdapter());

        popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        // popupWindow.setAnimationStyle(R.style.AnimationFade);
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景（很神奇的）
        ColorDrawable dw = new ColorDrawable(0x00000000);
        popupWindow.setBackgroundDrawable(dw);
//        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
    }

    // 设置菜单项点击监听器
    public void setOnItemClickListener(OnItemClickListener listener) {
        // this.listener = listener;
        listView.setOnItemClickListener(listener);
    }

    // 批量添加菜单项
    public void addItems(String[] items) {
//		for (String s : items)
//			itemList.add(s);
    }

    // 单个添加菜单项
    public void addItem(String item) {
//		itemList.add(item);
    }

    // 下拉式 弹出 pop菜单 parent 右下角
    public void showAsDropDown(View parent) {

        popupWindow.showAsDropDown(parent, getScreenWidth(context) - 200,
                // 保证尺寸是根据屏幕像素密度来的
                0);

        // 使其聚集
        popupWindow.setFocusable(true);
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);
        // 刷新状态
        popupWindow.update();
        isShowing = true;
    }

    // 隐藏菜单
    public void dismiss() {
        popupWindow.dismiss();
        isShowing = false;
    }

    public int getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getWidth();
    }

    // 适配器
    private class PopAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return itemList.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return itemList.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(
                        R.layout.pomenu_item, null);
                holder = new ViewHolder();

                convertView.setTag(holder);

                holder.tvName = (TextView) convertView
                        .findViewById(R.id.tv_popmenu);
                holder.ivItem = (ImageView) convertView.findViewById(R.id.iv_popmenu);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tvName.setText(itemList.get(position).getName());
            holder.ivItem.setBackgroundResource(itemList.get(position).drID);

            return convertView;
        }

        private final class ViewHolder {
            TextView tvName;
            ImageView ivItem;
        }
    }

}
