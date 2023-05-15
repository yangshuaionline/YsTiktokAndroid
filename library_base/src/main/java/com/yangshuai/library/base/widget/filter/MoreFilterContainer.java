package com.yangshuai.library.base.widget.filter;

import android.content.Context;
import android.graphics.Color;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.yangshuai.lib.button.StateButton;
import com.yangshuai.library.base.R;
import com.yangshuai.library.base.utils.Utils;
import com.yangshuai.library.base.widget.filter.base.BaseFilterContainerView;
import com.yangshuai.library.base.widget.filter.model.MoreFilterChildData;
import com.yangshuai.library.base.widget.filter.model.MoreFilterGroupData;

import java.util.ArrayList;
import java.util.List;

/**
 * '更多'筛选容器
 *
 * @author hcp
 * @created 2019/4/2
 */
public class MoreFilterContainer extends BaseFilterContainerView {

    private RecyclerView mRecyclerView;
    private DelegateAdapter mDelegateAdapter;
    private List<MoreFilterGroupData> mFilterGroupData = new ArrayList<>();

    private StateButton btnOk, btnReset; // 确定和重置按钮

    public MoreFilterContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        setContentView(R.layout.base_filter_more_container);
    }

    @Override
    public void show() {
        super.show();
        // 当筛选容器显示出来后再初始化
        if (mRecyclerView == null) {
            initView();
        }
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.base_filter_more_rv);

        // 设置复用池大小
//        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
//        mRecyclerView.setRecycledViewPool(viewPool);
//        viewPool.setMaxRecycledViews(0, 10);
        VirtualLayoutManager layoutManager = new VirtualLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setNestedScrollingEnabled(false);
//        mRecyclerView.setFocusableInTouchMode(false);

        // 当hasConsistItemType=false的时候，不同子adapter之间的类型不共享
        mDelegateAdapter = new DelegateAdapter(layoutManager, false);
        List<DelegateAdapter.Adapter> adapters = new ArrayList<>();
        final List<Integer> viewTypes = new ArrayList<>();
        int dataSize = mFilterGroupData.size();
        for (int i = 0; i < dataSize; i++) {
            MoreFilterGroupData group = mFilterGroupData.get(i);
            final int column = group.getColumn();

            // 确定每组有多少个子项网格
            GridLayoutHelper layoutHelper = new GridLayoutHelper(column, group.getChilds().size());
            layoutHelper.setAutoExpand(false); // 如果autoExpand为true，视图的总宽度会填满可用区域；否则会在屏幕上留空白区域。
            layoutHelper.setMarginLeft(Utils.getDip2(getContext(), 16).intValue());
            layoutHelper.setMarginRight(Utils.getDip2(getContext(), 16).intValue());
            layoutHelper.setMarginBottom(Utils.getDip2(getContext(), 16).intValue());
            layoutHelper.setGap(Utils.getDip2(getContext(), 16).intValue()); // 设置垂直间距和水平间距。
            viewTypes.add(0);
            viewTypes.add(1);

            List<MoreFilterChildData> child = group.getChilds();
            for (int j = 0; j < child.size(); j++) {
                viewTypes.add(2);
            }

            // 跨列
            layoutHelper.setSpanSizeLookup(new GridLayoutHelper.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = viewTypes.get(position);
                    if (type == 0 || type == 1) {
                        return column;
                    }

                    return 1;
                }
            });

            MoreFilterAdapter adapter = new MoreFilterAdapter(getContext(), group, layoutHelper);
            adapter.setHasLine(i > 0);//第一组数据不显示线条
            adapters.add(adapter);
        }

        mDelegateAdapter.addAdapters(adapters);
        mRecyclerView.setAdapter(mDelegateAdapter);

        // 重置
        btnReset = findViewById(R.id.base_filter_more_btn_reset);
        btnReset.setOnClickListener(v -> {
            for (MoreFilterGroupData datum : mFilterGroupData) {
                // 清空选中状态
                List<MoreFilterChildData> childs = datum.getChilds();
                for (MoreFilterChildData child : childs) {
                    child.setSelected(false);
                }
            }
            mDelegateAdapter.notifyDataSetChanged();
        });

        // 确定
        btnOk = findViewById(R.id.base_filter_more_btn_ok);
        btnOk.setOnClickListener(v -> {
            if (onResultListener != null) {
                // 将参数回调出去
                onResultListener.onResult(mFilterGroupData, onResultListener.onConvert(mFilterGroupData));
            }
            close(); // 关闭筛选容器
        });
    }

    /**
     * 设置数据
     *
     * @param filterGroupData
     */
    public void setFilterData(List<MoreFilterGroupData> filterGroupData) {
        this.mFilterGroupData = filterGroupData;
    }

    private final class MoreFilterAdapter extends DelegateAdapter.Adapter<MoreFilterAdapter.ViewHolder> {

        private MoreFilterGroupData group;
        private GridLayoutHelper layoutHelper;
        private LayoutInflater inflater;
        private boolean hasLine;


        public void setHasLine(boolean hasLine) {
            this.hasLine = hasLine;
        }

        public MoreFilterAdapter(Context context, MoreFilterGroupData group, GridLayoutHelper layoutHelper) {
            this.group = group;
            this.layoutHelper = layoutHelper;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewHolder holder;
            if (viewType == 0) {
                // 0 分割线
                holder = new ViewHolder(inflater.inflate(R.layout.base_filter_more_cell_line, parent, false));
            } else if (viewType == 1) {
                // 1 标题文本
                holder = new ViewHolder(inflater.inflate(R.layout.base_filter_more_cell_title, parent, false));
            } else {
                // 2 选择按钮
                holder = new ViewHolder(inflater.inflate(R.layout.base_filter_more_cell_child, parent, false));
            }
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            int type = getItemViewType(position);
            if (type == 0) {
                if (hasLine) {
                    holder.itemView.setVisibility(View.VISIBLE);
                } else {
                    holder.itemView.setVisibility(View.INVISIBLE);
                }
            } else if (type == 1) {
                holder.titleView.setText(group.getGroupTitle());
            } else {
                final MoreFilterChildData bean = group.getChilds().get(position - 2);
//                holder.childBtn.setText(bean.getName());
                StateButton childView = (StateButton) holder.childBtn;
                childView.setText(bean.getName());
                if (bean.isSelected()) {
                    // 选中
                    childView.setNormalBackgroundColor(ContextCompat.getColor(getContext(), R.color.theme));
                    childView.setNormalTextColor(ContextCompat.getColor(getContext(), R.color.white));
                } else {
                    // 未选中
                    childView.setNormalBackgroundColor(Color.parseColor("#F7F7F7"));
                    childView.setNormalTextColor(ContextCompat.getColor(getContext(), R.color.text));
                }
                childView.setOnClickListener(v -> {
                    // 多选
                    if (group.isMultiple()) {
                        bean.setSelected(!bean.isSelected());
                        notifyDataSetChanged();
                        return;
                    } else {

                        if (bean.isSelected()) {
                            bean.setSelected(false);
                        } else {
                            List<MoreFilterChildData> childs = group.getChilds();
                            for (MoreFilterChildData child : childs) {
                                child.setSelected(bean == child);
                            }
                        }

                        notifyDataSetChanged();
                    }
                });
            }

        }

        @Override
        public int getItemCount() {
            return group.getChilds().size() + 2;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return 0;
            } else if (position == 1) {
                return 1;
            }
            return 2;
        }

        @Override
        public GridLayoutHelper onCreateLayoutHelper() {
            return layoutHelper;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private TextView titleView;
            private StateButton childBtn;

            public ViewHolder(View itemView) {
                super(itemView);
                View title = itemView.findViewById(R.id.base_filter_more_text_title);
                View child = itemView.findViewById(R.id.base_filter_more_btn_title);
                if (title != null) {
                    titleView = (TextView) title;
                }
                if (child != null) {
                    childBtn = (StateButton) child;
                }

            }
        }

    }

    private OnResultListener onResultListener;

    public <T> void setOnResultListener(OnResultListener<T> onResultListener) {
        this.onResultListener = onResultListener;

    }

    public interface OnResultListener<T> {
        T onConvert(List<MoreFilterGroupData> data);

        void onResult(List<MoreFilterGroupData> data, T result);
    }
}
