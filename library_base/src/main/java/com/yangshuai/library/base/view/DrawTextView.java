package com.yangshuai.library.base.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 删除线textview自定义颜色
 */
@SuppressLint("AppCompatCustomView")
public class DrawTextView extends TextView {
    public DrawTextView(Context context) {
        super(context);
        //初始化Paint
        initPaint();
    }
    private void initPaint() {
        //删除线的颜色和样式
        paint = new Paint();
        paint.setColor(Color.parseColor("#999999"));
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(5);
    }
    public DrawTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initPaint();
    }
    public DrawTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }
    private Paint paint;
    @Override
    protected void onDraw(Canvas canvas) {
        //TextView布局的高度和宽度
        float x = this.getWidth();
        float y = this.getHeight();
        //根据Textview的高度和宽度设置删除线的位置
        //四个参数的意思：起始x的位置，起始y的位置，终点x的位置，终点y的位置
        canvas.drawLine(0f, y/2f, x, y/2f, paint);
        //super最后调用表示删除线在位于文字的上边
        //super方法先调用删除线不显示
        super.onDraw(canvas);
    }
}

