package com.meelive.ingkee.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import androidx.viewpager.widget.PagerAdapter
import com.meelive.ingkee.home.fragment.TestFragment
import com.meelive.ingkee.view.RtlPagerAdapter
import com.sentox.demo.function.math.Test
import com.sentox.demo.function.rtl.HomeBaseFragment
import kotlin.collections.ArrayList

/**
 * 描述：主界面adapter
 * 说明：
 * Created by Sentox
 * Created on 2019/7/18
 */
class HomeNewGroupAdapter(fm: FragmentManager) : RtlPagerAdapter(fm) {
    private val mList: MutableList<Fragment> = ArrayList()
    fun addFragment(fragment: Fragment) {
        mList.add(fragment)
        notifyDataSetChanged()
    }

    fun addAllFragment(list: ArrayList<TestFragment>) {
        mList.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Fragment {
        return mList[position]
    }

    override fun getCount(): Int {
        return mList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return (mList[position] as TestFragment).getTagName()
    }

    fun getTagIdByPosition(position: Int): Int {
        return (mList[position] as TestFragment).getTagId()
    }
}