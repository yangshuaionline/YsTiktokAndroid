package com.yangshuai.library.base.view;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.yangshuai.library.base.interfaces.IPieElement;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PieChartView extends View implements GestureDetector.OnGestureListener {


    public static final int CLICK_ANIM_LENGTH = 20;
    public static final int DURATION = 200;
    /**
     * view的宽高
     */
    private int mWidth, mHeight;
    /**
     * 饼状图的半径、内部空白圆的半径
     */
    private float mRadius, mInnerRadius;
    /**
     * 饼状图的外切
     */
    private RectF mPieRect;
    /**
     * 各种画笔
     */
    private Paint mPiePaint, mBlankPaint, mLinePaint, mTextPaint, mLegendPaint;
    /**
     * 实体类集合
     */
    private List<IPieElement> mElements;
    /**
     * 各个元素的角度
     */
    private List<Float> mAngles = new ArrayList<>();
    /**
     * 元素的颜色
     */
    private List<Integer> mColors = new ArrayList<>();
    /**
     * 元素的占比
     */
    private List<String> mPercents = new ArrayList<>();
    /**
     * 中心文字
     */
    private String mText;

    /**
     * 中心文字
     */
    private TextPaint mCenterTextPaint;

    private SparseArray<double[]> angles = new SparseArray<>();

    private GestureDetector mDetector;

    private boolean mIsAnimEnable;

    public PieChartView(Context context) {
        this(context, null);
    }

    public PieChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setData(List<IPieElement> elements) {
        mElements = elements;
        setValuesAndColors();
        invalidate();
    }

    public void setAnimEnable(boolean enable) {
        mIsAnimEnable = enable;
    }

    private void init() {
        mDetector = new GestureDetector(getContext(), this);
        mDetector.setIsLongpressEnabled(false);
        mPieRect = new RectF();
        mPiePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPiePaint.setColor(Color.RED);

        mBlankPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBlankPaint.setColor(Color.WHITE);

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setStrokeWidth(4);
        mLinePaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(15);
        mTextPaint.setColor(Color.BLACK);

        mCenterTextPaint = new TextPaint();
        mCenterTextPaint.setTextSize(40);
        mCenterTextPaint.setColor(Color.parseColor("#FF333333"));
        mCenterTextPaint.setStyle(TextPaint.Style.FILL);

        mLegendPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLegendPaint.setTextSize(10);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mDetector.onTouchEvent(event);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = 0, height = 0;
        if (widthMode == MeasureSpec.AT_MOST) {
            width = (int) getSize();
        } else {
            width = widthSize;
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            height = (int) getSize();
        } else {
            height = heightSize;
        }
        int size = Math.min(width, height);
        setMeasuredDimension(size, size);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w - getPaddingLeft() - getPaddingRight();
        mHeight = h - getPaddingTop() - getPaddingBottom();
        mRadius = (float) (Math.min(mWidth, mHeight) / 2 * 0.5);
        resetRect();

        mInnerRadius = (float) (mRadius * 0.6);

    }

    private Path mPath = new Path();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(mWidth / 2, mHeight / 2);
        // 从12点方向开始画
        float sweepedAngle = -90;
        for (int i = 0; mElements != null && mElements.size() > i; i++) {
            // 设置扇形的颜色
            mPiePaint.setColor(mColors.get(i));
            mLinePaint.setColor(mColors.get(i));
            // 画扇形
            if (mIsAnimEnable && i == mCurrentPressedPosition) {
                setRect(sweepedAngle, i, mCurrentLength);
            }
            canvas.drawArc(mPieRect, sweepedAngle, mAngles.get(i), true, mPiePaint);
            resetRect();

            // 扫过的角度++
            double[] ang = new double[2];
            ang[0] = sweepedAngle + 90;
            ang[1] = ang[0] + mAngles.get(i);
            angles.put(i, ang);
            sweepedAngle += mAngles.get(i);
            IPieElement iPieElement = mElements.get(i);

            // 百分比
            String percentText = mPercents.get(i) + "%";
            // 画分割线
            canvas.drawArc(mPieRect, sweepedAngle, 1, true, mBlankPaint);
            sweepedAngle += 1;
            float x = getXCoordinate(mAngles.get(i), sweepedAngle);
            float y = getYCoordinate(mAngles.get(i), sweepedAngle);
            mPath.reset();
            mPath.moveTo(x, y);
            mPath.lineTo((float) (x * 1.2), (float) (y * 1.2));
            canvas.drawPath(mPath, mLinePaint);
            mPath.reset();
            mPath.moveTo((float) (x * 1.2), (float) (y * 1.2));

            // 拿到百分比和描述文字两个之间的最大值
            float max = Math.max(getTextWidth(mTextPaint, percentText), getTextWidth(mTextPaint, iPieElement.getDescription()));
            // 水平线的长度设置为文字长度的1.3倍
            float horizontalLineLength = (float) (max * 1.3);
            // 当线的起点在第三、四象限时，先把path移动到终点位置，然后向起点画线，使后面画文字时，文字方向是正确的
            if (x < 0) {
                horizontalLineLength = -horizontalLineLength;
                mPath.moveTo((float) (x * 1.2) + horizontalLineLength, (float) (y * 1.2));
                mPath.lineTo((float) (x * 1.2), (float) (y * 1.2));
            } else {
                mPath.lineTo((float) (x * 1.2) + horizontalLineLength, (float) (y * 1.2));

            }
            canvas.drawPath(mPath, mLinePaint);
            // 垂直方向的偏移量，画(百分比)文字时，文字显示在path的下方，为了让文字显示在上方，设置一个文字高度的垂直偏移量
            float offsetV = -getTextHeight(mTextPaint, percentText);
            canvas.drawTextOnPath(percentText, mPath, 0, offsetV, mTextPaint);

            // 垂直方向的偏移量，画描述文字时，文字显示在path的下方
            float offsetDescriptionV = getTextHeight(mTextPaint, iPieElement.getDescription()) + 10;
            canvas.drawTextOnPath(iPieElement.getDescription(), mPath, 0, offsetDescriptionV, mTextPaint);
        }
        mPath.close();

        // 这里开始画中心空白部分以及文字，空白部分半径设置为整个圆半径的0.6倍

        canvas.drawCircle(0, 0, mInnerRadius, mBlankPaint);

        canvas.save();
        canvas.translate(-mInnerRadius, -40);
        // 中间文字
        @SuppressLint("DrawAllocation")
        StaticLayout staticLayout = new StaticLayout(mText, mCenterTextPaint, (int) mInnerRadius * 2, Layout.Alignment.ALIGN_CENTER, 1.5f, 1.0f, false);
        staticLayout.draw(canvas);
        canvas.restore();

        if (mShowLegend) {
            //drawLegend(canvas);
        }
    }

    private void resetRect() {
        mPieRect.left = -mRadius;
        mPieRect.top = -mRadius;
        mPieRect.right = mRadius;
        mPieRect.bottom = mRadius;
    }

    private void setRect(float sweepedAngle, int i, int animatedLength) {
        float currentCenterAngle = sweepedAngle + mAngles.get(i) / 2;
        if (currentCenterAngle >= -90 && currentCenterAngle <= 0) {
            double actualAng = currentCenterAngle + 90;
            mPieRect.right += Math.sin(getRadian(actualAng)) * animatedLength;
            mPieRect.top -= Math.cos(getRadian(actualAng)) * animatedLength;

        } else if (currentCenterAngle > 0 && currentCenterAngle <= 90) {
            mPieRect.right += Math.cos(getRadian(currentCenterAngle)) * animatedLength;
            mPieRect.bottom += Math.sin(getRadian(currentCenterAngle)) * animatedLength;
        } else if (currentCenterAngle > 90 && currentCenterAngle <= 180) {
            double actualAng = currentCenterAngle - 90;
            mPieRect.left -= Math.sin(getRadian(actualAng)) * animatedLength;
            mPieRect.bottom += Math.cos(getRadian(actualAng)) * animatedLength;
        } else {
            double actualAng = currentCenterAngle - 180;
            mPieRect.left -= Math.cos(getRadian(actualAng)) * animatedLength;
            mPieRect.top -= Math.sin(getRadian(actualAng)) * animatedLength;
        }
    }

    private double getRadian(double actualAng) {
        return actualAng * 2 * Math.PI / 360;
    }

    private Rect rect = new Rect();


    /**
     * 设置线上的文本大小
     *
     * @param textSize
     */
    public void setDescriptionTextSize(int textSize) {
        mTextPaint.setTextSize(textSize);
    }

    /**
     * 设置中心文字
     *
     * @param title
     */
    public void setCenterText(String title) {
        this.setCenterText(title, mCenterTextPaint);
    }

    /**
     * @param title
     * @param mCenterTextPaint
     */
    public void setCenterText(String title, TextPaint mCenterTextPaint) {
        this.mText = title;
        this.mCenterTextPaint = mCenterTextPaint;
    }


    /**
     * 计算角度值和各个值的占比
     */
    private void setValuesAndColors() {
        float sum = 0;
        if (mElements != null && mElements.size() > 0) {
            for (IPieElement ele : mElements) {
                sum += ele.getValue();
                mColors.add(ele.getColor());
            }
            BigDecimal totleAngel = BigDecimal.valueOf(360 - mElements.size());
            for (int i = 0; i < mElements.size(); i++) {
                IPieElement ele = mElements.get(i);
                BigDecimal bigDecimal = new BigDecimal(String.valueOf(ele.getValue()));
                BigDecimal sumBigDecimal = BigDecimal.valueOf(sum);
                BigDecimal res = bigDecimal.divide(sumBigDecimal, 5, BigDecimal.ROUND_HALF_UP);
                //计算角度
                BigDecimal angle = res.multiply(totleAngel);
                mAngles.add(angle.floatValue());
                //计算百分比保留两位小数并保存
                mPercents.add(bigDecimal.multiply(new BigDecimal(100)).divide(sumBigDecimal, 2, BigDecimal.ROUND_HALF_UP).toPlainString());
            }
        }

    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        startTouchUpAnim();
        mCurrentPressedPosition = getPosition(motionEvent);
        Log.d("选中：", getPosition(motionEvent) + "");
        startTouchDownAnim();
        return true;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {

//        mCurrentPressedPosition = getPosition(motionEvent);
//        startTouchUpAnim();
//        if (mCurrentPressedPosition >= 0 && mListener != null) {
//           // mListener.onItemClick(mCurrentPressedPosition);
//        }

        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
//        mCurrentPressedPosition = getPosition(motionEvent);
//        startTouchUpAnim();
        return true;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    private int mCurrentLength;
    public int mCurrentPressedPosition;

    public void startTouchDownAnim() {
//        ValueAnimatorCompat va= new ValueAnimatorCompat();
        ValueAnimator va = ValueAnimator.ofInt(0, CLICK_ANIM_LENGTH);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentLength = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        va.setDuration(DURATION);
        va.start();
    }

    public void startTouchDownAnim1(final int p) {
//        ValueAnimatorCompat va= new ValueAnimatorCompat();
        ValueAnimator va = ValueAnimator.ofInt(0, CLICK_ANIM_LENGTH);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentLength = p;
                invalidate();
            }
        });
        va.setDuration(DURATION);
        va.start();
    }


    public void startTouchUpAnim() {
//        ValueAnimatorCompat va= new ValueAnimatorCompat();
        ValueAnimator va = ValueAnimator.ofInt(CLICK_ANIM_LENGTH, 0);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentLength = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        va.setDuration(DURATION);
        va.start();
    }

    /**
     * 获取点击位置坐标对应的饼状图的区域
     *
     * @param motionEvent
     * @return 数据的position
     */
    public int getPosition(MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        float centerX = getWidth() / 2;
        float centerY = getHeight() / 2;

        //判断点击位置是否在innerRadius内
        if ((Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2)) < mInnerRadius * mInnerRadius) {
            return -1;
        }

        //判断点击位置是否在饼状图以外
        if ((Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2)) > mRadius * mRadius) {
            return -1;
        }

        // 判断象限
        // 第一象限
        double angle = 0;

        if (x > centerX && y < centerY) {
            angle = Math.toDegrees(Math.atan((Math.abs(x - centerX)) / (Math.abs(centerY - y))));

        } else if (x > centerX && y > centerY) {//第二象限
            angle = Math.toDegrees(Math.atan(((y - centerY) / (x - centerX))));
            angle += 90;
        } else if (x < centerX && y > centerY) {//第三象限
            angle = Math.toDegrees(Math.atan(((centerX - x) / (y - centerY))));
            angle += 180;
        } else if (x < centerX && y < centerY) {//第四象限
            angle = Math.toDegrees(Math.atan(((centerY - y) / (centerX - x))));
            angle += 270;
        }

        for (int i = 0; i < angles.size(); i++) {
            double[] angs = angles.get(i);
            if (angle >= angs[0] && angle <= angs[1]) {
                return i;
            }
        }
        return -1;
    }

    public OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    private boolean mShowLegend = true;

    /**
     * 图例开关
     *
     * @param enable
     */
    public void enableLegend(boolean enable) {
        mShowLegend = enable;
    }

    /**
     * 画图例
     *
     * @param canvas
     */
    private void drawLegend(Canvas canvas) {
        float verticalOffset = 0;
        for (int i = 0; i < mElements.size(); i++) {
            IPieElement ele = mElements.get(i);
            mLegendPaint.setColor(ele.getColor());
            mLegendPaint.getTextBounds(ele.getDescription(), 0, ele.getDescription().length(), rect);
            verticalOffset = rect.height() + 20;
            canvas.translate(0, verticalOffset);
            mLegendPaint.setStrokeWidth(8);
            canvas.drawLine(10, 0, 80, 0, mLegendPaint);
            canvas.drawText(ele.getDescription(), 90, rect.height() / 2, mLegendPaint);
        }
    }

    /**
     * 把文字画在圆内接正方形内，依此计算画笔的textSize
     *
     * @param text
     */
    private void calculateTextPaint(String text) {
        if (!TextUtils.isEmpty(text)) {
            measureText(text, 40);
        }
    }

    /**
     * 递归调用，计算testSize
     *
     * @param text
     * @param textSize
     */
    private void measureText(String text, int textSize) {
        mTextPaint.setTextSize(textSize);
        float width = getTextWidth(mTextPaint, text);
        float height = getTextHeight(mTextPaint, text);
        if (width > mInnerRadius * 1.41421) {
            textSize--;
            measureText(text, textSize);
            return;
        }
        if (height * 2.5 > mInnerRadius * 1.41421) {
            textSize--;
            measureText(text, textSize);
        }
    }

    private float getTextHeight(Paint paint, String text) {
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect.height();
    }

    /**
     * @param paint
     * @param text
     * @return
     */
    private float getTextWidth(Paint paint, String text) {
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect.width();
    }

    /**
     * 获取圆弧中点的x轴坐标
     *
     * @param angle        圆弧对应的角度
     * @param sweepedAngle 扫过的角度
     * @return 圆弧中点的x轴坐标
     */
    private float getXCoordinate(float angle, float sweepedAngle) {
        float x = (float) (mRadius * Math.cos(Math.toRadians(sweepedAngle - angle / 2)));
        return x;

    }

    /**
     * 获取圆弧中点的y轴坐标
     *
     * @param angle        圆弧对应的角度
     * @param sweepedAngle 扫过的角度
     * @return 圆弧中点的y轴坐标
     */
    private float getYCoordinate(float angle, float sweepedAngle) {
        float y = (float) (mRadius * Math.sin(Math.toRadians(sweepedAngle - angle / 2)));
        return y;

    }

    private float getSize() {
        Display display = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        float widht = display.getWidth();
        float height = display.getHeight();
        return Math.min(widht, height);
    }
}
