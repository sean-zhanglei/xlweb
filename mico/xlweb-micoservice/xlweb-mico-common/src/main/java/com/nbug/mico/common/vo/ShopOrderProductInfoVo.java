package com.nbug.mico.common.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * 生成订单Vo对象
 
 */
@Data
public class ShopOrderProductInfoVo {

    /** 交易组件平台内部商品ID */
    @TableField(value = "product_id")
    private String productId;

    /** 商家自定义商品ID */
    @TableField(value = "out_product_id")
    private String outProductId;

    /** 交易组件平台内部skuID，可填0（如果这个product_id下没有sku） */
    @TableField(value = "sku_id")
    private String skuId;

    /** 商家自定义商品skuID，可填空字符串（如果这个product_id下没有sku） */
    @TableField(value = "out_sku_id")
    private String outSkuId;

    /** 购买的数量 */
    @TableField(value = "product_cnt")
    private Integer productCnt;

    /** 生成订单时商品的售卖价（单位：分），可以跟上传商品接口的价格不一致 */
    @TableField(value = "sale_price")
    private Long salePrice;
}
