package com.yangshuai.library.base.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yangshuai.library.base.R;

import java.util.List;

public class SimpleRecyclerView extends RecyclerView {

    private int mMaxHeight;
    private Adapter adapter;
    private List selectedList;
    private List list;

    public SimpleRecyclerView(Context context) {
        super(context, null);
    }

    public SimpleRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        if (attrs != null) {
            TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.MaxHeightRecyclerView);
            mMaxHeight = (int) arr.getDimension(R.styleable.MaxHeightRecyclerView_maxHeight, 0);
            arr.recycle();
        }
        setLayoutManager(new LinearLayoutManager(context));
    }

    public void setMaxHeight(int maxHeight) {
        mMaxHeight = maxHeight;
    }

    public void bindItemView(int itemViewResId, BindViewHandler bindViewHandler) {
        adapter = new SimpleRecyclerView.Adapter(itemViewResId, bindViewHandler);

        setAdapter(adapter);
    }

    public void setData(List data) {
        this.list = data;
    }

    public void setSelectedList(List selectedList) {
        this.selectedList = selectedList;
    }

    public void notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mMaxHeight > 0) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(mMaxHeight, MeasureSpec.AT_MOST);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public class Adapter extends RecyclerView.Adapter {

        private LayoutInflater layoutInflater;
        private int layoutResId;
        private BindViewHandler bindViewHandler;


        public Adapter(int layoutResId, BindViewHandler bindViewHandler) {
            this.layoutResId = layoutResId;
            this.bindViewHandler = bindViewHandler;
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
            Object item = list.get(i);
            View itemView = viewHolder.itemView;
            itemView.setSelected(isSelected(item));

            bindViewHandler.onBindView(itemView, item);
        }

        @Override
        public int getItemCount() {
            return list == null ? 0 : list.size();
        }
    }

    public interface BindViewHandler {
        void onBindView(View itemView, Object itemData);
    }

    public boolean isSelected(Object item) {
        return selectedList != null && selectedList.contains(item);
    }


    /**
     * item间距装饰器
     */
    public static class SpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spacing;

        public SpacingItemDecoration(int spacing) {
            this.spacing = spacing;
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull State state) {

            int position = parent.getChildAdapterPosition(view);

            // 不为第一行
            if (position != 0) {
                outRect.top = spacing;
            }
        }
    }

}
