package com.yangshuai.library.base.view;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;


/**
 * ListView和GridView的基础适配器
 *
 * @author hcp
 * @created 2019/3/26
 */
public abstract class BaseListAdapter<Model> extends BaseAdapter {
    protected Context context;
    protected List<Model> data;
    protected LayoutInflater inflater;

    public BaseListAdapter(Context context, List<Model> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @SuppressWarnings("unchecked")
    public static <T extends View> T getHolder(View v, int resId) {
        SparseArray<View> holder = (SparseArray<View>) v.getTag();
        if (null == holder) {
            holder = new SparseArray<View>();
            v.setTag(holder);
        }
        View childView = holder.get(resId);
        if (null == childView) {
            childView = v.findViewById(resId);
            holder.put(resId, childView);
        }
        return (T) childView;
    }

    @SuppressWarnings("unchecked")
    public static <T extends View> T get(View v, int resId) {

        return (T) v.findViewById(resId);
    }


    public List<Model> getData() {
        return data;
    }

    protected abstract int getLayoutId();

    protected abstract void bindHolder(int position, View convertView, ViewGroup parent, Model item);

    protected boolean supportClick() {
        return true;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {

        if (convertView == null) {
            convertView = inflater.inflate(getLayoutId(), null);

        }

        if (supportClick()) {
            convertView.setOnClickListener(view -> {
                if (onListItemClickListener != null) {
                    onListItemClickListener.onListItemClick(position, data.get(position));
                }
                if (onListItemClickListener2 != null) {
                    onListItemClickListener2.onListItemClick(view, position, data.get(position));
                }

            });
        }

        bindHolder(position, convertView, viewGroup, data.get(position));

        return convertView;
    }


    protected OnListItemClickListener onListItemClickListener;

    public void setOnListItemClickListener(OnListItemClickListener<Model> onListItemClickListener) {
        this.onListItemClickListener = onListItemClickListener;
    }

    public interface OnListItemClickListener<Model> {
        public void onListItemClick(int position, Model item);
    }

    protected OnListItemClickListener2 onListItemClickListener2;

    public void setOnListItemClickListener2(OnListItemClickListener2<Model> onListItemClickListener2) {
        this.onListItemClickListener2 = onListItemClickListener2;
    }

    public interface OnListItemClickListener2<Model> {
        public void onListItemClick(View convertView, int position, Model item);
    }

}
