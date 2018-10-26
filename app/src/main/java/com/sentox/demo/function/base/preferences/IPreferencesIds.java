package com.sentox.demo.function.base.preferences;

/**
 * prefers的文件名，key
 */
public class IPreferencesIds {

    // ============================== 文件名 ====================================

    public static final String DEFAULT_SHAREPREFERENCES_FILE = "default_cfg";

    // ============================== key ====================================

    // *****************************共用模块*******************************//
    /**
     * 程序首次运行的时间，也可粗略作为首次安装应用的时间, 单位毫秒<br>
     * 值类型: long<br>
     */
    public final static String KEY_FIRST_START_APP_TIME = "key_first_start_app_time";
    /**
     * 记录用户是否执行了数据库升级<br>
     * 值类型: boolean
     */
    public final static String KEY_DATA_BASE_UPGRADED = "key_data_base_upgraded";
    /**
     * 记录本程序的旧版本号, 用于统计时判断是否为升级用户.<br>
     * 值类型:int<br>
     */
    public static final String KEY_STATISTICS_OLD_VERSION_CODE = "key_statistics_old_version_code";
    /**
     * 是否为新用户
     * 值类型: boolean
     */
    public static final String IS_NEW_USER = "key_is_new_user";
    /**
     * 是否为升级用户
     * 值类型: boolean
     */
    public static final String IS_UPDATE_USER = "key_is_update_user";


//    用户设置
    /**
     * 亮屏时间
     */
    public static final String KEY_TIME_OUT = "key_time_out";

    /**
     * 时间显示格式
     */
    public static final String IS_24_HOUR_FORMAT = "key_is_24_hour_fotmat";

    /**
     * 当前皮肤
     * **/
    public static final String KEY_CURRENT_SKIN = "KEY_CURRENT_SKIN";

    /**
     * 铃声渐强
     */
    public static final String IS_VIBRATION = "key_is_vibration";

    /**
     * 亮度
     */
    public static final String KEY_BRIGTHNESS = "key_brightness";

    /**
     * 是否添加了默认闹钟
     * boolean
     */
    public static final String KEY_FIRST_INIT_CLOCK = "KEY_FIRST_INIT_CLOCK";

    /**
     *  是否需要显示双击提示
     * **/
    public static final String KEY_NEED_SHOW_DOUBLE_CLICK_TIPS = "KEY_NEED_SHOW_DOUBLE_CLICK_TIPS";

    /**
     *  最后一个暂停的秒表时间节点
     * **/
    public static final String KEY_LEAST_STOP_WATCH_TIME = "KEY_LEAST_STOP_WATCH_TIME";
    /**
     *  最后一个暂停的秒表时间节点的上一个时间点
     * **/
    public static final String KEY_LEAST_STOP_WATCH_TIME_LAST = "KEY_LEAST_STOP_WATCH_TIME_LAST";
}

