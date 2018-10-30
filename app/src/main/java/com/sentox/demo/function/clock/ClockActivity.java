package com.sentox.demo.function.clock;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.sentox.demo.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 描述：时钟activity
 * 说明：
 * Created by Sentox
 * Created on 2018/9/12
 */
public class ClockActivity extends Activity {

    public final static String KEY_EXTRAS_WAKE_UP_TIME = "KEY_EXTRAS_WAKE_UP_TIME";

    private CustomClockView mClockView;
    private TextView mTvTips;
    private Button mBtnAdd;

    private Calendar mSetCalendar = Calendar.getInstance();
    private Context mContext;

    private DatePicker mDatePicker;
    private TimePicker mTimePicker;

    private ClockService.ClockBinder mBinder;
    private boolean mFlagStart = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);
        mContext = this;
        mSetCalendar.set(Calendar.SECOND,0);
        init();
    }

    private void init() {
        mClockView = (CustomClockView) findViewById(R.id.v_clock_clockview);
        mDatePicker = (DatePicker) findViewById(R.id.v_clock_date_picker);
        mTimePicker = (TimePicker) findViewById(R.id.v_clock_time_picker);
        mTvTips = (TextView) findViewById(R.id.tv_clock_tips);
        mBtnAdd = (Button)findViewById(R.id.btn_clock_add);

        Calendar calendar = Calendar.getInstance();
        mDatePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new
                DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mSetCalendar.set(year, monthOfYear, dayOfMonth);
            }
        });

        mTimePicker.setIs24HourView(true);
        mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                mSetCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                mSetCalendar.set(Calendar.MINUTE, minute);
            }
        });

        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlarm();
                showSetTime();
            }
        });
    }

    private void setAlarm(){
        AlarmManager alarmManager = (AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent();
        intent.setClass(mContext,WakeUpActivity.class);
        intent.putExtra(KEY_EXTRAS_WAKE_UP_TIME,mSetCalendar.getTimeInMillis());
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext,1,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, mSetCalendar.getTimeInMillis(), pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, mSetCalendar.getTimeInMillis(), pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, mSetCalendar.getTimeInMillis(), pendingIntent);
        }

        if(!mFlagStart){
            Intent startIntent = new Intent(ClockActivity.this, ClockService.class);
            startIntent.putExtra(KEY_EXTRAS_WAKE_UP_TIME,(new SimpleDateFormat("yyyy-MM-dd HH:mm")).format(mSetCalendar.getTime()));
            startService(startIntent);
            Intent bindIntent = new Intent(ClockActivity.this,ClockService.class);
            bindService(bindIntent, mConnection,BIND_AUTO_CREATE);
        }else{
            if(mBinder!=null){
                mBinder.updateNoti((new SimpleDateFormat("yyyy-MM-dd HH:mm")).format(mSetCalendar.getTime()));
            }
        }
    }

    private void showSetTime() {
        mTvTips.setText((new SimpleDateFormat("yyyy-MM-dd HH:mm")).format(mSetCalendar.getTime()));
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
        if(mFlagStart){
            unbindService(mConnection);
        }
        mClockView.setClockStop();
    }
}
