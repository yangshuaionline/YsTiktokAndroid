/*
 * Copyright 2017 Zhihu Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yangshuai.library.base.widget.preview;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ImagePreviewPagerAdapter extends FragmentPagerAdapter {

    private List<String> mItems = new ArrayList<>();
    private OnFragmentInteractionListener mListener;

    public ImagePreviewPagerAdapter(FragmentManager manager, OnFragmentInteractionListener listener) {
        super(manager);
        mListener = listener;
    }

    @Override
    public Fragment getItem(int position) {
        ImagePreviewFragment fragment = ImagePreviewFragment.newInstance(mItems.get(position));
        fragment.setmListener(mListener);
        return fragment;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    public void setmItems(List<String> mItems) {
        this.mItems.clear();
        this.mItems.addAll(mItems);
    }

}
