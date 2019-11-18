package com.sentox.demo.function.lifecycle;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.sentox.demo.R;

/**
 * 描述：
 * 说明：
 * Created by sentox
 * Created on 2019-10-21
 */
public class LifecycleJavaActivity extends AppCompatActivity {

    public final static String TAG = "LifecycleJavaActivity";
    private TextView mTvText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifecycle);
        mTvText = findViewById(R.id.mTvLiveCycleTest);
        getLifecycle().addObserver(new LifecycleObserver() {
            @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
            public void connectListener() {
                Log.d(TAG, "connectListener:  --------   onResume" );
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000l);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mTvText.setText("123");
            }
        }).start();
    }

}
