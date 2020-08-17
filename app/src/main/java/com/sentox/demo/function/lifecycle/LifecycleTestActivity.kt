package com.sentox.demo.function.lifecycle

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sentox.demo.R
import com.sentox.demo.function.base.log.Loger
import kotlinx.android.synthetic.main.activity_lifecycle.*
import kotlinx.coroutines.*

/**
 * 描述：LifeCycle测试
 * 说明：
 * Created by sentox
 * Created on 2019-10-21
 */
class LifecycleTestActivity : AppCompatActivity() {

    companion object {
        const val TAG = "LifecycleTestActivity"
    }

    val mViewModel by lazy {
        ViewModelProviders.of(this).get(TestModel::class.java)
    }

    var mStrTest: String = "1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lifecycle)

//        lifecycle.addObserver(object : LifecycleObserver {
//            @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
//            fun connectListener() {
//                Loger.i(TAG, "connect----onResume")
//            }
//
//            @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
//            fun disconnectListener() {
//                Loger.i(TAG, "disconnect----onPause")
//            }
//        })
//
//        mTvLiveCycleTest.text = mViewModel.profile
//
//        mViewModel.profileData.observe(this, Observer {
//            it?.also {
//                Loger.i(TAG, "data change");
//                mTvLiveCycleTest.text = it
//            }
//        })
//
//        mTvLiveCycleTest.setOnClickListener {
//            Loger.i(TAG, "onclick");
//            it.postDelayed(Runnable {
//                mViewModel.testNewData("click")
//            }, 3000)
//
//        }
//
//        Loger.i(TAG, "初始化：${mViewModel.profile}")
//        var result = mViewModel.also {
//            it.profile = "2"
//            Loger.i(TAG, "赋值：${it.profile}")
//            it.profile
//        }
////        Loger.i(TAG, "结果：$result");
//        Loger.i(TAG, "对象中的数值：${mViewModel.profile}");

//        Loger.i(TAG, "主线程，下面开启阻塞的runBlocking协程作用域")
//        runBlocking {
//            launch {
//                delay(200L)
//                Loger.i(TAG, "runBlocking开启的不会阻塞的子协程任务")
//            }
//
//            coroutineScope{
//                launch {
//                    delay(500L)
//                    test1()
//                }
//                delay(100L)
//                Loger.i(TAG, "RunBlocking作用域开启的字协程作用域coroutineScope的任务")
//            }
//
//            Loger.i(TAG, "runBlocking协程结束")
//        }
//        Loger.i(TAG, "主线程，runBlocking结束")
//
//        GlobalScope.launch {
//            repeat(100000){
//                launch {
//                    delay(1000)
//                    Loger.i(TAG, ".");
//                }
//            }
//        }


       GlobalScope.launch (Dispatchers.IO){
           Loger.i(TAG, "Io   currentThread="+Thread.currentThread().name)
           delay(1000)
           mTvLiveCycleTest.text = "sdadfsdfgsdfgsdfgsdfgdfgdfggfdghf123"
           withContext(Dispatchers.Main){
               Loger.i(TAG, "Main   currentThread="+Thread.currentThread().name)
           }

       }

    }

    suspend fun test1() {
        Loger.i(TAG, "coroutineScope开启的不会阻塞的子协程任务")
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

//class LifecycleTestActivity : Activity(), LifecycleOwner {
//
//    companion object {
//        const val TAG = "LifecycleTestActivity"
//    }
//
//    lateinit var mLifecycle: LifecycleRegistry
//
//    override fun getLifecycle(): Lifecycle {
//        return mLifecycle
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_lifecycle)
//        mLifecycle = LifecycleRegistry(this)
//        lifecycle.addObserver(object :LifecycleObserver{
//            @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
//            fun connectListener(){
//                Loger.i(TAG, "connect----onResume")
//            }
//
//            @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
//            fun disconnectListener(){
//                Loger.i(TAG, "disconnect----onPause")
//            }
//        })
//        mLifecycle.markState(Lifecycle.State.CREATED)
//    }
//
//    override fun onResume() {
//        super.onResume()
//        mLifecycle.markState(Lifecycle.State.RESUMED)
//    }
//
//    override fun onPause() {
//        super.onPause()
//        mLifecycle.markState(Lifecycle.State.STARTED)
//    }
//
//
//}
