package com.yangshuai.library.base.interfaces;

/**
 * @Author hcp
 * @Created 3/15/19
 * @Editor hcp
 * @Edited 3/15/19
 * @Type
 * @Layout
 * @Api
 */
public interface ImageClickListener {

    void onImageClick(String url);

    default void onDeleteClick(String url) {
    }

    default void onAddClick() {

    }


}
