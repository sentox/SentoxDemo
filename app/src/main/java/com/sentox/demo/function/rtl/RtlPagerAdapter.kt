package com.meelive.ingkee.view


import androidx.fragment.app.Fragment

import androidx.fragment.app.FragmentPagerAdapter
import androidx.core.text.TextUtilsCompat
import android.util.LayoutDirection
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.PagerAdapter
import com.sentox.demo.function.base.log.Loger
import java.util.*

/**
 * 描述：配合自定义的InkeRtlViewPager使用
 * 说明：
 * Created by Sentox
 * Created on 2020/5/14
 */
abstract class RtlPagerAdapter(fm: FragmentManager) : androidx.fragment.app.FragmentPagerAdapter(fm) {

    abstract override fun getItem(position: Int): Fragment

    abstract override fun getCount(): Int

    override fun getPageTitle(position: Int): CharSequence? {
        return super.getPageTitle(reverse(position))
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    override fun getPageWidth(position: Int): Float {
        return super.getPageWidth(reverse(position))
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        Loger.i("zhr", "instantiateItem orgPos=$position,returnPos=${reverse(position)}")
        return super.instantiateItem(container, reverse(position))
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        Loger.i("zhr", "destroyItem orgPos=$position,returnPos=${reverse(position)}")
        super.destroyItem(container, reverse(position), `object`)
    }

    override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
        super.setPrimaryItem(container, reverse(position), `object`)
    }

    private fun reverse(position: Int): Int {
        return if (isRtl()) {
            count - position - 1
        } else {
            position
        }
    }

    fun isRtl(): Boolean {
        return TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault()) == LayoutDirection.RTL
    }
}