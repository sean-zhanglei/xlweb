package com.nbug.mico.common.model.cat;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 购物车表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_store_cart")
@Schema(description="购物车表")
public class StoreCart implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "购物车表ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "用户ID")
    private Integer uid;

    @Schema(description = "类型")
    private String type;

    @Schema(description = "商品ID")
    private Integer productId;

    @Schema(description = "商品属性")
    private String productAttrUnique;

    @Schema(description = "商品数量")
    private Integer cartNum;

    @Schema(description = "是否为立即购买")
    private Boolean isNew;

    @Schema(description = "拼团id")
    private Integer combinationId;

    @Schema(description = "秒杀商品ID")
    private Integer seckillId;

    @Schema(description = "砍价id")
    private Integer bargainId;

    @Schema(description = "添加时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;

    @Schema(description = "已添加的商品是否有效状态")
    private Boolean status;

    @Schema(description = "团长拼团id")
    @TableField(exist = false)
    private Integer pinkId;
}
