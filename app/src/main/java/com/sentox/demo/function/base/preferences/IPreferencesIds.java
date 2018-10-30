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
    public static final String IS_NEW_USER = "key_is_new_user";}

