package com.sentox.demo.function.clock;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import com.sentox.demo.R;

/**
 * 描述：闹钟前台服务
 * 说明：
 * Created by Sentox
 * Created on 2018/9/13
 */
public class ClockService extends Service {

    private ClockBinder mBinder = new ClockBinder();
    private Context mContext;
    private String mStrTime = "";
    private NotificationManager mNotificationManager;

    public final static int NOTI_ID = 10086;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mNotificationManager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle extras = intent.getExtras();
        if(extras!=null){
            if(extras.containsKey(ClockActivity.KEY_EXTRAS_WAKE_UP_TIME)){
                mStrTime = extras.getString(ClockActivity.KEY_EXTRAS_WAKE_UP_TIME,"");
            }
        }
        updateNoti();
        return super.onStartCommand(intent, flags, startId);

    }

    private void updateNoti(){
        if(!TextUtils.isEmpty(mStrTime)){
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext);
            mBuilder .setSmallIcon(R.mipmap.ic_launcher);
            mBuilder .setContentTitle("Next Alarm At：");
            mBuilder .setContentText(mStrTime);

            Intent intent = new Intent();
            intent.setClass(mContext,ClockActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(mContext,2,intent,PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(pendingIntent);
            Notification notification = mBuilder.build();
            notification.flags = Notification.FLAG_ONGOING_EVENT;
            mNotificationManager.notify(NOTI_ID,notification);
        }
    }

    class ClockBinder extends Binder{

        public void updateNoti(String time){
            mStrTime = time;
            ClockService.this.updateNoti();;
        }

        public void deleteNoti(){
            ClockService.this.mNotificationManager.cancel(NOTI_ID);
        }
    }
}
