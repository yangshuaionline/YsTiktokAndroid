package com.yangshuai.library.base.widget.filter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yangshuai.lib.button.StateButton;
import com.yangshuai.library.base.R;
import com.yangshuai.library.base.common.BaseCommon;
import com.yangshuai.library.base.dictionary.DictionaryHelp;
import com.yangshuai.library.base.entity.DictionaryBean;
import com.yangshuai.library.base.entity.HouseSourceBean;
import com.yangshuai.library.base.utils.TimeUtil;
import com.yangshuai.library.base.utils.ToastUtil;
import com.yangshuai.library.base.utils.ValidationUtils;
import com.yangshuai.library.base.view.recycleview.GridSectionAverageGapItemDecoration;
import com.yangshuai.library.base.widget.DatePicker;
import com.yangshuai.library.base.widget.filter.adapter.MoreSectionAdapter;
import com.yangshuai.library.base.widget.filter.base.BaseFilterContainerView;
import com.yangshuai.library.base.widget.filter.model.MoreFilterChildData;
import com.yangshuai.library.base.widget.filter.model.MoreFilterSectionData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 房源模块通用的‘更多筛选’ (有一堆输入框的)
 *
 * @author hcp
 * @created 2019-07-03
 */
public class MoreHouseFilterContainer extends BaseFilterContainerView {
    public List<HouseSourceBean> houseSourceBeans=new ArrayList<>();
    private int filterType = 1; // 筛选类型 1二手房 2租房
    private RecyclerView mRecyclerView;
    public MoreSectionAdapter mSectionAdapter;
    private List<MoreFilterSectionData> mFilterGroupData = new ArrayList<>();
    private RelativeLayout mHouseSource;
    private EditText etHouseCode, etHouseAgent; // 房源码和房源归属人输入框
    private EditText etOwnerPhone; // 业主号码
    private EditText etBuilding; // 栋座
    private EditText etUnit;// 单元
    private EditText etHouseNo; // 房号
    private EditText etStore; // 门店
    private TextView tvStartTime, tvEndTime; // 新增时间(开始和结束)
    private EditText etFloorMin, etFloorMax; // 楼层
    private EditText etFloorPriceMin, etFloorPriceMax; // 底价(售房才有)
    private EditText etFirstPayAmountMin, etFirstPayAmountMax; // 首付(售房才有)
    private TextView tvsourcename;//房屋来源
    private ImageButton ibCode;
    private StateButton btnOk, btnReset; // 确定和重置按钮
    private DatePicker datePickerStart, datePickerEnd; // 开始日期、结束日期(新增时间筛选)
    private EditText etHouseCreateAgent; // 房源创建人输入框
    private EditText etscoreMin, etscoreMax; // 房源质量分
    private EditText etbuildageMin, etbuildageMax; // 房源建筑年代
    private onQrScanClickListener mScanClickListener; // 扫码点击事件
    private onSourceClickListener msourceClickListener; // 房源来源点击事件
    private boolean isPubHouse;// 是否为公共房源(公共房源目前隐藏房源归属人筛选)

    public MoreHouseFilterContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        setContentView(R.layout.base_filter_house_more_container);
    }

    @Override
    public void show() {
        super.show();
        // 当筛选容器显示出来后再初始化
        if (mRecyclerView == null) {
            initView();
        }
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.base_filter_more_rv);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        mRecyclerView.addItemDecoration(new GridSectionAverageGapItemDecoration(
                16, 16,
                16, 16));
//        mRecyclerView.setNestedScrollingEnabled(false);

//        mFilterGroupData = getSampleData();

        mSectionAdapter = new MoreSectionAdapter(R.layout.base_filter_more_cell_child,
                R.layout.base_filter_more_cell_title_new, mFilterGroupData);
        mSectionAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            // 组内标签点击事件
            MoreFilterSectionData sectionData = mFilterGroupData.get(position);
            if (sectionData.t != null) {
                if (sectionData.t.isMultiple()) {
                    // 多选模式
                    sectionData.t.setSelected(!sectionData.t.isSelected());
                    mSectionAdapter.notifyDataSetChanged();
                } else {
                    // 单选模式
                    if (sectionData.t.isSelected()) {
                        if ("房屋用途".equals(sectionData.t.getGroupName())) {
                            // 房屋用途必须要选一个，不允许取消
                            sectionData.t.setSelected(true);
                        } else {
                            sectionData.t.setSelected(false);
                        }
                        mSectionAdapter.notifyDataSetChanged();
                    } else {
                        for (MoreFilterSectionData child : mFilterGroupData) {
                            if (!child.isHeader
                                    && child.t.getGroupName().equals(sectionData.t.getGroupName())) {
                                // 设置当前分组除了点击项之外全部取消选择
                                child.t.setSelected(sectionData.t == child.t);
                            }
                        }
                        mSectionAdapter.notifyDataSetChanged();
                    }
                }

//                mSectionAdapter.notifyDataSetChanged();
            }

        });

        mRecyclerView.setAdapter(mSectionAdapter);

        etHouseCode = findViewById(R.id.base_filter_et_house_code);
        etHouseAgent = findViewById(R.id.base_filter_et_house_agent);
        etHouseCreateAgent = findViewById(R.id.base_filter_et_house_agent_create);
        etOwnerPhone = findViewById(R.id.base_filter_et_owner);
        etBuilding = findViewById(R.id.base_filter_et_building);
        etUnit = findViewById(R.id.base_filter_et_unit);
        etHouseNo = findViewById(R.id.base_filter_et_room_num);
        etStore = findViewById(R.id.base_filter_et_store_num);
        tvStartTime = findViewById(R.id.base_filter_et_start_time);
        tvEndTime = findViewById(R.id.base_filter_et_ent_time);
        etFloorMin = findViewById(R.id.base_filter_et_floor_min);
        etFloorMax = findViewById(R.id.base_filter_et_floor_max);
        etFloorPriceMin = findViewById(R.id.base_filter_et_low_price_min);
        etFloorPriceMax = findViewById(R.id.base_filter_et_low_price_max);
        etFirstPayAmountMin = findViewById(R.id.base_filter_et_dp_price_min);
        etFirstPayAmountMax = findViewById(R.id.base_filter_et_dp_price_max);
        etscoreMin=findViewById(R.id.base_filter_et_dp_score_min);
        etscoreMax=findViewById(R.id.base_filter_et_dp_score_max);
        tvsourcename=findViewById(R.id.base_filter_tv_house_source_text);
        // 房源建筑年代
        etbuildageMin=findViewById(com.yangshuai.library.base.R.id.base_filter_et_dp_buildage_min);
        etbuildageMax=findViewById(com.yangshuai.library.base.R.id.base_filter_et_dp_buildage_max);
        mHouseSource=findViewById(com.yangshuai.library.base.R.id.base_filter_rly_house_source);
        ibCode = findViewById(R.id.base_filter_ib_house_code);
        ibCode.setOnClickListener(v -> {
            // 扫一扫
            if (mScanClickListener != null) {
                mScanClickListener.onQrScanClick();
            }
        });
        /**
         * 跳转界面选择房源来源
         */
        mHouseSource.setOnClickListener(v -> {
            // 扫一扫
            if (msourceClickListener != null) {
                msourceClickListener.oSourceClick();
            }
        });

        // 初始化日期选择器
        datePickerStart = new DatePicker((Activity) getContext(), "开始时间");
        datePickerStart.setOnDatePickListener(date -> {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = simpleDateFormat.format(date);
            tvStartTime.setText(dateString);
        });

        datePickerEnd = new DatePicker((Activity) getContext(), "结束时间");
        datePickerEnd.setOnDatePickListener(date -> {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = simpleDateFormat.format(date);
            tvEndTime.setText(dateString);
        });

        tvStartTime.setOnClickListener(v -> {
            // 选择开始时间
            datePickerStart.show();
        });

        tvEndTime.setOnClickListener(v -> {
            // 选择结束时间
            datePickerEnd.show();
        });

        // 重置
        btnReset = findViewById(R.id.base_filter_more_btn_reset);
        btnReset.setOnClickListener(v -> {
            setBtnReset();
        });

        // 确定
        btnOk = findViewById(R.id.base_filter_more_btn_ok);
        btnOk.setOnClickListener(v -> {
            if (onFilterResultListener != null) {
                // 将参数返回调用业务层
                Map<String, Object> resultMap = new HashMap<>(); // 存放已选中的筛选参数
                int resultAllSize = 0; // 用于获取当前选中的总数

                // 获取房源码筛选数据
//                if (!TextUtils.isEmpty(etHouseCode.getText())
//                        && etHouseCode.getText().toString().trim().length() < 18) {
//                    ToastUtil.Short("请输入18位房源码");
//                    return;
//                }
                if (!TextUtils.isEmpty(etHouseCode.getText())) {
                    resultMap.put("houseId", etHouseCode.getText().toString().trim()); // 房源ID
                    resultAllSize += 1;
                    resultMap.put("CurrentName", etHouseCode.getText().toString().trim()); // 当前点击的内容
                } else {
                    resultMap.remove("houseId");
                }

                // 获取房源归属经纪人筛选数据
                if (!TextUtils.isEmpty(etHouseAgent.getText())) {
                    resultMap.put("agent", etHouseAgent.getText().toString().trim()); // 归属经纪人/姓名/手机号
                    resultAllSize += 1;
                    resultMap.put("CurrentName", etHouseAgent.getText().toString().trim()); // 当前点击的内容
                } else {
                    resultMap.remove("agent");
                }

                // 房源创建人
                if (!TextUtils.isEmpty(etHouseCreateAgent.getText())) {
                    resultMap.put("createByInfo", etHouseCreateAgent.getText().toString().trim()); // 归属经纪人/姓名/手机号
                    resultAllSize += 1;
                    resultMap.put("CurrentName", etHouseCreateAgent.getText().toString().trim()); // 当前点击的内容
                } else {
                    resultMap.remove("createByInfo");
                }

                // 业主号码
                if (!TextUtils.isEmpty(etOwnerPhone.getText())) {
                    if (!ValidationUtils.isMobile(etOwnerPhone.getText().toString().trim())) {
                        ToastUtil.Short("请输入正确的业主电话");
                        return;
                    } else {
                        resultMap.put("ownerPhone", etOwnerPhone.getText().toString().trim()); // 业主号码
                        resultAllSize += 1;
                        resultMap.put("CurrentName", etOwnerPhone.getText().toString().trim()); // 当前点击的内容
                    }
                } else {
                    resultMap.remove("ownerPhone");
                }

                // 栋座
                if (!TextUtils.isEmpty(etBuilding.getText())) {
                    resultMap.put("building", etBuilding.getText().toString().trim()); // 归属经纪人/姓名/手机号
                    resultAllSize += 1;
                    resultMap.put("CurrentName", etBuilding.getText().toString().trim()); // 当前点击的内容
                } else {
                    resultMap.remove("building");
                }

                // 单元
                if (!TextUtils.isEmpty(etUnit.getText())) {
                    resultMap.put("unit", etUnit.getText().toString().trim()); // 归属经纪人/姓名/手机号
                    resultAllSize += 1;
                    resultMap.put("CurrentName", etUnit.getText().toString().trim()); // 当前点击的内容
                } else {
                    resultMap.remove("unit");
                }

                // 房号
                if (!TextUtils.isEmpty(etHouseNo.getText())) {
                    resultMap.put("propertyNo", etHouseNo.getText().toString().trim()); // 归属经纪人/姓名/手机号
                    resultAllSize += 1;
                    resultMap.put("CurrentName", etHouseNo.getText().toString().trim()); // 当前点击的内容
                } else {
                    resultMap.remove("propertyNo");
                }

                // 门店
                if (!TextUtils.isEmpty(etStore.getText())) {
                    resultMap.put("store", etStore.getText().toString().trim()); // 归属经纪人/姓名/手机号
                    resultAllSize += 1;
                    resultMap.put("CurrentName", etStore.getText().toString().trim()); // 当前点击的内容
                } else {
                    resultMap.remove("store");
                }

                // 发布时间
                if (!TextUtils.isEmpty(tvStartTime.getText())
                        && !TextUtils.isEmpty(tvEndTime.getText())) {
                    String startTime = tvStartTime.getText().toString().trim();
                    String endTime = tvEndTime.getText().toString().trim();
                    // 开始时间不能大于结束时间
                    if (TimeUtil.compareTimes(startTime, endTime)) {
                        ToastUtil.Short("开始时间不能大于结束时间！");
                        return;
                    } else {
                        resultMap.put("startReleaseTime", String.valueOf(TimeUtil.dateToLong(startTime, "yyyy-MM-dd"))); // 新增开始时间
                        resultMap.put("endReleaseTime", String.valueOf(TimeUtil.dateToLong(endTime, "yyyy-MM-dd"))); // 新增结束时间
                        resultAllSize += 1;
                        resultMap.put("CurrentName", startTime + "-" + endTime); // 当前点击的内容
                    }
                } else if (!TextUtils.isEmpty(tvStartTime.getText())
                        && TextUtils.isEmpty(tvEndTime.getText())) {
                    // 只筛选开始时间
                    resultMap.put("startReleaseTime", String.valueOf(TimeUtil.dateToLong(tvStartTime.getText().toString(), "yyyy-MM-dd"))); // 开始时间
                    resultMap.remove("endReleaseTime");
                    resultAllSize += 1;
                    resultMap.put("CurrentName", tvStartTime.getText().toString().trim()); // 当前点击的内容
                } else if (!TextUtils.isEmpty(tvEndTime.getText())
                        && TextUtils.isEmpty(tvStartTime.getText())) {
                    // 只筛选结束时间
                    resultMap.put("endReleaseTime", String.valueOf(TimeUtil.dateToLong(tvEndTime.getText().toString(), "yyyy-MM-dd"))); // 结束时间
                    resultMap.remove("startReleaseTime");
                    resultAllSize += 1;
                    resultMap.put("CurrentName", tvEndTime.getText().toString().trim()); // 当前点击的内容
                } else {
                    // 取消筛选时间
                    resultMap.remove("startReleaseTime");
                    resultMap.remove("endReleaseTime");
                }

                // 楼层
                if (!TextUtils.isEmpty(etFloorMin.getText())
                        && !TextUtils.isEmpty(etFloorMax.getText())) {
                    // 筛选楼层范围
                    int minFloor = Integer.parseInt(etFloorMin.getText().toString().trim());
                    int maxFloor = Integer.parseInt(etFloorMax.getText().toString().trim());
                    if (minFloor > maxFloor) {
                        ToastUtil.Short("最低楼层必须小于或等于最高楼层！");
                        return;
                    } else {
                        resultMap.put("minFloorNo", etFloorMin.getText().toString().trim()); // 最低楼层
                        resultMap.put("maxFloorNo", etFloorMax.getText().toString().trim()); // 最高楼层
                        resultAllSize += 1;
                        resultMap.put("CurrentName", etFloorMin.getText().toString().trim() + "层" + "~" + etFloorMax.getText().toString().trim() + "层"); // 当前点击的内容
                    }
                } else if (!TextUtils.isEmpty(etFloorMin.getText()) && TextUtils.isEmpty(etFloorMax.getText())) {
                    // 只筛选最小楼层
                    resultMap.put("minFloorNo", etFloorMin.getText().toString().trim()); // 最低楼层
                    resultMap.remove("maxFloorNo");
                    resultAllSize += 1;
                    resultMap.put("CurrentName", etFloorMin.getText().toString().trim() + "层以上"); // 当前点击的内容
                } else if (!TextUtils.isEmpty(etFloorMax.getText()) && TextUtils.isEmpty(etFloorMin.getText())) {
                    // 只筛选最大楼层
                    resultMap.put("maxFloorNo", etFloorMax.getText().toString().trim()); // 最高楼层
                    resultMap.remove("minFloorNo");
                    resultAllSize += 1;
                    resultMap.put("CurrentName", etFloorMax.getText().toString().trim() + "层以下"); // 当前点击的内容
                } else {
                    // 不筛选楼层
                    resultMap.remove("minFloorNo");
                    resultMap.remove("maxFloorNo");
                }

                // 底价(二手房筛选)
                if (!TextUtils.isEmpty(etFloorPriceMin.getText())
                        && !TextUtils.isEmpty(etFloorPriceMax.getText())) {
                    // 筛选底价范围
                    float minEdtPrice = Float.parseFloat(etFloorPriceMin.getText().toString().trim());
                    float maxEdtPrice = Float.parseFloat(etFloorPriceMax.getText().toString().trim());
                    if (minEdtPrice > maxEdtPrice) {
                        ToastUtil.Short("最低底价必须小于或等于最高底价！");
                        return;
                    } else {
                        resultMap.put("minFloorPrice", etFloorPriceMin.getText().toString().trim()); // 最低底价
                        resultMap.put("maxFloorPrice", etFloorPriceMax.getText().toString().trim()); // 最高底价
                        resultAllSize += 1;
                        resultMap.put("CurrentName", etFloorPriceMin.getText().toString().trim() + "万" + "~" + etFloorPriceMax.getText().toString().trim() + "万"); // 当前点击的内容
                    }
                } else if (!TextUtils.isEmpty(etFloorPriceMin.getText()) && TextUtils.isEmpty(etFloorPriceMax.getText())) {
                    // 只筛选最小底价
                    resultMap.put("minFloorPrice", etFloorPriceMin.getText().toString().trim()); // 最低底价
                    resultMap.remove("maxFloorPrice");
                    resultAllSize += 1;
                    resultMap.put("CurrentName", etFloorPriceMin.getText().toString().trim() + "万以上"); // 当前点击的内容
                } else if (!TextUtils.isEmpty(etFloorPriceMax.getText()) && TextUtils.isEmpty(etFloorPriceMin.getText())) {
                    // 只筛选最大底价
                    resultMap.put("maxFloorPrice", etFloorPriceMax.getText().toString().trim()); // 最高底价
                    resultMap.remove("minFloorPrice");
                    resultAllSize += 1;
                    resultMap.put("CurrentName", etFloorPriceMax.getText().toString().trim() + "万以下"); // 当前点击的内容
                } else {
                    // 不筛选底价
                    resultMap.remove("minFloorPrice");
                    resultMap.remove("maxFloorPrice");
                }

                // 首付(二手房筛选)
                if (!TextUtils.isEmpty(etFirstPayAmountMin.getText())
                        && !TextUtils.isEmpty(etFirstPayAmountMax.getText())) {
                    // 筛选首付范围
                    float minPrice = Float.parseFloat(etFirstPayAmountMin.getText().toString().trim());
                    float maxPrice = Float.parseFloat(etFirstPayAmountMax.getText().toString().trim());
                    if (minPrice > maxPrice) {
                        ToastUtil.Short("最低首付必须小于或等于最高首付！");
                        return;
                    } else {
                        resultMap.put("minFirstPayAmount", etFirstPayAmountMin.getText().toString().trim()); // 最低首付
                        resultMap.put("maxFirstPayAmount", etFirstPayAmountMax.getText().toString().trim()); // 最高首付
                        resultAllSize += 1;
                        resultMap.put("CurrentName", etFirstPayAmountMin.getText().toString().trim() + "万" + "~" + etFirstPayAmountMax.getText().toString().trim() + "万"); // 当前点击的内容
                    }
                } else if (!TextUtils.isEmpty(etFirstPayAmountMin.getText()) && TextUtils.isEmpty(etFirstPayAmountMax.getText())) {
                    // 只筛选最小首付
                    resultMap.put("minFirstPayAmount", etFirstPayAmountMin.getText().toString().trim()); // 最低首付
                    resultMap.remove("maxFirstPayAmount");
                    resultAllSize += 1;
                    resultMap.put("CurrentName", etFirstPayAmountMin.getText().toString().trim() + "万以上"); // 当前点击的内容
                } else if (!TextUtils.isEmpty(etFirstPayAmountMax.getText()) && TextUtils.isEmpty(etFirstPayAmountMin.getText())) {
                    // 只筛选最大首付
                    resultMap.put("maxFirstPayAmount", etFirstPayAmountMax.getText().toString().trim()); // 最高首付
                    resultMap.remove("minFirstPayAmount");
                    resultAllSize += 1;
                    resultMap.put("CurrentName", etFirstPayAmountMax.getText().toString().trim() + "万以下"); // 当前点击的内容
                } else {
                    // 不筛选首付
                    resultMap.remove("minFirstPayAmount");
                    resultMap.remove("maxFirstPayAmount");
                }



                // 房源质量分
                if (!TextUtils.isEmpty(etscoreMin.getText().toString().trim())
                        && !TextUtils.isEmpty(etscoreMax.getText().toString().trim())) {
                    // 房源质量分范围
                    int minPrice = Integer.parseInt(etscoreMin.getText().toString().trim());
                    int maxPrice =Integer.parseInt(etscoreMax.getText().toString().trim());
                    if (minPrice > maxPrice) {
                        ToastUtil.Short("最低质量分必须小于或等于最高质量分！");
                        return;
                    } else {
                        resultMap.put("gtQualityScore", etscoreMin.getText().toString().trim()); // 最低质量分
                        resultMap.put("ltQualityScore", etscoreMax.getText().toString().trim()); // 最高质量分
                        resultAllSize += 1;
                        resultMap.put("CurrentName", etscoreMin.getText().toString().trim()  + "~" + etscoreMax.getText().toString().trim() + "分"); // 当前点击的内容
                    }
                } else if (!TextUtils.isEmpty(etscoreMin.getText()) && TextUtils.isEmpty(etscoreMax.getText())) {
                  //   最小房源质量
                    resultMap.put("gtQualityScore", etscoreMin.getText().toString().trim()); // 最低质量分
                    resultMap.remove("ltQualityScore");
                    resultAllSize += 1;
                    resultMap.put("CurrentName", etscoreMin.getText().toString().trim() + "以上"); // 当前点击的内容
                } else if (!TextUtils.isEmpty(etscoreMax.getText()) && TextUtils.isEmpty(etscoreMin.getText())) {
                    // 只筛选最大房源质量
                    resultMap.put("ltQualityScore", etscoreMax.getText().toString().trim()); // 最高首付
                    resultMap.remove("gtQualityScore");
                    resultAllSize += 1;
                    resultMap.put("CurrentName", etscoreMax.getText().toString().trim() + "以下"); // 当前点击的内容
                } else {
                  //   不筛选房源质量分
                    resultMap.remove("ltQualityScore");
                    resultMap.remove("gtQualityScore");
                }


                // 建筑年代
                if (!TextUtils.isEmpty(etbuildageMin.getText().toString().trim())
                        && !TextUtils.isEmpty(etbuildageMax.getText().toString().trim())) {
                    // 房源建筑年代范围
                    int minPrice = Integer.parseInt(etbuildageMin.getText().toString().trim());
                    int maxPrice =Integer.parseInt(etbuildageMax.getText().toString().trim());
                    if (minPrice > maxPrice) {
                        ToastUtil.Short("最早建筑年代必须小于或等于最近建筑年代！");
                        return;
                    } else {
                        resultMap.put("startBuildingAge", etbuildageMin.getText().toString().trim()); // 最低建筑年代
                        resultMap.put("endBuildingAge", etbuildageMax.getText().toString().trim()); // 最高建筑年代
                        resultAllSize += 1;
                        resultMap.put("CurrentName", etbuildageMin.getText().toString().trim()  + "~" + etbuildageMax.getText().toString().trim() + "年"); // 当前点击的内容
                    }
                } else if (!TextUtils.isEmpty(etbuildageMin.getText()) && TextUtils.isEmpty(etscoreMax.getText())) {
                    //   最小房源建筑年代
                    resultMap.put("startBuildingAge", etbuildageMin.getText().toString().trim()); // 最低建筑年代
                    resultMap.remove("endBuildingAge");
                    resultAllSize += 1;
                    resultMap.put("CurrentName", etbuildageMin.getText().toString().trim() + "以上"); // 当前点击的内容
                } else if (!TextUtils.isEmpty(etbuildageMax.getText()) && TextUtils.isEmpty(etbuildageMin.getText())) {
                    // 只筛选最大房源建筑年代
                    resultMap.put("endBuildingAge", etbuildageMax.getText().toString().trim()); // 最高首付
                    resultMap.remove("startBuildingAge");
                    resultAllSize += 1;
                    resultMap.put("CurrentName", etbuildageMax.getText().toString().trim() + "以下"); // 当前点击的内容
                } else {
                    //   不筛选房源建筑年代
                    resultMap.remove("startBuildingAge");
                    resultMap.remove("endBuildingAge");
                }
                //获取房源来源标签
                String str="";
                ArrayList<Integer>housesoureids=new ArrayList<>();
                for (int i = 0; i < houseSourceBeans.size(); i++) {
                    if(houseSourceBeans.get(i).isIscheck()){
                        housesoureids.add(Integer.parseInt(houseSourceBeans.get(i).getId()));
                        resultAllSize += 1;
                        if(str.equals("")){
                            str=str+houseSourceBeans.get(i).getName();
                        }else {
                            str=str+","+houseSourceBeans.get(i).getName();
                        }
                    }
                }
                if(!TextUtils.isEmpty(str)){
                    int size =housesoureids.size();
                    Integer[] arr =(Integer[])housesoureids.toArray(new Integer[size]);
                    resultMap.put("sourceType",arr);//来源
                    resultMap.put("CurrentName", str); // 当前点击的内容
                }else {
                    resultMap.remove("sourceType");
                }



                // 遍历获取所有标签，取出选中的值
                List<String> propertyLevels = new ArrayList<>(); // 存储公共房源类型选择的内容
                List<String> queryTypes = new ArrayList<>(); // 存储房屋用途选择的内容
                List<String> marks = new ArrayList<>(); // 存储房源标记选择的内容
                List<String> rooms = new ArrayList<>(); // 存储房型选择的内容
                List<String> decorationTypes = new ArrayList<>(); // 存储装修选择的内容
                List<String> floors = new ArrayList<>(); // 存储楼层选择的内容
                List<String> elevators = new ArrayList<>(); // 存储电梯选择的内容
                List<Integer> tags = new ArrayList<>(); // 存储标签选择的内容
                List<String> status = new ArrayList<>(); // 存储状态选择的内容

                for (MoreFilterSectionData child : mFilterGroupData) {
                    // 过滤掉组标题
                    if (!child.isHeader) {
                        // 公共房源(多选)
                        if ("公共房源".equals(child.t.getGroupName())) {
                            if (child.t.isSelected()) {
                                // 选中
                                String key = child.t.getName();
                                String value = child.t.getValue();
                                propertyLevels.add(value);
                                resultAllSize += 1;
                                resultMap.put("CurrentName", key); // 当前点击的内容
                            }

                            if (propertyLevels.isEmpty()) {
                                resultMap.remove("propertyLevel");
                            } else {
                                resultMap.put("propertyLevel", join(propertyLevels, ","));
                            }
                        }

                        // 房屋用途(多选)
                        if ("房屋用途".equals(child.t.getGroupName())) {
                            if (child.t.isSelected()) {
                                // 选中
                                String key = child.t.getName();
                                String value = child.t.getValue();

                                queryTypes.add(value);
                                resultAllSize += 1;
                                resultMap.put("CurrentName", key); // 当前点击的内容
                            }

                            if (queryTypes.isEmpty()) {
                                resultMap.remove("queryType");
                            } else {
                                resultMap.put("queryType", join(queryTypes, ","));
                            }
                        }

                        // 状态(多选)
                        if ("状态".equals(child.t.getGroupName())) {
                            if (child.t.isSelected()) {
                                // 选中
                                status.add(child.t.getValue());
                                resultAllSize += 1;
                                resultMap.put("CurrentName", child.t.getName()); // 当前点击的内容
                            }

                            if (status.isEmpty()) {
                                resultMap.remove("status");
                            } else {
                                resultMap.put("status", join(status, ","));
                            }
                        }

                        // 房源标记(多选)
                        if ("房源标记".equals(child.t.getGroupName())) {
                            if (child.t.isSelected()) {
                                // 选中
                                marks.add(child.t.getValue());
                                resultAllSize += 1;
                                resultMap.put("CurrentName", child.t.getName()); // 当前点击的内容
                            }

                            if (marks.isEmpty()) {
                                resultMap.remove("markCategory");
                            } else {
                                resultMap.put("markCategory", join(marks, ","));
                            }
                        }

                        // 房型(多选)
                        if ("房型".equals(child.t.getGroupName())) {
                            if (child.t.isSelected()) {
                                // 选中
                                rooms.add(child.t.getValue());
                                resultAllSize += 1;
                                resultMap.put("CurrentName", child.t.getName()); // 当前点击的内容
                            }

                            if (rooms.isEmpty()) {
                                resultMap.remove("bedroom");
                            } else {
                                resultMap.put("bedroom", join(rooms, ","));
                            }
                        }

                        // 装修(多选)
                        if ("装修".equals(child.t.getGroupName())) {
                            if (child.t.isSelected()) {
                                // 选中
                                decorationTypes.add(child.t.getValue());
                                resultAllSize += 1;
                                resultMap.put("CurrentName", child.t.getName()); // 当前点击的内容
                            }

                            if (decorationTypes.isEmpty()) {
                                resultMap.remove("decorationSituation");
                            } else {
                                resultMap.put("decorationSituation", join(decorationTypes, ","));
                            }
                        }

                        // 电梯(多选)
                        if ("电梯".equals(child.t.getGroupName())) {
                            if (child.t.isSelected()) {
                                // 选中
                                elevators.add(child.t.getValue());
                                resultAllSize += 1;
                                resultMap.put("CurrentName", child.t.getName()); // 当前点击的内容
                            }

                            if (elevators.isEmpty()) {
                                resultMap.remove("whetherElevators");
                            } else {
                                resultMap.put("whetherElevators", join(elevators, ","));
                            }
                        }

                        // 楼层(多选)
                        if ("楼层".equals(child.t.getGroupName())) {
                            if (child.t.isSelected()) {
                                // 选中
                                floors.add(child.t.getValue());
                                resultAllSize += 1;
                                resultMap.put("CurrentName", child.t.getName()); // 当前点击的内容
                            }

                            if (floors.isEmpty()) {
                                resultMap.remove("floorType");
                            } else {
                                resultMap.put("floorType", join(floors, ","));
                            }
                        }

                        // 标签(多选)
                        if ("标签".equals(child.t.getGroupName())) {
                            if (child.t.isSelected()) {
                                // 选中
                                tags.add(Integer.parseInt(child.t.getValue()));
                                resultAllSize += 1;
                                resultMap.put("CurrentName", child.t.getName()); // 当前点击的内容
                            }

                            int sum = 0;
                            for (Integer value : tags) {
                                sum += value;
                            }

                            if (tags.isEmpty()) {
                                resultMap.remove("propertyTags");
                            } else {
                                resultMap.put("propertyTags", sum); // 标签筛选需要将所选码值相加
                            }
                        }

                    }


                }


                // KLog.e("MoreFilter" +  resultAllSize);
                resultMap.put("AllSize", resultAllSize); // 当前已选总数

                if (resultMap.isEmpty()) {
                    onFilterResultListener.onResult(MoreHouseFilterContainer.this, null);
                } else {
                    onFilterResultListener.onResult(MoreHouseFilterContainer.this, resultMap);
                }
            }
            close(); // 关闭筛选容器
        });

    }

    public void setBtnReset() {
        if (tvStartTime == null || tvEndTime == null) return;

        tvStartTime.setText("");
        tvEndTime.setText("");

        // 清空文本输入框框筛选的数据
        clearAllEditText(super.getContentView());

        // 清空选中状态
        List<MoreFilterSectionData> childs = mFilterGroupData;
        for (MoreFilterSectionData child : childs) {
            if (!child.isHeader) {
                child.t.setSelected(false);

                // 房屋用途重置为第一条选中(默认普通住宅)
//                    if ("房屋用途".equals(child.t.getGroupName()) && "普通住宅".equals(child.t.getName())) {
//                        child.t.setSelected(true);
//                    }
            }
        }
        mSectionAdapter.notifyDataSetChanged();
        for (int i = 0; i < houseSourceBeans.size(); i++) {
            houseSourceBeans.get(i).setIscheck(false);
        }
        setFilterSourceData(houseSourceBeans);
        tvsourcename.setText("");
    }

    /**
     * 设置一些默认的房源筛选数据，这样调用层不需要每次都自己设置
     *
     * @param type 1二手房 2租房
     */
    private List<MoreFilterSectionData> getSampleData(int type) {
        List<MoreFilterSectionData> list = new ArrayList<>();
        list.add(new MoreFilterSectionData(true, "房屋用途"));

        // 房屋用途
        list.add(new MoreFilterSectionData(new MoreFilterChildData("普通住宅", "1", true, "房屋用途")));
        list.add(new MoreFilterSectionData(new MoreFilterChildData("别墅", "2", true, "房屋用途")));
        list.add(new MoreFilterSectionData(new MoreFilterChildData("商住两用", "3", true, "房屋用途")));
        list.add(new MoreFilterSectionData(new MoreFilterChildData("车位", "4", true, "房屋用途")));
        list.add(new MoreFilterSectionData(new MoreFilterChildData("商铺", "5", true, "房屋用途")));
        list.add(new MoreFilterSectionData(new MoreFilterChildData("写字楼", "6", true, "房屋用途")));
        list.add(new MoreFilterSectionData(new MoreFilterChildData("厂房", "7", true, "房屋用途")));
        list.add(new MoreFilterSectionData(new MoreFilterChildData("土地", "8", true, "房屋用途")));

        // 房源状态
        list.add(new MoreFilterSectionData(true, "状态"));
        list.add(new MoreFilterSectionData(new MoreFilterChildData("上架", "1", true, "状态")));
        list.add(new MoreFilterSectionData(new MoreFilterChildData("下架", "2", true, "状态")));
        list.add(new MoreFilterSectionData(new MoreFilterChildData("已成交", "3", true, "状态")));
        list.add(new MoreFilterSectionData(new MoreFilterChildData("暂缓", "4", true, "状态")));
        list.add(new MoreFilterSectionData(new MoreFilterChildData("无效", "5", true, "状态")));

        // 房源标记
        list.add(new MoreFilterSectionData(true, "房源标记"));
        Map<String, String> decorations = DictionaryHelp.getMapByCode(BaseCommon.MARK_CATEGORY);
        if (decorations != null && decorations.size() > 0) {
            for (String key : decorations.keySet()) {
                if (!TextUtils.isEmpty(key)) {
                    list.add(new MoreFilterSectionData(new MoreFilterChildData( key,decorations.get(key),true,"房源标记")));
                }
            }
        }

        // 房型
        list.add(new MoreFilterSectionData(true, "房型"));
        list.add(new MoreFilterSectionData(new MoreFilterChildData("一室", "1", true, "房型")));
        list.add(new MoreFilterSectionData(new MoreFilterChildData("二室", "2", true, "房型")));
        list.add(new MoreFilterSectionData(new MoreFilterChildData("三室", "3", true, "房型")));
        list.add(new MoreFilterSectionData(new MoreFilterChildData("四室", "4", true, "房型")));
        list.add(new MoreFilterSectionData(new MoreFilterChildData("五室", "5", true, "房型")));
        list.add(new MoreFilterSectionData(new MoreFilterChildData("五室以上", "6", true, "房型")));

        // 装修(从字典获取)
        List<DictionaryBean> dictionaryBeanList = DictionaryHelp.getDictionaryByCode(BaseCommon.DECORATION_TYPE);
        if (dictionaryBeanList != null && !dictionaryBeanList.isEmpty()) {
            list.add(new MoreFilterSectionData(true, "装修"));
            int size = dictionaryBeanList.get(0).getDictDataDtos().size();
            for (int i = 0; i < size; i++) {
                // 遍历添加到筛选组件
                MoreFilterChildData childData = new MoreFilterChildData();
                childData.setName(dictionaryBeanList.get(0).getDictDataDtos().get(i).getDictDataName());
                childData.setValue(dictionaryBeanList.get(0).getDictDataDtos().get(i).getVal());
                childData.setMultiple(true); // 支持多选
                childData.setGroupName("装修"); // 所属组名
                list.add(new MoreFilterSectionData(childData));
            }
        }

        // 电梯
        list.add(new MoreFilterSectionData(true, "电梯"));
        list.add(new MoreFilterSectionData(new MoreFilterChildData("有电梯", "1", true, "电梯")));
        list.add(new MoreFilterSectionData(new MoreFilterChildData("无电梯", "0", true, "电梯")));

        // 标签(从字典获取) 售房ub_houselabel 租房ub_renthouselabel
        List<DictionaryBean> tagBeanList;
        if (type == 2) {
            // 租房标签
            tagBeanList = DictionaryHelp.getDictionaryByCode(BaseCommon.UB_HOUSE_LABLE_RENT);
        } else {
            // 二手房标签
            tagBeanList = DictionaryHelp.getDictionaryByCode(BaseCommon.UB_HOUSELABLE);
        }

        if (tagBeanList != null && !tagBeanList.isEmpty()) {
            list.add(new MoreFilterSectionData(true, "标签"));
            int size = tagBeanList.get(0).getDictDataDtos().size();
            for (int i = 0; i < size; i++) {
                // 遍历添加到筛选组件
                MoreFilterChildData childData = new MoreFilterChildData();
                childData.setName(tagBeanList.get(0).getDictDataDtos().get(i).getDictDataName());
                childData.setValue(tagBeanList.get(0).getDictDataDtos().get(i).getVal());
                childData.setMultiple(true); // 支持多选
                childData.setGroupName("标签"); // 所属组名
                list.add(new MoreFilterSectionData(childData));
            }
        }


        return list;
    }

    /**
     * 异步加载
     *
     * @param type     1二手房 2租房
     * @param listener 加载回调
     */
    public void getAsyncData(int type, LoadListener listener) {
        filterType = type;
        Observable.create((ObservableOnSubscribe<List<MoreFilterSectionData>>) emitter -> {
            List<MoreFilterSectionData> listData = new ArrayList<>();
            listData = getSampleData(type);

            emitter.onNext(listData);
            emitter.onComplete();
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<MoreFilterSectionData>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        listener.addDisposable(d);
                    }

                    @Override
                    public void onNext(List<MoreFilterSectionData> dataList) {
                        // 设置数据
                        if (dataList != null && !dataList.isEmpty()) {
                            setFilterData(dataList);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.loadFailed(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 设置数据
     */
    public void setFilterData(List<MoreFilterSectionData> filterData) {
        View priceBody = findViewById(R.id.base_filter_ll_dj_sf);
        if (filterType == 1) {
            // 二手房筛选显示底价和首付
            priceBody.setVisibility(VISIBLE);
        } else {
            // 租房隐藏底价和首付
            priceBody.setVisibility(GONE);
        }
        if (filterData != null) {
            this.mFilterGroupData.clear();
            this.mFilterGroupData = filterData;
            if (mSectionAdapter != null) {
                mSectionAdapter.setNewData(filterData);
            }
        }
    }

    /**
     * 设置数据
     */
    public void setFilterSourceData(List<HouseSourceBean> houseSourceBeans) {
        this.houseSourceBeans=houseSourceBeans;
       String str="";
        for (int i = 0; i < houseSourceBeans.size(); i++) {
            if(houseSourceBeans.get(i).isIscheck()){
                if(str.equals("")){
                    str=str+houseSourceBeans.get(i).getName();
                }else {
                    str=str+","+houseSourceBeans.get(i).getName();
                }
            }
        }
            tvsourcename.setText(str);



    }


    /**
     * 设置数据
     */
    public ArrayList<HouseSourceBean> getFilterSourceData() {
        if(houseSourceBeans!=null&&houseSourceBeans.size()>0){
            return (ArrayList)houseSourceBeans;
        }else {
            List<HouseSourceBean> houseSourceBeans=new ArrayList<>();
            Map<String, String> decorations = DictionaryHelp.getMapByCode(BaseCommon.PROPERTY_SOURCE);
            if (decorations != null && decorations.size() > 0) {
                for (String key : decorations.keySet()) {
                    if (!TextUtils.isEmpty(key)) {
                        houseSourceBeans.add(new HouseSourceBean(key,decorations.get(key),false));
                    }
                }
            }
            return (ArrayList)houseSourceBeans;
        }

    }
    /**
     * 设置是否为公共房源
     *
     * @param pubHouse
     */
    public void setPubHouse(boolean pubHouse) {
        isPubHouse = pubHouse;
        if (isPubHouse) {
            TextView tvAgent = findViewById(R.id.base_filter_tv_house_agent);
            EditText etHouseAgent = findViewById(R.id.base_filter_et_house_agent);

            tvAgent.setVisibility(GONE);
            etHouseAgent.setVisibility(GONE);
        }
    }
    /**
     * 设置是否隐藏房源质量分搜索
     */
    public void setHidequalityScore(boolean hide){
        if (!hide){
            findViewById(R.id.base_filter_tv_dp_score).setVisibility(GONE);
            findViewById(R.id.base_filter_lly_dp_score).setVisibility(GONE);

        }else {
            findViewById(R.id.base_filter_tv_dp_score).setVisibility(VISIBLE);
            findViewById(R.id.base_filter_lly_dp_score).setVisibility(VISIBLE);
        }
    }

    /**
     * 设置是否隐藏房源质量分搜索
     */
    public void setHidebuildage(boolean hide){
        if (!hide){
            findViewById(R.id.base_filter_tv_dp_buildage).setVisibility(GONE);
            findViewById(R.id.base_filter_ll_dp_buildage).setVisibility(GONE);

        }else {
            findViewById(R.id.base_filter_tv_dp_buildage).setVisibility(VISIBLE);
            findViewById(R.id.base_filter_ll_dp_buildage).setVisibility(VISIBLE);
        }
    }
    /**
     * 设置是否隐藏房源创建人搜索
     */
    public void setHideCreateAgent(boolean hide) {
        if (hide) {
            TextView tvCreateAgent = findViewById(R.id.base_filter_tv_house_agent_create);
            EditText etCreateAgent = findViewById(R.id.base_filter_et_house_agent_create);

            tvCreateAgent.setVisibility(GONE);
            etCreateAgent.setVisibility(GONE);
        }
    }
    /**
     * 是否隐藏房源来源筛选
     */
    public void hideHouseSourcer(boolean hide) {
        if (hide) {
            findViewById(R.id.base_filter_tv_house_source).setVisibility(VISIBLE);
            findViewById(R.id.base_filter_rly_house_source).setVisibility(VISIBLE);

        }
    }

    /**
     * 是否隐藏房源创建人筛选
     */
    public void hideCreateAgent(boolean hide) {
        if (hide) {
            TextView tvAgent = findViewById(R.id.base_filter_tv_house_agent_create);
            EditText etHouseAgent = findViewById(R.id.base_filter_et_house_agent_create);

            tvAgent.setVisibility(GONE);
            etHouseAgent.setVisibility(GONE);
        }
    }

    /**
     * 清除指定view内的所有文本框内容
     *
     * @param view
     */
    @Override
    protected void clearAllEditText(View view) {
        try {
            if (view == null) {
                return;
            }
            if (view instanceof ViewGroup) {

                ViewGroup viewGroup = (ViewGroup) view;
                final int len = viewGroup.getChildCount();
                for (int i = 0; i < len; i++) {
                    View child = viewGroup.getChildAt(i);
                    clearAllEditText(child);
                }
            } else if (view instanceof EditText) {
                EditText editText = (EditText) view;
                editText.setText("");
            } else {
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private OnResultListener onResultListener;

    public <T> void setOnResultListener(OnResultListener<T> onResultListener) {
        this.onResultListener = onResultListener;

    }

    public interface OnResultListener<T> {
        T onConvert(List<MoreFilterSectionData> data);

        void onResult(List<MoreFilterSectionData> data, T result);
    }

    // 扫一扫的点击事件
    public interface onQrScanClickListener {
        void onQrScanClick();
    }

    /**
     * 设置扫一扫的点击事件
     */
    public void setScanQrClickListener(onQrScanClickListener scanClickListener) {
        this.mScanClickListener = scanClickListener;
    }

    // 选择房屋来源点击事件
    public interface onSourceClickListener {
        void oSourceClick();
    }

    /**
     * 设置选择房屋来源点击事件
     */
    public void setSourceClickListener(onSourceClickListener scanClickListener) {
        this.msourceClickListener = scanClickListener;
    }
    public interface LoadListener {

        void addDisposable(Disposable disposable);

        void loadFailed(String errorMsg);
    }

}
