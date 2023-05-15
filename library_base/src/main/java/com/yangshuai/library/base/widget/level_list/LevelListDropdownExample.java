package com.yangshuai.library.base.widget.level_list;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * 分级下拉列表使用例子
 *
 * @Author hcp
 * @Created 3/22/19
 * @Editor hcp
 * @Edited 3/22/19
 * @Type
 * @Layout
 * @Api
 */
public class LevelListDropdownExample {

    public static LevelListDropdown show(Activity activity, View anchorView) {
        LevelListDropdown levelListDropdown = new LevelListDropdown(activity, anchorView);
        levelListDropdown.setItems(generateLevelOneList());
        levelListDropdown.setOnItemClickListener(levelItem -> {
            Toast.makeText(activity, levelItem.getName(), Toast.LENGTH_SHORT).show();
            levelListDropdown.dismiss();
        });
        levelListDropdown.show();
        return levelListDropdown;
    }

    public static List<Item> generateLevelOneList() {
        List<Item> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Item item = new Item();
            item.setName("一级节点" + i);
            item.setId(i + "");
            item.setLevel(Level.LEVEL_ONE);
            item.setChildren(generateLevelTwoList(i));
            list.add(item);
        }
        return list;
    }


    public static List<Item> generateLevelTwoList(int parentId) {
        List<Item> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Item item = new Item();
            int id = parentId * 10 + i;
            item.setName("二级节点" + id);
            item.setId(id + "");
            item.setParentId(parentId + "");
            item.setLevel(Level.LEVEL_TWO);
            list.add(item);
        }
        return list;
    }

}
