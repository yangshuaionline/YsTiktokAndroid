package com.yangshuai.library.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by hcp on 2017/7/7.
 */

public class NoScollListView extends ListView {
    public NoScollListView(Context context) {
        super(context);
    }

    public NoScollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScollListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
