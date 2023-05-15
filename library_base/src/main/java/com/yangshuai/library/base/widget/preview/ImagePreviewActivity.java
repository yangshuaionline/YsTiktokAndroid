package com.yangshuai.library.base.widget.preview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.yangshuai.library.base.R;

import java.util.ArrayList;

/**
 * 图片预览页面
 * 需传参:
 * position int 显示第几张图片
 * images ArrayList<String> 图片地址集合
 *
 * @Author hcp
 * @Created 4/4/19
 * @Editor hcp
 * @Edited 19/6/4
 * @Type
 * @Layout
 * @Api
 */
public class ImagePreviewActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ArrayList<String> imageUrlsList;
    private TextView tvIndicator;
    private int position;

    private static final String KEY_POSITION = "position";
    private static final String KEY_IMAGES = "images";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideBottomUIMenu();
        setContentView(R.layout.base_preview_act);
        initData();
        initViewpager();
        initIndicator();

    }

    private void initData() {
        position = getIntent().getIntExtra(KEY_POSITION, 0);
        imageUrlsList = getIntent().getStringArrayListExtra(KEY_IMAGES);
    }

    private void initIndicator() {
        tvIndicator = findViewById(R.id.tv_indicator);

        // 只有一张图片的话就不显示指示器了
        if (imageUrlsList.size() > 1) {
            tvIndicator.setVisibility(View.VISIBLE);
            tvIndicator.setText(getIndicatorNumber(position));
        } else {
            tvIndicator.setVisibility(View.GONE);
        }

    }

    private void initViewpager() {
        viewPager = findViewById(R.id.viewpager);
        ImagePreviewPagerAdapter imageViewpagerAdapter = new ImagePreviewPagerAdapter(getSupportFragmentManager(), new OnFragmentInteractionListener() {
            @Override
            public void onClick() {
                exitPage();
            }
        });
        imageViewpagerAdapter.setmItems(imageUrlsList);
        viewPager.setAdapter(imageViewpagerAdapter);
        viewPager.setCurrentItem(position);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                tvIndicator.setText(getIndicatorNumber(i));
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void exitPage() {
        finish();
    }

    @Override
    public void onBackPressed() {
        exitPage();
    }

    public static void openPreview(Context activity, int position, ArrayList<String> imageUrlsList) {
        Intent intent = new Intent(activity, ImagePreviewActivity.class);
        intent.putStringArrayListExtra(KEY_IMAGES, imageUrlsList);
        intent.putExtra(KEY_POSITION, position);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    private String getIndicatorNumber(int position) {
        int index = position + 1;
        return index + "/" + imageUrlsList.size();
    }

    /**
     * 透明状态栏和导航栏，并且全屏
     */
    protected void hideBottomUIMenu() {
        Window window = getWindow();
        View decorView = window.getDecorView();

        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(uiOptions);
        window.setStatusBarColor(Color.TRANSPARENT);
        window.setNavigationBarColor(Color.TRANSPARENT);

    }
}
