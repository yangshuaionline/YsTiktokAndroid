package com.yangshuai.module.main.activity.adapter

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class FragmentAdapterKt(var fm: FragmentManager, var mlist: List<Fragment>) :
    FragmentPagerAdapter(fm) {

    override fun getCount(): Int = mlist.size

    override fun getItem(position: Int): Fragment = mlist[position]

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        return super.instantiateItem(container, position)
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
    }

}