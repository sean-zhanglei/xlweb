package com.nbug.mico.common.constants;

/**
 * 用户常量表

 */
public class UserConstants {

    /** 用户类型——H5 */
    public static final String USER_TYPE_H5 = "h5";
    /** 用户类型——公众号 */
    public static final String USER_TYPE_WECHAT = "wechat";
    /** 用户类型——小程序 */
    public static final String USER_TYPE_ROUTINE = "routine";

    /**
     * =========================================================
     * UserToken部分
     * =========================================================
     */
    /** 用户Token类型——公众号 */
    public static final Integer USER_TOKEN_TYPE_WECHAT = 1;
    /** 用户Token类型——小程序 */
    public static final Integer USER_TOKEN_TYPE_ROUTINE = 2;
    /** 用户Token类型——unionid */
    public static final Integer USER_TOKEN_TYPE_UNIONID = 3;

    /**
     * 商品热搜关键字 key
     */
    public static final String USER_HOT_KEYWORDS = "USER_HOT_KEYWORDS";

    /**
     * 首页数据 key
     */
    public static final String USER_INDEX_INFO = "USER_INDEX_INFO";

    /**
     * 首页商品列表 key
     */
    public static final String INDEX_PRODUCT = "INDEX_PRODUCT";
}
