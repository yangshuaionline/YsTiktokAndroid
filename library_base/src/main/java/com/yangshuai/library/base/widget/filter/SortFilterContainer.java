package com.yangshuai.library.base.widget.filter;

import android.content.Context;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.yangshuai.library.base.R;
import com.yangshuai.library.base.view.recycleview.BaseRecycleAdapter;
import com.yangshuai.library.base.view.recycleview.BaseViewHolder;
import com.yangshuai.library.base.view.recycleview.SimpleDividerItemDecoration;
import com.yangshuai.library.base.widget.filter.base.BaseFilterContainerView;
import com.yangshuai.library.base.widget.filter.model.SortFilterData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 排序筛选器
 *
 * @author hcp
 * @created 2019/4/1
 */
public class SortFilterContainer extends BaseFilterContainerView {

    private Context content;
    private RecyclerView mRecyclerView;
    private SortListAdapter mListAdapter;
    private List<SortFilterData> mList;

    public SortFilterContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.content = context;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        setContentView(R.layout.base_filter_sort_container);
//        setContainerHeight(Utils.dp2Px(getContext(), 325)); // 设置容器高度


        mRecyclerView = findViewById(R.id.base_filter_sort_rv);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // 添加分隔线
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getContext(),
                SimpleDividerItemDecoration.VERTICAL_LIST,
                com.yangshuai.library.resource.R.drawable.base_line_divider,
                0));
        mList = new ArrayList<>();
        mListAdapter = new SortListAdapter(getContext(), mList);
        mListAdapter.setClick((v, vId, position, item) -> {
            // 清空选中状态
            for (int i = 0; i < mList.size(); i++) {
                mList.get(i).setSelect(false);
            }

            mList.get(position).setSelect(true);
            filterResult(item.getName(), item.getValue());

            mListAdapter.notifyDataSetChanged();
        });
        mRecyclerView.setAdapter(mListAdapter);
    }

    public void setData(List<SortFilterData> data) {
        mList.clear();
        mList.addAll(data);
        mListAdapter.notifyDataSetChanged();

    }

    /**
     * 将筛选参数返回
     *
     * @param name  菜单显示名称
     * @param value 排序参数
     */
    private void filterResult(String name, String value) {
        if (onFilterResultListener != null) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("Name", name);
            resultMap.put("Value", value);
            if (!resultMap.isEmpty()) {
                onFilterResultListener.onResult(SortFilterContainer.this, resultMap);
            } else {
                onFilterResultListener.onResult(SortFilterContainer.this, null);
            }
        }
        // 关闭筛选容器
        close();
    }

    private class SortListAdapter extends BaseRecycleAdapter<SortFilterData> {

        public SortListAdapter(Context context, List<SortFilterData> dataList) {
            super(context, dataList);
        }

        @Override
        protected int bindItemLayout() {
            return R.layout.base_filter_sort_item;
        }

        @Override
        protected void bindData(BaseViewHolder holder, SortFilterData data, int position) {
            TextView tvName = holder.getView(R.id.base_filter_sort_tv_name);
            tvName.setText(data.getName());

            // 设置选中状态
            if (data.isSelect()) {
                tvName.setTextColor(ContextCompat.getColor(mContext, R.color.theme));
            } else {
                tvName.setTextColor(ContextCompat.getColor(mContext, R.color.text));
            }
        }
    }
}
