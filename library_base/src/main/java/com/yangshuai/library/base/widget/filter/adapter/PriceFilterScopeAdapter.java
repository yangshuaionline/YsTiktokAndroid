package com.yangshuai.library.base.widget.filter.adapter;

import android.content.Context;
import android.graphics.Color;
import androidx.core.content.ContextCompat;

import com.yangshuai.lib.button.StateButton;
import com.yangshuai.library.base.R;
import com.yangshuai.library.base.view.recycleview.BaseRecycleAdapter;
import com.yangshuai.library.base.view.recycleview.BaseViewHolder;
import com.yangshuai.library.base.widget.filter.model.PriceFilterScopeData;

import java.util.List;

/**
 * 价格筛选列表范围
 *
 * @author hcp
 * @created 2019-07-08
 */
public class PriceFilterScopeAdapter extends BaseRecycleAdapter<PriceFilterScopeData> {

    private OnCheckboxSelectedListener checkboxSelectedListener;

    public PriceFilterScopeAdapter(Context context, List<PriceFilterScopeData> dataList) {
        super(context, dataList);
    }

    @Override
    protected int bindItemLayout() {
        return R.layout.base_filter_price_scope_item;
    }

    @Override
    protected void bindData(BaseViewHolder holder, PriceFilterScopeData data, int position) {
        StateButton button = holder.getView(R.id.base_filter_price_btn_title);
        button.setText(data.getName());
        if (data.isSelected()) {
            // 选中
            button.setNormalBackgroundColor(ContextCompat.getColor(mContext, R.color.theme));
            button.setNormalTextColor(ContextCompat.getColor(mContext, R.color.white));
        } else {
            // 未选中
            button.setNormalBackgroundColor(Color.parseColor("#F7F7F7"));
            button.setNormalTextColor(ContextCompat.getColor(mContext, R.color.text));
        }

        button.setOnClickListener(v -> {
            if (checkboxSelectedListener != null){
                checkboxSelectedListener.onCheckboxSelected(position,data.isSelected(),data);
            }
//            if (data.isMultiple()) {
//                // 多选
//                data.setSelected(!data.isSelected());
//                notifyDataSetChanged();
//                return;
//            } else {
//                // 单选
//                if (data.isSelected()) {
//                    data.setSelected(false);
//                } else {
//                    List<PriceFilterScopeData> childs = dataList;
//                    for (PriceFilterScopeData child : childs) {
//                        child.setSelected(data == child);
//                    }
//                }
//
//                notifyDataSetChanged();
//            }
        });

    }

    public interface OnCheckboxSelectedListener {
        /**
         * 点击选中事件
         *
         * @param pos        当前选中的下标
         * @param isSelected 是否选中
         * @param data       当前选中的数据
         */
        void onCheckboxSelected(int pos, boolean isSelected, PriceFilterScopeData data);

    }

    public void setCheckboxSelectedListener(OnCheckboxSelectedListener checkboxSelectedListener) {
        this.checkboxSelectedListener = checkboxSelectedListener;
    }
}
