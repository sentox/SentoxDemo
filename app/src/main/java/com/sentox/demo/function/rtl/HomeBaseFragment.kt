package com.sentox.demo.function.rtl


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.sentox.demo.function.language.LanguageManager


/**
 * 描述：首页fragment基类
 * 说明：
 * Created by Sentox
 * Created on 2019-11-26
 */
abstract class HomeBaseFragment<VB : ViewBinding> : Fragment() {

    var binding: VB? = null
    private var mContentView: View? = null


    abstract fun getTagName(): String

    abstract fun scrollTopToRefresh()

    abstract fun getTagId(): Int

    override fun getView(): View? {
        return mContentView
    }

    override fun onAttach(context: Context?) {
        super.onAttach(LanguageManager.instance.attachBaseContext(context!!))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (binding != null) {
            this.mContentView = binding!!.root
//            mContentView!!.setTag(BaseViewTag.TAG_NAME, this)
        }
        return this.mContentView
    }

}