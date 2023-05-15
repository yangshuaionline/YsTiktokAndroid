package com.yangshuai.library.base.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author hcp
 * Create on 2019-11-12 10:13
 */
public class FragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> listFragment; //fragment列表
    private List<String> listTitle; // tab名称列表

    public FragmentAdapter(FragmentManager fm, List<Fragment> fragments,List<String> titles) {
        super(fm);
        this.listFragment = fragments;
        this.listTitle = titles;
    }

    public FragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.listFragment = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        return listFragment.get(i);
    }

    @Override
    public int getCount() {
        return listFragment.size();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return listTitle == null ? "" : listTitle.get(position % listTitle.size());
    }


    public void setListTitle(List<String> titles) {
        listTitle = titles;
        notifyDataSetChanged();
    }

//    @Override
//    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
//        return view == object;
//    }
}
