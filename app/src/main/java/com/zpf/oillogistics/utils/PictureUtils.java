package com.zpf.oillogistics.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by Administrator on 2017/10/10.
 */

public class PictureUtils {


    public static String toBase64Str(String url){

        Bitmap bitmap = BitmapFactory.decodeFile(url);
        //第一步:将Bitmap压缩至字节数组输出流ByteArrayOutputStream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        //第二步:利用Base64将字节数组输出流中的数据转换成字符串String
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return new String(Base64.encodeToString(byteArray, Base64.DEFAULT));

    }

}
