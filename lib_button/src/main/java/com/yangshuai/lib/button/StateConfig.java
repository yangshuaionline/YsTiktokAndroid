package com.yangshuai.lib.button;

import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;

/**
 * @Author yangshuai
 * @Date 2023-05-08 周一 15:51
 * @Description
 */

public final class StateConfig {

    //animation duration
    private int mDuration;

    //radius
    private float mRadius;

    //text color
    private int mNormalTextColor;
    private int mPressedTextColor;
    private int mUnableTextColor;

    //stroke
    private float mStrokeDashWidth;
    private float mStrokeDashGap;
    private int mNormalStrokeWidth;
    private int mPressedStrokeWidth;
    private int mUnableStrokeWidth;
    private int mNormalStrokeColor;
    private int mPressedStrokeColor;
    private int mUnableStrokeColor;

    //background color
    private int mNormalBackgroundColor;
    private int mPressedBackgroundColor;
    private int mUnableBackgroundColor;

    private StateConfig(StateConfig.Builder builder) {
        this.mNormalTextColor = builder.mNormalTextColor;
        this.mPressedTextColor = builder.mPressedTextColor;
        this.mUnableTextColor = builder.mUnableTextColor;
        this.mDuration = builder.mDuration;
        this.mRadius = builder.mRadius;
        this.mStrokeDashWidth = builder.mStrokeDashWidth;
        this.mStrokeDashGap = builder.mStrokeDashGap;
        this.mNormalStrokeWidth = builder.mNormalStrokeWidth;
        this.mPressedStrokeWidth = builder.mPressedStrokeWidth;
        this.mUnableStrokeWidth = builder.mUnableStrokeWidth;
        this.mNormalStrokeColor = builder.mNormalStrokeColor;
        this.mPressedStrokeColor = builder.mPressedStrokeColor;
        this.mUnableStrokeColor = builder.mUnableStrokeColor;
        this.mNormalBackgroundColor = builder.mNormalBackgroundColor;
        this.mPressedBackgroundColor = builder.mPressedBackgroundColor;
        this.mUnableBackgroundColor = builder.mUnableBackgroundColor;
    }

    public int getNormalTextColor() {
        return mNormalTextColor;
    }

    public int getPressedTextColor() {
        return mPressedTextColor;
    }

    public int getUnableTextColor() {
        return mUnableTextColor;
    }

    public int getDuration() {
        return mDuration;
    }

    public float getRadius() {
        return mRadius;
    }

    public float getStrokeDashWidth() {
        return mStrokeDashWidth;
    }

    public float getStrokeDashGap() {
        return mStrokeDashGap;
    }

    public int getNormalStrokeWidth() {
        return mNormalStrokeWidth;
    }

    public int getPressedStrokeWidth() {
        return mPressedStrokeWidth;
    }

    public int getUnableStrokeWidth() {
        return mUnableStrokeWidth;
    }

    public int getNormalStrokeColor() {
        return mNormalStrokeColor;
    }

    public int getPressedStrokeColor() {
        return mPressedStrokeColor;
    }

    public int getUnableStrokeColor() {
        return mUnableStrokeColor;
    }

    public int getNormalBackgroundColor() {
        return mNormalBackgroundColor;
    }

    public int getPressedBackgroundColor() {
        return mPressedBackgroundColor;
    }

    public int getUnableBackgroundColor() {
        return mUnableBackgroundColor;
    }

    public static StateConfig.Builder newBuilder() {
        return new StateConfig.Builder();
    }

    public static class Builder {

        //animation duration
        private int mDuration;

        //radius
        private float mRadius;

        //text color
        private int mNormalTextColor;
        private int mPressedTextColor;
        private int mUnableTextColor;

        //stroke
        private float mStrokeDashWidth;
        private float mStrokeDashGap;
        private int mNormalStrokeWidth;
        private int mPressedStrokeWidth;
        private int mUnableStrokeWidth;
        private int mNormalStrokeColor;
        private int mPressedStrokeColor;
        private int mUnableStrokeColor;

        //background color
        private int mNormalBackgroundColor;
        private int mPressedBackgroundColor;
        private int mUnableBackgroundColor;


        public StateConfig.Builder setDuration(@IntRange(from = 0) int duration) {
            this.mDuration = duration;
            return this;
        }

        public StateConfig.Builder setRadius(@FloatRange(from = 0) float radius) {
            this.mRadius = radius;
            return this;
        }

        public StateConfig.Builder setNormalTextColor(@ColorInt int normalTextColor) {
            this.mNormalTextColor = normalTextColor;
            return this;
        }

        public StateConfig.Builder setPressedTextColor(@ColorInt int pressedTextColor) {
            this.mPressedTextColor = pressedTextColor;
            return this;
        }

        public StateConfig.Builder setUnableTextColor(@ColorInt int unableTextColor) {
            this.mUnableTextColor = unableTextColor;
            return this;
        }

        public StateConfig.Builder setStrokeDashWidth(@FloatRange(from = 0) float strokeDashWidth) {
            this.mStrokeDashWidth = strokeDashWidth;
            return this;
        }

        public StateConfig.Builder setStrokeDashGap(@FloatRange(from = 0) float strokeDashGap) {
            this.mStrokeDashGap = strokeDashGap;
            return this;
        }

        public StateConfig.Builder setNormalStrokeWidth(@IntRange(from = 0) int normalStrokeWidth) {
            this.mNormalStrokeWidth = normalStrokeWidth;
            return this;
        }

        public StateConfig.Builder setPressedStrokeWidth(@IntRange(from = 0) int pressedStrokeWidth) {
            this.mPressedStrokeWidth = pressedStrokeWidth;
            return this;
        }

        public StateConfig.Builder setUnableStrokeWidth(@IntRange(from = 0) int unableStrokeWidth) {
            this.mUnableStrokeWidth = unableStrokeWidth;
            return this;
        }

        public StateConfig.Builder setNormalStrokeColor(@ColorInt int normalStrokeColor) {
            this.mNormalStrokeColor = normalStrokeColor;
            return this;
        }

        public StateConfig.Builder setPressedStrokeColor(@ColorInt int pressedStrokeColor) {
            this.mPressedStrokeColor = pressedStrokeColor;
            return this;
        }

        public StateConfig.Builder setUnableStrokeColor(@ColorInt int unableStrokeColor) {
            this.mUnableStrokeColor = mUnableStrokeColor;
            return this;
        }

        public StateConfig.Builder setNormalBackgroundColor(@ColorInt int normalBackgroundColor) {
            this.mNormalBackgroundColor = normalBackgroundColor;
            return this;
        }

        public StateConfig.Builder setPressedBackgroundColor(@ColorInt int pressedBackgroundColor) {
            this.mPressedBackgroundColor = pressedBackgroundColor;
            return this;
        }

        public StateConfig.Builder setUnableBackgroundColor(@ColorInt int unableBackgroundColor) {
            this.mUnableBackgroundColor = unableBackgroundColor;
            return this;
        }

        public StateConfig.Builder setStateTextColor(@ColorInt int normal, @ColorInt int pressed, @ColorInt int unable){
            this.mNormalTextColor = normal;
            this.mPressedTextColor = pressed;
            this.mUnableTextColor = unable;
            return this;
        }

        public StateConfig.Builder setStateBackgroundColor(@ColorInt int normal, @ColorInt int pressed, @ColorInt int unable){
            mNormalBackgroundColor = normal;
            mPressedBackgroundColor = pressed;
            mUnableBackgroundColor = unable;
            return this;
        }

        public StateConfig.Builder setStateStrokeColor(@ColorInt int normal, @ColorInt int pressed, @ColorInt int unable){
            mNormalStrokeColor = normal;
            mPressedStrokeColor = pressed;
            mUnableStrokeColor = unable;
            return this;
        }

        public Builder() {
        }

        public StateConfig build() {
            return new StateConfig(this);
        }
    }
}
