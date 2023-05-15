package com.yangshuai.library.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.yangshuai.library.base.BaseBindingAdapter;
import com.yangshuai.library.base.R;
import com.yangshuai.library.base.interfaces.ImageClickListener;
import com.yangshuai.library.base.utils.ScreenUtils;
import com.yangshuai.library.base.view.recycleview.BaseRecycleAdapter;
import com.yangshuai.library.base.view.recycleview.BaseViewHolder;

import java.util.List;

/**
 * 网格选择编辑器
 */
public class ImageGridAdapter extends BaseRecycleAdapter<String> {

    private ImageClickListener imageClickListener;

    // 默认格数（感觉显示的格式显示隐藏添加图片的btn）
    private int maxNumber = 9;

    // 根据行数计算itme宽高
    private int itemRows = 4;

    // item宽高
    private int itemWidth, itemHeight;


    public ImageGridAdapter(Context context, List<String> data) {
        super(context, data);
        calcItemReact();
        initAddView();
    }


    @Override
    protected int bindItemLayout() {
        return R.layout.base_cell_image_edit;
    }

    @Override
    protected void bindData(BaseViewHolder holder, String data, int position) {
        // 图片
        ImageView image = holder.getView(R.id.image);
        BaseBindingAdapter.loadImage(image, data);


        // 删除按钮
        ImageView delete = holder.getView(R.id.delete);

        // 点击事件
        if (imageClickListener != null) {
            // 图片点击
            image.setOnClickListener(v -> imageClickListener.onImageClick(data));
            // 删除按钮
            delete.setOnClickListener(v -> imageClickListener.onDeleteClick(data));
            // 添加图片按钮
            getFooterView().setOnClickListener(v -> imageClickListener.onAddClick());
        }

        // 显示添加图片
        if (maxNumber > dataList.size()) {
            getFooterView().setVisibility(View.VISIBLE);
        } else {
            getFooterView().setVisibility(View.GONE);
        }

        // 设置item的宽高
        View itemView = holder.getView(R.id.itemView);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(itemWidth, itemHeight);
        itemView.setLayoutParams(params);
    }


    /**
     * 添加图片的按钮
     */
    private void initAddView() {
        // 添加图片btn
        View addView = LayoutInflater.from(mContext).inflate(R.layout.base_add_image_view, null);

        // 添加图片的大小
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(itemWidth, itemHeight);
        addView.setLayoutParams(layoutParams);

        // 把添加图片btn添加到尾部
        setFooterView(addView);

        // 添加图片默认点击事件
        addView.setOnClickListener(v -> imageClickListener.onAddClick());
        // 添加尾部View默认要跟着添加个头部View，否则有bug；
        setHeaderView(new View(mContext));
    }


    /**
     * 计算menu item宽高
     */
    private void calcItemReact() {
        itemWidth = (ScreenUtils.getScreenWidth()
                - (int) mContext.getResources().getDimension(R.dimen.base_margin)) / itemRows;
        itemHeight = itemWidth;
    }

    public void setImageClickListener(ImageClickListener imageClickListener) {
        this.imageClickListener = imageClickListener;
    }

    public void setMaxNumber(int maxNumber) {
        this.maxNumber = maxNumber;
    }

    public void setItemRows(int itemRows) {
        this.itemRows = itemRows;
        // 重新计算宽高
        calcItemReact();
        // 再初始化添加图标
        initAddView();
    }
}
