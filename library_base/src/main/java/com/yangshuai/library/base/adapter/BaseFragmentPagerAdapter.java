package com.yangshuai.library.base.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * viewpager适配器
 *
 * @Author hcp
 * @Created 3/25/19
 * @Editor hcp
 * @Edited 3/25/19
 * @Type
 * @Layout
 * @Api
 */
public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;
    private List<String> pageTitles;

    public BaseFragmentPagerAdapter(List<String> pageTitles, List<Fragment> fragmentList, FragmentManager fm) {
        super(fm);
        this.fragmentList = fragmentList;
        this.pageTitles = pageTitles;
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    @Override
    public int getCount() {
        return fragmentList == null ? 0 : fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return pageTitles.get(position);
    }

    /**
     *动态设置标题
     */
    public void setPageTitle(int position, String title)
    {
        if(position >= 0 && position < pageTitles.size())
        {
            pageTitles.set(position, title);
            notifyDataSetChanged();
        }
    }
}
