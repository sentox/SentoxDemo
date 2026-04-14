package com.sentox.demo.function.base.activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.sentox.demo.function.base.log.L
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.ParameterizedType

/**
 * author: lishangming
 * e-mail: lishangming@miya818.com
 * time: 2022/01/04
 * desc: viewBinding fragment、activity绑定布局
 * @see 参考 https://github.com/DylanCaiCoding/ViewBindingKTX/blob/master/viewbinding-base/src/main/java/com/dylanc/viewbinding/base/ViewBindingUtil.kt
 */
object ViewBindingUtil {
    private const val TAG = "ViewBindingUtil"


    @JvmStatic
    fun <VB : ViewBinding> inflateWithActivity(
        genericOwner: Any, layoutInflater: LayoutInflater
    ): VB? = withGenericBindingClass<VB>(genericOwner) { clazz ->
        clazz.getMethod("inflate", LayoutInflater::class.java).invoke(null, layoutInflater) as? VB
    }

    @JvmStatic
    fun <VB : ViewBinding> inflateWithFragment(
        genericOwner: Any,
        layoutInflater: LayoutInflater,
        parent: ViewGroup?,
    ): VB? = withGenericBindingClass<VB>(genericOwner) { clazz ->
        clazz.getMethod(
            "inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java
        ).invoke(null, layoutInflater, parent, false) as? VB
    }


    @JvmStatic
    fun <VB : ViewBinding> inflateWithView(
        genericOwner: Any,
        layoutInflater: LayoutInflater,
        parent: ViewGroup?,
    ): VB? = withGenericBindingClass<VB>(genericOwner) { clazz ->
        var vb = try {
            clazz.getMethod(
                "inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java
            ).invoke(null, layoutInflater, parent, true) as? VB
        } catch (e: Exception) {
            //merge 标签会找不到
            null
        }
        if (vb == null) {
            //merge 标签的只有该方法
            vb = clazz.getMethod(
                "inflate", LayoutInflater::class.java, ViewGroup::class.java
            ).invoke(null, layoutInflater, parent) as? VB
        }
        return@withGenericBindingClass vb
    }

    private fun <VB : ViewBinding> withGenericBindingClass(
        genericOwner: Any, block: (Class<VB>) -> VB?
    ): VB? {
        var genericSuperclass = genericOwner.javaClass.genericSuperclass
        var superclass = genericOwner.javaClass.superclass
        while (superclass != null) {
            if (genericSuperclass is ParameterizedType) {
                genericSuperclass.actualTypeArguments.forEach {
                    try {
                        return block.invoke(it as Class<VB>)
                    } catch (e: NoSuchMethodException) {
                    } catch (e: ClassCastException) {
                    } catch (e: InvocationTargetException) {
                        L.e(
                            TAG,
                            "withGenericBindingClass => ${e.message}" + ", class: " + genericOwner.javaClass.simpleName
                        )
                        return null
                    }
                }
            }
            genericSuperclass = superclass.genericSuperclass
            superclass = superclass.superclass
        }
        L.e(
            TAG,
            "withGenericBindingClass： " + genericOwner.javaClass.simpleName + ", genericSuperclass: " + genericOwner.javaClass.genericSuperclass
        )
        return null
    }

}