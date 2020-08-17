package com.meelive.ingkee.home.fragment

import android.graphics.Outline
import android.os.Bundle
import android.view.*
import android.widget.TextView
import com.sentox.demo.R
import com.sentox.demo.function.rtl.HomeBaseFragment
import com.sentox.demo.utils.dp
import kotlinx.android.synthetic.main.layout_test.*

/**
 * 描述：
 * 说明：
 * Created by Sentox
 * Created on 2020/5/15
 */
class TestFragment(val count: Int) : HomeBaseFragment() {

    private lateinit var mRootView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mRootView = inflater.inflate(R.layout.layout_test, container, false)
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRootView.findViewById<TextView>(R.id.tv_test).text = "$count"
        view_test.outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View?, outline: Outline?) {
                outline?.setRoundRect(0, 0, 100.dp, 100.dp, 50.dp.toFloat());
            }
        }
        view_test.clipToOutline = true
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