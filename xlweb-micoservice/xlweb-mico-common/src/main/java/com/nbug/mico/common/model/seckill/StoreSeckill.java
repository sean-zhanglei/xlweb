package com.nbug.mico.common.model.seckill;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品秒杀产品表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_store_seckill")
@Schema(description="商品秒杀产品表")
public class StoreSeckill implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "商品秒杀产品表id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "商品id")
    private Integer productId;

    @Schema(description = "推荐图")
    private String image;

    @Schema(description = "轮播图")
    private String images;

    @Schema(description = "活动标题")
    private String title;

    @Schema(description = "简介")
    private String info;

    @Schema(description = "价格")
    private BigDecimal price;

    @Schema(description = "成本")
    private BigDecimal cost;

    @Schema(description = "原价")
    private BigDecimal otPrice;

    @Schema(description = "返多少积分")
    private Integer giveIntegral;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "库存")
    private Integer stock;

    @Schema(description = "销量")
    private Integer sales;

    @Schema(description = "单位名")
    private String unitName;

    @Schema(description = "邮费")
    private BigDecimal postage;

    @Schema(description = "内容")
    private String description;

    @Schema(description = "开始时间")
    private Date startTime;

    @Schema(description = "结束时间")
    private Date stopTime;

    @Schema(description = "添加时间")
    private Date createTime;

    @Schema(description = "秒杀状态 0=关闭 1=开启")
    private Integer status;

    @Schema(description = "是否包邮")
    private Boolean isPostage;

    @Schema(description = "删除 0未删除1已删除")
    private Boolean isDel;

    @Schema(description = "最多秒杀几个")
    private Integer num;

    @Schema(description = "显示")
    private Boolean isShow;

    @Schema(description = "时间段ID")
    private Integer timeId;

    @Schema(description = "运费模板ID")
    private Integer tempId;

    @Schema(description = "重量")
    private BigDecimal weight;

    @Schema(description = "体积")
    private BigDecimal volume;

    @Schema(description = "限购总数 随销量递减")
    private Integer quota;

    @Schema(description = "限购总数显示 不变")
    private Integer quotaShow;

    @Schema(description = "规格 0单 1多")
    private Boolean specType;


}
