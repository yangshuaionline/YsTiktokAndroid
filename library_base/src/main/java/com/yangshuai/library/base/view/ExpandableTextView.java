package com.yangshuai.library.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yangshuai.library.base.R;

/**
 * 展开收起的TextView
 *
 * @author hcp
 * @created 2019-04-26
 */
public class ExpandableTextView extends LinearLayout {

    private TextView mTextView; // 显示文本内容
    private TextView mTextViewExpan; // 展开收起按钮的文字
    private View mViewExpan; // 展开收起按钮
    private ImageView arrowIcon; // 箭头图标
    private int mMaxLine = 3; // 未展开状态的最大显示行数

    private CharSequence mContent;
    private boolean mIsExpansion; // 是否已展开


    public ExpandableTextView(Context context) {
        super(context);
        initView(context);
    }

    public ExpandableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ExpandableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        setOrientation(VERTICAL);
        LayoutInflater.from(context).inflate(R.layout.base_expandable_view, this);
        mTextView = findViewById(R.id.tv_content);
        mViewExpan = findViewById(R.id.ll_expan);
        arrowIcon = findViewById(R.id.iv_arrow);
        mTextViewExpan = findViewById(R.id.tv_expan);

        // 监听文本控件的布局绘制
        mTextView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if (mTextView.getWidth() == 0) {
                            return;
                        }
                        mTextView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        // 文本控件绘制成功后, 更新文本
                        setText(mContent);
                    }
                }
        );

        // 展开收起按钮
        mViewExpan.setOnClickListener(v -> {
            toggleExpansionStatus();
        });
    }

    /**
     * 切换展开/收起状态
     */
    private void toggleExpansionStatus() {
        mIsExpansion = !mIsExpansion;

        // 更新内容和切换按钮的显示
        if (mIsExpansion) {
            mTextViewExpan.setText("收起");  // 全文状态, 按钮显示 "收起"
            mTextView.setMaxLines(Integer.MAX_VALUE); // 全文状态, 行数设置为最大
            arrowIcon.setVisibility(GONE);
        } else {
            mTextViewExpan.setText("展开全部"); // 收起状态, 按钮显示 "全文"
            mTextView.setMaxLines(mMaxLine); // 收起状态, 最大显示指定的行数
            arrowIcon.setVisibility(VISIBLE);
        }
    }

    /**
     * 设置需要显示的文本
     */
    public void setText(CharSequence text) {
        mContent = text;

        // 文本控件有宽度时（绘制成功后）才能获取到文本显示的所需要的行数,
        // 如果控件还没有被绘制, 等监听到绘制成功后再设置文本
        if (mTextView.getWidth() == 0) {
            return;
        }

        mTextView.setMaxLines(Integer.MAX_VALUE); // 默认先设置最大行数为最大值（即不限制行数）
        mTextView.setText(mContent); // 设置文本
        int lineCount = mTextView.getLineCount(); // 设置完文本后, 获取显示该文本所需要的行数

        if (lineCount > mMaxLine) {
            // 行数超过显示, 显示 "展开全部" 按钮
            mViewExpan.setVisibility(VISIBLE);
            mTextViewExpan.setText("展开全部");
            mTextView.setMaxLines(mMaxLine);// 设置文本控件的最大允许显示行数
            arrowIcon.setVisibility(VISIBLE);
            mIsExpansion = false;
        } else {
            mViewExpan.setVisibility(GONE);
        }
    }

    /**
     * 设置未展开状态最大允许显示的行数（超过该行数显示 "收起" 按钮）
     */
    public void setMaxLine(int maxLine) {
        this.mMaxLine = maxLine;
        setText(mContent); // 更新状态, 重新显示文本
    }

}
