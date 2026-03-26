package com.sentox.demo.function.drawingboard

import android.os.Bundle
import com.sentox.demo.R
import com.sentox.demo.databinding.ActivityDrawboardBinding
import com.sentox.demo.function.base.activity.BaseActivity

/**
 * 描述：画板测试view
 * 说明：
 * Created by Sentox
 * Created on 2020/11/13
 */
class DrawBoardActivity : BaseActivity<ActivityDrawboardBinding>() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawboard)
    }
}