package com.yangshuai.library.base.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yangshuai.library.base.R;

/**
 * @author hcp
 * @date 2019/3/20
 */
public class ToastUtil {

    private static Toast toast = null;

    /**
     * 长时间提示信息显示
     */
    public static void Long(String text) {
        Long(text, false);
    }

    /**
     * 长时间提示信息显示
     *
     * @param text          提示信息
     * @param needHideOther 是否需要隐藏其他Toast
     */
    public static void Long(String text, boolean needHideOther) {
        if (toast != null && needHideOther) {
            toast.cancel();
        }
        showToast(AppContext.getAppContext(), text, Toast.LENGTH_LONG);
    }

    /**
     * 长时间提示信息显示
     */
    public static void Long(int srcId) {
        Long(srcId, false);
    }


    /**
     * 长时间提示信息显示
     *
     * @param srcId         提示信息
     * @param needHideOther 是否需要隐藏其他Toast
     */
    public static void Long(int srcId, boolean needHideOther) {
        if (toast != null && needHideOther) {
            toast.cancel();
        }

        showToast(AppContext.getAppContext(), srcId, Toast.LENGTH_LONG);
    }

    /**
     * 短时间提示信息显示
     */
    public static void Short(String text) {
        Short(text, false);
    }

    /**
     * 短时间提示信息显示
     *
     * @param text          提示信息
     * @param needHideOther 是否需要隐藏其他Toast
     */
    public static void Short(String text, boolean needHideOther) {
        if (toast != null && needHideOther) {
            toast.cancel();
        }
        showToast(AppContext.getAppContext(), text, Toast.LENGTH_SHORT);
    }

    /**
     * 短时间提示信息显示
     */
    public static void Short(int srcId) {
        Short(srcId, false);
    }

    /**
     * 短时间提示信息显示
     *
     * @param srcId         提示信息
     * @param needHideOther 是否需要隐藏其他Toast
     */
    public static void Short(int srcId, boolean needHideOther) {
        if (toast != null && needHideOther) {
            toast.cancel();
        }
        showToast(AppContext.getAppContext(), srcId, Toast.LENGTH_SHORT);
    }

    private static void showToast(Context context, int resId, int duration) {
        if (context == null) {
            return;
        }
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }
        String text = context.getString(resId);
        showToast(context, text, duration);
    }

    private static void showToast(Context context, String text, int duration) {
        if (context == null) {
            return;
        }
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }
        if (!TextUtils.isEmpty(text) && !"".equals(text.trim())) {
            toast = Toast.makeText(context, text, duration);
            toast.setGravity(Gravity.BOTTOM, 0, 360);
            toast.show();
        }
    }

    public static void hideToast() {
        if (toast != null) {
            toast.cancel();
        }
    }


    /**
     * 弹出列表总数
     */
    public static void total(String massage) {
        Toast toast = Toast.makeText(AppContext.getAppContext(), massage, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


    /**
     * 自定义toast
     *
     * @param massage 显示信息
     */
    public static void showDialogByLayout(String massage) {
        Toast toast = new Toast(AppContext.getAppContext());
        View layout = View.inflate(AppContext.getAppContext(), R.layout.base_toast, null);
        TextView mTextView = layout.findViewById(R.id.tv_message);
        mTextView.setText(massage);
        toast.setView(layout);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


    /**
     * 显示图片的Toast
     */
    public static void showImageToast() {
        @SuppressLint("InflateParams")
        View toastView = LayoutInflater.from(AppContext.getAppContext()).inflate(R.layout.base_toast_image_layout, null);
        Toast toast = new Toast(AppContext.getAppContext());   //上下文
        toast.setGravity(Gravity.CENTER, 0, 0);   //位置居中
        toast.setDuration(Toast.LENGTH_SHORT);  //设置短暂提示
        toast.setView(toastView);   //把定义好的View布局设置到Toast里面
        toast.show();
    }

}
