package com.sentox.demo.function.base.application;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;


/**
 * 线程助手类
 */
public class ThreadHelper {

    /**
     * 异步线程，用于处理一般比较短暂的耗时操作，如数据库读写操作等<br>
     */
    private static final HandlerThread SHORT_TASK_WORKER_THREAD = new HandlerThread(
            "Short-Task-Worker-Thread");

    static {
        SHORT_TASK_WORKER_THREAD.start();
    }

    private final static Handler SHORT_TASK_HANDLER = new Handler(
            SHORT_TASK_WORKER_THREAD.getLooper());
    private final static Handler MAIN_LOOPER_HANDLER = new Handler(
            Looper.getMainLooper());


    /**
     * 提交一个Runnable到短时任务线程执行<br>
     * <p>
     * <strong>NOTE:</strong>
     * 只充许提交比较短暂的耗时操作，如数据库读写操作等，像网络请求这类可能耗时较长的<i>不能</i>提交，<br>
     * 以免占用线程影响其他的重要数据库操作。
     * </p>
     *
     * @see #postRunOnShortTaskThread(Runnable, long)
     * @see #removeFromShortTaskThread(Runnable)
     */
    public static void postRunOnShortTaskThread(Runnable r) {
        postRunnableByHandler(SHORT_TASK_HANDLER, r);
    }

    /**
     * 提交一个Runnable到短时任务线程执行<br>
     * <p>
     * <strong>NOTE:</strong>
     * 只充许提交比较短暂的耗时操作，如数据库读写操作等，像网络请求这类可能耗时较长的<i>不能</i>提交，<br>
     * 以免占用线程影响其他的重要数据库操作。
     * </p>
     *
     * @param delayMillis 延迟指定的毫秒数执行.
     * @see #postRunOnShortTaskThread(Runnable)
     * @see #removeFromShortTaskThread(Runnable)
     */
    public static void postRunOnShortTaskThread(Runnable r, long delayMillis) {
        postRunnableByHandler(SHORT_TASK_HANDLER, r, delayMillis);
    }

    /**
     * 从短时任务线程移除一个先前post进去的Runnable<b>
     *
     * @see #postRunOnShortTaskThread(Runnable)
     * @see #postRunOnShortTaskThread(Runnable, long)
     */
    @SuppressWarnings("unused")
    public static void removeFromShortTaskThread(Runnable r) {
        removeRunnableFromHandler(SHORT_TASK_HANDLER, r);
    }

    /**
     * 提交一个Runnable到UI线程执行<br>
     *
     * @see #removeFromUiThread(Runnable)
     */
    public static void postRunOnUiThread(Runnable r) {
        postRunnableByHandler(MAIN_LOOPER_HANDLER, r);
    }

    /**
     * 提交一个Runnable到UI线程执行<br>
     *
     * @param delayMillis 延迟指定的毫秒数执行.
     * @see #postRunOnUiThread(Runnable)
     * @see #removeFromUiThread(Runnable)
     */
    public static void postRunOnUiThread(Runnable r, long delayMillis) {
        postRunnableByHandler(MAIN_LOOPER_HANDLER, r, delayMillis);
    }

    /**
     * 从UI线程移除一个先前post进去的Runnable<b>
     *
     * @see #postRunOnUiThread(Runnable)
     */
    @SuppressWarnings("unused")
    public static void removeFromUiThread(Runnable r) {
        removeRunnableFromHandler(MAIN_LOOPER_HANDLER, r);
    }

    /**
     * 是否运行在UI线程<br>
     */
    @SuppressWarnings("unused")
    public static boolean isRunOnUiThread() {
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }

    private static void postRunnableByHandler(Handler handler, Runnable r) {
        handler.post(r);
    }

    private static void postRunnableByHandler(Handler handler, Runnable r,
                                              long delayMillis) {
        if (delayMillis <= 0) {
            postRunnableByHandler(handler, r);
        } else {
            handler.postDelayed(r, delayMillis);
        }
    }

    private static void removeRunnableFromHandler(Handler handler, Runnable r) {
        handler.removeCallbacks(r);
    }
}
