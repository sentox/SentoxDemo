package com.sentox.demo.function.drawingboard

import android.icu.lang.UProperty

/**
 * 描述：白板点数据
 * 说明：
 * Created by Sentox
 * Created on 2020/11/16
 */
class DrawInfo {

    /**
     *  本地实际的x坐标
     * **/
    var x:Int = 0
    /**
     *  本地实际的y坐标
     * **/
    var y:Int = 0


    /**
     *  传输x坐标（经过换算）
     * **/
    var xPect:Int = 0
    /**
     *  传输y坐标（经过换算）
     * **/
    var yPect:Int = 0
    /**
     *  当前指令状态
     * **/
    var action:Int = DrawAction.DEFAULT

    object DrawAction{
        const val DEFAULT = -1
        const val DOWN = 11
        const val MOVE = 21
        const val UP = 31
        const val CLEAR = 41

    }

}