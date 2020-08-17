package com.sentox.demo.function.rtl


import android.content.Context
import androidx.fragment.app.Fragment
import com.sentox.demo.function.language.LanguageManager


/**
 * 描述：首页fragment基类
 * 说明：
 * Created by Sentox
 * Created on 2019-11-26
 */
abstract class HomeBaseFragment : Fragment() {

    abstract fun getTagName(): String

    abstract fun scrollTopToRefresh()

    abstract fun getTagId():Int

    override fun onAttach(context: Context?) {
        super.onAttach(LanguageManager.instance.attachBaseContext(context!!))
    }

}