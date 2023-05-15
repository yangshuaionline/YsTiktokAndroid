package com.yangshuai.library.base.widget.filter;

import android.text.TextUtils;

import com.yangshuai.library.base.widget.filter.model.MoreFilterChildData;
import com.yangshuai.library.base.widget.filter.model.MoreFilterGroupData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 更多筛选的返回监听
 *
 * @author hcp
 * @created 2019/4/24
 */
public class MoreFilterResultListener implements MoreFilterContainer.OnResultListener<Map<String, Object>> {

    private int type; // 用于区分当前筛选的类型  1二手房 2租房
    private Callback callback;

    /**
     * @param type     1二手房 2租房 后续可能会根据不同的类型需要的筛选参数也不同
     * @param callback
     */
    public MoreFilterResultListener(int type, Callback callback) {
        this.type = type;
        this.callback = callback;
    }

    @Override
    public Map<String, Object> onConvert(List<MoreFilterGroupData> data) {
        Map<String, Object> result = new HashMap<>();
        // 在根据type此解析数据二手房/租房的筛选数据
        switch (type) {
            case 1:
                // 二手房参数
                result = usedHouseConvert(data);
                break;
            case 2:
                // 租房参数
                result = usedHouseConvert(data);
                break;
        }
        return result;
    }

    private Map<String, Object> usedHouseConvert(List<MoreFilterGroupData> data) {
        Map<String, Object> result = new HashMap<>();
        int groupSize = data.size();
        for (int i = 0; i < groupSize; i++) {
            // 遍历取出选中的值
            String title = data.get(i).getGroupTitle();
            int childSize = data.get(i).getChilds().size();

            switch (title) {
                case "房屋用途":
                    // 单选
                    for (int j = 0; j < childSize; j++) {
                        MoreFilterChildData childData = data.get(i).getChilds().get(j);
                        if (childData.isSelected()) {
                            String key = childData.getName();
                            String value = childData.getValue();
                            if (!TextUtils.isEmpty(value)) {
                                result.put("queryType", Integer.parseInt(value));
                            } else {
                                result.put("queryType", key);
                            }
                        }
                    }
                    break;

                case "房源标记":
                    List<String> marks = new ArrayList<>();
                    for (int j = 0; j < childSize; j++) {
                        MoreFilterChildData childData = data.get(i).getChilds().get(j);
                        if (childData.isSelected()) {
                            // 选中
                            marks.add(childData.getValue());
                        }
                    }

                    if (marks.isEmpty()) {
                        result.remove("markCategory");
                    } else {
                        result.put("markCategory", join(marks,","));
                    }
                    break;

                case "房型":
                    List<String> rooms = new ArrayList<>();
                    for (int j = 0; j < childSize; j++) {
                        MoreFilterChildData childData = data.get(i).getChilds().get(j);
                        if (childData.isSelected()) {
                            // 选中
                            rooms.add(childData.getValue());
                        }
                    }

                    if (rooms.isEmpty()) {
                        result.remove("bedroom");
                    } else {
                        result.put("bedroom", join(rooms, ","));
                    }
                    break;

                case "装修":
                    List<String> decorationTypes = new ArrayList<>();
                    for (int j = 0; j < childSize; j++) {
                        MoreFilterChildData childData = data.get(i).getChilds().get(j);
                        if (childData.isSelected()) {
                            // 选中
                            decorationTypes.add(childData.getValue());
                        }
                    }

                    if (decorationTypes.isEmpty()) {
                        result.remove("decorationSituation");
                    } else {
                        result.put("decorationSituation", join(decorationTypes, ","));
                    }
                    break;

                case "楼层":
                    List<String> floors = new ArrayList<>();
                    for (int j = 0; j < childSize; j++) {
                        MoreFilterChildData childData = data.get(i).getChilds().get(j);
                        if (childData.isSelected()) {
                            // 选中
                            floors.add(childData.getValue());
                        }
                    }

                    if (floors.isEmpty()) {
                        result.remove("floorType");
                    } else {
                        result.put("floorType", join(floors, ","));
                    }
                    break;

                case "标签":
                    List<Integer> tags = new ArrayList<>();
                    for (int j = 0; j < childSize; j++) {
                        MoreFilterChildData childData = data.get(i).getChilds().get(j);
                        if (childData.isSelected()) {
                            // 选中
                            tags.add(Integer.parseInt(childData.getValue()));
                        }
                    }

                    int sum = 0;
                    for (Integer value: tags) {
                        sum += value;
                    }

                    if (tags.isEmpty()) {
                        result.remove("propertyTags");
                    } else {
                        result.put("propertyTags",sum);
                    }
                    break;

                case "状态":
                    List<String> status = new ArrayList<>();
                    for (int j = 0; j < childSize; j++) {
                        MoreFilterChildData childData = data.get(i).getChilds().get(j);
                        if (childData.isSelected()) {
                            // 选中
                            status.add(childData.getValue());
                        }
                    }

                    if (status.isEmpty()) {
                        result.remove("status");
                    } else {
                        result.put("status", join(status, ","));
                    }
                    break;
            }
        }
        return result;
    }

    @Override
    public void onResult(List<MoreFilterGroupData> data, Map<String, Object> result) {
        if (callback != null) {
            callback.callback(result);
        }
    }

    public interface Callback {
        void callback(Map<String, Object> result);
    }

    /**
     * 将多选的参数数组输出为带分隔符的字符串内容
     *
     * @param list      需要操作的List
     * @param seperator 分隔符内容
     * @return 拼接好的字符串
     */
    private String join(List<String> list, String seperator) {
        if (list.isEmpty()) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i)).append(seperator);
        }
        return sb.substring(0, sb.length() - seperator.length());
    }
}
