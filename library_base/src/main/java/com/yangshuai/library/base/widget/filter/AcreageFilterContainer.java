package com.yangshuai.library.base.widget.filter;

import android.content.Context;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.yangshuai.lib.button.StateButton;
import com.yangshuai.library.base.R;
import com.yangshuai.library.base.entity.ScopeConfigData;
import com.yangshuai.library.base.utils.ScreenUtils;
import com.yangshuai.library.base.utils.ToastUtil;
import com.yangshuai.library.base.utils.Utils;
import com.yangshuai.library.base.view.recycleview.GridSpacingItemDecoration;
import com.yangshuai.library.base.widget.DoubleRangeSeekBar;
import com.yangshuai.library.base.widget.filter.adapter.PriceFilterScopeAdapter;
import com.yangshuai.library.base.widget.filter.base.BaseFilterContainerView;
import com.yangshuai.library.base.widget.filter.model.PriceFilterScopeData;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 面积筛选器
 *
 * @author hcp
 * @created 2019/4/1
 */
public class AcreageFilterContainer extends BaseFilterContainerView {

    private TextView tvTitle;
    private DoubleRangeSeekBar mSeekBar;
    private EditText etMinSize, etMaxSize; // 输入框
    private StateButton btnOk, btnReset; // 确定和重置按钮
    private String minSize = "";
    private String maxSize = "";

    private RecyclerView mRecyclerView; // 价格区间选择列表
    private PriceFilterScopeAdapter scopeAdapter;
    private List<PriceFilterScopeData> filterScopeData;
    private int type = 0; // 筛选数据类型 1二手房 2租房 3新房
    private String scopeValue; // API返回的区间字符串，如50,80,100,120,150,200,300
    private List<String> scopeValueList; // 拆分后的数组

    public AcreageFilterContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        setContentView(R.layout.base_filter_acreage_container);
        setContainerHeight(Utils.dp2Px(getContext(), 300)); // 设置容器高度

        tvTitle = findViewById(R.id.base_filter_acreage_title);
        etMinSize = findViewById(R.id.base_filter_et_min_acreage); // 最低价输入框
        etMaxSize = findViewById(R.id.base_filter_et_max_acreage); // 最高价输入框
        btnOk = findViewById(R.id.base_filter_acreage_btn_ok);
        btnReset = findViewById(R.id.base_filter_acreage_btn_reset);

        mRecyclerView = findViewById(R.id.base_filter_acreage_rv);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setFocusableInTouchMode(false);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(4, ScreenUtils.dp2px(15), false));

        filterScopeData = new ArrayList<>();
//        if (type == 0){
//            // 如果没设置类型，则默认加载二手房的数据
//            getScopeData(1);
//        }
        scopeAdapter = new PriceFilterScopeAdapter(getContext(), filterScopeData);
        scopeAdapter.setCheckboxSelectedListener((pos, isSelected, data) -> {
            // 点击选择事件 清空文本框内容，二者只能有一个存在内容
            minSize = "";
            maxSize = "";
            etMinSize.setText("");
            etMaxSize.setText("");
            etMinSize.clearFocus();
            etMaxSize.clearFocus();

            PriceFilterScopeData sectionData = filterScopeData.get(pos);
            if (sectionData.isMultiple()) {
                // 多选
                sectionData.setSelected(!sectionData.isSelected());
                scopeAdapter.notifyDataSetChanged();
                return;
            } else {
                // 单选
                if (sectionData.isSelected()) {
                    sectionData.setSelected(false);
                } else {
                    List<PriceFilterScopeData> childs = filterScopeData;
                    for (PriceFilterScopeData child : childs) {
                        child.setSelected(data == child);
                    }
                }

                scopeAdapter.notifyDataSetChanged();
            }
        });
        mRecyclerView.setAdapter(scopeAdapter);

        // 监听输入框，如果输入了内容就重置列表的选择,二者只能有一个存在内容
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // s修改之前的文字
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // s改变后的字符串
            }

            @Override
            public void afterTextChanged(Editable s) {
                //s修改后的文字
                if (!TextUtils.isEmpty(s)) {
                    // 清空列表选中状态
                    List<PriceFilterScopeData> childs = filterScopeData;
                    for (PriceFilterScopeData child : filterScopeData) {
                        child.setSelected(false);
                    }
                    scopeAdapter.notifyDataSetChanged();
                }
            }
        };

        etMinSize.addTextChangedListener(watcher);
        etMaxSize.addTextChangedListener(watcher);

        mSeekBar = findViewById(R.id.base_filter_acreage_seekbar);
        mSeekBar.setUnit("不限", "1000㎡   "); // 添加空格避免让右侧单位显示不全。
        mSeekBar.setMax(1000);
        mSeekBar.setMinValue(0);
        mSeekBar.setMaxValue(1000);
        mSeekBar.setBarCallBack(new DoubleRangeSeekBar.SeekBarCallBack() {
            @Override
            public void onMoveTouch(int minValue, int maxValue) {
                // 获取滑动时的值
                if (minValue == 0) {
                    // 滑到0表示不限
                    etMinSize.setText("");
                } else {
                    etMinSize.setText("" + minValue);
                }

                if (maxValue == 0) {
                    etMaxSize.setText("");
                } else {
                    etMaxSize.setText("" + maxValue);
                }

            }
        });

        btnOk.setOnClickListener(v -> {
            // 确定，将所选参数回调给页面
            try {
                if (TextUtils.isEmpty(etMinSize.getText().toString())
                        && TextUtils.isEmpty(etMaxSize.getText().toString())) {
                    // 输入框为空,获取列表选中项
                    for (int i = 0; i < filterScopeData.size(); i++) {
                        if (filterScopeData.get(i).isSelected()) {
                            minSize = filterScopeData.get(i).getMinValue();
                            maxSize = filterScopeData.get(i).getMaxValue();
                        }
                    }
                } else {
                    minSize = etMinSize.getText().toString().trim();
                    maxSize = etMaxSize.getText().toString().trim();
                }


                if (TextUtils.isEmpty(minSize)) {
                    // 没有输入最小面积 显示格式为XX㎡以下
                    filterResult("", maxSize, String.format("%s%s%s", maxSize, "㎡", "以下"));
                } else if (TextUtils.isEmpty(maxSize)) {
                    // 没有输入最大面积 显示格式为XX万㎡以上
                    filterResult(minSize, "", String.format("%s%s%s", minSize, "㎡", "以上"));
                } else {
                    // 判断价格输入的正确性
                    if (Integer.parseInt(maxSize) <= 0) {
                        ToastUtil.Short("最大面积不能为0");
                    } else if (Integer.parseInt(minSize) > Integer.parseInt(maxSize)) {
                        ToastUtil.Short("最小面积不能超过最大面积");
                    } else {
                        // XX ~ XX ㎡
                        filterResult(minSize, maxSize, String.format("%s-%s%s", minSize, maxSize, "㎡"));
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        btnReset.setOnClickListener(v -> {
            // 重置，清空选择状态
            if (!TextUtils.isEmpty(etMinSize.getText())) {
                etMinSize.setText("");
            }

            if (!TextUtils.isEmpty(etMaxSize.getText())) {
                etMaxSize.setText("");
            }

            // 清空列表选中状态
            List<PriceFilterScopeData> childs = filterScopeData;
            for (PriceFilterScopeData child : filterScopeData) {
                child.setSelected(false);
            }
            scopeAdapter.notifyDataSetChanged();

            mSeekBar.setMinValue(0);
            mSeekBar.setMaxValue(1000);
            mSeekBar.invalidate(); // 重新绘制到初始状态
            minSize = "";
            maxSize = "";
//            filterResult("", "", "价格");

        });

    }

    /**
     * 设置面积筛选的数据类型
     *
     * @param type 1二手房 2租房 3新房
     */
    public void setType(int type, String cityCode) {
        this.type = type;
        getScopeData(type, cityCode);
    }

    /**
     * 获取面积区间数据(产品说固定用普通住宅类型的)
     *
     * @param type 1二手房 2租房 3新房
     */
    public void getScopeData(int type, String cityCode) {
        this.type = type;
        if (!filterScopeData.isEmpty()) {
            filterScopeData.clear();
        }

        String channelName = "";
        if (type == 1) {
            channelName = "二手房";
        } else if (type == 2) {
            channelName = "租房";
        } else if (type == 3) {
            channelName = "新房";
        }

        scopeValueList = new ArrayList<>();
        List<ScopeConfigData> scopeConfigList = LitePal.where("channelClassname = ? AND name = ? AND cityCode = ?", channelName, "普通住宅", cityCode)
                .find(ScopeConfigData.class, true);
        if (scopeConfigList != null && !scopeConfigList.isEmpty()) {
            // 取出面积区间的数据，后端返回的是逗号分隔的 50,80,100,120,150,200,300，要自己拆分出标签
            if (scopeConfigList.get(0).getScope() != null
                    && !scopeConfigList.get(0).getScope().isEmpty()) {
                int size = scopeConfigList.get(0).getScope().size();
                for (int i = 0; i < size; i++) {
                    if ("area".equals(scopeConfigList.get(0).getScope().get(i).getType())) {
                        if (!TextUtils.isEmpty(scopeConfigList.get(0).getScope().get(i).getValue())) {
                            scopeValue = scopeConfigList.get(0).getScope().get(i).getValue();
                            // 将字符串转换为集合
                            scopeValueList = Arrays.asList(scopeValue.split(","));
                        }
                    }
                }
            }

            // 从拆分出来的数组，处理成一组一组的标签
            if (!scopeValueList.isEmpty()) {
                int valueSize = scopeValueList.size();
                if (valueSize == 1) {
                    // 如果只有一条数据，则分别添加XX以下和XX以上
                    filterScopeData.add(new PriceFilterScopeData(scopeValueList.get(0) + "㎡以下", "", scopeValueList.get(0), false));
                    filterScopeData.add(new PriceFilterScopeData(scopeValueList.get(0) + "㎡以上", scopeValueList.get(0), "", false));
                } else {
                    String value2 = "";
                    String startValue = ""; // 第一条内容
                    String endValue = ""; // 最后一条内容
                    List<PriceFilterScopeData> middleValues = new ArrayList<>();
                    for (int i = 0; i < valueSize; i++) {
                        if (i == 0) {
                            // 第一条数据，XX以下
                            startValue = scopeValueList.get(i);
                            value2 = startValue;
                        } else if (i == (valueSize - 1)) {
                            // 最后一条数据，XX以上
                            endValue = scopeValueList.get(i);
                        }

                        if (i != 0) {
                            middleValues.add(new PriceFilterScopeData(value2 + "-" + scopeValueList.get(i) + "㎡", value2, scopeValueList.get(i), false));
                        }
                        value2 = scopeValueList.get(i);
                    }

                    if (!TextUtils.isEmpty(startValue)) {
                        filterScopeData.add(new PriceFilterScopeData(startValue + "㎡以下", "", startValue, false));
                    }

                    if (!middleValues.isEmpty()) {
                        filterScopeData.addAll(middleValues);
                    }

                    if (!TextUtils.isEmpty(endValue)) {
                        filterScopeData.add(new PriceFilterScopeData(endValue + "㎡以上", endValue, "", false));
                    }

                }
            } else {
                getSampleData();
            }

        } else {
            getSampleData();
        }

        if (scopeAdapter != null) {
            scopeAdapter.notifyDataSetChanged();
        }
    }

    private void getSampleData() {
        filterScopeData.clear();
        // 如果没有获取到API数据，则用本地固定的一套数据
        filterScopeData.add(new PriceFilterScopeData("50㎡以下", "", "50", false));
        filterScopeData.add(new PriceFilterScopeData("50-70㎡", "50", "70", false));
        filterScopeData.add(new PriceFilterScopeData("70-90㎡", "70", "90", false));
        filterScopeData.add(new PriceFilterScopeData("90-120㎡", "90", "120", false));
        filterScopeData.add(new PriceFilterScopeData("120-150㎡", "120", "150", false));
        filterScopeData.add(new PriceFilterScopeData("150-200㎡", "150", "200", false));
        filterScopeData.add(new PriceFilterScopeData("200-300㎡", "200", "300", false));
        filterScopeData.add(new PriceFilterScopeData("300㎡以上", "300", "", false));
    }

    /**
     * 将筛选参数返回
     *
     * @param minSize 当前最小值
     * @param maxSize 当前最大值
     * @param label   显示筛选按钮的文本内容,例: 50-100㎡
     */
    private void filterResult(String minSize, String maxSize, String label) {
        if (onFilterResultListener != null) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("MinSize", minSize);
            resultMap.put("MaxSize", maxSize);
            resultMap.put("Label", label);
            if (!resultMap.isEmpty()) {
                onFilterResultListener.onResult(AcreageFilterContainer.this, resultMap);
            } else {
                onFilterResultListener.onResult(AcreageFilterContainer.this, null);
            }
        }

        // 关闭软键盘
        hideSoftInput(getContext(), etMaxSize);
        // 关闭筛选容器
        close();
    }

    /**
     * 隐藏键盘
     */
    private void hideSoftInput(Context context, EditText et) {
        try {
            ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(et.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
