package com.nbug.mico.common.model.product;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品点赞和收藏表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_store_product_relation")
@Schema(description="商品点赞和收藏表")
public class StoreProductRelation implements Serializable {

    private static final long serialVersionUID=1L;
    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "用户ID")
    private Integer uid;

    @Schema(description = "商品ID")
    private Integer productId;

    @Schema(description = "类型(收藏(collect）、点赞(like))")
    private String type = "collect";

    @Schema(description = "某种类型的商品(普通商品、秒杀商品)")
    private String category;

    @Schema(description = "创建时间")
    private Date updateTime;

    @Schema(description = "创建时间")
    private Date createTime;


}
