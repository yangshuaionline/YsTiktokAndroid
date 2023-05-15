package com.yangshuai.library.base.dialog;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.yangshuai.library.base.R;
import com.yangshuai.library.base.utils.ScreenUtils;
import com.yangshuai.library.base.widget.SimpleRecyclerView;

import java.util.List;

/**
 * 垂直列表底部弹窗
 * 功能：
 * 1. 设置列表item布局，绑定item数据
 * 2. 设置选中item
 * <p>
 * 使用例子：
 * {@link com.yangshuai.library.base.dialog.Examples#showListBottomSheetDialog(Context)}
 *
 * @Author hcp
 * @Created 3/20/19
 * @Editor hcp
 * @Edited 3/20/19
 * @Type
 * @Layout
 * @Api
 */
public class ListBottomSheetDialog extends BaseBottomSheetDialog {

    private SimpleRecyclerView simpleRecyclerView;

    public ListBottomSheetDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected View createContentView(ViewGroup viewGroup) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.base_dialog_list_bottom_sheet, viewGroup, false);
        simpleRecyclerView = view.findViewById(R.id.simple_recycle_view);
        simpleRecyclerView.setMaxHeight((int) (ScreenUtils.getScreenHeight() * 0.7));
        return simpleRecyclerView;
    }

    @Override
    public void onShow() {

    }

    @Override
    public void onDismiss() {

    }

    /**
     * 绑定item
     *
     * @param items         item数据列表
     * @param selectedItems 选中的item列表
     * @param listener      item点击事件回调
     */
    public void bindItem(List<Item> items, List<Item> selectedItems, OnItemClickListener listener) {
        simpleRecyclerView.setData(items);
        simpleRecyclerView.setSelectedList(selectedItems);
        simpleRecyclerView.bindItemView(R.layout.base_dialog_item_bottom_sheet, (itemView, itemData) -> {
            Item item = ((Item) itemData);
            Button button = itemView.findViewById(R.id.btn_bottom_sheet_item);
            button.setText(item.getName());
            button.setOnClickListener(v -> {
                listener.onItemClick(item);
                dismiss();
            });
        });
    }

    /**
     * 绑定item
     *
     * @param items    item数据列表
     * @param listener item点击事件回调
     */
    public void bindItem(List<Item> items, OnItemClickListener listener) {
        simpleRecyclerView.setData(items);
        simpleRecyclerView.bindItemView(R.layout.base_dialog_item_bottom_sheet, (itemView, itemData) -> {
            Item item = ((Item) itemData);
            Button button = itemView.findViewById(R.id.btn_bottom_sheet_item);
            button.setText(item.getName());
            button.setOnClickListener(v -> {
                listener.onItemClick(item);
                dismiss();
            });
        });
    }

    public void setItems(List<Item> items) {
        simpleRecyclerView.setData(items);
        simpleRecyclerView.notifyDataSetChanged();
    }

    public void setSelectedItems(List<Item> items) {
        simpleRecyclerView.setSelectedList(items);
        simpleRecyclerView.notifyDataSetChanged();
    }

    /**
     * 判断item是否选中
     *
     * @param item 需要验证的item
     * @return
     */
    public boolean isSelected(Item item) {
        return simpleRecyclerView.isSelected(item);
    }

    /**
     * item列表或者被选择item列表发生变化时调用
     */
    public void notifyItemChanged() {
        simpleRecyclerView.notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(Item item);
    }

    public static class Item {
        private int id;
        private String name;
        private long  other;
        private String rmark;

        public Item() {
        }

        public Item(String name, String rmark) {
            this.name = name;
            this.rmark = rmark;
        }

        public String getRmark() {
            return rmark;
        }

        public void setRmark(String rmark) {
            this.rmark = rmark;
        }

        public Item(long other, String name) {
            this.other = other;
            this.name = name;
        }

        public Item(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public Item(String name) {
            this.name = name;
        }
        public long getOther() {
            return other;
        }

        public void setOther(long other) {
            this.other = other;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
