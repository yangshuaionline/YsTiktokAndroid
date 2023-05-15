package com.yangshuai.library.base.widget.filter;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yangshuai.lib.button.StateButton;
import com.yangshuai.library.base.R;
import com.yangshuai.library.base.entity.CityAreaData;
import com.yangshuai.library.base.entity.CityTradingAreaData;
import com.yangshuai.library.base.entity.SubwayLineData;
import com.yangshuai.library.base.entity.SubwayStationData;
import com.yangshuai.library.base.utils.Utils;
import com.yangshuai.library.base.view.recycleview.BaseRecycleAdapter;
import com.yangshuai.library.base.view.recycleview.BaseViewHolder;
import com.yangshuai.library.base.widget.filter.base.BaseFilterContainerView;
import com.yangshuai.library.base.widget.filter.model.RegionFilterData;

import org.litepal.LitePal;

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
 * 区域、地铁、附近筛选器(第三级别支持多选)
 *
 * @author hcp
 * @created 2019/12/5
 */
public class RegionMultipleFilterContainer extends BaseFilterContainerView {

    private RecyclerView recyclerView1, recyclerView2, recyclerView3; // 每一级的列表
    private RegionListAdapter listAdapter;

    private StateButton btnOk, btnReset; // 确定和重置按钮

    private int houseType;// 1二手房 2租房

    // 层级枚举
    enum Level {
        Level_1(1), Level_2(2), Level_3(3);
        public int value;

        Level(int value) {
            this.value = value;
        }
    }

    public RegionMultipleFilterContainer(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.RegionMultipleFilterContainer);
        houseType = typedArray.getInteger(R.styleable.RegionMultipleFilterContainer_regionHouseType, 0);
        typedArray.recycle();

    }

    @Override
    protected void onCreate() {
        super.onCreate();
        setContentView(R.layout.base_filter_region_multiple_container);
        setContainerHeight(Utils.dp2Px(getContext(), 300)); // 设置容器高度

        recyclerView1 = findViewById(R.id.base_filter_region_rv1);
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView2 = findViewById(R.id.base_filter_region_rv2);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView3 = findViewById(R.id.base_filter_region_rv3);
        recyclerView3.setHasFixedSize(true);
        recyclerView3.setLayoutManager(new LinearLayoutManager(getContext()));

        // 重置
        btnReset = findViewById(R.id.base_filter_more_btn_reset);
        btnReset.setOnClickListener(v -> {
            try {
                for (int i = 0; i < listAdapter.getDataList().size(); i++) {
                    if ("区域".equals(listAdapter.getDataList().get(i).getType())) {
                        listAdapter.getDataList().get(i).setSelect(true);
                    } else {
                        listAdapter.getDataList().get(i).setSelect(false);
                    }

                    // 让所有类型的2级列表都选中不限
                    if (listAdapter.getDataList().get(i).getChilds() != null) {
                        int regionChildSize = listAdapter.getDataList().get(i).getChilds().size();
                        List<RegionFilterData> regionList = listAdapter.getDataList().get(i).getChilds();
                        for (int j = 0; j < regionChildSize; j++) {
                            if ("不限".equals(regionList.get(j).getName())) {
                                regionList.get(j).setSelect(true);
                            } else {
                                regionList.get(j).setSelect(false);
                            }
                        }
                    }
                }

                listAdapter.notifyDataSetChanged();

                setFilterData(listAdapter.getDataList());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // 确定
        btnOk = findViewById(R.id.base_filter_more_btn_ok);
        btnOk.setOnClickListener(v -> {
            try {
                Map<String, Object> resultMap = new HashMap<>();
                if (listAdapter == null || listAdapter.getDataList() == null) return;

                for (int i = 0; i < listAdapter.getDataList().size(); i++) {
                    // 先获取当前选中的1级
                    if (listAdapter.getDataList().get(i).isSelect()) {
                        switch (listAdapter.getDataList().get(i).getType()) {
                            case "区域":
                                // 获取区域选中的2级
                                if (listAdapter.getDataList().get(i).getChilds() != null) {
                                    int regionChildSize = listAdapter.getDataList().get(i).getChilds().size();
                                    List<RegionFilterData> regionList = listAdapter.getDataList().get(i).getChilds();
                                    for (int j = 0; j < regionChildSize; j++) {
                                        if (regionList.get(j).isSelect()) {
                                            if ("不限".equals(regionList.get(j).getName())) {
                                                // 不限直接移除所有区域、附近、地铁关联参数
                                                resultMap.remove("regionShowName");
                                                resultMap.remove("districtName"); // 区域名
                                                resultMap.remove("districtCode"); // 区域编号
                                                resultMap.remove("tradingAreaName"); // 商圈名
                                                resultMap.remove("tradingAreaIds"); // 商圈ID
                                                resultMap.remove("distance");
                                            } else {
                                                // 筛选区域
                                                // resultMap.put("districtName", regionList.get(j).getName());
                                                resultMap.put("regionShowName", regionList.get(j).getName()); // 显示到筛选条的名称
                                                resultMap.put("districtCode", regionList.get(j).getValue());
                                                resultMap.remove("tradingAreaName");
                                                resultMap.remove("tradingAreaIds");
                                                resultMap.remove("distance");


                                                // 获取对应区域内的商圈片区(如西乡塘区 → 西大)
                                                if (regionList.get(j).getChilds() != null) {
                                                    int businessSize = regionList.get(j).getChilds().size();
                                                    List<RegionFilterData> businessList = regionList.get(j).getChilds();
                                                    List<RegionFilterData> selectList = new ArrayList<>(); // 存储已选择的片区内容
                                                    List<String> selectIdList = new ArrayList<>(); // 存储已选择的片区ID
                                                    for (int k = 0; k < businessSize; k++) {
                                                        if (businessList.get(k).isSelect()) {
                                                            if ("不限".equals(businessList.get(k).getName())) {
                                                                // 商圈选择不限,则只传对应城区
                                                                // resultMap.put("districtName", businessList.get(k).getParent().getName()); // 区域名称
                                                                resultMap.put("districtCode", businessList.get(k).getParent().getValue()); // 区域ID
                                                                resultMap.put("regionShowName", businessList.get(k).getParent().getName()); // 显示到筛选条的名称

                                                                // resultMap.remove("tradingAreaName"); // 商圈名
                                                                resultMap.remove("tradingAreaIds"); // 商圈ID
                                                                resultMap.remove("distance"); // 距离
                                                            } else {
                                                                selectList.add(businessList.get(k));
                                                                selectIdList.add(businessList.get(k).getValue());
                                                            }
                                                        }
                                                    }

                                                    if (!selectList.isEmpty()) {
                                                        if (selectList.size() > 1) {
                                                            resultMap.put("regionShowName", "多选");// 显示到筛选条的名称
                                                        } else {
                                                            // 只选了一条
                                                            resultMap.put("regionShowName", selectList.get(0).getName()); // 显示到筛选条的名称
                                                        }

                                                        // 商圈需要和上级城区也一起传
                                                        // resultMap.put("districtName", selectList.get(0).getParent().getName()); // 城区名称
                                                        resultMap.put("districtCode", selectList.get(0).getParent().getValue()); // 城区id


                                                        // resultMap.put("tradingAreaName", filterData.getName()); // 商圈名
                                                        resultMap.put("tradingAreaIds", join(selectIdList, ",")); // 商圈ID

                                                        resultMap.remove("distance");
                                                    } else {
                                                        // 如果没有选商圈，也直接传城区
                                                        resultMap.put("districtCode", regionList.get(j).getValue()); // 区域ID
                                                        resultMap.put("regionShowName", regionList.get(j).getName()); // 显示到筛选条的名称

                                                        // resultMap.remove("tradingAreaName"); // 商圈名
                                                        resultMap.remove("tradingAreaIds"); // 商圈ID
                                                        resultMap.remove("distance"); // 距离

                                                    }

                                                }
                                            }

                                        }
                                    }
                                }

                                break;
                            case "地铁":
                                // 获取地铁选中的2级
                                if (listAdapter.getDataList().get(i).getChilds() != null) {
                                    int subwayChildSize = listAdapter.getDataList().get(i).getChilds().size();
                                    List<RegionFilterData> subwayList = listAdapter.getDataList().get(i).getChilds();
                                    for (int j = 0; j < subwayChildSize; j++) {
                                        if (subwayList.get(j).isSelect()) {
                                            if ("不限".equals(subwayList.get(j).getName())) {
                                                // 不限直接移除所有区域、附近、地铁关联参数
                                                resultMap.remove("regionShowName");
                                                resultMap.remove("districtCode"); // 区域编号
                                                resultMap.remove("tradingAreaIds"); // 商圈ID
                                                resultMap.remove("distance"); // 距离
                                                resultMap.remove("subwayIds"); // 地铁线路ID
                                                resultMap.remove("subwayStationIds"); // 地铁站点ID
                                            } else {
                                                // 筛选地铁
                                                resultMap.put("regionShowName", subwayList.get(j).getName()); // 显示到筛选条的名称
                                                resultMap.put("subwayIds", subwayList.get(j).getValue());
                                                resultMap.remove("districtCode");
                                                resultMap.remove("tradingAreaIds");
                                                resultMap.remove("distance");
                                                resultMap.remove("subwayStationIds");


                                                // 获取对应地铁线内的站点(如1号线 → 广西大学站)
                                                if (subwayList.get(j).getChilds() != null) {
                                                    int businessSize = subwayList.get(j).getChilds().size();
                                                    List<RegionFilterData> businessList = subwayList.get(j).getChilds();
                                                    List<RegionFilterData> selectList = new ArrayList<>(); // 存储已选择的站点内容
                                                    List<String> selectIdList = new ArrayList<>(); // 存储已选择的站点ID
                                                    for (int k = 0; k < businessSize; k++) {
                                                        if (businessList.get(k).isSelect()) {
                                                            if ("不限".equals(businessList.get(k).getName())) {
                                                                // 站点选择不限,则只传对应线路
                                                                resultMap.put("subwayIds", businessList.get(k).getParent().getValue()); // 线路ID
                                                                resultMap.put("regionShowName", businessList.get(k).getParent().getName()); // 显示到筛选条的名称

                                                                resultMap.remove("districtCode");// 城区ID
                                                                resultMap.remove("tradingAreaIds"); // 商圈ID
                                                                resultMap.remove("distance"); // 距离
                                                                resultMap.remove("subwayStationIds"); // 站点ID
                                                            } else {
                                                                selectList.add(businessList.get(k));
                                                                selectIdList.add(businessList.get(k).getValue());
                                                            }
                                                        }
                                                    }

                                                    if (!selectList.isEmpty()) {
                                                        if (selectList.size() > 1) {
                                                            resultMap.put("regionShowName", "多选");// 显示到筛选条的名称
                                                        } else {
                                                            // 只选了一条
                                                            resultMap.put("regionShowName", selectList.get(0).getName()); // 显示到筛选条的名称
                                                        }

                                                        // 站点需要和上级线路也一起传
                                                        resultMap.put("subwayIds", selectList.get(0).getParent().getValue()); // 线路ID
                                                        resultMap.put("subwayStationIds", join(selectIdList, ",")); // 站点ID

                                                        resultMap.remove("districtCode"); // 城区ID
                                                        resultMap.remove("tradingAreaIds"); // 商圈ID
                                                        resultMap.remove("distance"); // 距离


                                                    } else {
                                                        // 如果没有选站点，也直接传线路
                                                        resultMap.put("subwayIds", subwayList.get(j).getValue()); // 线路ID
                                                        resultMap.put("regionShowName", subwayList.get(j).getName()); // 显示到筛选条的名称

                                                        resultMap.remove("districtCode");
                                                        resultMap.remove("tradingAreaIds"); // 商圈ID
                                                        resultMap.remove("distance"); // 距离
                                                        resultMap.remove("subwayStationIds");

                                                    }

                                                }
                                            }

                                        }
                                    }
                                }
                                break;
                            case "附近":
                                // 获取附近选中的2级
                                if (listAdapter.getDataList().get(i).getChilds() != null) {
                                    int nearbyChildSize = listAdapter.getDataList().get(i).getChilds().size();
                                    List<RegionFilterData> nearbyList = listAdapter.getDataList().get(i).getChilds();
                                    for (int j = 0; j < nearbyChildSize; j++) {
                                        if (nearbyList.get(j).isSelect()) {
                                            if ("不限".equals(nearbyList.get(j).getName())) {
                                                // 不限直接移除所有区域、附近、地铁关联参数
                                                resultMap.remove("regionShowName");
                                                resultMap.remove("distance"); // 距离

                                                resultMap.remove("districtName"); // 区域名
                                                resultMap.remove("districtCode"); // 区域编号
                                                resultMap.remove("tradingAreaName"); // 商圈名
                                                resultMap.remove("tradingAreaIds"); // 商圈ID
                                            } else {
                                                resultMap.put("regionShowName", nearbyList.get(j).getName()); // 显示到筛选条的名称
                                                String km = nearbyList.get(j).getName().replace("km", ""); // 去掉单位
                                                int m = Integer.parseInt(km) * 1000; // 千米转换为米
                                                resultMap.put("distance", String.valueOf(m));

                                                resultMap.remove("districtName"); // 区域名
                                                resultMap.remove("districtCode"); // 区域编号
                                                resultMap.remove("tradingAreaName"); // 商圈名
                                                resultMap.remove("tradingAreaIds"); // 商圈ID
                                            }
                                        }
                                    }
                                }
                                break;
                        }
                    }

                }

//            KLog.e("RegionMultipleFilter" + resultMap);

                if (onFilterResultListener != null) {
                    onFilterResultListener.onResult(this, resultMap);
                }
                close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }

    /**
     * 异步获取区域筛选数据(跟setFilterData两者只能调用一个，建议使用异步方法，避免数据过多阻塞UI)
     */
    public void getAsyncRegionData(String cityCode, LoadListener listener) {
        Observable.create((ObservableOnSubscribe<List<RegionFilterData>>) emitter -> {
            List<RegionFilterData> listData = new ArrayList<>();

            // 区域
            RegionFilterData area = new RegionFilterData("区域", "区域", true);

            // 读取ORM数据库中的城区数据
            List<CityAreaData> cityAreaData;

            if (houseType == 1) {
                cityAreaData = LitePal
                        .where("cityCode=? and isEsf = ?", cityCode, "1")
                        .find(CityAreaData.class);
            } else if (houseType == 2) {
                cityAreaData = LitePal
                        .where("cityCode=? and isRent = ?", cityCode, "1")
                        .find(CityAreaData.class);
            } else {
                cityAreaData = LitePal
                        .where("cityCode=?", cityCode)
                        .find(CityAreaData.class);
            }

            List<RegionFilterData> regionChild = new ArrayList<>();
            if (cityAreaData.isEmpty()) {
                // 如果没有城区数据，增加一条“不限”
                RegionFilterData region = new RegionFilterData("不限", "区域", true);
                regionChild.add(region);
            } else {
                int areaSize = cityAreaData.size();
                for (int i = 0; i < areaSize; i++) {
                    if (i == 0) {
                        // 第一条数据添加个默认的子级
                        RegionFilterData region = new RegionFilterData("不限", "区域", true);
                        regionChild.add(region);
                    }

                    RegionFilterData region = new RegionFilterData("区域", false);
                    region.setName(cityAreaData.get(i).getDistrictName()); // 城区名称
                    region.setValue(cityAreaData.get(i).getDistrictCode()); // 城区code(筛选的时候传这个)
                    regionChild.add(region);

                    // 片区
                    List<RegionFilterData> sectionChild = new ArrayList<>();
                    // 通过城区编号districtCode，查询对应的商圈(片区)
                    List<CityTradingAreaData> section;
                    if (houseType == 1) {
                        section = LitePal
                                .where("districtCode=? and isEsf = ?", cityAreaData.get(i).getDistrictCode(), "1")
                                .find(CityTradingAreaData.class);
                    } else if (houseType == 2) {
                        section = LitePal
                                .where("districtCode=? and isRent = ?", cityAreaData.get(i).getDistrictCode(), "1")
                                .find(CityTradingAreaData.class);
                    } else {
                        section = LitePal
                                .where("districtCode=?", cityAreaData.get(i).getDistrictCode())
                                .find(CityTradingAreaData.class);
                    }
                    if (section.isEmpty()) {
                        // 如果没有商圈数据，增加一条“不限”
//                    RegionFilterData sectionData = new RegionFilterData("不限", "片区", true);
//                    sectionChild.add(sectionData);
                    } else {
                        int sectionSize = section.size();
                        for (int j = 0; j < sectionSize; j++) {
                            if (j == 0) {
                                // 第一条数据添加个默认的子级(不限)
                                RegionFilterData sectionData = new RegionFilterData("不限", "片区", true);

                                // 设置不限对应的父级区域数据(避免点击不限的时候会闪退)
                                RegionFilterData parent = new RegionFilterData();
                                parent.setName(cityAreaData.get(i).getDistrictName());
                                parent.setValue(cityAreaData.get(i).getDistrictCode());
                                sectionData.setParent(parent);
                                sectionChild.add(sectionData);

                            }
                            RegionFilterData s = new RegionFilterData("片区", false);
                            s.setName(section.get(j).getTradingAreaName()); // 商圈名称
                            s.setValue(section.get(j).getTradingAreaId()); // 商圈ID(筛选的时候传这个)
                            s.setShowCheckBox(true);
                            s.setParent(region); // 设置所属父级
                            sectionChild.add(s);
                        }
                    }

                    region.setChilds(sectionChild); // 设置区域的子级(片区)
                    region.setParent(new RegionFilterData("", "区域", false)); // 给区域也设置一个空父级，避免数据错乱
                }
            }
            area.setChilds(regionChild);
            listData.add(area);

            // 地铁
            RegionFilterData metro = new RegionFilterData("地铁", "地铁", false);
            // 读取ORM数据库保存的地铁线路数据(只查询已开通的、前端按照orderNum正序排序)
            List<SubwayLineData> metroData = LitePal
                    .where("cityCode=? AND isOpen = ?", cityCode, "1")
                    .order("orderNum asc")
                    .find(SubwayLineData.class);
            List<RegionFilterData> metroChild = new ArrayList<>();
            if (metroData.isEmpty()) {
                // 该城市没有地铁数据
                RegionFilterData data = new RegionFilterData("不限", "地铁", true);
                metroChild.add(data);
            } else {
                int metroSize = metroData.size();
                for (int i = 0; i < metroSize; i++) {
                    if (i == 0) {
                        // 第一条数据添加个默认的子级
                        RegionFilterData data = new RegionFilterData("不限", "地铁", true);
                        metroChild.add(data);
                    }

                    RegionFilterData data = new RegionFilterData("地铁", false);
                    data.setName(metroData.get(i).getSubwayName()); // 线路名称
                    data.setValue(metroData.get(i).getSubwayId()); // 线路UUID(筛选的时候传这个)
                    metroChild.add(data);

                    // 站点
                    List<RegionFilterData> sectionChild = new ArrayList<>();
                    // 通过线路ID:subwayId，查询对应的站点(只查询已开通的)
                    List<SubwayStationData> section = LitePal
                            .where("subwayId=? AND isOpen = ?", metroData.get(i).getSubwayId(), "1")
                            .find(SubwayStationData.class);
                    if (!section.isEmpty()) {
                        int sectionSize = section.size();
                        for (int j = 0; j < sectionSize; j++) {
                            if (j == 0) {
                                // 第一条数据添加个默认的子级(不限)
                                RegionFilterData sectionData = new RegionFilterData("不限", "地铁站点", true);

                                // 设置不限对应的父级区域数据(避免点击不限的时候会闪退)
                                RegionFilterData parent = new RegionFilterData();
                                parent.setName(metroData.get(i).getSubwayName());
                                parent.setValue(metroData.get(i).getSubwayId());
                                sectionData.setParent(parent);
                                sectionChild.add(sectionData);

                            }
                            RegionFilterData s = new RegionFilterData("地铁站点", false);
                            s.setName(section.get(j).getStationName()); // 站点名称
                            s.setValue(section.get(j).getStationId()); // 站点UUID(筛选的时候传这个)
                            s.setShowCheckBox(true);
                            s.setParent(data); // 设置所属父级
                            sectionChild.add(s);
                        }
                    }

                    data.setChilds(sectionChild); // 设置线路的子级(站点)
                    data.setParent(new RegionFilterData("", "地铁", false)); // 给区域也设置一个空父级，避免数据错乱

                }
            }
            metro.setChilds(metroChild);
            listData.add(metro);

            // 附近
            RegionFilterData near = new RegionFilterData("附近", "附近", false);
            String nears[] = {"不限", "1km", "2km", "3km"};
            List<RegionFilterData> nearChild = new ArrayList<>();
            for (int i = 0; i < nears.length; i++) {
                if (nears[i].equals("不限")) {
                    // 第一条数据添加个默认的子级
                    RegionFilterData s = new RegionFilterData(nears[i], "附近", true);
                    nearChild.add(s);
                } else {
                    RegionFilterData s = new RegionFilterData(nears[i], "附近", false);
                    nearChild.add(s);
                }
            }
            near.setChilds(nearChild);
            listData.add(near);

            emitter.onNext(listData);
            emitter.onComplete();
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<RegionFilterData>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        listener.addDisposable(d);
                    }

                    @Override
                    public void onNext(List<RegionFilterData> regionFilterData) {
                        // 设置数据
                        if (regionFilterData != null && !regionFilterData.isEmpty()) {
                            setFilterData(regionFilterData);
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
     * 设置筛选数据(建议使用异步方法getAsyncRegionData)
     */
    public void setFilterData(List<RegionFilterData> data) {

        if (data != null && !data.isEmpty()) {

            listAdapter = new RegionListAdapter(getContext(), data);
            recyclerView1.setAdapter(listAdapter);

            // 默认显示第一项的二级列表(如：区域的片区)
            for (RegionFilterData filter : data) {
                if (filter.isSelect() && hasChilds(filter)) {
                    recyclerView2.setVisibility(VISIBLE);
                    recyclerView2.setAdapter(new RegionListAdapter(getContext(), filter.getChilds(), Level.Level_2));
                    recyclerView3.setVisibility(GONE);
                    break;
                }
            }
        }
    }

    /**
     * 判断是否有子级数据
     *
     * @param item
     * @return
     */
    private boolean hasChilds(RegionFilterData item) {
        return item.getChilds() != null && item.getChilds().size() > 0;
    }

    private class RegionListAdapter extends BaseRecycleAdapter<RegionFilterData> {

        private Level currentLevel = Level.Level_1;
        private int defaultColor; // 默认颜色
        private int selectedColor; // 选中颜色

        public RegionListAdapter(Context context, List<RegionFilterData> dataList) {
            super(context, dataList);
            this.defaultColor = ContextCompat.getColor(context, R.color.text);
            this.selectedColor = ContextCompat.getColor(context, R.color.theme);
        }

        public RegionListAdapter(Context context, List<RegionFilterData> dataList, Level currentLevel) {
            super(context, dataList);
            this.currentLevel = currentLevel;
            this.defaultColor = ContextCompat.getColor(context, R.color.text);
            this.selectedColor = ContextCompat.getColor(context, R.color.theme);
        }

        @Override
        protected int bindItemLayout() {
            return R.layout.base_filter_region_cell;
        }

        @Override
        protected void bindData(BaseViewHolder holder, RegionFilterData item, int position) {
            TextView titleView = holder.getView(R.id.base_filter_region_tv_title);
            CheckBox checkBox = holder.getView(R.id.base_filter_region_checkbox);

            // 设置显示名称
            titleView.setText(item.getName());

            // 设置是否显示选择框
            if (item.isShowCheckBox()) {
                checkBox.setVisibility(VISIBLE);
            } else {
                checkBox.setVisibility(GONE);
            }

            // 判断是否选中
            if (item.isSelect()) {
                titleView.setTextColor(selectedColor);  // 设置文本颜色状态
                checkBox.setChecked(true);
            } else {
                titleView.setTextColor(defaultColor);
                checkBox.setChecked(false);
            }

            // 点击事件
            titleView.setOnClickListener(v -> {
                // 点击后设置成选中
                setCheck(position, item);

                // 判断当前点击的层级
                if (currentLevel.value == Level.Level_1.value) {
                    // 1级
                    if (hasChilds(item)) {
                        // 判断当前类别是否有子级
                        recyclerView2.setVisibility(VISIBLE); // 显示第二级列表
                        List<RegionFilterData> childs = new ArrayList<>(); // 子级数据集
                        childs.addAll(item.getChilds());

                        recyclerView2.setAdapter(new RegionListAdapter(mContext, childs, Level.Level_2));
                        recyclerView3.setVisibility(GONE); // 隐藏第三级
                    } else {
                        // 没有子级，直接返回当前级别的参数
                        recyclerView2.setVisibility(GONE);
                        recyclerView3.setVisibility(GONE);
                    }
                } else if (currentLevel.value == Level.Level_2.value) {
                    // 2级
                    if (hasChilds(item)) {
                        recyclerView3.setVisibility(VISIBLE); // 显示第三级列表
                        List<RegionFilterData> childs = new ArrayList<>(); // 子级数据集
                        for (int i = 0; i < item.getChilds().size(); i++) {
                            if (i == 0) {
                                item.getChilds().get(i).setSelect(true);
                            } else {
                                // 清空之前选中状态
                                item.getChilds().get(i).setSelect(false);
                            }
                        }
                        childs.addAll(item.getChilds());

                        recyclerView3.setAdapter(new RegionListAdapter(mContext, childs, Level.Level_3));
                    } else {
                        recyclerView3.setVisibility(GONE);
                    }
                } else if (currentLevel.value == Level.Level_3.value) {
                    // 3级: 片区、地铁站点
                    if (item.isShowCheckBox() && !"不限".equals(item.getName())) {
                        dataList.get(0).setSelect(false); // 当选择了其他项时，取消选中“不限”
                        item.setSelect(!item.isSelect());
                        notifyDataSetChanged();
                    }
                }
            });

            checkBox.setOnClickListener(v -> {
                if (item.isShowCheckBox() && !"不限".equals(item.getName())) {
                    dataList.get(0).setSelect(false); // 当选择了其他项时，取消选中“不限”
                    item.setSelect(!item.isSelect());
                    notifyDataSetChanged();
                }
            });
        }

        // 设置某项选中
        private void setCheck(int position, RegionFilterData item) {
            if (item.isShowCheckBox()) return;
            for (int i = 0; i < dataList.size(); i++) {
                dataList.get(i).setSelect(position == i);
            }
            notifyDataSetChanged();
        }

        // 设置当前的级别
        public void setCurrentLevel(Level currentLevel) {
            this.currentLevel = currentLevel;
        }

        public List<RegionFilterData> getDataList() {
            return dataList;
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


    public interface LoadListener {

        void addDisposable(Disposable disposable);

        void loadFailed(String errorMsg);
    }
}
