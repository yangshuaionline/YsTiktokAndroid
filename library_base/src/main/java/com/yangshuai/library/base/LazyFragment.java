package com.yangshuai.library.base;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.View;

/**
 * @Author hcp
 * @Created 8/27/19
 * @Editor hcp
 * @Edited 8/27/19
 * @Type
 * @Layout
 * @Api
 */
public abstract class LazyFragment extends Fragment {

    private boolean isViewCreated;
    private boolean isVisibleToUser;
    private boolean lazyLoaded;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public final void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        this.isVisibleToUser = isVisibleToUser;
        onVisibleChanged(this.isVisibleToUser);
        onLazyLoad();

    }

    public boolean isFragmentVisible() {
        return isVisibleToUser;
    }

    @Override
    public final void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.isViewCreated = true;
        onLazyLoad();
    }

    public boolean isFragmentViewCreated() {
        return isViewCreated;
    }

    /**
     * 进行懒加载
     */
    private void onLazyLoad() {
        if (isFragmentVisible() && isFragmentViewCreated()) {
            if (!lazyLoaded) {
                loadDataByLazy();
                lazyLoaded = true;
            }
        }

    }

    /**
     * fragment可见性变化时会回调该方法
     *
     * @param isVisibleToUser
     */
    public void onVisibleChanged(boolean isVisibleToUser) {
        // do something
    }

    /**
     * 重写该方法进行数据懒加载
     */
    public abstract void loadDataByLazy();


}
