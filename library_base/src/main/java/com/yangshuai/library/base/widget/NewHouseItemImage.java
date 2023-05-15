package com.yangshuai.library.base.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.yangshuai.library.base.R;
import com.yangshuai.library.base.utils.AppContext;

/**
 * @Description: 项目列表图片
 * @Author:
 * @CreateDate: 2019/11/1 14:44
 * @UpdateUser: hcp
 * @UpdateDate: 2019/11/1 14:44
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@SuppressLint("AppCompatCustomView")
public class NewHouseItemImage extends ImageView {

    private String text = "";
    private String hotText = "热销";
    private boolean isTop;
    private Context context;
    private Paint bluePaint;
    private Paint redPaint;
    private Paint whitePaint;
    private Path levelPath;
    private Path topPath;
    private int themeColor;
    private int redColor;
    private Rect topTextRect;
    private int width;
    private int hotHeight;
    private int hotMidHeight;

    public NewHouseItemImage(Context context) {
        super(context);
    }

    public NewHouseItemImage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        //绿色画笔  抗锯齿
        bluePaint = new Paint();
        bluePaint.setAntiAlias(true);

        themeColor = ContextCompat.getColor(AppContext.getAppContext(), R.color.theme);
        redColor = ContextCompat.getColor(AppContext.getAppContext(), R.color.price_color);

        //白色画笔  抗锯齿
        whitePaint = new Paint();
        whitePaint.setColor(Color.WHITE);
        whitePaint.setAntiAlias(true);

        //实例化路径
        levelPath = new Path();
        topPath = new Path();
        topTextRect = new Rect();
        //右下角小三角的宽高
        width = dp2Px(context, 28);
        hotHeight = dp2Px(context, 18);
        hotMidHeight = dp2Px(context, 15);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //获取图片的宽度
        int measuredWidth = getMeasuredWidth();
        //获取图片的高度
        int measuredHeight = getMeasuredHeight();

        int textWidth = measuredWidth - width / 2;
        whitePaint.setTextSize(dp2Px(context, 12));
        //根据字符长度调整位置
        if (!TextUtils.isEmpty(text)) {
            bluePaint.setColor(themeColor);
            // 此点为多边形的起点
            levelPath.moveTo(measuredWidth, measuredHeight - width);
            levelPath.lineTo(measuredWidth - width, measuredHeight);
            levelPath.lineTo(measuredWidth, measuredHeight);
            // 使这些点构成封闭的多边形
            levelPath.close();
            canvas.drawPath(levelPath, bluePaint);
            if (text.length() == 2) {
                canvas.drawText(text, textWidth - dp2Px(context, 2), measuredHeight - dp2Px(context, 3), whitePaint);
            } else {
                canvas.drawText(text, textWidth + dp2Px(context, 2), measuredHeight - dp2Px(context, 3), whitePaint);
            }
        }


        //热销标识
        if (isTop) {
            bluePaint.setColor(redColor);
            topPath.moveTo(0, 0);
            topPath.lineTo(width, 0);
            topPath.lineTo(width, hotHeight);
            topPath.lineTo(width >> 1, hotMidHeight);
            topPath.lineTo(0, hotHeight);
            topPath.close();
            canvas.drawPath(topPath, bluePaint);
            whitePaint.getTextBounds(hotText, 0, hotText.length(), topTextRect);
            canvas.drawText(hotText, dp2Px(context, 2), topTextRect.height(), whitePaint);
        }
    }

    /**
     * 传入项目等级
     */
    public void setText(String s, boolean isTop) {
        text = s;
        this.isTop = isTop;
        invalidate();
    }

    public static int dp2Px(Context context, int dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (scale * dp + 0.5);
    }

}
