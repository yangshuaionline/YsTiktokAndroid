package com.yangshuai.library.base.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.yangshuai.library.base.BaseConst;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author hcp
 * on 2019-07-27
 */
public class ViewUtils {


    /**
     * 给textView 右侧显示图片
     * @param context
     * @param tartView
     * @param resId
     * @param textValue
     */
    public static void setTextViewRightImage(Context context,TextView tartView, int resId, String textValue) {
        tartView.setText(textValue);
        Drawable drawable = context.getResources().getDrawable(resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tartView.setCompoundDrawables(null,null,drawable,null);
    }


    /**
     * 保存图片到sd卡
     * @param view
     * @param imageName
     * @return
     */
    public static boolean saveImage(Context context,View view,String imageName) {
        view.buildDrawingCache();
        Bitmap bitmap = getBitmapFromView(view);
        if (saveBitmap(context,bitmap,imageName)) return true;
        else return false;
    }


    /**
     * 将View 转化成 bitmap
     * @param view
     * @return
     */
    public static Bitmap getBitmapFromView(View view){
        Bitmap bitmap = null;
        try {
            bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            view.draw(canvas);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    /**
     * 将bitmap 存储在本地
     * @param context
     * @param bitmap
     * @param imageName
     * @return
     */
    public static boolean saveBitmap(Context context,Bitmap bitmap,String imageName) {
        FileOutputStream fos = null;
        try {
            //生成路径
            File filePath = new File(Utils.sdPath(),BaseConst.APP_FOLDER_PHOTO);
            if (!filePath.exists()) {
                filePath.mkdirs();
            }

//            KLog.v("imagePath: " + filePath + "/" + imageName);

            //获取文件
            File file = new File(filePath, imageName);
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            //通知系统相册刷新
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.fromFile(new File(file.getPath()))));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
