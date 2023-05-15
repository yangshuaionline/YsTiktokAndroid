package com.yangshuai.library.base.view.pickerview;


import android.content.Context;
import android.graphics.Typeface;
import androidx.annotation.ColorInt;
import android.view.View;
import android.view.ViewGroup;

import com.contrarywind.view.WheelView;


/**
 * @author hcp
 * @created 2019/3/21
 */
public class SinglePickerBuilder {

    // 配置类
    private SinglePickerOptions mPickerOptions;

    //Required
    public SinglePickerBuilder(Context context, WheelRoomOptions.OnRoomOnOptionsSelectListener listener) {
        mPickerOptions = new SinglePickerOptions();
        mPickerOptions.context = context;
        mPickerOptions.optionsSelectListener = listener;
    }

    // Option
    public SinglePickerBuilder setTitleText(String textContentTitle) {
        mPickerOptions.textContentTitle = textContentTitle;
        return this;
    }

    public SinglePickerBuilder isDialog(boolean isDialog) {
        mPickerOptions.isDialog = isDialog;
        return this;
    }

    public SinglePickerBuilder setOnCancelClickListener(View.OnClickListener cancelListener) {
        mPickerOptions.cancelListener = cancelListener;
        return this;
    }

    /**
     * 显示时的外部背景色颜色,默认是灰色
     *
     * @param outSideColor color resId.
     * @return
     */
    public SinglePickerBuilder setOutSideColor(int outSideColor) {
        mPickerOptions.outSideColor = outSideColor;
        return this;
    }

    /**
     * ViewGroup 类型
     * 设置PickerView的显示容器
     *
     * @param decorView Parent View.
     * @return
     */
    public SinglePickerBuilder setDecorView(ViewGroup decorView) {
        mPickerOptions.decorView = decorView;
        return this;
    }

    public SinglePickerBuilder setBgColor(int bgColorWheel) {
        mPickerOptions.bgColorWheel = bgColorWheel;
        return this;
    }

    public SinglePickerBuilder setLabels(String label1) {
        mPickerOptions.label1 = label1;
        return this;
    }

    /**
     * 设置Item 的间距倍数，用于控制 Item 高度间隔
     *
     * @param lineSpacingMultiplier 浮点型，1.0-4.0f 之间有效,超过则取极值。
     */
    public SinglePickerBuilder setLineSpacingMultiplier(float lineSpacingMultiplier) {
        mPickerOptions.lineSpacingMultiplier = lineSpacingMultiplier;
        return this;
    }

    /**
     * Set item divider line type color.
     *
     * @param dividerColor color resId.
     */
    public SinglePickerBuilder setDividerColor(@ColorInt int dividerColor) {
        mPickerOptions.dividerColor = dividerColor;
        return this;
    }

    /**
     * Set item divider line type.
     *
     * @param dividerType enum Type {@link WheelView.DividerType}
     */
    public SinglePickerBuilder setDividerType(WheelView.DividerType dividerType) {
        mPickerOptions.dividerType = dividerType;
        return this;
    }

    /**
     * Set the textColor of selected item.
     *
     * @param textColorCenter color res.
     */
    public SinglePickerBuilder setTextColorCenter(int textColorCenter) {
        mPickerOptions.textColorCenter = textColorCenter;
        return this;
    }

    public SinglePickerBuilder setContentTextSize(int textSizeContent) {
        mPickerOptions.textSizeContent = textSizeContent;
        return this;
    }


    /**
     * Set the textColor of outside item.
     *
     * @param textColorOut color resId.
     */
    public SinglePickerBuilder setTextColorOut(@ColorInt int textColorOut) {
        mPickerOptions.textColorOut = textColorOut;
        return this;
    }

    public SinglePickerBuilder setTypeface(Typeface font) {
        mPickerOptions.font = font;
        return this;
    }

    public SinglePickerBuilder setCyclic(boolean cyclic) {
        mPickerOptions.cyclic1 = cyclic;
        return this;
    }

    public SinglePickerBuilder setSelectOptions(int option1) {
        mPickerOptions.option = option1;
        return this;
    }


    public SinglePickerBuilder setTextXOffset(int xoffset) {
        mPickerOptions.x_offset_one = xoffset;
        return this;
    }

    public SinglePickerBuilder isCenterLabel(boolean isCenterLabel) {
        mPickerOptions.isCenterLabel = isCenterLabel;
        return this;
    }

    /**
     * 切换选项时，是否还原第一项
     *
     * @param isRestoreItem true：还原； false: 保持上一个选项
     * @return RoomPickerBuilder
     */
    public SinglePickerBuilder isRestoreItem(boolean isRestoreItem) {
        mPickerOptions.isRestoreItem = isRestoreItem;
        return this;
    }

    /**
     * @param listener 切换item项滚动停止时，实时回调监听。
     * @return
     */
    public SinglePickerBuilder setOptionsSelectChangeListener(WheelRoomOptions.OnRoomOptionsSelectChangeListener listener) {
        mPickerOptions.optionsSelectChangeListener = listener;
        return this;
    }

    public <T> SinglePickerView<T> build() {
        return new SinglePickerView<>(mPickerOptions.context,mPickerOptions);
    }

}
