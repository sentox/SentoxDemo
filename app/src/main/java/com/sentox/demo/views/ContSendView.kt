package com.sentox.demo.views

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Outline
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.sentox.demo.R
import com.sentox.demo.utils.AnimatorUtils
import com.sentox.demo.utils.dp

/**
 * 描述：新版连送按钮
 * 说明：
 * Created by Sentox
 * Created on 2020/5/25
 */
class ContSendView : FrameLayout {

//    private val mLyWater by lazy { findViewById<RelativeLayout>(R.id.ly_cont_send_water) }
//    private val mViewWave by lazy { findViewById<View>(R.id.iv_cont_send_wave) }

    private val mViewWave by lazy { findViewById<CircleWaveView>(R.id.view_cont_send_wave) }

    constructor(context: Context) : super(context) {

    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {

    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr) {

    }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_cont_send, this, true)
//        mLyWater.outlineProvider = object : ViewOutlineProvider() {
//            override fun getOutline(view: View?, outline: Outline?) {
//                outline?.setRoundRect(100.dp, 0, 200.dp, 100.dp, 50.dp.toFloat());
//            }
//        }
//        mLyWater.clipToOutline = true
//
//        AnimatorUtils.translationX(mViewWave, -(100.dp), 0, 3000, 0, ValueAnimator.INFINITE, null)


    }

}