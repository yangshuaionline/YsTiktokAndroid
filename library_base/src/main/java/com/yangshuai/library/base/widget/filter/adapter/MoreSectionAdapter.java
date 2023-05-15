package com.yangshuai.library.base.widget.filter.adapter;

import android.graphics.Color;
import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yangshuai.lib.button.StateButton;
import com.yangshuai.library.base.R;
import com.yangshuai.library.base.widget.filter.model.MoreFilterChildData;
import com.yangshuai.library.base.widget.filter.model.MoreFilterSectionData;

import java.util.List;

/**
 * 更多筛选里边的分组适配器
 * @author hcp
 * @created 2019-07-03
 */
public class MoreSectionAdapter extends BaseSectionQuickAdapter<MoreFilterSectionData,BaseViewHolder> {

    /**
     * 初始化
     * @param layoutResId 每个子项布局ID
     * @param sectionHeadResId 每个组节点布局ID
     */
    public MoreSectionAdapter(int layoutResId, int sectionHeadResId, List data) {
        super(layoutResId, sectionHeadResId, data);
    }

    /**
     * 设置头部分组的数据
     */
    @Override
    protected void convertHead(BaseViewHolder helper, MoreFilterSectionData item) {
        if (helper.getAdapterPosition() == 0){
            helper.setGone(R.id.view_top_line,true);
        } else {
            helper.setGone(R.id.view_top_line,false);
        }
        helper.setText(R.id.base_filter_more_text_title,item.header); // 设置每个组节点的标题内容
    }

    /**
     * 设置子项数据
     * @param helper
     * @param item
     */
    @Override
    protected void convert(BaseViewHolder helper, MoreFilterSectionData item) {
        MoreFilterChildData childData = (MoreFilterChildData) item.t;
        StateButton button = helper.getView(R.id.base_filter_more_btn_title);
        if (childData != null){
            // 设置标签选中点击事件
            helper.addOnClickListener(R.id.base_filter_more_btn_title);
            button.setText(childData.getName());
            if (childData.isSelected()) {
                // 选中
                button.setNormalBackgroundColor(ContextCompat.getColor(mContext, R.color.theme));
                button.setNormalTextColor(ContextCompat.getColor(mContext, R.color.white));
            } else {
                // 未选中
                button.setNormalBackgroundColor(Color.parseColor("#F7F7F7"));
                button.setNormalTextColor(ContextCompat.getColor(mContext, R.color.text));
            }
        }

    }
}
