package com.seven.lib_common.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;


import com.seven.lib_common.config.RunTimeConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2018/4/12
 */

public class SaveUtils {

    private static SaveUtils saveUtils;

    public SaveUtils() {

    }

    public static SaveUtils getInstance() {

        if (saveUtils == null) {

            synchronized (SaveUtils.class) {

                saveUtils = new SaveUtils();

            }
        }
        return saveUtils;
    }

    public void isExists(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public String getCameraVideo() {
        String dir = RunTimeConfig.PathConfig.CAMERA_VIDEO_PATH;
        isExists(dir);

        return dir + System.currentTimeMillis() + ".mp4";
    }

    public String getCameraImage() {
        String dir = RunTimeConfig.PathConfig.CAMERA_IMAGE_PATH;
        isExists(dir);

        return dir + System.currentTimeMillis() + ".png";
    }

    public String getTempDir() {
        String dir = RunTimeConfig.PathConfig.TEMP_PATH;
        isExists(dir);
        return dir;
    }

    public String getTempVideo() {
        String dir = RunTimeConfig.PathConfig.TEMP_PATH;
        isExists(dir);
        return dir + System.currentTimeMillis() + ".mp4";
    }

    public String getTempImage() {
        String dir = RunTimeConfig.PathConfig.TEMP_PATH;
        isExists(dir);
        return dir + System.currentTimeMillis() + ".png";
    }

    public String downloadApk(String version) {
        String dir = RunTimeConfig.PathConfig.APK_DOWNLOAD_PATH;
        isExists(dir);

        return dir + version + ".apk";
    }

    public boolean saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "dearxy";
        File appDir = new File(storePath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //通过io流的方式来压缩保存图片
            boolean isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 60, fos);
            fos.flush();
            fos.close();

            //把文件插入到系统图库
            //MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);

            //保存图片后发送广播通知更新数据库
            Uri uri = Uri.fromFile(file);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            if (isSuccess) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
