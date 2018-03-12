package com.zpf.oillogistics.diy;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zpf.oillogistics.R;

/**
 * Created by Administrator on 2017/9/14.
 *
 * 自定义toast
 */

public class DiyToast {

    public static void centerToast(Context context, String content) {
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        View view = LayoutInflater.from(context).inflate(R.layout.view_center_toast, null);
        TextView toastTv = view.findViewById(R.id.center_toast_tv);
        toastTv.setText(content);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
}
