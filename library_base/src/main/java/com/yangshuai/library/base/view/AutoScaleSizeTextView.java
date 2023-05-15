package com.yangshuai.library.base.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.yangshuai.library.base.R;

/**
 * 自动缩放字体大小的TexView
 *
 * @author hcp
 * @created 2019-11-11
 */
public class AutoScaleSizeTextView extends AppCompatTextView {

    private static final String TAG = "AutoScaleSizeTextView";

    private float preferredTextSize; // 正常显示字体大小(取当前textSize)
    private float minTextSize; // 最小缩放字体大小
    private Paint textPaint; // 绘制文本

    public AutoScaleSizeTextView(Context context) {
        this(context, null);
    }

    public AutoScaleSizeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoScaleSizeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 读取attr配置
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.AutoScaleSizeTextView, defStyleAttr, 0);

        this.minTextSize = a.getDimension(R.styleable.AutoScaleSizeTextView_minTextSize, 10f);
        a.recycle();

        this.preferredTextSize = this.getTextSize();
    }

    /**
     * 设置最小缩放的字体大小
     *
     * @param minTextSize
     */
    public void setMinTextSize(float minTextSize) {
        this.minTextSize = minTextSize;
    }

    /**
     * 根据填充文本内容自适应调整TextView
     *
     * @param text
     * @param textWidth
     */
    private void refitText(String text, int textWidth) {
        if (textWidth <= 0 || text == null || text.length() == 0) {
            return;
        }

        int targetWidth = textWidth - this.getPaddingLeft() - this.getPaddingRight();

        final float threshold = 0.5f;
        this.textPaint.set(this.getPaint());
        this.textPaint.setTextSize(this.preferredTextSize);

        if (this.textPaint.measureText(text) <= targetWidth) {
            this.setTextSize(TypedValue.COMPLEX_UNIT_PX, this.preferredTextSize);
            return;
        }

        float tempMinTextSize = this.minTextSize;
        float tempPreferredTextSize = this.preferredTextSize;

        while ((tempPreferredTextSize - tempMinTextSize) > threshold) {
            float size = (tempPreferredTextSize + tempMinTextSize) / 2;
            this.textPaint.setTextSize(size);
            if (this.textPaint.measureText(text) >= targetWidth) {
                tempPreferredTextSize = size;
            } else {
                tempMinTextSize = size;
            }
        }

        this.setTextSize(TypedValue.COMPLEX_UNIT_PX, tempMinTextSize);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        this.refitText(text.toString(), this.getWidth());
    }

    @Override
    protected void onSizeChanged(int width, int h, int oldw, int oldh) {
        if (width != oldw) {
            this.refitText(this.getText().toString(), width);
        }
    }

}
