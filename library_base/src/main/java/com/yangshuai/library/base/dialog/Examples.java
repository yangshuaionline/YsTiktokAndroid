package com.yangshuai.library.base.dialog;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * 对话框使用例子
 *
 * @Author hcp
 * @Created 3/20/19
 * @Editor hcp
 * @Edited 3/20/19
 * @Type
 * @Layout
 * @Api
 */
public class Examples {

    public static void showAlertDialog(Context context) {
        AlertDialog alertDialog = new AlertDialog(context);
        alertDialog.setTitle("提示");
        alertDialog.setMessage("测试消息");
        alertDialog.setLeftButton("取消", v -> alertDialog.dismiss());
        alertDialog.setRightButton("确定", v -> {
            Toast.makeText(context, "确定", Toast.LENGTH_SHORT).show();
            alertDialog.dismiss();
        });
        alertDialog.show();
    }

    public static void showListBottomSheetDialog(Context context) {
        ListBottomSheetDialog listBottomSheetDialog = new ListBottomSheetDialog(context);
        List<ListBottomSheetDialog.Item> items = new ArrayList<>();
        List<ListBottomSheetDialog.Item> selecteditems = new ArrayList<>();
        items.add(new ListBottomSheetDialog.Item(0, "拍照"));
        items.add(new ListBottomSheetDialog.Item(1, "从选择相册"));
        selecteditems.add(items.get(0));
        listBottomSheetDialog.bindItem(items, selecteditems, item -> {
//            Toast.makeText(context, item.getName(), Toast.LENGTH_SHORT).show();
//            listBottomSheetDialog.dismiss();

            if (!listBottomSheetDialog.isSelected(item)) {
                selecteditems.clear();
                selecteditems.add(item);
                listBottomSheetDialog.notifyItemChanged();
            }

        });
        listBottomSheetDialog.show();
    }
}
