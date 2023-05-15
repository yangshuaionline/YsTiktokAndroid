package com.yangshuai.library.base.utils;

import androidx.recyclerview.widget.RecyclerView;

import com.yangshuai.library.base.widget.SimpleGridView;

/**
 * @Author hcp
 * @Created 5/29/19
 * @Editor hcp
 * @Edited 5/29/19
 * @Type
 * @Layout
 * @Api
 */
public class RecyclerViewUtils {

    public static RecyclerView.ItemDecoration gridSpace(int spanCount, int spacing) {
        return new SimpleGridView.GridSpacingItemDecoration(spanCount, spacing);
    }

}
