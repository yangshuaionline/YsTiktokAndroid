package com.yangshuai.library.base.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 圆饼View
 * 带折线文字
 *
 * @author hcp
 * @date 2019/9/10
 */
public class DataAnalysisPieChartView extends View {
    private String TAG = "饼图测试";
    private int width;
    private int height;
    private Context mContext;
    private Paint mPaint;
    /**
     * 每块占比的绘制的颜色
     */
    private List<Integer> mColorList = new ArrayList<>();
    /**
     * 圆弧占比的集合
     */
    private List<Float> mRateList = new ArrayList<>();
    /**
     * 是否展示文字
     */
    private boolean isShowRateText = true;
    /**
     * 圆弧半径
     */
    private float radius;
    /**
     * 起始角度
     */
    private int startAngle = 0;
    /**
     * 不同色块之间是否需要空隙offset
     */
    private int offset = 0;
    /**
     * 圆弧中心点小圆点的圆心半径
     */
    private int centerPointRadius;
    /**
     * 折线文本字体大小
     */
    private float showRateSize;
    /**
     * 折线线宽
     */
    private int lintWidth;
    /**
     * 数据源集合
     */
    private List<String> sourceDataList = new ArrayList<>();
    /**
     * 文本+百分比集合
     */
    private List<String> strWithPercentList = new ArrayList<>();
    /**
     * 文本区域集合
     */
    private List<Rect> textRectList = new ArrayList<>();
    /**
     * 折线x向间距
     */
    private int xOffset;
    /**
     * 折线y向间距
     */
    private int yOffset;
    /**
     * 文字与折线y间距
     */
    private int textLineDiv;
    private Point lastPoint;
    private float doubleRadius;
    private float startDiv;
    private float topDiv;

    public DataAnalysisPieChartView(Context context) {
        this(context, null);
    }

    public DataAnalysisPieChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DataAnalysisPieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        radius = dip2px(40);
        doubleRadius = radius * 2;
        centerPointRadius = dip2px(0);
        //折线x向位移
        xOffset = dip2px(5);
        //折线y向位移
        yOffset = dip2px(5);
        textLineDiv = dip2px(3);
        showRateSize = dip2px(10);
        lintWidth = dip2px(1);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(showRateSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        startDiv = (width - radius * 2) / 2;
        topDiv = (height - radius * 2) / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (sourceDataList.size() <= 0) {
            return;
        }
        RectF rectCircle = new RectF(startDiv, topDiv, startDiv + doubleRadius, topDiv + doubleRadius);
        List<Point> mPointList = new ArrayList<>();
        int otherRateTotal = 0;
        int rateAngle = 0;
        for (int i = 0; i < mRateList.size(); i++) {
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(mColorList.get(i));
            if (i == mRateList.size() - 1) {
                rateAngle = 360 - otherRateTotal;
            } else {
                rateAngle = (int) (mRateList.get(i) * (360));
                otherRateTotal += rateAngle;
            }
            //(1)绘制圆饼
            canvas.drawArc(rectCircle, startAngle, rateAngle, true, mPaint);
            //(2)处理每块圆饼弧的中心点，绘制折线，显示对应的文字
            if (isShowRateText) {
                //处理折线起点坐标
                int keyAngle = startAngle + (rateAngle / 2);
                boolean isSpecialAngle = keyAngle > 45 && keyAngle < 135;
                Point point = dealPoint(rectCircle, startAngle, rateAngle / 2, mPointList);
                dealRateText(canvas, point, i, mPointList, isSpecialAngle);
            }
            //起始角度变化
            startAngle = startAngle + rateAngle;
        }
    }

    private void dealRateText(Canvas canvas, Point point, int position, List<Point> pointList, boolean isSpecialAngle) {
        if (position == 0) {
            lastPoint = pointList.get(0);
        } else {
            lastPoint = pointList.get(position - 1);
        }
        float[] floats = new float[8];
        //起始点位
        floats[0] = point.x;
        floats[1] = point.y;
        //文字起点间距
        int bottomTextLeft = 0;
        //判断左右半圆
        boolean isLeftCircle = (point.x <= width / 2);
        //判断上下半圆
        boolean isTopCircle = (point.y <= height / 2);
        Rect textRect = textRectList.get(position);
        int yLineOffset;
        if (isSpecialAngle) {
            yLineOffset = yOffset + yOffset;
        } else {
            yLineOffset = textLineDiv;
        }
        if (isLeftCircle) {
            //左半圆
            mPaint.setTextAlign(Paint.Align.RIGHT);
            if (isTopCircle) {
                //左上
                //第二个点
                floats[2] = floats[4] = point.x - xOffset;
                floats[3] = floats[5] = point.y - yOffset;
                //第三个点
                floats[6] = floats[2] - textRect.width();
                floats[7] = floats[3];
            } else {
                //左下
                //第二个点
                floats[2] = floats[4] = point.x - xOffset;
                floats[3] = floats[5] = point.y + yOffset + yLineOffset;
                //第三个点
                floats[6] = floats[2] - textRect.width() - textLineDiv;
                floats[7] = floats[3];
                bottomTextLeft = -textLineDiv;
            }
        } else {
            //右半圆
            mPaint.setTextAlign(Paint.Align.LEFT);
            if (isTopCircle) {
                //右上
                //第二个点
                floats[2] = floats[4] = point.x + xOffset;
                floats[3] = floats[5] = point.y - yOffset;
                //第三个点
                floats[6] = floats[2] + textRect.width();
                floats[7] = floats[3];
            } else {
                //右下
                //第二个点
                floats[2] = floats[4] = point.x + xOffset;
                floats[3] = floats[5] = point.y + yOffset + yLineOffset;
                //第三个点
                floats[6] = floats[2] + textRect.width() + textLineDiv;
                floats[7] = floats[3];
                bottomTextLeft = textLineDiv;
            }
        }
        //根据每块的颜色，绘制对应颜色的折线
        mPaint.setColor(mColorList.get(position));
        //画圆饼图每块边上的折线
        mPaint.setStrokeWidth(lintWidth);
        canvas.drawLines(floats, mPaint);
        mPaint.setStyle(Paint.Style.FILL);
        //绘制显示的文字,需要根据类型显示不同的文字
        //Y轴：+ textRect.height() / 2 ,相对沿线居中显示
        canvas.drawText(strWithPercentList.get(position),
                floats[2] + bottomTextLeft, floats[3] - textLineDiv, mPaint);
    }

    private Point dealPoint(RectF rectF, float startAngle, float endAngle, List<Point> pointList) {
        Path path = new Path();
        //通过Path类画一个90度（180—270）的内切圆弧路径
        path.addArc(rectF, startAngle, endAngle);
        PathMeasure measure = new PathMeasure(path, false);
        float[] coords = new float[]{0f, 0f};
        //利用PathMeasure分别测量出各个点的坐标值coords
        int divisor = 1;
        measure.getPosTan(measure.getLength() / divisor, coords, null);
        float x = coords[0];
        float y = coords[1];
        Point point = new Point(Math.round(x), Math.round(y));
        pointList.add(point);
        return point;
    }

    public void updateData(List<String> rateList, List<Integer> colorList) {
        sourceDataList.clear();
        strWithPercentList.clear();
        mColorList.clear();
        mRateList.clear();
        textRectList.clear();
        startAngle = 0;
        //初始化数据
        sourceDataList.addAll(rateList);
        mColorList.addAll(colorList);
        float total = 0;
        for (int i = 0; i < sourceDataList.size(); i++) {
            total += Integer.parseInt(sourceDataList.get(i));
        }

        for (int i = 0; i < sourceDataList.size(); i++) {
            float rate = Integer.parseInt(sourceDataList.get(i)) / total;
            mRateList.add(rate);
        }
        for (int i = 0; i < mRateList.size(); i++) {
            Rect textRect = new Rect();
            String rateStr = sourceDataList.get(i) + "(" + getFormatPercentRate(mRateList.get(i) * 100) + "%)";
            mPaint.getTextBounds(rateStr, 0, rateStr.length(), textRect);
            textRectList.add(textRect);
            strWithPercentList.add(rateStr);
        }
        init();
        invalidate();
    }

    /**
     * 获取格式化的保留两位数的数
     */
    public String getFormatPercentRate(float dataValue) {
        //构造方法的字符格式这里如果小数不足2位,会以0补足.
        DecimalFormat decimalFormat = new DecimalFormat(".00");
        return decimalFormat.format(dataValue);
    }


    private int dip2px(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
