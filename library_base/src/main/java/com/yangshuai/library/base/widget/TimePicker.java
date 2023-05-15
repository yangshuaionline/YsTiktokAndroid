package com.yangshuai.library.base.widget;

import android.app.Activity;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.yangshuai.library.base.R;
import com.yangshuai.library.base.interfaces.TimePickerListener;

import java.util.Calendar;

/**
 * 时间选择器：年月日时分
 *
 * @Author hcp
 * @Created 3/26/19
 * @Editor hcp
 * @Edited 3/26/19
 * @Type
 * @Layout
 * @Api
 */
public class TimePicker {

    private TimePickerView pickerViewTime;
    private TimePickerListener listener;


    public TimePicker(Activity activity) {
        initTimePicker(activity, "", null, null);
    }

    public TimePicker(Activity activity, String title) {
        initTimePicker(activity, title, null, null);
    }

    public TimePicker(Activity activity, String title, Calendar startDate, Calendar endDate) {
        initTimePicker(activity, title, startDate, endDate);
    }

    private void initTimePicker(Activity activity, String title, Calendar startDate, Calendar endDate) {
        //开始时间   默认当前时间10年前
        if (startDate == null) {
            startDate = Calendar.getInstance();
            startDate.add(Calendar.YEAR, -10);
        }
        // 结束时间   默认当前时间10年后
        if (endDate == null) {
            endDate = Calendar.getInstance();
            endDate.add(Calendar.YEAR, +10);
        }
        if (TextUtils.isEmpty(title)) {
            title = "请选择";
        }
        Calendar selectedDate = Calendar.getInstance();// 系统当前时间
        String finalTitle = title;
        pickerViewTime = new TimePickerBuilder(activity,
                (date, v) -> {
                    if (listener != null) {
                        listener.onTimeSelect(date);
                    }
                })
                .setLayoutRes(R.layout.base_picker_custom, v -> {
                    // 获取自定义布局里面的控件
                    TextView tvPickerTitle = v.findViewById(R.id.tv_picker_title);
                    tvPickerTitle.setText(finalTitle);
                    View vok = v.findViewById(R.id.rl_picker_ok); // 确定
                    vok.setOnClickListener(v1 -> {
                        pickerViewTime.returnData();
                        pickerViewTime.dismiss();
                    });
                })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setType(new boolean[]{true, true, true, true, true, false})
                .setLabel("年", "月", "日", "时", "分", "")
                // 设置滚轮文字大小
                .setContentTextSize(16)
                // 设置选中文字颜色
                .setTextColorCenter(ContextCompat.getColor(activity, R.color.theme))
                // 设置未选中文字颜色
                .setTextColorOut(ContextCompat.getColor(activity, R.color.light_grey))
                // 解决PickerView被虚拟按键遮挡的问题
                .setDecorView(activity.getWindow().getDecorView().findViewById(android.R.id.content))
                .build();
    }

    public void show() {
        pickerViewTime.show();
    }

    public void setListener(TimePickerListener listener) {
        this.listener = listener;
    }

    public void dismiss() {
        pickerViewTime.dismiss();
    }

    public TimePickerView getPickerViewTime() {
        return pickerViewTime;
    }
}
