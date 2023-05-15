package com.yangshuai.library.base.widget.filter;

import android.content.Context;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.yangshuai.library.base.R;
import com.yangshuai.library.base.entity.CityAreaData;
import com.yangshuai.library.base.entity.CityTradingAreaData;
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
 * 区域筛选器(单选)
 *
 * @author hcp
 * @created 2019/4/3
 * 多选请使用 -> {@link RegionMultipleFilterContainer}
 */
public class RegionFilterContainer extends BaseFilterContainerView {

    private RecyclerView recyclerView1, recyclerView2, recyclerView3; // 每一级的列表
    private RegionListAdapter listAdapter;
    private List<RegionFilterData> filterData;

    // 层级枚举
    enum Level {
        Level_1(1), Level_2(2), Level_3(3);
        public int value;

        Level(int value) {
            this.value = value;
        }
    }

    public RegionFilterContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        setContentView(R.layout.base_filter_region_container);
        setContainerHeight(Utils.dp2Px(getContext(), 233)); // 设置容器高度

        recyclerView1 = findViewById(R.id.base_filter_region_rv1);
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView2 = findViewById(R.id.base_filter_region_rv2);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView3 = findViewById(R.id.base_filter_region_rv3);
        recyclerView3.setHasFixedSize(true);
        recyclerView3.setLayoutManager(new LinearLayoutManager(getContext()));


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
            List<CityAreaData> cityAreaData = LitePal
                    .where("cityCode=?", cityCode)
                    .find(CityAreaData.class);
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
                    List<CityTradingAreaData> section = LitePal
                            .where("districtCode=?", cityAreaData.get(i).getDistrictCode())
                            .find(CityTradingAreaData.class);
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
        this.filterData = data;

        if (filterData != null && !filterData.isEmpty()) {
            filterData.get(0).setSelect(true); // 默认显示第一项选中
        }

        listAdapter = new RegionListAdapter(getContext(), filterData);
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

    /**
     * 返回的参数内容
     *
     * @param result
     */
    public void setResult(RegionFilterData result) {
        Map<String, Object> resultMap = new HashMap<>();

        resultMap.put("result", result);
        if (onFilterResultListener != null) {
            onFilterResultListener.onResult(this, resultMap);
        }

        close();
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
            // 设置显示名称
            TextView titleView = holder.getView(R.id.base_filter_region_tv_title);
            titleView.setText(item.getName());
            // 设置文本颜色状态
            if (item.isSelect()) {
                titleView.setTextColor(selectedColor);
            } else {
                titleView.setTextColor(defaultColor);
            }

            // 点击事件
            titleView.setOnClickListener(v -> {
                // 点击后设置成选中
                setCheck(position);

                // 判断当前点击的层级
                if (currentLevel.value == Level.Level_1.value) {
                    // 1级
                    if (hasChilds(item)) {
                        // 判断当前类别是否有子级
                        recyclerView2.setVisibility(VISIBLE); // 显示第二级列表
                        List<RegionFilterData> childs = new ArrayList<>(); // 子级数据集
                        childs.addAll(item.getChilds());

                        // 用空白item补全子级数据长度
//                        int disparity = dataList.size() - childs.size();
//                        if (disparity > 0) {
//                            for (int i = 0; i < disparity; i++) {
//                                childs.add(new RegionFilterData(""));
//                            }
//                        }

                        recyclerView2.setAdapter(new RegionListAdapter(mContext, childs, Level.Level_2));
                        recyclerView3.setVisibility(GONE); // 隐藏第三级
                    } else {
                        // 没有子级，直接返回当前级别的参数
                        recyclerView2.setVisibility(GONE);
                        recyclerView3.setVisibility(GONE);
                        setResult(item);
                    }
                } else if (currentLevel.value == Level.Level_2.value) {
                    // 2级
                    if (hasChilds(item)) {
                        recyclerView3.setVisibility(VISIBLE); // 显示第三级列表
                        List<RegionFilterData> childs = new ArrayList<>(); // 子级数据集
                        for (int i = 0; i < item.getChilds().size(); i++) {
                            if (i == 0){
                                item.getChilds().get(i).setSelect(true);
                            } else {
                                // 清空之前选中状态
                                item.getChilds().get(i).setSelect(false);
                            }
                        }
                        childs.addAll(item.getChilds());

                        // 用空白item补全子级数据长度
//                        int disparity = dataList.size() - childs.size();
//                        if (disparity > 0) {
//                            for (int i = 0; i < disparity; i++) {
//                                childs.add(new RegionFilterData(""));
//                            }
//                        }

                        recyclerView3.setAdapter(new RegionListAdapter(mContext, childs, Level.Level_3));
                    } else {
                        recyclerView3.setVisibility(GONE);
                        setResult(item);
                    }
                } else if (currentLevel.value == Level.Level_3.value) {
                    // 3级: 片区、地铁站点
                    for (int i = 0; i < item.getChilds().size(); i++) {
                        if (i == 0){
                            item.getChilds().get(i).setSelect(true);
                        } else {
                            // 清空之前选中状态
                            item.getChilds().get(i).setSelect(false);
                        }
                    }
                    setResult(item); // 直接返回对应item数据
                }
            });
        }

        // 设置某项选中
        private void setCheck(int position) {
            for (int i = 0; i < dataList.size(); i++) {
                dataList.get(i).setSelect(position == i);
            }
            notifyDataSetChanged();
        }

        // 设置当前的级别
        public void setCurrentLevel(Level currentLevel) {
            this.currentLevel = currentLevel;
        }
    }

    public interface LoadListener {

        void addDisposable(Disposable disposable);

        void loadFailed(String errorMsg);
    }
}
