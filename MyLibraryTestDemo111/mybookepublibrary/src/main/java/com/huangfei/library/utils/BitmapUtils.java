package com.huangfei.library.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2016/4/15.
 * 图片处理类
 */
public class BitmapUtils {

    /**
     * 将图片压缩到小于200k
     *
     * @param srcFile 源文件
     * @param file    目标文件
     */
    public static boolean compressBmpToFile(String srcFile, File file) {
        BitmapFactory.Options option = new BitmapFactory.Options();
        // 只读边不读内容
        option.inJustDecodeBounds = true;
        // 创建Bitmap对象
        Bitmap bm = BitmapFactory.decodeFile(srcFile, option);
        option.inJustDecodeBounds = false;
        int w = option.outWidth;
        int h = option.outHeight;
        float ww = 480f, hh = 800f;
        // 设置缩放比
        int be = 1;
        if (w > h && w > ww) {
            be = (int) (option.outWidth / ww);
        } else if (w < h && h > hh) {
            be = (int) (option.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        option.inSampleSize = be;
        try {
            bm = BitmapFactory.decodeFile(srcFile, option);
        } catch (OutOfMemoryError e) {
            return false;
        }

        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 80;
        bm.compress(Bitmap.CompressFormat.JPEG, options, baos);
        while (baos.toByteArray().length / 1024 > 200) {
            baos.reset();
            options -= 10;
            bm.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }

        try {
            FileOutputStream output = new FileOutputStream(file);
            output.write(baos.toByteArray());
            output.flush();
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            bm.recycle();
        }

        return true;
    }

    /**
     * 将bitmap保存到本地
     *
     * @param bitmap
     * @param path
     * @throws IOException
     */
    public static void saveBitmap(Bitmap bitmap, String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)) {
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
