package com.nbug.mico.common.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 *  自定义交易组件商品添加响应Vo
 
 */
@Data
public class ShopSpuAddSkuResponseVo {

    /** 交易组件平台自定义skuID */
    @TableField(value = "sku_id")
    private String skuId;

    /** 商家自定义skuID */
    @TableField(value = "out_sku_id")
    private String outSkuId;
}
