package com.yangshuai.library.base.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yangshuai.library.base.R;
import com.yangshuai.library.base.view.recycleview.BaseRecycleAdapter;
import com.yangshuai.library.base.view.recycleview.BaseViewHolder;
import com.yangshuai.library.base.widget.SimpleRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hcp
 * @create 2019-4-18
 * @Describe
 */
@SuppressLint("ValidFragment")
public class BottomSheetListDialog extends BottomSheetDialogFragment {


    private View view;
    private SimpleRecyclerView recycleView;
    private TextView tvTitle;

    // 菜单数据
    private List<String> list;

    // 选择点击事件
    private OnItemClick itemClick;

    // 取消点击事件
    private OnCancelClick onCancelClick;

    // 字体颜色
    private String color;

    // 当前选择的颜色
    private String currentSelectColor;

    private int height = 0;
    private BottomSheetAdapter adapter;

    // 当前选择(因为是后期扩展的，所以只能这么写，否则是可以在内部自行记录的)
    private String currentSelect;

    /**
     * 标题文本
     */
    private String titleStr;

    public BottomSheetListDialog(List<String> list) {
        this.list = list;
    }

    /**
     * @param list
     * @param height 菜单的高度（某些情况）
     */
    public BottomSheetListDialog(List<String> list, int height) {
        this.list = list;
        this.height = height;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.base_bottom_sheet, container);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 设置背景色透明
        getDialog().findViewById(R.id.design_bottom_sheet).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        tvTitle = view.findViewById(R.id.tv_title);
        if (!TextUtils.isEmpty(titleStr)) {
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(titleStr);
        }
        recycleView = view.findViewById(R.id.recycleView);
//        if (height != 0) {
//            recycleView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
//        }
        recycleView.setMaxHeight(height);
        recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new BottomSheetAdapter(getActivity(), list);
        recycleView.setAdapter(adapter);

        adapter.setClick((v, vId, position, item) -> {
            if (itemClick != null) {
                itemClick.onClick(position, item);
                dismiss();
            }
        });

        //取消
        view.findViewById(R.id.cancel).setOnClickListener(v -> {
            if (onCancelClick != null) {
                onCancelClick.onCancelClick();
            }
            dismiss();
        });
    }


    /**
     * 底部菜单适配器
     */
    public class BottomSheetAdapter extends BaseRecycleAdapter<String> {

        public BottomSheetAdapter(Context context, List<String> dataList) {
            super(context, dataList);
        }

        @Override
        protected int bindItemLayout() {
            return R.layout.base_cell_bottom_sheet;
        }

        @Override
        protected void bindData(BaseViewHolder holder, String data, int position) {
            TextView itemText = holder.getView(R.id.item_text);
            if (!TextUtils.isEmpty(color)) {
                itemText.setTextColor(Color.parseColor(color));
            }

            if (!TextUtils.isEmpty(currentSelect)
                    && data.equals(currentSelect)
                    && !data.equals("取消")) {
                if (!TextUtils.isEmpty(currentSelectColor)) {
                    itemText.setTextColor(Color.parseColor(currentSelectColor));
                } else {
                    itemText.setTextColor(Color.parseColor("#18A65E"));
                }
            }
            itemText.setText(data);
        }
    }

    /**
     * 设置字体颜色
     *
     * @param color
     */
    public void setColor(String color) {
        this.color = color;
    }

    public interface OnItemClick {
        void onClick(int position, String item);
    }

    public void setItemClick(OnItemClick itemClick) {
        this.itemClick = itemClick;
    }

    //取消
    public interface OnCancelClick {
        void onCancelClick();
    }

    public void setOnCancelClick(OnCancelClick onCancelClick) {
        this.onCancelClick = onCancelClick;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }

    public void setTvTitle(String titleStr) {
        this.titleStr = titleStr;
    }


    public final void notifyDataSetChanged() {
        if (adapter != null) adapter.notifyDataSetChanged();
    }

    public String getCurrentSelect() {
        return currentSelect;
    }

    public void setCurrentSelect(String currentSelect) {
        this.currentSelect = currentSelect;
    }

    public String getCurrentSelectColor() {
        return currentSelectColor;
    }

    public void setCurrentSelectColor(String currentSelectColor) {
        this.currentSelectColor = currentSelectColor;
    }
}
