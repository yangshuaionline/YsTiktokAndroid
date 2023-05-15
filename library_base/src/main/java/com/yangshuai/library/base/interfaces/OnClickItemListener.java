package com.yangshuai.library.base.interfaces;

import android.view.View;

public interface OnClickItemListener<T> {
    default void onMoreClick(View view, T t, long id){};

    void onItemListClick(View view, T t);

    boolean onItemListLongClick(View view, T t);
}
