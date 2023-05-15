package com.yangshuai.library.base.view.recycleview;

import android.content.Context;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


/**
 * RecycleView适配器基类
 * Created by lsy on 2017/4/24.
 */
public abstract class BaseRecycleAdapter<T> extends RecyclerView.Adapter<com.yangshuai.library.base.view.recycleview.BaseViewHolder> implements DataHelper<T> {

    protected Context mContext;
    protected LayoutInflater layoutInflater;
    protected List<T> dataList;
    protected int layoutId;
    protected MultiTypeSupport<T> multiTypeSupport; // 多类型布局支持

    // 头部和尾部相关
    public static final int TYPE_HEADER = -1;
    public static final int TYPE_FOOTER = -2;
    private View mHeaderView;
    private View mFooterView;

    // 点击事件相关
    private OnItemClick onItemClick; // 单击
    private OnItemLongClick onItemLongClick; // 长按


    public BaseRecycleAdapter(Context context, List<T> dataList) {
        this.mContext = context;
        this.layoutInflater = LayoutInflater.from(mContext);
        this.dataList = dataList;
        this.layoutId = bindItemLayout();
    }


    protected Context getContext() {
        return mContext;
    }

    /**
     * 设置头部
     *
     * @param headerView
     */
    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }


    /**
     * 设置尾部
     *
     * @param footerView
     */
    public View setFooterView(View footerView) {
        this.mFooterView = footerView;
        if (dataList.size() == 0 && mHeaderView == null) {
            notifyItemInserted(0);
        } else if (dataList.size() > 0 && mHeaderView == null) {
            notifyItemInserted(dataList.size());
        } else if (dataList.size() == 0 && mHeaderView != null) {
            notifyItemInserted(dataList.size() + 1);
        } else {
            notifyItemInserted(dataList.size());
        }
        return mFooterView;
    }

    /**
     * 获取头部
     *
     * @return
     */
    public View getHeaderView() {
        return mHeaderView;
    }


    /**
     * 获取尾部
     *
     * @return
     */
    public View getFooterView() {
        return mFooterView;
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new BaseViewHolder(mHeaderView);
        }
        if (mFooterView != null && viewType == TYPE_FOOTER) {
            return new BaseViewHolder(mFooterView);
        }

        if (multiTypeSupport != null) {
            layoutId = viewType;
        } else {
            layoutId = bindItemLayout();
        }
        BaseViewHolder holder = null;

        if (supportDataBinding()) {
            ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    layoutId, parent, false);
            holder = new BaseViewHolder(binding.getRoot());
            holder.setBinding(binding);
        } else {
            View itemView = layoutInflater.inflate(layoutId, parent, false);
            holder = new BaseViewHolder(itemView);
        }

        return holder;
    }


    protected boolean supportDataBinding() {
        return false;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_HEADER) return;
        if (getItemViewType(position) == TYPE_FOOTER) return;
        position = getRealPosition(position);
        final T data = dataList.get(position);
        bindData(holder, data, position);
        bindListener(holder, data, position);
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0 && mHeaderView != null) {
            // 加载Header
            return TYPE_HEADER;
        }

        if (position == (getItemCount() - 1) && mFooterView != null) {
            // 加载Footer
            return TYPE_FOOTER;
        }

        position = getRealPosition(position);
        if (multiTypeSupport != null) {
            // 多布局
            return multiTypeSupport.getMultiTypeLayout(dataList.get(position), position);

        }
        return position;
    }

    /*返回View中Item的个数，这个时候，总的个数应该是列表中Item的个数加上HeaderView和FooterView*/
    @Override
    public int getItemCount() {
        int size = dataList.size();

        // 有头有尾
        if (mHeaderView != null && mFooterView != null) {
            return size + 2;
        }

        // 只有头或只有尾
        if (mHeaderView != null || mFooterView != null) {
            return size + 1;
        }
        return size;

    }

    private int getRealPosition(int position) {
        if (mHeaderView != null) {
            return position - 1;
        }
        return position;
    }

    /**
     * 绑定item的布局
     *
     * @return
     */
    protected abstract int bindItemLayout();


    /**
     * 绑定数据
     *
     * @param holder
     * @param data
     * @param position
     */
    protected abstract void bindData(BaseViewHolder holder, T data, int position);

    // 绑定监听器
    private void bindListener(BaseViewHolder holder, final T data, final int position) {
        // 设置单击监听器
        holder.setOnItemViewClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClick != null) {
                    onItemClick.onItemClick(v, v.getId(), position, data);
                }
            }
        });

        // 设置长按监听器
        holder.setOnItemViewLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemLongClick != null) {
                    onItemLongClick.onItemLongClick(v, v.getId(), position, data);
                }
                return true;
            }
        });
    }


    /**
     * 设置 RecyclerView Item 点击事件 的回调
     *
     * @param itemClick
     */
    public void setClick(OnItemClick<T> itemClick) {
        this.onItemClick = itemClick;
    }

    /**
     * 设置 RecyclerView Item 长按事件 的回调
     *
     * @param itemLongClick
     */
    public void setLongClick(OnItemLongClick<T> itemLongClick) {
        this.onItemLongClick = itemLongClick;
    }

    /****
     * RecyclerView Item 的点击事件接口
     * @param <T>
     */
    public interface OnItemClick<T> {
        void onItemClick(View v, int vId, int position, T item);
    }

    /****
     * RecyclerView Item 的长按事件接口
     * @param <T>
     */
    public interface OnItemLongClick<T> {
        void onItemLongClick(View v, int vId, int position, T item);
    }

    /***
     * 处理 GridLayoutManager兼容headerView
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
//                    return getItemViewType(position) == TYPE_HEADER || getItemViewType(position) == TYPE_FOOTER  ? gridManager.getSpanCount() : 1;
                    return getItemViewType(position) == TYPE_HEADER
                            ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    /*****
     * 处理  StaggeredGridLayoutManager（瀑布流）兼容headerView
     */
    @Override
    public void onViewAttachedToWindow(BaseViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams
                && getHeaderView() != null) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(holder.getLayoutPosition() == 0);
        }
    }

    // 数据操作
    @Override
    public boolean addAll(List<T> list) {
        boolean result = dataList.addAll(list);
        notifyDataSetChanged();
        return result;
    }

    @Override
    public boolean addAll(int position, List<T> list) {
        boolean result = dataList.addAll(position, list);
        notifyDataSetChanged();
        return result;
    }

    @Override
    public void add(T data) {
        dataList.add(data);
        notifyDataSetChanged();
    }

    @Override
    public void add(int position, T data) {
        dataList.add(position, data);
        notifyDataSetChanged();
    }

    @Override
    public void clear() {
        dataList.clear();
        notifyDataSetChanged();
    }

    @Override
    public boolean contains(T data) {
        return dataList.contains(data);
    }

    @Override
    public T getData(int index) {
        return dataList.get(index);
    }

    @Override
    public void modify(T oldData, T newData) {
        modify(dataList.indexOf(oldData), newData);
    }

    @Override
    public void modify(int index, T newData) {
        dataList.set(index, newData);
        notifyDataSetChanged();
    }

    @Override
    public boolean remove(T data) {
        boolean result = dataList.remove(data);
        notifyDataSetChanged();
        return result;
    }

    @Override
    public void remove(int index) {
        dataList.remove(index);
        notifyDataSetChanged();
    }


    /**
     * 设置不同颜色的提示与信息显示
     */
    public CharSequence getHtmlText(String hint, String content, String color) {
        if (TextUtils.isEmpty(color)) {
            color = "#666666";
        }

        String text = String.format("%s：<font color='%s'>%s</font>", hint, color, content);

        return Html.fromHtml(text);
    }
}
