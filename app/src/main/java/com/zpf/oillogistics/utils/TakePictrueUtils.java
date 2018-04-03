package com.zpf.oillogistics.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;

import com.zpf.oillogistics.base.Constant;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Administrator on 2017/9/22.
 */

public class TakePictrueUtils {

    public static final int PHOTO_CAMERA = 0;    //相机拍照
    public static final int PHOTO_WALL = 1;      //相册
    public static final int PHOTO_STORE = 2;     //图片存储
    public static final int PHOTO_NOT_STORE = 3;   //图片不存储

    private Context context;

    public Uri imageUri;
    public File tempFile;
    private String img_url = "";
    private String imageFlag = "";
    private Bitmap bitmap;


    public TakePictrueUtils(Context context, String imageFlag) {
        this.context = context;
        this.imageFlag = imageFlag;

        //创建本地图片存储文件
//        File dir = new File(Constant.APK_IMAG_URI);
//        if (!dir.exists())
//            dir.mkdirs();
        //调用系统的拍照功能
//        tempFile = new File(Constant.APK_IMAG_URI,
//                imageFlag + System.currentTimeMillis() + ".jpg");
//        img_url = tempFile.getAbsolutePath();
//        try {
//            if (tempFile.exists()) {
//                tempFile.delete();
//            }
//            tempFile.createNewFile();
//        } catch (IOException e) {
//        }
//        //文件路径
//        imageUri = Uri.fromFile(tempFile);
    }

    /**
     * 进入照相机
     */
    public void startCamera() {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        // 系统版本大于N的统一用FileProvider处理
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
//
//            // 将文件转换成content://Uri的形式
//            Uri photoURI = FileProvider.getUriForFile(context,
//                    "com.zpf.oillogistics.fileprovider",
//                    new File(img_url));
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//        } else {
//            intent.addCategory(Intent.CATEGORY_DEFAULT);
//            Uri uri = Uri.parse("file://" + Constant.APK_CASHE_URI);
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//        }
//        intent.putExtra("camerasensortype", 2);//调用前置摄像头
//        intent.putExtra("autofocus", true);//进行自动对焦操作
//        intent.putExtra("fullScreen", false);//设置全屏
//        intent.putExtra("showActionIcons", false);
////        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//指定调用相机之后所拍照存储到的位置
//        ((Activity) context).startActivityForResult(intent, PHOTO_CAMERA);
        // 设置相机拍照后照片保存路径
        tempFile = new File(Constant.APK_IMAG_URI,
                "/picture" + System.currentTimeMillis() / 1000 + ".jpg");
        // 启动拍照,并保存到临时文件
        Intent mIntent = new Intent();
        mIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        mIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        mIntent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        ((Activity) context).startActivityForResult(mIntent, PHOTO_CAMERA);


    }

    /**
     * 进入相册
     */
    public void startWall() {
//
//        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//        ((Activity) context).startActivityForResult(intent, PHOTO_WALL);
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        ((Activity) context).startActivityForResult(intent, PHOTO_WALL);

    }


    private void updateGallery(String filename) {
        MediaScannerConnection.scanFile(context, new String[]{filename}, null,
                new MediaScannerConnection.OnScanCompletedListener() {

                    @Override
                    public void onScanCompleted(String path, Uri uri) {

                    }
                });
    }

    /**
     * 裁剪图片
     *
     * @param uri
     * @param size
     * @param flag
     */
    public void startPhotoCut(Uri uri, int size, boolean flag) {
        String fileSrc;
        if (uri != null) {
            if ("file".equals(uri.getScheme())) {
                // 有些低版本机型返回的Uri模式为file
                fileSrc = uri.getPath();
            } else {
                // Uri模型为content
                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor cursor = context.getContentResolver().query(uri, proj,
                        null, null, null);
                cursor.moveToFirst();
                int idx = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                fileSrc = cursor.getString(idx);

                cursor.close();
            }
        } else {
            fileSrc = tempFile.getAbsolutePath();
        }
        Uri ruri = Uri.fromFile(new File(fileSrc));
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        //        content://com.miui.gallery.open/raw/%2Fstorage%2Femulated%2F0%2FDCIM%2FCamera%2F1516015162777.jpg
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
////            if (!uri.toString().substring(0, 7).equals("content")) {
////                if (uri.toString().substring(0, 10).contains("f")) {
////                    String uriStr = uri.toString().replace("file://", "");
////                    Uri uriC = FileProvider.getUriForFile(context, "com.zpf.oillogistics.fileprovider", new File(uriStr));
////                    intent.setDataAndType(uriC, "image/*");
////                }
////            } else {
//            intent.setDataAndType(uri, "image/*");
////            }
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//            Uri outPutUri = Uri.fromFile(tempFile);
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutUri);
//
//        } else {
//            intent.setDataAndType(uri, "image/*");
//            Uri outPutUri = Uri.fromFile(tempFile);
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutUri);
//        }
//        intent.putExtra("crop", true);//设置Intent中的view是可以裁剪的
//        //设置宽高比
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
//        //设置裁剪图片的宽高
//        intent.putExtra("outputX", size);
//        intent.putExtra("outputY", size);
//        //设置是否返回数据
//        intent.putExtra("return-data", true);

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(ruri, "image/*");
        intent.putExtra("crop", "true");// 才能出剪辑的小方框，不然没有剪辑功能，只能选取图片
        intent.putExtra("aspectX", 1); // 放大缩小比例的X
        intent.putExtra("aspectY", 1);// 放大缩小比例的X   这里的比例为：   1:1
        intent.putExtra("outputX", 400);  //这个是限制输出图片大小
        intent.putExtra("outputY", 400);
        intent.putExtra("return-data", true);//不回传bigmap 容易崩溃...
        // 切图大小不足输出，无黑框
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        String imagePath = getImagePath(context);
        File imageFile = new File(imagePath);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, img_url);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());


        if (flag == true)                    //存储
            ((Activity) context).startActivityForResult(intent, PHOTO_STORE);
        else {
            Intent tempIntent = intent;
            try {                             //不存储
                ((Activity) context).startActivityForResult(tempIntent, PHOTO_NOT_STORE);
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }

    /**
     * 保存裁剪的图片的路径
     *
     * @return
     */
    public String getImagePath(Context context) {
        String path = null;

        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            path = context.getFilesDir().getAbsolutePath();
        } else {
            path = Constant.APK_IMAG_URI;
        }

        if (!path.endsWith("/")) {
            path += "/";
        }

        File folder = new File(path);
        if (folder != null && !folder.exists()) {
            folder.mkdirs();
        }
        path += "sht.jpg";
        Log.d("path", path);
        return path;
    }

    /**
     * 裁剪图片
     *
     * @param uri
     * @param wide
     * @param high
     * @param flag
     */
    public void startPhotoCut(Uri uri, int wide, int high, boolean flag) {
        String fileSrc;
        if (uri != null) {
            if ("file".equals(uri.getScheme())) {
                // 有些低版本机型返回的Uri模式为file
                fileSrc = uri.getPath();
            } else {
                // Uri模型为content
                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor cursor = context.getContentResolver().query(uri, proj,
                        null, null, null);
                cursor.moveToFirst();
                int idx = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                fileSrc = cursor.getString(idx);

                cursor.close();
            }
        } else {
            fileSrc = tempFile.getAbsolutePath();
        }
        Uri ruri = Uri.fromFile(new File(fileSrc));
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(ruri, "image/*");
        intent.putExtra("crop", "true");// 才能出剪辑的小方框，不然没有剪辑功能，只能选取图片
        intent.putExtra("aspectX", 9); // 放大缩小比例的X
        intent.putExtra("aspectY", 5);// 放大缩小比例的X   这里的比例为：   1:1
        intent.putExtra("outputX", wide);  //这个是限制输出图片大小
        intent.putExtra("outputY", high);
        intent.putExtra("return-data", true);//不回传bigmap 容易崩溃...
        // 切图大小不足输出，无黑框
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        String imagePath = getImagePath(context);
        File imageFile = new File(imagePath);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, img_url);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());


        if (flag == true)                    //存储
            ((Activity) context).startActivityForResult(intent, PHOTO_STORE);
        else {
            Intent tempIntent = intent;
            try {                             //不存储
                ((Activity) context).startActivityForResult(tempIntent, PHOTO_NOT_STORE);
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }

    /**
     * 转换成Bitmap
     *
     * @param data
     * @param flag
     * @return
     */
    public Bitmap setPictureToImageView(Intent data, boolean flag) {
        Bundle bundle = data.getExtras();

        if (null != bundle) {
            bitmap = bundle.getParcelable("data");
            // 将Bitmap转换成字符串
//            photo1 = bitmaptoString(bitmap, 1);
            //pic_head.setImageBitmap(bmpreal);//将图片显示到ImageView上面

            if (flag == true)
                savePictureToSD(bitmap);//保存图片到sd卡上面
        } else {
            bitmap = BitmapFactory.decodeFile(getImagePath(context));//filePath
            if (flag == true) {
                savePictureToSD(bitmap);//保存图片到sd卡上面
            }
        }
        return bitmap;
    }

    /**
     * Bitmap转码
     *
     * @return
     */
    public String bitmaptoString() {
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        if (bitmap != null) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bStream);
            byte[] bytes = bStream.toByteArray();
            return Base64.encodeToString(bytes, Base64.DEFAULT);
        }
        return null;
    }


    /**
     * 保存至本地
     *
     * @param bitmap
     */
    public void savePictureToSD(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FileOutputStream fos = null;
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//第2个参数表示压缩率，100表示不压缩
        try {
            fos = new FileOutputStream(tempFile);
            fos.write(baos.toByteArray());
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != baos) {
                    baos.close();
                    baos = null;
                }
                if (null != fos) {
                    fos.close();
                    fos = null;
                }
            } catch (Exception e2) {

            }
        }
    }
}
