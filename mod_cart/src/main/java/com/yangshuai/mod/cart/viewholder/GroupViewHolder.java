package com.yangshuai.mod.cart.viewholder;

import android.view.View;
import android.widget.TextView;

import com.yangshuai.mod.cart.R;
import com.ocnyang.cartlayout.viewholder.CartViewHolder;

/**
 * @Author yangshuai
 * @Date 2023-04-20 周四 9:48
 * @Description TODO
 */
public class GroupViewHolder extends CartViewHolder {
    public TextView textView;

    public GroupViewHolder(View itemView, int chekbox_id) {
        super(itemView, chekbox_id);
        textView = itemView.findViewById(R.id.tv);
    }
}