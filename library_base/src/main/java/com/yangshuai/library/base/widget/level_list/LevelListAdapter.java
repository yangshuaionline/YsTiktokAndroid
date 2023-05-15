package com.yangshuai.library.base.widget.level_list;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.yangshuai.library.base.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现分级列表的适配器，暂时只支持二级列表
 * 应用于RecyclerView才能生效
 *
 * @Author hcp
 * @Created 3/22/19
 * @Editor hcp
 * @Edited 3/22/19
 * @Type
 * @Layout
 * @Api
 */
public class LevelListAdapter extends RecyclerView.Adapter {

    // 需要显示的item（包括展开的二级item）
    private List<Item> showedItems;
    // 标记选中的二级item
    private Item selectedItem;

    private OnItemClickListener onItemClickListener;

    public void setGroupItems(List<Item> groupItems) {
        showedItems = new ArrayList<>();
        if (groupItems != null) {
            for (Item item : groupItems) {
                showedItems.add(item);
                if (item.isExpanded() && item.getChildren() != null) {
                    for (Item child : item.getChildren()) {
                        showedItems.add(child);
                        if (child.isExpanded()) {
                            selectedItem = child;
                        }
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    public void setSelectedItem(Item selectedItem) {
        this.selectedItem = selectedItem;
        notifyDataSetChanged();
    }

    public void setSavaSelected(int index,Item parent,Item child){
        if(index!=0){
            onGroupClick(parent);
        }
        this.selectedItem = child;
        notifyDataSetChanged();
    }

    /**
     * 设置二级节点的点击事件
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int itemType) {

        if (itemType == Level.LEVEL_ONE) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.base_rv_item_level_one, viewGroup, false);
            return new GroupViewHolder(view);
        } else if (itemType == Level.LEVEL_TWO) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.base_rv_item_level_tow, viewGroup, false);
            return new ChildViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof GroupViewHolder) {
            GroupViewHolder groupViewHolder = ((GroupViewHolder) viewHolder);
            bindGroup(groupViewHolder, position);
        } else if (viewHolder instanceof ChildViewHolder) {
            ChildViewHolder childViewHolder = ((ChildViewHolder) viewHolder);
            bindChild(childViewHolder, position);
        }
    }

    /**
     * 处理二级节点的数据绑定
     *
     * @param childViewHolder
     * @param position
     */
    private void bindChild(ChildViewHolder childViewHolder, final int position) {

        final Item item = showedItems.get(position);
        childViewHolder.tvSubCompanyName.setText(item.getName());
        childViewHolder.tvSubCompanyName.setSelected(item == selectedItem);
        childViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(item);
                }

                int selectedIndex;
                selectedIndex = showedItems.indexOf(selectedItem);


                selectedItem = item;
                if (selectedIndex != -1) {
                    notifyItemChanged(selectedIndex);
                }
                notifyItemChanged(position);
            }
        });

    }

    /**
     * 处理一级节点的数据绑定
     *
     * @param groupViewHolder
     * @param position
     */
    private void bindGroup(GroupViewHolder groupViewHolder, int position) {
        final Item item = showedItems.get(position);
        groupViewHolder.tvCompanyName.setText(item.getName());

        groupViewHolder.itemView.setSelected(item.isExpanded());
        groupViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onGroupClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return showedItems == null ? 0 : showedItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        Item item = showedItems.get(position);
        return item.getLevel();
    }

    /**
     * 处理一级节点的点击事件
     *
     * @param item
     */
    private void onGroupClick(Item item) {

        // 展开二级节点时，关闭展开，否则展开二级节点
        if (item.isExpanded()) {

            disexpandGroup(item);
        } else {
            // 先隐藏上一个打开的
            Item expandedGroup = findExpandedGroup();
            if (expandedGroup != null) {
                disexpandGroup(expandedGroup);
            }
            expandGroup(item);
        }

    }

    /**
     * 根据id找到当前展示的item
     *
     * @param id
     * @return
     */
    private Item findItemById(String id) {
        for (Item item : showedItems) {
            if (id.equals(item.getId())) {
                return item;
            }
        }
        return null;
    }

    /**
     * 找出当前展开的一级节点
     *
     * @return
     */
    private Item findExpandedGroup() {
        for (Item item : showedItems) {
            if (item.isExpanded()) {
                return item;
            }
        }
        return null;
    }

    /**
     * 展开一级节点下的二级节点
     *
     * @param item
     */
    private void expandGroup(Item item) {
        item.setExpanded(true);

        if (item.getChildren() != null && item.getChildren().size() != 0) {

            int startIndex = showedItems.indexOf(item) + 1;

            showedItems.addAll(startIndex, item.getChildren());

            int itemCount = item.getChildren().size();
//            notifyItemRangeInserted(startIndex, itemCount);
        }
//        notifyItemChanged(showedItems.indexOf(item));
        notifyDataSetChanged();
    }

    /**
     * 关闭一级节点下的二级节点的展开
     *
     * @param item
     */
    private void disexpandGroup(Item item) {
        item.setExpanded(false);

        if (item.getChildren() != null && item.getChildren().size() != 0) {

            int startIndex = showedItems.indexOf(item) + 1;

            showedItems.removeAll(item.getChildren());
            int itemCount = item.getChildren().size();
//            notifyItemRangeRemoved(startIndex, itemCount);
        }
//        notifyItemChanged(showedItems.indexOf(item));
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {

        void onItemClick(Item item);
    }

    /**
     * 一级节点ViewHolder
     */
    public class GroupViewHolder extends RecyclerView.ViewHolder {

        TextView tvCompanyName;

        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCompanyName = itemView.findViewById(R.id.tv_company_name);
        }
    }

    /**
     * 二级节点ViewHolder
     */
    public class ChildViewHolder extends RecyclerView.ViewHolder {

        TextView tvSubCompanyName;

        public ChildViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSubCompanyName = itemView.findViewById(R.id.tv_sub_company_name);
        }
    }
}
