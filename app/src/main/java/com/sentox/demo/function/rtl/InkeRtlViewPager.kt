package com.meelive.ingkee.view

import android.content.Context
import android.database.DataSetObserver
import android.util.AttributeSet
import android.util.LayoutDirection
import androidx.core.text.TextUtilsCompat
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.sentox.demo.function.base.log.Loger
import java.util.*
import kotlin.math.max

/**
 * 描述：订制RTLViewPager
 * 说明：
 * Created by Sentox
 * Created on 2020/5/14
 */
class InkeRtlViewPager : ViewPager {

    companion object {
        private const val TAG = "InkeRtlViewPager"
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attr: AttributeSet) : super(context, attr) {
        init()
    }

    private fun init() {
        addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                mCurrentPosition = convert(position)
            }

        })
    }

    private var mPageChangeListener: ReverseOnPageChangeListener? = null

    private var mCurrentPosition = 0

    private var mDataObserver: DataSetObserver = object : DataSetObserver() {
        override fun onChanged() {
            super.onChanged()
            mPageChangeListener?.mBlockListen = true
            if (adapter != null) {
                offscreenPageLimit = adapter!!.count
                setCurrentItem(mCurrentPosition,false)
            }
            mPageChangeListener?.mBlockListen = false
        }
    }

    override fun setCurrentItem(item: Int) {
        super.setCurrentItem(convert(item))
    }

    override fun setCurrentItem(item: Int, smoothScroll: Boolean) {
        super.setCurrentItem(convert(item), smoothScroll)
    }

    override fun getCurrentItem(): Int {
        return convert(super.getCurrentItem())
    }

    override fun getAdapter(): PagerAdapter? {
        return super.getAdapter()
    }

    override fun fakeDragBy(xOffset: Float) {
        super.fakeDragBy(if (isRtl()) -xOffset else xOffset)
    }

    override fun setAdapter(adapter: PagerAdapter?) {
        super.setAdapter(adapter)
        adapter?.registerDataSetObserver(mDataObserver)
        currentItem = 0
    }

    fun setRtlOnPageChangeListener(listener: OnPageChangeListener) {
        if (isRtl()) {
            mPageChangeListener = ReverseOnPageChangeListener(listener)
            super.addOnPageChangeListener(mPageChangeListener!!)
        } else {
            super.addOnPageChangeListener(listener)
        }
    }

    fun removeRtlOnPageChangeListener(listener: OnPageChangeListener) {
        if (isRtl() && mPageChangeListener != null) {
            super.removeOnPageChangeListener(mPageChangeListener!!)
        }
        super.removeOnPageChangeListener(listener)
    }

    private fun convert(position: Int): Int {
        return if (position >= 0 && isRtl()) {
            if (adapter == null) 0 else adapter!!.count - position - 1
        } else {
            position
        }
    }

    fun isRtl(): Boolean {
        return TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault()) == LayoutDirection.RTL
    }


    inner class ReverseOnPageChangeListener(private val orgListener: OnPageChangeListener) : OnPageChangeListener {

        var mBlockListen = false
        private var pagerPosition = -1

        override fun onPageScrollStateChanged(state: Int) {
            if (!mBlockListen) {
                orgListener.onPageScrollStateChanged(state)
            }
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            if (!mBlockListen) {
                pagerPosition = if (positionOffset == 0f && positionOffsetPixels == 0) {
                    reverse(position)
                } else {
                    reverse(position + 1)
                }
                orgListener.onPageScrolled(pagerPosition, if (positionOffset > 0) 1f - positionOffset else positionOffset, positionOffsetPixels)
            }
        }

        override fun onPageSelected(position: Int) {
            var reversePos = reverse(position)
            Loger.i("$TAG:position=$position")
            if (!mBlockListen) {
                orgListener.onPageSelected(reverse(position))
            }
        }

        private fun reverse(position: Int): Int {
            val adapter = adapter
            return if (adapter == null) position else adapter.count - position - 1
        }

    }

}