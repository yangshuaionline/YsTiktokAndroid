package com.yangshuai.library.base.widget;

import android.app.Activity;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.yangshuai.library.base.R;

import java.util.Calendar;
import java.util.Date;

/**
 * 日期选择器：年月日
 *
 * @Author hcp
 * @Created 3/28/19
 * @Editor hcp
 * @Edited 3/28/19
 * @Type
 * @Layout
 * @Api
 */
public class DatePicker {
    private TimePickerView pickerViewDate;

    private OnDatePickListener onDatePickListener;


    public DatePicker(Activity activity) {
        initDatePicker(activity, "", null, null);
    }

    public DatePicker(Activity activity, String title) {
        initDatePicker(activity, title, null, null);
    }

    public DatePicker(Activity activity, String title, Calendar selectedDate) {
        initDatePicker(activity, title, null, null,selectedDate);
    }

    public DatePicker(Activity activity, String title, Calendar startDate, Calendar endDate) {
        initDatePicker(activity, title, startDate, endDate);
    }

    public DatePicker(Activity activity, String title, Calendar startDate, Calendar endDate, Calendar selectedDate) {
        initDatePicker(activity, title, startDate, endDate, selectedDate);
    }

    private void initDatePicker(Activity activity, String title, Calendar startDate, Calendar endDate) {
        if (TextUtils.isEmpty(title)) {
            title = "请选择";
        }
        if (startDate == null) {
            //开始时间 当前时间10年前
            startDate = Calendar.getInstance();
            startDate.add(Calendar.YEAR, -10);
        }

        if (endDate == null) {
            // 结束时间   当前时间10年后
            endDate = Calendar.getInstance();
            endDate.add(Calendar.YEAR, +10);
        }

        // 系统当前时间
        Calendar selectedDate = Calendar.getInstance();
        String finalTitle = title;
        pickerViewDate = new TimePickerBuilder(activity,
                (date, v) -> {
                    if (onDatePickListener != null) {
                        onDatePickListener.onDatePick(date);
                    }
                })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.base_picker_custom, v -> {
                    TextView tvPickerTitle = v.findViewById(R.id.tv_picker_title);
                    tvPickerTitle.setText(finalTitle);
                    View vok = v.findViewById(R.id.rl_picker_ok); // 确定
                    vok.setOnClickListener(v1 -> {
                        pickerViewDate.returnData();
                        pickerViewDate.dismiss();
                    });
                })
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

    private void initDatePicker(Activity activity, String title, Calendar startDate, Calendar endDate, Calendar selectedDate) {
        if (TextUtils.isEmpty(title)) {
            title = "请选择";
        }
        if (startDate == null) {
            //开始时间 当前时间10年前
            startDate = Calendar.getInstance();
            startDate.add(Calendar.YEAR, -10);
        }

        if (endDate == null) {
            // 结束时间   当前时间10年后
            endDate = Calendar.getInstance();
            endDate.add(Calendar.YEAR, +10);
        }

        // 当前时间
//        Calendar selectedDate = Calendar.getInstance();
        String finalTitle = title;
        pickerViewDate = new TimePickerBuilder(activity,
                (date, v) -> {
                    if (onDatePickListener != null) {
                        onDatePickListener.onDatePick(date);
                    }
                })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.base_picker_custom, v -> {
                    TextView tvPickerTitle = v.findViewById(R.id.tv_picker_title);
                    tvPickerTitle.setText(finalTitle);
                    View vok = v.findViewById(R.id.rl_picker_ok); // 确定
                    vok.setOnClickListener(v1 -> {
                        pickerViewDate.returnData();
                        pickerViewDate.dismiss();
                    });
                })
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


    public TimePickerView getPickerViewDate() {
        return pickerViewDate;
    }

    public void setPickerViewDate(TimePickerView pickerViewDate) {
        this.pickerViewDate = pickerViewDate;
    }

    public interface OnDatePickListener {
        void onDatePick(Date date);
    }

    public void setOnDatePickListener(OnDatePickListener onDatePickListener) {
        this.onDatePickListener = onDatePickListener;
    }

    public void show() {
        pickerViewDate.show();
    }

}
