package com.sentox.demo.function.rtl

import android.os.Bundle
import com.sentox.demo.R
import com.sentox.demo.databinding.ActivityRtlBinding
import com.sentox.demo.function.base.activity.BaseActivity
import com.sentox.demo.function.language.LanguageManager

/**
 * 描述：右到左测试界面
 * 说明：
 * Created by sentox
 * Created on 2019-11-14
 */
class RightToLeftActivity : BaseActivity<ActivityRtlBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding?.apply {
            mBtnEnglish.setOnClickListener {
                LanguageManager.instance.applyLanguage("en")
                recreate()
            }
            mBtnAr.setOnClickListener {
                LanguageManager.instance.applyLanguage("ar")
                recreate()
            }
            mBtnChinese.setOnClickListener {
                LanguageManager.instance.applyLanguage("zh_CN")
                recreate()
            }
        }
    }
}