package com.yangshuai.mod.cart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.yangshuai.mod.cart.bean.GoodsBean;
import com.yangshuai.mod.cart.bean.NormalBean;
import com.yangshuai.mod.cart.bean.ShopBean;
import com.yangshuai.mod.cart.viewholder.ChildViewHolder;
import com.yangshuai.mod.cart.viewholder.GroupViewHolder;
import com.yangshuai.mod.cart.viewholder.NormalViewHolder;
import com.ocnyang.cartlayout.CartAdapter;
import com.ocnyang.cartlayout.viewholder.CartViewHolder;

import java.util.List;

/**
 * @Author yangshuai
 * @Date 2023-04-20 周四 9:55
 * @Description TODO
 */
public class CartMainAdapter extends CartAdapter<CartViewHolder> {

    public CartMainAdapter(Context context, List datas) {
        super(context, datas);
    }

    @Override
    protected CartViewHolder getNormalViewHolder(View itemView) {
        return new NormalViewHolder(itemView, -1);
    }

    @Override
    protected CartViewHolder getGroupViewHolder(View itemView) {
        return (CartViewHolder) new GroupViewHolder(itemView, R.id.checkbox);
    }

    @Override
    protected CartViewHolder getChildViewHolder(View itemView) {
        return (CartViewHolder) (new ChildViewHolder(itemView, R.id.checkbox) {
            @Override
            public void onNeedCalculate() {
                if (onCheckChangeListener != null) {
                    onCheckChangeListener.onCalculateChanged(null);
                }
            }
        });
    }

    @Override
    protected int getChildItemLayout() {
        return R.layout.activity_main_item_child;
    }

    @Override
    protected int getGroupItemLayout() {
        return R.layout.activity_main_item_group;
    }

    @Override
    protected int getNormalItemLayout() {
        return R.layout.activity_main_item_normal;
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        super.onBindViewHolder(holder, position);
        if (holder instanceof ChildViewHolder) {
            ChildViewHolder childViewHolder = (ChildViewHolder) holder;
            childViewHolder.textView.setText(((GoodsBean) mData.get(position)).getGoods_name());
            childViewHolder.textViewPrice.setText(
                    mContext.getString(R.string.rmb_X, ((GoodsBean) mData.get(position)).getGoods_price()));
            childViewHolder.textViewNum.setText(String.valueOf(((GoodsBean) mData.get(position)).getGoods_amount()));
        } else if (holder instanceof GroupViewHolder) {
            GroupViewHolder groupViewHolder = (GroupViewHolder) holder;
            groupViewHolder.textView.setText(((ShopBean) mData.get(position)).getShop_name());
        } else if (holder instanceof NormalViewHolder) {
            NormalViewHolder normalViewHolder = (NormalViewHolder) holder;
            normalViewHolder.imgViewClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mData.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, mData.size());
                }
            });
            normalViewHolder.textView.setText(mContext.getString(R.string.normal_tip_X,
                    ((NormalBean) mData.get(position)).getMarkdownNumber()));
        }
    }
}