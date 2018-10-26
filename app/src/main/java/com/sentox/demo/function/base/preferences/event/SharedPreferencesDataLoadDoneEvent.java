package com.sentox.demo.function.base.preferences.event;

/**
 * SharedPreferences的数据加载完成后发出的事件.<br>
 * 会使用{@code EventBus.post(Object)}发出<br>
 *
 * @author laojiale
 * @see SharedPreferencesDataLoadDoneEvent
 */
public class SharedPreferencesDataLoadDoneEvent {

    /**
     * 暂无不加数据字段，当发生事件时通过
     * {@link LauncherModel#getSharedPreferencesManager()}
     * 获取感兴趣的数据就好了。
     */

}
