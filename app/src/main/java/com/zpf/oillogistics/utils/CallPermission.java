package com.zpf.oillogistics.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

/**
 * Created by Administrator on 2017/9/22.
 */

public class CallPermission {

    public static void callPhone(Context context, String phone) {
        if (TextUtils.isEmpty(phone)) {
        } else {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
            context.startActivity(intent);
        }
    }

}
