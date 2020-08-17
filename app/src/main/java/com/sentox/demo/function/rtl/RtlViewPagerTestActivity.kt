package com.sentox.demo.function.rtl

import android.os.Bundle
import com.meelive.ingkee.home.adapter.HomeNewGroupAdapter
import com.meelive.ingkee.home.fragment.TestFragment
import com.meelive.ingkee.view.InkeRtlViewPager
import com.sentox.demo.R
import com.sentox.demo.function.base.activity.BaseActivity
import kotlinx.android.synthetic.main.activity_rtl_viewpager.*

/**
 * 描述：自定义rtlviewpager测试
 * 说明：
 * Created by Sentox
 * Created on 2020/5/15
 */
class RtlViewPagerTestActivity : BaseActivity() {

    private val adapter: HomeNewGroupAdapter by lazy { HomeNewGroupAdapter(supportFragmentManager) }
    private val mViewPager: InkeRtlViewPager by lazy { findViewById<InkeRtlViewPager>(R.id.vp_test) }
    private var mCount = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rtl_viewpager)

        adapter.addAllFragment(arrayListOf<HomeBaseFragment>(TestFragment(mCount), TestFragment(++mCount)))
        mViewPager.adapter = adapter

        btn_test_add.setOnClickListener {
            adapter.addAllFragment(arrayListOf<HomeBaseFragment>(TestFragment(++mCount), TestFragment(++mCount)))
        }


    }
}