package com.sentox.demo.function.base.application;

import org.greenrobot.eventbus.EventBus;

/**
 * EventBus简化类
 *
 * @author chenbenbin
 */
public class GOEventBus {
    private final static EventBus GLOBAL_EVENT_BUS = EventBus.getDefault();

    /**
     * 获取一个全局的EventBus实例
     */
    public static EventBus getGlobalEventBus() {
        return GLOBAL_EVENT_BUS;
    }

    public static void register(Object subscriber) {
        GLOBAL_EVENT_BUS.register(subscriber);
    }

    public static void unregister(Object subscriber) {
        GLOBAL_EVENT_BUS.unregister(subscriber);
    }

    public static void post(Object event) {
        GLOBAL_EVENT_BUS.post(event);
    }

    /**
     * 使用全局EventBus post一个Sticky事件
     */
    @SuppressWarnings("unused")
    public static void postStickyEvent(Object event) {
        GLOBAL_EVENT_BUS.postSticky(event);
    }

    public static boolean isRegistered(Object subscriber) {
        return GLOBAL_EVENT_BUS.isRegistered(subscriber);
    }
}
