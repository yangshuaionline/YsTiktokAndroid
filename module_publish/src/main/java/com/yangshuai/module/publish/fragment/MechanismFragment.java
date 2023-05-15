package com.yangshuai.module.publish.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.yangshuai.library.base.BaseFragment;
import com.yangshuai.library.base.router.RouterPath;
import com.yangshuai.module.publish.BR;
import com.yangshuai.module.publish.R;
import com.yangshuai.module.publish.databinding.MechanismFrgmBinding;
import com.yangshuai.module.publish.viewmodel.MechanismViewModel;


/**
 * 工作台
 *
 * @author hcp
 * @created 2019/3/14
 */
@Route(path = RouterPath.Work.ROUTE_WORK_PATH)
public class MechanismFragment extends BaseFragment<MechanismFrgmBinding, MechanismViewModel> {

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.mechanism_frgm;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
}
