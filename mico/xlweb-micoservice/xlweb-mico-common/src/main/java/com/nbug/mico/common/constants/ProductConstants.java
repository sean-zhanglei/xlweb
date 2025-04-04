package com.nbug.mico.common.constants;

/**
 * 商品常量类

 */
public class ProductConstants {

    /** 单规格属性 */
    public static final String SINGLE_ATTR_NAME = "规格";
    /** 单规格属性值 */
    public static final String SINGLE_ATTR_VALUE = "默认";

    // 商品类型 活动类型 0=商品，1=秒杀，2=砍价，3=拼团 attrResult表用到
    /** 商品活动类型——普通商品 */
    public static final Integer PRODUCT_TYPE_NORMAL = 0;
    /** 商品活动类型——普通商品文字 */
    public static final String PRODUCT_TYPE_NORMAL_STR = "默认";
    /** 商品活动类型——秒杀商品 */
    public static final Integer PRODUCT_TYPE_SECKILL = 1;
    /** 商品活动类型——秒杀商品文字 */
    public static final String PRODUCT_TYPE_SECKILL_STR = "秒杀";
    /** 商品活动类型——砍价商品 */
    public static final Integer PRODUCT_TYPE_BARGAIN = 2;
    /** 商品活动类型——砍价商品文字 */
    public static final String PRODUCT_TYPE_BARGAIN_STR = "砍价";
    /** 商品活动类型——拼团商品 */
    public static final Integer PRODUCT_TYPE_PINGTUAN= 3;
    /** 商品活动类型——拼团商品文字 */
    public static final String PRODUCT_TYPE_PINGTUAN_STR= "拼团";

    /**
     * 优选商品推荐key
     */
    public static final String GOOD_PRODUCT = "GOOD_PRODUCT";
    /**
     * 获取热门推荐商品列表 key
     */
    public static final String HOT_PRODUCT = "HOT_PRODUCT";

    /**
     * 获取排行榜商品列表 key
     */
    public static final String LEADER_BOARD_PRODUCT = "LEADER_BOARD_PRODUCT";

}
