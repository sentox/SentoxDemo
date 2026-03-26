package com.meelive.ingkee.home.fragment

import android.graphics.Outline
import android.os.Bundle
import android.view.*
import android.widget.TextView
import com.sentox.demo.R
import com.sentox.demo.databinding.LayoutTestBinding
import com.sentox.demo.function.rtl.HomeBaseFragment
import com.sentox.demo.utils.dp

/**
 * 描述：
 * 说明：
 * Created by Sentox
 * Created on 2020/5/15
 */
class TestFragment(val count: Int) : HomeBaseFragment<LayoutTestBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            tvTest.text = "$count"
            viewTest.outlineProvider = object : ViewOutlineProvider() {
                override fun getOutline(view: View?, outline: Outline?) {
                    outline?.setRoundRect(0, 0, 100.dp, 100.dp, 50.dp.toFloat());
                }
            }
            viewTest.clipToOutline = true
        }
    }

    override fun getTagName(): String {
        return "$count"
    }

    override fun scrollTopToRefresh() {

    }

    override fun getTagId(): Int {
        return -1
    }
}