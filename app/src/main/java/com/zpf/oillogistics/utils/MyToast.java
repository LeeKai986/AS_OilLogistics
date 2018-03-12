package com.zpf.oillogistics.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zpf.oillogistics.R;

/**
 * Created by wjw on 2017/2/27.
 */

public class MyToast {
    private static Toast toast;

    public static void show(Context context, String msg) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        } else {
            toast.cancel();
            toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        }
        toast.show();
    }

}
