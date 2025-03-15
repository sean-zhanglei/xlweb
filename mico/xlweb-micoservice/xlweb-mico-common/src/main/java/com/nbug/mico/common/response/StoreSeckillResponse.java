package com.nbug.mico.common.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.nbug.mico.common.model.product.StoreProductAttr;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 秒杀商品 response

 */
@Data
public class StoreSeckillResponse {

    @Schema(description = "商品秒杀产品表id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "商品id")
    private Integer productId;

    @Schema(description = "推荐图")
    private String image;

    @Schema(description = "轮播图")
    private List<String> images;

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
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date startTime;

    @Schema(description = "结束时间")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date stopTime;

    @Schema(description = "添加时间")
    private Date createTime;

    @Schema(description = "秒杀状态 原本 0=关闭 1=开启")
    private Integer status;

    @Schema(description = "秒杀状态String 未开始/进行中/活动已结束")
    private String statusName;

    @Schema(description = "秒杀状态，前端用")
    private Integer killStatus;

    @Schema(description = "是否包邮")
    private Boolean isPostage;

    @Schema(description = "删除 0未删除1已删除")
    private Boolean isDel;

    @Schema(description = "最多秒杀几个")
    private Integer num;

    @Schema(description = "剩余限量")
    private int limitLeftNum;

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

    @Schema(description = "限购总数")
    private Integer quota;

    @Schema(description = "限购总数显示")
    private Integer quotaShow;

    @Schema(description = "商品属性")
    private List<StoreProductAttr> attr;

    @Schema(description = "商品属性详情")
    private List<StoreProductAttrValueResponse> attrValue;

    @Schema(description = "管理端用于映射attrResults")
    private List<HashMap<String,Object>> attrValues;

    private Integer[] cateIds;

    @Schema(description = "商品描述")
    private String content;

    @Schema(description = "秒杀配置")
    private StoreSeckillManagerResponse storeSeckillManagerResponse;

    @Schema(description = "规格 0单 1多")
    private boolean specType;

    @Schema(description = "时间戳")
    private String timeSwap;

    @Schema(description = "已抢百分比")
    private Integer percent;

    @Schema(description = "当前正在秒杀的timeId")
    private Integer currentTimeId;

    @Schema(description = "当前参与的秒杀的时间段")
    private String currentTime;
}
