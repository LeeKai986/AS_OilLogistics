package com.zpf.oillogistics.base;

import android.net.Uri;
import android.os.Environment;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.compress.CompressConfig;
import org.devio.takephoto.model.CropOptions;
import org.devio.takephoto.model.LubanOptions;
import org.devio.takephoto.model.TakePhotoOptions;

import java.io.File;


/**
 * - 支持通过相机拍照获取图片
 * - 支持从相册选择图片
 * - 支持从文件选择图片
 * - 支持多图选择
 * - 支持批量图片裁切
 * - 支持批量图片压缩
 * - 支持对图片进行压缩
 * - 支持对图片进行裁剪
 * - 支持对裁剪及压缩参数自定义
 * - 提供自带裁剪工具(可选)
 * - 支持智能选取及裁剪异常处理
 * - 支持因拍照Activity被回收后的自动恢复
 * Author: crazycodeboy
 * Date: 2016/9/21 0007 20:10
 * Version:4.0.0
 * 技术博文：http://www.devio.org
 * GitHub:https://github.com/crazycodeboy
 * Email:crazycodeboy@gmail.com
 */
public class CustomHelper {

    public static CustomHelper init() {
        return new CustomHelper();
    }


    public void onClick(TakePhoto takePhoto, int type, int width, int heigh) {
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        Uri imageUri = Uri.fromFile(file);

        configCompress(takePhoto);
        configTakePhotoOption(takePhoto);

        switch (type) {
            case 1://相册
                takePhoto.onPickFromGalleryWithCrop(imageUri, getCropOptions(width, heigh));
                break;
            case 2://拍照
                takePhoto.onPickFromCaptureWithCrop(imageUri, getCropOptions(width, heigh));
                break;
            default:
                break;
        }
    }

    private void configTakePhotoOption(TakePhoto takePhoto) {
        TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
        builder.setWithOwnGallery(false);//系统相册 false
        builder.setCorrectImage(false);//不纠正拍照角度 false
        takePhoto.setTakePhotoOptions(builder.create());

    }

    private void configCompress(TakePhoto takePhoto) {
        takePhoto.onEnableCompress(null, true);//是否压缩
        int maxSize = 1024 * 300;//图片大小
        int width = 800;//图片宽度
        int height = 800;//图片高度
        boolean showProgressBar = true;//是否显示压缩进度条
        boolean enableRawFile = true;//压缩后是否保存图片
        CompressConfig config;
//        if (rgCompressTool.getCheckedRadioButtonId() == R.id.rbCompressWithOwn) {//自带压缩工具
//            config = new CompressConfig.Builder().setMaxSize(maxSize)
//                    .setMaxPixel(width >= height ? width : height)
//                    .enableReserveRaw(enableRawFile)
//                    .create();
//        } else {//鲁班压缩工具
        LubanOptions option = new LubanOptions.Builder().setMaxHeight(height).setMaxWidth(width).setMaxSize(maxSize).create();
        config = CompressConfig.ofLuban(option);
        config.enableReserveRaw(enableRawFile);
//        }
        takePhoto.onEnableCompress(config, showProgressBar);


    }

    /**
     * Bitmap转码
     *
     * @return
     */


    private CropOptions getCropOptions(int cwidth, int cheigh) {
        int height = cheigh;
        int width = cwidth;
        boolean withWonCrop = false;//裁剪工具 true  原生裁剪
        CropOptions.Builder builder = new CropOptions.Builder();
        //宽高比 true
        builder.setAspectX(width).setAspectY(height);
        builder.setWithOwnCrop(withWonCrop);
        return builder.create();
    }

}
