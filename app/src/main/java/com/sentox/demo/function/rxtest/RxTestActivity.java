package com.sentox.demo.function.rxtest;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.sentox.demo.R;
import com.sentox.demo.function.base.log.Loger;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * 描述：rxjava测试activity
 * 说明：
 * Created by sentox
 * Created on 2019-09-05
 */
public class RxTestActivity extends Activity {

    private final static String TAG = "RxTestActivity";
    private TextView mTvShow;
    Integer i = 10086;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxtest);
        mTvShow = findViewById(R.id.tv_rxtext_main);

        //创建被观察者
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Loger.i(TAG, "开始订阅被观察者");
            }

            @Override
            public void onNext(Integer value) {
                Loger.i(TAG, "处理onNext事件+"+value);
            }

            @Override
            public void onError(Throwable e) {
                Loger.i(TAG, "处理错误事件");
            }

            @Override
            public void onComplete() {
                Loger.i(TAG, "处理完成事件");
            }
        });
//        //just
//        Observable.just(1,2,3,4).subscribe(new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer) throws Exception {
//                Loger.i(TAG, "acept:"+integer);
//            }
//        });
//        //fromArray
//        Integer[] items = {0,1,2,3,4};
//        Observable.fromArray(items).subscribe(new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer) throws Exception {
//                Loger.i(TAG, "FromArray:accept="+integer);
//            }
//        });

//        //fromIterable
//        List<Integer> list = new ArrayList<>();
//        list.add(3);
//        list.add(4);
//        list.add(5);
//        Observable.fromIterable(list).subscribe(new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer) throws Exception {
//                Loger.i(TAG, "fromIterable:"+integer);
//            }
//        });

//        Observable<Integer> observable = Observable.defer(new Callable<ObservableSource<? extends Integer>>() {
//            @Override
//            public ObservableSource<? extends Integer> call() throws Exception {
//                return Observable.just(i);
//            }
//        });
//        i = 12580;
//        observable.subscribe(new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer) throws Exception {
//                Loger.i(TAG, "defer:"+integer);
//            }
//        });

//        final long l = System.currentTimeMillis();
//        Observable.timer(2, TimeUnit.SECONDS)
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(Long aLong) throws Exception {
//                        Log.i("zhr", "RxTestActivity.java - accept : " + (System.currentTimeMillis() - l));
//                    }
//                });
//        Loger.i(TAG, "main:"+Thread.currentThread().getId());
//        Observable.interval(1, TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
//            @Override
//            public void accept(Long aLong) throws Exception {
//                Loger.i(TAG, "call:"+Thread.currentThread().getId());
//            }
//        });

    }
}
