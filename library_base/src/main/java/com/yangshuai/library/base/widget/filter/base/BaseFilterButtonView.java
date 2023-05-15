package com.yangshuai.library.base.widget.filter.base;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.yangshuai.library.base.R;

import java.util.ArrayList;
import java.util.List;


/**
 * 筛选栏按钮(默认显示效果是 文本+右侧小图标 仿贝壳)
 *
 * @author hcp
 * @created 17/1/4
 * @editor hcp
 * @edited 19/3/28
 * @type 筛选组件
 */
public class BaseFilterButtonView extends LinearLayout {


    private final static long AMIN_DURATION = 400;

    //    private final static int TEXT_COLOR_CHECKED = Color.parseColor("#18A65E");
    private final static int TEXT_COLOR_UNCHECK = Color.parseColor("#333333");
    // 默认的选择、未选中图标
    private final static int ICON_CHECKED = R.mipmap.icon_filter_arrow_checked;
    private final static int ICON_UNCHECK = R.mipmap.icon_filter_arrow_uncheck;
    private final Context context;

    private String mText = "";
    private String mDefaultText = "";
    // 选中时候的字体颜色
    private int mTextColor_Checked;
    // 未选中时候的字体颜色
    private int mTextColor_UnCheck = TEXT_COLOR_UNCHECK;
    // 选中时候显示的图标
    private int mIcon_Checked = ICON_CHECKED;
    // 未选中时候显示的图标
    private int mIcon_UnCheck = ICON_UNCHECK;
    // 是否被选中
    private boolean mChecked = false;
    // 是否只显示图标(例如房源排序筛选按钮，只有图标没有文字的需求)
    private boolean mOnlyIcon = false;
    // 是否只显示文本
    private boolean mOnlyText = false;

    // 按钮字体大小,默认14SP
    private float mTextSize;
    // 按钮的文本
    private TextView mTextView;
    // 按钮文本旁边的图标
    private ImageView mImageView;
    // 图标大小
    private int mIconWidth;
    private int mIconHeight;

    // 最长文本长途
    private int maxLength;

    // 动画持续时长
    private long mAnimDuration = AMIN_DURATION;
    // 是否开启动画
    private boolean mOpenAnim = true;

    private List<OnFilterButtonListener> listeners;

    public BaseFilterButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initArrts(context, attrs, 0);
        init(context);
    }


    public BaseFilterButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initArrts(context, attrs, defStyleAttr);
        init(context);
    }


    private void initArrts(Context context, AttributeSet attrs, int defStyleAttr) {
        // 读取attrs配置
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BaseFilterButtonView, defStyleAttr, 0);
        mText = a.getString(R.styleable.BaseFilterButtonView_fbv_text);
        mDefaultText = mText;
        mTextColor_Checked = a.getColor(R.styleable.BaseFilterButtonView_fbv_text_color_checked, ContextCompat.getColor(context, R.color.theme));
        mTextColor_UnCheck = a.getColor(R.styleable.BaseFilterButtonView_fbv_text_color, TEXT_COLOR_UNCHECK);
        mIcon_Checked = a.getResourceId(R.styleable.BaseFilterButtonView_fbv_icon_checked, ICON_CHECKED);
        mIcon_UnCheck = a.getResourceId(R.styleable.BaseFilterButtonView_fbv_icon_uncheck, ICON_UNCHECK);
        mTextSize = a.getDimension(R.styleable.BaseFilterButtonView_fbv_text_size, TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, context.getResources().getDisplayMetrics()));
        mIconWidth = a.getDimensionPixelSize(R.styleable.BaseFilterButtonView_fbv_icon_width, 0);
        mIconHeight = a.getDimensionPixelOffset(R.styleable.BaseFilterButtonView_fbv_icon_height, 0);
        mChecked = a.getBoolean(R.styleable.BaseFilterButtonView_fbv_checked, false);
        mOnlyIcon = a.getBoolean(R.styleable.BaseFilterButtonView_fbv_only_icon, false);
        mOnlyText = a.getBoolean(R.styleable.BaseFilterButtonView_fbv_only_text, false);
        mOpenAnim = a.getBoolean(R.styleable.BaseFilterButtonView_fbv_open_anim, true);
        maxLength = a.getInteger(R.styleable.BaseFilterButtonView_fbv_max_length, 0);
        a.recycle();
    }

    /**
     * 初始化
     */
    private void init(Context context) {
        setOnClickListener(view -> {
            setChecked(!isChecked());
        });
        removeAllViews();
        this.setGravity(Gravity.CENTER);
        this.setOrientation(HORIZONTAL);

        // 设置文本布局参数
        mTextView = new TextView(getContext());
        mTextView.setText(mText);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        mTextView.setMaxLines(1);
        mTextView.setGravity(Gravity.CENTER);
        mTextView.setEllipsize(TextUtils.TruncateAt.END);
        if (maxLength != 0) {
            mTextView.setMaxEms(maxLength);
        }
        LayoutParams textViewPrams = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mTextView.setLayoutParams(textViewPrams);

        // 设置图标布局参数
        mImageView = new ImageView(getContext());
        LayoutParams imageViewPrams = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mImageView.setLayoutParams(imageViewPrams);

        // 如果有自定义图标宽高
        if (mIconWidth != 0 && mIconHeight != 0) {
            LayoutParams params = (LayoutParams) mImageView.getLayoutParams();
            params.weight = mIconWidth;
            params.height = mIconHeight;
            mImageView.setLayoutParams(params);
        }

        int imageRes = isChecked() ? mIcon_Checked : mIcon_UnCheck;
        int textColor = isChecked() ? mTextColor_Checked : mTextColor_UnCheck;

        mImageView.setImageResource(imageRes);
        mTextView.setTextColor(textColor);


        this.addView(mTextView);
        this.addView(mImageView);

        if (isOnlyIcon()) {
            // 只有图标
            mImageView.setVisibility(VISIBLE);
            mTextView.setVisibility(GONE);
        } else if (isOnlyText()) {
            // 只有文本
            mImageView.setVisibility(GONE);
            mTextView.setVisibility(VISIBLE);

        } else {
            // 默认显示 文本 + 小图标
            mImageView.setVisibility(VISIBLE);
            mTextView.setVisibility(VISIBLE);
            // 设置文本右侧的小箭头布局参数
            LinearLayout.LayoutParams imageViewSmallPrams = new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);
            imageViewSmallPrams.leftMargin = dp2px(4);
            mImageView.setLayoutParams(imageViewSmallPrams);

        }
    }


    public void setChecked(boolean isChecked) {
        if (mChecked == isChecked) {
            return;
        }
        mChecked = isChecked;
        if (mChecked) {
            checked();
        } else {
            unchecked();
        }
    }

    public boolean isChecked() {
        return mChecked;
    }

    /**
     * 设置按钮文本
     *
     * @param text
     */
    public void setText(String text) {
        mText = text;
        mTextView.setText(mText);
    }

    /**
     * 设置按钮文本颜色
     *
     * @return
     */
    public void setTextColor(int textColor) {
        mTextView.setTextColor(textColor);
    }

    /**
     * 最大长度
     *
     * @return
     */
    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
        mTextView.setMaxEms(maxLength);
    }

    public String getText() {
        return mText;
    }

    /**
     * 设置是否只有图标的按钮
     *
     * @return
     */
    public void setOnlyIcon(boolean isOnlyIcon) {
        this.mOnlyIcon = isOnlyIcon;
    }

    public boolean isOnlyIcon() {
        return mOnlyIcon;
    }

    /**
     * 设置是否只有文本的按钮
     */
    public void setOnlyText(boolean isOnlyText) {
        this.mOnlyText = isOnlyText;
    }

    public boolean isOnlyText() {
        return mOnlyText;
    }

    /**
     * 设置是否开启图标动画
     *
     * @param mOpenAnim
     */
    public void setOpenAnim(boolean mOpenAnim) {
        this.mOpenAnim = mOpenAnim;
    }

    public boolean isOpenAnim() {
        return mOpenAnim;
    }

    /**
     * 设置图标(纯图标模式)
     */
    public void setIcon(int imageRes) {
        if (mImageView != null) {
            mImageView.setImageResource(imageRes);
        }
    }

    /**
     * 设置选中状态的图标
     *
     * @param mIcon_Checked
     */
    public void setCheckedIcon(int mIcon_Checked) {
        this.mIcon_Checked = mIcon_Checked;
    }

    public int getCheckedIcon() {
        return mIcon_Checked;
    }

    /**
     * 设置未选中状态的图标
     *
     * @param icon 图标资源
     */
    public void setUnCheckIcon(int icon) {
        this.mIcon_UnCheck = icon;
        init(context);
    }

    public int getUnCheckIcon() {
        return mIcon_UnCheck;
    }


    /**
     * 设置图标宽度
     *
     * @param iconWidth
     */
    public void setIconWidth(int iconWidth) {
        this.mIconWidth = mIconWidth;
    }

    public int getIconWidth() {
        return mIconWidth;
    }


    /**
     * 设置图标高度
     *
     * @param iconHeight
     */
    public void setIconHeight(int iconHeight) {
        this.mIconHeight = mIconHeight;
    }


    public int getIconHeight() {
        return mIconHeight;
    }

    private void checked() {

        if (onFilterButtonListener != null) {
            onFilterButtonListener.onChecked(BaseFilterButtonView.this, isChecked());

        }
        if (listeners != null) {
            for (OnFilterButtonListener listener : listeners) {
                listener.onChecked(BaseFilterButtonView.this, isChecked());
            }
        }

        int pivotType = Animation.RELATIVE_TO_SELF; // 相对于自己
        float pivotX = 0.5f; // 取自身区域在X轴上的中心点
        float pivotY = 0.5f; // 取自身区域在Y轴上的中心点
        RotateAnimation anim = new RotateAnimation(0, -180.f, pivotType, pivotX, pivotType, pivotY);
        anim.setDuration(mAnimDuration);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mImageView.clearAnimation();
                mTextView.setTextColor(mTextColor_Checked);
                mImageView.setImageResource(mIcon_Checked);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        if (isOpenAnim()) {
            // 设置开启动画，但当前为纯文本模式，不动画效果
            if (isOnlyText()) {
                mTextView.setTextColor(mTextColor_Checked);
                mImageView.setImageResource(mIcon_Checked);
            } else {
                mImageView.clearAnimation();
                mImageView.startAnimation(anim);
            }
        } else {
            // 关闭动画
            mTextView.setTextColor(mTextColor_Checked);
            mImageView.setImageResource(mIcon_Checked);
        }


    }

    private void unchecked() {
        if (onFilterButtonListener != null) {
            onFilterButtonListener.onChecked(BaseFilterButtonView.this, isChecked());
        }
        if (listeners != null) {
            for (OnFilterButtonListener listener : listeners) {
                listener.onChecked(BaseFilterButtonView.this, isChecked());
            }
        }
        int pivotType = Animation.RELATIVE_TO_SELF; // 相对于自己
        float pivotX = 0.5f; // 取自身区域在X轴上的中心点
        float pivotY = 0.5f; // 取自身区域在Y轴上的中心点
        RotateAnimation anim = new RotateAnimation(0, 180.f, pivotType, pivotX, pivotType, pivotY);
        anim.setDuration(mAnimDuration);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                if (!isOnlyIcon()) {
                    String text = mTextView.getText().toString();
                    if ("不限".equals(text) || "更多".equals(text) || mDefaultText.equals(text)) {
                        mTextView.setTextColor(mTextColor_UnCheck);
                    } else {
                        mTextView.setTextColor(mTextColor_Checked);
                    }
                }

                if (isOnlyIcon()) {
                    String text = mTextView.getText().toString();
                    if ("不限".equals(text)
                            || "更多".equals(text)
                            || "默认排序".equals(text)
                            || TextUtils.isEmpty(text)) {
                        mImageView.setImageResource(mIcon_UnCheck);
                    } else {
                        mImageView.setImageResource(mIcon_Checked);
                    }
                    mImageView.clearAnimation();
                } else {
                    mImageView.clearAnimation();
                    mImageView.setImageResource(mIcon_UnCheck);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        if (isOpenAnim()) {
            // 设置开启动画，但当前为纯文本模式，不动画效果
            if (isOnlyText()) {
                String text = mTextView.getText().toString();
                if ("不限".equals(text) || "更多".equals(text) || mDefaultText.equals(text)) {
                    mTextView.setTextColor(mTextColor_UnCheck);
                } else {
                    mTextView.setTextColor(mTextColor_Checked);
                }
            } else {
                mImageView.clearAnimation();
                mImageView.startAnimation(anim);
            }
        } else {
            // 设置关闭动画
            if (!isOnlyIcon()) {
                // 文本模式
                String text = mTextView.getText().toString();
                if ("不限".equals(text) || "更多".equals(text) || mDefaultText.equals(text)) {
                    mTextView.setTextColor(mTextColor_UnCheck);
                } else {
                    mTextView.setTextColor(mTextColor_Checked);
                }
            } else {
                // 图标模式
                String text = mTextView.getText().toString();
                if ("不限".equals(text)
                        || "更多".equals(text)
                        || "默认排序".equals(text)
                        || TextUtils.isEmpty(text)) {
                    mImageView.setImageResource(mIcon_UnCheck);
                } else {
                    mImageView.setImageResource(mIcon_Checked);
                }
            }

        }

//        mImageView.clearAnimation();
//        mImageView.startAnimation(anim);


    }

    private Float getDip(int dipValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue,
                getResources().getDisplayMetrics());
    }


    private OnFilterButtonListener onFilterButtonListener;

    public void addOnFilterButtonListener(OnFilterButtonListener onFilterButtonListener) {
        if (listeners == null) {
            listeners = new ArrayList<>();
        }
        listeners.add(onFilterButtonListener);
    }

    public OnFilterButtonListener getOnFilterButtonListener() {
        return onFilterButtonListener;
    }

    @Deprecated
    public void setOnFilterButtonListener(OnFilterButtonListener onFilterButtonListener) {
        this.onFilterButtonListener = onFilterButtonListener;
    }

    public interface OnFilterButtonListener {
        public void onChecked(BaseFilterButtonView fbv, boolean isChecked);
    }

    public int dp2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


}
