package com.yangshuai.library.base.widget;

import com.google.android.material.appbar.AppBarLayout;

/**
 * @author hcp
 * @date 2020/1/15
 */
public abstract class AppBarStateChangeListener implements AppBarLayout.OnOffsetChangedListener {
    public enum State {
        /**
         *
         */
        EXPANDED,
        /**
         *
         */
        COLLAPSED,
        /**
         *
         */
        IDLE
    }

    private State mCurrentState = State.IDLE;

    @Override
    public final void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        if (i == 0) {
            if (mCurrentState != State.EXPANDED) {
                onStateChanged(appBarLayout, State.EXPANDED, i);
            }
            mCurrentState = State.EXPANDED;
        } else if (Math.abs(i) >= appBarLayout.getTotalScrollRange()) {
            if (mCurrentState != State.COLLAPSED) {
                onStateChanged(appBarLayout, State.COLLAPSED, i);
            }
            mCurrentState = State.COLLAPSED;
        } else {
            if (mCurrentState != State.IDLE) {
                onStateChanged(appBarLayout, State.IDLE, i);
            }
            mCurrentState = State.IDLE;
        }
    }

    /**
     * 状态改变
     */
    public abstract void onStateChanged(AppBarLayout appBarLayout, State state, int verticalOffset);
}
