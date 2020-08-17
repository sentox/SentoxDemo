package com.sentox.demo.function.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.sentox.demo.function.base.log.Loger

/**
 * 描述：Lifecycle观察者
 * 说明：
 * Created by sentox
 * Created on 2019-10-21
 */
class LifeObserver : LifecycleObserver{
    companion object {
        const val TAG = "LifeObserver"
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun connectListener(){
        Loger.i(TAG, "connect----onResume");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun disconnectListener(){
        Loger.i(TAG, "disconnect----onPause");
    }
}