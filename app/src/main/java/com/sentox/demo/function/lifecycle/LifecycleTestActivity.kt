package com.sentox.demo.function.lifecycle

import android.graphics.BitmapFactory
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import com.sentox.demo.databinding.ActivityLifecycleBinding
import com.sentox.demo.function.base.activity.BaseActivity
import com.sentox.demo.function.base.log.L
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

/**
 * 描述：LifeCycle测试
 * 说明：
 * Created by sentox
 * Created on 2019-10-21
 */
class LifecycleTestActivity : BaseActivity<ActivityLifecycleBinding>() {

    companion object {
        const val TAG = "LifecycleTestActivity"
    }

    val mViewModel by lazy {
        ViewModelProviders.of(this).get(TestModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        L.info(TAG, "主线线程${Thread.currentThread().name},id=${Thread.currentThread().id}")
        val flow = MutableStateFlow(1)
        CoroutineScope(Dispatchers.Default).launch {
            launch(Dispatchers.IO) {
                for (i in 1 .. 10) {
                    L.info(TAG, "StateFlow.emit=$i")
                    flow.emit(i)
                    delay(100)
                }
                L.info(TAG, "StateFlow.emit=10,重复发送测试")
                flow.emit(10)//重复发送不会被接收
            }
            launch(Dispatchers.Main) {
                delay(200)
                flow.collect {
                    // 每次值变化都会收到，新订阅者立刻收到当前值
                    L.info(TAG, "StateFlow.collect1=$it")
                }
            }
            BitmapFactory.decodeFile("")
        }
    }

    private suspend fun getResult(): Int {
        delay(2000)
        return 555
    }

    override fun onDestroy() {
        super.onDestroy()

    }

    suspend fun test1() {
        L.info(TAG, "coroutineScope开启的不会阻塞的子协程任务")
    }

    class TestModel : ViewModel() {
        val profileData = MutableLiveData<String>()
        var profile: String? = "null"

        override fun onCleared() {
            profile = null
        }

        fun testNewData(data: String) {
            profileData.postValue(data)
        }
    }
}
