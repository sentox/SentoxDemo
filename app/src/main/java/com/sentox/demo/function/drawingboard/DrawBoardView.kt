package com.sentox.demo.function.drawingboard

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import com.sentox.demo.R

/**
 * 描述：
 * 说明：
 * Created by Sentox
 * Created on 2020/11/13
 */
class DrawBoardView: View, View.OnTouchListener {


    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr)

    private var mPaint:Paint? = null
    private var mCurrentPath:Path?=null

    init {
        setOnTouchListener(this)
    }

    private fun drawImage(canvas:Canvas?){

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawImage(canvas)
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when(event?.action){
            MotionEvent.ACTION_DOWN ->{

            }
            MotionEvent.ACTION_MOVE ->{

            }
            MotionEvent.ACTION_UP->{

            }
        }
        return true
    }
}