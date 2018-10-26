package com.sentox.demo.utils;


import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;

import com.sentox.demo.function.base.application.GOApplication;
import com.sentox.demo.function.base.log.Loger;

import java.util.ArrayList;
import java.util.List;

/**
 * 应用信息工具类
 *
 * @author chenbenbin
 */
public class AppUtils {
    /**
     * 获取app包信息
     *
     * @param packageName 包名
     */
    public static PackageInfo getAppPackageInfo(final Context context,
                                                final String packageName) {
        PackageInfo info;
        try {
            info = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (Exception e) {
            info = null;
            e.printStackTrace();
        }
        return info;
    }

    public static String getAppName(final Context context,
                                    final String packageName) {
        PackageInfo info = getAppPackageInfo(context, packageName);
        return getAppName(context, info);
    }

    public static String getAppName(final Context context, PackageInfo info) {
        if (info != null) {
            return info.applicationInfo.loadLabel(context.getPackageManager())
                    .toString();
        }
        return "";
    }

    public static long getAppFirstInstallTime(final Context context,
                                              final String packageName) {
        PackageInfo info = getAppPackageInfo(context, packageName);
        return getAppFirstInstallTime(context, info);
    }

    public static long getAppFirstInstallTime(final Context context,
                                              PackageInfo info) {
        if (null != info) {
            return info.firstInstallTime;
        }
        return 0;
    }

    /**
     * 判断一个应用是否已经停用（不是停止运行）.<br>
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isAppDisable(Context context, String packageName) {
        boolean isDisable = false;
        try {
            ApplicationInfo app = context.getPackageManager()
                    .getApplicationInfo(packageName, 0);
            isDisable = !app.enabled;
        } catch (Exception e) {
            isDisable = true;
            e.printStackTrace();
        }
        return isDisable;
    }

    /**
     * 检查是安装某包
     */
    public static boolean isAppExist(final Context context, final String packageName) {
        if (context == null || packageName == null) {
            return false;
        }
        boolean result = false;
        try {
            context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SHARED_LIBRARY_FILES);
            result = true;
        } catch (PackageManager.NameNotFoundException e) {
            result = false;
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    /**
     * 判断应用是否在前台
     *
     * @return
     */
    public static boolean isAppForeground(Context context) {
//        context = context.getApplicationContext();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager == null) {
            return false;
        }
        List<RunningAppProcessInfo> runningAppProcessInfos = activityManager.getRunningAppProcesses();
        if (runningAppProcessInfos != null) {
            for (RunningAppProcessInfo processInfo : runningAppProcessInfos)
                if (processInfo != null && processInfo.pid == android.os.Process.myPid()) {
                    //增加一步判断，更保险吧
                    return processInfo.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
                }
        }
        return false;
    }

    public static boolean isAppExist(ResolveInfo resolveInfo, String pkgName) {
        try {
            ApplicationInfo applicationInfo = resolveInfo.activityInfo.applicationInfo;
            String packageName = applicationInfo.packageName;
            if (packageName.equals(pkgName)) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    public static boolean isAppExist(final Context context, final Intent intent) {
        List<ResolveInfo> infos = null;
        try {
            infos = context.getPackageManager()
                    .queryIntentActivities(intent, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (infos != null) && (infos.size() > 0);
    }

    /**
     * 查询已安装的应用
     *
     * @param context
     * @return
     */
    public static List<PackageInfo> getInstalledPackages(Context context) {
        PackageManager pManager = context.getPackageManager();
        List<PackageInfo> paklist = null;
        try {
            paklist = pManager.getInstalledPackages(0);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        if (paklist == null) {
            paklist = new ArrayList<PackageInfo>();
        }
        return paklist;
    }

    /**
     * 获得当前所在进程的进程名<br>
     *
     * @param cxt
     * @return
     */
    public static String getCurrentProcessName(Context cxt) {
        ActivityManager actMgr = (ActivityManager) cxt
                .getSystemService(Context.ACTIVITY_SERVICE);
        if (actMgr == null) {
            return null;
        }
        final List<RunningAppProcessInfo> runningAppProcesses = actMgr
                .getRunningAppProcesses();
        if (runningAppProcesses == null) {
            return null;
        }
        final int pid = android.os.Process.myPid();
        for (RunningAppProcessInfo appProcess : runningAppProcesses) {
            if (appProcess != null && appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    /**
     * 获取App版本号
     *
     * @return App版本号
     */
    public static String getAppVersionName(Context context) {
        return getAppVersionName(context, context.getPackageName());
    }

    /**
     * 获取App版本号
     *
     * @param packageName 包名
     * @return App版本号
     */
    private static String getAppVersionName(Context context, final String packageName) {
        if (packageName == null) {
            return null;
        }
        try {
            PackageManager pm = context.getApplicationContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取App版本码
     *
     * @return App版本码
     */
    public static int getAppVersionCode(Context context) {
        return getAppVersionCode(context, context.getPackageName());
    }

    /**
     * 获取App版本码
     *
     * @param packageName 包名
     * @return App版本码
     */
    private static int getAppVersionCode(Context context, final String packageName) {
        if (packageName == null) {
            return -1;
        }
        try {
            PackageManager pm = context.getApplicationContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? -1 : pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * url跳转
     *
     * @param url
     */
    public static void openLinkSafe(Context context, String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        if (isIntentSafe(context, intent)) {
            context.startActivity(intent);
        }
    }

    /**
     * 在使用Intent试图打开其它软件(尤其是第三方)前, 应该先进行判断是否有支持该打开该Intent的Activity
     *
     * @param context
     * @param intent
     * @return
     */
    public static boolean isIntentSafe(Context context, Intent intent) {
        final PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    /**
     * 跳转到指定应用的google play的详情页<br>
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean openGooglePlay(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }
        return openGooglePlay(context, "market://details?id=" + packageName, "https://play.google.com/store/apps/details?id=" + packageName);
    }


    /**
     * 跳转到google play, 优先跳转到客户端，若失败尝试跳转到网页
     *
     * @param context
     * @param uriString 用于跳转客户端的uri字符串， 如 market://details?id=com.jiubang.alock&referrer=utm_source%3Dcom.com.gto.zero.zboost_icon_%26utm_medium%3Dhyperlink%26utm_campaign%3DZboosticon
     * @param urlString 用于跳转到网页版的url字符串， 如 https://play.google.com/store/apps/details?id=com.gto.zero.zboost
     * @return
     */
    public static boolean openGooglePlay(Context context, String uriString, String urlString) {
        boolean isOk = false;
        if (!TextUtils.isEmpty(uriString)) {
            // 先尝试打开客户端
            isOk = openActivitySafely(context, Intent.ACTION_VIEW, uriString, "com.android.vending");
            if (!isOk) {
                isOk = openActivitySafely(context, Intent.ACTION_VIEW, uriString, null);
            }
        }
        if (!isOk) {
            if (!TextUtils.isEmpty(urlString)) {
                // 试试打开浏览器
                isOk = openActivitySafely(context, Intent.ACTION_VIEW, urlString, null);
            }
        }
        return isOk;
    }

    /**
     * 安全地打开外部的activity
     *
     * @param action      如Intent.ACTION_VIEW
     * @param uri
     * @param packageName 可选，明确要跳转的程序的包名
     * @return 是否成功
     */
    public static boolean openActivitySafely(Context context, String action, String uri, String packageName) {
        boolean isOk = true;
        try {
            Uri uriData = Uri.parse(uri);
            final Intent intent = new Intent(action, uriData);
            if (!TextUtils.isEmpty(packageName)) {
                intent.setPackage(packageName);
            }
            if (!Activity.class.isInstance(context)) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            isOk = false;
        } catch (Throwable e) {
            e.printStackTrace();
            isOk = false;
        }
        return isOk;
    }


    /**
     * 判断是否为新用户
     *
     * @param context
     * @return
     */
    public static boolean isNewUser(Context context) {
        PackageManager pm = context.getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return pi != null && pi.firstInstallTime == pi.lastUpdateTime;
    }

    /**
     * 判断是否为新用户
     *
     * @param context
     * @return
     */
    public static long getFirstInstallTime(Context context) {
        PackageManager pm = context.getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (pi == null) {
            return 0;
        }
        return pi.firstInstallTime;
    }

    /**
     * 隐藏桌面图标
     */
    public static void hideAppLogo(boolean isGone, Class<?> cls) {
        PackageManager p = GOApplication.getAppContext().getPackageManager();
        int i = PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
        if (isGone) {
            i = PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
        }
        ComponentName componentName = new ComponentName(GOApplication.getAppContext(), cls);
        Loger.v("appIconGone", componentName.getClassName() + "  isGone : " + isGone);
        p.setComponentEnabledSetting(componentName, i, PackageManager.DONT_KILL_APP);
    }
}
