package com.sentox.demo.function.language

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.os.LocaleList
import android.text.TextUtils
import com.sentox.demo.R
import com.sentox.demo.function.base.application.GOApplication
import com.sentox.demo.function.base.application.LauncherModel
import com.sentox.demo.function.base.log.Loger
import com.sentox.demo.function.base.preferences.IPreferencesIds
import java.util.*
import kotlin.collections.LinkedHashMap


/**
 * 描述：多语言管理类
 * 说明：
 * Created by Sentox
 * Created on 2019-11-15
 */
class LanguageManager private constructor() {

    companion object {
        const val TAG = "LanguageManager"
        val instance: LanguageManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            LanguageManager()
        }

        /**
         * 获取格式化的语言唯一的代码, 由语言码及地区码组合而成(地区码为可选)
         * 格式形如en_US
         */
        fun getKeyCode(languageCode: String, countryCode: String): String {
            require(!TextUtils.isEmpty(languageCode)) { "languageCode can't not be EMPTY!" }
            val code = StringBuffer()
            code.append(languageCode.toLowerCase(Locale.US))
            if (!TextUtils.isEmpty(countryCode)) {
                code.append("_")
                code.append(countryCode.toUpperCase(Locale.US))
            }
            return code.toString()
        }
    }

    private var mContext: Context = GOApplication.getAppContext()
    private var mLanguages: LinkedHashMap<String, LanguageBean> = LinkedHashMap()
    private var mResources: Resources
    private var mLocale: Locale = Locale("en","us")

    init {
        mResources = mContext.resources
        initData()
    }

    /**
     *  初始化
     * **/
    private fun initData() {
        loadInternal()
        loadCurrentLanguage()
    }

    /**
     *  加载当前设置的语言
     * **/
    private fun loadCurrentLanguage() {
        var keyCode = getSystemUserLangKeycode()
        val dbCode = getKeyCodeFromSp()
        if (!TextUtils.isEmpty(dbCode)) {
            keyCode = dbCode
        }
        applyLanguage(keyCode)
    }

    /**
     * 获取用户当前系统语言_国家
     *
     * @return
     */
    private fun getSystemUserLangKeycode(): String {
        val local = Locale.getDefault()
        val lang = local.language.toLowerCase(Locale.US)
        val country = local.country.toUpperCase(Locale.US)
        return lang + "_" + country
    }

    /**
     *  加载内置多语言列表
     * **/
    private fun loadInternal() {
        val r = mContext.resources
        val langConfigs = r.getStringArray(R.array.internal_languages)

        for (config in langConfigs) {
            val param = config.split("#")
            if (param.size == 2) {
                val bean = LanguageBean()
                bean.mDisPlayName = param[0]
                val codes = JsonResultParser.parseKeyCode(param[1])
                bean.mLanguageCode = codes[0]
                bean.mCountryCode = codes[1]
                mLanguages.put(param[1], bean)
                Loger.i(TAG, "\n${bean.mDisPlayName}")
            }
        }
    }

    /**
     *  设置本地化对象
     * **/
    private fun setLocale(lang: String, country: String) {
        if (!TextUtils.isEmpty(lang)) {
            mLocale = Locale(lang, country)
        }
    }

    /**
     *  8.0以下直接替换Resource中的文件
     * **/
    private fun updateResource() {
        val dm = mResources.displayMetrics
        val config = mResources.configuration
        config.locale = mLocale
        config.setLayoutDirection(mLocale)
        mResources.updateConfiguration(config, dm)
    }

    /**
     *  8.0以上更新resource
     *  需要将多语言植入Context
     * **/
    @TargetApi(Build.VERSION_CODES.N)
    private fun updateContext(context: Context): Context {
        val resources = context.resources

        val configuration = resources.configuration
        configuration.setLocale(mLocale)
        configuration.locales = LocaleList(mLocale)
        return context.createConfigurationContext(configuration)
    }

    /**
     *  设置语言
     *  @param keyCode
     *            {@link #getKeyCode(String, String)}
     * **/
    fun applyLanguage(keyCode: String?) {
        if (!TextUtils.isEmpty(keyCode)) {
            val bean = keyCode?.let { getLanguageByKeycode(it) }
            if (bean != null) {
                var codes = JsonResultParser.parseKeyCode(keyCode)
                setLocale(codes[0], codes[1])
                saveKeyCodeToSp(keyCode)
                updateResource()
            }
        }
    }

    /**
     *  保存设置的语言keycode
     * **/
    private fun saveKeyCodeToSp(keyCode: String?) {
        keyCode.let {
            LauncherModel.getInstance().sharedPreferencesManager.commitString(IPreferencesIds.RECORD_LANGUAGE, it)
        }
    }

    /**
     *  获取用户设置的语言keycode
     * **/
    private fun getKeyCodeFromSp(): String {
        return LauncherModel.getInstance().sharedPreferencesManager.getString(IPreferencesIds.RECORD_LANGUAGE, "")
    }

    /**
     * 通过keycode获取语言对象,优先使用语言_国家的形式,如果获取失败使用语言获取
     *
     * @param keycode
     * @return
     */
    fun getLanguageByKeycode(keycode: String): LanguageBean? {
        var lang: LanguageBean? = mLanguages[keycode]
        if (lang == null && keycode.contains("_")) {
            lang = mLanguages[keycode.split("_".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]]
        }
        return lang
    }

    /**
     *  8.0以上在BaseActivity中使用Context来修改语言
     * **/
    fun attachBaseContext(context: Context): Context {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateContext(context)
        } else {
            return context
        }
    }

    /**
     *  获取定制好的资源对象
     * **/
    fun getResource(): Resources {
        return mResources
    }

    /**
     *  获取内置语言类型
     * **/
    fun getInternalMap(): LinkedHashMap<String, LanguageBean> {
        return mLanguages
    }
}