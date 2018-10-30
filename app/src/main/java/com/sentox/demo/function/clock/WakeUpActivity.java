package com.sentox.demo.function.clock;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sentox.demo.R;

import java.text.SimpleDateFormat;

/**
 * 描述：闹铃界面
 * 说明：
 * Created by Sentox
 * Created on 2018/9/13
 */
public class WakeUpActivity extends Activity {

    private TextView mTvTips;
    private CustomClockView mCustomClockView;
    private long mWakeUpTime = 0;
    private Button mBtnStop;
    private Ringtone mRingtone;
    private ClockService.ClockBinder mBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wakeup);
        initData();
        init();
    }

    private void initData(){
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            if(bundle.containsKey(ClockActivity.KEY_EXTRAS_WAKE_UP_TIME)){
                mWakeUpTime = bundle.getLong(ClockActivity.KEY_EXTRAS_WAKE_UP_TIME,0);
            }
        }
    }

    private void init(){
        Intent bindIntent = new Intent(this,ClockService.class);
        bindService(bindIntent, mConnection,BIND_AUTO_CREATE);
        mCustomClockView = (CustomClockView)findViewById(R.id.v_wakeup_clockview);
        mBtnStop = (Button)findViewById(R.id.btn_wakeup_stop);
        mTvTips = (TextView)findViewById(R.id.tv_wakeup_tips);
        mTvTips.setText((new SimpleDateFormat("yyyy-MM-dd HH:mm")).format(mWakeUpTime)+"\n Wake Up!!!");

        if(mWakeUpTime>0){
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            mRingtone = RingtoneManager.getRingtone(this,notification);
            mRingtone.play();

            mBtnStop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mRingtone.stop();
                    if(mBinder!=null){
                        mBinder.deleteNoti();
                    }
                }
            });
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = (ClockService.ClockBinder) service;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
        mCustomClockView.setClockStop();
    }
}
