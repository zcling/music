package com.qmx.constants;


import java.util.HashMap;
import java.util.Map;

/**
 * 常量
 */
public interface Constants {

    /** 缓存键值 */
    public static final Map<Class<?>, String> cacheKeyMap = new HashMap<>();

    /**
     * 异常信息统一头信息<br>
     * 非常遗憾的通知您,程序发生了异常
     */
    public static final String Exception_Head = "OH,MY GOD! SOME ERRORS OCCURED! AS FOLLOWS :";

    /** 当前用户 */
    public static final String CURRENT_USER = "CURRENT_USER";

    /** 缓存命名空间 */
    public static final String CACHE_NAMESPACE = "QMXGDS:";

    /** 密码错误次数*/
    public static final String PASSWORD_ERROR_COUNT = "PASSWORD_ERROR_COUNT";
}
