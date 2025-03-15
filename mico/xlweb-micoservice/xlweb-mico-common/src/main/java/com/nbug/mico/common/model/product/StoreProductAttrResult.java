package com.nbug.mico.common.model.product;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 商品属性详情表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_store_product_attr_result")
@Schema(description="商品属性详情表")
public class StoreProductAttrResult implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "ID")
    private Integer id;

    @Schema(description = "商品ID")
    private Integer productId;

    @Schema(description = "商品属性参数")
    private String result;

    @Schema(description = "上次修改时间")
    private Integer changeTime;

    @Schema(description = "活动类型 0=商品，1=秒杀，2=砍价，3=拼团")
    private Integer type;


}
