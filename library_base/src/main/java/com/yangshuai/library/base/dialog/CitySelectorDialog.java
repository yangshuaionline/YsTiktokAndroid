package com.yangshuai.library.base.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.yangshuai.library.base.R;
import com.yangshuai.library.base.entity.city.AddressBean;
import com.yangshuai.library.base.entity.city.AddressSelector;
import com.yangshuai.library.base.entity.city.CityInterface;
import com.yangshuai.library.base.entity.city.OnItemClickListener;

import java.util.ArrayList;

/**
 * Created by hcp on 2018/5/29.
 */

public class CitySelectorDialog extends Dialog implements OnItemClickListener, AddressSelector.OnTabSelectedListener {
    private Context mContext;

    private AddressBean mAddressBeans; // 总数据
    private ArrayList<AddressBean.AddressItemBean> mRvDatas = new ArrayList<>(); //用来在recyclerview显示的数据

    private AddressBean.AddressItemBean mSelectProvince; //选中 省份 bean
    private AddressBean.AddressItemBean mSelectCity;//选中 城市  bean
    private AddressBean.AddressItemBean mSelectDistrict;//选中 区县  bean

    private AddressSelector addressSelector;

    private AddressResultListener addressListener;


    public CitySelectorDialog(Context context, AddressResultListener addressListener) {
        super(context,R.style.BaseBottomSheetDialog);
        setContentView(R.layout.base_dialog_city_selector);
        mContext = context;
        this.addressListener = addressListener;
        initView();
    }

    private void initView() {
        addressSelector = findViewById(R.id.address_selector);
        TextView tvCancel = findViewById(R.id.tv_cancel);
        addressSelector.setTabAmount(2);
        addressSelector.setOnItemClickListener(this);
        addressSelector.setOnTabSelectedListener(this);
        addressSelector.setListTextSize(17);

        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        window.setWindowAnimations(R.style.BaseBottomSheetDialog);  //添加动画

        tvCancel.setOnClickListener(v->{
            dismiss();
        });
    }


    public void setCityData(AddressBean addressBeans) {
        mAddressBeans = addressBeans;

        mRvDatas.clear();
        mRvDatas.addAll(mAddressBeans.getProvince());
        addressSelector.setCities(mRvDatas);
    }

    public void setDataSelect(AddressBean.AddressItemBean province
            ,AddressBean.AddressItemBean city) {
        mRvDatas.clear();
        for (AddressBean.AddressItemBean itemBean : mAddressBeans.getCity()) {
            if (itemBean.getProvinceId().equals(province.getCityI()))
                mRvDatas.add(itemBean);
        }
        if (!mRvDatas.isEmpty()) addressSelector.setCities(mRvDatas);
        addressSelector.setTabText(province.getName(),city.getName());
        mSelectProvince = province;
        mSelectCity = city;
    }

    @Override
    public void itemClick(AddressSelector addressSelector, CityInterface selectBean, int tabPosition) {
        switch (tabPosition){
            case 0:
                mRvDatas.clear();
                if (selectBean != null) {
                    mSelectProvince = (AddressBean.AddressItemBean) selectBean;
                    for (AddressBean.AddressItemBean itemBean : mAddressBeans.getCity()) {
                        if (itemBean.getProvinceId().equals(selectBean.getCityI()))
                            mRvDatas.add(itemBean);
                    }
                    if (!mRvDatas.isEmpty()) addressSelector.setCities(mRvDatas);
                }
                break;
            case 1:
                mRvDatas.clear();
                if (selectBean != null) {
                    mSelectCity = (AddressBean.AddressItemBean) selectBean;
//                    for (AddressBean.AddressItemBean itemBean : mAddressBeans.getDistrict()) {
//                        if (itemBean.getProvinceId().equals(selectBean.getCityI()))
//                            mRvDatas.add(itemBean);
//                    }
//                    if (!mRvDatas.isEmpty()) addressSelector.setCities(mRvDatas);
                }
//                String address = mSelectProvince.getCityName() + "  " + mSelectCity.getCityName() + "  " + selectBean.getCityName();
                addressListener.onReturnAddress(mSelectProvince,mSelectCity);
                dismiss();
                break;
//            case 2:
//                String address = mSelectProvince.getCityName() + "  " + mSelectCity.getCityName() + "  " + selectBean.getCityName();
//                addressListener.onReturnAddress(address);
//                dismiss();
//                break;
        }
    }

    @Override
    public void onTabSelected(AddressSelector addressSelector, AddressSelector.Tab tab) {
        switch (tab.getIndex()){
            case 0:
                mRvDatas.clear();
                addressSelector.setCities(mAddressBeans.getProvince());
                break;
            case 1:
                if (mSelectProvince != null) {
                    mRvDatas.clear();
                    for (AddressBean.AddressItemBean itemBean : mAddressBeans.getCity()) {
                        if (itemBean.getProvinceId().equals(mSelectProvince.getCityI()))
                            mRvDatas.add(itemBean);
                    }
                    if (!mRvDatas.isEmpty()) addressSelector.setCities(mRvDatas);
                }
                break;
            case 2:
//                addressSelector.setCities(mRvDatas);
                break;
        }
    }

    @Override
    public void onTabReselected(AddressSelector addressSelector, AddressSelector.Tab tab) {

    }


    public interface AddressResultListener {
        void onReturnAddress(AddressBean.AddressItemBean selectProvince,AddressBean.AddressItemBean selectCity);
    }

    public void setAddressListener(AddressResultListener addressListener) {
        this.addressListener = addressListener;
    }

}