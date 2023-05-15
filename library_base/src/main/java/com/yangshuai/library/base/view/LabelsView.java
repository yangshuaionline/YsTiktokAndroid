package com.yangshuai.library.base.view;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;
import com.yangshuai.library.base.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 标签列表控件
 *
 * @Author hcp
 * @Created 3/28/19
 * @Editor hcp
 * @Edited 3/28/19
 * @Type
 * @Layout
 * @Api
 */
public class LabelsView extends FlexboxLayout {

    private List<Label> labels;
    private List<Label> selectedLabelList;
    private Map<Label, View> selectedLabels = new HashMap<>();
    private OnLabelClickListener labelClickListener;
    private OnLabelSelectListener onLabelSelectListener;
    private boolean multiSelected = true;
    private boolean canSelected = true;

    public LabelsView(@NonNull Context context) {
        super(context, null);
    }

    public LabelsView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setShowDivider(SHOW_DIVIDER_MIDDLE);
        setDividerDrawable(ContextCompat.getDrawable(context, R.drawable.base_flex_devider));
        setFlexWrap(FlexWrap.WRAP);

    }

    public void setCanSelected(boolean canSelected) {
        this.canSelected = canSelected;
    }

    /**
     * 设置是否可以多选，默认可以多选
     *
     * @param multiSelected
     */
    public void setMultiSelected(boolean multiSelected) {
        this.multiSelected = multiSelected;
    }

    /**
     * 设置点击事件监听
     *
     * @param labelClickListener
     */
    public void setLabelClickListener(OnLabelClickListener labelClickListener) {
        this.labelClickListener = labelClickListener;
    }

    /**
     * 设置选中监听
     *
     * @param onLabelSelectListener
     */
    public void setOnLabelSelectListener(OnLabelSelectListener onLabelSelectListener) {
        this.onLabelSelectListener = onLabelSelectListener;
    }

    /**
     * 设置标签数据
     *
     * @param labels
     */
    public void setLabels(@NonNull List<Label> labels) {
        this.labels = labels;
        setupLabels();
        notifyChanged();
    }

    private void setupLabels() {
        removeAllViews();
        for (Label label : labels) {
            View view = createLabel(label);
            addView(view);
        }
    }

    /**
     * 创建一个标签
     *
     * @param label 标签数据
     * @return
     */
    private View createLabel(Label label) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.base_view_label, this, false);

        TextView textView = view.findViewById(R.id.tv_label);
        textView.setText(label.name);

        // 设置选中的label
        if (selectedLabelList != null && selectedLabelList.contains(label)) {
            selectedLabels.put(label, view);
            selectedLabelList.remove(label);
        }

        view.setOnClickListener(v -> {

            if (canSelected) {
                if (selectedLabels.containsKey(label)) {
                    selectedLabels.remove(label);
                    if (onLabelSelectListener != null)
                        onLabelSelectListener.onSelect(label, false);
                } else {
                    // 不支持多选的情况下先清空数据，再添加label
                    if (!multiSelected) {
                        selectedLabels.clear();
                    }
                    if (onLabelSelectListener != null) onLabelSelectListener.onSelect(label, true);
                    selectedLabels.put(label, v);
                }
                notifyChanged();
            } else {
                selectedLabels.remove(label);
//                KLog.i("test", "移除" + label.name);
                notifyChanged();

            }

            if (labelClickListener != null) {
                labelClickListener.onLabelClick(label);
            }
        });
        view.setOnLongClickListener(view1 -> {
            if (labelClickListener != null) {

                labelClickListener.onLongClick(label, label.name);
            }

            return false;
        });

        return view;
    }

    /**
     * 设置选中项
     * @param selectedLabelList
     */
    public void setSelectedLabelList(List<Label> selectedLabelList) {
        this.selectedLabelList = selectedLabelList;
    }

    /**
     * 改变标签选中状态
     */
    private void notifyChanged() {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (selectedLabels.containsValue(view)) {
                view.setSelected(true);
            } else {
                view.setSelected(false);
            }
        }
    }

    /**
     * 获取所有选择的标签
     *
     * @return
     */
    public List<Label> getSelectedLabels() {

        return new ArrayList<>(selectedLabels.keySet());
    }

    public interface OnLabelClickListener {
        void onLabelClick(Label label);

        void onLongClick(Label label, String str);
    }

    public interface OnLabelSelectListener {
        /**
         * @param label    点击的label
         * @param selected true标示被选择
         */
        void onSelect(Label label, boolean selected);
    }

    public static class Label {
        public int id;
        public String name;
        public String varr1;
        public boolean isClick = true;

        public boolean isClick() {
            return isClick;
        }

        public void setClick(boolean click) {
            isClick = click;
        }

        public Label() {
        }

        public Label(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public Label(String varr1, String name) {
            this.varr1 = varr1;
            this.name = name;
        }

        @Override
        public boolean equals(Object obj) {

            if (obj == null) {
                return false;
            } else {

                if (obj instanceof Label) {
                    Label ne = (Label) obj;
                    if (ne.name.equals(this.name)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
