package com.yangshuai.library.base.widget.level_list;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * 绘制分级列表的分割线
 *
 * @Author zrh
 * @Created 3/22/19
 * @Editor zrh
 * @Edited 3/22/19
 * @Type
 * @Layout
 * @Api
 */
public abstract class LineDecorator extends RecyclerView.ItemDecoration {


    private Paint linePaint;

    public LineDecorator() {
        linePaint = new Paint();
        linePaint.setColor(Color.parseColor("#BEBEBE"));
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        if (needDrawLine(position)) {
            outRect.top = 1;
        }
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);

        int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {
            View childView = parent.getChildAt(i);
            int childPosition = parent.getChildAdapterPosition(childView);

            if (needDrawLine(childPosition)) {
                c.drawRect(childView.getLeft() + childView.getPaddingLeft(), childView.getTop() - 1, childView.getRight(), childView.getTop(), linePaint);
            }
        }
    }

    /**
     * 判断哪些item项需要绘制分割线
     *
     * @param position
     * @return
     */
    public abstract boolean needDrawLine(int position);
}
