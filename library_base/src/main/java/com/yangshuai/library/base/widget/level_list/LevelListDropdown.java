package com.yangshuai.library.base.widget.level_list;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;


import com.yangshuai.library.base.R;

import java.util.List;

/**
 * 下拉分级列表控件
 * 拓展PopupWindow实现下拉动画，结合RecyclerView使用{@link com.yangshuai.library.base.widget.level_list.LevelListAdapter}实现分级列表
 *
 * @Author hcp
 * @Created 3/22/19
 * @Editor hcp
 * @Edited 3/22/19
 * @Type
 * @Layout
 * @Api
 */
public class LevelListDropdown extends PopupWindow {

    private View mWindowRootView;
    // 分级列表
    private RecyclerView mContentRv;
    // 显示位置参照的View
    private View mAnchorView;
    // 下拉动画执行的时间
    private static final int ANIM_DURATION = 300;
    private int screenHeight;
    private LevelListAdapter levelListAdapter;
    private int offsetY = -1;

    public LevelListDropdown(Activity context, View anchorView) {
        super(View.inflate(context, R.layout.base_level_list_dropdown, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        mWindowRootView = ((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0);
        mAnchorView = anchorView;
        screenHeight = getScreenHeight(context);
        initView();
    }

    private void initView() {

        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setAnimationStyle(android.R.style.Animation);
        mContentRv = getContentView().findViewById(R.id.dropdown_content);
        mContentRv.addItemDecoration(new LineDecorator() {

            @Override
            public boolean needDrawLine(int position) {
                return position != 0 && levelListAdapter.getItemViewType(position) == Level.LEVEL_ONE;
            }
        });
        mContentRv.setLayoutManager(new LinearLayoutManager(getContentView().getContext()));
        levelListAdapter = new LevelListAdapter();
        mContentRv.setAdapter(levelListAdapter);
        getContentView().setOnClickListener(v -> dismiss());
    }

    /**
     * 添加数据
     *
     * @param items
     */
    public void setItems(final List<Item> items) {
        if (items != null && items.size() > 0) {
            levelListAdapter.setGroupItems(items);
        }
    }

    /**
     * 设置选中的选项
     *
     * @param item
     */
    public void setSelectedItem(Item item) {
        levelListAdapter.setSelectedItem(item);
    }

    /**
     * 设置上次点击选中的选项
     *
     * @param parent
     */
    public void setSavaSelectedItem(int index,Item parent,Item child) {
        levelListAdapter.setSavaSelected(index,parent,child);
    }


    /**
     * 绑定点击事件（点击时返回该选项的数据）
     *
     * @param listener
     */
    public void setOnItemClickListener(LevelListAdapter.OnItemClickListener listener) {
        levelListAdapter.setOnItemClickListener(listener);

    }

    /**
     * 调用此方法显示下拉列表
     */
    public void show() {
        try {
            // 列表无数据时不显示
            if (levelListAdapter.getItemCount() == 0) {
                return;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                if (offsetY == -1) {
                    int[] location = new int[2];
                    mAnchorView.getLocationInWindow(location);
                    offsetY = location[1] + mAnchorView.getHeight();
                }

                int height = mWindowRootView.getHeight() - offsetY;
//                Log.d("popupwindow", "screen height:" + screenHeight + " rootview height:" + mWindowRootView.getHeight() + " popupwindow height:" + height);

                setHeight(height);

                showAtLocation(mAnchorView, Gravity.NO_GRAVITY, 0, offsetY);
            } else {
                showAsDropDown(mAnchorView);
            }

            ViewCompat.animate(mContentRv).translationY(-mWindowRootView.getHeight()).setDuration(0).start();
            ViewCompat.animate(mContentRv).translationY(0).setDuration(ANIM_DURATION).start();
            ViewCompat.animate(getContentView()).alpha(0).setDuration(0).start();
            ViewCompat.animate(getContentView()).alpha(1).setDuration(ANIM_DURATION).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 调用此方法关闭下拉列表
     */
    @Override
    public void dismiss() {
        ViewCompat.animate(mContentRv).translationY(-mWindowRootView.getHeight()).setDuration(ANIM_DURATION).start();
        ViewCompat.animate(getContentView()).alpha(1).setDuration(0).start();
        ViewCompat.animate(getContentView()).alpha(0).setDuration(ANIM_DURATION).start();

        mContentRv.postDelayed(LevelListDropdown.super::dismiss, ANIM_DURATION);
    }

    private int getScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }
}
