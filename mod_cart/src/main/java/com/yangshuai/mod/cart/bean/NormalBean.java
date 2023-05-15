package com.yangshuai.mod.cart.bean;

import com.ocnyang.cartlayout.bean.CartItemBean;

/**
 * @Author yangshuai
 * @Date 2023-04-20 周四 9:52
 * @Description TODO
 */
public class NormalBean extends CartItemBean {
    int markdownNumber;

    public int getMarkdownNumber() {
        return markdownNumber;
    }

    public void setMarkdownNumber(int markdownNumber) {
        this.markdownNumber = markdownNumber;
    }
}