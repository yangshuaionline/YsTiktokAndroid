package com.yangshuai.library.base.view.recycleview;

import androidx.recyclerview.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * RecycleView基础ViewHolder
 * Created by lsy on 2017/4/25.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {

    // SparseArray 比 HashMap 更省内存，在某些条件下性能更好，只能存储 key 为 int 类型的数据，
    // 用来存放 View 以减少 findViewById 的次数
    private SparseArray<View> viewSparseArray = new SparseArray<>();

    private View mConvertView;


    private Object binding;

    public BaseViewHolder(View itemView) {
        super(itemView);
        mConvertView = itemView;
        mConvertView.setTag(this);
    }

    public <T> T getBinding() {
        return (T)binding;
    }

    public void setBinding(Object binding) {
        this.binding = binding;
    }

    /**
     * 根据 ID 来获取 View
     *
     * @param viewId viewID
     * @param <T>    泛型
     * @return 将结果强转为 View 或 View 的子类型
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId) {
        // 先从缓存中找，找打的话则直接返回
        // 如果找不到则 findViewById ，再把结果存入缓存中
        View view = viewSparseArray.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            viewSparseArray.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 获取item布局
     *
     * @return
     */
    public View getItemView() {
        return mConvertView;
    }


    /**
     * 根据ID设置TextView文本
     * @param viewId
     * @param text
     * @return
     */
    public BaseViewHolder setText(int viewId, CharSequence text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    /**
     * 根据ID设置View显示状态
     * @param viewId
     * @param visibility
     * @return
     */
    public BaseViewHolder setViewVisibility(int viewId, int visibility) {
        getView(viewId).setVisibility(visibility);
        return this;
    }

    /**
     * 根据ID设置ImageView本地图片资源
     * @param viewId
     * @param resourceId
     * @return
     */
    public BaseViewHolder setImageResource(int viewId, int resourceId) {
        ImageView imageView = getView(viewId);
        imageView.setImageResource(resourceId);
        return this;
    }

    /**
     * 根据ID设置ImageView的网络图片(通过第三方图片框架)
     * @return
     */
    public BaseViewHolder setImgeURL(int viewId, String url){
        ImageView imageView = getView(viewId);
        return this;
    }

    /**
     * 根据ID设置item任意View的点击事件
     */
    public BaseViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    /**
     * 设置Item点击事件
     */
    public BaseViewHolder setOnItemViewClickListener(View.OnClickListener listener){
        mConvertView.setOnClickListener(listener);
        return this;
    }

    /**
     * 设置Item长按事件
     */
    public BaseViewHolder setOnItemViewLongClickListener(View.OnLongClickListener listener){
        mConvertView.setOnLongClickListener(listener);
        return this;
    }

}
