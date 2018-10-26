package com.sentox.demo.greendao.entry;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * 描述：秒表数据记录bean
 * 说明：
 * Created by Sentox
 * Created on 2018/10/24
 */

@Entity
public class StopWatchDataEntity {

    @Id
    public long id;

    /**
     *  记录的时间节点
     * **/
    public long mCountTime = 0;
    /**
     *  记录的时间节点的显示字符串
     * **/
    public String mStrCountTime = "";
    /**
     *  记录的时间节点距离上一个时间节点的时间的字符串
     * **/
    public String mStrLastTimePlus = "";
    @Generated(hash = 1143359137)
    public StopWatchDataEntity(long id, long mCountTime, String mStrCountTime,
                               String mStrLastTimePlus) {
        this.id = id;
        this.mCountTime = mCountTime;
        this.mStrCountTime = mStrCountTime;
        this.mStrLastTimePlus = mStrLastTimePlus;
    }
    @Generated(hash = 1353892063)
    public StopWatchDataEntity() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getMCountTime() {
        return this.mCountTime;
    }
    public void setMCountTime(long mCountTime) {
        this.mCountTime = mCountTime;
    }
    public String getMStrCountTime() {
        return this.mStrCountTime;
    }
    public void setMStrCountTime(String mStrCountTime) {
        this.mStrCountTime = mStrCountTime;
    }
    public String getMStrLastTimePlus() {
        return this.mStrLastTimePlus;
    }
    public void setMStrLastTimePlus(String mStrLastTimePlus) {
        this.mStrLastTimePlus = mStrLastTimePlus;
    }


}
