package com.yangshuai.library.base.view.pickerview;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.listener.OnDismissListener;
import com.bigkoo.pickerview.utils.PickerViewAnimateUtil;
import com.yangshuai.library.base.R;

import java.util.List;

/**
 * 户型选择器(4级不联动滚轮)
 *
 * @author hcp
 * @created 2019/3/20
 */
public class RoomPickerView<T> implements View.OnClickListener {

    private static final String TAG_SUBMIT = "submit";
    private static final String TAG_CANCEL = "cancel";

    private Context mContext;
    private ViewGroup contentContainer;
    private ViewGroup rootView;//附加View 的 根View
    private ViewGroup dialogView;//附加Dialog 的 根View

    private WheelRoomOptions wheelOptions;
    private PickerRoomOptions mPickerOptions;
    private OnDismissListener onDismissListener;
    private boolean dismissing;

    private Animation outAnim;
    private Animation inAnim;
    private boolean isShowing;

    protected int animGravity = Gravity.BOTTOM;

    private Dialog mDialog;
    protected View clickView;//是通过哪个View弹出的
    private boolean isAnim = true;


    public RoomPickerView(Context context, PickerRoomOptions pickerOptions) {
        this.mContext = context;
        this.mPickerOptions = pickerOptions;
        initView(context);
    }

    private void initView(Context context) {
        setDialogOutSideCancelable();
        initViews();
        initAnim();

        LayoutInflater.from(context).inflate(mPickerOptions.layoutRes, contentContainer);

        // 顶部标题
        TextView tvTitle = (TextView) findViewById(R.id.tv_picker_title);
        tvTitle.setOnClickListener(v -> {}); // 避免点击标题的时候关闭弹窗

        // 确定按钮
        View btnSubmit = (View) findViewById(R.id.rl_picker_ok);
//        Button btnCancel = (Button) findViewById(R.id.btnCancel);

        btnSubmit.setTag(TAG_SUBMIT);
//        btnCancel.setTag(TAG_CANCEL);
        btnSubmit.setOnClickListener(this);
//        btnCancel.setOnClickListener(this);

        // 设置标题文字
        tvTitle.setText(TextUtils.isEmpty(mPickerOptions.textContentTitle) ? "请选择" : mPickerOptions.textContentTitle);

        // 滚轮布局
        final LinearLayout optionsPicker = (LinearLayout) findViewById(R.id.timepicker);
        optionsPicker.setBackgroundColor(mPickerOptions.bgColorWheel);

        wheelOptions = new WheelRoomOptions(optionsPicker, mPickerOptions.isRestoreItem);
        if (mPickerOptions.optionsSelectChangeListener != null) {
            wheelOptions.setOptionsSelectChangeListener(mPickerOptions.optionsSelectChangeListener);
        }

        wheelOptions.setTextContentSize(mPickerOptions.textSizeContent);
        wheelOptions.setLabels(mPickerOptions.label1, mPickerOptions.label2, mPickerOptions.label3, mPickerOptions.label4);
        wheelOptions.setTextXOffset(mPickerOptions.x_offset_one, mPickerOptions.x_offset_two, mPickerOptions.x_offset_three, mPickerOptions.x_offset_four);
        wheelOptions.setCyclic(mPickerOptions.cyclic1, mPickerOptions.cyclic2, mPickerOptions.cyclic3, mPickerOptions.cyclic4);
        wheelOptions.setTypeface(mPickerOptions.font);

        setOutSideCancelable(mPickerOptions.cancelable);

        wheelOptions.setDividerColor(mPickerOptions.dividerColor);
        wheelOptions.setDividerType(mPickerOptions.dividerType);
        wheelOptions.setLineSpacingMultiplier(mPickerOptions.lineSpacingMultiplier);
        wheelOptions.setTextColorOut(mPickerOptions.textColorOut);
        wheelOptions.setTextColorCenter(mPickerOptions.textColorCenter);
        wheelOptions.isCenterLabel(mPickerOptions.isCenterLabel);
    }

    private void initViews() {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        if (isDialog()) {
            //如果是对话框模式
            dialogView = (ViewGroup) layoutInflater.inflate(R.layout.layout_basepickerview, null, false);
            //设置界面的背景为透明
            dialogView.setBackgroundColor(Color.TRANSPARENT);
            //这个是真正要加载选择器的父布局
            contentContainer = (ViewGroup) dialogView.findViewById(R.id.content_container);
            //设置对话框 默认左右间距屏幕30
            params.leftMargin = 30;
            params.rightMargin = 30;
            contentContainer.setLayoutParams(params);
            //创建对话框
            createDialog();
            //给背景设置点击事件,这样当点击内容以外的地方会关闭界面
            dialogView.setOnClickListener(view -> dismiss());
        } else {
            //如果只是要显示在屏幕的下方
            //decorView是activity的根View,包含 contentView 和 titleView
            if (mPickerOptions.decorView == null) {
                mPickerOptions.decorView = (ViewGroup) ((Activity) mContext).getWindow().getDecorView();
            }
            //将控件添加到decorView中
            rootView = (ViewGroup) layoutInflater.inflate(R.layout.layout_basepickerview, mPickerOptions.decorView, false);
            rootView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            if (mPickerOptions.outSideColor != -1) {
                rootView.setBackgroundColor(mPickerOptions.outSideColor);
            }
            //这个是真正要加载时间选取器的父布局
            contentContainer = rootView.findViewById(R.id.content_container);
            contentContainer.setLayoutParams(params);
        }
        setKeyBackCancelable(true);
    }

    private void initAnim() {
        inAnim = getInAnimation();
        outAnim = getOutAnimation();
    }

    /**
     * 动态设置标题
     *
     * @param text 标题文本内容
     */
    public void setTitleText(String text) {
        TextView tvTitle = (TextView) findViewById(R.id.tv_picker_title);
        if (tvTitle != null) {
            tvTitle.setText(text);
        }
    }

    //不联动情况下调用
    public void setPicker(List<T> options1Items,
                          List<T> options2Items,
                          List<T> options3Items,
                          List<T> options4Items) {

        wheelOptions.setPicker(options1Items, options2Items, options3Items,options4Items);
        reSetCurrentItems();
    }

    /**
     * @param v      (是通过哪个View弹出的)
     * @param isAnim 是否显示动画效果
     */
    public void show(View v, boolean isAnim) {
        this.clickView = v;
        this.isAnim = isAnim;
        show();
    }

    public void show(boolean isAnim) {
        show(null, isAnim);
    }

    public void show(View v) {
        this.clickView = v;
        show();
    }

    /**
     * 添加View到根视图
     */
    public void show() {
        if (isDialog()) {
            showDialog();
        } else {
            if (isShowing()) {
                return;
            }
            isShowing = true;
            onAttached(rootView);
            rootView.requestFocus();
        }
    }

    /**
     * show的时候调用
     *
     * @param view 这个View
     */
    private void onAttached(View view) {
        mPickerOptions.decorView.addView(view);
        if (isAnim) {
            contentContainer.startAnimation(inAnim);
        }
    }


    /**
     * 检测该View是不是已经添加到根视图
     *
     * @return 如果视图已经存在该View返回true
     */
    public boolean isShowing() {
        if (isDialog()) {
            return false;
        } else {
            return rootView.getParent() != null || isShowing;
        }

    }

    public void dismiss() {
        if (isDialog()) {
            dismissDialog();
        } else {
            if (dismissing) {
                return;
            }

            if (isAnim) {
                //消失动画
                outAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        dismissImmediately();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                contentContainer.startAnimation(outAnim);
            } else {
                dismissImmediately();
            }
            dismissing = true;
        }


    }

    public void dismissImmediately() {

        mPickerOptions.decorView.post(() -> {
            //从根视图移除
            mPickerOptions.decorView.removeView(rootView);
            isShowing = false;
            dismissing = false;
            if (onDismissListener != null) {
                onDismissListener.onDismiss(RoomPickerView.this);
            }
        });


    }


    /**
     * 设置默认选中项
     *
     * @param option1
     */
    public void setSelectOptions(int option1) {
        mPickerOptions.option1 = option1;
        reSetCurrentItems();
    }


    public void setSelectOptions(int option1, int option2) {
        mPickerOptions.option1 = option1;
        mPickerOptions.option2 = option2;
        reSetCurrentItems();
    }

    public void setSelectOptions(int option1, int option2, int option3) {
        mPickerOptions.option1 = option1;
        mPickerOptions.option2 = option2;
        mPickerOptions.option3 = option3;
        reSetCurrentItems();
    }

    public void setSelectOptions(int option1, int option2, int option3, int option4) {
        mPickerOptions.option1 = option1;
        mPickerOptions.option2 = option2;
        mPickerOptions.option3 = option3;
        mPickerOptions.option4 = option4;
        reSetCurrentItems();
    }

    private void reSetCurrentItems() {
        if (wheelOptions != null) {
            wheelOptions.setCurrentItems(mPickerOptions.option1,
                    mPickerOptions.option2,
                    mPickerOptions.option3,
                    mPickerOptions.option4);
        }
    }

    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        if (tag.equals(TAG_SUBMIT)) {
            returnData();
        } else if (tag.equals(TAG_CANCEL)) {
            if (mPickerOptions.cancelListener != null) {
                mPickerOptions.cancelListener.onClick(v);
            }
        }
        dismiss();
    }


    //抽离接口回调的方法
    public void returnData() {
        if (mPickerOptions.optionsSelectListener != null) {
            int[] optionsCurrentItems = wheelOptions.getCurrentItems();
            mPickerOptions.optionsSelectListener.onOptionsSelect(
                    optionsCurrentItems[0],
                    optionsCurrentItems[1],
                    optionsCurrentItems[2],
                    optionsCurrentItems[3],
                    clickView
            );
        }
    }

    private Animation getInAnimation() {
        int res = PickerViewAnimateUtil.getAnimationResource(this.animGravity, true);
        return AnimationUtils.loadAnimation(mContext, res);
    }

    private Animation getOutAnimation() {
        int res = PickerViewAnimateUtil.getAnimationResource(this.animGravity, false);
        return AnimationUtils.loadAnimation(mContext, res);
    }

    public RoomPickerView setOnDismissListener(OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
        return this;
    }

    public void setKeyBackCancelable(boolean isCancelable) {

        ViewGroup View;
        if (isDialog()) {
            View = dialogView;
        } else {
            View = rootView;
        }

        View.setFocusable(isCancelable);
        View.setFocusableInTouchMode(isCancelable);
        if (isCancelable) {
            View.setOnKeyListener(onKeyBackListener);
        } else {
            View.setOnKeyListener(null);
        }
    }

    private View.OnKeyListener onKeyBackListener = (v, keyCode, event) -> {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == MotionEvent.ACTION_DOWN && isShowing()) {
            dismiss();
            return true;
        }
        return false;
    };

    private RoomPickerView setOutSideCancelable(boolean isCancelable) {

        if (rootView != null) {
            View view = rootView.findViewById(R.id.outmost_container);

            if (isCancelable) {
                view.setOnTouchListener(onCancelableTouchListener);
            } else {
                view.setOnTouchListener(null);
            }
        }

        return this;
    }

    /**
     * 设置对话框模式是否可以点击外部取消
     */
    public void setDialogOutSideCancelable() {
        if (mDialog != null) {
            mDialog.setCancelable(mPickerOptions.cancelable);
        }
    }


    /**
     * Called when the user touch on black overlay, in order to dismiss the dialog.
     */
    private final View.OnTouchListener onCancelableTouchListener =
            (v, event) -> {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    dismiss();
                }
                return false;
            };

    public View findViewById(int id) {
        return contentContainer.findViewById(id);
    }

    public void createDialog() {
        if (dialogView != null) {
            mDialog = new Dialog(mContext, R.style.custom_dialog2);
            mDialog.setCancelable(mPickerOptions.cancelable);//不能点外面取消,也不能点back取消
            mDialog.setContentView(dialogView);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(R.style.picker_view_scale_anim);
                dialogWindow.setGravity(Gravity.CENTER);//可以改成Bottom
            }

            mDialog.setOnDismissListener(
                    dialog -> {
                        if (onDismissListener != null) {
                            onDismissListener.onDismiss(RoomPickerView.this);
                        }
                    });
        }
    }

    private void showDialog() {
        if (mDialog != null) {
            mDialog.show();
        }
    }

    private void dismissDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    public ViewGroup getDialogContainerLayout() {
        return contentContainer;
    }


    public Dialog getDialog() {
        return mDialog;
    }


    public boolean isDialog() {
        return false;
    }
}
