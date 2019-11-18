package com.sentox.demo.function.language

/**
 * 描述：多语言选项数据bean
 * 说明：
 * Created by sentox
 * Created on 2019-11-15
 */
class LanguageBean {
    var mDisPlayName:String = ""
    var mLanguageCode:String = ""
    var mCountryCode:String = ""

    fun getKeyCode():String{
        return LanguageManager.getKeyCode(mLanguageCode,mCountryCode)
    }

}