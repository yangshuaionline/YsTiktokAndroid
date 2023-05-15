package com.yangshuai.library.base.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yangshuai.library.base.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hcp
 * 带间距的网格布局
 */
public class TagSimpleGridView extends RecyclerView {

    private Adapter adapter;
    private GridSpacingItemDecoration gridSpacingItemDecoration;

    private OnItemClickListener listener;

    private List<Item> selectedItems = new ArrayList<>();
    private List<Item> disableItems = new ArrayList<>();
    private List<Item> displayItems = new ArrayList<>();
    private List<Item> someItems = new ArrayList<>();
    private List<Item> moreItems = new ArrayList<>();

    private boolean muiltSelected = false;
    private boolean displayAll = true;
    private int spanCount;

    public TagSimpleGridView(@NonNull Context context) {
        super(context, null);
    }

    public TagSimpleGridView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagSimpleGridView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SimpleGridView, defStyle, 0);
        int itemSpacing = (int) a.getDimension(R.styleable.SimpleGridView_itemSpacing, dp2Px(context, 20));
        spanCount = a.getInteger(R.styleable.SimpleGridView_spanCount, 3);
        a.recycle();
        gridSpacingItemDecoration = new GridSpacingItemDecoration(spanCount, itemSpacing);
        setLayoutManager(new GridLayoutManager(context, spanCount) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        addItemDecoration(gridSpacingItemDecoration);

        adapter = new Adapter(R.layout.base_grid_tag_item) {
            @Override
            public void onBindView(View itemView, Item item) {
                TextView textView = itemView.findViewById(R.id.tv_label);
                textView.setText(item.getDiaplayName());

                if (selectedItems.contains(item)) {
                    itemView.setSelected(true);
                } else {
                    itemView.setSelected(false);
                }

                if (disableItems.contains(item)) {
                    itemView.setEnabled(false);
                } else {
                    itemView.setEnabled(true);
                }

                itemView.setOnClickListener(v -> {
                    if (selectedItems.contains(item)) {
                        if (muiltSelected) {
                            selectedItems.remove(item);
                        }
                    } else {
                        if (!muiltSelected) {
                            selectedItems.clear();
                        }
                        selectedItems.add(item);
                    }
                    notifyDataSetChanged();
                    if (listener != null) {
                        listener.onClick(item);
                    }
                });

            }
        };

        setAdapter(adapter);
    }

    /**
     * 设置禁止选择的选项
     *
     * @param items
     */
    public void setDisableItems(List<Item> items) {
        disableItems.clear();
        disableItems.addAll(items);
    }

    /**
     * 获取已选择的选项
     *
     * @return
     */
    public List<Item> getSelectedItems() {
        return selectedItems;
    }

    /**
     * 设置是否多选
     *
     * @param muiltSelected
     */
    public void setMuiltSelected(boolean muiltSelected) {
        this.muiltSelected = muiltSelected;
    }

    /**
     * 设置item列数和间距
     *
     * @param itemSpacing
     * @param spanCount
     */
    private void setup(int itemSpacing, int spanCount) {
        this.spanCount = spanCount;
        int spacing = dp2Px(getContext(), itemSpacing);
        gridSpacingItemDecoration.setSpacing(spacing);
        gridSpacingItemDecoration.setSpanCount(spanCount);
    }

    /**
     * 设置item的点击事件
     *
     * @param listener
     */
    public void setItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * 设置是否全部显示，默认全部显示
     * 需要在设置数据前设置生效，否则初次显示时显示全部
     *
     * @param displayAll
     */
    public void setDisplayAll(boolean displayAll) {
        this.displayAll = displayAll;
    }

    /**
     * 显示更多
     */
    public void displayMore() {
        if (displayAll) {
            return;
        }

        displayItems.clear();
        displayItems.addAll(someItems);
        displayItems.addAll(moreItems);
        notifyDataSetChanged();
    }

    /**
     * 显示部分
     */
    public void displaySome() {
        if (displayAll) {
            return;
        }
        displayItems.clear();
        displayItems.addAll(someItems);
        notifyDataSetChanged();
    }

    /**
     * 设置数据
     *
     * @param data
     */
    public void setData(List<Item> data) {
        displayItems.clear();
        someItems.clear();
        moreItems.clear();
        if (data.size() <= getSpanCount()) {
            someItems.addAll(data);
        } else {
            someItems.addAll(data.subList(0, getSpanCount()));
            moreItems.addAll(data.subList(getSpanCount(), data.size()));
        }

        if (displayAll) {
            displayItems.addAll(data);
        } else {
            displayItems.addAll(someItems);
        }

        adapter.setList(displayItems);
    }

    public void clear() {
        displayItems.clear();
        someItems.clear();
        moreItems.clear();
        adapter.setList(null);
        adapter.notifyDataSetChanged();
    }

    /**
     * 数据发生改变时通知更新
     */
    public void notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
    }


    public static int dp2Px(Context context, int dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (scale * dp + 0.5);
    }

    public abstract class Adapter extends RecyclerView.Adapter {

        private LayoutInflater layoutInflater;
        private int layoutResId;
        private List<Item> list;

        public Adapter(int layoutResId) {
            this.layoutResId = layoutResId;
        }

        public void setList(List<Item> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            if (layoutInflater == null) {
                layoutInflater = LayoutInflater.from(viewGroup.getContext());
            }

            View view = layoutInflater.inflate(layoutResId, viewGroup, false);

            ViewHolder viewHolder = new ViewHolder(view) {
            };

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            Item data = list.get(i);
            View itemView = viewHolder.itemView;
            onBindView(itemView, data);
        }

        abstract public void onBindView(View itemView, Item data);

        @Override
        public int getItemCount() {
            return list == null ? 0 : list.size();
        }
    }

    public static class GridSpacingItemDecoration extends ItemDecoration {

        private int spanCount;
        private int spacing;

        public GridSpacingItemDecoration(int spanCount, int spacing) {
            this.spanCount = spanCount;
            this.spacing = spacing;
        }

        public void setSpanCount(int spanCount) {
            this.spanCount = spanCount;
        }

        public void setSpacing(int spacing) {
            this.spacing = spacing;
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull State state) {

            int position = parent.getChildAdapterPosition(view);
            int column = position % spanCount;

            // 不为第一行
            if (position > spanCount - 1) {
                outRect.top = spacing;
            }

            outRect.left = column * spacing / spanCount;
            outRect.right = spacing - (column + 1) * spacing / spanCount;
//
//            if (position < spanCount) {
//                outRect.top = spacing;
//            }
//            outRect.bottom = spacing;
        }
    }


    public static class Item {
        private String name;
        private String appendText;
        private int id;
        private String strId;
        private String FloorNo;


        public Item(){}

        public Item(String name, String appendText, int id) {
            this.name = name;
            this.appendText = appendText;
            this.id = id;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "name='" + name + '\'' +
                    ", appendText='" + appendText + '\'' +
                    ", id=" + id +
                    '}';
        }

        public Item(String name, int id) {
            this.name = name;
            this.id = id;
        }

        public Item(String name, String strId) {
            this.name = name;
            this.strId = strId;
        }

        public Item(String name, String strId,String FloorNo) {
            this.name = name;
            this.strId = strId;
            this.FloorNo = FloorNo;
        }

        public String getFloorNo() {
            return FloorNo;
        }

        public void setFloorNo(String floorNo) {
            FloorNo = floorNo;
        }

        public Item(String name) {
            this.name = name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setAppendText(String appendText) {
            this.appendText = appendText;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public String getStrId() {
            return strId;
        }

        public void setStrId(String strId) {
            this.strId = strId;
        }

        public String getName() {
            return this.name;
        }

        public String getDiaplayName() {

            if (appendText != null)
                return name + appendText;

            return name;
        }

    }

    public interface OnItemClickListener {
        void onClick(Item item);
    }

    public int getSpanCount() {
        return spanCount;
    }

    public void setSpanCount(int spanCount) {
        this.spanCount = spanCount;
    }


}

