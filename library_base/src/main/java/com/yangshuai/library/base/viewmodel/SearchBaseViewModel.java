package com.yangshuai.library.base.viewmodel;

import android.app.Activity;
import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import android.content.Context;

import androidx.databinding.Observable;
import androidx.databinding.ObservableBoolean;
import androidx.annotation.NonNull;

import android.widget.TextView;
import com.yangshuai.library.base.R;
import com.yangshuai.library.base.dialog.DeleteCheckDialog;
import com.yangshuai.library.base.entity.SearchClass;
import com.yangshuai.library.base.utils.StringUtils;
import com.yangshuai.library.base.utils.ViewUtils;
import com.yangshuai.library.base.view.LabelsView;
import org.litepal.LitePal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * 0. 创建表类 继承 SearchClass ,并在litepal.xml 中声明 更新版本号
 *
 * 1.继承SearchBaseViewModel
 *
 * 2.在xml里导入基本布局
 *
 * <variable
 *      name="baseViewModel"
 *      type="com.ujuz.library.base.viewmodel.SearchBaseViewModel" />
 *
 * <include
 *      android:id="@+id/history"
 *      layout="@layout/base_search_history"
 *      android:visibility="@{viewModel.showHistory?View.VISIBLE:View.GONE}"
 *      bind:viewModel="@{baseViewModel}"/> // 这里直接导入viewModel会转化失败
 *
 * 3.在activity中设置
 *
 *   mBinding.setViewModel(mViewModel);
 *   mBinding.setBaseViewModel(mViewModel);// 不设置这个会编译失败
 *
 *   mViewModel.setSearchClass(SearchHouseData.class);
 *
 *   mViewModel.initSearchView(this,findViewById(R.id.labels),findViewById(R.id.tv_search_more_history));
 *
 * 4.设置点击标签监听
 *
 *   mViewModel.setLabelSearchListener(this);
 *
 * 5.根据查询的内容添加至表中
 *
 *   mViewModel.setLitePalData(new SearchHouseData(houseName, TypeUtils.toLong(new Date()).toString()));
 *
 * @author hcp
 * on 2019-08-23
 */
public abstract class SearchBaseViewModel<T extends SearchClass> extends AndroidViewModel {

    protected final int MAX_HISTORY = 29;
    private Class<T> searchSaveClass;
    public Context mContext;
    private TextView mTvSearchMoreHistory;
    private LabelsView mLabelsView;

    private static LabelSearchListener mLabelSearchListener;

    public final ObservableBoolean showHistory = new ObservableBoolean(true);
    public final ObservableBoolean clickMoreHistory = new ObservableBoolean(false);
    public final ObservableBoolean isShowMoreHistory = new ObservableBoolean(false);

    public List<LabelsView.Label> displayLabels = new ArrayList<>();
    public List<LabelsView.Label> someLabels = new ArrayList<>();
    public List<LabelsView.Label> moreLabels = new ArrayList<>();

    public ObservableBoolean labelViewAttr = new ObservableBoolean(true);

    public SearchBaseViewModel(@NonNull Application application) {
        super(application);
    }


    /**
     * 传递初始化控件
     * @param context
     * @param labelsView
     * @param tvSearchMoreHistory
     */
    public void initSearchView(Activity context,LabelsView labelsView,TextView tvSearchMoreHistory) {
        mContext = context;

        mLabelsView = labelsView;
        mTvSearchMoreHistory = tvSearchMoreHistory;

        initSomeMoreLabels();
        displaySomeLabels();

        clickMoreHistory.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (!clickMoreHistory.get()) {
                    displayMoreLabels();
                } else {
                    displaySomeLabels();
                }
            }
        });

        mLabelsView.setCanSelected(true);
        mLabelsView.setMultiSelected(false);

        mLabelsView.setLabelClickListener(new LabelsView.OnLabelClickListener() {
            @Override
            public void onLabelClick(LabelsView.Label label) {
                //点击
                mLabelSearchListener.onLabelClick(label);
            }

            @Override
            public void onLongClick(LabelsView.Label label, String str) {
                //长按
//                ToastUtil.Short("删除" + label.name + "成功！");
//                LitePal.deleteAll(SearchData.class, "searchStr = ?", label.name);
//                mBinding.labels.getSelectedLabels().remove(label.id);
            }
        });
    }


    /**
     * 设置搜索类
     * @param searchDataClass
     */
    public void setSearchClass(Class<T> searchDataClass) {
        searchSaveClass = searchDataClass;
    }

    /**
     * 点击显示或隐藏更多搜索历史label
     */
    public void showMoreHistory() {
        clickMoreHistory.set(!clickMoreHistory.get());
    }


    /**
     * 显示前几个
     */
    private void displaySomeLabels() {
        displayLabels.clear();
        displayLabels.addAll(someLabels);
        mLabelsView.setLabels(displayLabels);

        if (moreLabels.size() > 0) {
            isShowMoreHistory.set(true);
            ViewUtils.setTextViewRightImage(mContext,mTvSearchMoreHistory
                    , R.mipmap.icon_filter_arrow_uncheck,"更多搜索历史");
        }
    }

    /**
     * 显示更多
     */
    private void displayMoreLabels() {
        displayLabels.addAll(moreLabels);
        mLabelsView.setLabels(displayLabels);

        isShowMoreHistory.set(true);
        ViewUtils.setTextViewRightImage(mContext,mTvSearchMoreHistory
                , R.mipmap.icon_filter_arrow_checked,"收起更多搜索历史");
    }


    /**
     * 写初始化数据库集合，进行倒叙显示 - 执行一遍去重
     * 子类中重写以下方法 - 替换掉存储类
     */
    public void initSomeMoreLabels() {

        List<T> searchDataAll = LitePal.findAll(searchSaveClass, true);
        if (searchDataAll != null && searchDataAll.size() > 0) {

            HashSet<String> searchStrSet = new LinkedHashSet<>();// 去重 并 保证存储顺序 LinkedHashSet
            ArrayList<String> searchStrList = new ArrayList<>();

            // 去重
            for (int i = 0; i < searchDataAll.size(); i++) {
                searchStrSet.add(searchDataAll.get(i).getSearchStr());
            }

            searchStrList.addAll(searchStrSet);

            for (int i = searchStrList.size() - 1; i >= 0; i--) {

                if (searchStrList.size() - i <= 4) {
                    someLabels.add(new LabelsView.Label(i, searchStrList.get(i)));
                } else {
                    //initMoreLabels
                    moreLabels.add(new LabelsView.Label(i, searchStrList.get(i)));
                }
            }
        }

        showHistory.set(someLabels.size() > 0 ? true : false);
        clickMoreHistory.set(moreLabels.size() > 0 ? true : false);
    }

    /**
     * 写进数据库，进行显示 -- 去重
     * 子类中重写以下方法 - 替换掉存储类
     * @param searchText
     */
    /**
     * 写进数据库，进行显示 -- 去重
     *
     * @param searchInstance
     */
    public void setLitePalData(T searchInstance) {
        if (StringUtils.isEmpty(searchInstance.getSearchStr())) return;

        List<T> searchDataAll = LitePal.findAll(searchSaveClass, true);

        //删除数据30条以外的数据
//        if(searchDataAll!= null && searchDataAll.size() > MAX_HISTORY ){
//            for (int i = 0;i < MAX_HISTORY; i++){
//                searchDataAll.get(MAX_HISTORY).deleteAsync();
//            }
//        }

        // 查重 -- 如果是相同字段则不录入
        for (int i = 0; i < searchDataAll.size(); i++) {
            if (searchInstance.getSearchStr().equals(searchDataAll.get(i).getSearchStr())) {
                return;
            }
        }

        searchDataAll.add(searchInstance);

        LitePal.saveAll(searchDataAll);
    }


    /**
     * 删除搜索记录
     * 子类中重写以下方法 - 替换掉存储类
     * @param
     */
    public void clearHistoryData() {
        //做判断是否为空数据，如果不为空，就弹出框提示
        List<T> searchDataAll = LitePal.findAll(searchSaveClass, true);
        if (searchDataAll != null && searchDataAll.size() > 0) {
            DeleteCheckDialog deleteCheckDialog = new DeleteCheckDialog(mContext, null);
            deleteCheckDialog.setDialogClickListener(new DeleteCheckDialog.AlertDialogClickListener() {
                @Override
                public void onCancel() {
                    deleteCheckDialog.dismiss();
                }

                @Override
                public void onOk() {
                    LitePal.deleteAll(searchSaveClass);
                    //删除搜素记录
                    someLabels.clear();
                    moreLabels.clear();
                    showHistory.set(false);
                    deleteCheckDialog.dismiss();
                }
            });
            deleteCheckDialog.show();
        }
    }


    /**
     * 用于点击标签和写入数据库的监听
     */
    public void setLabelSearchListener(LabelSearchListener labelSearchListener) {
        mLabelSearchListener = labelSearchListener;
    }

    public interface LabelSearchListener {
        // 点击label
        void onLabelClick(LabelsView.Label label);
        // 创建对象 写入数据库
//        List<T> addSearchData(List<T> searchDataAll,String searchText);
    }


}
