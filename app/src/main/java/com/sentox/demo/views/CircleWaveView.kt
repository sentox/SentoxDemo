package com.sentox.demo.views

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.sentox.demo.utils.dp

/**
 * 描述：圆形波浪自定义view
 * 说明：
 * Created by Sentox
 * Created on 2020/5/25
 */
class CircleWaveView : View {

    private var mWidth = 0
    private var mHeight = 0
    private val mPaint1 = Paint()
    private val mPaint2 = Paint()
    private var mPath: Path = Path()
    private var mAnimator: ValueAnimator

    private var mWaveWidth = 0f
    private var mOffset = 0f
    private var mCenter = 0f
    private var mWaveHeight = 20.dp.toFloat()

    private val mClipPath = Path()

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr)

    init {
        mAnimator = ValueAnimator.ofFloat(0f, mWaveWidth)
        initPaint()

    }

    private fun initPaint() {
        mPaint1.color = Color.parseColor("#D6B630")
        mPaint1.style = Paint.Style.FILL_AND_STROKE

        mPaint2.color = Color.parseColor("#987C14")
        mPaint2.style = Paint.Style.FILL_AND_STROKE
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        countData(w, h)
        initAnim()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.clipPath(mClipPath)
        drawWave2(canvas)
        drawWave1(canvas)

    }

    private fun countData(w: Int, h: Int) {
        mWidth = w
        mHeight = h
        mWaveWidth = mWidth.toFloat()
        mOffset = 0f
        mCenter = mHeight.toFloat() / 2
        mClipPath.reset()
        mClipPath.addCircle(mWidth / 2f, mHeight / 2f, mWidth / 2f, Path.Direction.CW)
        mClipPath.close()
    }

    private fun drawWave1(canvas: Canvas?) {
        mPath.reset()
        mPath.moveTo(-mWaveWidth + mOffset, mCenter)
        for (i in 0..1) {
            mPath.quadTo((-mWaveWidth * 3 / 4) + (i * mWaveWidth) + mOffset, mCenter + mWaveHeight, (-mWaveWidth / 2) + (i * mWaveWidth) + mOffset, mCenter)
            mPath.quadTo((-mWaveWidth / 4) + (i * mWaveWidth) + mOffset, mCenter - mWaveHeight, i * mWaveWidth + mOffset, mCenter)
        }
        mPath.lineTo(mWidth.toFloat(), mHeight.toFloat())
        mPath.lineTo(0f, mHeight.toFloat())
        mPath.close()
        canvas?.drawPath(mPath, mPaint1)
    }

    private fun drawWave2(canvas: Canvas?) {
        mPath.reset()
        var offset = mOffset - mWaveWidth * 3 / 4
        mPath.moveTo(-mWaveWidth + offset, mCenter)
        for (i in 0..2) {
            mPath.quadTo((-mWaveWidth * 3 / 4) + (i * mWaveWidth) + offset, mCenter + mWaveHeight, (-mWaveWidth / 2) + (i * mWaveWidth) + offset, mCenter)
            mPath.quadTo((-mWaveWidth / 4) + (i * mWaveWidth) + offset, mCenter - mWaveHeight, i * mWaveWidth + offset, mCenter)
        }
        mPath.lineTo(mWidth.toFloat(), mHeight.toFloat())
        mPath.lineTo(-mWaveWidth + offset, mHeight.toFloat())
        mPath.close()
        canvas?.drawPath(mPath, mPaint2)
    }

    private fun initAnim() {
        if (mAnimator.isRunning) {
            mAnimator.cancel()
        }
        mAnimator = ValueAnimator.ofFloat(0f, mWaveWidth)
        mAnimator.duration = 1000L
        mAnimator.repeatCount = ValueAnimator.INFINITE
        mAnimator.interpolator = LinearInterpolator()
        mAnimator.addUpdateListener {
            mOffset = it.animatedValue as Float
            postInvalidate()
        }
    }

    /**
     *  设置水平面高度
     *  @param level 高度百分比，0是填满整个圆，1则是空的
     * **/
    fun setWaveLevel(level: Float) {
        mCenter = -mWaveHeight + (mHeight + 2 * mWaveHeight) * level
    }

    fun startAnim() {
        if (!mAnimator.isRunning) {
            mAnimator.start()
        }
    }

    fun cancelAnim() {
        if (mAnimator.isRunning) {
            mAnimator.cancel()
        }
    }
}