package com.yangshuai.library.base.view.pickerview;

import android.graphics.Typeface;
import android.view.View;

import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.contrarywind.view.WheelView;
import com.yangshuai.library.base.R;

import java.util.List;

/**
 * @author hcp
 * @created 2020/03/16
 */
public class WheelTimeOptions<T> {

    private View view;
    private WheelView wv_option1;
    private WheelView wv_option2;

    // 不联动的四级菜单，所以数据源不需要多级
    private List<T> mOptions1Items;
    private List<T> mOptions2Items;

    private boolean isRestoreItem; // 切换时，还原第一项

    public OnTimeOptionsSelectChangeListener optionsSelectChangeListener;
    public OnTimeOnOptionsSelectListener optionsSelectListener;

    // 文字的颜色和分割线的颜色
    private int textColorOut;
    private int textColorCenter;
    private int dividerColor;

    private WheelView.DividerType dividerType;

    // 条目间距倍数
    private float lineSpacingMultiplier;

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    /**
     * @param view
     * @param isRestoreItem 是否还原为选中第一项
     */
    public WheelTimeOptions(View view, boolean isRestoreItem) {
        super();
        this.isRestoreItem = isRestoreItem;
        this.view = view;
        wv_option1 = view.findViewById(R.id.day); // 初始化时显示的数据
        wv_option2 = view.findViewById(R.id.time);
    }

    /**
     * 不联动的四列选择器
     *
     * @param options1Items 第一列选项数据
     * @param options2Items 第二列选项数据
     */
    public void setPicker(List<T> options1Items,
                          List<T> options2Items) {

        this.mOptions1Items = options1Items;
        this.mOptions2Items = options2Items;

        // 选项1
        wv_option1.setAdapter(new ArrayWheelAdapter(mOptions1Items));// 设置显示数据
        wv_option1.setCurrentItem(0);// 初始化时显示的数据

        // 选项2
        if (mOptions2Items != null) {
            wv_option2.setAdapter(new ArrayWheelAdapter(mOptions2Items)); // 设置显示数据
        }
        wv_option2.setCurrentItem(0);// 初始化时显示的数据


        wv_option1.setIsOptions(true);
        wv_option2.setIsOptions(true);

        if (optionsSelectChangeListener != null) {
            // 设置选中监听器
            wv_option1.setOnItemSelectedListener(index -> {
                optionsSelectChangeListener.onOptionsSelectChanged(
                        index,
                        wv_option2.getCurrentItem());
            });
        }


        // 如果2 选项数据为空，就隐藏掉对应的滚轮
        if (this.mOptions2Items == null) {
            wv_option2.setVisibility(View.GONE);
        } else {
            wv_option2.setVisibility(View.VISIBLE);
//            if (optionsSelectChangeListener != null) {
                // 设置选中监听器
//                wv_option2.setOnItemSelectedListener(index -> {
//                    optionsSelectChangeListener.onOptionsSelectChanged(
//                            wv_option1.getCurrentItem(),
//                            index);
//                });
//            }
        }
    }

    /**
     *
     * @param options2Items 第二列选项数据
     */
    public void setPickerTime(List<T> options2Items) {

        this.mOptions2Items = options2Items;

        // 选项2
        if (mOptions2Items != null) {
            wv_option2.setAdapter(new ArrayWheelAdapter(mOptions2Items)); // 设置显示数据
        }
        wv_option2.setCurrentItem(0);// 初始化时显示的数据

//        wv_option2.setIsOptions(true);

//        if (optionsSelectChangeListener != null) {
//            // 设置选中监听器
//            wv_option1.setOnItemSelectedListener(index -> {
//                optionsSelectChangeListener.onOptionsSelectChanged(
//                        index,
//                        wv_option2.getCurrentItem());
//            });
//        }


        // 如果2 选项数据为空，就隐藏掉对应的滚轮
//        if (this.mOptions2Items == null) {
//            wv_option2.setVisibility(View.GONE);
//        } else {
//            wv_option2.setVisibility(View.VISIBLE);
//            if (optionsSelectChangeListener != null) {
//                // 设置选中监听器
//                wv_option2.setOnItemSelectedListener(index -> {
//                    optionsSelectChangeListener.onOptionsSelectChanged(
//                            wv_option1.getCurrentItem(),
//                            index);
//                });
//            }
//        }
    }


    public void setTextContentSize(int textSize) {
        wv_option1.setTextSize(textSize);
        wv_option2.setTextSize(textSize);
    }

    private void setTextColorOut() {
        wv_option1.setTextColorOut(textColorOut);
        wv_option2.setTextColorOut(textColorOut);
    }

    private void setTextColorCenter() {
        wv_option1.setTextColorCenter(textColorCenter);
        wv_option2.setTextColorCenter(textColorCenter);
    }

    private void setDividerColor() {
        wv_option1.setDividerColor(dividerColor);
        wv_option2.setDividerColor(dividerColor);
    }

    private void setDividerType() {
        wv_option1.setDividerType(dividerType);
        wv_option2.setDividerType(dividerType);
    }

    private void setLineSpacingMultiplier() {
        wv_option1.setLineSpacingMultiplier(lineSpacingMultiplier);
        wv_option2.setLineSpacingMultiplier(lineSpacingMultiplier);
    }

    /**
     * 设置选项的单位
     *
     * @param label1 单位
     * @param label2 单位
     */
    public void setLabels(String label1, String label2) {
        if (label1 != null) {
            wv_option1.setLabel(label1);
        }
        if (label2 != null) {
            wv_option2.setLabel(label2);
        }
    }

    /**
     * 设置x轴偏移量
     */
    public void setTextXOffset(int x_offset_one, int x_offset_two) {
        wv_option1.setTextXOffset(x_offset_one);
        wv_option2.setTextXOffset(x_offset_two);
    }


    /**
     * 设置是否循环滚动
     *
     * @param cyclic 是否循环
     */
    public void setCyclic(boolean cyclic) {
        wv_option1.setCyclic(cyclic);
        wv_option2.setCyclic(cyclic);
    }

    /**
     * 分别设置第一二三级是否循环滚动
     *
     * @param cyclic1,cyclic2,cyclic3 是否循环
     */
    public void setCyclic(boolean cyclic1, boolean cyclic2) {
        wv_option1.setCyclic(cyclic1);
        wv_option2.setCyclic(cyclic2);
    }

    /**
     * 设置字体样式
     *
     * @param font 系统提供的几种样式
     */
    public void setTypeface(Typeface font) {
        wv_option1.setTypeface(font);
        wv_option2.setTypeface(font);
    }

    public int[] getCurrentItems() {
        int[] currentItems = new int[4];

        currentItems[0] = wv_option1.getCurrentItem();
        currentItems[1] = wv_option2.getCurrentItem();

        return currentItems;
    }

    public void setCurrentItems(int option1, int option2) {
        wv_option1.setCurrentItem(option1);
        wv_option2.setCurrentItem(option2);
    }

    /**
     * 设置间距倍数,但是只能在1.2-4.0f之间
     *
     * @param lineSpacingMultiplier
     */
    public void setLineSpacingMultiplier(float lineSpacingMultiplier) {
        this.lineSpacingMultiplier = lineSpacingMultiplier;
        setLineSpacingMultiplier();
    }

    /**
     * 设置分割线的颜色
     *
     * @param dividerColor
     */
    public void setDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
        setDividerColor();
    }

    /**
     * 设置分割线的类型
     *
     * @param dividerType
     */
    public void setDividerType(WheelView.DividerType dividerType) {
        this.dividerType = dividerType;
        setDividerType();
    }


    /**
     * 设置分割线之间的文字的颜色
     *
     * @param textColorCenter
     */
    public void setTextColorCenter(int textColorCenter) {
        this.textColorCenter = textColorCenter;
        setTextColorCenter();
    }

    /**
     * 设置分割线以外文字的颜色
     *
     * @param textColorOut
     */
    public void setTextColorOut(int textColorOut) {
        this.textColorOut = textColorOut;
        setTextColorOut();
    }

    /**
     * Label 是否只显示中间选中项的
     *
     * @param isCenterLabel
     */

    public void isCenterLabel(boolean isCenterLabel) {
        wv_option1.isCenterLabel(isCenterLabel);
        wv_option2.isCenterLabel(isCenterLabel);
    }



    public interface OnTimeOptionsSelectChangeListener {
        void onOptionsSelectChanged(int options1, int options2);
    }



    public interface OnTimeOnOptionsSelectListener {
        void onOptionsSelect(int options1, int options2, View v);
    }

    public void setOptionsSelectChangeListener(OnTimeOptionsSelectChangeListener optionsSelectChangeListener) {
        this.optionsSelectChangeListener = optionsSelectChangeListener;
    }

    public void setOptionsSelectListener(OnTimeOnOptionsSelectListener optionsSelectListener) {
        this.optionsSelectListener = optionsSelectListener;
    }
}
