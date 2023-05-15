package com.yangshuai.library.base.view.pickerview;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.contrarywind.view.WheelView;
import com.yangshuai.library.base.R;

/**
 * Build Options
 *
 * @author hcp
 * @created 2019/3/21
 */
public class SinglePickerOptions {

    private static final int PICKER_VIEW_BTN_COLOR_NORMAL = 0xFF057dff;
    private static final int PICKER_VIEW_BG_COLOR_TITLE = 0xFFf5f5f5;
    private static final int PICKER_VIEW_COLOR_TITLE = 0xFF000000;
    private static final int PICKER_VIEW_BG_COLOR_DEFAULT = 0xFFFFFFFF;

    public WheelRoomOptions.OnRoomOnOptionsSelectListener optionsSelectListener;
    public WheelRoomOptions.OnRoomOptionsSelectChangeListener optionsSelectChangeListener;

    public OnTimeSelectListener timeSelectListener;
    public View.OnClickListener cancelListener;

    //options picker
    public String label1; //单位字符
    public int option; //默认选中项
    public int x_offset_one;//x轴偏移量

    public boolean cyclic1 = false;//是否循环，默认否

    public boolean isRestoreItem = false; //切换时，还原第一项

    //******* general field ******//
    public int layoutRes;
    public ViewGroup decorView;
    public int textGravity = Gravity.CENTER;
    public Context context;

    public String textContentTitle;//标题文字
    public int bgColorWheel = PICKER_VIEW_BG_COLOR_DEFAULT;//滚轮背景颜色

    public int textColorOut = 0xFFa8a8a8; //分割线以外的文字颜色
    public int textColorCenter = 0xFF2a2a2a; //分割线之间的文字颜色
    public int textSizeContent = 18;// 滚轮内容文字大小
    public int dividerColor = 0xFFd5d5d5; //分割线的颜色
    public int outSideColor = -1; //显示时的外部背景色颜色,默认是灰色

    public float lineSpacingMultiplier = 1.6f; // 条目间距倍数 默认1.6
    public boolean isDialog;//是否是对话框模式

    public boolean cancelable = true;//是否能取消
    public boolean isCenterLabel = false;//是否只显示中间的label,默认每个item都显示
    public Typeface font = Typeface.MONOSPACE;//字体样式
    public WheelView.DividerType dividerType = WheelView.DividerType.FILL;//分隔线类型

    public SinglePickerOptions() {
        layoutRes = R.layout.base_picker_custom;
    }

}
