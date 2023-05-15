package com.yangshuai.library.base.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.yangshuai.library.base.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * 双向范围选择条(用于价格筛选效果)
 *
 * @author hcp
 * @created 2019/3/29
 */
public class DoubleRangeSeekBar extends View {

    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mLayoutParams, mLayoutParams1, mLayoutParams2;
    private SeekBarCallBack barCallBack;

    private float buttonWidth = 0;//按钮宽
    private float buttonHeight = 0;//按钮高
    private int viewWidth; // 控件宽
    private int textColor, bgColor; // 文字颜色

    private String unitStr1 = "";
    private String unitStr2 = "";
    private float unitTextSize = 0;// 显示单位的字体大小
    private int bgHeight = dp2px(4);// 背景高度
    private float seekWidth;// 进度条宽度
    private Bitmap buttonImg;

    private Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint valuePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int max = 100; // 总百分比
    private int minValue = 0; // 最小值百分比
    private int maxValue = 0; // 最大值百分比
    private boolean isMinMode = true; // 是否选择最小值模式
    private View toastView, toastView1, toastView2;

    public DoubleRangeSeekBar(Context context) {
        this(context, null);
    }

    public DoubleRangeSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DoubleRangeSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 读取attr配置
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.DoubleRangeSeekBar, defStyleAttr, 0);
        buttonWidth = a.getDimension(R.styleable.DoubleRangeSeekBar_drsb_button_width, dp2px(60));
        buttonHeight = a.getDimension(R.styleable.DoubleRangeSeekBar_drsb_button_height, dp2px(30));
        textColor = a.getColor(R.styleable.DoubleRangeSeekBar_drsb_text_color, Color.parseColor("#333333"));
        bgColor = a.getColor(R.styleable.DoubleRangeSeekBar_drsb_bg_color, Color.parseColor("#F2F2F2"));
        bgHeight = (int) a.getDimension(R.styleable.DoubleRangeSeekBar_drsb_seek_height, dp2px(4));
        int seekColor = a.getColor(R.styleable.DoubleRangeSeekBar_drsb_seek_color, Color.parseColor("#3672DC"));
        valuePaint.setColor(seekColor);
        int buttonImgId = a.getResourceId(R.styleable.DoubleRangeSeekBar_drsb_button_img, R.drawable.seekbar_thumb);
        a.recycle();

        // 设置单位显示的字体
        unitTextSize = buttonHeight * 0.4f;
        textPaint.setTextSize(unitTextSize);
        textPaint.setColor(textColor);

        bgPaint.setColor(bgColor);

        buttonImg = setImgSize(BitmapFactory.decodeResource(context.getResources(), buttonImgId), buttonWidth, buttonHeight);

        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        mLayoutParams2 = new WindowManager.LayoutParams();
        mLayoutParams = new WindowManager.LayoutParams();
        mLayoutParams1 = new WindowManager.LayoutParams();
        initLayoutParams(mLayoutParams2);
        initLayoutParams(mLayoutParams);
        initLayoutParams(mLayoutParams1);


    }

    public Bitmap setImgSize(Bitmap bm, float newWidth, float newHeight) {
        // 获得图片的宽高.
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例.
        float scaleWidth = newWidth / width;
        float scaleHeight = newHeight / height;
        // 取得想要缩放的matrix参数.
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片.
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }

    public void setUnit(String unitStr1, String unitStr2) {
        this.unitStr1 = unitStr1;
        this.unitStr2 = unitStr2;
        invalidate();
    }

    private void initLayoutParams(WindowManager.LayoutParams mLayoutParams) {

        mLayoutParams.gravity = Gravity.START | Gravity.TOP;
        mLayoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        mLayoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        mLayoutParams.format = PixelFormat.TRANSLUCENT;
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
        // MIUI禁止了开发者使用TYPE_TOAST，Android 7.1.1 对TYPE_TOAST的使用更严格
        if (isMIUI() || Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION;
        } else {
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawUnit(canvas);
        drawBg(canvas);
        drawValue(canvas);
        drawButton(canvas);
//        calculationToastIndex();
//        drawBubble();
//        showToastView();
    }

    private void drawUnit(Canvas canvas) {
        drawText(canvas, buttonWidth / 2, unitTextSize, unitStr1);
        drawText(canvas, viewWidth - buttonWidth / 2, unitTextSize, unitStr2);
    }

    private void drawText(Canvas canvas, float x, float y, String str) {
        //居中对齐
        textPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(str, x, y, textPaint);
    }

    private void drawBg(Canvas canvas) {
        canvas.drawRoundRect(new RectF(buttonWidth / 2, buttonHeight - bgHeight / 2, viewWidth - buttonWidth / 2, buttonHeight + bgHeight / 2),
                bgHeight / 2,
                bgHeight / 2
                , bgPaint);
    }

    private void drawValue(Canvas canvas) {
        float minx = seekWidth * minValue / max;
        float m1 = minx + buttonWidth / 2;

        float maxx = seekWidth * maxValue / max;
        float m2 = maxx + buttonWidth / 2;

        canvas.drawRoundRect(new RectF(m1, buttonHeight - bgHeight / 2, m2, buttonHeight + bgHeight / 2),
                bgHeight / 2,
                bgHeight / 2
                , valuePaint);
    }

    private void drawButton(Canvas canvas) {
        if (isMinMode) {
            canvas.drawBitmap(buttonImg, seekWidth * maxValue / max, buttonHeight / 2, textPaint);
            canvas.drawBitmap(buttonImg, seekWidth * minValue / max, buttonHeight / 2, textPaint);
        } else {
            canvas.drawBitmap(buttonImg, seekWidth * minValue / max, buttonHeight / 2, textPaint);
            canvas.drawBitmap(buttonImg, seekWidth * maxValue / max, buttonHeight / 2, textPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int defalueSize = 750;
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int specValue = MeasureSpec.getSize(widthMeasureSpec);
        viewWidth = specValue;
        switch (mode) {
            //指定一个默认值
            case MeasureSpec.UNSPECIFIED:
                viewWidth = defalueSize;
                break;
            //取测量值
            case MeasureSpec.EXACTLY:
                viewWidth = specValue;
                break;
            //取测量值和默认值中的最小值
            case MeasureSpec.AT_MOST:
                viewWidth = Math.min(defalueSize, specValue);
                break;
            default:
                break;
        }
        setMeasuredDimension(viewWidth, (int) (buttonHeight * 1.5f));

    }

    private static final String KEY_MIUI_MANE = "ro.miui.ui.version.name";
    private static Properties sProperties = new Properties();
    private static Boolean miui;

    private static boolean isMIUI() {
        if (miui != null) {
            return miui;
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(new File(Environment.getRootDirectory(), "build.prop"));
                sProperties.load(fis);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            miui = sProperties.containsKey(KEY_MIUI_MANE);
        } else {
            Class<?> clazz;
            try {
                clazz = Class.forName("android.os.SystemProperties");
                Method getMethod = clazz.getDeclaredMethod("get", String.class);
                String name = (String) getMethod.invoke(null, KEY_MIUI_MANE);
                miui = !TextUtils.isEmpty(name);
            } catch (Exception e) {
                miui = false;
            }
        }

        return miui;
    }

    public void setMax(int max) {
        this.max = max;
    }

    /**
     * 设置最小值的百分比
     *
     * @param minValue
     */
    public void setMinValue(int minValue) {
        if (minValue < 0) {
            minValue = 0;
        } else if (minValue > max) {
            minValue = max;
        }

        this.minValue = minValue;
    }

    /**
     * 设置最大值的百分比
     *
     * @param maxValue
     */
    public void setMaxValue(int maxValue) {
        if (maxValue < 0) {
            maxValue = 0;
        } else if (maxValue > max) {
            maxValue = max;
        }
        this.maxValue = maxValue;
    }


    public void setBarCallBack(SeekBarCallBack barCallBack) {
        this.barCallBack = barCallBack;
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }

    private void getTouchSeekValue(MotionEvent event) {
        int x = (int) event.getX();
        x = (int) (x - buttonWidth / 2);
        int t = (int) (max * x / seekWidth);
        if (isMinMode) {

            if (t < 0) {
                minValue = 0;
            } else if (t > maxValue) {
                minValue = maxValue;
            } else {
                minValue = t;
            }

        } else {
            if (t < minValue) {
                maxValue = minValue;
            } else if (t > max) {
                maxValue = max;
            } else {
                maxValue = t;
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        seekWidth = viewWidth - buttonWidth;
//        getLocationOnScreen(point);
    }

    /**
     * 是否触摸在控件内
     * 同时判断是拖动最大按钮还是最小按钮
     *
     * @param event
     * @return
     */
    private boolean isTouchSeek(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        if (y < buttonHeight / 2) {
            return false;
        }

        float minx = seekWidth * minValue / max;
        float m1 = minx + buttonWidth / 2;

        float maxx = seekWidth * maxValue / max;
        float m2 = maxx + buttonWidth / 2;

        if (isMinMode) {

            if (x > m1 - buttonWidth / 2 && x < m1 + buttonWidth / 2) {
                isMinMode = true;
                return true;
            }

            if (x > m2 - buttonWidth / 2 && x < m2 + buttonWidth) {
                isMinMode = false;
                return true;
            }
        } else {
            if (x > m2 - buttonWidth / 2 && x < m2 + buttonWidth) {
                isMinMode = false;
                return true;
            }

            if (x > m1 - buttonWidth / 2 && x < m1 + buttonWidth / 2) {
                isMinMode = true;
                return true;
            }
        }

        return false;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                performClick();
                getParent().requestDisallowInterceptTouchEvent(true);
                if (!isTouchSeek(event)) {
                    return false;
                }

                break;

            case MotionEvent.ACTION_MOVE:
                int a = minValue;
                int b = maxValue;
                getTouchSeekValue(event);
                if (a == minValue && b == maxValue) {

                } else {
                    invalidate();
                }

                if (barCallBack != null) {
                    barCallBack.onMoveTouch(minValue,maxValue);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:

                if (barCallBack != null) {
                    barCallBack.onEndTouch(minValue, maxValue);
                }
//
                break;
        }

        return true;
    }

    public abstract static class SeekBarCallBack {

        // 获取最小值
        public String getMinString(int value) {
            return value + "";
        }

        // 获取最大值
        public String getMaxString(int value) {
            return value + "";
        }

        // 获取范围值 最小 ~ 最大
        public String getRangeString(int minValue, int maxValue) {
            return minValue + "-" + maxValue;
        }

        /**
         * 结束滑动后
         */
        public void onEndTouch(int minPercentage, int maxPercentage) {

        }

        /**
         * 正在滑动中
         */
        public void onMoveTouch(int minValue, int maxValue) {

        }
    }
}
