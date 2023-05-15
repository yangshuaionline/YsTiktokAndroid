package com.yangshuai.library.base.widget.filter;

import android.content.Context;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
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
 * 价格筛选器
 *
 * @author hcp
 * @created 2019/3/29
 */
public class PriceFilterContainer extends BaseFilterContainerView {

    private TextView tvTitle;
    private DoubleRangeSeekBar mSeekBar;
    private String unitStr = "万"; // 价格单位
    private EditText etMinPrice, etMaxPrice; // 输入框
    private StateButton btnOk, btnReset; // 确定和重置按钮
    private String minPrice = "";
    private String maxPrice = "";
//     int spanCount = 4;//布局显示个数，新房个数3/其他4

    private RecyclerView mRecyclerView; // 价格区间选择列表
    private PriceFilterScopeAdapter scopeAdapter;
    private List<PriceFilterScopeData> filterScopeData;

    private int type = 1; // 筛选数据类型 1二手房 2租房 3新房
    private String scopeValue; // API返回的区间字符串，如50,80,100,120,150,200,300
    private List<String> scopeValueList; // 拆分后的数组

    public PriceFilterContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        setContentView(R.layout.base_filter_price_container);
        setContainerHeight(Utils.dp2Px(getContext(), 300)); // 设置容器高度

        tvTitle = findViewById(R.id.base_filter_price_title);
        etMinPrice = findViewById(R.id.base_filter_et_min_price); // 最低价输入框
        etMaxPrice = findViewById(R.id.base_filter_et_max_price); // 最高价输入框

        // 根据价格类型设置最大输入长度(二手房——单元万，6位整数限制（十亿）租房——单位元，10位整数限制（十亿）)
        if ("万".equals(unitStr)) {
            // 设置maxLength
            InputFilter[] filters = {new InputFilter.LengthFilter(6)};
            etMinPrice.setFilters(filters);
            etMaxPrice.setFilters(filters);
        } else {
            InputFilter[] filters = {new InputFilter.LengthFilter(10)};
            etMinPrice.setFilters(filters);
            etMaxPrice.setFilters(filters);
        }


        btnOk = findViewById(R.id.base_filter_price_btn_ok);
        btnReset = findViewById(R.id.base_filter_price_btn_reset);
        mRecyclerView = findViewById(R.id.base_filter_price_rv);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setFocusableInTouchMode(false);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(4, ScreenUtils.dp2px(12), false));

        filterScopeData = new ArrayList<>();
        scopeAdapter = new PriceFilterScopeAdapter(getContext(), filterScopeData);
        scopeAdapter.setCheckboxSelectedListener((pos, isSelected, data) -> {
            // 点击选择事件 清空文本框内容，二者只能有一个存在内容
            minPrice = "";
            maxPrice = "";
            etMinPrice.setText("");
            etMaxPrice.setText("");
            etMinPrice.clearFocus();
            etMinPrice.clearFocus();

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

        etMinPrice.addTextChangedListener(watcher);
        etMaxPrice.addTextChangedListener(watcher);

        mSeekBar = findViewById(R.id.base_filter_price_seekbar);
        mSeekBar.setUnit("1", "1000"); // 添加空格避免让右侧单位显示不全。
        mSeekBar.setMax(1000);
        mSeekBar.setMinValue(1);
        mSeekBar.setMaxValue(1000);
        mSeekBar.setBarCallBack(new DoubleRangeSeekBar.SeekBarCallBack() {
            @Override
            public void onMoveTouch(int minValue, int maxValue) {
                // 获取滑动时的值
                if (minValue == 0) {
                    etMinPrice.setText("1");
                } else {
                    etMinPrice.setText("" + minValue);
                }

                if (maxValue == 0) {
                    etMaxPrice.setText("");
                } else {
                    etMaxPrice.setText("" + maxValue);
                }
            }
        });


        btnOk.setOnClickListener(v -> {
            // 确定，将所选参数回调给页面
            try {
                if (TextUtils.isEmpty(etMinPrice.getText().toString())
                        && TextUtils.isEmpty(etMaxPrice.getText().toString())) {
                    // 输入框为空,获取列表选中项
                    for (int i = 0; i < filterScopeData.size(); i++) {
                        if (filterScopeData.get(i).isSelected()) {
                            minPrice = filterScopeData.get(i).getMinValue();
                            maxPrice = filterScopeData.get(i).getMaxValue();
                        }
                    }
                } else {
                    minPrice = etMinPrice.getText().toString().trim();
                    maxPrice = etMaxPrice.getText().toString().trim();
                }
                if (TextUtils.isEmpty(minPrice)) {
                    // 没有输入最低价 显示格式为XX万(元)以下
                    filterResult("", maxPrice, String.format("%s%s%s", maxPrice, unitStr, "以下"));
                } else if (TextUtils.isEmpty(maxPrice)) {
                    // 没有输入最高价 显示格式为XX万(元)以上
                    filterResult(minPrice, "", String.format("%s%s%s", minPrice, unitStr, "以上"));
                } else {
                    // 判断价格输入的正确性
                    if (Integer.parseInt(maxPrice) <= 0) {
                        ToastUtil.Short("最高价不能为0");
                    } else if (Integer.parseInt(minPrice) > Integer.parseInt(maxPrice)) {
                        ToastUtil.Short("最低价不能超过最高价");
                    } else {
                        // XX ~ XX 万(元)
                        filterResult(minPrice, maxPrice, String.format("%s-%s%s", minPrice, maxPrice, unitStr));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        btnReset.setOnClickListener(v -> {
            // 重置，清空选择状态
            if (!TextUtils.isEmpty(etMinPrice.getText())) {
                etMinPrice.setText("");
            }

            if (!TextUtils.isEmpty(etMaxPrice.getText())) {
                etMaxPrice.setText("");
            }

            // 清空列表选中状态
            List<PriceFilterScopeData> childs = filterScopeData;
            for (PriceFilterScopeData child : filterScopeData) {
                child.setSelected(false);
            }
            scopeAdapter.notifyDataSetChanged();

            if ("元".equals(unitStr)) {
                mSeekBar.setMinValue(1);
                mSeekBar.setMaxValue(10000);
            } else {
                mSeekBar.setMinValue(1);
                mSeekBar.setMaxValue(1000);
            }
            mSeekBar.invalidate(); // 重新绘制到初始状态
            minPrice = "";
            maxPrice = "";
//            filterResult("", "", "价格");

        });

    }

    /**
     * 设置价格筛选的数据类型
     *
     * @param type     1二手房 2租房 3新房
     * @param cityCode 城市代码
     */
    public void setType(int type, String cityCode) {
        this.type = type;
        getScopeData(type, cityCode);
    }

    /**
     * 获取价格区间数据(产品说固定用普通住宅类型的)
     *
     * @param type     1二手房 2租房 3新房
     * @param cityCode 城市代码(区间数据按照城市区分)
     */
    public void getScopeData(int type, String cityCode) {
        this.type = type;
        filterScopeData.clear();

        String channelName = "";
        String scopeType = "";
        if (type == 1) {
            channelName = "二手房";
            scopeType = "price";
            this.unitStr = "万";
        } else if (type == 2) {
            channelName = "租房";
            scopeType = "rentprice";
            this.unitStr = "元";
        } else if (type == 3) {
            channelName = "新房";
            scopeType = "averageprice";
            this.unitStr = "元";
        }

        scopeValueList = new ArrayList<>();
        List<ScopeConfigData> scopeConfigList =
                LitePal.where("channelClassname = ? AND name = ? AND cityCode = ?", channelName, "普通住宅", cityCode)
                .find(ScopeConfigData.class, true);
        if (scopeConfigList != null && !scopeConfigList.isEmpty()) {
            // 取出价格区间的数据，后端返回的是逗号分隔的 50,80,100,120,150,200,300，要自己拆分出标签
            if (scopeConfigList.get(0).getScope() != null
                    && !scopeConfigList.get(0).getScope().isEmpty()) {
                int size = scopeConfigList.get(0).getScope().size();
                for (int i = 0; i < size; i++) {
                    if (scopeType.equals(scopeConfigList.get(0).getScope().get(i).getType())) {
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
                filterScopeData.clear();
                int valueSize = scopeValueList.size();
                if (valueSize == 1) {
                    // 如果只有一条数据，则分别添加XX以下和XX以上
                    filterScopeData.add(new PriceFilterScopeData(scopeValueList.get(0) + unitStr + "以下", "", scopeValueList.get(0), false));
                    filterScopeData.add(new PriceFilterScopeData(scopeValueList.get(0) + unitStr + "以上", scopeValueList.get(0), "", false));
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
                            middleValues.add(new PriceFilterScopeData(value2 + "-" + scopeValueList.get(i) + unitStr, value2, scopeValueList.get(i), false));
                        }
                        value2 = scopeValueList.get(i);
                    }

                    if (!TextUtils.isEmpty(startValue)) {
                        filterScopeData.add(new PriceFilterScopeData(startValue + unitStr + "以下", "", startValue, false));
                    }

                    if (!middleValues.isEmpty()) {
                        filterScopeData.addAll(middleValues);
                    }

                    if (!TextUtils.isEmpty(endValue)) {
                        filterScopeData.add(new PriceFilterScopeData(endValue + unitStr + "以上", endValue, "", false));
                    }

                }
            } else {
                getSampleData();
            }

        } else {
            // 如果没有获取到API数据，则用本地固定的一套数据
            getSampleData();
        }

        if (scopeAdapter != null) {
            scopeAdapter.notifyDataSetChanged();
        }
    }

    private void getSampleData() {
        filterScopeData.clear();

        if ("万".equals(unitStr)) {
            filterScopeData.add(new PriceFilterScopeData("50万以下", "", "50", false));
            filterScopeData.add(new PriceFilterScopeData("50-100万", "50", "100", false));
            filterScopeData.add(new PriceFilterScopeData("100-150万", "100", "150", false));
            filterScopeData.add(new PriceFilterScopeData("150-200万", "150", "200", false));
            filterScopeData.add(new PriceFilterScopeData("200-250万", "200", "250", false));
            filterScopeData.add(new PriceFilterScopeData("250-300万", "250", "300", false));
            filterScopeData.add(new PriceFilterScopeData("300-350万", "300", "350", false));
            filterScopeData.add(new PriceFilterScopeData("350万以上", "350", "", false));
        } else {
            filterScopeData.add(new PriceFilterScopeData("500元以下", "", "500", false));
            filterScopeData.add(new PriceFilterScopeData("500-1000元", "500", "1000", false));
            filterScopeData.add(new PriceFilterScopeData("1000-1500元", "1000", "1500", false));
            filterScopeData.add(new PriceFilterScopeData("1500-2000元", "1500", "2000", false));
            filterScopeData.add(new PriceFilterScopeData("2000-3000元", "2000", "3000", false));
            filterScopeData.add(new PriceFilterScopeData("3000-4000元", "3000", "4000", false));
            filterScopeData.add(new PriceFilterScopeData("4000-5000元", "4000", "5000", false));
            filterScopeData.add(new PriceFilterScopeData("5000元以上", "5000", "", false));
        }

        if (scopeAdapter != null) {
            scopeAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 设置价格范围数据
     */
    public void setPriceScopeData(List<PriceFilterScopeData> scopeData) {
        if (scopeData != null) {
            filterScopeData.clear();
            filterScopeData.addAll(scopeData);
            scopeAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 设置筛选价格单位(默认为 万)
     */
    public void setUnit(String unit) {
        if (TextUtils.isEmpty(unit)) {
            return;
        }

        this.unitStr = unit;

        if ("元".equals(unit)) {
            tvTitle.setText(String.format("价格区间(%s%s%s)", unitStr, "/", "月"));
        } else {
            tvTitle.setText(String.format("价格区间(%s)", unitStr));
        }

        // 根据价格类型设置最大输入长度(二手房——单元万，6位整数限制（十亿）租房——单位元，10位整数限制（十亿）)
        if ("万".equals(unitStr)) {
            // 设置maxLength
            InputFilter[] filters = {new InputFilter.LengthFilter(6)};
            etMinPrice.setFilters(filters);
            etMaxPrice.setFilters(filters);
        } else {
            InputFilter[] filters = {new InputFilter.LengthFilter(10)};
            etMinPrice.setFilters(filters);
            etMaxPrice.setFilters(filters);
        }

//        getSampleData();
    }

    /**
     * 设置选择条的可选值范围
     *
     * @param minValue 最小值
     * @param maxValue 最大值
     * @param unit     单位
     */
    public void setRangeValue(int minValue, int maxValue, String unit) {
        this.unitStr = unit;
        if ("元".equals(unit)) {
            tvTitle.setText(String.format("价格区间(%s%s%s)", unitStr, "/", "月"));
        } else {
            tvTitle.setText(String.format("价格区间(%s)", unitStr));
        }

        mSeekBar.setUnit("" + minValue + unitStr, maxValue + unitStr + "      "); // 添加空格避免让右侧单位显示不全。
        mSeekBar.setMax(maxValue);
        mSeekBar.setMinValue(minValue);
        mSeekBar.setMaxValue(maxValue);

        if (!TextUtils.isEmpty(unit)) {
//            getSampleData();
        }
    }


    /**
     * 将筛选参数返回
     *
     * @param minPrice 当前最小值
     * @param maxPrice 当前最大值
     * @param label    显示筛选按钮的文本内容,例: 5-100万
     */
    private void filterResult(String minPrice, String maxPrice, String label) {
        if (onFilterResultListener != null) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("MinPrice", minPrice);
            resultMap.put("MaxPrice", maxPrice);
            resultMap.put("Label", label);
            if (!resultMap.isEmpty()) {
                onFilterResultListener.onResult(PriceFilterContainer.this, resultMap);
            } else {
                onFilterResultListener.onResult(PriceFilterContainer.this, null);
            }
        }

        // 关闭软键盘
        hideSoftInput(getContext(), etMaxPrice);
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


    public void setTitile(String title) {
        tvTitle.setText(title);
    }
}
